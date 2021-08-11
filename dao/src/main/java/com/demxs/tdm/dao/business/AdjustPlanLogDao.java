package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.AdjustPlanLog;

/**
 * 调整计划日志DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface AdjustPlanLogDao extends CrudDao<AdjustPlanLog> {

}