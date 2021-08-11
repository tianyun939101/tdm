package com.demxs.tdm.dao.resource.haocai;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.haocai.HaocaiKc;

import java.util.List;

/**
 * 耗材库存DAO接口
 * @author zhangdengcai
 * @version 2017-09-01
 */
@MyBatisDao
public interface HaocaiKcDao extends CrudDao<HaocaiKc> {

    /**
     * 根据 耗材名称和型号 精确查询
     * @param haocaiKc
     * @return
     */
    public List<HaocaiKc> findListByMcXh(HaocaiKc haocaiKc);
}