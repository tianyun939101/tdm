package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.FlightTestInfo;

/**
 * 试飞架次信息DAO
 */
@MyBatisDao
public interface FlightTestInfoDao extends CrudDao<FlightTestInfo> {
	
}