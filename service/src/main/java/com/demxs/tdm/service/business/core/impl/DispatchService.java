package com.demxs.tdm.service.business.core.impl;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.service.business.*;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.service.business.*;
import com.demxs.tdm.service.business.core.IDispatchService;
import com.demxs.tdm.service.business.core.ITaskService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调度业务服务实现
 * User: wuliepeng
 * Date: 2017-11-09
 * Time: 上午11:09
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DispatchService implements IDispatchService {
    private static final Logger log = LoggerFactory.getLogger(DispatchService.class);

    @Autowired
    private EntrustInfoService entrustInfoService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private EntrustTestGroupService entrustTestGroupService;
    @Autowired
    private EntrustSampleGroupService entrustSampleGroupService;
    @Autowired
    private EntrustSampleGroupItemService entrustSampleGroupItemService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private ActTaskService actTaskService;
    @Resource
    private SystemService systemService;


    /**
     * 试验负责人确认样品
     * @param entrustId 申请单ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void confirmSample(String entrustId, AuditInfo auditInfo) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);

            Map<String,Object> model = new HashMap<>();
            model.put("code",entrustInfo.getCode());
            String title = "";
            String assigner = "";
            if (entrustInfo != null) {
                //确认样品
                if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                    auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason())?"同意":auditInfo.getReason());
                    //设置申请状态
                    entrustInfo.setStage(EntrustConstants.Stage.DISPATCH);
                    entrustInfo.setStatus(EntrustConstants.DispatchStage.CREATE_PLAN);
                    entrustInfo.setConfirmDate(new Date());
                    //拆分样品库
                    splitSample(entrustInfo);

                    title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Plan,model);
                    User testCharge = entrustInfo.getTestCharge();
                    testCharge = systemService.getUser(testCharge.getId());
                    assigner = testCharge.getLoginName();
                }
                //修改样品
                if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
                    //设置申请状态
                    entrustInfo.setStage(EntrustConstants.Stage.DISPATCH);
                    entrustInfo.setStatus(EntrustConstants.DispatchStage.MODIFY_SAMPLE);

                    title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Modify_Sample,model);
                    User user = entrustInfo.getUser();
                    user = systemService.getUser(user.getId());
                    assigner = user.getLoginName();
                }
                //设置审核信息
                auditInfo.setBusinessKey(entrustId);
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setType(EntrustConstants.AuditType.ENTRUST_AUDIT);

                //保存
                auditInfoService.save(auditInfo);
                entrustInfoService.save(entrustInfo);

                //确认样品
                Map<String,Object> vars = new HashMap<>();
                vars.put("message", title);
                actTaskService.apiComplete(entrustId,"",auditInfo.getResult()+"",assigner,vars);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 拆分样品
     * @param entrustInfo 申请信息
     */
    private void splitSample(EntrustInfo entrustInfo){
        //获取试验组信息
        List<EntrustTestGroup> testGroups = entrustTestGroupService.listByEntrustId(entrustInfo.getId());
        //插入样品库
        //SampleEntrust sampleEntrust = new SampleEntrust(entrustInfo.getId(),entrustInfo.getCode(),entrustInfo.getSampleType(),0,entrustInfo.getUser(),entrustInfo.getOrg(),entrustInfo.getAfterTest());
        Integer sampleAmount = 0;
        Integer sampleSn = 1;
        for(EntrustTestGroup testGroup : testGroups){
            String testGroupId = testGroup.getId();
            Integer abilityType = testGroup.getAbilityType();
            //获取试验组中样品组信息
            List<EntrustSampleGroup> sampleGroups = entrustSampleGroupService.listByTestGroupId(testGroupId);

            //拆分样品信息
            for(EntrustSampleGroup sampleGroup : sampleGroups){
                //样品库信息
               /* SampleGroup sample = new SampleGroup(sampleGroup.getName(),sampleGroup.getType(),sampleGroup.getSn(),sampleGroup
                        .getNo(),sampleGroup.getAmount(),sampleGroup.getVoltage(),sampleGroup.getSize(),sampleGroup.getDesc(),sampleGroup
                        .getToken(),sampleGroup.getNo(),sampleGroup.getSn());*/

                if(EntrustConstants.Sample_Type.PRODUCT.equals(entrustInfo.getSampleType())){
                    EntrustSampleGroupItem sampleGroupItem = new EntrustSampleGroupItem();
                    BeanUtils.copyProperties(sampleGroup,sampleGroupItem,"id","bom","no");
                    sampleGroupItem.setSGroupId(sampleGroup.getId());
                    sampleGroupItem.setNo(entrustInfo.getCode()+ IdGen.getSeqNo(sampleSn,3));
                    entrustSampleGroupItemService.save(sampleGroupItem);
                    sampleSn++;
                   /* //样品库信息
                    Sampleinfo sampleinfo = new iu(sample.getName(),sample.getType(),sample.getCode(),sample
                            .getFlowCode(),sample.getAmount(),sampleGroup.getStatus(),sampleGroup.getVoltage(),sampleGroup.getSize(),sampleGroup
                            .getDesc(),sampleGroup.getModel(),sampleGroup.getToken(),sampleGroup.getFactureDate(),sampleGroup
                            .getSn());
                    sample.getSampleinfos().add(sampleinfo);*/
                }else{
                    for(int i=0;i<sampleGroup.getAmount();i++) {
                        EntrustSampleGroupItem sampleGroupItem = new EntrustSampleGroupItem();
                        BeanUtils.copyProperties(sampleGroup, sampleGroupItem, "id","bom", "no");

                        //sampleGroupItem.setSn(entrustInfo.getCode()+ IdGen.getSeqNo(sampleSn,3));
                        sampleGroupItem.setNo(entrustInfo.getCode()+ IdGen.getSeqNo(sampleSn,3));
                        sampleGroupItem.setSn(sampleGroupItem.getNo());
                        sampleGroupItem.setAmount(1);
                        sampleGroupItem.setSGroupId(sampleGroup.getId());
                        entrustSampleGroupItemService.save(sampleGroupItem);

                        /*//样品库信息
                        Sampleinfo sampleinfo = new Sampleinfo(sample.getName(),sample.getType(),sample.getCode(),sample
                                .getFlowCode(),sample.getAmount(),sampleGroup.getStatus(),sampleGroup.getVoltage(),sampleGroup.getSize(),sampleGroup
                                .getDesc(),sampleGroup.getModel(),sampleGroup.getToken(),sampleGroup.getFactureDate(),sampleGroup
                                .getSn());
                        sample.getSampleinfos().add(sampleinfo);
                        sampleSn++;*/
                        sampleSn++;
                    }
                }
                //样品库信息
                sampleAmount += sampleGroup.getAmount();
                /*sampleEntrust.getSamplegroupList().add(sample);*/
            }
            /*sampleEntrust.setSampleCount(sampleAmount);
            sampleEntrustService.save(sampleEntrust);*/
        }
    }

    /**
     * 创建计划
     * @param entrustId 申请ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void createPlan(String entrustId,Integer emergency) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            entrustInfo.setEmergency(emergency);
            entrustInfo.setPlanDate(new Date());
            entrustInfoService.save(entrustInfo);
            if (entrustInfo != null) {
                //设置申请状态
                entrustInfo.setStage(EntrustConstants.Stage.DISPATCH);
                entrustInfo.setStatus(EntrustConstants.DispatchStage.ASSIGN_TASK);
                taskService.createTask(entrustId);
                entrustInfoService.save(entrustInfo);

                Map<String,Object> model = new HashMap<>();
                model.put("code",entrustInfo.getCode());
                String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Task_Assign,model);

                //创建计划
                Map<String,Object> vars = new HashMap<>();
                vars.put("message", title);
                User testCharge = entrustInfo.getTestCharge();
                testCharge = systemService.getUser(testCharge.getId());
                actTaskService.apiComplete(entrustId,"", Global.YES, testCharge.getLoginName(),vars);
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 确认样品列表
     * @param page  分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<EntrustInfo> listByConfirmSample(Page<EntrustInfo> page, EntrustInfo entrustInfo) throws ServiceException {
        return entrustInfoService.findPage(page,entrustInfo);
    }

    /**
     * 编制计划列表
     * @param page  分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<EntrustInfo> listByPlan(Page<EntrustInfo> page, EntrustInfo entrustInfo) throws ServiceException {
        return entrustInfoService.findPlanPage(page,entrustInfo);
    }
}
