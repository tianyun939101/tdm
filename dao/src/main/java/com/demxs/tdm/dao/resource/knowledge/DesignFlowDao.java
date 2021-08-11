package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.DesignFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface DesignFlowDao extends TreeDao<DesignFlow> {
    List<DesignFlow> findParent(@Param("id") String id);
    List<DesignFlow> findChildrenByParentId(DesignFlow designFlow);

    void changeName(@Param("id") String id,@Param("name") String name);
    List<DesignFlow> findStat();
}
