package com.demxs.tdm.dao.resource.haocai;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.haocai.HaocaiMl;

import java.util.List;

/**
 * 耗材名录DAO接口
 * @author zhangdengcai
 * @version 2017-08-31
 */
@MyBatisDao
public interface HaocaiMlDao extends CrudDao<HaocaiMl> {

    /**
     * 批量删除
     * @param haocaiMl
     */
    public void batchDelete(HaocaiMl haocaiMl);

    /**
     * 根据耗材名称/型号精确查找
     * @param haocaiMl
     * @return
     */
    public List<HaocaiMl> findByMcXh(HaocaiMl haocaiMl);
}