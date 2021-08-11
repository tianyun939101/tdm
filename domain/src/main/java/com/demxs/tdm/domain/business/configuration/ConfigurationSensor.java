/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.dataCenter.TestEquipment;
import com.demxs.tdm.domain.resource.consumeables.Haocaiku;
import org.hibernate.validator.constraints.Length;

/**
 * 构型传感器信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationSensor extends DataEntity<ConfigurationSensor> {
	
	private static final long serialVersionUID = 1L;
	private String cvId;		// 构型版本ID
	private String sensorId;		// 传感器ID
	private TestEquipment chuanGanQi;
	
	public ConfigurationSensor() {
		super();
	}

	public ConfigurationSensor(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型版本ID长度必须介于 0 和 64 之间")
	public String getCvId() {
		return cvId;
	}

	public void setCvId(String cvId) {
		this.cvId = cvId;
	}
	
	@Length(min=0, max=64, message="传感器ID长度必须介于 0 和 64 之间")
	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public TestEquipment getChuanGanQi() {
		return chuanGanQi;
	}

	public void setChuanGanQi(TestEquipment chuanGanQi) {
		this.chuanGanQi = chuanGanQi;
	}
}