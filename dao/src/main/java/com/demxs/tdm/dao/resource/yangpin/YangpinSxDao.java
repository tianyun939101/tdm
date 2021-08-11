package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yangpin.YangpinSx;
import org.apache.ibatis.annotations.Param;

/**
 * 样品属性DAO接口
 * @author 詹小梅
 * @version 2017-07-04
 */
@MyBatisDao
public interface YangpinSxDao extends CrudDao<YangpinSx> {
    /**
     * 属性删除
     * @param yangpinSx
     */
    public void deleteData(YangpinSx yangpinSx);

    /**
     * 出库单动态加列后更新样品属性的filed字段值
     * @param id 属性id
     * @param filed   属性对应的filed value
     */
    public void updateFiled(@Param("id") String id, @Param("filed") String filed);
	
}