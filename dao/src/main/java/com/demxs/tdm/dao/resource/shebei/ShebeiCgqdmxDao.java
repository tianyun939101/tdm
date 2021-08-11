package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiCgqdmx;

import java.util.List;

/**
 * 设备采购清单明细DAO接口
 * @author zhangdengcai
 * @version 2017-07-15
 */
@MyBatisDao
public interface ShebeiCgqdmxDao extends CrudDao<ShebeiCgqdmx> {

    /**
     * 根据采购单id获取采购的设备
     * @param shebeiCgqdmx
     * @return
     */
    public List<ShebeiCgqdmx> listByShebeicgqdzj(ShebeiCgqdmx shebeiCgqdmx);

    /**
     * 批量插入
     * @param shebeiCgqdmxs
     */
    public void batchInsert(List<ShebeiCgqdmx> shebeiCgqdmxs);
}