package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzMl;

import java.util.List;

/**
 * 标准物质名录DAO接口
 * @author zhangdengcai
 * @version 2017-08-31
 */
@MyBatisDao
public interface BiaozhunwzMlDao extends CrudDao<BiaozhunwzMl> {

    /**
     * 批量删除
     * @param biaozhunwzMl
     */
    public void batchDelete(BiaozhunwzMl biaozhunwzMl);

    /**
     * 根据 名称和型号 精确查询
     * @param biaozhunwzMl
     * @return
     */
    public List<BiaozhunwzMl> findByMcXh(BiaozhunwzMl biaozhunwzMl);

    /**
     * 根据编码精确查询
     * @param biaozhunwzMl
     * @return
     */
    public List<BiaozhunwzMl> findByBm(BiaozhunwzMl biaozhunwzMl);
}