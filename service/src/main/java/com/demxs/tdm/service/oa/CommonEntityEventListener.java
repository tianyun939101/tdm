package com.demxs.tdm.service.oa;

import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.resource.shebei.ShebeiService;
import com.demxs.tdm.service.resource.shebei.ShebeiWxjlService;
import com.demxs.tdm.service.resource.zhishi.ZhishiXxService;
import com.demxs.tdm.service.sys.UserRoleService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.external.DeleteTodoParam;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.sys.UserRole;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjinfan on 2017/12/15.
 */
public class CommonEntityEventListener extends BaseEntityEventListener {


	public static final String NOT_COMPLETED = "-1";
	public static final boolean SEND_EXTERNAL_MESSAGE = Global.getConfig("send.external.message").equals("true");

	private ZhishiXxService zhishiXxService;

	private ShebeiWxjlService shebeiWxjlService;

	private ShebeiService shebeiService;

	private UserRoleService userRoleService;

	private ISystemService systemService;

	private ExternalApi externalApi;

	@Override
	protected void onCreate(ActivitiEvent event) {
		super.onCreate(event);

	}

	@Override
	protected void onInitialized(ActivitiEvent event) {
		super.onInitialized(event);
	}

	@Override
	protected void onDelete(ActivitiEvent event) {
		if(((ActivitiEntityEventImpl) event).getEntity() instanceof ExecutionEntity){//发起人终止流程
			ExecutionEntity processInstance = (ExecutionEntity)((ActivitiEntityEventImpl) event).getEntity();
			if(NOT_COMPLETED.equals(processInstance.getVariable("pass"))){
				synchronizeServiceStatus(processInstance);//回调业务撤销接口
			}
			for (TaskEntity taskEntity : processInstance.getTasks()) {
				removeExternalTodo(taskEntity);
			}
		}
	}

	@Override
	protected void onUpdate(ActivitiEvent event) {
		super.onUpdate(event);
	}

	@Override
	protected void onEntityEvent(ActivitiEvent event) {
		if(event.getType() == ActivitiEventType.PROCESS_COMPLETED) {
			ExecutionEntity processInstance = (ExecutionEntity)event.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(event.getProcessInstanceId()).active().singleResult();
			synchronizeServiceStatus(processInstance);
		} else if(event.getType() == ActivitiEventType.PROCESS_STARTED) {
			ExecutionEntity entity = (ExecutionEntity)((ActivitiProcessStartedEventImpl) event).getEntity();
			entity.setVariable("status", "running");
			entity.setVariable("businessKey", entity.getBusinessKey());
		} else if(event.getType() == ActivitiEventType.TASK_ASSIGNED) {//更改审批人
			TaskEntity taskEntity = (TaskEntity) ((ActivitiEntityEvent) event).getEntity();
			removeExternalTodo(taskEntity);
			/*String currentAssignee = (String)taskEntity.getVariableLocal("current_assignee");
			if (StringUtils.isNotEmpty(currentAssignee)) {//通知消息系统删除原有待办
				removeExternalTodo(taskEntity);
			}else{
//				@todo  删除候选人待办消息
			}*/
			if (StringUtils.isEmpty(taskEntity.getFormKey())) {//formkey 未设置时使用 start  formkey
				String formKey = event.getEngineServices().getFormService().getStartFormKey(taskEntity.getProcessDefinitionId());
				taskEntity.setFormKey(formKey);
			}
			taskEntity.setVariableLocal("current_assignee", taskEntity.getAssignee());
			sendExternalTodo(taskEntity);
		} else if(event.getType() == ActivitiEventType.TASK_CREATED) {//创建待办
			TaskEntity taskEntity = (TaskEntity) ((ActivitiEntityEvent) event).getEntity();
			String status = (String)taskEntity.getExecution().getVariable("status");
			if("start".equals(status)){//驳回到发起，设为发起人，通知业务模块
				synchronizeServiceStatus(taskEntity.getExecution());
				String startUserId = event.getEngineServices().getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(event.getProcessInstanceId()).singleResult().getStartUserId();
				taskEntity.setAssignee(startUserId);
				taskEntity.setVariableLocal("current_assignee", taskEntity.getAssignee());
			}
			if (StringUtils.isEmpty(taskEntity.getFormKey())) {//formkey 未设置时使用 start  formkey
				String formKey = event.getEngineServices().getFormService().getStartFormKey(taskEntity.getProcessDefinitionId());
				taskEntity.setFormKey(formKey);
			}
			String assignee = (String)taskEntity.getVariable("assignee");
			if (StringUtils.isNotEmpty(assignee)) {
				List<String> userList = new ArrayList<>();
				for (String u : assignee.split(";")) {
					if (StringUtils.isNotEmpty(u)) {
						userList.add(u);
					}
				}
				if (userList.size() == 1) {
					taskEntity.setAssignee(assignee);
					taskEntity.setVariableLocal("current_assignee", taskEntity.getAssignee());
					taskEntity.setVariable("assignee", "");
				} else {
					taskEntity.addCandidateUsers(userList);
				}
			}
			taskEntity.setVariableLocal("task_pass",NOT_COMPLETED);
			taskEntity.setVariable("pass",NOT_COMPLETED);
			sendExternalTodo(taskEntity);
		} else if(event.getType() == ActivitiEventType.TASK_COMPLETED) {//删除待办
			TaskEntity taskEntity = (TaskEntity) ((ActivitiEntityEvent) event).getEntity();
			Object pass = taskEntity.getExecution().getVariable("pass");
			if (pass != null) {
				taskEntity.setVariableLocal("task_pass", pass);
			}
			if(taskEntity.getExecution().getVariable("status").equals("start")) {//重新发起，状态变为审核中
				taskEntity.getExecution().setVariable("status","running");
			}
			completeTodo(taskEntity);
		} else {

		}
	}

