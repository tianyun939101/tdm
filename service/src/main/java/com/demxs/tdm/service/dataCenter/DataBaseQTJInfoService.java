package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.dao.dataCenter.DataBaseInfoDao;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck;
import com.demxs.tdm.domain.dataCenter.*;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.business.nostandard.NoStandardTestCheckService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class DataBaseQTJInfoService extends CrudService<DataBaseInfoDao, DataBaseInfo> {

    @Autowired
    private DataBasePermService dataBasePermService;
    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private DataTestInfoService dataTestInfoService;
    @Autowired
    private ATAChapterService ataChapterService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AttachmentInfoService attachmentInfoService;
    @Autowired
    private AssetInfoService assetInfoService;
    @Autowired
    private FileStore fileStore;
    @Autowired
    private TestInfoAttrValueService testInfoAttrValueService;
    @Autowired
    private OfficeService officeService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuditInfoDao auditInfoDao;
    @Autowired
    private NoStandardTestCheckService testCheckService;

    @Autowired
    private  DataBaseInfoDao  dataBaseInfoDao;

    private static String DATA_REPORT_TMP_PATH = Global.getCreateFilePath()+"/dataReport/";

    @Transactional(readOnly = true)
    public DataBaseInfo get(String baseId){
        DataBaseInfo dataBaseInfo = super.dao.get(baseId);
        TestInfoAttrValue testInfoAttrValue = new TestInfoAttrValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List <DataTestInfo>  list=dataTestInfoService.getByBaseId(baseId);
        dataBaseInfo.setTestInfoList(list);
        String startDate="";
        String  endDate="";
        String  timeRange="";
        //封装试验数据信息
        if(!CollectionUtils.isEmpty(list)){
            for(DataTestInfo dataTestInfo:dataBaseInfo.getTestInfoList()){
                List<String> orList = new ArrayList<>();
                List<String> tdList = new ArrayList<>();
                List<String> tlList = new ArrayList<>();
                List<String> vdList = new ArrayList<>();
                List<String> adList = new ArrayList<>();
                List<String> imList = new ArrayList<>();
                testInfoAttrValue.setTestInfoId(dataTestInfo.getId());
                if(null !=dataTestInfo.getStartDate()){
                    startDate=dateFormat.format(dataTestInfo.getStartDate());
                }
                if(null !=dataTestInfo.getEndDate()){
                    endDate=dateFormat.format(dataTestInfo.getEndDate());
                }
                if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
                    timeRange=startDate+"-"+endDate;
                    dataTestInfo.setTimeRange(timeRange);
                }
                List<TestInfoAttrValue> fields = testInfoAttrValueService.findList(testInfoAttrValue);
                dataTestInfo.setFields(fields);
                for(DataInfo dataInfo:dataTestInfo.getDatalist()){
                    switch (dataInfo.getDataType()==null?"0":dataInfo.getDataType()){
                        case DataCenterConstants.DataType.ORIGINAL_RECORD://原始记录单
                            orList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.TEST_DATA://试验数据
                            tdList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.TEST_LOG://试验日志
                            tlList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.VIDEO://视频数据
                            vdList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.AUDIO://音频数据
                            adList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.IMAGE://图片数据
                            imList.add(dataInfo.getTestData());
                            break;
                        default:
                            break;
                    }
                }
                dataTestInfo.setOriginalRecord(StringUtils.join(orList,","));
                dataTestInfo.setTestData(StringUtils.join(tdList,","));
                dataTestInfo.setTestLog(StringUtils.join(tlList,","));
                dataTestInfo.setVideoData(StringUtils.join(vdList,","));
                dataTestInfo.setAudioData(StringUtils.join(adList,","));
                dataTestInfo.setImgData(StringUtils.join(imList,","));
            }
        }
        //获取ATA章节信息
        String ataChapter = dataBaseInfo.getAtaChapter();
        if(StringUtils.isNotBlank(ataChapter)){
            List<ATAChapter> ataList = new ArrayList<>();
            String[] ataArr = ataChapter.split(",");
            for(String ataId:ataArr){
                ATAChapter ata = ataChapterService.get(ataId);
                if(ata!=null){
                    ataList.add(ata);
                }
            }
            dataBaseInfo.setAtaList(ataList);
        }
        return dataBaseInfo;
    }

    private static String getCode(){
        Date date = new Date();
        String format = new SimpleDateFormat("yyyyMMdd").format(date);
        String time = date.getTime()+"";
        String suffix = time.substring(time.length() - 2);
        String e1 = time.substring(time.length()-4,time.length()-3);
        String e2 = time.substring(time.length()-3,time.length()-2);
        String result = Integer.parseInt(e1) * Integer.parseInt(e2)+"";

        return format+result+suffix;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(DataBaseInfo dataBaseInfo){
        Boolean isNewRecord = false;
        if(StringUtils.isBlank(dataBaseInfo.getId())){
            isNewRecord = true;
        }
        //保存数据中心基础信息
        dataBaseInfo.setEntrustType(DataCenterConstants.EntrustType.UPLOAD);//数据中心提报1
        dataBaseInfo.setEntrustCode(getCode());//生成单号
        dataBaseInfo.setReportCode(dataBaseInfo.getEntrustCode());
        dataBaseInfo.setTestLeader(UserUtils.getUser().getId());//检测负责人默认当前登录人
        dataBaseInfo.setApplicant(UserUtils.getUser().getId());//申请人默认当前登录人
        dataBaseInfo.setApplyDate(new Date());//申请时间当前时间
        dataBaseInfo.setInsertUser(UserUtils.getUser().getName());
        if(dataBaseInfo.getAuditUser() != null){
            dataBaseInfo.setAuditUserId(dataBaseInfo.getAuditUser().getId());
        }
        super.save(dataBaseInfo);
        //保存试验项目信息
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        if(dataBaseInfo.getTestInfoList() != null){
            for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
                test.setBaseId(dataBaseInfo.getId());
                dataInfoService.deleteByTestId(test.getId());
                test.setId("");
                //保存试验项目数据信息
                dataTestInfoService.save(test);
                saveTestData(test);
                //保存试验项目扩展属性
                testInfoAttrValueService.saveAttrValues(test);
            }
        }

        //判断是否为试验前检查提报
        NoStandardTestCheck testCheck = testCheckService.getTestCheckByReportId(dataBaseInfo.getId());
        if(testCheck != null && "1".equals(dataBaseInfo.getIsFlag())){//试验前检查流程
            String auditId = dataBaseInfo.getAuditUser().getId();
            ApproveDTO approveDto = new ApproveDTO(testCheck.getId(),"",auditId);
            testCheckService.approve(approveDto);
        }else if("1".equals(dataBaseInfo.getIsFlag())){//数据提报流程
            dataBaseInfo.setInsertUser(UserUtils.getUser().getName());
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(dataBaseInfo.getId()).active().singleResult();
            User createBy = UserUtils.getUser();
            User auditUser = userDao.get(dataBaseInfo.getAuditUser());
            if(null == task){
                User  ss=userDao.get(dataBaseInfo.getUpdateBy());
                Map<String, Object> map = new HashMap<String, Object>();
                List<String> auditList = new ArrayList<>();

                if(null !=auditUser){
                    Map<String,Object> model = new HashMap<>(1);
                    model.put("userName",createBy.getName());
                    String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);
                    Map<String,Object> vars = new HashMap<>(1);
                    vars.put("message", title);
                    actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.DATA_QTG_PROCESS,
                            auditUser.getLoginName(), dataBaseInfo.getId(), title, vars);
                }
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setBusinessKey(dataBaseInfo.getId());
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setResult(1);
                // auditInfo.setReason("提交申请单");
                auditInfo.setType(Integer.parseInt(DataConstant.dataConstantStatus.APPLY));
                auditInfoService.save(auditInfo);
                String  status="2";
                this.dao.changeStatus(dataBaseInfo.getId(),status);
                this.dao.changeInsertUser(dataBaseInfo.getId(),UserUtils.getUser().getName());
                //  this.dao.changeAuditUser(dataBaseInfo);
            }else{
                //审批
                Map<String,Object> model = new HashMap<>(1);
                model.put("userName",createBy);
                String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);

                Map<String,Object> vars = new HashMap<>(1);
                vars.put("message", title);
                actTaskService.apiComplete(dataBaseInfo.getId(),"","", auditUser.getLoginName(),vars);
            }

        }

        //新增信息保存默认权限当前用户
        //试验前检查流程 已授权不需要重复授权
        if(isNewRecord && testCheck == null){
            this.addDataBasePerm(dataBaseInfo.getId(),UserUtils.getUser());
        }
    }
    //
    public void addDataBasePerm(String id,User user){
        DataBasePerm dataBasePerm = new DataBasePerm();
        dataBasePerm.setBaseId(id);
        dataBasePerm.setAuthorizationId(UserUtils.getUser().getId());
        dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.REPORT);//权限类型 1查看权限 2提报页操作权限
        dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//授权类型 1人员 2 部门
        dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);//授权范围 1全部 2时间段
        dataBasePerm.setAuthorizer(user.getId());//授权人取当前用户
        dataBasePermService.save(dataBasePerm);
    }

    /**
     * 保存试验数据
     */
    private void saveTestData(DataTestInfo test) {
        //原始记录单
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.ORIGINAL_RECORD,test.getOriginalRecord());
        //试验数据（处理后）
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.TEST_DATA,test.getTestData());
        //试验日志
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.TEST_LOG,test.getTestLog());
        //视频数据
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.VIDEO,test.getVideoData());
        //音频数据
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.AUDIO,test.getAudioData());
        //图片数据
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.IMAGE,test.getImgData());
    }


    private void saveTestDataByType(String testId,String dataType, String tsetData){
        if(StringUtils.isBlank(tsetData)){
            return;
        }
        String[] arr = tsetData.split(",");
        for(String fileId:arr){
            DataInfo data = new DataInfo();
            data.setTestInfoId(testId);
            data.setDataType(dataType);
            data.setTestData(fileId);
            dataInfoService.save(data);
        }
    }
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(DataBaseInfo dataBaseInfo){
        DataBaseInfo baseInfo = super.dao.get(dataBaseInfo.getId());
        //删除试验项目数据信息
        for(DataTestInfo test :baseInfo.getTestInfoList()){
            DataTestInfo dataTestInfo = new DataTestInfo();
            dataTestInfo.setId(test.getId());
            dataTestInfoService.delete(dataTestInfo);
            dataInfoService.deleteByTestId(test.getId());
        }
        //删除试验项目信息
        dataTestInfoService.deleteByBaseId(baseInfo.getId());
        //删除权限信息
        dataBasePermService.detetePermByBaseId(baseInfo.getId());
        //删除审批信息
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setBusinessKey(dataBaseInfo.getId());
        auditInfoService.delete(auditInfo);
        //删除数据中心基础信息
        super.delete(baseInfo);
    }

    /**
     * 数据权限操作
     * @param dataBaseInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveAuthority(DataBaseInfo dataBaseInfo) {
        //保存前删除原有权限
        dataBasePermService.detetePermByBaseId(dataBaseInfo.getId());
        String reportUserStr = dataBaseInfo.getReportUserStr();
        String reportOfficeStr = dataBaseInfo.getReportOfficeStr();
        String searchUserStr = dataBaseInfo.getSearchUserStr();
        String searchOfficeStr = dataBaseInfo.getSearchOfficeStr();
        Date date = dataBaseInfo.getSearchEndDate();
        saveAuthorityByStr(dataBaseInfo.getId(),reportUserStr,DataCenterConstants.PermissionType.REPORT,DataCenterConstants.AuthorizationType.PERSON,null);
        saveAuthorityByStr(dataBaseInfo.getId(),reportOfficeStr,DataCenterConstants.PermissionType.REPORT,DataCenterConstants.AuthorizationType.ORG,null);
        saveAuthorityByStr(dataBaseInfo.getId(),searchUserStr,DataCenterConstants.PermissionType.SEARCH,DataCenterConstants.AuthorizationType.PERSON,date);
        saveAuthorityByStr(dataBaseInfo.getId(),searchOfficeStr,DataCenterConstants.PermissionType.SEARCH,DataCenterConstants.AuthorizationType.ORG,date);
    }

    private void saveAuthorityByStr(String baseId,String str,String permType, String authorizationType,Date date){
        if(StringUtils.isBlank(str)){
            return;
        }
        String[] arr = str.split(",");
        for(String authorizationId:arr){
            DataBasePerm dataBasePerm = new DataBasePerm();
            dataBasePerm.setBaseId(baseId);
            dataBasePerm.setPermissionType(permType);
            dataBasePerm.setAuthorizationId(authorizationId);
            dataBasePerm.setAuthorizationType(authorizationType);
            dataBasePerm.setEndDate(date);
            dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//授权人取当前用户
            dataBasePermService.save(dataBasePerm);
        }
    }
    /**
     * 根据基础信息ID获取数据权限信息
     * @param dataBaseInfo
     * @return
     */
    @Transactional(readOnly = true)
    public DataBaseInfo getAuthorityByBaseId(DataBaseInfo dataBaseInfo) {
        List<User> reportUser = new ArrayList<>();//提报权限用户集合
        List<Office> reportOffice = new ArrayList<>();//提报权限部部门集合
        List<User> searchUser = new ArrayList<>();//查看权限用户集合
        List<Office> searchOffice = new ArrayList<>();//查看权限部部门集合
        Date searchEndDate = null;//查看权限截止日期
        //获取用户权限数据
        List<DataBasePerm> userPermList = dataBasePermService.getUserPermByBaseId(dataBaseInfo.getId());
        //设计变更 查看权限变更为列表显示
        for(DataBasePerm data:userPermList){
            if("1".equals(data.getPermissionType())){//查看权限
                //searchUser.add(data.getUser());
                //searchEndDate = data.getEndDate();//部门和人员权限有效期一致
            }else if("2".equals(data.getPermissionType())){//提报权限
                reportUser.add(data.getUser());
            }
        }
        //获取部门权限数据
        List<DataBasePerm> officePermList = dataBasePermService.getOfficePermByBaseId(dataBaseInfo.getId());
        for(DataBasePerm data:officePermList){
            if("1".equals(data.getPermissionType())){//查看权限
                //searchOffice.add(data.getOffice());
            }else if("2".equals(data.getPermissionType())){//提报权限
                reportOffice.add(data.getOffice());
            }
        }
        dataBaseInfo.setReportUser(reportUser);
        dataBaseInfo.setReportOffice(reportOffice);
        dataBaseInfo.setSearchUser(searchUser);
        dataBaseInfo.setSearchOffice(searchOffice);
        dataBaseInfo.setSearchEndDate(searchEndDate);
        //获取授权列表集合
        DataBasePerm bp = new DataBasePerm();
        bp.setBaseId(dataBaseInfo.getId());
        bp.setPermissionType("1");
        List<DataBasePerm> perms = dataBasePermService.findList(bp);
        for(DataBasePerm perm:perms){
            if("1".equals(perm.getAuthorizationType())){//人
                perm.setUser(UserUtils.get(perm.getAuthorizer()));
            }else{
                perm.setOffice(officeService.get(perm.getAuthorizer()));
            }
        }
        dataBaseInfo.setPermList(perms);
        return dataBaseInfo;
    }

    /**
     *
     * @param dataBaseInfo
     */
    public void saveFromOther(DataBaseInfo dataBaseInfo) {
        super.save(dataBaseInfo);
        //保存试验项目信息
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
            test.setBaseId(dataBaseInfo.getId());
            dataTestInfoService.save(test);
            //保存试验项目数据信息
            dataInfoService.deleteByTestId(test.getId());
            saveTestData(test);
        }
        //自动保存权限信息
        dataBasePermService.save(setDataBasePerm(dataBaseInfo.getId(),dataBaseInfo.getApplicant())); //申请人
        if(!dataBaseInfo.getApplicant().equals(dataBaseInfo.getTestLeader())){
            dataBasePermService.save(setDataBasePerm(dataBaseInfo.getId(),dataBaseInfo.getTestLeader()));//试验负责人
        }
