package com.demxs.tdm.service.business.core.impl;

import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.lab.LabTestItemDao;
import com.demxs.tdm.dao.lab.LabTestItemUnitDao;
import com.demxs.tdm.dao.sys.SysDataChangeLogDao;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.service.business.*;
import com.demxs.tdm.service.business.util.CodeUtil;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.resource.shebei.ShebeiService;
import com.demxs.tdm.service.resource.shebei.ShebeiShiyongjlService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.common.utils.zrutils.PoiExcelUtil;
import com.demxs.tdm.domain.ability.TestItemUnit;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.vo.TaskInfoVO;
import com.demxs.tdm.domain.business.vo.TaskParam;
import com.demxs.tdm.domain.business.vo.TaskVO;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.domain.lab.LabTestItemCondition;
import com.demxs.tdm.domain.lab.LabTestItemUnit;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiShiyongjl;
import com.demxs.tdm.comac.common.constant.SysConstants;
import com.demxs.tdm.comac.common.jdbc.utils.SambaUtils;
import com.demxs.tdm.service.business.*;
import com.demxs.tdm.service.business.core.ITaskService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 任务服务
 * User: wuliepeng
 * Date: 2017-11-09
 * Time: 下午4:45
 */
@Service("tdmTaskService")
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TaskService implements ITaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    public static final String ROLE_ENAME_REPORT_AUTHOR = "ReportAuthor";

    @Autowired
    private EntrustInfoService entrustInfoService;
    @Autowired
    private EntrustTestGroupService entrustTestGroupService;
    @Autowired
    private TestPlanService testPlanService;
    @Autowired
    private TestPlanDetailService testPlanDetailService;
    @Autowired
    private TestTaskService testTaskService;
    @Autowired
    private TestTaskExecutionService testTaskExecutionService;
    @Autowired
    private TestTaskAssignUserService testTaskAssignUserService;
    @Autowired
    private EntrustSampleGroupItemService entrustSampleGroupItemService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private ShebeiService shebeiService;
    @Autowired
    private AssetInfoService assetInfoService;
    @Autowired
    private LabTestItemDao labTestItemDao;
    @Autowired
    private LabTestItemUnitDao labTestItemUnitDao;
    @Autowired
    private EntrustTestItemCoditionService entrustTestItemCoditionService;
    @Resource
    private ActTaskService actTaskService;
    @Resource
    private SystemService systemService;
    @Resource
    private AttachmentInfoService attachmentInfoService;
    @Resource
    private EntrustReportService entrustReportService;
    @Autowired
    private ShebeiShiyongjlService shebeiShiyongjlService;
    @Autowired
    private FileStore fileStore;
    @Resource
    private SysDataChangeLogDao sysDataChangeLogDao;
    @Resource
    private ExternalApi externalApi;
    /**
     * 创建任务
     * @param entrustId 申请单ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void createTask(String entrustId) throws ServiceException {
        try {
            createTask(entrustId, null);
        }catch (Exception e){
            throw new ServiceException(e);
        }
    }

    /**
     * 创建任务
     *  创建任务主要做如下几件事:
     *   1.创建执行干道,执行干道又分为主干道和分支干道,主干道只有一条,分支干道有上下级之分,执行干道的主要作为是为了驱动任务的执行
     *   2.创建任务,所有任务都属于某一个干道,任务也可以包含子任务,父任务主要的作为是为了分配任务执行人,任务实际执行的是子任务
     * @param entrustId 申请单ID
     * @param planId 计划ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void createTask(String entrustId, String planId) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            String entrustCode = entrustInfo.getCode();
            int initTaskNo = 1;
            if (StringUtils.isEmpty(planId)) {
            /*获取申请单中所有试验组*/
                List<EntrustTestGroup> testGroups = entrustTestGroupService.listByEntrustId(entrustId);
                for (EntrustTestGroup testGroup : testGroups) {
                    if(null!=testGroup.getDisableFlag() && testGroup.getDisableFlag().equals(1)){
                        continue;
                    }
                    /*String testGroupId = testGroup.getId();
                    Integer abilityType = testGroup.getAbilityType();*/
                    TestPlan testPlan = testGroup.getTestPlan();
                    if (testPlan == null) {
                        throw new ServiceException("第"+(testGroup.getSort()+1)+"个试验未编制计划");
                    }
                    List<TestPlanDetail> details = testPlanDetailService.listByPlan(testPlan.getId());
                    for (TestPlanDetail testPlanDetail : details){
                        testPlanDetail.setTestPlan(testPlan);
                    }

                    /*初始化主执行干道*/
                    TestTaskExecution superExecution = new TestTaskExecution();
                    superExecution.setParentId(SysConstants.ROOT);
                    superExecution.setSuperExec(SysConstants.ROOT);
                    superExecution.setBusinessKey(entrustCode);
                    //superExecution.setIsActive(SysConstants.YES_NO.YES);
                    superExecution.setStatus(EntrustConstants.ExecutionStatus.EXECUTING);
                    /**
                     * 设置主执行干道名称
                     * 1.如果为序列则为序列名称
                     * 2.如果为自定义序列或试验项目则为所有试验项目的名称以逗号分隔
                     */
                    /*if (EntrustConstants.Ability_Type.SEQUENCE.equals(abilityType)) {
                        EntrustTestGroupAbility ability = entrustTestGroupAbilityService.listByTestGroupId(testGroupId).get(0);
                        superExecution.setName(ability.getSeqName());
                    } else {
                        List<EntrustTestGroupAbility> abilitys = entrustTestGroupAbilityService.listByTestGroupId(testGroupId);
                        StringBuilder nameBuilder = new StringBuilder();
                        for (EntrustTestGroupAbility ability : abilitys) {
                            nameBuilder.append(ability.getItemName()).append(",");
                        }
                        superExecution.setName(nameBuilder.substring(0, nameBuilder.length() - 1));
                    }*/
                    testTaskExecutionService.save(superExecution);
                    /**
                     * 结构化试验计划
                     */
                    Comparator<TestPlanDetail> comparator = new Comparator<TestPlanDetail>() {
                        @Override
                        public int compare(TestPlanDetail s1, TestPlanDetail s2) {
                            return s1.getSort() - s2.getSort();
                        }
                    };
                    TestPlanDetail detail = getChildDetail(details,null);
                    sortDetail(detail,comparator);
                /*初始化所有执行干道*/
                    String superExecutionId = superExecution.getId();
                    instanceBranch(detail, entrustCode,testGroup.getAbilityType(), superExecutionId, superExecutionId, true);
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw  new ServiceException(e);
        }
    }

    private void sortDetail(TestPlanDetail detail,Comparator<TestPlanDetail> comparator){
        if(detail.getChildDetail() == null && detail.getChildDetail().isEmpty()){
            return;
        }
        Collections.sort(detail.getChildDetail(),comparator);
        for (TestPlanDetail childDetail : detail.getChildDetail()){
            sortDetail(childDetail,comparator);
        }
    }

    /**
     * 结构化试验计划
     * @param details
     * @param parent
     * @return
     */
    private TestPlanDetail getChildDetail(List<TestPlanDetail> details,TestPlanDetail parent){
        if(parent==null) {
            parent = new TestPlanDetail(SysConstants.ROOT);
        }
        for (TestPlanDetail detail : details){
            if(detail.getParentId().equals(parent.getId())){
                parent.getChildDetail().add(detail);
                if(EntrustConstants.Ability_Type.SEQUENCE.equals(detail.getAbilityType()) || EntrustConstants.Ability_Type.ITEM.equals(detail
                        .getAbilityType())){
                    getChildDetail(details,detail);
                }
            }
        }
        return parent;
    }

    /**
     * 初始化执行干道,每个分支为一个独立的执行干道
     * @param detail 试验计划详情
     * @param businessKey   业务主键
     * @param superExecId   总执行ID
     * @param executionId   上级执行ID
     */
    private void instanceBranch(TestPlanDetail detail,String businessKey,Integer testGroupAbilityType,String superExecId,String executionId,Boolean
            isRoot) {
        for (TestPlanDetail planDetail : detail.getChildDetail()) {
            //试验能力为序列/分支
            if (EntrustConstants.Ability_Type.SEQUENCE.equals(planDetail.getAbilityType())) {
                TestTaskExecution execution = new TestTaskExecution();
                execution.setBusinessKey(businessKey);
                execution.setSuperExec(superExecId);
                execution.setParentId(executionId);
                execution.setName(planDetail.getAbilityName());
                //execution.setIsActive(SysConstants.YES_NO.NO);
                execution.setPlanDetailId(planDetail.getId());
                execution.setStatus(isRoot?EntrustConstants.ExecutionStatus.EXECUTING:EntrustConstants.ExecutionStatus.NOSTART);
                testTaskExecutionService.save(execution);
                instanceBranch(planDetail, businessKey, testGroupAbilityType, superExecId, execution.getId(),false);
            } else if (EntrustConstants.Ability_Type.ITEM.equals(planDetail.getAbilityType())) {
                TestTask testTask = new TestTask();
                testTask.setParentId(SysConstants.ROOT);
                testTask.setExecutionId(executionId);
                testTask.setBusinessKey(businessKey);
                testTask.setTaskName(planDetail.getAbilityName());
                testTask.setSampleId(planDetail.getSampleId());
                testTask.setDeviceId(planDetail.getDeviceId());
                testTask.setPlanDetailId(planDetail.getId());
                testTask.setTaskCode(CodeUtil.genTaskCode(planDetail.getTestPlan().getCode()));
                testTask.setSort(EntrustConstants.Ability_Type.ITEM.equals(testGroupAbilityType)?1:planDetail.getSort());
                testTask.setStatus(EntrustConstants.TaskStatus.NOASSIGN);
                System.out.println(testTask.getTaskCode());
                testTaskService.save(testTask);
                initSubTask(planDetail.getChildDetail(),testTask,businessKey);
                testTaskService.save(testTask);

                //启动流程
                String taskCode = testTask.getTaskCode();
                Map<String,Object> model = new HashMap<>();
                model.put("taskCode", taskCode);
                String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Task_Execute,model);
                Map<String,Object> vars = new HashMap<>();
                vars.put("message", title);
                vars.put("taskCode", taskCode);
                vars.put("code", testTask.getBusinessKey());
                EntrustInfo entrustInfo = entrustInfoService.get(planDetail.getEntrustId());
                actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.SHIYANRW,"", taskCode,entrustInfo.getUser().getName()+"("+entrustInfo.getCode()+")-"+testTask.getTaskName()+"("+ testTask.getTaskCode()+")",vars);
            }
        }
    }

    /**
     * 初始化试验项任务
     * @param details
     * @param parentTask
     * @param businessKey
     */
    private void initSubTask(List<TestPlanDetail> details,TestTask parentTask,String businessKey){
        int initTaskNo = 1;
        for (TestPlanDetail planDetail : details){
            if (EntrustConstants.Ability_Type.UNIT.equals(planDetail.getAbilityType())) {
                TestTask testTask = new TestTask();
                testTask.setParentId(parentTask.getId());
                testTask.setBusinessKey(businessKey);
                testTask.setTaskName(planDetail.getAbilityName());
                testTask.setSampleId(parentTask.getSampleId());
                testTask.setDeviceId(planDetail.getDeviceId());
                testTask.setPlanDetailId(planDetail.getId());
                // TODO: 17/11/10 编号生成做成配置
                testTask.setTaskCode(IdGen.getSubSeqNo(parentTask.getTaskCode(),initTaskNo, 3));
                testTask.setSort(planDetail.getSort());
                testTask.setStatus(EntrustConstants.TaskStatus.NOASSIGN);
                testTaskService.save(testTask);
                if(initTaskNo == 1){
                    parentTask.setCurrentTask(testTask);
                }
                initTaskNo++;
            }
        }
    }

    /**
     * 分配任务
     * @param taskId
     * @param userIds
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public TestTask assignTask(String taskId, String userIds,String userNames) throws ServiceException {
        TestTask task = testTaskService.get(taskId);
        try {
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            if(task.getStatus() >= EntrustConstants.TaskStatus.DONE){
                throw new ServiceException("任务已结束");
            }
            if (EntrustConstants.TaskStatus.ASSIGN >= task.getStatus()) {
                /*设置任务状态*/
                setTaskStatus(task);
            }
            /*样品库接口*/
            //addSampleOutLib(task);
            /*设置任务执行人*/
            task.setOwner("," + userIds + ",");
            task.setOwnerName(userNames);
            testTaskService.save(task);
            /*启动子任务*/
            startSubTask(task);

            TestTaskAssignUser perTaskAssignUser = testTaskAssignUserService.getByLast(taskId);
            if(perTaskAssignUser!=null){
                perTaskAssignUser.setEndDate(new Date());
                testTaskAssignUserService.save(perTaskAssignUser);
            }
            //记录任务人员分配情况
            TestTaskAssignUser taskAssignUser = new TestTaskAssignUser();
            taskAssignUser.setTaskId(taskId);
            taskAssignUser.setUserId(task.getOwner());
            taskAssignUser.setUserName(task.getOwnerName());
            taskAssignUser.setStartDate(new Date());
            testTaskAssignUserService.save(taskAssignUser);

            //流程中的任务更改人员
            if (EntrustConstants.TaskStatus.TODO == task.getStatus() || EntrustConstants.TaskStatus.EXECUTING == task.getStatus()) {
                setProcessTaskAssignee(task);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
        return task;
    }

    private void setProcessTaskAssignee(TestTask task) {
        Map<String,Object> model = new HashMap<>();
        model.put("taskCode", task.getTaskCode());
        String message = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Task_Execute,model);
        actTaskService.apiClaim(task.getTaskCode(),getTaskOwnerLoginNames(task),message);
    }

    private String getTaskOwnerLoginNames(TestTask task) {
        List<String> userLoginNames = new ArrayList<>();
        for (String s : task.getOwner().split(",")) {
            if (StringUtils.isNotEmpty(s)) {
                userLoginNames.add(systemService.getUser(s).getLoginName());
            }
        }
        return StringUtils.join(userLoginNames,";");
    }

    /**
     * 设置子任务节点状态
     * @param parentTask    父任务
     */
    private void startSubTask(TestTask parentTask) throws ServiceException{
        List<TestTask> subTasks = testTaskService.listByParent(parentTask.getId());
       /* TestTask firstTask = getFirstTask(subTasks);
        if(firstTask == null){
            throw new ServiceException("未找到子任务");
        }
        firstTask.setStatus(EntrustConstants.TaskStatus.TODO);
        //firstTask.setStartTime(new Date());
        firstTask.setOwner(parentTask.getOwner());
        firstTask.setOwnerName(parentTask.getOwnerName());
        testTaskService.save(firstTask);*/

       //子任务所有的状态改成待执行 执行人变成父任务的分配人
       if(CollectionUtils.isNotEmpty(subTasks)){
           for(TestTask task:subTasks){
               task.setStatus(EntrustConstants.TaskStatus.TODO);
               //task.setStartTime(new Date());
               task.setOwner(parentTask.getOwner());
               task.setOwnerName(parentTask.getOwnerName());
               testTaskService.save(task);
           }
       }
    }

    /**
     * 设置任务状态
     * 1.根据任务获取任务所在的执行干道
     * 2.获取执行干道上的所有任务
     * 3.如果有前置任务未完成,则设置任务状态为已分配
     * 4.如果所有前置任务都已完成,则设置任务状态为待执行
     * @param task
     * @throws ServiceException
     */
    private void setTaskStatus(TestTask task) throws ServiceException{
        if(task == null){
            return;
        }
        String executionId = task.getExecutionId();
        List<TestTask> tasks = testTaskService.listByExecution(executionId);
        TestTaskExecution taskExecution = testTaskExecutionService.get(executionId);
        if(isPreTaskDone(tasks,task)){
            if(taskExecution.getStatus().equals(EntrustConstants.ExecutionStatus.EXECUTING)){
                task.setStatus(EntrustConstants.TaskStatus.TODO);
            }else{
                task.setStatus(EntrustConstants.TaskStatus.ASSIGN);
            }
        }else{
            task.setStatus(EntrustConstants.TaskStatus.ASSIGN);
        }
    }

    /**
     * 添加样品入库信息
     * @param task
     */
   /* private void addSampleOutLib(TestTask task){
        List<EntrustSampleGroupItem> sampleList = entrustSampleGroupItemService.findByIds(task.getSampleId());
        List<Sampleinfo> sampleinfos = new ArrayList<>();
        for (EntrustSampleGroupItem item : sampleList){
            Sampleinfo sampleinfo = new Sampleinfo();
            sampleinfo.setSn(item.getSn());
            sampleinfo.setCode(item.getSn());
            sampleinfos.add(sampleinfo);
        }
        EntrustInfo entrustInfo = entrustInfoService.getByCode(task.getBusinessKey());
        SampleTask sampleTask = new SampleTask(task.getId(),task.getTaskCode(),task.getTaskName(),task.getBusinessKey(),entrustInfo.getId(),task
                .getOwner(),task.getOwnerName(),sampleinfos);
        sampleTaskService.save(sampleTask);
    }*/

    /**
     * 判断前置任务是否都已执行完成
     * @param tasks
     * @param own
     * @return
     */
    private boolean isPreTaskDone(List<TestTask> tasks,TestTask own){
        for(TestTask task:tasks){
            if(task.getSort() < own.getSort() && !EntrustConstants.TaskStatus.DONE.equals(task.getStatus()) && !EntrustConstants.TaskStatus.STOP.equals(task.getStatus())){
                return false;
            }
        }
        return true;
    }

    /**
     * 开始任务
     * @param taskId 任务ID
     * @param taskUser  用户ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void startTask(String taskId, String taskUser) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null){
                throw new ServiceException("任务不存在");
            }
            if (StringUtils.isEmpty(taskUser)) {
                throw new ServiceException("任务执行人不存在");
            }
            task.setStatus(EntrustConstants.TaskStatus.EXECUTING);
            task.setStartTime(new Date());
            testTaskService.save(task);
            TestTask parentTask = testTaskService.get(task.getParentId());
            parentTask.setStatus(EntrustConstants.TaskStatus.EXECUTING);
            testTaskService.save(parentTask);
            /*List<TestTask> subTasks = testTaskService.listByParent(task.getParent());
            TestTask firstTask = getFirstTask(subTasks);
            if(taskId.equals(firstTask.getId())){
                TestTask parentTask = testTaskService.get(task.getParent());
                parentTask.setStatus(EntrustConstants.TaskStatus.EXECUTING);
                parentTask.setStartTime(new Date());
                testTaskService.save(parentTask);
            }*/
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 暂停任务
     * @param taskId 任务ID
     * @param taskUser  用户ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void suspendTask(String taskId, String taskUser) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null || StringUtils.isEmpty(taskUser)) {
                return;
            }
            task.setStatus(EntrustConstants.TaskStatus.SUSPEND);
            testTaskService.save(task);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 恢复执行
     * @param taskId    任务ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void recoverTask(String taskId,String taskUser) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            if (StringUtils.isEmpty(taskUser)) {
                throw new ServiceException("任务执行人不存在");
            }

            task.setStatus(EntrustConstants.TaskStatus.EXECUTING);
            testTaskService.save(task);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 完成任务
     *  e1(a,b,c)
     *      e2(d,e,f)
     *      e3(h,m,n)
     *  b完成-->c(待分配-->待分配,已分配-->待执行)
     *  c完成-->e2(未开始-->执行中)
     *              d(待分配-->待分配,已分配-->待执行)
     *         e3(未开始-->执行中)
     *              h(待分配-->待分配,已分配-->待执行)
     *  f完成-->e2(执行中-->完成)
     *          e3(执行中-->执行中,完成-->(e1(执行中-->完成),出报告))
     * @param taskId    任务ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void complateTask(String taskId) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            List<TestTask> subTasks = testTaskService.listByParent(task.getParentId());
            TestTask parentTask = testTaskService.get(task.getParentId());
            TestTask firstTask = getFirstTask(subTasks);
            if(taskId.equals(firstTask.getId())){
                parentTask.setStatus(EntrustConstants.TaskStatus.EXECUTING);
                parentTask.setStartTime(new Date());
                testTaskService.save(parentTask);
            }

            TestTask nextSubTask = getNextTask(subTasks, task);
            if (nextSubTask == null) {
                //parentTask.setStatus(EntrustConstants.TaskStatus.DONE);
                parentTask.setStatus(EntrustConstants.TaskStatus.ORIGIN_RECORD_EXAMINE);
                //parentTask.setEndTime(new Date());
                //parentTask.setCurrentTask(new TestTask());
                testTaskService.save(parentTask);
                //最后一个提交 添加一条提交记录 待审核  暂时放在审核表里面
                //  todo
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setBusinessKey(parentTask.getId());
                auditInfo.setType(EntrustConstants.AuditType.ORIGIN_RECORD);
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setResult(EntrustConstants.AuditResult.APPLY);
                auditInfo.setReason(EntrustConstants.MessageTemplate.APPLY_TASK);
                auditInfoService.save(auditInfo);
                //nextTask(parentTask);
                EntrustInfo entrustInfo = entrustInfoService.getByCode(task.getBusinessKey());
                User auditUser = entrustInfo.getTestCharge();
                auditUser = systemService.getUser(auditUser.getId());
                originRecordAudit(parentTask,EntrustConstants.AuditResult.PASS,auditUser.getLoginName(),EntrustConstants.MessageTemplate.Task_Audit,"");
            } else {
                parentTask.setCurrentTask(nextSubTask);
                testTaskService.save(parentTask);

                nextSubTask.setStatus(EntrustConstants.TaskStatus.TODO);
                nextSubTask.setOwner(task.getOwner());
                nextSubTask.setOwnerName(task.getOwnerName());
                testTaskService.save(nextSubTask);
            }
            TestTaskAssignUser perTaskAssignUser = testTaskAssignUserService.getByLast(taskId);
            if(perTaskAssignUser!=null){
                perTaskAssignUser.setEndDate(new Date());
                testTaskAssignUserService.save(perTaskAssignUser);
            }
            task.setStatus(EntrustConstants.TaskStatus.DONE);
            if(StringUtils.isEmpty(task.getOriginRecordId())){
                task.setStartTime(new Date());
            }
            task.setEndTime(new Date());
            testTaskService.save(task);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     *
     * 原始记录流程审核，完成并设置下一步为试验负责人
     * @param task
     */
    private void originRecordAudit(TestTask task, Integer pass,String assignee,String message,String comment) {
        Map<String, Object> vars = new HashMap<>();
        Map<String, Object> model = new HashMap<>();
        model.put("taskCode", task.getTaskCode());
        message = FreeMarkers.renderString(message, model);
        vars.put("message", message);
        actTaskService.apiComplete(task.getTaskCode(), comment, pass+"", assignee, vars);
    }

    /**
     * 任务完成后,执行下一个任务状态
     * 1.判断任务是否为执行干道上最后一个任务,是则判断执行干道所有子干道是否都已经完成,
     *   如果所有子干道都已经完成则将任务所在干道状态置为完成,并且设置父干道状态
     * 2.设置所有子干道与子干道中第一个任务的状态
     * 先按序执行所有同级任务，再并行执行所有同级分支
     * @param task
     */
    private void nextTask(TestTask task){
        List<TestTask> executionTasks = testTaskService.listByExecution(task.getExecutionId());
        TestTask nextTask = getNextTask(executionTasks,task);
        if(nextTask == null || EntrustConstants.TaskStatus.STOP.equals(nextTask.getStatus())){//同级任务执行完成，并行执行分支
            TestTaskExecution taskExecution = testTaskExecutionService.get(task.getExecutionId());
            List<TestTaskExecution> subExecutions = testTaskExecutionService.listByParent(taskExecution.getId());
            if (isExecutionsDone(subExecutions)) {//没有分支
                if(isExecutionTasksDone(executionTasks)) {
                    taskExecution.setStatus(EntrustConstants.ExecutionStatus.DONE);
                    testTaskExecutionService.save(taskExecution);
                    doneParentExecution(taskExecution);
                }
            }
            if(!subExecutions.isEmpty()) {//启动所有分支
                for(TestTaskExecution execution : subExecutions){
                    execution.setStatus(EntrustConstants.ExecutionStatus.EXECUTING);
                    testTaskExecutionService.save(execution);
                    startExecution(execution);
                }
            }
        }else{
            if(EntrustConstants.TaskStatus.ASSIGN.equals(nextTask.getStatus())){
                nextTask.setStatus(EntrustConstants.TaskStatus.TODO);
                testTaskService.save(nextTask);

                //设置流程任务人员
                setProcessTaskAssignee(nextTask);
            }
        }
    }

    private void startExecution(TestTaskExecution execution){
        List<TestTask> subExecutionTasks = testTaskService.listByExecution(execution.getId());
        if (subExecutionTasks.isEmpty()){
            List<TestTaskExecution> subExecutions = testTaskExecutionService.listByParent(execution.getId());
            for (TestTaskExecution subTaskExecution : subExecutions){
                startExecution(subTaskExecution);
            }
        }else {
            TestTask firstTask = getFirstTask(subExecutionTasks);
            if (EntrustConstants.TaskStatus.ASSIGN.equals(firstTask.getStatus())) {
                firstTask.setStatus(EntrustConstants.TaskStatus.TODO);
                testTaskService.save(firstTask);
                //流程中的任务更改人员
                setProcessTaskAssignee(firstTask);
            }
        }
    }

    /**
     * 获取执行干道中第一个任务
     * @param tasks
     * @return
     */
    private TestTask getFirstTask(List<TestTask> tasks){
        if(tasks.isEmpty()){
            throw new ServiceException("没有找到下级任务");
        }
        for(int i=0;i<tasks.size();i++){
            for (int j=0;j<tasks.size()-1-i;j++){
                if(tasks.get(j).getSort()>tasks.get(j+1).getSort()){
                    TestTask temp = tasks.get(j);
                    tasks.set(j,tasks.get(j+1));
                    tasks.set(j+1,temp);
                }
            }
        }
        return tasks.get(0);
    }

    /**
     * 完成父级执行干道
     * 所有同级执行干道都完成则父级执行干道也完成,递归调用
     * @param taskExecution
     */
    private void doneParentExecution(TestTaskExecution taskExecution){
        //同级执行干道
        List<TestTaskExecution> peerExecutions = new ArrayList<>();
        if (!SysConstants.ROOT.equals(taskExecution.getParentId())) {
            peerExecutions = testTaskExecutionService.listByParent(taskExecution.getParentId());
        }
        if(isExecutionsDone(peerExecutions)){
            TestTaskExecution parentTaskExecution = testTaskExecutionService.get(taskExecution.getParentId());
            if(parentTaskExecution!=null) {
                parentTaskExecution.setStatus(EntrustConstants.ExecutionStatus.DONE);
                testTaskExecutionService.save(parentTaskExecution);
                doneParentExecution(parentTaskExecution);
            }else{
                /**
                 * 如果当前执行干道为主干道并且状态为已完成,则设置申请状态为完成
                 */
                if(SysConstants.ROOT.equals(taskExecution.getParentId()) && EntrustConstants.ExecutionStatus.DONE.equals(taskExecution.getStatus())){
                    List<TestTaskExecution> testTaskExecutions = testTaskExecutionService.listByBusinessKey(taskExecution.getBusinessKey());
                    for (TestTaskExecution execution : testTaskExecutions){
                        if(!EntrustConstants.ExecutionStatus.DONE.equals(execution.getStatus())){
                            return;
                        }
                    }
                    EntrustInfo entrustInfo = entrustInfoService.getByCode(taskExecution.getBusinessKey());
                    boolean flag = true;
                    List<TestTask> testTasks = testTaskService.listByBusinessKey(taskExecution.getBusinessKey());
                    for (TestTask testTask:testTasks){
                        if(!EntrustConstants.TaskStatus.STOP.equals(testTask.getStatus())){
                            flag = false;
                        }
                    }
                    if(flag){
                        entrustInfo.setStage(EntrustConstants.Stage.FINISH);
                        entrustInfo.setStatus(EntrustConstants.FinishStage.STOP);
                        entrustInfoService.save(entrustInfo);
                        //删除流程
                        actTaskService.apiDeleteProcess(entrustInfo.getId());
                        //发送通知
                        SendTodoParam sendTodoParam = new SendTodoParam();
                        sendTodoParam.setType(2);
                        sendTodoParam.setSubject("您申请单"+entrustInfo.getCode()+"的所有任务已经终止");
                        sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/a/tdm/business/entrust/detail?id="+entrustInfo.getId());
                        sendTodoParam.setCreateTime(new Date());
                        sendTodoParam.setModelId(IdGen.uuid());
                        sendTodoParam.addTarget(systemService.getUser(entrustInfo.getUser().getId()).getLoginName());
                        externalApi.sendTodo(sendTodoParam);
                        return;
                    }

                    if (entrustInfo.getReportFlag().equals(SysConstants.YES_NO.NO)){
                        entrustInfo.setStage(EntrustConstants.Stage.FINISH);
                        entrustInfo.setStatus(EntrustConstants.FinishStage.DONE);
                        entrustInfoService.save(entrustInfo);
                    }else {
                        entrustInfo.setStage(EntrustConstants.Stage.REPORT);
                        entrustInfo.setStatus(EntrustConstants.ReportStage.DRAW);
                        entrustInfoService.save(entrustInfo);

                        EntrustReport report = new EntrustReport();
                        report.setCode(entrustInfo.getCode());
                        report.setEntrustInfo(entrustInfo);
                        report.setLab(new LabInfo(entrustInfo.getLabId()));
                        report.setStatus(EntrustConstants.ReportStatus.DRAW);
                        List<User> reportAuthor = systemService.getUserByLabRoleExt(entrustInfo.getLabId(), ROLE_ENAME_REPORT_AUTHOR);
                        if (reportAuthor.isEmpty()) {
                            throw new ServiceException("所在试验室没有找到报告撰写人");
                        }
                        StringBuilder userNameSb = new StringBuilder();
                        StringBuilder userIdSb = new StringBuilder();
                        for (User user : reportAuthor) {
                            userIdSb.append(user.getId()).append(",");
                            userNameSb.append(user.getName()).append(",");
                        }
                        report.setOwner(userIdSb.substring(0, userIdSb.length() - 1));
                        report.setOwnerName(userNameSb.substring(0, userNameSb.length() - 1));
                        entrustReportService.save(report);

                        //流程 试验执行完成
                        //发起编制报告流程
                        Map<String, Object> vars = new HashMap<>();
                        Map<String, Object> model = new HashMap<>();
                        model.put("code", entrustInfo.getCode());
                        String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Report_Draw, model);
                        vars.put("message", title);
                        //试验室报告撰写人审核
                        //List<User> users = nuabsystemService.getUserByLabRole(entrustInfo.getLabId(), ROLE_ENAME_REPORT_AUTHOR);
                        List<String> userLoginNames = new ArrayList<>();
                        for (User u : reportAuthor) {
                            userLoginNames.add(u.getLoginName());
                        }
                        actTaskService.apiComplete(entrustInfo.getId(), "", Global.YES, StringUtils.join(userLoginNames, ";"), vars);
                    }
                }
            }
        }
    }

    /**
     * 执行干道所有任务是否都执行完成
     * @param tasks 执行干道上所有任务
     * @return
     */
    private boolean isExecutionTasksDone(List<TestTask> tasks){
        for(TestTask task : tasks) {
            if (!EntrustConstants.TaskStatus.DONE.equals(task.getStatus())  && !EntrustConstants.TaskStatus.STOP.equals(task.getStatus())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否所有下级执行干道都执行完成
     * @param taskExecutions
     * @return
     */
    private boolean isExecutionsDone(List<TestTaskExecution> taskExecutions){
        for(TestTaskExecution taskExecution : taskExecutions) {
            //子级执行干道
            List<TestTaskExecution> subExecutions = testTaskExecutionService.listByParent(taskExecution.getId());
            for (TestTaskExecution execution : subExecutions) {
                if (!EntrustConstants.ExecutionStatus.DONE.equals(execution.getStatus()) && !EntrustConstants.TaskStatus.STOP.equals(execution.getStatus())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取此任务的下一个任务
     * @param tasks
     * @param currentTask
     * @return
     */
    private TestTask getNextTask(List<TestTask> tasks,TestTask currentTask){
        TestTask nextTask = null;
        for (TestTask testTask : tasks){
            if(testTask.getSort()>currentTask.getSort()){
                if(nextTask == null) {
                    nextTask = testTask;
                }else if(nextTask.getSort() > testTask.getSort()){
                    nextTask = testTask;
                }
            }
        }
        if (nextTask != null && EntrustConstants.TaskStatus.STOP.equals(nextTask.getStatus())) {//跳过终止任务
            return getNextTask(tasks, nextTask);
        }
        return nextTask;
    }

    /**
     * 提交任务
     * @param taskId 任务ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void submitTask(String taskId,String fileIds) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            /*if(StringUtils.isEmpty(task.getOriginRecordId())){
                throw new ServiceException("任务未填写原始记录");
            }*/
            assetInfoService.saveAttachBusiness(taskId, fileIds);
            /*task.setStatus(EntrustConstants.TaskStatus.ORIGIN_RECORD_EXAMINE);
            task.setAuditUser(auditUser);
            testTaskService.save(task);*/
            //todo 原始记录单赋值
            String[] cellNames = new String[2];
            cellNames[0] = "user.name";
            cellNames[1] = "createDate";
            String[] cellValues = new String[2];
            cellValues[0] = UserUtils.getUser().getName();
            cellValues[1] = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
            List<TestTask> tasks = new ArrayList<TestTask>();
            tasks.add(task);
            setYsData(tasks,cellNames,cellValues);
            complateTask(taskId);
            //调用设备使用记录接口
            if(StringUtils.isNotBlank(task.getDeviceId())){
                for(String deviceId:task.getDeviceId().split(",")){
                    ShebeiShiyongjl shiyongjl = new ShebeiShiyongjl();
                    shiyongjl.setShebeiid(deviceId);
                    shiyongjl.setEntrustCode(task.getBusinessKey());
                    shiyongjl.setTask(task);
                    shiyongjl.setItemId(testTaskService.get(task.getParentId()).getId());
                    shiyongjl.setItemName(testTaskService.get(task.getParentId()).getTaskName());
                    shiyongjl.setSource(ShebeiShiyongjl.NO_SHOUDONG);
                    shebeiShiyongjlService.save(shiyongjl);
                }
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void submitTask(TestTask testTask) throws ServiceException {
        this.submitTask(testTask.getId(),testTask.getFileIds());
        this.saveImgIds(testTask.getId(),testTask.getImgIds(),testTask.getFilePath());
    }

    /**
     * 保存任务
     * @param taskId 任务ID
     * @param fileIds  任务数据
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveTask(String taskId, String fileIds) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            if(task.getStatus() < EntrustConstants.TaskStatus.EXECUTING){
                task.setStatus(EntrustConstants.TaskStatus.EXECUTING);
            }
            if(task.getStartTime() == null){
                task.setStartTime(new Date());
            }
            testTaskService.save(task);
            assetInfoService.saveAttachBusiness(taskId, fileIds);
            // todo 给原始记录赋值记录人 记录时间

            String[] cellNames = new String[2];
            cellNames[0] = "user.name";
            cellNames[1] = "createDate";
            String[] cellValues = new String[2];
            cellValues[0] = UserUtils.getUser().getName();
            cellValues[1] = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
            List<TestTask> tasks = new ArrayList<TestTask>();
            tasks.add(task);
            setYsData(tasks,cellNames,cellValues);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveTask(TestTask testTask) throws ServiceException {
        this.saveTask(testTask.getId(),testTask.getFileIds());
        this.saveImgIds(testTask.getId(),testTask.getImgIds(),testTask.getFilePath());

    }

    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveImgIds(String taskId,String imgIds,List<Map<String,String>> files){
        List<String> attachIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(files)){
            for(Map file:files){
                String fileName = file.get("fileName").toString();
                String filePath = file.get("filePath").toString();
                String fileDate = file.get("fileDate").toString();
                int i = fileName.lastIndexOf(".");
                StringBuilder s = new StringBuilder(fileName);
                s.insert(i,"_"+fileDate);
                //下载到本地
                try {
                    //String newFilePath = fileStore.saveFile(fileName, FileUtils.readFileToByteArray(new File(filePath)));
                    String newFilePath = fileStore.saveFile(s.toString(), SambaUtils.readFileToByteArray(filePath));
                    attachIds.add(assetInfoService.save(s.toString(), newFilePath,null).getId());
                } catch (IOException e) {
                    throw new ServiceException("文件下载异常",e.getCause());
                } catch (Exception e) {
                    throw new ServiceException("文件下载异常",e.getCause());
                }

            }
        }
        //拼装新的imgids里面去
        String addIds = StringUtils.join(attachIds.toArray(),",");
        if(StringUtils.isNotBlank(addIds)){
            if(!StringUtils.isEmpty(imgIds)){
                imgIds+=","+addIds;
            }else{
                imgIds +=addIds;
            }

        }
        //修改该任务的imgids
        testTaskService.updateImgIds(taskId,imgIds);
    }

    /**
     * 加载试验任务的样品详情
     * @param taskId 任务ID
     * @return
     * @throws ServiceException
     */
    @Override
    public TestTask getSampleDetails(String taskId){
        try {
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("任务不存在");
            }
            List<EntrustSampleGroupItem> sampleList = entrustSampleGroupItemService.findByIds(testTask.getSampleId());
            testTask.setSampleList(sampleList);
            EntrustInfo entrustInfo = entrustInfoService.getByCode(testTask.getBusinessKey());
            testTask.setSampleType(entrustInfo.getSampleType());
            return testTask;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTaskSample(String taskId, String sampleId) {
        try {
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("任务不存在");
            }
            String oldSampleId = testTask.getSampleId();
            if(oldSampleId.indexOf(sampleId+",") >= 0){
                testTask.setSampleId(oldSampleId.replace(sampleId+",",""));
            }else if(oldSampleId.indexOf(","+sampleId) >= 0){
                testTask.setSampleId(oldSampleId.replace(","+sampleId,""));
            }else if(oldSampleId.indexOf(sampleId) >= 0){
                testTask.setSampleId(oldSampleId.replace(sampleId,""));
            }
            testTaskService.save(testTask);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTaskSample(String taskId, String sampleId) {
        try {
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("任务不存在");
            }
            String oldSampleId = testTask.getSampleId();
            oldSampleId+=","+sampleId;
            testTask.setSampleId(oldSampleId);
            testTaskService.save(testTask);
        } catch (ServiceException e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 更改设备
     * @param taskId 设备ID
     * @param oldDeviceId 原设备ID
     * @param newDeviceId 新设备ID
     * @return
     */
    @Override
    public void changeDevice(String taskId, String oldDeviceId, String newDeviceId) throws ServiceException{
        try {
            if (oldDeviceId.equals(newDeviceId)) {
                return;
            }
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("任务不存在");
            }
            testTask.setDeviceId(testTask.getDeviceId().replace(oldDeviceId, newDeviceId));
            testTaskService.save(testTask);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 保存原始记录数据
     * @param taskId 设备ID
     * @param originId 原始记录ID
     * @return
     */
    @Override
    public void saveOriginRecord(String taskId, String originId) throws ServiceException{
        try {
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("任务不存在");
            }
            testTask.setOriginRecordId(originId);
            testTaskService.save(testTask);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }


    /**
     * 审核任务
     * @param taskId 任务ID
     * @param auditInfo 审核信息
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void auditTask(String taskId, AuditInfo auditInfo) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                task.setStatus(EntrustConstants.TaskStatus.DONE);
                task.setEndTime(new Date());
                task.setCurrentTask(new TestTask());
                //setYsAuditData(taskId);
                String[] cellNames = new String[2];
                cellNames[0] = "auditUser.name";
                cellNames[1] = "auditDate";
                String[] cellValues = new String[2];
                cellValues[0] = UserUtils.getUser().getName();
                cellValues[1] = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                setYsData(testTaskService.listByParent(task.getId()),cellNames,cellValues);
                testTaskService.save(task);
                nextTask(task);

                actTaskService.apiComplete(task.getTaskCode() ,auditInfo.getReason(), auditInfo.getResult()+"", null, null);
            } else {
                testTaskService.resetSubTask(taskId);
                task.setStatus(EntrustConstants.TaskStatus.EXECUTING);
                testTaskService.save(task);
                originRecordAudit(task,auditInfo.getResult(),getTaskOwnerLoginNames(task),EntrustConstants.MessageTemplate.Task_Return,auditInfo.getReason());
            }
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfo.setType(EntrustConstants.AuditType.ORIGIN_RECORD);
            auditInfo.setBusinessKey(taskId);
            auditInfoService.save(auditInfo);

        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }


    public void setYsData(List<TestTask> tasks,String[] cellNames,String[] cellValues){

        try {
            if(CollectionUtils.isNotEmpty(tasks)){
                    for(TestTask task:tasks){
                        if(StringUtils.isNotBlank(task.getOriginRecordId())){
                            //表示有原始记录单
                            AttachmentInfo attachmentInfo = attachmentInfoService.get(task.getOriginRecordId());
                            PoiExcelUtil.setNameValue(Global.getConfig("sourceDir").toString()+attachmentInfo.getPath(),cellNames,cellValues);
                            //todo 入库数据 表单数据 只入库这两个字段 单独处理

                        }
                    }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 终止任务
     * @param taskId 任务ID
     * @param reason 终止原因
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void stopTask(String taskId, String reason) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("任务不存在");
            }
            task.setStatus(EntrustConstants.TaskStatus.STOP);
            task.setStopReason(reason);
            testPlanDetailService.deleteById(task.getPlanDetailId());
            /*更新所有子任务的状态为已终止*/
            testTaskService.stopSubTask(task.getId());
            //终止任务 流程
            actTaskService.apiDeleteProcess(task.getTaskCode());
            testTaskService.save(task);
            nextTask(task);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * 任务分配记录
     * @param taskId    任务ID
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TestTaskAssignUser> taskAssignRecord(String taskId) throws ServiceException {
        return testTaskAssignUserService.listByTaskId(taskId);
    }

    /**
     * 任务分配列表
     * @param page 分页信息
     * @param taskParam 查询参数
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<TaskVO> findPage(Page<TaskVO> page, TaskParam taskParam) throws ServiceException{
        return testTaskService.findPage(page,taskParam);
    }

    /**
     * 已完成任务列表
     * @param page 分页信息
     * @param testTask  查询条件信息
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<TestTask> listByFinsh(Page<TestTask> page, TestTask testTask) throws ServiceException {
        return testTaskService.findPage(page,testTask);
    }

    /**
     * 任务历史分配列表
     * @param page 分页信息
     * @param testTask  查询条件信息
     * @return
     * @throws ServiceException
     *//*
    @Override
    public Page<TestTask> listByHistoryAssign(Page<TestTask> page,TestTask testTask) throws ServiceException{
        return testTaskService.findPage(page,testTask);
    }*/

    /**
     * 任务列表
     * @param page  分页信息
     * @param testTask 查询条件信息
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<TestTask> list(Page<TestTask> page,TestTask testTask) throws ServiceException{
        return testTaskService.findPage(page,testTask);
    }

    /**
     * 任务明细表
     * @param taskId 任务ID
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TestTask> listByDetail(String taskId) throws ServiceException{
        return testTaskService.findList(null);
    }

    /**
     * 任务详细信息
     * @param taskId 任务ID
     * @return
     * @throws ServiceException
     */
    @Override
    public TestTask get(String taskId) throws ServiceException{
        TestTask testTask = testTaskService.get(taskId);
        List<EntrustSampleGroupItem> sampleList = entrustSampleGroupItemService.findByIds(testTask.getSampleId());
        testTask.setSampleList(sampleList);
        if(!sampleList.isEmpty()){
            testTask.setItem(sampleList.get(0));
        }
        StringBuilder deviceCode = new StringBuilder();
        if(StringUtils.isNotBlank(testTask.getDeviceId())){
            List<Shebei> shebeis = shebeiService.findByIds(testTask.getDeviceId());
            for (Shebei shebei : shebeis){
                deviceCode.append(shebei.getShebeibh()).append(";");
            }
            testTask.setDeviceCodes(deviceCode.substring(0,deviceCode.length()-1));
        }
        return testTask;
    }

    /**
     * 加载试验任务信息
     * @param taskId 任务ID
     * @throws ServiceException
     */
    @Override
    public TaskInfoVO loadTaskInfo(String taskId) throws ServiceException{
        TestTask parentTask = testTaskService.get(taskId);
        TestPlanDetail testItemPlanDetail = testPlanDetailService.get(parentTask.getPlanDetailId());
        parentTask.setTestPlanDetail(testItemPlanDetail);
        List<TestTask> subTasks = testTaskService.listByParent(parentTask.getId());
        TestTask testTask = null;
        for (TestTask task : subTasks){
            if(EntrustConstants.TaskStatus.EXECUTING.equals(task.getStatus()) || EntrustConstants.TaskStatus.SUSPEND.equals(task.getStatus()) ||
                    EntrustConstants.TaskStatus.TODO.equals(task.getStatus())){
                testTask = task;
                break;
            }
        }
        /*if(testTask == null){
            throw new ServiceException("未找到执行任务");
        }

        testTask = this.getSampleDetails(testTask.getId());*/

        EntrustInfo entrustInfo = entrustInfoService.getByCode(parentTask.getBusinessKey());
        TestPlanDetail testPlanDetail = null;
        TestPlan plan = null;
        if(testTask != null) {
            testPlanDetail = testPlanDetailService.get(testTask.getPlanDetailId());
            plan = testPlanService.get(testPlanDetail.getPlanId());
            testTask = this.getSampleDetails(testTask.getId());
        }else{
            testPlanDetail = testPlanDetailService.get(parentTask.getPlanDetailId());
            plan = testPlanService.get(testPlanDetail.getPlanId());
        }
        LabTestItem testItem = labTestItemDao.getLabItem(entrustInfo.getLabId(),testItemPlanDetail.getAbilityId());
         List<LabTestItemUnit> testItemUnits = labTestItemUnitDao.findByItem(testItem.getId());

        TestItemUnit testItemUnit = null;
        for(LabTestItemUnit unit : testItemUnits){
            if(unit.getUnit().getId().equals(testPlanDetail.getAbilityId())){
                testItemUnit = unit;
                break;
            }
        }

        //获取试验条件
        List<EntrustTestItemCodition> conditions = getEntrustTestItemCoditions(testItemPlanDetail, testItem);

        parentTask.setConditions(conditions);
        //获取审核信息
        AuditInfo param = new AuditInfo();
        param.setBusinessKey(parentTask.getId());
        param.setType(EntrustConstants.AuditType.ORIGIN_RECORD);
        List<AuditInfo> auditInfos = auditInfoService.findList(param);
        TaskInfoVO taskInfo = new TaskInfoVO(entrustInfo,parentTask,testTask,plan,testPlanDetail,subTasks,testItemUnit);
        taskInfo.setAuditInfos(auditInfos);

        //获取调整记录
        taskInfo.setChangeLogs(sysDataChangeLogDao.findByServiceId(taskId));
        return taskInfo;
    }

    /**
     * 获取试验项目条件
     * @param testItemPlanDetailList  labid, abilityId,  entrustAbilityId  试验项目计划ID
     * @return
     */
    @Override
    public  List<List<EntrustTestItemCodition>> getEntrustTestItemConditions(List<String> testItemPlanDetailList) {
        List<List<EntrustTestItemCodition>> res = new ArrayList<>();
        for (String  id : testItemPlanDetailList) {
            TestPlanDetail testPlanDetail = testPlanDetailService.get(id);
            EntrustInfo entrustInfo = entrustInfoService.get(testPlanDetail.getEntrustId());
            LabTestItem testItem = labTestItemDao.getLabItem(entrustInfo.getLabId(),testPlanDetail.getAbilityId());
            res.add(getEntrustTestItemCoditions(testPlanDetail, testItem));
        }
        return res;
    }
    private List<EntrustTestItemCodition> getEntrustTestItemCoditions(TestPlanDetail testItemPlanDetail, LabTestItem testItem) {
        List<EntrustTestItemCodition> conditions;
        if (StringUtils.isNotEmpty(testItemPlanDetail.getEntrustAbilityId())) {
            conditions = entrustTestItemCoditionService.listByEntrustAbilityId(testItemPlanDetail.getEntrustAbilityId());
        } else {
            conditions = new ArrayList<>();
            for (LabTestItemCondition labTestItemCondition : testItem.getLabTestItemConditions()) {
                EntrustTestItemCodition c = new EntrustTestItemCodition();
                c.setEntrustId(testItemPlanDetail.getEntrustId());
                c.setItemId(testItemPlanDetail.getAbilityId());
                c.setParameter(labTestItemCondition.getParameter());
                c.setTestCondition(labTestItemCondition.getCondition());
                c.setTGroupId(testItemPlanDetail.gettGroupId());
                conditions.add(c);
            }
        }
        return conditions;
    }

    /**
     * 加载试验任务信息
     * @param taskId 任务ID
     * @throws ServiceException
     */
    @Override
    public TaskInfoVO loadSubTaskInfo(String taskId) throws ServiceException{
        TestTask task = testTaskService.get(taskId);
        TestPlanDetail testPlanDetail = testPlanDetailService.get(task.getPlanDetailId());
        task.setTestPlanDetail(testPlanDetail);
        TestPlan plan = testPlanService.get(testPlanDetail.getPlanId());
        EntrustInfo entrustInfo = entrustInfoService.getByCode(task.getBusinessKey());
//        List<Shebei> shebeis = shebeiService.findByIds(testPlanDetail.getDeviceId());
//        testPlanDetail.setDeviceList(shebeis);
        task = this.getSampleDetails(task.getId());
        TaskInfoVO taskInfo = new TaskInfoVO(entrustInfo,task,null,plan,testPlanDetail,null,null);
        return taskInfo;
    }


}
