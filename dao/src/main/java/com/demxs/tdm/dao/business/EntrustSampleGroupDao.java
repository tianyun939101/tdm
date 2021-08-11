package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustSampleGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请试验组中的样品组信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustSampleGroupDao extends CrudDao<EntrustSampleGroup> {
    /**
     * 根据申请单ID删除所有样品组信息
     * @param entrustId 申请单ID
     */
    void deleteByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 根据试验组ID加载样品组数据
     * @param testGroupId 试验组ID
     * @return
     */
    List<EntrustSampleGroup> listByTestGroupId(@Param("testGroupId")String testGroupId);

    /**
     * 根据申请单ID加载样品组数据
     * @param entrustId 申请单ID
     * @return
     */
    List<EntrustSampleGroup> listByEntrustId(String entrustId);

    /**
     * 更改样品组状态
     * @param id
     * @param sampleStatus
     */
    void updateStatus(@Param("id")String id, @Param("sampleStatus")Integer sampleStatus);

    /**
     * 获取样品组根据入库状态
     * @param entrustId
     * @return
     */
    List<EntrustSampleGroup> listBySampleStatus(@Param("entrustId") String entrustId,
                                         @Param("sampleStatus")Integer sampleStatus);


    void updateLabInfo(@Param("id")String id,@Param("labInfoId")String labInfoId,@Param("labInfoName") String labInfoName);
}