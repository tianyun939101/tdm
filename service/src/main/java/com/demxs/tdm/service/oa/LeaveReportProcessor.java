package com.demxs.tdm.service.oa;

import com.demxs.tdm.dao.oa.LeaveDao;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.oa.Leave;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 销假后处理器
 * @author liuj
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LeaveReportProcessor implements TaskListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LeaveDao leaveDao;
	@Autowired
	private RuntimeService runtimeService;
	
	/**
	 * 销假完成后执行，保存实际开始和结束时间
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		String processInstanceId = delegateTask.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		Leave leave = new Leave(processInstance.getBusinessKey());
		leave.setRealityStartTime((Date) delegateTask.getVariable("realityStartTime"));
		leave.setRealityEndTime((Date) delegateTask.getVariable("realityEndTime"));
		leave.preUpdate();
		leaveDao.updateRealityTime(leave);
	}

}
