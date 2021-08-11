package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.TestPlanExecuteDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 试验组试验计划执行详情DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestPlanExecuteDetailDao extends CrudDao<TestPlanExecuteDetail> {

	/**
	 * @param startTime
	 * @param endTime
	 * @param deviceId 逗号分隔
	 * @param sampleType 1,2,3  @see EntrustConstants.Sample_Type
	 * @return
	 */
	List<TestPlanExecuteDetail> findByDeviceBetweenTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("deviceId") String deviceId, @Param("sampleType") Integer sampleType);

	/**
	 * 删除计划执行信息
	 * @param planId
	 */
	void deleteByPlanId(@Param("planId") String planId);

	/**
	 * @param planDetailId
	 */
	void deleteByPlanDetailId(@Param("planDetailId") String planDetailId);
	void deleteByPlanDetailIds(@Param("planDetailIds") List<String> planDetailIds);

	/**
	 * 根据设备排期获取要使用此设备的申请的试验负责人
	 * @param code 设备编号
	 * @param start
	 * @return
	 */
	List<User> getTestChargeByDeviceuse(@Param("code") String code, @Param("start") Date start);
}