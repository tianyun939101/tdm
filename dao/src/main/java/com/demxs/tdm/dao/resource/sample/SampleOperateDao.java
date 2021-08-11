package com.demxs.tdm.dao.resource.sample;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.sample.SampleOperate;

/**
 * 样品操作记录信息DAO接口
 * @author sunjunhui
 * @version 2017-11-08
 */
@MyBatisDao
public interface SampleOperateDao extends CrudDao<SampleOperate> {
	
}