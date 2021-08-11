package com.demxs.tdm.dao.resource.consumeables;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.consumeables.HaocaiRecord;

/**
 * 耗材流转记录DAO接口
 * @author sunjunhui
 * @version 2017-11-30
 */
@MyBatisDao
public interface HaocaiRecordDao extends CrudDao<HaocaiRecord> {
	
}