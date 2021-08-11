package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestDataInfo;

@MyBatisDao
public interface TestDataInfoDao extends CrudDao<TestDataInfo> {
}
