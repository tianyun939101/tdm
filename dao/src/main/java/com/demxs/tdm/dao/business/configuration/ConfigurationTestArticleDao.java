/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.dao.business.configuration;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationTestArticle;

import java.util.List;

/**
 * 构型试验件信息DAO接口
 * @author neo
 * @version 2020-02-18
 */
@MyBatisDao
public interface ConfigurationTestArticleDao extends CrudDao<ConfigurationTestArticle> {
	List<ConfigurationTestArticle> findByVersionId(String cvId);

	void deleteByVersionId(String cvId);
}