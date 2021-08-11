package com.demxs.tdm.dao.resource.haocai;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.haocai.HaocaiCgqdmx;

import java.util.List;

/**
 * 耗材采购清单明细DAO接口
 * @author zhangdengcai
 * @version 2017-07-17
 */
@MyBatisDao
public interface HaocaiCgqdmxDao extends CrudDao<HaocaiCgqdmx> {

    /**
     * 根据采购清单获取采购的耗材
     * @param haocaiCgqdmx
     * @return
     */
    public List<HaocaiCgqdmx> listByCgqdzj(HaocaiCgqdmx haocaiCgqdmx);
}