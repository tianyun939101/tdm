package com.demxs.tdm.service.oa.service;


import com.demxs.tdm.comac.common.act.utils.ActUtils;
import com.demxs.tdm.comac.common.act.utils.ProcessDefCache;
import com.demxs.tdm.comac.common.act.utils.ProcessDefUtils;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.BaseService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.oa.ActDao;
import com.demxs.tdm.domain.external.DeleteTodoParam;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.oa.Act;
import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.oa.service.cmd.CreateAndTakeTransitionCmd;
import com.demxs.tdm.service.oa.service.cmd.JumpTaskCmd;
import com.demxs.tdm.service.oa.service.creator.ChainedActivitiesCreator;
import com.demxs.tdm.service.oa.service.creator.MultiInstanceActivityCreator;
import com.demxs.tdm.service.oa.service.creator.RuntimeActivityDefinitionEntityIntepreter;
import com.demxs.tdm.service.oa.service.creator.SimpleRuntimeActivityDefinitionEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGeneratorMine;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

/**
 * ??????????????????Service
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ActTaskService extends BaseService {

	@Autowired
	private ActDao actDao;

	@Autowired
	private ProcessEngineFactoryBean processEngineFactory;
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Resource
	private IExternalApi externalApi;

	/**
	 * ??????????????????
	 * @param act ??????????????????
	 * @return
	 */
	public List<Act> todoList(Act act){
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

		List<Act> result = new ArrayList<Act>();
		
		// =============== ?????????????????????  ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();

		// ??????????????????
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}
		
		// ????????????
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setBusinessId(processInstance.getBusinessKey());
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("todo");
			result.add(e);
		}
		
		// =============== ?????????????????????  ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
				.includeProcessVariables().active().orderByTaskCreateTime().desc();
		
		// ??????????????????
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			toClaimQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			toClaimQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			toClaimQuery.taskCreatedBefore(act.getEndDate());
		}
		
		// ????????????
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setBusinessId(processInstance.getBusinessKey());
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("claim");
			result.add(e);
		}
		return result;
	}

	/**
	 * ??????????????????
	 *
	 * @param page ??????ID
	 * @return
	 */
	@Transactional(readOnly = true,rollbackFor = ServiceException.class)
	public Page<Act> findTodoTasks(Page<Act> page,Act act) {
		List<Act> results = new ArrayList<Act>();
		User user = UserUtils.getUser();
		// ??????????????????ID??????
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(user.getLoginName()).orderByTaskCreateTime().desc();
		page.setCount(taskQuery.count());
		List<Task> tasks = taskQuery.listPage(page.getFirstResult(), page.getMaxResults());

		// ?????????????????????ID?????????????????????
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (processInstance == null) {
				continue;
			}
			String businessKey = processInstance.getBusinessKey();
			if (businessKey == null) {
				continue;
			}
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setBusinessId(processInstance.getBusinessKey());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			e.setTitle((String)taskService.getVariable(task.getId(),"title"));
			String formKey = this.getFormKey(processInstance.getProcessDefinitionId(), "");
			e.setFormKey(formKey);
			results.add(e);
		}
		page.setList(results);
		return page;
	}

	/**
	 * ??????????????????
	 * @param page
	 * @param act ??????????????????
	 * @return
	 */
	public Page<Act> historicList(Page<Act> page, Act act){
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished()
				.includeTaskLocalVariables().includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		
		// ??????????????????
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}
		
		// ????????????
		page.setCount(histTaskQuery.count());
		
		// ????????????
		List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
		//??????????????????
		List<Act> actList=Lists.newArrayList();
		for (HistoricTaskInstance histTask : histList) {
			HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(histTask.getProcessInstanceId()).singleResult();
			Act e = new Act();
			e.setHistTask(histTask);
			e.setBusinessId(processInstance.getBusinessKey());
			e.setVars(histTask.getTaskLocalVariables());
			e.setTitle((String) histTask.getProcessVariables().get("title"));
//			e.setTaskVars(histTask.getTaskLocalVariables());
//			System.out.println(histTask.getId()+"  =  "+histTask.getProcessVariables() + "  ========== " + histTask.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
			String formKey = this.getFormKey(processInstance.getProcessDefinitionId(), "");
			e.setFormKey(formKey);
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("finish");
			actList.add(e);
			//page.getList().add(e);
		}
		page.setList(actList);
		return page;
	}
	
	/**
	 * ????????????????????????
	 * @param procInsId ????????????
	 * @param startAct ????????????????????????
	 * @param endAct ????????????????????????
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct){
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
				.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		
		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();
		
		for (int i=0; i<list.size(); i++){
			
			HistoricActivityInstance histIns = list.get(i);
			
			// ??????????????????????????????
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start){
				continue;
			}
			
			// ????????????????????????????????????????????????????????????????????????
			if (StringUtils.isNotBlank(histIns.getAssignee())
					 || "startEvent".equals(histIns.getActivityType())
					 || "endEvent".equals(histIns.getActivityType())){
				
				// ???????????????????????????
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null){
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				
				Act e = new Act();
				e.setHistIns(histIns);
				// ???????????????????????????
				if ("startEvent".equals(histIns.getActivityType())){
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
//					List<HistoricIdentityLink> il = historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
					if (il.size() > 0){
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())){
							User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
							if (user != null){
								e.setAssignee(histIns.getAssignee());
								e.setAssigneeName(user.getName());
							}
						}
					}
				}
				// ???????????????????????????
				if (StringUtils.isNotEmpty(histIns.getAssignee())){
					User user = UserUtils.getByLoginName(histIns.getAssignee());
					if (user != null){
						e.setAssignee(histIns.getAssignee());
						e.setAssigneeName(user.getName());
					}
				}
				// ????????????????????????
				if (StringUtils.isNotBlank(histIns.getTaskId())){
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size()>0){
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}
			
			// ??????????????????????????????
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// ??????????????????????????????????????????????????????????????????????????????????????????
				for (int j=i+1; j<list.size(); j++){
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
						bl = true;
					}
				}
				if (!bl){
					break;
				}
			}
		}
		return actList;
	}

	/**
	 * ??????????????????
	 * @param category ????????????
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category) {
		/*
		 * ??????????????????????????????ProcessDefinition??????????????????????????????Deployment??????????????????
		 */
	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
	    		.latestVersion().active().orderByProcessDefinitionKey().asc();
	    
	    if (StringUtils.isNotEmpty(category)){
	    	processDefinitionQuery.processDefinitionCategory(category);
		}
	    
	    page.setCount(processDefinitionQuery.count());
	    
	    List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
	    for (ProcessDefinition processDefinition : processDefinitionList) {
	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      page.getList().add(new Object[]{processDefinition, deployment});
	    }
		return page;
	}
	
	/**
	 * ???????????????????????????????????????????????????KEY?????????????????????????????????????????????KEY???
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)){
			if (StringUtils.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)){
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}
	
	/**
	 * ????????????????????????
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public ProcessInstance getProcIns(String procInsId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
		return processInstance;
	}

	/**
	 * ????????????
	 * @param procDefKey ????????????KEY
	 * @param businessTable ???????????????
	 * @param businessId	???????????????
	 * @return ????????????ID
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public String startProcess(String procDefKey, String businessTable, String businessId) {
		return startProcess(procDefKey, businessTable, businessId, "");
	}
	
	/**
	 * ????????????
	 * @param procDefKey ????????????KEY
	 * @param businessTable ???????????????
	 * @param businessId	???????????????
	 * @param title			??????????????????????????????????????????
	 * @return ????????????ID
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
		Map<String, Object> vars = Maps.newHashMap();
		return startProcess(procDefKey, businessTable, businessId, title, vars);
	}
	
	/**
	 * ????????????
	 * @param procDefKey ????????????KEY
	 * @param businessTable ???????????????
	 * @param businessId	???????????????
	 * @param title			??????????????????????????????????????????
	 * @param vars			????????????
	 * @return ????????????ID
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId())
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey(procDefKey).processInstanceBusinessKey(businessId).active().singleResult();
		if (processInstance != null) {
			Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessId).taskCandidateOrAssigned(userId).active().singleResult();
			if (task == null) {
				throw new RuntimeException("?????????????????????????????????");
			}
			this.complete(task.getId(),task.getProcessInstanceId(),"",vars);
			return task.getProcessInstanceId();
		}

		// ?????????????????????????????????ID???????????????????????????ID?????????activiti:initiator???
		identityService.setAuthenticatedUserId(userId);
		// ??????????????????
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// ??????????????????
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// ????????????
//		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessId, vars);
		
		// ???????????????????????????ID
		if (StringUtils.isEmpty(businessTable)) {
			return procIns.getId();
		}
		Act act = new Act();
		act.setBusinessTable(businessTable);// ????????????
		act.setBusinessId(businessId);	// ?????????ID
		act.setProcInsId(procIns.getId());
		actDao.updateProcInsIdByBusinessId(act);
		return act.getProcInsId();
	}

	/**
	 * ????????????
	 * @param taskId ??????ID
	 */
	public Task getTask(String taskId){
		return taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned(UserUtils.getUser().getLoginName()).singleResult();
	}
	
	/**
	 * ????????????
	 * @param taskId ??????ID
	 * @param deleteReason ????????????
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteTask(String taskId, String deleteReason){
		taskService.deleteTask(taskId, deleteReason);
	}
	
	/**
	 * ????????????
	 * @param taskId ??????ID
	 * @param userId ????????????ID?????????????????????
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void claim(String taskId, String userId){
		taskService.claim(taskId, userId);
	}
	
	/**
	 * ????????????, ???????????????
	 * @param taskId ??????ID
	 * @param procInsId ????????????ID????????????????????????????????????????????????
	 * @param comment ???????????????????????????
	 * @param vars ????????????
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars){
		complete(taskId, procInsId, comment, "", vars);
	}
	
	/**
	 * ????????????, ???????????????
	 * @param taskId ??????ID
	 * @param procInsId ????????????ID????????????????????????????????????????????????
	 * @param comment ???????????????????????????
	 * @param title			??????????????????????????????????????????
	 * @param vars ????????????
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars){
		//?????????????????????
		String loginName = UserUtils.getUser().getLoginName();
		Task task = taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned(loginName).singleResult();
		if (task == null) {
			throw new IllegalArgumentException("????????????????????????????????????????????????????????????");
		}
		if (StringUtils.isEmpty(task.getAssignee())) {
			taskService.setAssignee(task.getId(),loginName);
			task.setAssignee(loginName);
		}
		// ????????????
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// ??????????????????
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// ??????????????????
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// ????????????
		taskService.complete(taskId, vars);
	}


	/**
	 * @param procDefKey
	 * @param assignee ?????????
	 * @param businessId ????????????
	 * @param title	??????
	 * @param vars	????????????
	 * @return
	 */
	public String apiStartProcess(String procDefKey, String assignee, String businessId, String title, Map<String, Object> vars) {
		if (vars == null) {
			vars = new HashMap<>();
		}
		if (StringUtils.isNotEmpty(assignee)) {
			vars.put("assignee", assignee);
		}
		return this.startProcess(procDefKey, "", businessId, title, vars);
	}
	/**
	 * ??????
	 * @param businessKey ????????????
	 * @param comment ????????????
	 * @param result  1:?????? ,0 : ??????    Global.YES   Global.NO
	 * @param assignee ??????????????????
	 */
	public void apiComplete(String businessKey, String comment, String result, String assignee, Map<String, Object> vars){
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).active().singleResult();
		if (vars == null) {
			vars = new HashMap<>();
		}
		if(task==null){
			return;
		}
		if(StringUtils.isNotBlank(assignee)){
			vars.put("assignee", assignee);
		}
		vars.put("pass", result);
		this.complete(task.getId(),task.getProcessInstanceId(),comment,"",vars);
	}

	/**
	 * ????????????
	 * @param businessKey
	 */
	public void processEnd(String businessKey){
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).active().singleResult();
		taskService.complete(task.getId());
	}

	/**
	 * ???????????????
	 * @param businessKey
	 * @param assignee
	 * @param message ????????????
	 */
	public void apiClaim(String businessKey,String assignee, String message){
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).active().singleResult();
		if(task==null){
			return;
		}
		taskService.unclaim(task.getId());
		if (StringUtils.isNotEmpty(assignee)) {
			List<String> userList = new ArrayList<>();
			for (String u : assignee.split(";")) {
				if (StringUtils.isNotEmpty(u)) {
					userList.add(u);
				}
			}
			if (userList.size() == 1) {
				taskService.setAssignee(task.getId(),assignee);
			} else {
				List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
				if (identityLinksForTask.size() > 0) {
					DeleteTodoParam deleteTodoParam = new DeleteTodoParam();
					deleteTodoParam.setModelId(task.getId());
					for (IdentityLink identityLink : taskService.getIdentityLinksForTask(task.getId())) {
						deleteTodoParam.addTarget(identityLink.getUserId());
						taskService.deleteCandidateUser(task.getId(),identityLink.getUserId());
					}
					//externalApi.deleteTodo(deleteTodoParam);
				}
				SendTodoParam sendTodoParam = new SendTodoParam();
				for (String u : userList) {
					sendTodoParam.addTarget(u);
					taskService.addCandidateUser(task.getId(),u);
				}
				sendTodoParam.setSubject(message);
				sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/act/task/approval?taskId"+task.getId());
				sendTodoParam.setCreateTime(task.getCreateTime());
				//externalApi.sendTodo(sendTodoParam);
			}
		}
