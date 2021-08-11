/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationCableDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationCable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型电缆信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationCableService extends CrudService<ConfigurationCableDao, ConfigurationCable> {

	public ConfigurationCable get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationCable> findList(ConfigurationCable configurationCable) {
		return super.findList(configurationCable);
	}
	
	public Page<ConfigurationCable> findPage(Page<ConfigurationCable> page, ConfigurationCable configurationCable) {
		return super.findPage(page, configurationCable);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationCable configurationCable) {
		super.save(configurationCable);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationCable configurationCable) {
		super.delete(configurationCable);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}

}