package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiQjhcjl;

import java.util.List;
import java.util.Map;

/**
 * 设备期间核查记录DAO接口
 * @author zhangdengcai
 * @version 2017-07-13
 */
@MyBatisDao
public interface ShebeiQjhcjlDao extends CrudDao<ShebeiQjhcjl> {

    /**
     * 根据期间核查计划id 获取期间记录
     * @param shebeiQjhcjl
     * @return
     */
    public List<ShebeiQjhcjl> listByHcjhid(ShebeiQjhcjl shebeiQjhcjl);

    /**
     * 单个设备的核查记录
     * @param shebeiQjhcjl
     * @return
     */
    public List<ShebeiQjhcjl> dangesbsyjl(ShebeiQjhcjl shebeiQjhcjl);

    /**
     * 全部设备的期间核查记录
     * @param shebeiQjhcjl
     * @return
     */
    @Override
    public List<ShebeiQjhcjl> findAllList(ShebeiQjhcjl shebeiQjhcjl);

    /**
     * 获取所有设备期间核查任务
     * @param shebeiQjhcjl
     * @return
     */
    public List<Map<String, Object>> getAllhcrw(ShebeiQjhcjl shebeiQjhcjl);
}