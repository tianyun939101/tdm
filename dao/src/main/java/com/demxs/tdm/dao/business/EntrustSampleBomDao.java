package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustSampleBom;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 申请单样品BOMDAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustSampleBomDao extends CrudDao<EntrustSampleBom> {

    /**
     * 根据申请单ID删除所有样品BOM信息
     * @param entrustId 申请单ID
     */
    void deleteByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 根据样品组ID查询样品BOM信息
     * @param sampleGroupId 样品组ID
     */
    List<EntrustSampleBom> findBySampleGroupdId(@Param("sampleGroupId") String sampleGroupId);
}