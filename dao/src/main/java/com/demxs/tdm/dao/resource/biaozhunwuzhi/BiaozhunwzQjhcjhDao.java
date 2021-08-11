package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzQjhcjh;

import java.util.List;

/**
 * 标准物质期间核查计划DAO接口
 * @author zhangdengcai
 * @version 2017-07-19
 */
@MyBatisDao
public interface BiaozhunwzQjhcjhDao extends CrudDao<BiaozhunwzQjhcjh> {

    /**
     * 根据标准物质主键查询 计划列表
     * @param biaozhunwzQjhcjh
     * @return
     */
    public List<BiaozhunwzQjhcjh> findPageByBzwzId(BiaozhunwzQjhcjh biaozhunwzQjhcjh);

    /**
     * 批量删除
     * @param biaozhunwzQjhcjh
     */
    public void deleteMore(BiaozhunwzQjhcjh biaozhunwzQjhcjh);
}