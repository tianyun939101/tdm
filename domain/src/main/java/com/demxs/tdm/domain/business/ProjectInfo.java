package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;


/**
 * 试验项目Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class ProjectInfo extends DataEntity<ProjectInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 项目名称
	private String code;		// 项目编号
	private ProjectType type;		// 项目类型
	private String status;		// 项目状态 0：进行 1已完成 数据字典
	private User charge;		// 项目负责人 项目组长
	private Office dept;		// 负责人部门 费用部门
	private String mainUserIds;//核心成员ids

	private String normalUserIds;//普通成员
	private List<ProjectUser> users;	//项目成员
	private Date startDate;		// 立项时间
	private Date endDate;		// 计划结项时间
	private String summary;		// 项目简介
	private Integer pubData;		// 公布数据
	private Integer codeFlag;		// 项目编号可用

	private String mainSampleSummary;//主要样品介绍

	private String fileIds;//项目文件

	private List<Attachment> files = Lists.newArrayList();//文件对象

	private String stopReason;//暂停原因

	private String excepReason;//异常原因
	
	public ProjectInfo() {
		super();
	}

	public ProjectInfo(String id){
		super(id);
	}

	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="项目编号长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
	}

	@Length(min=0, max=64, message="项目状态长度必须介于 0 和 64 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getCharge() {
		return charge;
	}

	public void setCharge(User charge) {
		this.charge = charge;
	}

	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}

	public List<ProjectUser> getUsers() {
		return users;
	}

	public void setUsers(List<ProjectUser> users) {
		this.users = users;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=500, message="项目简介长度必须介于 0 和 500 之间")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public Integer getPubData() {
		return pubData;
	}

	public void setPubData(Integer pubData) {
		this.pubData = pubData;
	}
	
	public Integer getCodeFlag() {
		return codeFlag;
	}

	public void setCodeFlag(Integer codeFlag) {
		this.codeFlag = codeFlag;
	}

	public String getMainSampleSummary() {
		return mainSampleSummary;
	}

	public void setMainSampleSummary(String mainSampleSummary) {
		this.mainSampleSummary = mainSampleSummary;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public List<Attachment> getFiles() {
		return files;
	}

	public void setFiles(List<Attachment> files) {
		this.files = files;
	}

	public String getMainUserIds()
	{
		if(StringUtils.isNotBlank(mainUserIds)){
			return  mainUserIds;
		}else{
			String mIds = "";
			if(CollectionUtils.isNotEmpty(users)){
				for(ProjectUser u:users){
					if(u.getCoreFlag()==ProjectUser.MAINUSER){
						mIds+=u.getUser().getId()+",";
					}
				}
			}
			return mIds;
		}

	}

	public void setMainUserIds(String mainUserIds) {
		this.mainUserIds = mainUserIds;
	}


	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getExcepReason() {
		return excepReason;
	}

	public void setExcepReason(String excepReason) {
		this.excepReason = excepReason;
	}

	public String getNormalUserIds() {
		//return normalUserIds;
		if(StringUtils.isNotBlank(normalUserIds)){
			return  normalUserIds;
		}else{
			String mIds = "";
			if(CollectionUtils.isNotEmpty(users)){
				for(ProjectUser u:users){
					if(u.getCoreFlag()==ProjectUser.NORMALUSER){
						mIds+=u.getUser().getId()+",";
					}
				}
			}
			return mIds;
		}
	}

	public void setNormalUserIds(String normalUserIds) {
		this.normalUserIds = normalUserIds;
	}
}