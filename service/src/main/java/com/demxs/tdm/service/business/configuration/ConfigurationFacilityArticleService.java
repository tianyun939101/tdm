/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationFacilityArticleDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationFacilityArticle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设施构型信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationFacilityArticleService extends CrudService<ConfigurationFacilityArticleDao, ConfigurationFacilityArticle> {

	public ConfigurationFacilityArticle get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationFacilityArticle> findList(ConfigurationFacilityArticle configurationFacilityArticle) {
		return super.findList(configurationFacilityArticle);
	}
	
	public Page<ConfigurationFacilityArticle> findPage(Page<ConfigurationFacilityArticle> page, ConfigurationFacilityArticle configurationFacilityArticle) {
		return super.findPage(page, configurationFacilityArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationFacilityArticle configurationFacilityArticle) {
		super.save(configurationFacilityArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationFacilityArticle configurationFacilityArticle) {
		super.delete(configurationFacilityArticle);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}
	
}