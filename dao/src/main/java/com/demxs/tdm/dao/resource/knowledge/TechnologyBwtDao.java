package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.TechnologyBwt;

import java.util.List;

@MyBatisDao
public interface TechnologyBwtDao {

    int insert(TechnologyBwt technologyBwt);

    int delete(TechnologyBwt technologyBwt);

    List<TechnologyBwt> select(TechnologyBwt technologyBwt);
}
