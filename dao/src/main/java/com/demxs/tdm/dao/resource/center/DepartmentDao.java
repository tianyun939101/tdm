package com.demxs.tdm.dao.resource.center;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.center.Department;
import com.demxs.tdm.domain.resource.kowledge.DesignFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DepartmentDao extends TreeDao<Department> {
    List<Department> findDeart();
    Department findByName(@Param("name") String name);
    List<String> findName();


   List<Map<String, String >> findLabTree();
   String getParent(@Param("id") String id);

    Department findById(@Param("id") String id);
}
