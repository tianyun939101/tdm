package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCgqd;

/**
 * 标准物质采购清单DAO接口
 * @author zhangdengcai
 * @version 2017-07-18
 */
@MyBatisDao
public interface BiaozhunwzCgqdDao extends CrudDao<BiaozhunwzCgqd> {

    /**
     * 获取最大采购单id
     */
    public String getMaxCaigoudid(BiaozhunwzCgqd biaozhunwzCgqd);

    /**
     * 批量删除
     * @param biaozhunwzCgqd
     */
    public void batchDelete(BiaozhunwzCgqd biaozhunwzCgqd);
}