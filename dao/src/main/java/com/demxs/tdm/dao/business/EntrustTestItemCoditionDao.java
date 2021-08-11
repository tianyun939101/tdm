package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustTestItemCodition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请单试验项目试验条件DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustTestItemCoditionDao extends CrudDao<EntrustTestItemCodition> {
    /**
     * 根据申请单ID删除所有试验项的试验条件
     * @param entrustId 申请单ID
     */
    void deleteByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 根据试验组ID与试验项目ID获取试验项目的试验条件参数
     * @param testGroupId   试验组ID
     * @param itemId    试验项目ID
     * @return
     */
    List<EntrustTestItemCodition> listByTestGroupIdAndItemId(@Param("testGroupId")String testGroupId,@Param("itemId")String itemId);

    /**
     * 根据能力ID 查询关联条件
     * @param entrustAbilityId
     * @return
     */
    List<EntrustTestItemCodition> listByEntrustAbilityId(@Param("entrustAbilityId")String entrustAbilityId);
}