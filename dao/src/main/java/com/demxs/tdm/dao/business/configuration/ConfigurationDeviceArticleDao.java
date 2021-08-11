/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.dao.business.configuration;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationDeviceArticle;

import java.util.List;

/**
 * 设备构型信息DAO接口
 * @author neo
 * @version 2020-02-18
 */
@MyBatisDao
public interface ConfigurationDeviceArticleDao extends CrudDao<ConfigurationDeviceArticle> {
	List<ConfigurationDeviceArticle> findByVersionId(String cvId);

	void deleteByVersionId(String cvId);
}