package com.demxs.tdm.dao.config;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.config.UrlConfig;

/**
 * url配置DAO接口
 * @author sunjunhui
 * @version 2017-11-30
 */
@MyBatisDao
public interface UrlConfigDao extends CrudDao<UrlConfig> {
	
}