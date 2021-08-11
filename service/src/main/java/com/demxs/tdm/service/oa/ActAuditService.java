package com.demxs.tdm.service.oa;

import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.BaseService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.comac.common.act.utils.ActUtils;
import com.google.common.collect.Maps;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 审核流程Service
 * @author 郭金龙
 * @version 2017-08-04
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ActAuditService extends BaseService implements IActAuditService{

	@Autowired
	protected ActTaskService actTaskService;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;


	
	/**
	 * 启动流程
	 * @param businessKey 业务主键
	 * @param actType 流程类型
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void start(String businessKey,String actType) {
		this.start(businessKey,actType,"");
	}
	/**
	 * 启动流程
	 * @param businessKey 业务主键
	 * @param actType 流程类型
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void start(String businessKey,String actType,String title) {
		Map<String, Object> variables = Maps.newHashMap();
		this.start(businessKey,actType,title,variables);
	}

	@Override
	@Transactional
	public void start(String businessKey,String actType,String title,String comment,Map<String, Object> variables) {
		variables.put("start_comment", comment);
		String procInstId = this.start(businessKey,actType,title,variables);
		/*Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
		if (task != null) {
			taskService.addComment(task.getId(), procInstId, comment);
		}*/
	}
	@Override
	@Transactional
	public String start(String businessKey, String actType, String title, Map<String, Object> variables) {

		// 启动流程
		variables.put("type", actType);
		variables.put("busId", businessKey);
		String procDefKey = actType;
		String businessTable = "";
		if("".equals(actType)){

		}
		 if(GlobalConstans.ActProcDefKey.ZHISHISH.equals(actType)){//知识审核
			procDefKey = ActUtils.PD_ZHISHISH_AUDIT[0];
			businessTable = ActUtils.PD_ZHISHISH_AUDIT[1];
		}else if(GlobalConstans.ActProcDefKey.SHEBEIWXSH.equals(actType)){//设备维修审核
			procDefKey = ActUtils.PD_SHEBEIWXSH_AUDIT[0];
//			businessTable = ActUtils.PD_SHEBEIWXSH_AUDIT[1];
		}else if(GlobalConstans.ActProcDefKey.WEITUODSH.equals(actType)){//申请单审核
			procDefKey = GlobalConstans.ActProcDefKey.WEITUODSH;
//			businessTable = ActUtils.PD_SHEBEIWXSH_AUDIT[1];
		}
		String procIns = actTaskService.startProcess(procDefKey, businessTable,businessKey,title,variables);
		logger.debug("start process of {key={}, bkey={},procIns={}, variables={}}", new Object[] {procDefKey, businessKey,procIns, variables });
		return procIns;
	}

	/**
	 *  审核流程
	 * @param taskId 任务ID
	 * @param processInstanceId 流程实例ID
	 * @param flag 是否通过（通过：yes 不通过：no）
	 * @param comment
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void auidt(String taskId,String processInstanceId,String flag,String comment) {
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(flag)? "1" : "0");
		actTaskService.complete(taskId,processInstanceId,comment,"",vars);
	}

	/**
	 *  撤消流程
	 * @param processInstanceId 流程实例ID
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void undo(String processInstanceId){
		/*boolean isAudit = false;
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processInstanceId).singleResult();
		Task task = taskService.createTaskQuery().taskAssignee(UserUtils.getUser().getLoginName()).processInstanceId(processInstanceId).active().singleResult();
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
		for (Task t:taskList) {
			Map<String, Object> variables = task.getProcessVariables();
			String pass = (String)variables.get("pass");
			if(StringUtils.isNotBlank(pass)){
				isAudit = true;
			}
		}

		if(isAudit){
			return;
		}else{*/
			runtimeService.deleteProcessInstance(processInstanceId,"发起人撤消审核！");
//		}
	}
}
