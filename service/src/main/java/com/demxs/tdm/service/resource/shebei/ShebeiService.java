package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.utils.excel.ImportExcel;
import com.demxs.tdm.dao.quartz.QuartzJobDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.domain.resource.shebei.*;
import com.demxs.tdm.service.resource.changshangygys.ChangshanggysxxService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.quartz.QuartzJob;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.comac.common.constant.ZhishiConstans;
import com.demxs.tdm.service.ability.AptitudeService;
import com.demxs.tdm.service.business.TestPlanExecuteDetailService;
import com.demxs.tdm.service.business.TestPlanService;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.job.QuartzManager;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.IActAuditService;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.list.SetUniqueList;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import com.demxs.tdm.service.job.MyJob;

/**
 * 设备信息Service
 * @author zhangdengcai
 * @version 2017-06-13
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiService extends CrudService<ShebeiDao, Shebei> {

    @Autowired
    private ShebeiDao shebeiDao;
    @Autowired
    private QuartzJobDao quartzJobDao;
    @Autowired
    private QuartzJobService quartzJobService;
    @Autowired
    private ShebeiLxService shebeiLxService;
    @Autowired
    private AttachmentInfoService attachmentInfoService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private ShebeiCfwzService shebeiCfwzService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ChangshanggysxxService changshanggysxxService;
    @Autowired
    private AptitudeService aptitudeService;
    @Autowired
    private ShebeiQitingjlService shebeiQitingjlService;
    @Autowired
    private LabInfoService labInfoService;
    private IActAuditService iActAuditService;
    @Autowired
    private ExternalApi externalApi;
    @Autowired
    private TestPlanExecuteDetailService testPlanExecuteDetailService;
    @Autowired
    private TestPlanService testPlanService;

    @Autowired
    EquipmentFileService equipmentFileService;

    @Autowired
    EquipmentInfoService equipmentInfoService;
    @Autowired
    EquipmentContentService equipmentContentService;
    @Autowired
    UserDao userDao;
    @Autowired
    OfficeDao officeDao;


    private Shebei transferShebei(Shebei s){
        if (s.getShiyongdw()!=null && StringUtils.isNotBlank(s.getShiyongdw().getId())) {
            s.setShiyongdw(officeService.get(s.getShiyongdw().getId()));
        }else{
            s.setShiyongdw(new Office());
        }
        if (s.getShebeicfwz()!=null && StringUtils.isNotBlank(s.getShebeicfwz().getId())) {
            s.setShebeicfwz(shebeiCfwzService.get(s.getShebeicfwz().getId()));
            s.getShebeicfwz().setWeizhimc(shebeiCfwzService.getAllName(s.getShebeicfwz(),"weizhimc"));
        } else {
            s.setShebeicfwz( new ShebeiCfwz());
        }

        if (s.getJlsyfzr()!=null &&StringUtils.isNotBlank(s.getJlsyfzr().getId())) {
            s.setJlsyfzr(systemService.getUser(s.getJlsyfzr().getId()));
        } else {
            s.setJlsyfzr(new User());
        }
        if (s.getShebeigly()!=null && StringUtils.isNotBlank(s.getShebeigly().getId())) {
            s.setShebeigly(systemService.getUser(s.getShebeigly().getId()));
        } else {
            s.setShebeigly(new User());
        }
        if (s.getShebeics()!=null && StringUtils.isNotBlank(s.getShebeics().getId())) {
            s.setShebeics(changshanggysxxService.get(s.getShebeics().getId()));
        } else {
            s.setShebeics(new Changshanggysxx());
        }
        if (s.getShebeigys()!=null && StringUtils.isNotBlank(s.getShebeigys().getId())) {
            s.setShebeigys(changshanggysxxService.get(s.getShebeigys().getId()));
        } else {
            s.setShebeigys(new Changshanggysxx());
        }
        if (s.getShebeilx()!=null && StringUtils.isNotBlank(s.getShebeilx().getId())) {
            s.setShebeilx(shebeiLxService.get(s.getShebeilx().getId()));
        } else {
            s.setShebeilx(new ShebeiLx());
        }
        //获取设备图片
        if(StringUtils.isNotEmpty(s.getShebeitp())){
            List<AttachmentInfo> zpList = new ArrayList<AttachmentInfo>();
            String[] zpArr = s.getShebeitp().split(",");
            for(String zpId:zpArr){
                zpList.add(attachmentInfoService.get(zpId));
            }
            s.setZpList(zpList);
        }
        //获取设备资料
        if(StringUtils.isNotEmpty(s.getShebeizl())){
            List<AttachmentInfo> zlList = new ArrayList<AttachmentInfo>();
            String[] zlArr =s.getShebeizl().split(",");
            for(String zlId:zlArr){
                zlList.add(attachmentInfoService.get(zlId));
            }
            s.setZlList(zlList);
        }
        //获取设备资质
        if(StringUtils.isNotEmpty(s.getShebeizz())){
            List<Aptitude> zzList = new ArrayList<Aptitude>();
            String[] zzArr =s.getShebeizz().split(",");
            for(String zzid:zzArr){
                zzList.add(aptitudeService.get(zzid));
            }
            s.setZzList(zzList);
        }
        //获取试验室
        s.setLabInfo(labInfoService.get(s.getLabInfoId()));
        return s;
    }
    private Shebei sampleShebei(Shebei s){
        if (s.getShebeicfwz()!=null && StringUtils.isNotBlank(s.getShebeicfwz().getId())) {
            //s.setShebeicfwz(shebeiCfwzService.get(s.getShebeicfwz().getId()));
            s.getShebeicfwz().setWeizhimc(shebeiCfwzService.getAllName(s.getShebeicfwz(),"weizhimc"));
        } else {
            s.setShebeicfwz( new ShebeiCfwz());
        }
        if (s.getShebeigly()!=null && StringUtils.isNotBlank(s.getShebeigly().getId())) {
            s.setShebeigly(systemService.getUser(s.getShebeigly().getId()));
        } else {
            s.setShebeigly(new User());
        }
        return s;
    }

    @Override
    public Shebei get(String id) {
        Shebei shebei = super.get(id);
        if(shebei != null){
            transferShebei(shebei);
        }
        return shebei;
    }

    public  List<Shebei> getKind(String id, String tt) {
        List<Shebei> kind = shebeiDao.getKind(id, tt);
        List<Shebei> shebeis = new ArrayList<Shebei>();
        for (Shebei shebei : kind){
            if(shebei != null){
                shebeis.add(transferShebei(shebei));
            }
        }

        return shebeis;
    }

    @Override
    public List<Shebei> findList(Shebei shebei) {
        //shebei.getSqlMap().put("dsf", dataScopeFilter(shebei.getCurrentUser(), "o", "u8"));
        return super.findList(shebei);
    }

    @Override
    public Page<Shebei> findPage(Page<Shebei> page, Shebei shebei) {
        /*shebei.getSqlMap().put("dsf", dataScopeFilter(shebei.getCurrentUser(), "o", "u8"));*/
        Page<Shebei> shebeiPage = super.findPage(page, shebei);
        if(shebeiPage!=null){
            for(Shebei s:shebeiPage.getList()){
                sampleShebei(s);
            }
        }
        return shebeiPage;
    }

    /**
     * 获取有效设备（审核通过的 且没有报废的）
     * @param page
     * @param shebei
     * @return
     */
    public Page<Shebei> findValidPage(Page<Shebei> page, Shebei shebei) {
        /*user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
        // 设置分页参数
        user.setPage(page);
        // 执行分页查询
        page.setList(userDao.findList(user));*/
        //shebei.getSqlMap().put("dsf",dataScopeFilter(shebei.getCurrentUser(), "o", "a"));
        shebei.setPage(page);
        page.setList(dao.findValidShebeis(shebei));
        if(page!=null){
            for(Shebei s:page.getList()){
                sampleShebei(s);
            }
        }
        return page;
    }

    public List<Map> listWithTaskCount(){

        List<Map> list = new ArrayList<>();
        if(UserUtils.getUser().isAdmin()){
            list = dao.listWithTaskCount(null);
        }else {
            list = dao.listWithTaskCount(UserUtils.getUser().getId());
        }
        for (Map shebei : list) {
            if(ShebeiConstans.shebeizt.KONGXIAN.equals(shebei.get("SHEBEIZT"))){
                Integer count = dao.testingCount((String) shebei.get("ID"));
                if (count > 0) {
                    shebei.put("SHEBEIZT",100);
                }
            }
        }
        return list;
    }
    /**
     * 根据编号查设备 json格式返回
     * @param shebeimc
     * @return
     */
    public String findSheBei(String shebeimc){
        Shebei shebei = new Shebei();
        shebei.setShebeimc(shebeimc);
        return JsonMapper.toJsonString(this.dao.findList(shebei));
    }

    /**
     * 保存
     * @param shebei
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save( Shebei shebei) {
        Boolean isNew = this.dao.get(shebei.getId()) == null ;
        if(shebei.getLabInfo() != null){
            shebei.setLabInfoId(shebei.getLabInfo().getId());
            shebei.setLabInfoName(labInfoService.get(shebei.getLabInfoId()).getName());
        }
        if(shebei.getType()!=null && shebei.getType().equals(Shebei.TIJIAO)){
            shebei.setAddAuditStatus(ShebeiConstans.sheBeicgdzt.DAISHENH);
        }else{
            if(StringUtils.isBlank(shebei.getId())){
                shebei.setAddAuditStatus(ShebeiConstans.sheBeicgdzt.DAITIJ);
                shebei.setId(IdGen.uuid());
            }
        }
        shebei.setFirstStartDate(new Date());
        shebei.setIsNewRecord(isNew);
        super.save(shebei);
        List<EquipmentFile> fileList = shebei.getEquipmentFileList();
        if(!CollectionUtils.isEmpty(fileList)){
            for(EquipmentFile e:fileList){
                e.setEquipmentId(shebei.getId());
                equipmentFileService.saveEquipmentDetail(e);
            }
        }
        /*if(ShebeiConstans.sheBeicgdzt.DAISHENH.equals(shebei.getAddAuditStatus())){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("labLeader",labInfoService.get(shebei.getLabInfoId()).getLeader().getLoginName());
            map.put("message", MessageConstants.shebeiMessage.ADD);
            starAddtAudit(shebei.getId(),shebei.getShebeimc()+MessageConstants.auditMessage.ADD,shebei.getYijian(),map);
        }*/
        if(CollectionUtils.isNotEmpty(shebei.getSubEquipmentList())){
            //移除子设备
            this.dao.removeShebeiParent(shebei.getId());
            //更新子设备
            this.dao.updateShebeiParent(shebei.getId(),shebei.getSubEquipmentList());
        }
    }

    /**
     * 发起设备新增流程审核
     */
    public void starAddtAudit(String id,String title,String comment,Map<String,Object> map){
        try{
            logger.debug("开始发起设备新增审核流程ID：{}"+id);
            iActAuditService = getiActAuditService();
            iActAuditService.start(id, GlobalConstans.ActProcDefKey.SHEBEIXZSH,title,comment,map);
        }catch (Exception e){
            logger.error("发起设备新增审核流程失败！",e);
        }
    }
    /**
     * 发起设备报废流程审核
     */
    public void starOvertAudit(String id,String title,String comment,Map<String,Object> map){
        try{
            logger.debug("开始发起设备报废审核流程ID：{}"+id);
            iActAuditService = getiActAuditService();
            iActAuditService.start(id, GlobalConstans.ActProcDefKey.SHEBEIBFSH,title,comment,map);
        }catch (Exception e){
            logger.error("发起设备报废审核流程失败！",e);
        }
    }

    public IActAuditService getiActAuditService() {
        if(iActAuditService == null){
            iActAuditService = SpringContextHolder.getBean(IActAuditService.class);
        }
        return iActAuditService;
    }

    /**
     * 保存审核信息
     * @param id     实体id
     * @param isPass 状态
     */
    public void saveShenhe(String id,String isPass){
        if(StringUtils.isNoneBlank(id)){
            Shebei entity = super.get(id);
            if(isPass.equals(ZhishiXx.CHEXIAO)){
                entity.setAddAuditStatus(ZhishiConstans.optStatus.DAITIJIAO);//撤销
            }
            if (isPass.equals(ZhishiXx.TONGGUO)) {
                entity.setShebeizt(ShebeiConstans.shebeizt.KONGXIAN);
                entity.setShebeiqtzt(ShebeiConstans.ShebeiQTZT.START);
                entity.setFirstStartDate(new Date());
                entity.setAddAuditStatus(ZhishiConstans.optStatus.YISHENHE);//通过
                sendAuditTodo(entity,"add");
            }
            if (isPass.equals(ZhishiXx.BOHUI)) {
                entity.setAddAuditStatus(ZhishiConstans.optStatus.BOHUI);//驳回
            }
            entity.preUpdate();
            entity.setFirstStartDate(new Date());
            this.dao.update(entity);

        }
    }
    private void sendAuditTodo(Shebei entity,String type){
        SendTodoParam sendTodoParam = new SendTodoParam();
        sendTodoParam.setType(2);
        if(type.equals("add")){//新增
            sendTodoParam.setSubject("您的设备新增"+entity.getShebeimc()+"申请已通过");
        }else{
            sendTodoParam.setSubject("您的设备报废"+entity.getShebeimc()+"申请已通过");
        }
        sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/shebei/detail?id="+entity.getId());
        sendTodoParam.setCreateTime(new Date());
        sendTodoParam.setModelId(IdGen.uuid());
        sendTodoParam.addTarget(systemService.getUser(entity.getUpdateBy().getId()).getLoginName());
        externalApi.sendTodo(sendTodoParam);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(Shebei shebei) { super.delete(shebei); }

    /**
     * 设备报废审核
     * @param shebeiid
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void shebeiOver(String shebeiid,String overYijian){
        Shebei shebei = get(shebeiid);
        shebei.setOverAuditStatus(ShebeiConstans.sheBeicgdzt.DAISHENH);
        shebei.preUpdate();
        this.dao.update(shebei);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("labLeader",labInfoService.get(shebei.getLabInfoId()).getLeader().getLoginName());
        map.put("message", MessageConstants.shebeiMessage.OVER);
        starOvertAudit(shebei.getId(),shebei.getShebeimc()+MessageConstants.auditMessage.OVER,overYijian,map);
    }

    /**
     * 修改设备状态
     * @param shebeiid 设备id
     * @param status 状态
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void updateShebeiStatus(String shebeiid,String status){
        this.dao.updateShebeiStatus(shebeiid,status);
    }

    /**
     * 保存报废审核信息
     * @param id     实体id
     * @param isPass 状态
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveShenheOver(String id,String isPass){
        if(StringUtils.isNoneBlank(id)){
            Shebei entity = super.get(id);
            if(isPass.equals(ZhishiXx.CHEXIAO)){
                entity.setAddAuditStatus(ZhishiConstans.optStatus.DAITIJIAO);//撤销
            }
            if (isPass.equals(ZhishiXx.TONGGUO)) {
                entity.setShebeizt(ShebeiConstans.shebeizt.YIBAOF);
                entity.setOverAuditStatus(ZhishiConstans.optStatus.YISHENHE);//通过
                sendAuditTodo(entity,"over");
            }
            if (isPass.equals(ZhishiXx.BOHUI)) {
                entity.setOverAuditStatus(ZhishiConstans.optStatus.BOHUI);//驳回
            }
            entity.preUpdate();
            this.dao.update(entity);

        }
    }

    public boolean checkTimeValid(String shebeiid,String qitingStatus,Date qtTime){
        //获取该设备的
        if(ShebeiConstans.ShebeiQTZT.START.equals(qitingStatus)){//启动
            //
            List<ShebeiQitingjl> jls = shebeiQitingjlService.findListByType(shebeiid,ShebeiConstans.ShebeiQTZT.STOP);
            if(!CollectionUtils.isNotEmpty(jls)){
                return false;
            }else{
                if(DateUtils.compare(qtTime,jls.get(0).getQtTime())<=0){
                    return false;
                }else {
                    return  true;
                }
            }
        }else{
            //暂停
            List<ShebeiQitingjl> jls = shebeiQitingjlService.findListByType(shebeiid,ShebeiConstans.ShebeiQTZT.START);
            if(!CollectionUtils.isNotEmpty(jls)){
                if(DateUtils.compare(qtTime,this.dao.get(shebeiid).getFirstStartDate())<=0){
                    return false;
                }else {
                    return true;
                }
            }else{
                if(DateUtils.compare(qtTime,jls.get(0).getQtTime())<=0){
                    return false;
                }else {
                    return true;
                }
            }
        }

    }

    private void realJob(String shebeiid,String qitingStatus,Date qtTime,String remarks){
        ShebeiQitingjl jl = new ShebeiQitingjl(null,shebeiid,qitingStatus,this.get(shebeiid).getShebeibh(),qtTime,remarks);
        shebeiQitingjlService.save(jl);//一条记录


        Date realTime = DateUtils.getBeforeHourTime(qtTime,2);
        //判断当前时间
        if(DateUtils.compare(qtTime,new Date())<=0){
            jl.setValid("1");
            upQtStatus(shebeiid,qitingStatus,qtTime,remarks);
            List<User> users = testPlanExecuteDetailService.getTestChargeByDeviceuse(this.get(shebeiid).getShebeibh(),qtTime);
            if(CollectionUtils.isNotEmpty(users)){
                for(User u:users){
                    if (u!=null) {
                        sendTodo(shebeiid,this.get(shebeiid).getShebeibh(),qitingStatus,u,qtTime,this.dao.get(shebeiid).getShebeimc());
                    }
                }
            }
        }else{
            String id = UUID.randomUUID().toString();
            Map<String,Object> map = new HashMap<>();
            map.put("id",id);
            map.put("jlid",jl.getId());
            map.put("qtTime",qtTime.getTime());
            map.put("shebeiid",shebeiid);
            map.put("type",qitingStatus);
            map.put("remarks",remarks);
            QuartzJob quartzJob = new QuartzJob("设备启停任务"+id,"shebei_qt_group","shebei_qt_trigger"+id,"shebei_qt_trigger_group"+id,ShebeiConstans.quartzClassType.SHEBEI_QT,DateUtils.getCron(qtTime),qtTime,JsonMapper.toJsonString(map),"0");
            quartzJob.setId(id);
            quartzJob.setDelFlag("0");
            quartzJobDao.insert(quartzJob);
            QuartzManager.addJob(quartzJob);
            //添加一条无效的使用记录

            jl.setValid("0");//无效的
            jl.setJobId(quartzJob.getId());

            if(DateUtils.compare(realTime,new Date())<=0){//不足两个小时
                List<User> users = testPlanExecuteDetailService.getTestChargeByDeviceuse(this.get(shebeiid).getShebeibh(),qtTime);
                if(CollectionUtils.isNotEmpty(users)){
                    for(User u:users){
                        if(u!=null){
                            sendTodo(shebeiid,this.get(shebeiid).getShebeibh(),qitingStatus,u,qtTime,this.dao.get(shebeiid).getShebeimc());
                        }

                    }
                }
            }else{
                String txid = UUID.randomUUID().toString();
                Map<String,Object> param = new HashMap<>();
                param.put("id",txid);
                param.put("jlid",jl.getId());
                param.put("shebeiid",shebeiid);
                param.put("qtTime",qtTime.getTime());
                param.put("shebeimc",this.get(shebeiid).getShebeimc());
                param.put("shebeibh",this.get(shebeiid).getShebeibh());
                param.put("type",qitingStatus);
                QuartzJob quartzJob2 = new QuartzJob("设备启停发消息任务"+txid,"shebei_qt_send_group","shebei_qt_send_trigger"+txid,"shebei_qt_send_trigger_group"+txid,ShebeiConstans.quartzClassType.SHEBEI_QT_SEND,DateUtils.getCron(realTime),realTime,JsonMapper.toJsonString(param),"0");
                quartzJob2.setId(txid);
                quartzJob2.setDelFlag("0");
                quartzJobDao.insert(quartzJob2);
                QuartzManager.addJob(quartzJob2);
                jl.setTxJobId(quartzJob2.getId());
            }
        }
        shebeiQitingjlService.save(jl);
    }


    public void updateQTType(ShebeiQitingjl shebeiQitingjl){
        if(StringUtils.isNotBlank(shebeiQitingjl.getId())){

            shebeiQitingjlService.delete(shebeiQitingjl);
            //表示修改
            //删除相关联的任务
            ShebeiQitingjl jl = shebeiQitingjlService.get(shebeiQitingjl.getId());
            if(StringUtils.isNotBlank(jl.getJobId())){
                quartzJobDao.delete(new QuartzJob(jl.getJobId()));
                QuartzJob job = quartzJobService.get(jl.getJobId());
                QuartzManager.removeJob(job);
            }
            if(StringUtils.isNotBlank(jl.getTxJobId())){
                quartzJobDao.delete(new QuartzJob(jl.getTxJobId()));
                QuartzJob job = quartzJobService.get(jl.getTxJobId());
                QuartzManager.removeJob(job);
            }
        }
        updateShebeiQTStatus(shebeiQitingjl.getShebeiId(),shebeiQitingjl.getStatus(),shebeiQitingjl.getQtTime(),shebeiQitingjl.getRemarks());
    }

    /**
     * 修改设备启停状态
     * @param shebeiid
     * @param qitingStatus
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void updateShebeiQTStatus(String shebeiid,String qitingStatus,Date qtTime,String remarks){
        //如果是启动的话调计划调整接口
        realJob(shebeiid, qitingStatus, qtTime, remarks);

    }

    public void upQtStatus(String shebeiid,String qitingStatus,Date qtTime,String remarks){
        this.dao.updateShebeiQTStatus(shebeiid,qitingStatus);
        if(qitingStatus.equals(ShebeiConstans.ShebeiQTZT.START)){
            this.dao.updateShebeiStatus(shebeiid,ShebeiConstans.shebeizt.KONGXIAN);
        }else if(qitingStatus.equals(ShebeiConstans.ShebeiQTZT.STOP)){
            this.dao.updateShebeiStatus(shebeiid,ShebeiConstans.shebeizt.ZHANYONG);
        }
        //shebeiQitingjlService.save(new ShebeiQitingjl(null,shebeiid,qitingStatus,this.get(shebeiid).getShebeibh(),qtTime,remarks));
    }


    public void sendTodo(String shebeiid,String shebeibh,String type,User u,Date date,String shebeimc){

        SendTodoParam sendTodoParam = new SendTodoParam();
        sendTodoParam.setType(2);
        if(type.equals(ShebeiConstans.ShebeiQTZT.STOP)){
            sendTodoParam.setSubject(String.format(MessageConstants.qtMessage.QT,shebeibh,shebeimc,DateUtils.formatDate(date,"yyyy-MM-dd HH:mm:ss"),"停止"));
        }else{
            sendTodoParam.setSubject(String.format(MessageConstants.qtMessage.QT,shebeibh,shebeimc,DateUtils.formatDate(date,"yyyy-MM-dd HH:mm:ss"),"开启"));
        }

        sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/shebei/qiting/tolist?shebeid="+shebeiid);
        sendTodoParam.setCreateTime(new Date());
        sendTodoParam.setModelId(IdGen.uuid());
        sendTodoParam.addTarget(u.getLoginName());
        externalApi.sendTodo(sendTodoParam);
    }

    /**
     * 根据设备资质查找设备
     * @param idStr 逗号拼接设备资质ID
     * @param labId
     * @return
     */
    public List<Shebei> findByZizhi(String idStr,String labId){
        List<Shebei> result = SetUniqueList.decorate(new ArrayList<>());
        if (StringUtils.isEmpty(idStr)) {
            return result;
        }
        for (String id : idStr.split(",")) {
            result.addAll(dao.findByZizhi(id,labId));
        }
        return result;
    }

    /**
     * 根据设备ids获取设备 逗号分隔的id
     * @param ids
     * @return
     */
    public List<Shebei> findByIds(String ids){

        return this.dao.findByIds(ids);
    }


    /**
     * 根据设备id 编号获取(有id的话 除了此id看其他有没有重复的编号  没有id的话 就是看编号有没有重复的)
     * @param id
     * @param code
     * @return
     */
    public Boolean checkCodeUnique(String id,String code){

        Shebei shebei = this.dao.getShebeiByCodeId(id,code);
        if(shebei==null || StringUtils.isBlank(shebei.getId())){
            return true;
        }else{
            return false;
        }
    }

    public Shebei getByCode(String code){
        return this.dao.getByCode(code);
    }

    public Shebei getByNewCode(String newCode){
        return this.dao.getByNewCode(newCode);
    }


    /**
     * @Describe:根据分中心后去设备汇总数据
     * @Author:WuHui
     * @Date:9:32 2020/8/31
     * @param centerId
     * @return:int
    */
    public Integer countShebeiByCenter(String centerId) {
        //获取实验设备
        Integer cnt = dao.countShebeiByCenter(centerId);
        return cnt;
    }

    /**
     * @Describe:根据分中心查询列表数据
     * @Author:WuHui
     * @Date:9:33 2020/8/31
     * @param page
     * @param centerId
     * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.resource.shebei.Shebei>
    */
    public Page<Shebei> findShebeiByCenter(Page<Shebei> page, String centerId, String labinfoId) {
        List<Shebei> list = dao.findShebeiByCenter(page, centerId, labinfoId);
        page.setList(list);
        if(page!=null){
            for(Shebei s:page.getList()){
                sampleShebei(s);
            }
        }
        return page;
    }

    public List<Shebei> buidShebeiTreeList(List<Shebei> list/*Shebei shebei*/){
        //List<Shebei> list = this.findList(shebei);
        List<Shebei> all = this.findList(new Shebei());
        Map<String,Shebei> map = new HashMap();
        for(Shebei sb:list){
            if((StringUtils.isNotBlank(sb.getKind()) && sb.getKind().equals("02")) || StringUtils.isEmpty(sb.getParentId())){
                map.put(sb.getId(),sb);
            }
        }
        for(Shebei sb:all){
            if(StringUtils.isNotEmpty(sb.getParentId())){
                if( map.get(sb.getParentId())!=null && map.get(sb.getParentId()).getChildren()!=null){
                    map.get(sb.getParentId()).getChildren().add(sb);
                }

            }
        }
        List<Shebei> tree =  new ArrayList<Shebei>(map.values());;
        return tree;
    }

    /**
     * @Describe:获取设备维护计划列表
     * @Author:WuHui
     * @Date:17:15 2020/11/17
     * @param shebei
     * @return:java.util.List<com.demxs.tdm.domain.resource.shebei.Shebei>
    */
    public List<Shebei> shebeiPlanList(@RequestBody Shebei shebei){
        List<Shebei> list = null ;
        if(CollectionUtils.isNotEmpty(shebei.getIds())){
            list= this.dao.findAllList(shebei);
            for(Shebei  s:list){
                EquipmentInfo  ei  = new EquipmentInfo();
                ei.setEquipmentId(s.getId());
                ei.setDefendYear(DateUtils.getYear());
                List<EquipmentInfo> equipmentList= equipmentInfoService.getEquipmentByEpId(ei);
                if(!CollectionUtils.isEmpty(equipmentList)){
                    List<EquipmentInfo>   listEqu= equipmentList.stream().sorted(Comparator.comparing(EquipmentInfo::getId).reversed()).collect(Collectors.toList());
                    s.setEquipmentInfo(listEqu.get(0));
                    List<EquipmentContent>  listContext=equipmentContentService.getEqContentByEpId(listEqu.get(0).getId());
                    s.setEquipmentContentList(listContext);
                }
            }
        }
        return list;
    }

    /**
     * 获取上一次维护记录
     */
    public String findContent(String id){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        String str = year+"-"+month;
        if(month < 10){
            str=year+"-0"+month;
        }
        return shebeiDao.findContent(id,str);
    }

    /**
     * 设备Excel导入
     * @param file
     */
    public void importData(MultipartFile file)throws Exception{
        Calendar c = new GregorianCalendar(1900,0,-1);
        ImportExcel importExcel = new ImportExcel(file,1,0);
        int rowTotal = importExcel.getLastDataRowNum();
        for (int i=2;i<rowTotal;i++){
            Row row = importExcel.getRow(i);
            for(int j=1;j<row.getLastCellNum();j++) {
                Shebei shebei = new Shebei();
                String sheibeiMC = importExcel.getCellValue(row, j).toString();//设备名称
                String newCode = importExcel.getCellValue(row, ++j).toString();//新设备编号
                String oldCode = importExcel.getCellValue(row, ++j).toString();//原设备编号
                String modelNorm = importExcel.getCellValue(row, ++j).toString();//规格型号
                String measureScope = importExcel.getCellValue(row, ++j).toString();//主要参数或测量范围
                String runningState = importExcel.getCellValue(row, ++j).toString();//设备运行状态
                String engineeringOffice = importExcel.getCellValue(row, ++j).toString();//所属技术室
                String labName = importExcel.getCellValue(row, ++j).toString();//公司试验室名称
                String shebeicfwz = importExcel.getCellValue(row, ++j).toString();//设备安放地点
                String principal = importExcel.getCellValue(row, ++j).toString();//设备负责人
                String principalNo = importExcel.getCellValue(row, ++j).toString();//设备负责人工号
                String admin = importExcel.getCellValue(row, ++j).toString();//设备管理员
                String adminNo = importExcel.getCellValue(row, ++j).toString();//设备管理员工号
                String certiNum = importExcel.getCellValue(row, ++j).toString();//有资质设备操作人员数量
                String environmentRequire = importExcel.getCellValue(row, ++j).toString();//使用环境要求
                String recordNo = importExcel.getCellValue(row, ++j).toString();//设备履历簿编号
                String manufacturer = importExcel.getCellValue(row, ++j).toString();//制造商
                Object cellValue = importExcel.getCellValue(row, ++j);
                String productionTime = cellValue.toString();//生产时间
                String acceptTime = importExcel.getCellValue(row, ++j).toString();//验收时间
                String comeIntoUseTime = importExcel.getCellValue(row, ++j).toString();//投入使用时间
                String purContractCode = importExcel.getCellValue(row, ++j).toString();//采购合同编号
                String businessProcess = importExcel.getCellValue(row, ++j).toString();//对应业务流程
                String isReplaceSB = importExcel.getCellValue(row, ++j).toString();//是否由替代设备
                String maintainYear = importExcel.getCellValue(row, ++j).toString();//维护年份
                String maintain1th = importExcel.getCellValue(row, ++j).toString();//维护内容1月
                String maintain2th = importExcel.getCellValue(row, ++j).toString();//维护内容2月
                String maintain3th = importExcel.getCellValue(row, ++j).toString();//维护内容3月
                String maintain4th = importExcel.getCellValue(row, ++j).toString();//维护内容4月
                String maintain5th = importExcel.getCellValue(row, ++j).toString();//维护内容5月
                String maintain6th = importExcel.getCellValue(row, ++j).toString();//维护内容6月
                String maintain7th = importExcel.getCellValue(row, ++j).toString();//维护内容7月
                String maintain8th = importExcel.getCellValue(row, ++j).toString();//维护内容8月
                String maintain9th = importExcel.getCellValue(row, ++j).toString();//维护内容9月
                String maintain10th = importExcel.getCellValue(row, ++j).toString();//维护内容10月
                String maintain11th = importExcel.getCellValue(row, ++j).toString();//维护内容11月
                String maintain12th = importExcel.getCellValue(row, ++j).toString();//维护内容12月
                String totalWorkHours = importExcel.getCellValue(row, ++j).toString();//年度总工时数(单位：小时）
                String firstPlanHours       = importExcel.getCellValue(row, ++j).toString();//一季度计划工时数(单位：小时）
                String firstpracticalHours  = importExcel.getCellValue(row, ++j).toString();//一季度实际工时数(单位：小时）
                String firstUserRatio       = importExcel.getCellValue(row, ++j).toString();//第一季度利用率
                String firstFailure         = importExcel.getCellValue(row, ++j).toString();//第一季度故障率
                String secPlanHours         = importExcel.getCellValue(row, ++j).toString();//二季度计划工时数(单位：小时）
                String secpracticalHours    = importExcel.getCellValue(row, ++j).toString();//二季度实际工时数(单位：小时）
                String secUserRatio         = importExcel.getCellValue(row, ++j).toString();//第二季度利用率
                String secFailure           = importExcel.getCellValue(row, ++j).toString();//第二季度故障率
                String thirPlanHours        = importExcel.getCellValue(row, ++j).toString();//三季度计划工时数(单位：小时）
                String thirpracticalHours   = importExcel.getCellValue(row, ++j).toString();//三季度实际工时数(单位：小时）
                String thirUserRatio        = importExcel.getCellValue(row, ++j).toString();//第三季度利用率
                String thirFailure          = importExcel.getCellValue(row, ++j).toString();//第三季度故障率
                String fourPlanHours        = importExcel.getCellValue(row, ++j).toString();//四季度计划工时数(单位：小时）
                String fourpracticalHours   = importExcel.getCellValue(row, ++j).toString();//四季度实际工时数(单位：小时）
                String fourUserRatio        = importExcel.getCellValue(row, ++j).toString();//第四季度利用率
                String fourFailure          = importExcel.getCellValue(row, ++j).toString();//第四季度故障率
                String remarks              = importExcel.getCellValue(row, ++j).toString();//备注


               /**存放位置*/
                ShebeiCfwz shebeiCfwz = new ShebeiCfwz();
                List<ShebeiCfwz> byName = shebeiCfwzService.getByName(shebeicfwz);
                if(CollectionUtils.isNotEmpty(byName)){
                    shebeiCfwz = byName.get(0);
                }
                EquipmentInfo equipmentInfo = new EquipmentInfo();  //维护内容
                List<EquipmentContent> equipmentContents = new ArrayList<EquipmentContent>();
                User user = new User();
                /**所属技术室*/
                Office office = new Office();
                office.setName(engineeringOffice);
                List<Office> listByNameIfExist = officeDao.findListByNameIfExist(office);
                if(CollectionUtils.isNotEmpty(listByNameIfExist)){
                    shebei.setOfficeId(listByNameIfExist.get(0).getId());   //技术试验室
                }
                /**设备负责人*/
                User responsible = new User();
                //数据存放
                shebei.setShebeimc(sheibeiMC);
                shebei.setNewCode(newCode);
                shebei.setGudingzcbm(oldCode);
                shebei.setModelNorm(modelNorm);
                shebei.setMeasureScope(measureScope);
                shebei.setRunningState(runningState);
                shebei.setLabInfoName(labName);     //公司试验室
                shebei.setShebeicfwz(shebeiCfwz);
                if(StringUtils.isNotBlank(principalNo) && principalNo.length()>2){
                    responsible.setNo(principalNo.substring(0,principalNo.length()-2));
                }
                if(userDao.getByEmpNo(responsible)!=null){
                    userDao.getByEmpNo(responsible).getId();
                    shebei.setResponsibleId(userDao.getByEmpNo(responsible).getId());   //设备负责人
                }
                if(StringUtils.isNotBlank(adminNo) && adminNo.length() > 2 ){
                    user.setNo(adminNo.substring(0,adminNo.length()-2));
                }

                shebei.setShebeigly(userDao.getByEmpNo(user));   //设备管理员
                shebei.setCertiNum(certiNum);//有资质设备操作人员数量
                shebei.setEnvironmentRequire(environmentRequire);//使用环境要求
                shebei.setRecordNo(recordNo);   //设备履历簿编号
                shebei.setZzsMc(manufacturer);//制造商
                if(StringUtils.isNotBlank(productionTime) && productionTime.length() > 2  ){
                    shebei.setShengcrq(DateUtils.addDays(c.getTime(), Integer.parseInt(productionTime.substring(0,productionTime.length()-2))));//生产时间
                }
                if(StringUtils.isNotBlank(acceptTime)  && acceptTime.length() > 2){
                    shebei.setGoumairq(DateUtils.addDays(c.getTime(), Integer.parseInt(acceptTime.substring(0,acceptTime.length()-2))));//验收时间
                }
                if(StringUtils.isNotBlank(comeIntoUseTime) && comeIntoUseTime.length() > 2){
                    shebei.setTouyongrq(DateUtils.addDays(c.getTime(), Integer.parseInt(comeIntoUseTime.substring(0,comeIntoUseTime.length()-2))));//投入使用时间
                }
                shebei.setContract(purContractCode);//采购合同编号
                //是否由替代设备
                equipmentInfo.setDefendYear(maintainYear);
                //维护内容
                if(StringUtils.isNotBlank(maintainYear)){
                    for(int m=0;m<12;m++){
                         String str = maintainYear+"-"+(String.valueOf(m));
                         EquipmentContent equipmentContent = new EquipmentContent(); //维护内容详细信息
                         equipmentContent.setDefendTime(str);
                         switch (i){
                             case 0: equipmentContent.setDefendContent(maintain1th);
                                 break;
                             case 1: equipmentContent.setDefendContent(maintain2th);
                                 break;
                             case 2: equipmentContent.setDefendContent(maintain3th);
                                 break;
                             case 3: equipmentContent.setDefendContent(maintain4th);
                                 break;
                             case 4: equipmentContent.setDefendContent(maintain5th);
                                 break;
                             case 5: equipmentContent.setDefendContent(maintain6th);
                                 break;
                             case 6: equipmentContent.setDefendContent(maintain7th);
                                 break;
                             case 7: equipmentContent.setDefendContent(maintain8th);
                                 break;
                             case 8: equipmentContent.setDefendContent(maintain9th);
                                 break;
                             case 9: equipmentContent.setDefendContent(maintain10th);
                                 break;
                             case 10: equipmentContent.setDefendContent(maintain11th);
                                 break;
                             case 11: equipmentContent.setDefendContent(maintain12th);
                                 break;
                         }
                         equipmentContents.add(equipmentContent);
                        }
                }
                equipmentInfo.setEquipmentContentList(equipmentContents);
                //是否替代设备
                shebei.setIsReplaceSB(isReplaceSB);
                //年度总工时
                shebei.setTotalWorkHours(totalWorkHours);
                //工时
                shebei.setFirstPlanHours(firstPlanHours);
                shebei.setFirstpracticalHours(firstpracticalHours);
                shebei.setFirstUserRatio(firstUserRatio);
                shebei.setFirstFailure(firstFailure);
                shebei.setSecPlanHours(secPlanHours);
                shebei.setSecpracticalHours(secpracticalHours);
                shebei.setSecUserRatio(secUserRatio);
                shebei.setSecFailure(secFailure);
                shebei.setThirPlanHours(thirPlanHours);
                shebei.setThirpracticalHours(thirpracticalHours);
                shebei.setThirUserRatio(thirUserRatio);
                shebei.setThirFailure(thirFailure);
                shebei.setFourPlanHours(fourPlanHours);
                shebei.setFourpracticalHours(fourpracticalHours);
                shebei.setFourUserRatio(fourUserRatio);
                shebei.setFourFailure(fourFailure);
                shebei.setRemarks(remarks);
                //保存设备基础信息
                this.save(shebei);
                //保存维护内容信息
                String id = shebei.getId();
                equipmentInfo.setEquipmentId(id);
                equipmentInfoService.savePlan(equipmentInfo);
            }
        }
    }
    public void batchSave(Shebei shebei){
        shebeiDao.batchSave(shebei);
    }
}