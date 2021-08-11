package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiCgqdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.haocai.HaocaiCgqd;
import com.demxs.tdm.domain.resource.haocai.HaocaiCgqdmx;
import com.demxs.tdm.comac.common.constant.HaocaiConstans;
import com.demxs.tdm.service.oa.IActAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 耗材采购Service
 * @author zhangdengcai
 * @version 2017-07-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiCgqdService extends CrudService<HaocaiCgqdDao, HaocaiCgqd> {

    @Autowired
    private HaocaiCgqdmxService haocaiCgqdmxService;
    private IActAuditService iActAuditService;

    @Override
    public HaocaiCgqd get(String id) {
        return super.get(id);
    }

    @Override
    public List<HaocaiCgqd> findList(HaocaiCgqd haocaiCgqd) {
        return super.findList(haocaiCgqd);
    }

    /**
     * 分页列表
     * @param page 分页对象
     * @param haocaiCgqd
     * @return
     */
    @Override
    public Page<HaocaiCgqd> findPage(Page<HaocaiCgqd> page, HaocaiCgqd haocaiCgqd) {
        if(UserUtils.getUser()!=null){
            haocaiCgqd.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        }
        return super.findPage(page, haocaiCgqd);
    }

    /**
     * 分页列表
     * @param page 分页对象
     * @param haocaiCgqd
     * @return
     */
    public Page<HaocaiCgqd> findPageForOther(Page<HaocaiCgqd> page, HaocaiCgqd haocaiCgqd) {
        return super.findPage(page, haocaiCgqd);
    }

    /**
     * 保存
     * @param haocaiCgqd
     * @param saveOrCommit 保存或提交
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(HaocaiCgqd haocaiCgqd, String saveOrCommit) {
        String id = haocaiCgqd.getId();
        if(StringUtils.isNotBlank(id)){//修改
            super.save(haocaiCgqd);
        }else {
            haocaiCgqd.setShenqingr(UserUtils.getUser()==null?"":UserUtils.getUser().getName());//申请人名称
            haocaiCgqd.setShenqingrid(UserUtils.getUser()==null?"":UserUtils.getUser().getId());//申请人id
            haocaiCgqd.setShenqingsj(DateUtils.formatDateTime(new Date()));//申请时间
            haocaiCgqd.setCaigoudid(getMaxCaigoudid());//采购单id
            haocaiCgqd.setQingdanzt(HaocaiConstans.haoCaicgdzt.DAITIJ);//采购单状态：待提交
            haocaiCgqd.preInsert();
            id = IdGen.uuid();
            haocaiCgqd.setId(id);
            this.dao.insert(haocaiCgqd);
        }

        List<HaocaiCgqdmx> hcs = haocaiCgqd.getHaocai();
        if(hcs!=null && !hcs.isEmpty()){
            for (HaocaiCgqdmx hc : hcs) {
                hc.setCaigoudzj(haocaiCgqd.getId());
                haocaiCgqdmxService.save(hc);
            }
        }

        //提交流程
        if("commit".equals(saveOrCommit)){
            startAudit(id);
        }
    }

    /**
     * 详情
     * @param haocaiCgqd
     * @return
     */
    @Override
    public HaocaiCgqd get(HaocaiCgqd haocaiCgqd) {
        HaocaiCgqd cgqd = super.get(haocaiCgqd);
        if(cgqd!=null){
            List<HaocaiCgqdmx> cgqdmxes = haocaiCgqdmxService.listByCgqdzj(cgqd.getId());
            cgqd.setHaocai(cgqdmxes);
        }
        return cgqd;
    }

    /**
     * 采购单id
     * @return
     */
    public String getMaxCaigoudid(){
        String caigoudid = "CSP".concat(DateUtils.getDate().replace("-",""));
        HaocaiCgqd haocaiCgqd = new HaocaiCgqd();
        haocaiCgqd.setCaigoudid(caigoudid);
        String maxNum = this.dao.getMaxCaigoudid(haocaiCgqd);
        if (StringUtils.isNotBlank(maxNum)) {
            int no = Integer.parseInt(maxNum.substring(maxNum.length()-3)) + 1;
            DecimalFormat df = new DecimalFormat("000");
            caigoudid += df.format(no);
        } else {
            caigoudid += "001";
        }
        return caigoudid;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(HaocaiCgqd haocaiCgqd) {
        super.delete(haocaiCgqd);
    }

    /**
     * 删除
     * @param ids
     */
    public void deleteMore(String ids){
        if(StringUtils.isNotBlank(ids)){
            String[] idsArr = ids.split(",");
            HaocaiCgqd haocaiCgqd = new HaocaiCgqd();
            haocaiCgqd.setArrIDS(idsArr);
            this.dao.batchDelete(haocaiCgqd);
        }
    }

    /**
     * 发起流程审核
     * @param id
     */
    public void startAudit(String id){
        try{
            logger.debug("开始发起方法验证流程ID：{}"+id);
            iActAuditService = getiActAuditService();
//            iActAuditService.start(id, GlobalConstans.ActProcDefKey.HAOCAICGSH);

            HaocaiCgqd haocaiCgqd = new HaocaiCgqd();
            haocaiCgqd.setId(id);
            HaocaiCgqd cgqd = get(haocaiCgqd);
            if(cgqd!=null){
                cgqd.setQingdanzt(HaocaiConstans.haoCaicgdzt.DAISHENH);//带提交
                this.dao.update(cgqd);
            }
        }catch (Exception e){
            logger.error("发起方法验证流程失败！",e);
        }
    }

    /**
     * 审核结束，改变状态
     * @param id
     * @param isPass 是否通过
     */
    public void changeCgzt(String id, boolean isPass){
        HaocaiCgqd haocaiCgqd = new HaocaiCgqd();
        haocaiCgqd.setId(id);
        HaocaiCgqd cgqd = get(haocaiCgqd);
        if(cgqd!=null){
            cgqd.setQingdanzt(HaocaiConstans.haoCaicgdzt.YISHENH);//已审核
            cgqd.setShenher(UserUtils.getUser()==null?"":UserUtils.getUser().getName());
            cgqd.setShenherid(UserUtils.getUser()==null?"":UserUtils.getUser().getId());
            if (isPass) {
                cgqd.setShenhejg(HaocaiConstans.haoCaishjg.TONGGUO);//通过
            } else {
                cgqd.setShenhejg(HaocaiConstans.haoCaishjg.BUTONGG);//不通过
            }
            this.dao.update(cgqd);
        }
    }

    /**
     * 获取IActAuditService
     * @return
     */
    public IActAuditService getiActAuditService() {
        if(iActAuditService == null){
            iActAuditService = SpringContextHolder.getBean(IActAuditService.class);
        }
        return iActAuditService;
    }
}