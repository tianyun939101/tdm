/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;

import com.demxs.tdm.domain.resource.consumeables.Haocaiku;
import org.hibernate.validator.constraints.Length;

/**
 * 构型电缆信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationCable extends DataEntity<ConfigurationCable> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String cableId;		// 电缆ID
	private Haocaiku dianLan;
	
	public ConfigurationCable() {
		super();
	}

	public ConfigurationCable(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=64, message="电缆ID长度必须介于 0 和 64 之间")
	public String getCableId() {
		return cableId;
	}

	public void setCableId(String cableId) {
		this.cableId = cableId;
	}

	public Haocaiku getDianLan() {
		return dianLan;
	}

	public void setDianLan(Haocaiku dianLan) {
		this.dianLan = dianLan;
	}
}