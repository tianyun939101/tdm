package com.demxs.tdm.service.sys;

import org.hibernate.validator.constraints.Length;

/**
 * 系统消息Entity
 * @author 谭冬梅
 * @version 2017-09-06
 */
public class SysMessageDTO  {

	private String title;		// 消息标题
	private String content;		// 消息内容
	private String toUser;		// 提醒人
	private String mtype;		    // 消息类型1:流程 2：流程完结通知（点击一次即视为已读如流程完结，标签打印提醒）3：核查类（核查后不再提醒）
	private String state;		    // 消息状态 0 未读 1 已读
	private String statemc;     //是否已读
	private String redirectUrl;		// 处理地址
	private String important;		// 0一般1重要
	private String isRepeat;		// 是否重复提醒直至任务处理结束（只针对流程任务）0：是(流程重复提醒直至处理结束) 1：否  2:点击提醒信息即视为已读
	private String mainId;		// flowtaskid （只针对流程任务有值）
	private String endTime;		// 消息提醒截止日期
	private String startTime;		// 消息提醒开始日期
	private String toUsersId;		// 提醒人id（多人）
	private String toUsersName;		// 提醒人名称（多人）

	public SysMessageDTO() {
		super();
	}
	public String getStatemc() {
		return statemc;
	}
	public void setStatemc(String statemc) {
		this.statemc = statemc;
	}

	@Length(min=0, max=500, message="消息标题长度必须介于 0 和 500 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min=0, max=4000, message="消息内容长度必须介于 0 和 4000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Length(min=0, max=4000, message="处理地址长度必须介于 0 和 4000 之间")
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public String getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(String isRepeat) {
		this.isRepeat = isRepeat;
	}

	@Length(min=0, max=100, message="flowtaskid （只针对流程任务有值）长度必须介于 0 和 100 之间")
	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	@Length(min=0, max=100, message="消息提醒截止日期长度必须介于 0 和 100 之间")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Length(min=0, max=100, message="消息提醒开始日期长度必须介于 0 和 100 之间")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Length(min=0, max=500, message="提醒人id（多人）长度必须介于 0 和 500 之间")
	public String getToUsersId() {
		return toUsersId;
	}

	public void setToUsersId(String toUsersId) {
		this.toUsersId = toUsersId;
	}

	@Length(min=0, max=500, message="提醒人名称（多人）长度必须介于 0 和 500 之间")
	public String getToUsersName() {
		return toUsersName;
	}

	public void setToUsersName(String toUsersName) {
		this.toUsersName = toUsersName;
	}

}