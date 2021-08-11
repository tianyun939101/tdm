/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationTestArticleDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationTestArticle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型试验件信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationTestArticleService extends CrudService<ConfigurationTestArticleDao, ConfigurationTestArticle> {

	public ConfigurationTestArticle get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationTestArticle> findList(ConfigurationTestArticle configurationTestArticle) {
		return super.findList(configurationTestArticle);
	}
	
	public Page<ConfigurationTestArticle> findPage(Page<ConfigurationTestArticle> page, ConfigurationTestArticle configurationTestArticle) {
		return super.findPage(page, configurationTestArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationTestArticle configurationTestArticle) {
		super.save(configurationTestArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationTestArticle configurationTestArticle) {
		super.delete(configurationTestArticle);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}
}