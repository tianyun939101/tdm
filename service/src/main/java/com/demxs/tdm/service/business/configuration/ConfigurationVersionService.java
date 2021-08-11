/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationVersionDao;
import com.demxs.tdm.domain.business.configuration.BaseConfiguration;
import com.demxs.tdm.domain.business.configuration.ConfigurationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型版本信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationVersionService extends CrudService<ConfigurationVersionDao, ConfigurationVersion> {

	@Autowired
	private BaseConfigurationService baseConfigurationService;

	public ConfigurationVersion get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationVersion> findList(ConfigurationVersion configurationVersion) {
		return super.findList(configurationVersion);
	}

	public List<ConfigurationVersion> findByBaseId(String id) {
		return this.dao.findByBaseId(id);
	}
	
	public Page<ConfigurationVersion> findPage(Page<ConfigurationVersion> page, ConfigurationVersion configurationVersion) {
		return super.findPage(page, configurationVersion);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationVersion configurationVersion) {
		super.save(configurationVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationVersion configurationVersion) {
		super.delete(configurationVersion);
	}

	@Transactional(readOnly = false)
	public int updateRevision(ConfigurationVersion configurationVersion){
		return super.dao.updateRevision(configurationVersion);
	}

	@Transactional(readOnly = false)
	public int updateActive(ConfigurationVersion configurationVersion){
		return super.dao.updateActive(configurationVersion);
	}

	@Transactional(readOnly = false)
	public int changeStatus(ConfigurationVersion configurationVersion){
		return super.dao.changeStatus(configurationVersion);
	}

	@Transactional(readOnly = false)
	public int changeLocalReport(ConfigurationVersion configurationVersion){
		BaseConfiguration baseConfiguration = new BaseConfiguration(configurationVersion.getBaseId());
		baseConfiguration.setEditVersion(configurationVersion.getId());
		baseConfigurationService.changeEditVersion(baseConfiguration);
		return super.dao.changeLocalReport(configurationVersion);
	}

	@Transactional(readOnly = false)
	public int changeAuditReport(ConfigurationVersion configurationVersion){
		BaseConfiguration baseConfiguration = new BaseConfiguration(configurationVersion.getBaseId());
		baseConfiguration.setDefVersion(configurationVersion.getId());
		baseConfiguration.setAuditVersion(configurationVersion.getId());
		baseConfigurationService.changeCurVersion(baseConfiguration);
		return super.dao.changeAuditReport(configurationVersion);
	}

	@Transactional(readOnly =  false)
	public int changeCurVersion(ConfigurationVersion configurationVersion){
		return super.dao.changeCurVersion(configurationVersion);
	}

	/**
	 * @Describe:设置当前版本
	 * @Author:WuHui
	 * @Date:9:11 2021/2/24
	 * @param configurationVersion
	 * @return:int
	*/
	@Transactional(readOnly =  false)
	public int setCurrentVersion(ConfigurationVersion configurationVersion){
		//重置当前版本
		configurationVersion.setCurVersion("0");
		super.dao.changeCurVersion(configurationVersion);
		configurationVersion.setStatus("0");
		//设置当前版本
		configurationVersion.setCurVersion("1");
		configurationVersion.setStatus("2");
		return super.dao.changeAuditReport(configurationVersion);
	}

}