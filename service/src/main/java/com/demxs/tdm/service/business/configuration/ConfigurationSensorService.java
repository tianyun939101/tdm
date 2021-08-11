/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationSensorDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationSensor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型传感器信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationSensorService extends CrudService<ConfigurationSensorDao, ConfigurationSensor> {

	public ConfigurationSensor get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationSensor> findList(ConfigurationSensor configurationSensor) {
		return super.findList(configurationSensor);
	}
	
	public Page<ConfigurationSensor> findPage(Page<ConfigurationSensor> page, ConfigurationSensor configurationSensor) {
		return super.findPage(page, configurationSensor);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationSensor configurationSensor) {
		super.save(configurationSensor);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationSensor configurationSensor) {
		super.delete(configurationSensor);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}
}