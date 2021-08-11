package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustTestGroupAbility;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验组试验能力DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustTestGroupAbilityDao extends CrudDao<EntrustTestGroupAbility> {
    /**
     * 根据申请单ID删除所有试验组中试验能力信息
     * @param entrustId 申请单ID
     */
    void deleteByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 根据试验组ID加载试验能力数据
     * @param testGroupId 试验组ID
     * @return
     */
    List<EntrustTestGroupAbility> listByTestGroupId(@Param("testGroupId")String testGroupId);
    /**
     * 根据试验组ID加载试验能力数据，包含试验项目详情（试验序列只返回ID）
     * @param testGroupId 试验组ID
     * @return
     */
    List<EntrustTestGroupAbility> listDetailByTestGroupId(@Param("testGroupId")String testGroupId);
}