//		taskService.setAssignee(task.getId(),assignee);
	}

	/**
	 * @param businessKey
	 */
	public void apiDeleteProcess(String businessKey){
		Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKey).singleResult();
		if(execution == null){
			return;
		}
		runtimeService.deleteProcessInstance(execution.getProcessInstanceId(),"????????????");
	}










	/**
	 * ?????????????????????
	 * @param procInsId
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void completeFirstTask(String procInsId){
		completeFirstTask(procInsId, null, null, null);
	}
	
	/**
	 * ?????????????????????
	 * @param procInsId
	 * @param comment
	 * @param title
	 * @param vars
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void completeFirstTask(String procInsId, String comment, String title, Map<String, Object> vars){
		String userId = UserUtils.getUser().getLoginName();
		Task task = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(procInsId).active().singleResult();
		if (task != null){
			complete(task.getId(), procInsId, comment, title, vars);
		}
	}

//	/**
//	 * ????????????
//	 * @param taskId ??????ID
//	 * @param userId ????????????
//	 */
//	public void delegateTask(String taskId, String userId){
//		taskService.delegateTask(taskId, userId);
//	}
//	
//	/**
//	 * ????????????????????????
//	 * @param taskId ??????ID
//	 */
//	public void resolveTask(String taskId){
//		taskService.resolveTask(taskId);
//	}
//	
//	/**
//	 * ????????????
//	 * @param taskId
//	 */
//	public void backTask(String taskId){
//		taskService.
//	}
	
	/**
	 * ??????????????????
	 */
	public void addTaskComment(String taskId, String procInsId, String comment){
		taskService.addComment(taskId, procInsId, comment);
	}
	
	//////////////////  ????????????????????????????????????????????????????????? ??????  https://github.com/bluejoe2008/openwebflow  ////////////////////////////////////////////////// 

	/**
	 * ??????????????????
	 */
	public void taskBack(String procInsId, Map<String, Object> variables) {
		taskBack(getCurrentTask(procInsId), variables);
	}

	/**
	 * ???????????????????????????
	 */
	public void taskBack(TaskEntity currentTaskEntity, Map<String, Object> variables) {
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getIncomingTransitions().get(0).getSource();
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * ??????????????????
	 */
	public void taskForward(String procInsId, Map<String, Object> variables) {
		taskForward(getCurrentTask(procInsId), variables);
	}

	/**
	 * ???????????????????????????
	 */
	public void taskForward(TaskEntity currentTaskEntity, Map<String, Object> variables) {
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getOutgoingTransitions().get(0).getDestination();

		jumpTask(currentTaskEntity, activity, variables);
	}
	
	/**
	 * ??????????????????????????????????????????????????????
	 */
	public void jumpTask(String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getCurrentTask(procInsId), targetTaskDefinitionKey, variables);
	}

	/**
	 * ??????????????????????????????????????????????????????
	 */
	public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getTaskEntity(currentTaskId), targetTaskDefinitionKey, variables);
	}

	/**
	 * ??????????????????????????????????????????????????????
	 * @param currentTaskEntity ??????????????????
	 * @param targetTaskDefinitionKey ????????????????????????????????????????????????????????????
	 * @throws Exception
	 */
	public void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
		ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
				targetTaskDefinitionKey);
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * ??????????????????????????????????????????????????????
	 * @param currentTaskEntity ??????????????????
	 * @param targetActivity ????????????????????????????????????????????????????????????
	 * @throws Exception
	 */
	private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
	}
	
	/**
	 * ?????????
	 */
	@SuppressWarnings("unchecked")
	public ActivityImpl[] insertTasksAfter(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
		List<String> assigneeList = new ArrayList<String>();
		assigneeList.add(Authentication.getAuthenticatedUserId());
		assigneeList.addAll(CollectionUtils.arrayToList(assignees));
		String[] newAssignees = assigneeList.toArray(new String[0]);
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		ActivityImpl prototypeActivity = ProcessDefUtils.getActivity(processEngine, processDefinition.getId(), targetTaskDefinitionKey);
		return cloneAndMakeChain(processDefinition, procInsId, targetTaskDefinitionKey, prototypeActivity.getOutgoingTransitions().get(0).getDestination().getId(), variables, newAssignees);
	}

	/**
	 * ?????????
	 */
	public ActivityImpl[] insertTasksBefore(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
		ProcessDefinitionEntity procDef = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		return cloneAndMakeChain(procDef, procInsId, targetTaskDefinitionKey, targetTaskDefinitionKey, variables, assignees);
	}

	/**
	 * ?????????????????????????????????
	 */
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignee) {
		return splitTask(procDefId, procInsId, targetTaskDefinitionKey, variables, true, assignee);
	}
	
	/**
	 * ?????????????????????????????????
	 */
	@SuppressWarnings("unchecked")
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, boolean isSequential, String... assignees) {
		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(procDefId);
		info.setProcessInstanceId(procInsId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);

		radei.setPrototypeActivityId(targetTaskDefinitionKey);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setSequential(isSequential);
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(processEngine, processDefinition, info)[0];

		TaskEntity currentTaskEntity = this.getCurrentTask(procInsId);
		
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new CreateAndTakeTransitionCmd(currentTaskEntity, clone, variables));

