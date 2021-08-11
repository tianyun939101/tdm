package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.annotation.MyBatisDao;

import java.util.List;

@MyBatisDao
public interface TechnologyTestDao {
    List<TechnologyTest> select(String id);
}
