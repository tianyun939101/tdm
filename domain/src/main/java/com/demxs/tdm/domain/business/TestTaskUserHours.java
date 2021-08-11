package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 试验人员任务工时Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestTaskUserHours extends DataEntity<TestTaskUserHours> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String tGroupId;		// 试验组ID
	private String entrustCode;		// 申请单编号
	private String taskId;		// 试验任务ID
	private User user;		// 试验人员ID
	private String userName;		// 试验人员名称
	private String seqId;		// 试验序列ID
	private String seqName;		// 试验序列名称
	private String itemId;		// 试验项目ID
	private String itemName;		// 试验项目名称
	private String unitId;		// 试验项ID
	private String unitName;		// 试验项名称
	private Date startTime;		// 任务开始时间
	private Date endTime;		// 任务结束时间
	private Double duration;		// 工作时长
	
	public TestTaskUserHours() {
		super();
	}

	public TestTaskUserHours(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}
	
	@Length(min=0, max=100, message="申请单编号长度必须介于 0 和 100 之间")
	public String getEntrustCode() {
		return entrustCode;
	}

	public void setEntrustCode(String entrustCode) {
		this.entrustCode = entrustCode;
	}
	
	@Length(min=0, max=64, message="试验任务ID长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=100, message="试验人员名称长度必须介于 0 和 100 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=64, message="试验序列ID长度必须介于 0 和 64 之间")
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	@Length(min=0, max=200, message="试验序列名称长度必须介于 0 和 200 之间")
	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	
	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=100, message="试验项目名称长度必须介于 0 和 100 之间")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Length(min=0, max=64, message="试验项ID长度必须介于 0 和 64 之间")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@Length(min=0, max=100, message="试验项名称长度必须介于 0 和 100 之间")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
}