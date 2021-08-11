package com.demxs.tdm.dao.cj;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.cj.CjZiduan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 采集字段信息DAO接口
 * @author 张仁
 * @version 2017-08-11
 */
@MyBatisDao
public interface CjZiduanDao extends CrudDao<CjZiduan> {

    /**
     * 执行SQL
     * @param sql
     */
    public void exesql(@Param("sql") String sql);

    public List<Map> findZiduan(CjZiduan cjZiduan);

    public Map findMingCheng(CjZiduan cjZiduan);
}