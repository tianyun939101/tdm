package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.SapmleConfirmRecord;

/**
 * 样品确认记录DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface SapmleConfirmRecordDao extends CrudDao<SapmleConfirmRecord> {
	
}