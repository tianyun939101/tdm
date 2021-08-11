package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiCgqd;

/**
 * 设备采购清单管理DAO接口
 * @author zhangdengcai
 * @version 2017-07-15
 */
@MyBatisDao
public interface ShebeiCgqdDao extends CrudDao<ShebeiCgqd> {

    /**
     * 获取最大采购单id
     */
    public String getMaxCaigoudid(ShebeiCgqd ShebeiCgqd);

    /**
     * 批量删除
     * @param shebeiCgqd
     */
    public void batchDelete(ShebeiCgqd shebeiCgqd);
}