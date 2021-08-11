package com.demxs.tdm.dao.cj;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.cj.Wenjian;
import org.apache.ibatis.annotations.Param;

/**
 * 采集文件表DAO接口
 * @author 张仁
 * @version 2017-08-21
 */
@MyBatisDao
public interface WenjianDao extends CrudDao<Wenjian> {
    /**
     * 执行SQL
     * @param sql
     */
    public void exesql(@Param("sql") String sql);
}