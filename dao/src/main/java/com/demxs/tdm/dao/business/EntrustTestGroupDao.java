package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustTestGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请试验需求DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustTestGroupDao extends CrudDao<EntrustTestGroup> {
    /**
     * 根据申请单ID删除所有试验组信息
     * @param entrustId 申请单ID
     */
    void deleteByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 根据申请单ID加载试验组数据
     * @param entrustId 申请单ID
     * @return
     */
    List<EntrustTestGroup> listByEntrustId(@Param("entrustId")String entrustId);
}