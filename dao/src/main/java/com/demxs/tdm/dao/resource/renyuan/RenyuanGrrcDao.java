package com.demxs.tdm.dao.resource.renyuan;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.renyuan.RenyuanGrrc;

/**
 * 个人日程DAO接口
 * @author zhangdengcai
 * @version 2017-08-09
 */
@MyBatisDao
public interface RenyuanGrrcDao extends CrudDao<RenyuanGrrc> {

    /**
     * 批量删除
     * @param renyuanGrrc
     */
    public void batchDelete(RenyuanGrrc renyuanGrrc);
}