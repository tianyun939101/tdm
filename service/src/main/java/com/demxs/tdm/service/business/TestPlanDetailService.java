package com.demxs.tdm.service.business;

import com.alibaba.fastjson.JSON;
import com.demxs.tdm.dao.business.TestPlanDao;
import com.demxs.tdm.dao.business.TestPlanDetailDao;
import com.demxs.tdm.dao.business.TestTaskDao;
import com.demxs.tdm.dao.sys.SysDataChangeLogDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.TestPlan;
import com.demxs.tdm.domain.business.TestPlanDetail;
import com.demxs.tdm.domain.business.TestPlanExecuteDetail;
import com.demxs.tdm.domain.sys.SysDataChangeLog;
import com.demxs.tdm.comac.common.constant.SysConstants;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 试验组试验计划详情Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestPlanDetailService extends CrudService<TestPlanDetailDao, TestPlanDetail> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestPlanDetailService.class);

	@Resource
	private TestPlanDao testPlanDao;
	@Resource
	private TestPlanExecuteDetailService testPlanExecuteDetailService;
	@Resource
	private SysDataChangeLogDao sysDataChangeLogDao;
	@Resource
	private TestTaskDao taskdao;
	@Override
	public TestPlanDetail get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestPlanDetail> findList(TestPlanDetail testPlanDetail) {
		return super.findList(testPlanDetail);
	}
	
	@Override
	public Page<TestPlanDetail> findPage(Page<TestPlanDetail> page, TestPlanDetail testPlanDetail) {
		return super.findPage(page, testPlanDetail);
	}
	
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void update(String taskId,TestPlanDetail testPlanDetail) {
		this.save(testPlanDetail);
	}

	public void saveTaskPlanChangeLog(String taskId, TestPlanDetail testPlanDetail) {
		String lineSeperator = "\r\n";
		TestPlanDetail oldData = this.get(testPlanDetail.getId());
		StringBuffer message = new StringBuffer();
//		message.append("试验项 "+testPlanDetail.getAbilityName()+" 计划变更。");
		if(oldData.getPlanStartDate().getTime() != testPlanDetail.getPlanStartDate().getTime() ||
				oldData.getPlanEndDate().getTime() != testPlanDetail.getPlanEndDate().getTime()){
			message.append("计划时间由 " + DateUtils.formatDateTime(oldData.getPlanStartDate()) + " - " + DateUtils.formatDateTime(oldData.getPlanEndDate()));
			message.append("，");
			message.append(" 变为 " + DateUtils.formatDateTime(testPlanDetail.getPlanStartDate()) + " - " + DateUtils.formatDateTime(testPlanDetail.getPlanEndDate()));
			message.append("。"+lineSeperator);
		}
		boolean sampleChanges = false;
		for (int i = 0; i < oldData.getTestPlanExecuteDetails().size(); i++) {
			TestPlanExecuteDetail old = oldData.getTestPlanExecuteDetails().get(i);
			TestPlanExecuteDetail executeDetail = testPlanDetail.getTestPlanExecuteDetails().get(i);
			if(old.getPlanStartDate().getTime() != executeDetail.getPlanStartDate().getTime() ||
					old.getPlanEndDate().getTime() != executeDetail.getPlanEndDate().getTime() ||
					!old.getDeviceId().equals(executeDetail.getDeviceId()) || !old.getStation().equals(executeDetail.getStation())){
				if (!sampleChanges) {
					sampleChanges = true;
					message.append("样品排期变化："+lineSeperator);
				}
				message.append(old.getSampleCode()+"：");
				if (old.getPlanStartDate().getTime() != executeDetail.getPlanStartDate().getTime() ||
						old.getPlanEndDate().getTime() != executeDetail.getPlanEndDate().getTime()) {
					message.append("计划时间由 " + DateUtils.formatDateTime(oldData.getPlanStartDate()) + " - " + DateUtils.formatDateTime(oldData.getPlanEndDate()));
					message.append("，");
					message.append(" 变为 " + DateUtils.formatDateTime(testPlanDetail.getPlanStartDate()) + " - " + DateUtils.formatDateTime(testPlanDetail.getPlanEndDate()));
					message.append("；");
				}
				if(!old.getDeviceId().equals(executeDetail.getDeviceId())){
					message.append("设备由 " + old.getDeviceCode() + "变为 " + executeDetail.getDeviceCode());
					message.append("；");
				}
				if(!old.getStation().equals(executeDetail.getStation())){
					message.append("工位由 " + old.getStation() + "变为 " + executeDetail.getStation());
					message.append("；");
				}
				message.append(lineSeperator);
			}
		}
		if (sampleChanges) {
			SysDataChangeLog sysDataChangeLog = new SysDataChangeLog();
			sysDataChangeLog.setId(IdGen.uuid());
			sysDataChangeLog.setServiceType(400);
			sysDataChangeLog.setTitle(testPlanDetail.getAbilityName());
			sysDataChangeLog.setMessage(message.toString().substring(0,Math.min(message.length(),2000)));
			sysDataChangeLog.setServiceId(taskdao.get(taskId).getParentId());
			try {
				sysDataChangeLog.setOldJson(JSON.toJSONString(oldData).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {

			}
			try {
				sysDataChangeLog.setNewJson(JSON.toJSONString(testPlanDetail).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {

			}
			sysDataChangeLog.setChangeTime(new Date());
			sysDataChangeLog.setChangeUser(UserUtils.getUser());
			sysDataChangeLogDao.insert(sysDataChangeLog);
		}
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void update(TestPlanDetail testPlanDetail) {
		super.save(testPlanDetail);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestPlanDetail testPlanDetail) {
		if (StringUtils.isEmpty(testPlanDetail.getParentId())) {
			testPlanDetail.setParentId(SysConstants.ROOT);
		}
		if (testPlanDetail.getTask() != null) {
			saveTaskPlanChangeLog(testPlanDetail.getTask().getId(), testPlanDetail);
		}else{//未生成任务，直接删除
		}
		testPlanExecuteDetailService.deleteByPlanDetailId(testPlanDetail.getId());
		super.save(testPlanDetail);
		List<TestPlanExecuteDetail> executeDetails = testPlanDetail.getTestPlanExecuteDetails();
		TestPlan testPlan = testPlanDetail.getTestPlan();
		if (testPlan == null) {
			if (StringUtils.isNotEmpty(testPlanDetail.getPlanId())) {
				testPlan = testPlanDao.get(testPlanDetail.getPlanId());
			}
		}
		if (executeDetails != null && executeDetails.size() > 0) {
			for (TestPlanExecuteDetail executeDetail : executeDetails) {
//				if (testPlanDetail.getTask() == null) {
					executeDetail.setIsNewRecord(true);
					executeDetail.setId(IdGen.uuid());
//				}
				executeDetail.setPlanId(testPlan.getId());
				executeDetail.settGroupId(testPlan.getTGroupId());
				executeDetail.setEntrustId(testPlan.getEntrustId());
				executeDetail.setEntrustCode(testPlan.getEntrustCode());
				executeDetail.setPlanDetailId(testPlanDetail.getId());
				testPlanExecuteDetailService.save(executeDetail);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestPlanDetail testPlanDetail) {
		testPlanExecuteDetailService.deleteByPlanDetailId(testPlanDetail.getId());
		super.delete(testPlanDetail);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteById(String id) {
		testPlanExecuteDetailService.deleteByPlanDetailId(id);
		TestPlanDetail testPlanDetail = new TestPlanDetail();
		testPlanDetail.setId(id);
		super.delete(testPlanDetail);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByPlanId(String planId) {
		dao.deleteByPlanId(planId);
	}

	/**
	 * 获取计划中的计划详情
	 * @param planId	计划ID
	 * @return
	 * @throws ServiceException
     */
	public List<TestPlanDetail> listByPlan(String planId) throws ServiceException{
		return this.dao.findByPlan(planId);
	}

}