//		recordActivitiesCreation(info);
		return clone;
	}

	private TaskEntity getCurrentTask(String procInsId) {
		return (TaskEntity) taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
	}

	private TaskEntity getTaskEntity(String taskId) {
		return (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	@SuppressWarnings("unchecked")
	private ActivityImpl[] cloneAndMakeChain(ProcessDefinitionEntity procDef, String procInsId, String prototypeActivityId, String nextActivityId, Map<String, Object> variables, String... assignees) {
		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(procDef.getId());
		info.setProcessInstanceId(procInsId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
		radei.setPrototypeActivityId(prototypeActivityId);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setNextActivityId(nextActivityId);

		ActivityImpl[] activities = new ChainedActivitiesCreator().createActivities(processEngine, procDef, info);

		jumpTask(procInsId, activities[0].getId(), variables);
//		recordActivitiesCreation(info);

		return activities;
	}
	
//	private void recordActivitiesCreation(SimpleRuntimeActivityDefinitionEntity info) {
//		info.serializeProperties();
//		_activitiesCreationStore.save(info);
//	}
	
	//////////////////////////////////////////////////////////////////// 
	

//	private void recordActivitiesCreation(SimpleRuntimeActivityDefinitionEntity info) throws Exception {
//		info.serializeProperties();
//		_activitiesCreationStore.save(info);
//	}
//
//	/**
//	 * ?????????????????????????????????
//	 * 
//	 * @param targetTaskDefinitionKey
//	 * @param assignee
//	 * @throws IOException
//	 * @throws IllegalAccessException
//	 * @throws IllegalArgumentException
//	 */
//	public ActivityImpl split(String targetTaskDefinitionKey, boolean isSequential, String... assignees) throws Exception {
//		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
//		info.setProcessDefinitionId(processDefinition.getId());
//		info.setProcessInstanceId(_processInstanceId);
//
//		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
//
//		radei.setPrototypeActivityId(targetTaskDefinitionKey);
//		radei.setAssignees(CollectionUtils.arrayToList(assignees));
//		radei.setSequential(isSequential);
//
//		ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(_processEngine, processDefinition, info)[0];
//
//		TaskEntity currentTaskEntity = getCurrentTask();
//		executeCommand(new CreateAndTakeTransitionCmd(currentTaskEntity.getExecutionId(), clone));
//		executeCommand(new DeleteRunningTaskCmd(currentTaskEntity));
//
//		recordActivitiesCreation(info);
//		return clone;
//	}
//
//	public ActivityImpl split(String targetTaskDefinitionKey, String... assignee) throws Exception {
//		return split(targetTaskDefinitionKey, true, assignee);
//	}

	////////////////////////////////////////////////////////////////////
	
	/**
	 * ????????????????????????
	 * @param executionId	??????ID
	 * @return	???????????????????????????
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		//????????????????????????
		HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
//		List<String> activeActivityIds = Lists.newArrayList();
//		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
//			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
//		}
		List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(executionId).orderByHistoricActivityInstanceStartTime().asc().list();
		//????????????id??????
		List<String> highLightedActivitis = new ArrayList<String>();

//		Context.setProcessEngineConfiguration(processEngineFactory.getProcessEngineConfiguration());

//		ProcessDiagramGenerator diagramGenerator = processEngineFactory.getProcessEngineConfiguration().getProcessDiagramGenerator();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

		//????????????id??????
		List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);

		//????????????
		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}
		for(HistoricActivityInstance tempActivity : highLightedActivitList){
			String activityId = tempActivity.getActivityId();
			highLightedActivitis.add(activityId);
		}

		//???????????????????????????????????????????????????
//		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"??????","??????",null,1.0);

		// ?????????spring??????????????????????????????
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// ??????spring??????????????????????????????????????????
		Context.setProcessEngineConfiguration(processEngineFactory.getProcessEngineConfiguration());
//		return ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
		ProcessDiagramGenerator pr = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
		return new DefaultProcessDiagramGeneratorMine().generateDiagram(bpmnModel, "png", activeActivityIds,highLightedActivitis,highLightedFlows,"??????","??????","??????",null,1.0);
		//return null;
		//return pr.generateDiagram(bpmnModel, "png", activeActivityIds,highLightedActivitis,highLightedFlows,"??????","??????","??????",null,1.0);
	}
	/**
	 * ????????????????????????
	 * @param processDefinitionEntity
	 * @param historicActivityInstances
	 * @return
	 */
	private List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// ????????????????????????flowId
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// ?????????????????????????????????
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i)
							.getActivityId());// ?????????????????????????????????
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// ?????????????????????????????????????????????
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1)
							.getActivityId());
			// ????????????????????????????????????????????????????????????
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances
						.get(j);// ?????????????????????
				HistoricActivityInstance activityImpl2 = historicActivityInstances
						.get(j + 1);// ?????????????????????
				if (activityImpl1.getStartTime().equals(
						activityImpl2.getStartTime())) {
					// ???????????????????????????????????????????????????????????????
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// ????????????????????????
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl
					.getOutgoingTransitions();// ?????????????????????????????????
			for (PvmTransition pvmTransition : pvmTransitions) {
				// ???????????????????????????
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();
				// ?????????????????????????????????????????????????????????????????????????????????id?????????????????????
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}
	/**
	 * ?????????????????????
	 * @param processInstanceId		????????????ID
	 * @return	???????????????????????????
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//????????????
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();//?????????????????????????????????

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// ????????????
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;
	}
	

	/**
	 * ?????????????????????????????????????????????X???Y???????????????????????????????????????????????????
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
														  boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("????????????", properties.get("name"));
		vars.put("????????????", ActUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			// ???????????????task
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			// ???????????????????????????
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// ?????????????????????
				setTaskGroup(vars, candidateGroupIdExpressions);

				// ???????????????
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("????????????", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("??????", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	/**
	 * ???????????????
	 * @param vars
	 * @param candidateGroupIdExpressions
	 */
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("??????????????????", roles);
	}

	/**
	 * ???????????????????????????
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("???????????????", userInfo);
		}
	}

	/**
	 * ????????????????????????
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
					.singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * ???????????????????????????
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * ??????????????????
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	
}
