package com.demxs.tdm.dao.data;


import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DependReprtDao {


    List<Map<String,Object>> executeSql(@Param("sql") String sql);


    List<String> executeSampleCodeSql(@Param("sql")String sql);
}
