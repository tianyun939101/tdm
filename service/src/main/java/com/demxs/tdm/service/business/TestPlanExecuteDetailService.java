package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestPlanExecuteDetailDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.TestPlanExecuteDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 试验组试验计划执行详情Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestPlanExecuteDetailService extends CrudService<TestPlanExecuteDetailDao, TestPlanExecuteDetail> {

	@Override
	public TestPlanExecuteDetail get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestPlanExecuteDetail> findList(TestPlanExecuteDetail testPlanExecuteDetail) {
		return super.findList(testPlanExecuteDetail);
	}
	
	@Override
	public Page<TestPlanExecuteDetail> findPage(Page<TestPlanExecuteDetail> page, TestPlanExecuteDetail testPlanExecuteDetail) {
		return super.findPage(page, testPlanExecuteDetail);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestPlanExecuteDetail testPlanExecuteDetail) {
		super.save(testPlanExecuteDetail);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestPlanExecuteDetail testPlanExecuteDetail) {
		super.delete(testPlanExecuteDetail);
	}


	/**
	 * 根据设备、开始时间找出此设备在指定时间后（未结束、未开始）进行的试验
	 * @param startTime
	 * @param deviceId
	 * @param sampleType
	 * @return
	 */
	public List<TestPlanExecuteDetail> findByDeviceBetweenTime(Date startTime,Date endTime, String deviceId, Integer sampleType){
		return dao.findByDeviceBetweenTime(startTime, endTime,deviceId, sampleType);
	}

	/**
	 * 删除计划执行信息
	 * @param planId
	 */
	public void deleteByPlanId(String planId) {
		dao.deleteByPlanId(planId);
	}

	/**
	 * @param planDetailId
	 */
	public void deleteByPlanDetailId(String planDetailId) {
		dao.deleteByPlanDetailId(planDetailId);
	}
	public void deleteByPlanDetailIds(List<String> planDetailId) {
		dao.deleteByPlanDetailIds(planDetailId);
	}

	/**
	 * 根据设备排期获取要使用此设备的申请的试验负责人
	 * @param code 设备编号
	 * @param start
	 * @return
	 */
	public List<User> getTestChargeByDeviceuse(String code,Date start) {
		return dao.getTestChargeByDeviceuse(code,start);
	}
}