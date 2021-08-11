/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.shebei.ShebeiTaiTao;
import org.hibernate.validator.constraints.Length;

/**
 * 设施构型信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationFacilityArticle extends DataEntity<ConfigurationFacilityArticle> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String facilityId;		// 设施ID
	private ShebeiTaiTao shebeiTaiTao;
	
	public ConfigurationFacilityArticle() {
		super();
	}

	public ConfigurationFacilityArticle(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=64, message="设施ID长度必须介于 0 和 64 之间")
	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public ShebeiTaiTao getShebeiTaiTao() {
		return shebeiTaiTao;
	}

	public void setShebeiTaiTao(ShebeiTaiTao shebeiTaiTao) {
		this.shebeiTaiTao = shebeiTaiTao;
	}
}