package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.DateUtils;

import java.util.Date;

/**
 * 试验任务分配Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestTaskAssignUser extends DataEntity<TestTaskAssignUser> {

	private static final long serialVersionUID = 1L;
	private String taskId;	//任务ID
	private String userId;	//用户ID
	private String userName;	//用户名称
	private Date startDate;	//开始时间
	private Date endDate;	//结束时间
	//private Double duration;	//工作时长

	public TestTaskAssignUser() {
		super();
	}

	public TestTaskAssignUser(String id){
		super(id);
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
/*
	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}*/

	public String getDuration(){
		if(this.getEndDate() == null){
			return DateUtils.formatDateTime(System.currentTimeMillis() - this.getStartDate().getTime());
		}else{
			return DateUtils.formatDateTime(this.getEndDate().getTime() - this.getStartDate().getTime());
		}
	}
}