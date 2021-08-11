package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestTaskUserHours;

/**
 * 试验人员任务工时DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestTaskUserHoursDao extends CrudDao<TestTaskUserHours> {
	
}