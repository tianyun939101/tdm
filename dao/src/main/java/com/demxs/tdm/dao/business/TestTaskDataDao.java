package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestTaskData;

import java.util.List;

/**
 * 试验任务数据DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestTaskDataDao extends CrudDao<TestTaskData> {

    List<TestTaskData> getByEntrustId(String id);
}