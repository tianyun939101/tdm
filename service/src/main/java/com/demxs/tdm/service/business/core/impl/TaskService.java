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
 * ????????????
 * User: wuliepeng
 * Date: 2017-11-09
 * Time: ??????4:45
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
     * ????????????
     * @param entrustId ?????????ID
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
     * ????????????
     *  ????????????????????????????????????:
     *   1.??????????????????,?????????????????????????????????????????????,?????????????????????,??????????????????????????????,?????????????????????????????????????????????????????????
     *   2.????????????,????????????????????????????????????,??????????????????????????????,??????????????????????????????????????????????????????,?????????????????????????????????
     * @param entrustId ?????????ID
     * @param planId ??????ID
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
            /*?????????????????????????????????*/
                List<EntrustTestGroup> testGroups = entrustTestGroupService.listByEntrustId(entrustId);
                for (EntrustTestGroup testGroup : testGroups) {
                    if(null!=testGroup.getDisableFlag() && testGroup.getDisableFlag().equals(1)){
                        continue;
                    }
                    /*String testGroupId = testGroup.getId();
                    Integer abilityType = testGroup.getAbilityType();*/
                    TestPlan testPlan = testGroup.getTestPlan();
                    if (testPlan == null) {
                        throw new ServiceException("???"+(testGroup.getSort()+1)+"????????????????????????");
                    }
                    List<TestPlanDetail> details = testPlanDetailService.listByPlan(testPlan.getId());
                    for (TestPlanDetail testPlanDetail : details){
                        testPlanDetail.setTestPlan(testPlan);
                    }

                    /*????????????????????????*/
                    TestTaskExecution superExecution = new TestTaskExecution();
                    superExecution.setParentId(SysConstants.ROOT);
                    superExecution.setSuperExec(SysConstants.ROOT);
                    superExecution.setBusinessKey(entrustCode);
                    //superExecution.setIsActive(SysConstants.YES_NO.YES);
                    superExecution.setStatus(EntrustConstants.ExecutionStatus.EXECUTING);
                    /**
                     * ???????????????????????????
                     * 1.?????????????????????????????????
                     * 2.???????????????????????????????????????????????????????????????????????????????????????
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
                     * ?????????????????????
                     */
                    Comparator<TestPlanDetail> comparator = new Comparator<TestPlanDetail>() {
                        @Override
                        public int compare(TestPlanDetail s1, TestPlanDetail s2) {
                            return s1.getSort() - s2.getSort();
                        }
                    };
                    TestPlanDetail detail = getChildDetail(details,null);
                    sortDetail(detail,comparator);
                /*???????????????????????????*/
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
     * ?????????????????????
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
     * ?????????????????????,??????????????????????????????????????????
     * @param detail ??????????????????
     * @param businessKey   ????????????
     * @param superExecId   ?????????ID
     * @param executionId   ????????????ID
     */
    private void instanceBranch(TestPlanDetail detail,String businessKey,Integer testGroupAbilityType,String superExecId,String executionId,Boolean
            isRoot) {
        for (TestPlanDetail planDetail : detail.getChildDetail()) {
            //?????????????????????/??????
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

                //????????????
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
     * ????????????????????????
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
                // TODO: 17/11/10 ????????????????????????
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
     * ????????????
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
                throw new ServiceException("???????????????");
            }
            if(task.getStatus() >= EntrustConstants.TaskStatus.DONE){
                throw new ServiceException("???????????????");
            }
            if (EntrustConstants.TaskStatus.ASSIGN >= task.getStatus()) {
                /*??????????????????*/
                setTaskStatus(task);
            }
            /*???????????????*/
            //addSampleOutLib(task);
            /*?????????????????????*/
            task.setOwner("," + userIds + ",");
            task.setOwnerName(userNames);
            testTaskService.save(task);
            /*???????????????*/
            startSubTask(task);

            TestTaskAssignUser perTaskAssignUser = testTaskAssignUserService.getByLast(taskId);
            if(perTaskAssignUser!=null){
                perTaskAssignUser.setEndDate(new Date());
                testTaskAssignUserService.save(perTaskAssignUser);
            }
            //??????????????????????????????
            TestTaskAssignUser taskAssignUser = new TestTaskAssignUser();
            taskAssignUser.setTaskId(taskId);
            taskAssignUser.setUserId(task.getOwner());
            taskAssignUser.setUserName(task.getOwnerName());
            taskAssignUser.setStartDate(new Date());
            testTaskAssignUserService.save(taskAssignUser);

            //??????????????????????????????
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
     * ???????????????????????????
     * @param parentTask    ?????????
     */
    private void startSubTask(TestTask parentTask) throws ServiceException{
        List<TestTask> subTasks = testTaskService.listByParent(parentTask.getId());
       /* TestTask firstTask = getFirstTask(subTasks);
        if(firstTask == null){
            throw new ServiceException("??????????????????");
        }
        firstTask.setStatus(EntrustConstants.TaskStatus.TODO);
        //firstTask.setStartTime(new Date());
        firstTask.setOwner(parentTask.getOwner());
        firstTask.setOwnerName(parentTask.getOwnerName());
        testTaskService.save(firstTask);*/

       //??????????????????????????????????????? ????????????????????????????????????
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
     * ??????????????????
     * 1.?????????????????????????????????????????????
     * 2.????????????????????????????????????
     * 3.??????????????????????????????,?????????????????????????????????
     * 4.????????????????????????????????????,?????????????????????????????????
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
     * ????????????????????????
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
     * ??????????????????????????????????????????
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
     * ????????????
     * @param taskId ??????ID
     * @param taskUser  ??????ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void startTask(String taskId, String taskUser) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null){
                throw new ServiceException("???????????????");
            }
            if (StringUtils.isEmpty(taskUser)) {
                throw new ServiceException("????????????????????????");
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
     * ????????????
     * @param taskId ??????ID
     * @param taskUser  ??????ID
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
     * ????????????
     * @param taskId    ??????ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void recoverTask(String taskId,String taskUser) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("???????????????");
            }
            if (StringUtils.isEmpty(taskUser)) {
                throw new ServiceException("????????????????????????");
            }

            task.setStatus(EntrustConstants.TaskStatus.EXECUTING);
            testTaskService.save(task);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * ????????????
     *  e1(a,b,c)
     *      e2(d,e,f)
     *      e3(h,m,n)
     *  b??????-->c(?????????-->?????????,?????????-->?????????)
     *  c??????-->e2(?????????-->?????????)
     *              d(?????????-->?????????,?????????-->?????????)
     *         e3(?????????-->?????????)
     *              h(?????????-->?????????,?????????-->?????????)
     *  f??????-->e2(?????????-->??????)
     *          e3(?????????-->?????????,??????-->(e1(?????????-->??????),?????????))
     * @param taskId    ??????ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void complateTask(String taskId) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("???????????????");
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
                //?????????????????? ???????????????????????? ?????????  ???????????????????????????
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
     * ?????????????????????????????????????????????????????????????????????
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
     * ???????????????,???????????????????????????
     * 1.??????????????????????????????????????????????????????,????????????????????????????????????????????????????????????,
     *   ??????????????????????????????????????????????????????????????????????????????,???????????????????????????
     * 2.????????????????????????????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????
     * @param task
     */
    private void nextTask(TestTask task){
        List<TestTask> executionTasks = testTaskService.listByExecution(task.getExecutionId());
        TestTask nextTask = getNextTask(executionTasks,task);
        if(nextTask == null || EntrustConstants.TaskStatus.STOP.equals(nextTask.getStatus())){//?????????????????????????????????????????????
            TestTaskExecution taskExecution = testTaskExecutionService.get(task.getExecutionId());
            List<TestTaskExecution> subExecutions = testTaskExecutionService.listByParent(taskExecution.getId());
            if (isExecutionsDone(subExecutions)) {//????????????
                if(isExecutionTasksDone(executionTasks)) {
                    taskExecution.setStatus(EntrustConstants.ExecutionStatus.DONE);
                    testTaskExecutionService.save(taskExecution);
                    doneParentExecution(taskExecution);
                }
            }
            if(!subExecutions.isEmpty()) {//??????????????????
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

                //????????????????????????
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
                //??????????????????????????????
                setProcessTaskAssignee(firstTask);
            }
        }
    }

    /**
     * ????????????????????????????????????
     * @param tasks
     * @return
     */
    private TestTask getFirstTask(List<TestTask> tasks){
        if(tasks.isEmpty()){
            throw new ServiceException("????????????????????????");
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
     * ????????????????????????
     * ???????????????????????????????????????????????????????????????,????????????
     * @param taskExecution
     */
    private void doneParentExecution(TestTaskExecution taskExecution){
        //??????????????????
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
                 * ????????????????????????????????????????????????????????????,??????????????????????????????
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
                        //????????????
                        actTaskService.apiDeleteProcess(entrustInfo.getId());
                        //????????????
                        SendTodoParam sendTodoParam = new SendTodoParam();
                        sendTodoParam.setType(2);
                        sendTodoParam.setSubject("????????????"+entrustInfo.getCode()+"???????????????????????????");
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
                            throw new ServiceException("??????????????????????????????????????????");
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

                        //?????? ??????????????????
                        //????????????????????????
                        Map<String, Object> vars = new HashMap<>();
                        Map<String, Object> model = new HashMap<>();
                        model.put("code", entrustInfo.getCode());
                        String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Report_Draw, model);
                        vars.put("message", title);
                        //??????????????????????????????
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
     * ?????????????????????????????????????????????
     * @param tasks ???????????????????????????
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
     * ?????????????????????????????????????????????
     * @param taskExecutions
     * @return
     */
    private boolean isExecutionsDone(List<TestTaskExecution> taskExecutions){
        for(TestTaskExecution taskExecution : taskExecutions) {
            //??????????????????
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
     * ?????????????????????????????????
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
        if (nextTask != null && EntrustConstants.TaskStatus.STOP.equals(nextTask.getStatus())) {//??????????????????
            return getNextTask(tasks, nextTask);
        }
        return nextTask;
    }

    /**
     * ????????????
     * @param taskId ??????ID
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void submitTask(String taskId,String fileIds) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("???????????????");
            }
            /*if(StringUtils.isEmpty(task.getOriginRecordId())){
                throw new ServiceException("???????????????????????????");
            }*/
            assetInfoService.saveAttachBusiness(taskId, fileIds);
            /*task.setStatus(EntrustConstants.TaskStatus.ORIGIN_RECORD_EXAMINE);
            task.setAuditUser(auditUser);
            testTaskService.save(task);*/
            //todo ?????????????????????
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
            //??????????????????????????????
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
     * ????????????
     * @param taskId ??????ID
     * @param fileIds  ????????????
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveTask(String taskId, String fileIds) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("???????????????");
            }
            if(task.getStatus() < EntrustConstants.TaskStatus.EXECUTING){
                task.setStatus(EntrustConstants.TaskStatus.EXECUTING);
            }
            if(task.getStartTime() == null){
                task.setStartTime(new Date());
            }
            testTaskService.save(task);
            assetInfoService.saveAttachBusiness(taskId, fileIds);
            // todo ?????????????????????????????? ????????????

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
                //???????????????
                try {
                    //String newFilePath = fileStore.saveFile(fileName, FileUtils.readFileToByteArray(new File(filePath)));
                    String newFilePath = fileStore.saveFile(s.toString(), SambaUtils.readFileToByteArray(filePath));
                    attachIds.add(assetInfoService.save(s.toString(), newFilePath,null).getId());
                } catch (IOException e) {
                    throw new ServiceException("??????????????????",e.getCause());
                } catch (Exception e) {
                    throw new ServiceException("??????????????????",e.getCause());
                }

            }
        }
        //????????????imgids?????????
        String addIds = StringUtils.join(attachIds.toArray(),",");
        if(StringUtils.isNotBlank(addIds)){
            if(!StringUtils.isEmpty(imgIds)){
                imgIds+=","+addIds;
            }else{
                imgIds +=addIds;
            }

        }
        //??????????????????imgids
        testTaskService.updateImgIds(taskId,imgIds);
    }

    /**
     * ?????????????????????????????????
     * @param taskId ??????ID
     * @return
     * @throws ServiceException
     */
    @Override
    public TestTask getSampleDetails(String taskId){
        try {
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("???????????????");
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
                throw new ServiceException("???????????????");
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
                throw new ServiceException("???????????????");
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
     * ????????????
     * @param taskId ??????ID
     * @param oldDeviceId ?????????ID
     * @param newDeviceId ?????????ID
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
                throw new ServiceException("???????????????");
            }
            testTask.setDeviceId(testTask.getDeviceId().replace(oldDeviceId, newDeviceId));
            testTaskService.save(testTask);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * ????????????????????????
     * @param taskId ??????ID
     * @param originId ????????????ID
     * @return
     */
    @Override
    public void saveOriginRecord(String taskId, String originId) throws ServiceException{
        try {
            TestTask testTask = testTaskService.get(taskId);
            if (testTask == null) {
                throw new ServiceException("???????????????");
            }
            testTask.setOriginRecordId(originId);
            testTaskService.save(testTask);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }


    /**
     * ????????????
     * @param taskId ??????ID
     * @param auditInfo ????????????
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void auditTask(String taskId, AuditInfo auditInfo) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("???????????????");
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
                            //????????????????????????
                            AttachmentInfo attachmentInfo = attachmentInfoService.get(task.getOriginRecordId());
                            PoiExcelUtil.setNameValue(Global.getConfig("sourceDir").toString()+attachmentInfo.getPath(),cellNames,cellValues);
                            //todo ???????????? ???????????? ???????????????????????? ????????????

                        }
                    }
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * ????????????
     * @param taskId ??????ID
     * @param reason ????????????
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void stopTask(String taskId, String reason) throws ServiceException {
        try {
            TestTask task = testTaskService.get(taskId);
            if (task == null) {
                throw new ServiceException("???????????????");
            }
            task.setStatus(EntrustConstants.TaskStatus.STOP);
            task.setStopReason(reason);
            testPlanDetailService.deleteById(task.getPlanDetailId());
            /*??????????????????????????????????????????*/
            testTaskService.stopSubTask(task.getId());
            //???????????? ??????
            actTaskService.apiDeleteProcess(task.getTaskCode());
            testTaskService.save(task);
            nextTask(task);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }

    /**
     * ??????????????????
     * @param taskId    ??????ID
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TestTaskAssignUser> taskAssignRecord(String taskId) throws ServiceException {
        return testTaskAssignUserService.listByTaskId(taskId);
    }

    /**
     * ??????????????????
     * @param page ????????????
     * @param taskParam ????????????
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<TaskVO> findPage(Page<TaskVO> page, TaskParam taskParam) throws ServiceException{
        return testTaskService.findPage(page,taskParam);
    }

    /**
     * ?????????????????????
     * @param page ????????????
     * @param testTask  ??????????????????
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<TestTask> listByFinsh(Page<TestTask> page, TestTask testTask) throws ServiceException {
        return testTaskService.findPage(page,testTask);
    }

    /**
     * ????????????????????????
     * @param page ????????????
     * @param testTask  ??????????????????
     * @return
     * @throws ServiceException
     *//*
    @Override
    public Page<TestTask> listByHistoryAssign(Page<TestTask> page,TestTask testTask) throws ServiceException{
        return testTaskService.findPage(page,testTask);
    }*/

    /**
     * ????????????
     * @param page  ????????????
     * @param testTask ??????????????????
     * @return
     * @throws ServiceException
     */
    @Override
    public Page<TestTask> list(Page<TestTask> page,TestTask testTask) throws ServiceException{
        return testTaskService.findPage(page,testTask);
    }

    /**
     * ???????????????
     * @param taskId ??????ID
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TestTask> listByDetail(String taskId) throws ServiceException{
        return testTaskService.findList(null);
    }

    /**
     * ??????????????????
     * @param taskId ??????ID
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
     * ????????????????????????
     * @param taskId ??????ID
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
            throw new ServiceException("?????????????????????");
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

        //??????????????????
        List<EntrustTestItemCodition> conditions = getEntrustTestItemCoditions(testItemPlanDetail, testItem);

        parentTask.setConditions(conditions);
        //??????????????????
        AuditInfo param = new AuditInfo();
        param.setBusinessKey(parentTask.getId());
        param.setType(EntrustConstants.AuditType.ORIGIN_RECORD);
        List<AuditInfo> auditInfos = auditInfoService.findList(param);
        TaskInfoVO taskInfo = new TaskInfoVO(entrustInfo,parentTask,testTask,plan,testPlanDetail,subTasks,testItemUnit);
        taskInfo.setAuditInfos(auditInfos);

        //??????????????????
        taskInfo.setChangeLogs(sysDataChangeLogDao.findByServiceId(taskId));
        return taskInfo;
    }

    /**
     * ????????????????????????
     * @param testItemPlanDetailList  labid, abilityId,  entrustAbilityId  ??????????????????ID
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
     * ????????????????????????
     * @param taskId ??????ID
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
