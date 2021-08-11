/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;

import com.demxs.tdm.domain.resource.shebei.Shebei;
import org.hibernate.validator.constraints.Length;

/**
 * 设备构型信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationDeviceArticle extends DataEntity<ConfigurationDeviceArticle> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String deviceId;		// 设备ID
	private Shebei shebei;
	
	public ConfigurationDeviceArticle() {
		super();
	}

	public ConfigurationDeviceArticle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=64, message="设备ID长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Shebei getShebei() {
		return shebei;
	}

	public void setShebei(Shebei shebei) {
		this.shebei = shebei;
	}
}