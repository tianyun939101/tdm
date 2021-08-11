package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustInfoDao;
import com.demxs.tdm.dao.business.TestPlanDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.TestUnit;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.vo.LabVO;
import com.demxs.tdm.domain.business.vo.LineUpVO;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.domain.lab.LabTestSequence;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.service.ability.TestItemService;
import com.demxs.tdm.service.ability.TestUnitService;
import com.demxs.tdm.service.lab.LabInfoService;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 试验组试验计划Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestPlanService extends CrudService<TestPlanDao, TestPlan> {
	@Resource
	private DevicePlanCaculator devicePlanCaculator;
	@Resource
	private TestPlanDetailService testPlanDetailService;
	@Resource
	private TestPlanExecuteDetailService testPlanExecuteDetailService;
	@Resource
	private EntrustInfoService entrustInfoService;
	@Resource
	private TestUnitService testUnitService;
	@Resource
	private ShebeiDao shebeiDao;
	@Resource
	private EntrustTestGroupService entrustTestGroupService;
	@Resource
	private LabInfoService labInfoService;
	@Resource
	private TestItemService testItemService;
	@Resource
	private EntrustInfoDao entrustInfoDao;
	@Autowired
	private AdjustPlanLogService adjustPlanLogService;

	@Override
	public TestPlan get(String id) {
		return super.get(id);
	}

	public TestPlan getByTestGroupId(String testGroupId){
		TestPlan testPlan = dao.getByTestGroupId(testGroupId);
		if (testPlan == null) {
			return testPlan;
		}
		setTestplanLab(testPlan);
		return testPlan;
	}

	public TestPlan getByPlanDetailId(String planDetailId){
		TestPlanDetail testPlanDetail = testPlanDetailService.get(planDetailId);
		TestPlan testPlan = this.get(testPlanDetail.getPlanId());
		if (testPlan == null) {
			return testPlan;
		}
		setTestplanLab(testPlan);
		return testPlan;
	}

	/**
	 * 根据使用情况调整持续时间
	 * @param stopDate	设备停止时间
	 * @param startDate 设备启动时间
     */
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public List<EntrustInfo> adjustPlan(String labId, Date stopDate, Date startDate){
		List<EntrustInfo> result = new ArrayList<>();

		if(StringUtils.isEmpty(labId)){
			labId = UserUtils.getUser().getLabInfoId();
		}
		List<TestPlan> testPlenList = this.dao.findByLab(labId);
		//List<TestPlan> testPlenList =
		for (TestPlan testPlan : testPlenList){
			//如果计划的结束时间在设备停止时间之前则继续循环
			if(testPlan.getEndDate().before(stopDate)){
				continue;
			}

			testPlan = this.dao.get(testPlan.getId());

			List<TestPlanDetail> testPlanDetailList = testPlan.getTestPlanDetailList();

			TestPlanDetail root = new TestPlanDetail();
			root.setId("0");
			testPlanDetailList.add(root);
			DevicePlanCaculator.SequenceTreeGenerator sequenceTreeGenerator = new DevicePlanCaculator.SequenceTreeGenerator(testPlanDetailList, "0");
			sequenceTreeGenerator.list2tree();

			TestPlanDetail startAdjustPlanDetail = findAdjustPlanDetail(root,stopDate);
			if(startAdjustPlanDetail == null){
				continue;
			}
			adjustNextSiblings(sequenceTreeGenerator,startAdjustPlanDetail,stopDate,startDate);

			long duration = startDate.getTime() - stopDate.getTime();
			//设备启停范围在计划时间之内
			if((testPlan.getStartDate().before(stopDate) || testPlan.getStartDate().equals(stopDate))
					&& testPlan.getStartDate().before(startDate)
					&& testPlan.getEndDate().after(stopDate)
					&& (testPlan.getEndDate().after(startDate) || testPlan.getEndDate().equals(startDate))){
				testPlan.setEndDate(DateUtils.addMilliseconds(testPlan.getEndDate(),(int)duration));
			}
			//计划开始时间在停止时间与启动时间之前,计划结束时间在停止时间之后,计划结束时间在启动时间之前
			else if((testPlan.getStartDate().before(stopDate) || testPlan.getStartDate().equals(stopDate))
					&& testPlan.getStartDate().before(startDate)
					&& testPlan.getEndDate().after(stopDate)
					&& (testPlan.getEndDate().before(startDate) || testPlan.getEndDate().equals(startDate))){
				testPlan.setEndDate(DateUtils.addMilliseconds(testPlan.getEndDate(),(int)duration));
			}
			//计划开始与结束时间在设备启停范围内
			else if((testPlan.getStartDate().after(stopDate) || testPlan.getStartDate() .equals(stopDate))
					&& testPlan.getStartDate().before(startDate)
					&& testPlan.getEndDate().after(stopDate)
					&& (testPlan.getEndDate().before(startDate) || testPlan.getEndDate().equals(startDate))){
				testPlan.setStartDate(DateUtils.addMilliseconds(testPlan.getStartDate(),(int)duration));
				testPlan.setEndDate(DateUtils.addMilliseconds(testPlan.getEndDate(),(int)duration));
			}
			//计划开始时间在设备启停范围内,计划结束时间在设备开始时间之后
			else if((testPlan.getStartDate().after(stopDate) || testPlan.getStartDate().equals(stopDate))
					&& (testPlan.getStartDate().before(startDate) || testPlan.getStartDate().equals(startDate))
					&& testPlan.getEndDate().after(startDate)){
				testPlan.setStartDate(DateUtils.addMilliseconds(testPlan.getStartDate(),(int)duration));
				testPlan.setEndDate(DateUtils.addMilliseconds(testPlan.getEndDate(),(int)duration));
			}
			//计划开始与结束时间都在设备启动时间之后
			else if((testPlan.getStartDate().after(startDate) || testPlan.getStartDate()
					.equals(startDate)) && testPlan.getEndDate().after(startDate)){
				testPlan.setStartDate(DateUtils.addMilliseconds(testPlan.getStartDate(),(int)duration));
				testPlan.setEndDate(DateUtils.addMilliseconds(testPlan.getEndDate(),(int)duration));
			}
			super.save(testPlan);
			EntrustInfo entrustInfo = entrustInfoService.get(testPlan.getEntrustId());
			if(!result.contains(entrustInfo)){
				result.add(entrustInfo);
			}

		}
		adjustPlanLogService.savePlanLog(labId,result,stopDate,startDate);
		return result;
	}

	/**
	 * 获取最开始需要调整的试验项
	 * @param root
	 * @param stopDate
     */
	private TestPlanDetail findAdjustPlanDetail(TestPlanDetail root,Date stopDate){
		for (TestPlanDetail testPlanDetail : root.getChildDetail()) {
			if(testPlanDetail.getPlanEndDate().before(stopDate)){
				continue;
			}
			if (DevicePlanCaculator.isUnit(testPlanDetail)) {
				return testPlanDetail;
			}else if(DevicePlanCaculator.isSequence(testPlanDetail) || DevicePlanCaculator.isItem(testPlanDetail)){
				TestPlanDetail result = findAdjustPlanDetail(testPlanDetail,stopDate);
				if(result==null){
					continue;
				}
				return result;
			}
		}
		return null;
	}

	/**
	 * 调整试验项的开始时间与结束时间
	 * @param planDetail
	 * @param stopDate
	 * @param startDate
     */
	private void adjustPlanDetailDate(TestPlanDetail planDetail,Date stopDate,Date startDate){
		long duration = startDate.getTime() - stopDate.getTime();
		//设备启停范围在计划时间之内
		if((planDetail.getPlanStartDate().before(stopDate) || planDetail.getPlanStartDate().equals(stopDate))
				&& planDetail.getPlanStartDate().before(startDate)
				&& planDetail.getPlanEndDate().after(stopDate)
				&& (planDetail.getPlanEndDate().after(startDate) || planDetail.getPlanEndDate().equals(startDate))){

			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始时间在停止时间与启动时间之前,计划结束时间在停止时间之后,计划结束时间在启动时间之前
		else if((planDetail.getPlanStartDate().before(stopDate) || planDetail.getPlanStartDate().equals(stopDate))
				&& planDetail.getPlanStartDate().before(startDate)
				&& planDetail.getPlanEndDate().after(stopDate)
				&& (planDetail.getPlanEndDate().before(startDate) || planDetail.getPlanEndDate().equals(startDate))){
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始与结束时间在设备启停范围内
		else if((planDetail.getPlanStartDate().after(stopDate) || planDetail.getPlanStartDate() .equals(stopDate))
				&& planDetail.getPlanStartDate().before(startDate)
				&& planDetail.getPlanEndDate().after(stopDate)
				&& (planDetail.getPlanEndDate().before(startDate) || planDetail.getPlanEndDate().equals(startDate))){
			planDetail.setPlanStartDate(DateUtils.addMilliseconds(planDetail.getPlanStartDate(),(int)duration));
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始时间在设备启停范围内,计划结束时间在设备开始时间之后
		else if((planDetail.getPlanStartDate().after(stopDate) || planDetail.getPlanStartDate().equals(stopDate))
				&& (planDetail.getPlanStartDate().before(startDate) || planDetail.getPlanStartDate().equals(startDate))
				&& planDetail.getPlanEndDate().after(startDate)){
			planDetail.setPlanStartDate(DateUtils.addMilliseconds(planDetail.getPlanStartDate(),(int)duration));
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始与结束时间都在设备启动时间之后
		else if((planDetail.getPlanStartDate().after(startDate) || planDetail.getPlanStartDate()
				.equals(startDate)) && planDetail.getPlanEndDate().after(startDate)){
			planDetail.setPlanStartDate(DateUtils.addMilliseconds(planDetail.getPlanStartDate(),(int)duration));
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
	}

	/**
	 * 调整试验详情的开始时间与结束时间
	 * @param planDetail
	 * @param stopDate
	 * @param startDate
	 */
	private void adjustExecuteDetailDate(TestPlanExecuteDetail planDetail, Date stopDate, Date startDate){
		long duration = startDate.getTime() - stopDate.getTime();
		//设备启停范围在计划时间之内
		if((planDetail.getPlanStartDate().before(stopDate) || planDetail.getPlanStartDate().equals(stopDate))
				&& planDetail.getPlanStartDate().before(startDate)
				&& planDetail.getPlanEndDate().after(stopDate)
				&& (planDetail.getPlanEndDate().after(startDate) || planDetail.getPlanEndDate().equals(startDate))){

			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始时间在停止时间与启动时间之前,计划结束时间在停止时间之后,计划结束时间在启动时间之前
		else if((planDetail.getPlanStartDate().before(stopDate) || planDetail.getPlanStartDate().equals(stopDate))
				&& planDetail.getPlanStartDate().before(startDate)
				&& planDetail.getPlanEndDate().after(stopDate)
				&& (planDetail.getPlanEndDate().before(startDate) || planDetail.getPlanEndDate().equals(startDate))){
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始与结束时间在设备启停范围内
		else if((planDetail.getPlanStartDate().after(stopDate) || planDetail.getPlanStartDate() .equals(stopDate))
				&& planDetail.getPlanStartDate().before(startDate)
				&& planDetail.getPlanEndDate().after(stopDate)
				&& (planDetail.getPlanEndDate().before(startDate) || planDetail.getPlanEndDate().equals(startDate))){
			planDetail.setPlanStartDate(DateUtils.addMilliseconds(planDetail.getPlanStartDate(),(int)duration));
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始时间在设备启停范围内,计划结束时间在设备开始时间之后
		else if((planDetail.getPlanStartDate().after(stopDate) || planDetail.getPlanStartDate().equals(stopDate))
				&& (planDetail.getPlanStartDate().before(startDate) || planDetail.getPlanStartDate().equals(startDate))
				&& planDetail.getPlanEndDate().after(startDate)){
			planDetail.setPlanStartDate(DateUtils.addMilliseconds(planDetail.getPlanStartDate(),(int)duration));
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
		//计划开始与结束时间都在设备启动时间之后
		else if((planDetail.getPlanStartDate().after(startDate) || planDetail.getPlanStartDate()
				.equals(startDate)) && planDetail.getPlanEndDate().after(startDate)){
			planDetail.setPlanStartDate(DateUtils.addMilliseconds(planDetail.getPlanStartDate(),(int)duration));
			planDetail.setPlanEndDate(DateUtils.addMilliseconds(planDetail.getPlanEndDate(),(int)duration));
		}
	}

	/**
	 * 更新试验计划时间
	 * @param treeGenerator
	 * @param currentPlanDetail
	 * @param stopDate
	 * @param startDate
     */
	private void adjustNextSiblings(DevicePlanCaculator.SequenceTreeGenerator treeGenerator, TestPlanDetail currentPlanDetail, Date stopDate, Date startDate){
		if (currentPlanDetail.getId().equals("0")){
			return;
		}

		adjustPlanDetailDate(currentPlanDetail,stopDate,startDate);
		testPlanDetailService.update(currentPlanDetail);
		//更新试验计划执行详情
		if(DevicePlanCaculator.isUnit(currentPlanDetail)){
			adjustExecuteDetail(currentPlanDetail,stopDate,startDate);
			if (currentPlanDetail.getTask() != null) {
				testPlanDetailService.saveTaskPlanChangeLog(currentPlanDetail.getTask().getId(), currentPlanDetail);
			}
		}
		TestPlanDetail parentPlanDetail = treeGenerator.findParent(currentPlanDetail.getParentId());
		List<TestPlanDetail> childDetails = parentPlanDetail.getChildDetail();
		for (TestPlanDetail planDetail : childDetails) {
			if (planDetail.getSort() > currentPlanDetail.getSort()) {
				adjustPlanDetailDate(planDetail, stopDate, startDate);
				//更新试验计划执行详情
				if(DevicePlanCaculator.isUnit(planDetail)){
					adjustExecuteDetail(planDetail,stopDate,startDate);
					if (currentPlanDetail.getTask() != null) {
						testPlanDetailService.saveTaskPlanChangeLog(planDetail.getTask().getId(), planDetail);
					}
				}else if(DevicePlanCaculator.isSequence(planDetail) || DevicePlanCaculator.isItem(planDetail)){
					adjustSubDetail(planDetail, stopDate, startDate);
				}
				testPlanDetailService.update(planDetail);
			}
		}
		adjustNextSiblings(treeGenerator, parentPlanDetail, stopDate, startDate);
	}

	private void adjustSubDetail(TestPlanDetail parentPlanDetail,Date stopDate,Date startDate){
		List<TestPlanDetail> childDetails = parentPlanDetail.getChildDetail();
		for (TestPlanDetail planDetail : childDetails) {
			adjustPlanDetailDate(planDetail, stopDate, startDate);
			//更新试验计划执行详情
			if(DevicePlanCaculator.isUnit(planDetail)){
				adjustExecuteDetail(planDetail,stopDate,startDate);
				if (planDetail.getTask() != null) {
					testPlanDetailService.saveTaskPlanChangeLog(planDetail.getTask().getId(), planDetail);
				}
			}else if(DevicePlanCaculator.isSequence(planDetail) || DevicePlanCaculator.isItem(planDetail)){
				adjustSubDetail(planDetail, stopDate, startDate);
			}
			testPlanDetailService.update(planDetail);
		}
	}

	private void adjustExecuteDetail(TestPlanDetail planDetail,Date stopDate,Date startDate){
		TestPlanExecuteDetail planExecuteDetail = new TestPlanExecuteDetail();
		planExecuteDetail.setPlanId(planDetail.getPlanId());
		planExecuteDetail.setPlanDetailId(planDetail.getId());
		List<TestPlanExecuteDetail> testPlanExecuteDetails = testPlanExecuteDetailService.findList(planExecuteDetail);
		for(TestPlanExecuteDetail testPlanExecuteDetail : testPlanExecuteDetails){
			adjustExecuteDetailDate(testPlanExecuteDetail,stopDate,startDate);
			testPlanExecuteDetailService.save(testPlanExecuteDetail);
		}
	}

	private void setTestplanLab(TestPlan testPlan) {
		EntrustTestGroup testGroup = entrustTestGroupService.get(testPlan.getTGroupId());
		EntrustInfo entrustInfo = entrustInfoService.get(testGroup.getEntrustId());
		testPlan.setLabId(entrustInfo.getLabId());
	}

	@Override
	public List<TestPlan> findList(TestPlan testPlan) {
		return super.findList(testPlan);
	}
	
	@Override
	public Page<TestPlan> findPage(Page<TestPlan> page, TestPlan testPlan) {
		return super.findPage(page, testPlan);
	}

	/**
	 * 当前未完成的计划
	 * @return
	 */
	public List<TestPlan> findNotCompleted(){
		return dao.findNotCompleted();
	}

	/**
	 * 设备占用情况
	 * @return
	 */
	public List<Map<String,Object>> deviceTaskList(String testUnitId,String entrustId,String labId){
		TestUnit testUnit = testUnitService.get(testUnitId);
		EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
		List<Shebei> shebeis = devicePlanCaculator.getShebeis(testUnit,labId);
		return deviceTaskList(entrustInfo, shebeis,null,null);
	}

	/**
	 * @param deviceId
	 * @param startTime  default : 当前时间
	 * @param endTime
	 * @return
	 */
	public List<Map<String, Object>> deviceTaskList(String deviceId, Date startTime, Date endTime) {
		List<Shebei> deviceList = new ArrayList<>();
		for (String id : deviceId.split(",")) {
			deviceList.add(shebeiDao.get(id));
		}
		return deviceTaskList(null, deviceList, startTime, endTime);
	}
	private List<Map<String, Object>> deviceTaskList(EntrustInfo entrustInfo, List<Shebei> shebeis, Date startTime, Date endTime) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Shebei shebei : shebeis) {
			if (StringUtils.isEmpty(shebei.getKeshiyanypsl())) {
				continue;
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("id", shebei.getId());
			obj.put("name", shebei.getShebeibh());
			obj.put("code", shebei.getShebeibh());
			obj.put("type","device");
			obj.put("resource","");
			obj.put("note","");
			obj.put("entrustCode","");
			list.add(obj);
			if(startTime == null){
				startTime = new Date();
			}
			List<TestPlanExecuteDetail> executeDetails = testPlanExecuteDetailService.findByDeviceBetweenTime(startTime,endTime ,shebei.getId(), entrustInfo == null ? null : entrustInfo.getSampleType());
			for (int i = 0; i < Integer.parseInt(shebei.getKeshiyanypsl()); i++) {
				Map<String,Object> station = new HashMap<>();
				int no = i+1;
				String stationId = shebei.getId() + no;
				station.put("id", stationId);
				station.put("name", no);
				station.put("code", no);
				station.put("parentId", shebei.getId());
				station.put("type","station");
				station.put("resource","");
				station.put("note","");
				station.put("entrustCode","");
				list.add(station);
				for (TestPlanExecuteDetail executeDetail : executeDetails) {
					if (executeDetail.getStation() == no) {
						Map<String,Object> task = new HashMap<>();
						task.put("id", executeDetail.getId());
						task.put("name", executeDetail.getSampleCode());
						task.put("parentId",stationId);
						task.put("type","execute");
						task.put("entrustCode","");
						if (entrustInfo != null && executeDetail.getEntrustId().equals(entrustInfo.getId())) {
							task.put("type","executeCurr");
						}
						task.put("startDate", DateUtils.formatDate(executeDetail.getPlanStartDate(),"yyyy-MM-dd HH:mm:ss"));
						task.put("endDate", DateUtils.formatDate(executeDetail.getPlanEndDate(),"yyyy-MM-dd HH:mm:ss"));
						TestPlanDetail testPlanDetail = testPlanDetailService.get(executeDetail.getPlanDetailId());
						if (testPlanDetail != null) {
							task.put("resource", testPlanDetail.getAbilityName());
							TestPlanDetail parent = testPlanDetailService.get(testPlanDetail.getParentId());
							if (parent != null) {
								task.put("note", parent.getAbilityName());
								task.put("entrustCode", testPlanDetail.getEntrustCode());
							}
						}
						list.add(task);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 根据计划信息 自动排期
	 * @param testPlan
	 * @return
	 */
	public TestPlan caculate(TestPlan testPlan) {
		try {
			EntrustTestGroup testGroup = entrustTestGroupService.get(testPlan.getTGroupId());
			devicePlanCaculator.caculate(testPlan,testGroup.getAbilityType());
		} catch (DevicePlanCaculator.CaculateSuccessException e) {

		}
		return testPlan;
	}

	/**
	 * 根据计划信息 自动排期
	 * @param testPlan
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public TestPlan caculate(TestPlan testPlan,Integer abilityType) {
		try {
			devicePlanCaculator.caculate(testPlan,abilityType);
		} catch (DevicePlanCaculator.CaculateSuccessException e) {

		}
		return testPlan;
	}
	@Transactional
	public TestPlan caculatePart(TestPlan testPlan,String unitId) {
		try {
			devicePlanCaculator.caculateRest(testPlan,unitId);
		} catch (DevicePlanCaculator.CaculateSuccessException e) {

		}
		return testPlan;
	}
	/**
	 * 生成计划编码
	 * @param entrustCode
	 * @param index
	 * @return
	 */
	private String generateCode(String entrustCode, int index){
		return entrustCode + "JH" + IdGen.getSeqNo(index, 3);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestPlan testPlan) {
		EntrustInfo entrustInfo = entrustInfoService.get(testPlan.getEntrustId());
		testPlan.setEntrustCode(entrustInfo.getCode());
		if(testPlan.getIsNewRecord()){
			//id 为试验组序号
			testPlan.setCode(generateCode(testPlan.getEntrustCode(), Integer.parseInt(testPlan.getId())));
			testPlan.setId(IdGen.uuid());
		}
		super.save(testPlan);
//		testPlanDetailService.deleteByPlanId(testPlan.getId());
//		testPlanExecuteDetailService.deleteByPlanId(testPlan.getId());
		for (int i = 0; i < testPlan.getTestPlanDetailList().size(); i++) {
			TestPlanDetail testPlanDetail = testPlan.getTestPlanDetailList().get(i);
//			testPlanDetail.setId("");
			if(testPlan.getIsNewRecord()){
				testPlanDetail.setIsNewRecord(true);
			}
			testPlanDetail.setTestPlan(testPlan);
			if(StringUtils.isEmpty(testPlanDetail.getId())){
				testPlanDetail.setId(IdGen.uuid());
			}
			if (StringUtils.isNotEmpty(testPlanDetail.getAbilityId())) {
				testPlanDetail.setAbilityId(testPlanDetail.getAbilityId().split(EntrustTestGroupService.UNIT_PRE)[0]);
			}
			testPlanDetail.setPlanId(testPlan.getId());
			testPlanDetail.settGroupId(testPlan.getTGroupId());
			testPlanDetail.setEntrustId(testPlan.getEntrustId());
			testPlanDetail.setEntrustCode(testPlan.getEntrustCode());
			if (testPlanDetail.getSampleList() != null) {
				testPlanDetail.setSampleAmount(testPlanDetail.getSampleList().size());
			}
			if (testPlanDetail.getSort() == null) {
				testPlanDetail.setSort(i+1);
			}
			testPlanDetailService.save(testPlanDetail);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestPlan testPlan) {
		testPlanDetailService.deleteByPlanId(testPlan.getId());
		testPlanExecuteDetailService.deleteByPlanId(testPlan.getId());
		super.delete(testPlan);
	}

	/**
	 * 根据试验组信息计算所有试验室排期，完成时间
	 * @param testGroups
	 * @return
	 */
	@Transactional
	public List<LabVO> caculateLab(List<EntrustTestGroup> testGroups,Integer sampleType) {
		List<LabVO> result = new ArrayList<>();
		LabInfo filter = new LabInfo();
		filter.setStatus("1");
		List<LabInfo> allLab = labInfoService.findList(filter);
		List<LineUpVO> lineUpVOS = entrustInfoDao.getLineUp();
		for (LabInfo labInfo : allLab) {
			labInfo = labInfoService.get(labInfo.getId());//关联出详细信息
			LabVO labVO = new LabVO();
			for (LineUpVO lineUpVO : lineUpVOS) {
				if (lineUpVO.getLabId().equals(labInfo.getId())) {
					labVO.setWaitEntrustCount(lineUpVO.getTotal());
				}
			}
			List<String> cannotDoTestItems = SetUniqueList.decorate(new ArrayList<>());
			for (EntrustTestGroup testGroup : testGroups) {
				TestPlan testPlan = null;
				try {
					List<EntrustTestGroupAbility> entrustTestGroupAbilities = testGroup.getAbilityList();
					for (EntrustTestGroupAbility t : entrustTestGroupAbilities) {
						t.setItem(testItemService.get(t.getItemId()));
					}
					testPlan = entrustTestGroupService.generateTestPlan(testGroup,labInfo.getId(),sampleType);
				} catch (DevicePlanCaculator.TestPlanCaculateException e) {
					if (e.getCode().equals(DevicePlanCaculator.ERROR_CODE_NO_PRE_HANDLE)) {
						cannotDoTestItems.add("试验室没有设置预处理");
						continue;
					}
//					logger.error("",e);
				}
				testPlan.setLabId(labInfo.getId());
				testGroup.setTestPlan(testPlan);
				if(testGroup.getAbilityType().equals(EntrustConstants.Ability_Type.SEQUENCE)){
					boolean cando  = false;
					EntrustTestGroupAbility seqAbility = testGroup.getAbilityList().get(0);
					for (LabTestSequence sequence : labInfo.getTestSequenceList()) {
						if (sequence.getSequence().getId().equals(seqAbility.getSeqId())) {
							cando = true;
							break;
						}
					}
					if (!cando) {
						cannotDoTestItems.add(seqAbility.getSeqName());
					}
				}else{
					for (TestPlanDetail testPlanDetail : testPlan.getTestPlanDetailList()) {
						if (testPlanDetail.getAbilityType().equals(EntrustConstants.Ability_Type.ITEM)) {
							boolean cando  = false;
							for (LabTestItem testItem : labInfo.getTestItemList()) {
								if (testItem.getItem().getId().equals(testPlanDetail.getAbilityId())) {
									cando = true;
									break;
								}
							}
							if (labInfo.getPreHandleItem() != null) {
								if (testPlanDetail.getAbilityId().equals(labInfo.getPreHandleItem().getId())) {
									cando = true;
								}
							}
							if (labInfo.getPreItem() != null) {
								if (testPlanDetail.getAbilityId().equals(labInfo.getPreItem().getId())) {
									cando = true;
								}
							}
							if (!cando) {
								cannotDoTestItems.add(testPlanDetail.getAbilityName());
							}
						}
					}
				}
			}
			//if(cannotDoTestItems.size() == 0){
				Date lastEndDate = null;
				for (EntrustTestGroup testGroup : testGroups) {
					TestPlan testPlan = testGroup.getTestPlan();
					//try{
						List<TestPlanDetail> cannotTestPlanDetails = devicePlanCaculator.caculateCustom(testPlan);
						for (TestPlanDetail planDetail : cannotTestPlanDetails){
							cannotDoTestItems.add(planDetail.getAbilityName());
						}
					if(!cannotTestPlanDetails.isEmpty()){
						continue;
					}else{
						try{
							caculate(testPlan,testGroup.getAbilityType());
						}catch (DevicePlanCaculator.TestPlanCaculateException e){
							cannotDoTestItems.add(e.getMessage());
							continue;
						}
					}
					if (lastEndDate == null) {
						lastEndDate = testPlan.getEndDate();
					} else if (lastEndDate.getTime() < testPlan.getEndDate().getTime()) {
						lastEndDate = testPlan.getEndDate();
					}
				}
				labVO.setEndTime(DateUtils.formatDate(lastEndDate,"yyyy-MM-dd HH:mm"));
			//}
			labVO.setLabId(labInfo.getId());
			labVO.setLabName(labInfo.getName());
			labVO.setCannotItem(StringUtils.join(cannotDoTestItems,";"));
			result.add(labVO);
		}
		return result;
	}

	public void disableEntrustTestgroup(String groupId) {
		EntrustTestGroup testGroup = entrustTestGroupService.get(groupId);
		testGroup.setDisableFlag(1);
		entrustTestGroupService.save(testGroup);
	}
	public void enableEntrustTestgroup(String groupId) {
		EntrustTestGroup testGroup = entrustTestGroupService.get(groupId);
		testGroup.setDisableFlag(0);
		entrustTestGroupService.save(testGroup);
	}
}