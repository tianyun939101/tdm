package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface StandardDao extends TreeDao<Standard> {

    List<Standard> findListParent(Standard standard);
    List<Standard> findByParentIdMenu(@Param("parentId")String  parentId, @Param("type") String  type);
    List<Standard> findByParentId(@Param("parentId")String  parentId);
    List<Standard> findState();
    List<Standard> findByParentIdsLike(Standard standard);
    List<Standard> findAllList();
    List<Standard> findParent(@Param("id") String id);
    int insert(Standard standard);
    void changeName(@Param("id") String id,@Param("name") String name);
}