	private void completeTodo(TaskEntity taskEntity) {
		if (!SEND_EXTERNAL_MESSAGE) {
			return;
		}
		DeleteTodoParam deleteTodoParam = new DeleteTodoParam();
		setUser(taskEntity, deleteTodoParam);
		getExternalApi().completeTodo(deleteTodoParam);
	}

	private void removeExternalTodo(TaskEntity taskEntity) {
		if (!SEND_EXTERNAL_MESSAGE) {
			return;
		}
		DeleteTodoParam deleteTodoParam = new DeleteTodoParam();
		setUser(taskEntity, deleteTodoParam);
		//删除时人员不正确没关系，根据modelId 删除
		getExternalApi().deleteTodo(deleteTodoParam);
	}

	private void sendExternalTodo(TaskEntity taskEntity) {
		if (!SEND_EXTERNAL_MESSAGE) {
			return;
		}
		SendTodoParam sendTodoParam = new SendTodoParam();
		sendTodoParam.setSubject((String) taskEntity.getVariable("message"));
//		sendTodoParam.setSubject(taskEntity.getProcessInstance().getProcessDefinitionName()+"-"+taskEntity.getName());
		sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/a/act/task/approval?taskId="+taskEntity.getId());
		sendTodoParam.setCreateTime(taskEntity.getCreateTime());
		setUser(taskEntity, sendTodoParam);
		getExternalApi().sendTodo(sendTodoParam);
	}

	/**
	 * 根据任务待办，设置消息中的人员、唯一标识等
	 * @param taskEntity
	 * @param deleteTodoParam
	 */
	private void setUser(TaskEntity taskEntity, DeleteTodoParam deleteTodoParam) {
		deleteTodoParam.setModelId(taskEntity.getId());
		if (StringUtils.isNotEmpty(taskEntity.getAssignee())) {
//			User user = getSystemService().getUserByLoginName(taskEntity.getAssignee());
			deleteTodoParam.addTarget(taskEntity.getAssignee());
		}else if (taskEntity.getCandidates().size() > 0) {
			for (IdentityLink identityLink : taskEntity.getCandidates()) {
				if (StringUtils.isNotEmpty(identityLink.getUserId())) {
//					User user = getSystemService().getUserByLoginName(identityLink.getUserId());
					deleteTodoParam.addTarget(identityLink.getUserId());
				} else if (StringUtils.isNotEmpty(identityLink.getGroupId())) {
					Role role = getSystemService().getRoleByEnname(identityLink.getGroupId());//使用角色英文名称
					UserRole filter = new UserRole();
					filter.setRoleId(role.getId());
					List<UserRole> list = getUserRoleService().findList(filter);
					for (UserRole userRole : list) {
						deleteTodoParam.addTarget(userRole.getUser().getLoginName());
					}
				}
			}
		}
	}

	/**
	 * 同步业务数据状态
	 * @param processInstance
	 */
	private void synchronizeServiceStatus(ExecutionEntity processInstance) {
		String bussinessId = (String)processInstance.getVariable("busId");
		String pass = (String)processInstance.getVariable("pass");//流程审核结果
		String type = (String) processInstance.getVariable("type");//流程类型
//			根据业务类型改变业务状态
		if(GlobalConstans.ActProcDefKey.ZHISHISH.equals(type)){//知识审核
			getZhishiXxService().saveShenhe(bussinessId, pass);
		}else if(GlobalConstans.ActProcDefKey.SHEBEIXZSH.equals(type)){//设备新增审核
			getShebeiService().saveShenhe(bussinessId,pass);
		}else if(GlobalConstans.ActProcDefKey.SHEBEIBFSH.equals(type)){//设备报废审核
			getShebeiService().saveShenheOver(bussinessId,pass);
		}else if(GlobalConstans.ActProcDefKey.SHEBEIWXSH.equals(type)){//设备维修审核
			getShebeiWxjlService().saveShenhe(bussinessId, pass);
		}
	}

	public ZhishiXxService getZhishiXxService() {
		if(zhishiXxService == null){
			zhishiXxService = SpringContextHolder.getBean(ZhishiXxService.class);
		}
		return zhishiXxService;
	}

	public ShebeiWxjlService getShebeiWxjlService() {
		if(shebeiWxjlService == null){
			shebeiWxjlService = SpringContextHolder.getBean(ShebeiWxjlService.class);
		}
		return shebeiWxjlService;
	}
	public ShebeiService getShebeiService() {
		if(shebeiService == null){
			shebeiService = SpringContextHolder.getBean(ShebeiService.class);
		}
		return shebeiService;
	}





	public UserRoleService getUserRoleService() {
		if(userRoleService == null){
			userRoleService = SpringContextHolder.getBean(UserRoleService.class);
		}
		return userRoleService;
	}
	public ISystemService getSystemService() {
		if(systemService == null){
			systemService = SpringContextHolder.getBean(ISystemService.class);
		}
		return systemService;
	}

	public ExternalApi getExternalApi() {
		if(externalApi == null){
			externalApi = SpringContextHolder.getBean(ExternalApi.class);
		}
		return externalApi;
	}
}
