package com.demxs.tdm.dao.configure;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.ShebeiRule;

/**
 * 设备采集配置DAO接口
 * @author sunjunhui
 * @version 2018-01-08
 */
@MyBatisDao
public interface ShebeiRuleDao extends CrudDao<ShebeiRule> {
	
}