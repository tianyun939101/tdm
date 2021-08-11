/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.ConfigurationDeviceArticleDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationDeviceArticle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备构型信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationDeviceArticleService extends CrudService<ConfigurationDeviceArticleDao, ConfigurationDeviceArticle> {

	public ConfigurationDeviceArticle get(String id) {
		return super.get(id);
	}
	
	public List<ConfigurationDeviceArticle> findList(ConfigurationDeviceArticle configurationDeviceArticle) {
		return super.findList(configurationDeviceArticle);
	}
	
	public Page<ConfigurationDeviceArticle> findPage(Page<ConfigurationDeviceArticle> page, ConfigurationDeviceArticle configurationDeviceArticle) {
		return super.findPage(page, configurationDeviceArticle);
	}
	
	@Transactional(readOnly = false)
	public void save(ConfigurationDeviceArticle configurationDeviceArticle) {
		super.save(configurationDeviceArticle);
	}
	
	@Transactional(readOnly = false)
	public void delete(ConfigurationDeviceArticle configurationDeviceArticle) {
		super.delete(configurationDeviceArticle);
	}

	@Transactional(readOnly = false)
	public void deleteByVersionId(String cvId){
		super.dao.deleteByVersionId(cvId);
	}
}