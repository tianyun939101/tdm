/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 构型报告模版信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationReport extends DataEntity<ConfigurationReport> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String scope;		// 范围
	private String basisDoc;		// 依据性文件
	private String abb;		// 缩略语
	private String overview;		// 概述
	
	public ConfigurationReport() {
		super();
	}

	public ConfigurationReport(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=3072, message="范围长度必须介于 0 和 3072 之间")
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Length(min=0, max=3072, message="依据性文件长度必须介于 0 和 3072 之间")
	public String getBasisDoc() {
		return basisDoc;
	}

	public void setBasisDoc(String basisDoc) {
		this.basisDoc = basisDoc;
	}
	
	@Length(min=0, max=3072, message="缩略语长度必须介于 0 和 3072 之间")
	public String getAbb() {
		return abb;
	}

	public void setAbb(String abb) {
		this.abb = abb;
	}
	
	@Length(min=0, max=3072, message="概述长度必须介于 0 和 3072 之间")
	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}
	
}