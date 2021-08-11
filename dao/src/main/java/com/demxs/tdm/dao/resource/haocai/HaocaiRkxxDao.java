package com.demxs.tdm.dao.resource.haocai;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.haocai.HaocaiRkxx;

import java.util.List;

/**
 * 耗材入库信息DAO接口
 * @author zhangdengcai
 * @version 2017-07-16
 */
@MyBatisDao
public interface HaocaiRkxxDao extends CrudDao<HaocaiRkxx> {

    /**
     * 根据耗材编号 获取耗材详情
     * @param haocaiRkxx
     * @return
     */
    public HaocaiRkxx getByHcbh(HaocaiRkxx haocaiRkxx);

    /**
     * 获取最大耗材编号
     */
    public String getMaxHaocaibh(HaocaiRkxx haocaiRkxx);

    /**
     * 批量修改耗材数量
     * @param haocaiRkxxs
     */
    public void piliangxgsl(List<HaocaiRkxx> haocaiRkxxs);

    /**
     * 批量删除
     * @param haocaiRkxx
     */
    public void batchDelete(HaocaiRkxx haocaiRkxx);

    /**
     * 根据耗材名称和型号，获取入库信息表中的入库数量
     * @param haocaiRkxx
     * @return
     */
    public Integer getRukuslByMcXh(HaocaiRkxx haocaiRkxx);
}