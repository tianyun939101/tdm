package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yangpin.YangpinLbsx;

import java.util.List;

/**
 * 样品类别属性关系表DAO接口
 * @author 詹小梅
 * @version 2017-07-12
 */
@MyBatisDao
public interface YangpinLbsxDao extends CrudDao<YangpinLbsx> {
    /**
     * 获取样品类别属性
     * @param yangpinLbsx
     * @return
     */
    public List<YangpinLbsx> findyangpinLbsxsList(YangpinLbsx yangpinLbsx);

    /**
     * 根据样品类型 删除 样品类型和属性管理数据
     * @param yangpinLbsx
     */
    public void deleteByLx(YangpinLbsx yangpinLbsx);
}