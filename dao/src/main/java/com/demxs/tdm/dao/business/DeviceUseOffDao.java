package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.DeviceUseOff;
import com.demxs.tdm.domain.business.TestPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 试验设备使用结束时间DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface DeviceUseOffDao extends CrudDao<DeviceUseOff> {

    /**
     * 更新设备使用情况
     * @param deviceId 设备ID
     * @param useStation 使用工位
     * @param planEndTime 计划结束时间
     */
    void update(@Param("deviceId")String deviceId, @Param("useStation")Integer useStation, @Param("planEndTime")Date planEndTime);

    /**
     *
     * @param planDetails
     */
    void deleteByPlanDetails(@Param("planDetails") List<TestPlanDetail> planDetails);
}