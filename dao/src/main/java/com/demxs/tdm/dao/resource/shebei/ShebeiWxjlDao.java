package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiWxjl;
import com.demxs.tdm.domain.resource.shebei.ShebeiWxjlAll;

import java.util.List;

/**
 * 设备维修记录DAO接口
 * @author zhangdengcai
 * @version 2017-07-14
 */
@MyBatisDao
public interface ShebeiWxjlDao extends CrudDao<ShebeiWxjl> {

    /**
     * 批量删除
     * @param shebeiWxjl
     */
    public void deleteMore(ShebeiWxjl shebeiWxjl);

    /**
     * 获取全部设备的维修记录
     * @param shebeiWxjlAll
     * @return
     */
    public List<ShebeiWxjlAll> findAllList(ShebeiWxjlAll shebeiWxjlAll);
}