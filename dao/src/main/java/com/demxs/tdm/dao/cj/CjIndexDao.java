package com.demxs.tdm.dao.cj;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.cj.CjIndex;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 采集索引DAO接口
 * @author 郭金龙
 * @version 2017-10-12
 */
@MyBatisDao
public interface CjIndexDao extends CrudDao<CjIndex> {
    /**
     * 执行SQL
     * @param sql
     */
    public List<Map> exesql(@Param("sql") String sql);
}