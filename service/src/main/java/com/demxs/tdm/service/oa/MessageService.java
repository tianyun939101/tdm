package com.demxs.tdm.service.oa;

import java.util.Date;
import java.util.List;

import com.demxs.tdm.dao.oa.MessageDao;
import com.demxs.tdm.service.sys.SystemService;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.oa.Message;
import com.demxs.tdm.job.JobType;
import com.demxs.tdm.job.JobUtil;
import com.github.ltsopensource.core.commons.utils.DateUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.exception.JobSubmitException;
import com.github.ltsopensource.jobclient.domain.Response;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 消息管理Service
 * @author sunjunhui
 * @version 2017-12-04
 */
@Service
@Transactional(readOnly = true)
public class MessageService extends CrudService<MessageDao, Message> {

	private static final Logger log = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private SystemService systemService;

	private  Message getAll(Message message){
		List<User> users = Lists.newArrayList();
		if(StringUtils.isNotBlank(message.getAcceptUsers())){
			String[] userIdArr = message.getAcceptUsers().split(",");
			for(String uId:userIdArr){
				users.add(systemService.getUser(uId));
			}
			message.setAcceptUserList(users);
		}
		return message;
	}

	private List<Message> getAll(List<Message> messages){
		if(!Collections3.isEmpty(messages)){
			for(Message m:messages){
				getAll(m);
			}
		}
		return messages;
	}

	public Message get(String id) {
		return getAll(super.get(id));
	}
	
	public List<Message> findList(Message message) {
		return getAll(super.findList(message));
	}
	
	public Page<Message> findPage(Page<Message> page, Message message) {
		Page<Message> dataValue = super.findPage(page, message);
		if(dataValue!=null && !Collections3.isEmpty(dataValue.getList())){
			getAll(dataValue.getList());
		}
		return dataValue;
	}
	
	@Transactional(readOnly = false)
	public void save(Message message) {
		super.save(message);
		if(message.getSend()==Message.SENDED){//消息需要发送
			addSendMessageJob(message);
		}
		if(message.getHandle()==Message.HANDLED){//需要删除该代办
			addHandleMessageJob(message);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Message message) {
		super.delete(message);
	}


	/**
	 * 添加消息发送任务
	 * @param message
	 */
	private void addSendMessageJob(Message message)  {
		try {
			Job job = new Job();
			job.setParam("type", JobType.SendMessage.getType());
			job.setTaskId(String.format("%s-%s", JobType.SendMessage.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
			job.setParam("message", JsonMapper.toJsonString(message));
			job.setTaskTrackerNodeGroup(JobType.SendMessage.getTaskTracker());
			job.setNeedFeedback(true);
			Response response = JobUtil.getJobClient().submitJob(job);
			if (!response.isSuccess()) {
				String msg = String.format("提交消息发送任务失败: %s",response.toString());
				log.error(msg);
			}
		}catch (JobSubmitException e){
		}
	}

	/**
	 * 添加消息处理任务
	 * @param message
	 */
	private void addHandleMessageJob(Message message)  {
		try {
			Job job = new Job();
			job.setParam("type", JobType.HandleMessage.getType());
			job.setTaskId(String.format("%s-%s", JobType.HandleMessage.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
			job.setParam("message", JsonMapper.toJsonString(message));
			job.setTaskTrackerNodeGroup(JobType.HandleMessage.getTaskTracker());
			job.setNeedFeedback(true);
			Response response = JobUtil.getJobClient().submitJob(job);
			if (!response.isSuccess()) {
				String msg = String.format("提交消息处理任务失败: %s",response.toString());
				log.error(msg);
			}
		}catch (JobSubmitException e){
		}
	}
}