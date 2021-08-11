/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.BaseConfiguration;

import java.util.List;

/**
 * 构型基础信息DAO接口
 * @author neo
 * @version 2020-02-18
 */
@MyBatisDao
public interface BaseConfigurationDao extends CrudDao<BaseConfiguration> {
	int updateBase(BaseConfiguration baseConfiguration);

	int changeCurVersion(BaseConfiguration baseConfiguration);

	int changeEditVersion(BaseConfiguration baseConfiguration);

	List<BaseConfiguration> findCurVersionDetail(BaseConfiguration baseConfiguration);

}