/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationMeterDao;
import com.demxs.tdm.dao.business.configuration.ConfigurationTestArticleDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationMeter;
import com.demxs.tdm.domain.business.configuration.ConfigurationTestArticle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * @Describe:构型仪器仪表Service
 * @Author:WuHui
 * @Date:19:20 2021/2/2
*/
@Service
@Transactional(readOnly = true)
public class ConfigurationMeterService extends CrudService<ConfigurationMeterDao, ConfigurationMeter> {

	public ConfigurationMeter get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationMeter> findList(ConfigurationMeter meter) {
		return super.findList(meter);
	}
	
	public Page<ConfigurationMeter> findPage(Page<ConfigurationMeter> page, ConfigurationMeter meter) {
		return super.findPage(page, meter);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationMeter meter) {
		super.save(meter);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationMeter meter) {
		super.delete(meter);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}

	public List<ConfigurationMeter> findByVersionId(String cvId){
		return super.dao.findByVersionId(cvId);
	}
}