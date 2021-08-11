/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationReportDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型报告模版信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationReportService extends CrudService<ConfigurationReportDao, ConfigurationReport> {

	public ConfigurationReport get(String id) {
		return super.get(id);
	}

	public ConfigurationReport getByVersionId(String cvId){
		return super.dao.getByVersionId(cvId);
	}
	
	public List<ConfigurationReport> findList(ConfigurationReport configurationReport) {
		return super.findList(configurationReport);
	}
	
	public Page<ConfigurationReport> findPage(Page<ConfigurationReport> page, ConfigurationReport configurationReport) {
		return super.findPage(page, configurationReport);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationReport configurationReport) {
		super.save(configurationReport);
	}

	@Transactional(readOnly =  false)
	public int deleteByVersionId(String cvId){
		return super.dao.deleteByVersionId(cvId);
	}
	@Transactional(readOnly = false)
	public void delete(ConfigurationReport configurationReport) {
		super.delete(configurationReport);
	}
	
}