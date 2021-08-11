package com.demxs.tdm.dao.resource.haocai;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.consumeables.HaocaiCfwz;

import java.util.List;

/**
 * 耗材存放位置DAO接口
 * @author zhangdengcai
 * @version 2017-07-16
 */
@MyBatisDao
public interface HaocaiCfwzDao extends TreeDao<HaocaiCfwz> {

    /**
     * 获取子耗材存放位置
     * @param cfwz
     * @return
     */
    public List<HaocaiCfwz> getChildren(HaocaiCfwz cfwz);

    /**
     * 批量删除
     * @param cfwz
     */
    public void batchDelete(HaocaiCfwz cfwz);
}