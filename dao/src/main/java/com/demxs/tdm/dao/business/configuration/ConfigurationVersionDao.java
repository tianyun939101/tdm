/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.dao.business.configuration;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationVersion;

import java.util.List;

/**
 * 构型版本信息DAO接口
 * @author neo
 * @version 2020-02-18
 */
@MyBatisDao
public interface ConfigurationVersionDao extends CrudDao<ConfigurationVersion> {
    //根据构型基础信息查询版本信息
	List<ConfigurationVersion> findByBaseId(String id);

	int updateRevision(ConfigurationVersion configurationVersion);

	/**
	* @author Jason
	* @date 2020/10/15 9:32
	* @params [configurationVersion]
	* 修改参数不为空的字段
	* @return int
	*/
	int updateActive(ConfigurationVersion configurationVersion);

	int changeStatus(ConfigurationVersion configurationVersion);

	int changeLocalReport(ConfigurationVersion configurationVersion);

	int changeAuditReport(ConfigurationVersion configurationVersion);

	int changeCurVersion(ConfigurationVersion configurationVersion);
}