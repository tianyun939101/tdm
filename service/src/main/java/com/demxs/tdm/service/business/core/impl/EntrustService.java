package com.demxs.tdm.service.business.core.impl;

import com.demxs.tdm.dao.business.EntrustSampleGroupDao;
import com.demxs.tdm.dao.business.EntrustSampleGroupItemDao;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.dataCenter.ZyTestMethod;
import com.demxs.tdm.service.business.*;
import com.demxs.tdm.service.business.util.CodeUtil;
import com.demxs.tdm.service.dataCenter.ZyTestMethodService;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.vo.MainTotalVO;
import com.demxs.tdm.domain.business.vo.TaskExecuteVO;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.comac.common.constant.SysConstants;
import com.demxs.tdm.service.business.*;
import com.demxs.tdm.service.business.core.IEntrustService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.CommonEntityEventListener;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 申请业务实现类
 * User: wuliepeng
 * Date: 2017-11-08
 * Time: 下午3:38
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustService implements IEntrustService {
    private static final Logger log = LoggerFactory.getLogger(EntrustService.class);
    @Autowired
    private EntrustInfoService entrustInfoService;
    @Autowired
    private EntrustOtherInfoService entrustOtherInfoService;
    @Autowired
    private EntrustTestGroupService entrustTestGroupService;
    @Autowired
    private EntrustTestGroupAbilityService entrustTestGroupAbilityService;
    @Autowired
    private EntrustTestItemCoditionService entrustTestItemCoditionService;
    @Autowired
    private EntrustSampleGroupService entrustSampleGroupService;
    @Autowired
    private EntrustSampleBomService entrustSampleBomService;
    @Autowired
    private ProjectInfoService projectInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private EntrustSampleGroupItemDao entrustSampleGroupItemDao;
    @Autowired
    private EntrustSampleGroupDao entrustSampleGroupDao;
    @Autowired
    private TestTaskService testTaskService;
    @Autowired
    private EntrustReportService entrustReportService;
    @Resource
    private ActTaskService actTaskService;
    @Resource
    private ExternalApi externalApi;
    @Resource
    private org.activiti.engine.TaskService taskService;
    @Autowired
    private EntrustAtaChapterService ataChapterService;

    @Autowired
    ZyTestMethodService zyTestMethodService;

    /**
     * 保存申请单信息
     *  1.保存申请单主体信息
     *  2.保存申请单其他信息
     *  3.删除试验组信息
     *  4.保存试验组信息
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(EntrustInfo entrustInfo) throws ServiceException {
        try {
            //设置申请单状态
            if (entrustInfo.getStatus() == null) {
                entrustInfo.setStage(EntrustConstants.Stage.ENTRUST);
                entrustInfo.setStatus(EntrustConstants.EntrustStage.DRAFT);
            }
            if(entrustInfo.getUser() == null) {
                entrustInfo.setUser(UserUtils.getUser());
                entrustInfo.setOrg(UserUtils.getUser().getOffice());
                entrustInfo.setCompany(UserUtils.getUser().getOffice().getParent());
            }
            if(entrustInfo.getAssignUser() == null){
                entrustInfo.setAssignUser(UserUtils.getUser().getId()+",");
            }
            //保存申请单信息
            entrustInfoService.save(entrustInfo);
            //保存申请单其它信息
            String entrustId = entrustInfo.getId();
//            EntrustOtherInfo otherInfo = entrustInfo.getOtherInfo();
//            otherInfo.setEntrustId(entrustId);
//            entrustOtherInfoService.save(otherInfo);
            //删除试验组信息
            deleteTestGroup(entrustId);
            //保存申请单试验组信息
            List<EntrustTestGroup> testGroups = entrustInfo.getTestGroupList();
            for (EntrustTestGroup testGroup : testGroups) {
                if (testGroup == null){
                    continue;
                }
                //保存试验组信息
                testGroup.setEntrustId(entrustId);
                entrustTestGroupService.save(testGroup);
                String testGroupId = testGroup.getId();
                Integer abilityType = testGroup.getAbilityType();
                //样品组
                List<EntrustSampleGroup> sampleGroups = testGroup.getSampleGroups();
                //Integer sampleSn = 1;
                for (EntrustSampleGroup sampleGroup : sampleGroups) {
                    if (sampleGroup == null){
                        continue;
                    }
                    //保存样品组信息
                    sampleGroup.setEntrustId(entrustId);
                    sampleGroup.setTGroupId(testGroupId);
                    //sampleGroup.setNo(entrustInfo.getCode()+ IdGen.getSeqNo(sampleSn,3));
                    entrustSampleGroupService.save(sampleGroup);
                    //保存样品BOM信息
//                    String sampleGroupId = sampleGroup.getId();
//                    List<EntrustSampleBom> sampleBoms = sampleGroup.getBom();
//                    for (EntrustSampleBom sampleBom : sampleBoms){
//                        sampleBom.setEntrustId(entrustId);
//                        sampleBom.setTGroupId(testGroupId);
//                        sampleBom.setSGroupId(sampleGroupId);
//                        entrustSampleBomService.save(sampleBom);
//                    }
                    //sampleSn++;
                }
                //试验能力
                List<EntrustTestGroupAbility> testGroupAbilitys = testGroup.getAbilityList();
                for (EntrustTestGroupAbility testGroupAbility : testGroupAbilitys) {
                    if (testGroupAbility == null){
                        continue;
                    }
                    //保存试验能力
                    testGroupAbility.setEntrustId(entrustId);
                    testGroupAbility.setTGroupId(testGroupId);
                    entrustTestGroupAbilityService.save(testGroupAbility);
                    //如果试验能力为试验项或自定义序列,则保存试验条件与参数
                    if (EntrustConstants.Ability_Type.SEQUENCE.equals(abilityType) || EntrustConstants.Ability_Type.CUSTOM_SEQUENCE.equals(abilityType)) {
                        List<EntrustTestItemCodition> conditions = testGroupAbility.getConditions();
                        for (EntrustTestItemCodition condition : conditions) {
                            condition.setEntrustId(entrustId);
                            condition.setTGroupId(testGroupId);
                            condition.setItemId(testGroupAbility.getItemId());
                            condition.setEntrustAbilityId(testGroupAbility.getId());
                            entrustTestItemCoditionService.save(condition);
                        }
                    }
                }
            }
            //ata章节
            ataChapterService.deleteByEntrustId(entrustId);
            List<EntrustAtaChapter> ataChapterList = entrustInfo.getAtaChapterList();
            if(null != ataChapterList){
                for (EntrustAtaChapter ataChapter : ataChapterList) {
                    ataChapter.setEntrustId(entrustId);
                    ataChapterService.save(ataChapter);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 修改申请单信息
     *  1.保存申请单主体信息
     *  2.保存申请单其他信息
     *  3.删除试验组信息
     *  4.保存试验组信息
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void sysSave(EntrustInfo entrustInfo) throws ServiceException {
        try {
            EntrustInfo temp = entrustInfoService.get(entrustInfo.getId());
            entrustInfo.setUser(entrustInfo.getUser());
            entrustInfo.setAuditUser(temp.getAuditUser());
            entrustInfo.setStage(temp.getStage());
            entrustInfo.setStatus(temp.getStatus());
            entrustInfo.setPlanDate(temp.getPlanDate());
            entrustInfo.setReason(temp.getReason());
            entrustInfo.setAuditDate(temp.getAuditDate());
            entrustInfo.setAcceptDate(temp.getAcceptDate());
            entrustInfo.setConfirmDate(temp.getConfirmDate());
            entrustInfo.setPutInStatus(temp.getPutInStatus());
            entrustInfo.setLabManager(temp.getLabManager());
            entrustInfo.setTestCharge(temp.getTestCharge());
            entrustInfo.setEmergency(temp.getEmergency());
            this.save(entrustInfo);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 修改样品组信息
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void modifySample(EntrustInfo entrustInfo) throws ServiceException {
        try {
            String entrustId = entrustInfo.getId();
            //申请单试验样品组信息
            List<EntrustTestGroup> testGroups = entrustInfo.getTestGroupList();
            entrustInfo = entrustInfoService.get(entrustId);
            //设置申请单状态
            if (EntrustConstants.DispatchStage.MODIFY_SAMPLE.equals(entrustInfo.getStatus())) {
                entrustInfo.setStage(EntrustConstants.Stage.DISPATCH);
                entrustInfo.setStatus(EntrustConstants.DispatchStage.CONFIRM_SAMPLE);
                entrustInfoService.save(entrustInfo);
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setBusinessKey(entrustId);
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setResult(EntrustConstants.AuditResult.PASS);
                auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason())?"样品信息已修改":auditInfo.getReason());
                auditInfo.setType(EntrustConstants.AuditType.ENTRUST_AUDIT);
                auditInfoService.save(auditInfo);
            }

            //删除试验组样品组信息
            entrustSampleGroupService.deleteByEntrustId(entrustId);
            for (EntrustTestGroup testGroup : testGroups) {
                String testGroupId = testGroup.getId();
                //样品组
                List<EntrustSampleGroup> sampleGroups = testGroup.getSampleGroups();
                for (EntrustSampleGroup sampleGroup : sampleGroups) {
                    //保存样品组信息
                    sampleGroup.setEntrustId(entrustId);
                    sampleGroup.setTGroupId(testGroupId);
                    entrustSampleGroupService.save(sampleGroup);
                    //保存样品BOM信息
                    String sampleGroupId = sampleGroup.getId();
                    List<EntrustSampleBom> sampleBoms = sampleGroup.getBom();
                    for (EntrustSampleBom sampleBom : sampleBoms){
                        sampleBom.setEntrustId(entrustId);
                        sampleBom.setTGroupId(testGroupId);
                        sampleBom.setSGroupId(sampleGroupId);
                        entrustSampleBomService.save(sampleBom);
                    }
                }
            }

            Map<String,Object> model = new HashMap<>();
            model.put("code",entrustInfo.getCode());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Confirm_Sample,model);
            User testCharge = entrustInfo.getTestCharge();
            testCharge = systemService.getUser(testCharge.getId());
            String assigner = testCharge.getLoginName();
            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(entrustId,entrustInfo.getReason(), Global.YES,assigner,vars);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 删除申请单信息
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(String entrustId) throws ServiceException {
        try {
            entrustInfoService.delete(new EntrustInfo(entrustId));

            //删除流程
            actTaskService.apiDeleteProcess(entrustId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 根据申请单ID删除申请单中试验组信息
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    private void deleteTestGroup(String entrustId) throws ServiceException{
        entrustTestGroupService.deleteByEntrustId(entrustId);
        entrustTestGroupAbilityService.deleteByEntrustId(entrustId);
        entrustTestItemCoditionService.deleteByEntrustId(entrustId);
        entrustSampleGroupService.deleteByEntrustId(entrustId);
        entrustSampleBomService.deleteByEntrustId(entrustId);
    }

    /**
     * 提交申请
     * @param entrustInfo   申请单信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void submit(EntrustInfo entrustInfo) throws ServiceException {
        try {
            if(StringUtils.isEmpty(entrustInfo.getCode())) {
                entrustInfo.setCode(CodeUtil.genEntrustCode());
            }
            //设置申请状态
            entrustInfo.setStage(EntrustConstants.Stage.ENTRUST);
            entrustInfo.setStatus(EntrustConstants.EntrustStage.AUDIT);
            //设置申请单审核人
            User auditUser = null;
            //如果申请单关联了项目,则设置审核人为项目负责人
//            String projectId = entrustInfo.getOtherInfo().getProjectId();
//            if (StringUtils.isNotEmpty(projectId)) {
//                ProjectInfo projectInfo = projectInfoService.get(projectId);
//                auditUser = projectInfo.getCharge();
//                if(auditUser == null){
//                    throw new ServiceException(String.format("项目%s未设置负责人",projectInfo.getName()));
//                }
//            }
//            //如果申请单没有关联项目,则设置审核为申请人所在部门的部门经理
//            else {
//                if(entrustInfo.getUser() == null) {
//                    entrustInfo.setUser(UserUtils.getUser());
//                    auditUser = UserUtils.getUser().getOffice().getPrimaryPerson();
//                }else{
//                    User entrustUser = systemService.getUser(entrustInfo.getUser().getId());
//                    auditUser = entrustUser.getOffice().getPrimaryPerson();
//                }
//                if(auditUser == null){
//                    throw new ServiceException("申请人所在部门未设置负责人");
//                }
//            }
            if(entrustInfo.getUser() == null) {
                entrustInfo.setUser(UserUtils.getUser());
                auditUser = UserUtils.getUser().getOffice().getPrimaryPerson();
            }else{
                User entrustUser = systemService.getUser(entrustInfo.getUser().getId());
                auditUser = entrustUser.getOffice().getPrimaryPerson();
            }
            if(auditUser == null){
                throw new ServiceException("申请人所在部门未设置负责人");
            }
            auditUser = systemService.getUser(auditUser.getId());
            entrustInfo.setAuditUser(auditUser);

            entrustInfo.setAssignUser(UserUtils.getUser().getId()+","+auditUser.getId()+",");

            //保存申请单信息
            save(entrustInfo);

            AuditInfo auditInfo = new AuditInfo();
            auditInfo.setBusinessKey(entrustInfo.getId());
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfo.setResult(EntrustConstants.AuditResult.APPLY);
            auditInfo.setReason("提交申请单");
            auditInfo.setType(EntrustConstants.AuditType.ENTRUST_APPLY);
            auditInfoService.save(auditInfo);

            //启动流程
            Map<String,Object> model = new HashMap<>();
            model.put("userName",entrustInfo.getUser().getName());
            model.put("code",entrustInfo.getCode());

            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Audit,model);
            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            vars.put("code", entrustInfo.getCode());
            vars.put("reportFlag",entrustInfo.getReportFlag());
            String taskTitle = entrustInfo.getUser().getName()+"("+entrustInfo.getCode()+")";
            actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.WEITUODSH,entrustInfo.getAuditUser().getLoginName(),entrustInfo.getId(),taskTitle,vars);
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 撤消申请
     * @param entrustId 申请ID
     * @param userId    撤消人
     * @param reason    撤消原因
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void undo(String entrustId, String userId, String reason) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            if (entrustInfo != null) {
                //判断试验实负责人是否已经确认申请单样品信息,申请单确认样品之后不允许撤消撤消申请单
                if (entrustInfo.getStatus() > EntrustConstants.DispatchStage.MODIFY_SAMPLE) {
                    log.error("申请单[{}]确认样品之后不允许撤消", entrustInfo.getCode());
                    return;
                }
                //设置撤消人与撤消原因
                entrustInfo.setUndoUser(new User(userId));
                entrustInfo.setUndoReason(reason);
                //设置申请单状态
                entrustInfo.setStage(EntrustConstants.Stage.FINISH);
                entrustInfo.setStatus(EntrustConstants.FinishStage.UNDO);
                entrustInfoService.save(entrustInfo);
                //删除流程
                actTaskService.apiDeleteProcess(entrustInfo.getId());
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 信息反馈
     * @param entrustId 申请ID
     * @param reason    撤消原因
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void feedback(String entrustId, String reason) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            if (entrustInfo != null) {
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setType(EntrustConstants.AuditType.ENTRUST_FEEDBACK);
                auditInfo.setResult(EntrustConstants.AuditResult.FEEDBACK);
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setReason(reason);
                auditInfo.setBusinessKey(entrustId);
                auditInfoService.save(auditInfo);

//                SendTodoParam sendTodoParam = new SendTodoParam();
//                sendTodoParam.setType(2);
//                sendTodoParam.setSubject("您的申请单"+entrustInfo.getCode()+"信息通知:"+reason);
//                sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/a/tdm/business/entrust/detail?id="+entrustId);
//                sendTodoParam.setCreateTime(new Date());
//                sendTodoParam.setModelId(IdGen.uuid());
//                sendTodoParam.addTarget(systemService.getUser(entrustInfo.getUser().getId()).getLoginName());
//                externalApi.sendTodo(sendTodoParam);
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 终止申请
     * @param entrustId 申请单ID
     * @param userId    终止用户
     * @param reason    终止原因
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void stop(String entrustId, String userId, String reason,Integer notify) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            if (entrustInfo != null) {
                //设置终止人与终止原因
                entrustInfo.setStopUser(new User(userId));
                entrustInfo.setStopReason(reason);
                //设置申请单状态
                entrustInfo.setStage(EntrustConstants.Stage.FINISH);
                entrustInfo.setStatus(EntrustConstants.FinishStage.STOP);
                entrustInfoService.save(entrustInfo);

                //删除流程
                actTaskService.apiDeleteProcess(entrustInfo.getId());

//                if(notify.equals(1)){
//                    SendTodoParam sendTodoParam = new SendTodoParam();
//                    sendTodoParam.setType(2);
//                    sendTodoParam.setSubject("您的申请单"+entrustInfo.getCode()+"已被"+UserUtils.getUser().getName()+"终止");
//                    sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/a/tdm/business/entrust/detail?id="+entrustId);
//                    sendTodoParam.setCreateTime(new Date());
//                    sendTodoParam.setModelId(IdGen.uuid());
//                    sendTodoParam.addTarget(systemService.getUser(entrustInfo.getUser().getId()).getLoginName());
//                    externalApi.sendTodo(sendTodoParam);
//                }
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 完成申请
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void finish(String entrustId) throws ServiceException{
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            if (entrustInfo != null) {
                //设置申请单状态
                entrustInfo.setStage(EntrustConstants.Stage.FINISH);
                entrustInfo.setStatus(EntrustConstants.FinishStage.DONE);
                entrustInfoService.save(entrustInfo);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 申请部门负责人或项目负责人审核申请信息
     * @param entrustId 申请单ID
     * @param auditInfo    审核信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void audit(String entrustId, AuditInfo auditInfo) throws ServiceException {
        EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
        try {
            Map<String,Object> model = new HashMap<>();
            model.put("code",entrustInfo.getCode());
            User createUser = systemService.getUser(entrustInfo.getCreateById());
            model.put("userName",createUser.getName());
            String title = "";
            String assigner = "";
            if (entrustInfo != null) {
                //审核通过
                if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                    auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason())?"同意":auditInfo.getReason());
                    //设置申请单状态
                    entrustInfo.setStage(EntrustConstants.Stage.ENTRUST);
                    entrustInfo.setStatus(EntrustConstants.EntrustStage.ACCEPT);
                    entrustInfo.setAuditDate(new Date());
                     /*设置试验室负责人*/
                    LabInfo labInfo = labInfoService.get(entrustInfo.getLabId());
                    if(labInfo == null || labInfo.getLeader() == null || StringUtils.isEmpty(labInfo.getLeader().getId())){
                        throw new ServiceException("申请试验室未设置负责人");
                    }
                    entrustInfo.setLabManager(labInfo.getLeader());
                    entrustInfo.setAssignUser((StringUtils.isEmpty(entrustInfo.getAssignUser())?"":entrustInfo.getAssignUser())+labInfo.getLeader()
                            .getId() +",");
                    title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Audit,model);
                    User labManager = entrustInfo.getLabManager();
                    labManager = systemService.getUser(labManager.getId());
                    assigner = labManager.getLoginName();
                }
                //审核不通过
                if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
                    //设置申请单状态
                    entrustInfo.setStage(EntrustConstants.Stage.ENTRUST);
                    entrustInfo.setStatus(EntrustConstants.EntrustStage.AUDIT_RETURN);

                    title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Audit_Return,model);
                    assigner = createUser.getLoginName();
                }
                //设置审核信息
                auditInfo.setBusinessKey(entrustId);
                auditInfo.setType(EntrustConstants.AuditType.ENTRUST_AUDIT);
                auditInfo.setAuditUser(UserUtils.getUser());
                entrustInfoService.save(entrustInfo);
                auditInfoService.save(auditInfo);

                //审批
                Map<String,Object> vars = new HashMap<>();
                vars.put("message", title);
                actTaskService.apiComplete(entrustId,auditInfo.getReason(),auditInfo.getResult()+"",assigner,vars);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }

        try{
            entrustInfoService.sendAduitMail(entrustInfo.getCommissionName());
        }catch (Exception  e){
            e.printStackTrace();
        }
    }

    /**
     * 试验室负责人接收(审核)申请
     * @param entrustId 申请单ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void accept(String entrustId, AuditInfo auditInfo) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);

            Map<String,Object> model = new HashMap<>();
            model.put("code",entrustInfo.getCode());
            String title = "";
            String assigner = "";
            if (entrustInfo != null) {
                //接收
                if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                    auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason())?"同意":auditInfo.getReason());
                    //设置申请单状态
                    entrustInfo.setStage(EntrustConstants.Stage.DISPATCH);
                    entrustInfo.setStatus(EntrustConstants.DispatchStage.CONFIRM_SAMPLE);
                    entrustInfo.setAcceptDate(new Date());

                    title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Confirm_Sample,model);
                    User testCharge = entrustInfo.getTestCharge();
                    testCharge = systemService.getUser(testCharge.getId());
                    assigner = testCharge.getLoginName();

                    if (CommonEntityEventListener.SEND_EXTERNAL_MESSAGE) {
                        SendTodoParam sendTodoParam = new SendTodoParam();
                        sendTodoParam.setType(2);
                        Map<String,Object> model1 = new HashMap<>();
                        model1.put("userName", entrustInfo.getTestCharge().getName());
                        model1.put("code", entrustInfo.getCode());
                        sendTodoParam.setSubject(FreeMarkers.renderString(EntrustConstants.MessageTemplate.NOTICE_PUSH_SAMPLE,model1));
                        sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/a/tdm/business/entrust/detail?id="+entrustId);
                        sendTodoParam.setCreateTime(new Date());
                        sendTodoParam.setModelId(IdGen.uuid());
                        sendTodoParam.addTarget(systemService.getUser(entrustInfo.getUser().getId()).getLoginName());
                        externalApi.sendTodo(sendTodoParam);
                    }
                }
                //不接收
                if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
                    //设置申请单状态
                    entrustInfo.setStage(EntrustConstants.Stage.ENTRUST);
                    entrustInfo.setStatus(EntrustConstants.EntrustStage.ACCEPT_RETURN);

                    title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Audit_Return,model);
                    User user = entrustInfo.getUser();
                    user = systemService.getUser(user.getId());
                    assigner = user.getLoginName();
                }
                //设置审核信息
                auditInfo.setBusinessKey(entrustId);
                auditInfo.setType(EntrustConstants.AuditType.ENTRUST_ACCEPT);
                auditInfo.setAuditUser(UserUtils.getUser());
                entrustInfoService.save(entrustInfo);
                auditInfoService.save(auditInfo);

                //审批
                Map<String,Object> vars = new HashMap<>();
                vars.put("message", title);
                actTaskService.apiComplete(entrustId,auditInfo.getReason(),auditInfo.getResult()+"",assigner,vars);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 试验室负责人指定试验负责人
     * @param entrustId 申请单ID
     * @param testLeader 试验负责人
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void assignLeader(String entrustId, String testLeader,String reason) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            if (entrustInfo != null) {
                entrustInfo.setTestCharge(new User(testLeader));
                entrustInfo.setAssignUser((StringUtils.isEmpty(entrustInfo.getAssignUser())?"":entrustInfo.getAssignUser())+testLeader+",");
                entrustInfoService.save(entrustInfo);
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setBusinessKey(entrustId);
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setResult(EntrustConstants.AuditResult.PASS);
                auditInfo.setReason(StringUtils.isEmpty(reason)?"同意":reason);
                this.accept(entrustId, auditInfo);
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 任务处理人变更
     * @param entrustCode 申请单编号
     * @param oldUserId 原用户ID
     * @param newUserId 新用户ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void changeUser(String entrustCode, String oldUserId, String newUserId) throws ServiceException {
        if(oldUserId.equals(newUserId)){
            return;
        }
        if(StringUtils.isEmpty(entrustCode)){
            List<EntrustInfo> entrustInfos = entrustInfoService.findList(new EntrustInfo());
            for (EntrustInfo entrustInfo : entrustInfos){
                if(EntrustConstants.Stage.FINISH.equals(entrustInfo.getStage())){
                    continue;
                }
                changeUser(entrustInfo,new User(newUserId),oldUserId);
                entrustInfo.setAssignUser((StringUtils.isEmpty(entrustInfo.getAssignUser())?"":entrustInfo.getAssignUser())+newUserId+",");
                entrustInfoService.save(entrustInfo);
            }
        }else{
            EntrustInfo entrustInfo = entrustInfoService.getByCode(entrustCode);
            changeUser(entrustInfo,new User(newUserId),oldUserId);
            entrustInfo.setAssignUser((StringUtils.isEmpty(entrustInfo.getAssignUser())?"":entrustInfo.getAssignUser())+newUserId+",");
            entrustInfoService.save(entrustInfo);
        }

    }

    private void changeUser(EntrustInfo entrustInfo,User user,String oldUserId) throws ServiceException{
        try{
            /**
             * 审核状态
             */
            if(EntrustConstants.EntrustStage.AUDIT.equals(entrustInfo.getStatus())){
                entrustInfo.setAuditUser(user);
            }else if(EntrustConstants.EntrustStage.AUDIT_RETURN.equals(entrustInfo.getStatus())){
                entrustInfo.setUser(user);
            }else if(EntrustConstants.EntrustStage.ACCEPT.equals(entrustInfo.getStatus())){
                entrustInfo.setLabManager(user);
            }else if(EntrustConstants.EntrustStage.ACCEPT_RETURN.equals(entrustInfo.getStatus())){
                entrustInfo.setUser(user);
            }else if(EntrustConstants.DispatchStage.CONFIRM_SAMPLE.equals(entrustInfo.getStatus())){
                entrustInfo.setTestCharge(user);
            }else if(EntrustConstants.DispatchStage.MODIFY_SAMPLE.equals(entrustInfo.getStatus())){
                entrustInfo.setUser(user);
            }else if(EntrustConstants.DispatchStage.CREATE_PLAN.equals(entrustInfo.getStatus())){
                entrustInfo.setTestCharge(user);
            }else if(EntrustConstants.DispatchStage.ASSIGN_TASK.equals(entrustInfo.getStatus())){
                entrustInfo.setTestCharge(user);
            }else if(EntrustConstants.ReportStage.DRAW.equals(entrustInfo.getStatus())){
                EntrustReport report = entrustReportService.getByEntrustId(entrustInfo.getId());
                report.getOwner().replace(oldUserId,user.getId());
                entrustReportService.save(report);
            }else if(EntrustConstants.ReportStage.AUDIT.equals(entrustInfo.getStatus())){
                EntrustReport report = entrustReportService.getByEntrustId(entrustInfo.getId());
                report.getOwner().replace(oldUserId,user.getId());
                entrustReportService.save(report);
            }else if(EntrustConstants.ReportStage.REJECT.equals(entrustInfo.getStatus())){
                EntrustReport report = entrustReportService.getByEntrustId(entrustInfo.getId());
                report.getOwner().replace(oldUserId,user.getId());
                entrustReportService.save(report);
            }else if(EntrustConstants.ReportStage.PASS.equals(entrustInfo.getStatus())){
                EntrustReport report = entrustReportService.getByEntrustId(entrustInfo.getId());
                report.getOwner().replace(oldUserId,user.getId());
                entrustReportService.save(report);
            }
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(entrustInfo.getId()).active().singleResult();
            if (task != null) {
//                taskService.claim(task.getId(),systemService.getUser(user.getId()).getLoginName());
                taskService.setAssignee(task.getId(),systemService.getUser(user.getId()).getLoginName());
            }
            entrustInfoService.save(entrustInfo);
        }catch (Exception e){
            throw new ServiceException(e);
        }
    }

    /**
     * 查看申请单信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    @Override
    public EntrustInfo get(String entrustId) throws ServiceException {
        EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
        if(entrustInfo!=null){
            EntrustOtherInfo otherInfo = entrustOtherInfoService.getByEntrustId(entrustId);
            //申请单设置其他信息
            entrustInfo.setOtherInfo(otherInfo);
            //申请单设置试验组信息
            entrustInfo.setTestGroupList(loadTestGroup(entrustId));
        }
        if(!entrustInfo.getTestGroupList().isEmpty()){
            entrustInfo.setSampleGroup(entrustInfo.getTestGroupList().get(0).getSampleGroups().get(0));
        }
        return entrustInfo;
    }

    /**
     * 查看申请单信息
     * @param code 申请单编号
     * @return
     * @throws ServiceException
     */
    @Override
    public EntrustInfo getByCode(String code) throws ServiceException {
        EntrustInfo entrustInfo = entrustInfoService.getByCode(code);
        if(entrustInfo!=null){
            return this.get(entrustInfo.getId());
        }
        return entrustInfo;
    }

    /**
     * 查看申请单信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    @Override
    public EntrustInfo detail(String entrustId) throws ServiceException {
        EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
        if(entrustInfo!=null){
            EntrustOtherInfo otherInfo = entrustOtherInfoService.getByEntrustId(entrustId);
            //申请单设置其他信息
            entrustInfo.setOtherInfo(otherInfo);
            //申请单设置试验组信息
            entrustInfo.setTestGroupList(loadTestGroup(entrustId));
            //加载申请单审核信息
            loadAuditInfo(entrustInfo);
            //加载申请单任务执行信息
            loadExecuteDetail(entrustInfo);
            //加载申请单报告信息
            loadReport(entrustInfo);
            //设置数据查看权限
            entrustInfo.setDataAuth(getDataAuth(entrustInfo));
        }
        return entrustInfo;
    }

    /**
     * 查看申请单信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    @Override
    public EntrustInfo detailTask(String entrustId) throws ServiceException {
        EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
        if(entrustInfo!=null){
            //申请单设置试验组信息
            entrustInfo.setTestGroupList(loadTestGroup(entrustId));
            //加载申请单任务执行信息
            loadExecuteDetail(entrustInfo);
        }
        return entrustInfo;
    }

    /**
     * 查看样品组信息
     * @param sampleGroupId 样品组ID
     * @return
     * @throws ServiceException
     */
    @Override
    public EntrustSampleGroup sampleGroupDetail(String sampleGroupId) throws ServiceException {
        EntrustSampleGroup sampleGroup = entrustSampleGroupService.get(sampleGroupId);
        if(sampleGroup!=null){
            List<EntrustSampleBom> bom = entrustSampleBomService.listBySampleGroupId(sampleGroupId);
            sampleGroup.setBom(bom);
        }
        return sampleGroup;
    }

    /**
     * 申请单列表
     * @param page 分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<EntrustInfo> list(Page<EntrustInfo> page,EntrustInfo entrustInfo) throws ServiceException {
        return entrustInfoService.findPage(page,entrustInfo);
    }

    /**
     * 申请单列表
     * @param page 分页信息
     * @param entrustInfo 查询条件封装
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<EntrustInfo> listByReport(Page<EntrustInfo> page,EntrustInfo entrustInfo) throws ServiceException {
        return entrustInfoService.findByReport(page,entrustInfo);
    }

    /**
     * 首页申请单统计
     * @return
     */
    @Override
    public MainTotalVO getByMainTotal(){
        MainTotalVO mainTotalVO = entrustInfoService.getByMainTotal();
        return mainTotalVO;
    }

    @Override
    public void addLabInfoByEntrustId(String entrustId) {
        EntrustInfo entrustInfo = this.detail(entrustId);
        List<EntrustTestGroup> testGroups = entrustInfo.getTestGroupList();
        if(CollectionUtils.isNotEmpty(testGroups)){
            for(EntrustTestGroup testGroup :testGroups){
                List<EntrustSampleGroup> groups  = testGroup.getSampleGroups();
                if(CollectionUtils.isNotEmpty(groups)){
                    for(EntrustSampleGroup g:groups){
                        entrustSampleGroupDao.updateLabInfo(g.getId(),entrustInfo.getLabId(),entrustInfo.getLabName());
                        List<EntrustSampleGroupItem> samples = entrustSampleGroupItemDao.findListByGroup(testGroup.getId(),g.getId(),entrustId);
                        if(CollectionUtils.isNotEmpty(samples)){
                            for(EntrustSampleGroupItem sample:samples){
                                entrustSampleGroupItemDao.updateLabInfo(sample.getId(),entrustInfo.getLabId(),entrustInfo.getLabName());
                            }
                        }
                    }
                }
            }
        }



    }

    /**
     * 加载申请单试验组信息
     * @param entrustId 申请单ID
     * @return
     * @throws ServiceException
     */
    private List<EntrustTestGroup> loadTestGroup(String entrustId) throws ServiceException{
        //获取试验组信息
        List<EntrustTestGroup> testGroups = entrustTestGroupService.listByEntrustId(entrustId);
        for(EntrustTestGroup testGroup : testGroups){
            String testGroupId = testGroup.getId();
            Integer abilityType = testGroup.getAbilityType();
            //获取试验组中样品组信息
            List<EntrustSampleGroup> sampleGroups = entrustSampleGroupService.listByTestGroupId(testGroupId);
            for (EntrustSampleGroup sampleGroup : sampleGroups){
                String sampleGroupId = sampleGroup.getId();

                List<EntrustSampleBom> sampleBoms = entrustSampleBomService.listBySampleGroupId(sampleGroupId);
                sampleGroup.setBom(sampleBoms);
            }

            //获取试验组中试验能力
            List<EntrustTestGroupAbility> abilities = entrustTestGroupAbilityService.listByTestGroupId(testGroupId);
            //如果试验能力为试验项或自定义序列,则获取试验条件与参数
            if(EntrustConstants.Ability_Type.SEQUENCE.equals(abilityType) || EntrustConstants.Ability_Type.CUSTOM_SEQUENCE.equals(abilityType)) {
                for (EntrustTestGroupAbility ability : abilities) {
                    //获取试验条件
                    List<EntrustTestItemCodition> conditions = entrustTestItemCoditionService.listByEntrustAbilityId(ability.getId());
//                    List<EntrustTestItemCodition> conditions = entrustTestItemCoditionService.listByTestGroupIdAndItemId(testGroupId,ability.getItemId());
                    ability.setConditions(conditions);

                }
            }
            for (EntrustTestGroupAbility ability : abilities) {
                ZyTestMethod  zm=zyTestMethodService.get(ability.getMethod());
                ability.setZyTestMethod(zm);
                if(zm !=null){
                    ability.setMethodName(zm.getName());
                }else{
                    ability.setMethodName("");
                }

            }
            //试验组设置样品组信息
            testGroup.setSampleGroups(sampleGroups);
            //试验组设置试验能力信息
            testGroup.setAbilityList(abilities);
        }
        return testGroups;
    }

    /**
     * 加载申请单审核信息
     * @param entrustInfo 申请单信息
     * @return
     * @throws ServiceException
     */
    private void loadAuditInfo(EntrustInfo entrustInfo) throws ServiceException{
        AuditInfo param = new AuditInfo();
        param.setBusinessKey(entrustInfo.getId());
        //param.setType(EntrustConstants.AuditType.SAMPLE_CONFIRM);
        List<AuditInfo> auditInfos = auditInfoService.findList(param);
        entrustInfo.setAuditInfos(auditInfos);
    }

    /**
     * 加载申请执行详情
     * @param entrustInfo 申请单信息
     * @throws ServiceException
     */
    private void loadExecuteDetail(EntrustInfo entrustInfo) throws ServiceException{
        for(EntrustTestGroup testGroup : entrustInfo.getTestGroupList()){
            List<TaskExecuteVO> taskList = new ArrayList<>();

            List<TaskExecuteVO> taskExecuteList = testTaskService.findExecuteDetailByTestGroup(testGroup.getId());
            List<TaskExecuteVO> topTaskExecuteList = getTop(taskExecuteList);
            sortTask(topTaskExecuteList);
            for (TaskExecuteVO topTask : topTaskExecuteList){
                taskList.add(topTask);
                taskList.addAll(getSubTask(taskExecuteList,topTask));
            }

            testGroup.setTaskExecuteList(taskList);
        }
    }

    private List<TaskExecuteVO> getTop(List<TaskExecuteVO> list){
        List<TaskExecuteVO> result = new ArrayList<>();
        for (TaskExecuteVO taskExecuteVO : list){
            if(SysConstants.ROOT.equals(taskExecuteVO.getPid())){
                result.add(taskExecuteVO);
            }
        }
        sortTask(result);
        return result;
    }

    private List<TaskExecuteVO> getSubTask(List<TaskExecuteVO> list,TaskExecuteVO parentTask){
        List<TaskExecuteVO> result = new ArrayList<>();
        for (TaskExecuteVO taskExecuteVO : list){
            if(parentTask.getId().equals(taskExecuteVO.getPid())){
                result.add(taskExecuteVO);
                result.addAll(getSubTask(list,taskExecuteVO));
            }
        }
        if(EntrustConstants.Ability_Type.SEQUENCE.equals(parentTask.getType())){
            for (TaskExecuteVO executeVO : result){
                if(parentTask.getStartDate() == null || parentTask.getEndDate() == null){
                    parentTask.setStartDate(executeVO.getStartDate());
                    parentTask.setEndDate(executeVO.getEndDate());
                }
                if((parentTask.getStartDate()!=null && executeVO.getStartDate()!=null) && parentTask.getStartDate().after(executeVO.getStartDate())){
                    parentTask.setStartDate(executeVO.getStartDate());
                }
                if((parentTask.getEndDate()!=null && executeVO.getEndDate()!=null) && parentTask.getEndDate().before(executeVO.getEndDate())){
                    parentTask.setEndDate(executeVO.getEndDate());
                }
            }
        }
        return result;
    }

    private List<TaskExecuteVO> sortTask(List<TaskExecuteVO> list){
        list.sort(new Comparator<TaskExecuteVO>() {
            @Override
            public int compare(TaskExecuteVO o1, TaskExecuteVO o2) {
                if(o1.getSort() > o2.getSort()){
                    return 1;
                }else if(o1.getSort() < o2.getSort()){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        return list;
    }

    /**
     * 加载申请报告信息
     * @param entrustInfo 申请单信息
     * @throws ServiceException
     */
    private void loadReport(EntrustInfo entrustInfo) throws ServiceException{
        EntrustReport report = entrustReportService.getByEntrustId(entrustInfo.getId());
        entrustInfo.setReport(report);
    }

    private boolean getDataAuth(EntrustInfo entrustInfo) throws ServiceException{
        /*if(!EntrustConstants.FinishStage.DONE.equals(entrustInfo.getStatus())){
            return false;
        }*/
        String currentUser = UserUtils.getUser().getId();

        if(currentUser.equals(entrustInfo.getUser().getId())){
            return true;
        }

        /**
         * 项目负责人与核心用户判定
         */
        if(entrustInfo.getOtherInfo()!=null && StringUtils.isNotEmpty(entrustInfo.getOtherInfo().getProjectId())) {
            ProjectInfo projectInfo = projectInfoService.get(entrustInfo.getOtherInfo().getProjectId());
            User charge = projectInfo.getCharge();
            String coreUser = projectInfo.getMainUserIds();
            /**
             * 是否是项目负责人
             */
            if (currentUser.equals(charge.getId())) {
                return true;
            }
            /**
             * 是否是核心成员
             */
            if (coreUser != null && coreUser.indexOf(currentUser) >= 0) {
                return true;
            }
        }
        /**
         * 是否是申请单审核人
         */
        if(null !=entrustInfo.getAuditUser() && currentUser.equals(entrustInfo.getAuditUser().getId())){
            return true;
        }

        /**
         * 是否是试验室负责人
         */
        if(null !=entrustInfo.getLabManager() && currentUser.equals(entrustInfo.getLabManager().getId())){
            return true;
        }

        /**
         * 是否是试验负责人
         */
        if(null!=entrustInfo.getTestCharge() && currentUser.equals(entrustInfo.getTestCharge().getId())){
            return true;
        }

        if(entrustInfo.getReport() != null) {
                EntrustReport report = entrustInfo.getReport();
            if(StringUtils.isNotEmpty(report.getOwner()) && report.getOwner().indexOf(currentUser) >= 0){
                return true;
            }

            /**
             * 是否是报告撰写人
             */
            if (report.getDrawUser()!=null && currentUser.equals(report.getDrawUser().getId())) {
                return true;
            }

            /**
             * 是否是报告审核人
             */
            if (report.getAuditUser()!=null && currentUser.equals(report.getAuditUser().getId())) {
                return true;
            }

            /**
             * 是否是报告批准人
             */
            if (report.getApprovalUser()!=null && currentUser.equals(report.getApprovalUser().getId())) {
                return true;
            }
        }

        return false;
    }
}
