package com.demxs.tdm.dao.resource.haocai;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.haocai.HaocaiCgqd;

/**
 * 耗材采购清单DAO接口
 * @author zhangdengcai
 * @version 2017-07-17
 */
@MyBatisDao
public interface HaocaiCgqdDao extends CrudDao<HaocaiCgqd> {

    /**
     * 获取最大采购单id
     * @param haocaiCgqd
     * @return
     */
    public String getMaxCaigoudid(HaocaiCgqd haocaiCgqd);

    /**
     * 批量删除
     * @param haocaiCgqd
     */
    public void batchDelete(HaocaiCgqd haocaiCgqd);
}