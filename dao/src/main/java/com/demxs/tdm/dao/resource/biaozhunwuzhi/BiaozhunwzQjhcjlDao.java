package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzQjhcjl;

import java.util.List;
import java.util.Map;

/**
 * 标准物质期间核查记录DAO接口
 * @author zhangdengcai
 * @version 2017-07-19
 */
@MyBatisDao
public interface BiaozhunwzQjhcjlDao extends CrudDao<BiaozhunwzQjhcjl> {

    /**
     * 根据计划id查询
     * @param biaozhunwzQjhcjl
     * @return
     */
    public List<BiaozhunwzQjhcjl> listByHcjhId(BiaozhunwzQjhcjl biaozhunwzQjhcjl);

    /**
     * 单个标准物质的所有核查记录列表
     * @param biaozhunwzQjhcjl
     * @return
     */
    public List<BiaozhunwzQjhcjl> dangesbsyjl(BiaozhunwzQjhcjl biaozhunwzQjhcjl);

    /**
     * 获取所有标准物质核查任务
     * @param biaozhunwzQjhcjl
     * @return
     */
    public List<Map<String, Object>> getAllhcrw(BiaozhunwzQjhcjl biaozhunwzQjhcjl);
}