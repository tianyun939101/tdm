package com.demxs.tdm.dao.resource.huanjing;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.huanjing.HuanjingSbwh;

/**
 * 设备环境维护DAO接口
 * @author zhangdengcai
 * @version 2017-07-16
 */
@MyBatisDao
public interface HuanjingSbwhDao extends CrudDao<HuanjingSbwh> {

    /**
     * 批量删除
     * @param huanjingSbwh
     */
    public void deleteMore(HuanjingSbwh huanjingSbwh);
}