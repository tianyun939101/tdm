package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiQjhcjh;

import java.util.List;

/**
 * 设备期间核查计划DAO接口
 * @author zhangdengcai
 * @version 2017-07-13
 */
@MyBatisDao
public interface ShebeiQjhcjhDao extends CrudDao<ShebeiQjhcjh> {

    /**
     * 根据设备id 获取期间核查计划列表
     * @param shebeiQjhcjh
     * @return
     */
    public List<ShebeiQjhcjh> lsitByShebeiid(ShebeiQjhcjh shebeiQjhcjh);

    /**
     * 批量删除
     * @param shebeiQjhcjh
     */
    public void deleteMore(ShebeiQjhcjh shebeiQjhcjh);

    /**
     * 全部设备的期间核查计划
     * @param shebeiQjhcjh
     * @return
     */
    @Override
    public List<ShebeiQjhcjh> findAllList(ShebeiQjhcjh shebeiQjhcjh);
}