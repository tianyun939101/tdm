/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationCable;

import java.util.List;

/**
 * 构型电缆信息DAO接口
 * @author neo
 * @version 2020-02-18
 */
@MyBatisDao
public interface ConfigurationCableDao extends CrudDao<ConfigurationCable> {
	List<ConfigurationCable> findByVersionId(String cvId);

	void deleteByVersionId(String cvId);
}