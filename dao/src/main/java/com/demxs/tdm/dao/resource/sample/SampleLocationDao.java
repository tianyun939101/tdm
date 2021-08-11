package com.demxs.tdm.dao.resource.sample;


import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.sample.SampleLocation;

/**
 * 样品位置管理DAO接口
 * @author sunjunhui
 * @version 2017-11-08
 */
@MyBatisDao
public interface SampleLocationDao extends TreeDao<SampleLocation> {
	
}