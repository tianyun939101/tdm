package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.DesignFlow;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import com.demxs.tdm.domain.resource.kowledge.Technology;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface TechnologyDao extends TreeDao<Technology> {

    List<Technology> selectOther(@Param("id") String id);

    List<Technology> findFather();

    List<Technology> findParent(@Param("id") String id);

    void changeName(@Param("id") String id,@Param("name") String name);
}
