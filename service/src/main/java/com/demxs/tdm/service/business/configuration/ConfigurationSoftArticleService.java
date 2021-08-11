/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationSoftArticleDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationSoftArticle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型软件构型信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationSoftArticleService extends CrudService<ConfigurationSoftArticleDao, ConfigurationSoftArticle> {

	public ConfigurationSoftArticle get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationSoftArticle> findList(ConfigurationSoftArticle configurationSoftArticle) {
		return super.findList(configurationSoftArticle);
	}
	
	public Page<ConfigurationSoftArticle> findPage(Page<ConfigurationSoftArticle> page, ConfigurationSoftArticle configurationSoftArticle) {
		return super.findPage(page, configurationSoftArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationSoftArticle configurationSoftArticle) {
		super.save(configurationSoftArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationSoftArticle configurationSoftArticle) {
		super.delete(configurationSoftArticle);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}

}