/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.dao.business.configuration;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationFacilityArticle;

import java.util.List;

/**
 * 设施构型信息DAO接口
 * @author neo
 * @version 2020-02-18
 */
@MyBatisDao
public interface ConfigurationFacilityArticleDao extends CrudDao<ConfigurationFacilityArticle> {
	List<ConfigurationFacilityArticle> findByVersionId(String cvId);

	void deleteByVersionId(String cvId);
}