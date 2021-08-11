package com.demxs.tdm.dao.cj;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.cj.CjTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 采集信息表DAO接口
 * @author 张仁
 * @version 2017-08-11
 */
@MyBatisDao
public interface CjTableDao extends CrudDao<CjTable> {

    /**
     * 批量删除
     * @param cjTable
     */
    public void batchDelete(CjTable cjTable);

    /**
     * 执行SQL
     * @param sql
     */
    public void exesql(@Param("sql") String sql);

    /**
     * 执行查询SQL
     * @param sql
     */
    public List<Map> exeSelSql(@Param("sql") String sql);

}