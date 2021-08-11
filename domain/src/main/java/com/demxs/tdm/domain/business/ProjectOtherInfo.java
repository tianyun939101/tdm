package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目其他信息Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class ProjectOtherInfo extends DataEntity<ProjectOtherInfo> {
	
	private static final long serialVersionUID = 1L;
	private User adminUser;		// 项目管理员
	private String adviser;		// 产业顾问
	private String sampleSummary;		// 主要样品介绍
	private String unitType;		// 预使用方法类别
	private String devices;		// 预使用设备
	
	public ProjectOtherInfo() {
		super();
	}

	public ProjectOtherInfo(String id){
		super(id);
	}

	@Length(min=0, max=100, message="项目管理员长度必须介于 0 和 100 之间")
	public User getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(User adminUser) {
		this.adminUser = adminUser;
	}
	
	@Length(min=0, max=100, message="产业顾问长度必须介于 0 和 100 之间")
	public String getAdviser() {
		return adviser;
	}

	public void setAdviser(String adviser) {
		this.adviser = adviser;
	}
	
	@Length(min=0, max=500, message="主要样品介绍长度必须介于 0 和 500 之间")
	public String getSampleSummary() {
		return sampleSummary;
	}

	public void setSampleSummary(String sampleSummary) {
		this.sampleSummary = sampleSummary;
	}
	
	@Length(min=0, max=2000, message="预使用方法类别长度必须介于 0 和 2000 之间")
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	@Length(min=0, max=2000, message="预使用设备长度必须介于 0 和 2000 之间")
	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}
	
}