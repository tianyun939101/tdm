package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestBenchScheduling;

import java.util.List;

@MyBatisDao
public interface TestBenchSchedulingDao extends CrudDao<TestBenchScheduling> {

    List<TestBenchScheduling> findCount(TestBenchScheduling testBenchScheduling);
}
