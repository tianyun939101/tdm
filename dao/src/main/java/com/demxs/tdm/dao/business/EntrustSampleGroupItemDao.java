package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.EntrustSampleGroupItem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 申请试验组中样品组的样品信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustSampleGroupItemDao extends CrudDao<EntrustSampleGroupItem> {
    /**
     * 根据申请单ID删除所有样品信息
     * @param entrustId 申请单ID
     */
    void deleteByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 根据试验组ID 查询样品
     * @param tGroupId
     * @return
     */
    List<EntrustSampleGroupItem> findByTGroupId(@Param("page") Page<EntrustSampleGroupItem> page, @Param("tGroupId") String tGroupId, @Param("sn") String sn);

    /**
     * 更改样品状态
     * @param id
     * @param status
     * @param sampleLocation
     * @param sampleInDate
     * @param operater
     */
    void updateSampleStatus(@Param("id")String id, @Param("status")Integer status,
                            @Param("sampleLocation")String sampleLocation,
                            @Param("sampleInDate")Date sampleInDate,
                            @Param("operater")User operater,
                            @Param("updateDate")Date update);

    /**
     * 获取入库的样品数量
     * @return
     */
    Integer getPutCount();

    /**
     * 打印标签样品列表
     * @param entrustSampleGroupItem
     * @return
     */
    List<EntrustSampleGroupItem> printPage(EntrustSampleGroupItem entrustSampleGroupItem);


    void updateLabInfo(@Param("id")String id,@Param("labInfoId")String labInfoId,@Param("labInfoName") String labInfoName);


    /**
     *
     * @param tGroupId 试验组ID
     * @param sGroupId 样品组ID
     * @param entrustId 申请单id
     * @return
     */
    List<EntrustSampleGroupItem> findListByGroup(@Param("tGroupId")String tGroupId,
                                                 @Param("sGroupId")String sGroupId,
                                                 @Param("entrustId")String entrustId);
}