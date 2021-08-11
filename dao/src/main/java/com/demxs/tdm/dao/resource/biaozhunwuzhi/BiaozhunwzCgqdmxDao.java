package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCgqdmx;

import java.util.List;

/**
 * 标准物质采购清单明细DAO接口
 * @author zhangdengcai
 * @version 2017-07-18
 */
@MyBatisDao
public interface BiaozhunwzCgqdmxDao extends CrudDao<BiaozhunwzCgqdmx> {

    /**
     * 根据采购单主键 获取采购的标准物质
     * @param biaozhunwzCgqdmx
     * @return
     */
    public List<BiaozhunwzCgqdmx> listByCgdzj(BiaozhunwzCgqdmx biaozhunwzCgqdmx);
}