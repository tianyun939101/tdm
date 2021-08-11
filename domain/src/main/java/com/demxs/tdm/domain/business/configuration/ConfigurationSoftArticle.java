/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 构型软件构型信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationSoftArticle extends DataEntity<ConfigurationSoftArticle> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String softArticleId;		// 软件构型ID
	//软件构型
	private SoftwareLibrary softwareLibrary;
	
	public ConfigurationSoftArticle() {
		super();
	}

	public ConfigurationSoftArticle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=64, message="软件构型ID长度必须介于 0 和 64 之间")
	public String getSoftArticleId() {
		return softArticleId;
	}

	public void setSoftArticleId(String softArticleId) {
		this.softArticleId = softArticleId;
	}

	public SoftwareLibrary getSoftwareLibrary() {
		return softwareLibrary;
	}

	public void setSoftwareLibrary(SoftwareLibrary softwareLibrary) {
		this.softwareLibrary = softwareLibrary;
	}
}