//        for(DataTestInfo test:dataBaseInfo.getTestInfoList()){
            DataTestInfo test = dataBaseInfo.getTestInfoList().get(0);
            if(StringUtils.isNotBlank(test.getTester())){
                String[] split = test.getTester().split(",");
                for(String s : split){
                    if(StringUtils.isNotBlank(s)){
                        if(!s.equals(dataBaseInfo.getApplicant()) || !s.equals(dataBaseInfo.getTestLeader())) {
                            dataBasePermService.save(setDataBasePerm(dataBaseInfo.getId(), s));//试验人
                        }
                    }
                }

//            }
        }

    }

    private DataBasePerm setDataBasePerm(String baseId, String personId) {
        DataBasePerm dataBasePerm = new DataBasePerm();
        dataBasePerm.setBaseId(baseId);
        dataBasePerm.setAuthorizationId(personId);
        dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.SEARCH);//权限类型 1查看权限 2提报页操作权限
        dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//授权类型 1人员 2 部门
        dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);// 1全部2时间段
        dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//授权人取当前用户
        return dataBasePerm;
    }

    public List<DataBaseInfo> findAllList(DataBaseInfo dataBaseInfo){
        return super.dao.findAllList(dataBaseInfo);
    }





    /**
     * 查询服务商数据
     * @param page 分页对象
     * @param entity
     * @return
     */
    public Page<DataBaseInfo> findProviderPage(Page<DataBaseInfo> page, DataBaseInfo entity) {
        entity.setPage(page);
        page.setList(super.dao.findProviderList(entity));
        return page;
    }

    public List<DataBaseInfo> findCheckList(DataBaseInfo dataBaseInfo){
        return dataBaseInfoDao.findCheckList(dataBaseInfo);
    }


    /**
     * 服务商修改
     * @param dataBaseInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveByProvider(DataBaseInfo dataBaseInfo){
        //保存数据中心基础信息
        dataBaseInfo.setEntrustType(DataCenterConstants.EntrustType.PROVIDER);//服务商提报
        this.dao.updateByProvider(dataBaseInfo);//更新人取服务商ID
        //保存试验项目信息
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
            test.setBaseId(dataBaseInfo.getId());
            dataTestInfoService.save(test);
            //保存试验项目数据信息
            dataInfoService.deleteByTestId(test.getId());
            saveTestData(test);
        }

    }

    public Page<DataBaseInfo> findBasePage(Page<DataBaseInfo> dataBaseInfoPage, DataBaseInfo dataBaseInfo) {
        dataBaseInfo.setPage(dataBaseInfoPage);
        dataBaseInfoPage.setList(dao.findBaseList(dataBaseInfo));
        return dataBaseInfoPage;
    }

    /**
     * 发起审批流程
     * @param dataBaseInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void applyPermission(DataBaseInfo dataBaseInfo,String reason) {
        //设置审核信息
        User apply = UserUtils.getUser();
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setBusinessKey(dataBaseInfo.getId()+","+apply.getId());
        auditInfo.setReason(reason);
        auditInfo.setResult(1);
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfoService.save(auditInfo);
        //发起流程
        User audit = labInfoService.get(dataBaseInfo.getLabId()).getLeader();
        String taskTile = dataBaseInfo.getCreateBy().getName() +"申请查看申请单号："+dataBaseInfo.getEntrustCode()+"数据信息";;
        Map<String,Object> model = new HashMap<>();
        model.put("userName",dataBaseInfo.getCreateBy().getName());
        model.put("code",dataBaseInfo.getTestName()==null?"":dataBaseInfo.getTestName());
        String title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate.DATA_REPORT_APPLY,model);

        Map<String,Object> vars = new HashMap<>();
        vars.put("message", title);
        vars.put("code", dataBaseInfo.getEntrustCode()==null?"":dataBaseInfo.getEntrustCode());
        actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.DATA_REPORT,
                audit.getLoginName(),dataBaseInfo.getId()+","+apply.getId(),taskTile,vars);

    }

    /**
     * 审批
     * @param
     * @param auditInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void audit(String id, AuditInfo auditInfo) {
        String[] arr = id.split(",");
        String baseId = arr[0];
        String userId = arr[1];
        DataBaseInfo dataBaseInfo = super.dao.get(baseId);
        Map<String, Object> model = new HashMap<>();
        model.put("code", dataBaseInfo.getEntrustCode()==null?"":dataBaseInfo.getEntrustCode());
        User createUser = systemService.getUser(dataBaseInfo.getCreateById());
        model.put("userName", createUser.getName());
        String title = "";
        if (dataBaseInfo != null) {
            //审核通过
            if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason()) ? "同意" : auditInfo.getReason());
                title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate.AUDIT_PASS, model);
                //添加数据权限
                DataBasePerm dataBasePerm = new DataBasePerm();
                dataBasePerm.setBaseId(baseId);
                dataBasePerm.setAuthorizationId(userId);
                dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.SEARCH);//权限类型 1查看权限 2提报页操作权限
                dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//授权类型 1人员 2 部门
                dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);// 1全部2时间段
                dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//授权人取当前用户
                dataBasePermService.save(dataBasePerm);
            }
            //审核不通过
            if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
                title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate.AUDIT_RETURN, model);
            }
            //设置审核信息
            auditInfo.setBusinessKey(id);
            //auditInfo.setType(EntrustConstants.AuditType.ENTRUST_AUDIT);
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfoService.save(auditInfo);
            //审批
            Map<String, Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(id,auditInfo.getReason(),auditInfo.getResult()+"",null,null);
        }
    }

    public List<DataBaseInfo> findByLabId(DataBaseInfo dataBaseInfo){
        return this.dao.findByLabId(dataBaseInfo);
    }


    /**数据提报下载
     * @Describe:
     * @Author:WuHui
     * @Date:16:44 2020/9/18
     * @param dataBaseInfos
     * @return:java.lang.String
    */
    public String buildDataReport(List<DataBaseInfo> dataBaseInfos){
        //创建临时目录
        String basePath = System.getProperty("java.io.tmpdir")+ File.separator + System.currentTimeMillis() + "/";
        for(DataBaseInfo dataBaseInfo: dataBaseInfos){
            String taskPath = basePath + dataBaseInfo.getTaskNo() ;
            //生成任务书目录
            File baseFile = new File(taskPath);
            if(!baseFile.exists()) {
                baseFile.mkdirs(); //创建目录
            }
            //构建xml对象
            DataReportXml dataInfo = new DataReportXml(dataBaseInfo);
            //试验大纲
            if(!StringUtils.isBlank(dataBaseInfo.getTestProgram())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTestProgram(),taskPath,null);
                if(data != null)  dataInfo.setOutline(data);
            }
            //构型文件
            if(!StringUtils.isBlank(dataBaseInfo.getConfig())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getConfig(),taskPath,null);
                if(data != null) dataInfo.setConfig(data);
            }
            //任务书信息
            if(!StringUtils.isBlank(dataBaseInfo.getTaskNum())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTaskNum(),taskPath,null);
                if(data != null) dataInfo.setTaskNum(data);
            }
            //试验报告信息
            if(!StringUtils.isBlank(dataBaseInfo.getTestReport())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTestReport(),taskPath,null);
                if(data != null) dataInfo.setTestReport(data);
            }
            //计划书信息
            if(!StringUtils.isBlank(dataBaseInfo.getPlanFile())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getPlanFile(),taskPath,null);
                if(data != null) dataInfo.setPlanFile(data);
            }
            //构建任务
            List<DataReportXml.Task> tasks = new ArrayList<>();
            for(DataTestInfo info: dataBaseInfo.getTestInfoList()){
                String dataPath = taskPath + "/" + info.getId();
                List<DataReportXml.Data> datas = new ArrayList<>();
                for(DataInfo di:info.getDatalist()){
                    DataReportXml.Data data = this.buildReportData(di.getTestData(),dataPath,di.getDataType());
                    if(data != null) datas.add(data);
                }
                DataReportXml.Task task = new DataReportXml.Task(info,datas);
                tasks.add(task);
            }
            dataInfo.setTask(tasks);
            //生成xml文件
            taskPath = basePath + dataBaseInfo.getTaskNo() + "/" + dataBaseInfo.getTaskNo() +".xml";
            JaxbUtil.createXml(dataInfo,taskPath,"utf-8");
        }

        //文件压缩
      //  String zipPath = "/Users/zhengweiming/Downloads/"+System.currentTimeMillis()+".zip";
        String zipPath = System.getProperty("java.io.tmpdir")+ File.separator  + System.currentTimeMillis()+".zip";
        basePath = basePath.substring(0,basePath.length()-1);
        FileUtils.zipFiles(basePath,"",zipPath);
        //删除临时文件
        FileUtils.deleteDirectory(basePath);
        //返回压缩文件路径
        return zipPath;
    }

    /**
     * @Describe:数据提报 资源文件
     * @Author:WuHui
     * @Date:14:58 2020/9/18
     * @param attachId
     * @param type
     * @return:com.demxs.tdm.domain.dataCenter.DataReportXml.Data
    */
    private DataReportXml.Data buildReportData(String attachId,String path,String type){
        AttachmentInfo attach = attachmentInfoService.get(attachId);
        DataReportXml.Data data = null;
        //数据对象构建
        if(attach!=null){
            String target = path + "/" + attach.getName();
            String name = attach.getName();
            data =  new DataReportXml.Data(name,name.substring(0,name.lastIndexOf(".")));
            if(!StringUtils.isBlank(type)){
                data.setType(type);
            }
            String attachPath = Global.getCreateFilePath() + attach.getPath();
            InputStream is =  fileStore.downloadFileToStream(attachPath);
            //数据复制
            try {
                File file = new File(target);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                IOUtil.writeByteToFile(is,target);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * @Describe:数据提报上传
     * @Author:WuHui
     * @Date:18:54 2020/9/21
     * @param ziPath
     * @return:void
    */
    public void uploadDataReport(String ziPath){
        List<DataBaseInfo> dataBaseInfos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        //创建临时目录
        String basePath = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + "/";
        //将zip解压到临时目录
        FileUtils.unZipFiles(ziPath,basePath);
        File folder = new File(basePath);
        //读取提报信息
        File[] list = folder.listFiles();
        for (File infoFile : list) {
            String infoPath = "";
            if (infoFile.isDirectory()) {
                infoPath = basePath + infoFile.getName() + "/";
                //读取xml文件
                String filePath = infoPath + infoFile.getName() +".xml";
                DataReportXml dataInfo = JaxbUtil.readXml(filePath,DataReportXml.class);

                if(null != dataInfo){
                    DataBaseInfo dataBaseInfo = new DataBaseInfo(dataInfo);
                    dataBaseInfo.setLabInfo(labInfoService.get(dataBaseInfo.getLabId()));
                    //资源copy
                    //试验大纲
                    DataReportXml.Data data = dataInfo.getOutline();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTestProgram(attach.getId());
                    }
                    //构型文件
                    data = dataInfo.getConfig();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setConfig(attach.getId());
                    }
                    //任务文件
                    data = dataInfo.getTaskNum();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTaskNum(attach.getId());
                    }
                    //试验报告文件
                    data = dataInfo.getTestReport();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTestReport(attach.getId());
                    }
                    //计划书文件
                    data = dataInfo.getPlanFile();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setPlanFile(attach.getId());
                    }
                    //读取任务
                    String dataFile = "";
                    List<DataTestInfo> tests = new ArrayList<>();
                    for(DataReportXml.Task task : dataInfo.getTask()){
                        dataFile = infoPath + task.getId();
                        DataTestInfo test = new DataTestInfo(task);
                        test.setCurrentUser(UserUtils.getUser());
                        test.setCreateDate(now);
                        test.setUpdateBy(UserUtils.getUser());
                        test.setUpdateDate(now);
                        if(StringUtils.isNotEmpty(test.getTimeRange())){
                            int  s=  getIndex(test.getTimeRange(),"-",3);
                            String start=test.getTimeRange().substring(0,s).trim();
                            String end=test.getTimeRange().substring(s+1,test.getTimeRange().length()).trim();
                            try{
                                test.setStartDate(sdf.parse(start));
                                test.setEndDate(sdf.parse(end));
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        if(CollectionUtils.isNotEmpty(task.getData())){
                            for(DataReportXml.Data dat :task.getData()){
                                filePath = dataFile + "/"+ dat.getPath();
                                AttachmentInfo attach = this.saveDataReportAttach(filePath,dat.getPath());
                                // dataBaseInfo.setTestProgram(attach.getId());
                                switch (dat.getType()){
                                    case DataCenterConstants.DataType.ORIGINAL_RECORD:
                                        test.setOriginalRecord(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.TEST_DATA:
                                        test.setTestData(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.TEST_LOG:
                                        test.setTestLog(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.VIDEO:
                                        test.setVideoData(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.AUDIO:
                                        test.setAudioData(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.IMAGE:
                                        test.setImgData(attach.getId());
                                        break;
                                }
                            }
                        }
                        tests.add(test);
                    }
                    dataBaseInfo.setTestInfoList(tests);
                    dataBaseInfo.setCurrentUser(UserUtils.getUser());
                    dataBaseInfo.setCreateDate(now);
                    dataBaseInfo.setUpdateBy(UserUtils.getUser());
                    dataBaseInfo.setUpdateDate(now);
                    //持久化提报信息
                    this.save(dataBaseInfo);
                }
            }
        }
        //删除临时文件夹
        //FileUtils.deleteDirectory(basePath);
        //删除压缩文件
        FileUtils.deleteFile(ziPath);
    }


    private AttachmentInfo saveDataReportAttach(String path,String fileName){
        File file = new File(path);
        //将文件服务器存储值ftp服务器
        path =  DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+"/"+fileName;
        fileStore.saveFile(file);
        //存储附件信息
        AttachmentInfo attachment = assetInfoService.save(fileName, path, file.length());
        return attachment;
    }

    /**
     * @Describe:查询实验室 试验信息 树形结构
     * @Author:WuHui
     * @Date:16:42 2020/9/23
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String,Object>> findLabBaseInfoTree(DataBaseInfo dataBaseInfo){
        return dao.findLabBaseInfoTree(dataBaseInfo);
    }

    /**
     * @Describe: ATA 试验信息 树形结构
     * @Author:WuHui
     * @Date:14:29 2020/9/24
     * @param dataBaseInfo
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String,Object>> findATADatatree(DataBaseInfo dataBaseInfo){
        return dao.findATABaseInfoTree(dataBaseInfo);
    }

    /**
     * @Describe:是否具有数据查看权限
     * @Author:WuHui
     * @Date:14:35 2020/9/24
     * @param dataBaseInfo
     * @return:boolean
    */
    public boolean isSearchData(DataBaseInfo dataBaseInfo){
        boolean enable = false;
        User user = UserUtils.getUser();
        List<DataBasePerm> perms = dataBasePermService.findPermByBaseId(dataBaseInfo.getId(),user);
        for(DataBasePerm perm : perms){
            if(DataCenterConstants.PermissionType.SEARCH.equals(perm.getPermissionType())){
                //查看权限判断是否处于时间期间内
                if(perm.getAuthorizationScope() != null
                        && DataCenterConstants.AuthorizationScope.PERIOD.equals(perm.getAuthorizationScope())){
                    Date now = new Date();
                    enable = now.before(perm.getEndDate());
                }else{
                    enable = true;
                }
            }else if(DataCenterConstants.PermissionType.REPORT.equals(perm.getPermissionType())){
                //操作权限直接放行
                enable = true;
                break;
            }
        }
        return enable;
    }

    /**
     * @Describe:是否具备操作权限
     * @Author:WuHui
     * @Date:14:35 2020/9/24
     * @param dataBaseInfo
     * @return:boolean
    */
    public boolean isOperateData(DataBaseInfo dataBaseInfo){
        boolean enable = false;
        User user = UserUtils.getUser();
        List<DataBasePerm> perms = dataBasePermService.findPermByBaseId(dataBaseInfo.getId(),user);
        for(DataBasePerm perm : perms){
            if(DataCenterConstants.PermissionType.REPORT.equals(perm.getPermissionType())){
                //操作权限直接放行
                enable = true;
                break;
            }
        }
        return enable;
    }


    /**
     * @Describe:新增数据操作权限
     * @Author:WuHui
     * @Date:9:31 2020/9/27
     * @param user
     * @return:boolean
    */
    public void addOperateData(User user,String baseId){
        List<DataBasePerm> perms = dataBasePermService.findPermByBaseId(baseId,user);
        for(DataBasePerm perm:perms){
            if(DataCenterConstants.PermissionType.REPORT.equals(perm.getPermissionType())){
                return;
            }
        }
        DataBasePerm perm = new DataBasePerm();
        perm.setBaseId(baseId);
        perm.setPermissionType(DataCenterConstants.PermissionType.REPORT);
        perm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);
        perm.setAuthorizationId(user.getId());
        perm.setCreateBy(UserUtils.getUser());
        perm.setCreateDate(new Date());
        perm.setUpdateBy(UserUtils.getUser());
        perm.setUpdateDate(new Date());
        perm.setAuthorizer(UserUtils.getUser().getId());
        dataBasePermService.save(perm);
    }

    /**
     * @Describe:根据类型获取提报数据
     * @Author:WuHui
     * @Date:10:36 2020/11/4
     * @param company
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.DataBaseInfo>
    */
    public List<DataBaseInfo> findAllByCompany(String company) {
        return this.dao.findAllByCompany(company);
    }

    public  int getIndex(String str, String s,int j){
        int  x=str.indexOf(str);
        for(int i=0;i<j;i++){
            x=str.indexOf(s,x+1);
        }
        return x;
    }


    /**
     * @author Jason
     * @date 2020/9/27 9:48
     * @params [request]
     * 驳回始终返回至创建者
     * @return void
     */
    public void reject(DataBaseInfo request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        request = this.dao.get(request);
        DataBaseInfo assreq = this.dao.get(request.getId());
        //User initiator = UserUtils.get(assreq.getCreateBy());
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",assreq.getCreateBy().getName());
        String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.AUDIT_RETURN,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                assreq.getCreateBy().getLoginName(),vars);
        request.preUpdate();
        String  status=DataConstant.dataConstantStatus.REJECT;
        this.dao.changeStatus(request.getId(),status);
        request.setStatus(status);
        request.setAuditUser(UserUtils.getUser());
        if(null ==request.getAuditUser()){
            request.setAuditUserId(assreq.getCreateBy().getId());
        }else{
            request.setAuditUserId(assreq.getCreateBy().getId());
        }

        this.dao.changeAuditUser(request);
    }

    /**
     * @author Jason
     * @date 2020/9/27 9:47
     * @params [request]
     * 用户审核通过，下一节点数据管理员
     * @return void
     */
    public void labPass(DataBaseInfo dataBaseInfo) {
        AuditInfo auditInfo = dataBaseInfo.getAuditInfo();
        User  ss=UserUtils.getUser();
        auditInfo.setAuditUser(UserUtils.getUser());
       // auditInfo.setRemarks(TestCategoryAssessRequestEnum.LAB_AUDIT.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        DataBaseInfo assreq = this.dao.get(dataBaseInfo.getId());
       // User auditUser = UserUtils.get(assreq.getTecDir());
       // User createBy = UserUtils.getUser();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",UserUtils.getUser().getName());
        String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(dataBaseInfo.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                UserUtils.getUser().getLoginName(),vars);
        dataBaseInfo.preUpdate();
       // User updateBy = UserUtils.get(assreq.getTecDir());
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.COMPANY_AUDIT.getCode())
        dataBaseInfo.setAuditUser(ss);
       // request.setAuditUserId(assreq.getTecDir());
        dataBaseInfo.setStatus(DataConstant.dataConstantStatus.RECORD);
        dataBaseInfo.setAuditUserId(dataBaseInfo.getAuditUser().getId());
        this.dao.changeAuditUser(dataBaseInfo);
    }
    /**
     * @author Jason
     * @date 2020/9/27 9:48
     * @params [request]
     * 数据管理审批通过，流程结束
     * @return void
     */
    public void centerApproval(DataBaseInfo dataBaseInfo) {
        AuditInfo auditInfo = dataBaseInfo.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
       // auditInfo.setRemarks(TestCategoryAssessRequestEnum.CENTER_APPROVAL.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        actTaskService.apiComplete(dataBaseInfo.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,null);
        dataBaseInfo.preUpdate();
        dataBaseInfo.setStatus(DataConstant.dataConstantStatus.FINISH);

        this.dao.changeStatus(dataBaseInfo.getId(),dataBaseInfo.getStatus());
        this.dao.changeAuditUser(dataBaseInfo);

    }

}
