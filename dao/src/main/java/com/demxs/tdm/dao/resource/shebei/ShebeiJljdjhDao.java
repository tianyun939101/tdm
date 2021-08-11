package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjh;

import java.util.List;

/**
 * 设备计量检定计划DAO接口
 * @author zhangdengcai
 * @version 2017-07-13
 */
@MyBatisDao
public interface ShebeiJljdjhDao extends CrudDao<ShebeiJljdjh> {

    /**
     * 根据设备主键获取检定计划
     * @param shebeiJljdjh
     * @return
     */
   public List<ShebeiJljdjh> getByShebeiid(ShebeiJljdjh shebeiJljdjh);

    public void batchDelete(ShebeiJljdjh shebeiJljdjh);
}