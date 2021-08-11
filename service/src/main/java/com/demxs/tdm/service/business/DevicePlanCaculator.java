package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.ability.TestUnit;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.service.ability.TestUnitService;
import org.apache.commons.collections.list.SetUniqueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 设备计划排期计算
 */
@Component
public class DevicePlanCaculator {

	private static final Logger LOGGER = LoggerFactory.getLogger(DevicePlanCaculator.class);
	public static final String ERROR_CODE_NO_DEVICE = "510";//没有符合资质的设备进行试验
	public static final String ERROR_CODE_NO_DEVICE_CREDENTIAL = "511";//没有指定设备资质
	public static final String ERROR_CODE_NO_EXPECT_TIME = "512";//没有指定预计用时
	public static final String ERROR_CODE_NO_SAMPLE = "513";//试验项没有样品
	public static final String ERROR_CODE_NO_PRE_HANDLE = "520";//需要预处理时，试验室没有预处理

	@Resource
	private EntrustTestGroupService entrustTestGroupService;
	@Resource
	private TestUnitService testUnitService;
	@Resource
	private ShebeiDao shebeiDao;
	@Resource
	private TestPlanExecuteDetailService testPlanExecuteDetailService;
	public DevicePlanCaculator() {
	}

	/**
	 * 根据计划信息 自动排期
	 *
	 * @param testPlan
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public TestPlan caculate(TestPlan testPlan, Integer abiliType) throws CaculateSuccessException {
		if (testPlan == null) {
			throw new IllegalArgumentException();
		}
		boolean isNew = false;
		if (StringUtils.isEmpty(testPlan.getId())) {//生成一个临时ID，以便关联
			testPlan.setId(IdGen.uuid());
			isNew = true;
		}
		if (testPlan.getStartDate() == null) {
			testPlan.setStartDate(new Date());
		}
		EntrustTestGroup testGroup;
		/*if(abiliType == null){
			testGroup = entrustTestGroupService.get(testPlan.getTGroupId());
			abiliType = testGroup.getAbilityType();
		}*/
		if(!isNew){
			testPlanExecuteDetailService.deleteByPlanId(testPlan.getId());
		}
		caculateSequence(testPlan);
		/*if (abiliType == EntrustConstants.Ability_Type.SEQUENCE) {
			caculateSequence(testPlan);
		} else if (abiliType == EntrustConstants.Ability_Type.CUSTOM_SEQUENCE) {
			caculateCustomSequence(testPlan);
		} else if (abiliType == EntrustConstants.Ability_Type.ITEM) {
			caculateItem(testPlan);
		}*/
		if (isNew) {
			testPlan.setId("");
		}

		//抛出异常，触发事务回滚
		throw new CaculateSuccessException(testPlan);
	}

	/**
	 * 部分计算，试验项后续试验计算
	 * @param testPlan
	 * @param unitId 试验项ID
	 * @return
	 * @throws CaculateSuccessException
	 */
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public TestPlan caculateRest(TestPlan testPlan, String unitId) throws CaculateSuccessException {
		//分割序列
		List<TestPlanDetail> wholeTestPlanDetailList = testPlan.getTestPlanDetailList();
		TestPlanDetail root = new TestPlanDetail();
		root.setId("0");
		root.setPlanEndDate(testPlan.getEndDate());
		wholeTestPlanDetailList.add(root);
		SequenceTreeGenerator sequenceTreeGenerator = new SequenceTreeGenerator(wholeTestPlanDetailList, "0");
		sequenceTreeGenerator.list2tree();
		TestPlanDetail unit = sequenceTreeGenerator.findById(unitId);
		String currItemId = unit.getParentId();
		TestPlanDetail currItem = sequenceTreeGenerator.findById(currItemId);
		TestPlanDetail currSeq = sequenceTreeGenerator.findParent(currItem.getParentId());
		currItem = sequenceTreeGenerator.findById(currItemId);
		for (TestPlanDetail testPlanDetail : currItem.getChildDetail()) {
			testPlanDetail.isCaculated = true;
			if (testPlanDetail.getId().equals(unit.getId())) {//此试验项以后的，需重算
				break;
			}
		}
//		currSeq.addChildDetail(currItem);
		for (TestPlanDetail testPlanDetail : currSeq.getChildDetail()) {
			if (testPlanDetail.getId().equals(currItemId)) {//执行当前试验项目及其后续试验项目
				break;
			}
			testPlanDetail.isCaculated = true;
			for (TestPlanDetail child : testPlanDetail.getChildDetail()) {
				child.isCaculated = true;
			}
		}
		//删除需重算的项的原有计划
		List<String> notCaculatedList = new ArrayList<>();
		for (TestPlanDetail testPlanDetail : wholeTestPlanDetailList) {
			if (!testPlanDetail.isCaculated) {
				notCaculatedList.add(testPlanDetail.getId());
			}
		}
		testPlanExecuteDetailService.deleteByPlanDetailIds(notCaculatedList);
		caculateSequence(currItem.getPlanEndDate(), testPlan, currSeq);
		//序列一定在试验项目执行完成之后并行执行，故不需递归执行分支后的内容，递归更新完成时间即可
		TestPlanDetail parent = sequenceTreeGenerator.findParent(currSeq.getParentId());
		while (parent != null) {
			if (parent.getPlanEndDate() == null) {
				parent.setPlanEndDate(currSeq.getPlanEndDate());
			}
			if (parent.getPlanEndDate().getTime() < currSeq.getPlanEndDate().getTime()) {
				parent.setPlanEndDate(currSeq.getPlanEndDate());
			}
			if (StringUtils.isNotEmpty(parent.getParentId())) {
				parent = sequenceTreeGenerator.findParent(parent.getParentId());
			}else{
				break;
			}
		}
		testPlan.setEndDate(root.getPlanEndDate());
		wholeTestPlanDetailList.remove(root);
		throw new CaculateSuccessException(testPlan);
	}

	private void caculateSequence(TestPlan testPlan){
		TestPlanDetail root = new TestPlanDetail();
		root.setId("0");
		testPlan.getTestPlanDetailList().add(root);
		SequenceTreeGenerator sequenceTreeGenerator = new SequenceTreeGenerator(testPlan.getTestPlanDetailList(), "0");
		sequenceTreeGenerator.list2tree();
//		TestPlanDetail root = sequenceTreeGenerator.getRoot();
		caculateSequence(testPlan.getStartDate(),testPlan, root);
		testPlan.setStartDate(root.getPlanStartDate());
		testPlan.setEndDate(root.getPlanEndDate());
		testPlan.getTestPlanDetailList().remove(root);
	}

	/**
	 * 执行 序列 试验
	 * @param startDate 可开始执行时间
	 * @param testPlan
	 * @param root 当前节点
	 */
	private void caculateSequence(Date startDate, TestPlan testPlan, TestPlanDetail root) {
		if (root.getChildDetail() == null || root.getChildDetail().size() == 0) {
			return;
		}
		root.setPlanStartDate(startDate);
		List<TestPlanDetail> subSequenceList = new ArrayList<>();
//		if (SequenceTreeGenerator.hasItem(root)) {//按序执行
			for (TestPlanDetail testPlanDetail : root.getChildDetail()) {
				if (isItem(testPlanDetail)) {
					oneTestItem(startDate,testPlan, testPlanDetail);
					caculateSequence(testPlanDetail.getPlanEndDate(), testPlan, testPlanDetail);
					startDate = testPlanDetail.getPlanEndDate();
				}else if(isSequence(testPlanDetail)){
					subSequenceList.add(testPlanDetail);
//					caculateSequence(startDate, testPlan, testPlanDetail);
//					startDate = testPlanDetail.getPlanEndDate();
				}
			}
			Date lastDate = startDate;//最后完成时间
			//试验项目按序执行完成后，分支并行执行
			for (TestPlanDetail subSeq : subSequenceList) {
				caculateSequence(startDate, testPlan, subSeq);
				if (subSeq.getPlanEndDate().getTime() > lastDate.getTime()) {
					lastDate = subSeq.getPlanEndDate();
				}
			}
			root.setPlanStartDate(root.getChildDetail().get(0).getPlanStartDate());
			root.setPlanEndDate(lastDate);
		/*} else {//全是分支，并行执行
			Date firtDate = null;
			Date lastDate = startDate;
			for (TestPlanDetail testPlanDetail : root.getChildDetail()) {
				caculateSequence(startDate, testPlan, testPlanDetail);
				if (testPlanDetail.getPlanEndDate().getTime() > lastDate.getTime()) {
					lastDate = testPlanDetail.getPlanEndDate();
				}
				if (firtDate == null) {
					firtDate = testPlanDetail.getPlanStartDate();
				}else if (testPlanDetail.getPlanStartDate().getTime() < firtDate.getTime()) {
					firtDate = testPlanDetail.getPlanStartDate();
				}
			}
			root.setPlanStartDate(firtDate);
			root.setPlanEndDate(lastDate);
		}*/
	}

	public List<TestPlanDetail> caculateCustom(TestPlan testPlan){
		TestPlanDetail root = new TestPlanDetail();
		root.setId("0");
		testPlan.getTestPlanDetailList().add(root);
		SequenceTreeGenerator sequenceTreeGenerator = new SequenceTreeGenerator(testPlan.getTestPlanDetailList(), "0");
		sequenceTreeGenerator.list2tree();
		return caculateSequenceCustom(testPlan, root,new ArrayList<>());
	}
	private List<TestPlanDetail> caculateSequenceCustom(TestPlan testPlan, TestPlanDetail root,List<TestPlanDetail> cannotTestPlanDetails){

		if (root.getChildDetail() == null || root.getChildDetail().size() == 0) {
			return cannotTestPlanDetails;
		}
		List<TestPlanDetail> subSequenceList = new ArrayList<>();
		for (TestPlanDetail testPlanDetail : root.getChildDetail()) {
			if (isItem(testPlanDetail)) {
				try {
					oneTestItemCustom(testPlan, testPlanDetail);
				}catch (TestPlanCaculateException e){
					cannotTestPlanDetails.add(testPlanDetail);
				}
				caculateSequenceCustom(testPlan, testPlanDetail,cannotTestPlanDetails);
			}else if(isSequence(testPlanDetail)){
				subSequenceList.add(testPlanDetail);
			}
		}
		//试验项目按序执行完成后，分支并行执行
		for (TestPlanDetail subSeq : subSequenceList) {
			caculateSequenceCustom(testPlan, subSeq,cannotTestPlanDetails);
		}
		return cannotTestPlanDetails;
	}

	private TestPlan oneTestItemCustom(TestPlan testPlan, TestPlanDetail testPlanDetail) {
		List<TestPlanDetail> testUnits = testPlanDetail.getChildDetail();
		if(testUnits.size() == 0){
			testUnits = getTestUnit(testPlanDetail.getId(),testPlan.getTestPlanDetailList());
		}
		for (TestPlanDetail testUnit : testUnits) {
			if(!testUnit.isCaculated){
				testUnit.setSampleList(testPlanDetail.getSampleList());
				oneTestUnitCustom(testPlan,testUnit);
			}
		}
		return testPlan;
	}

	private void oneTestUnitCustom(TestPlan testPlan, TestPlanDetail testPlanDetail){
		String id = testPlanDetail.getAbilityId();
		if (StringUtils.isNotBlank(id)) {
			id = id.split(EntrustTestGroupService.UNIT_PRE)[0];
			TestUnit testUnit = testUnitService.get(id);
			List<Shebei> shebeiList = getShebeis(testUnit,testPlan.getLabId());

			if(shebeiList.isEmpty()){
				throw new TestPlanCaculateException(ERROR_CODE_NO_DEVICE , testUnit.getName());
			}

			if (testPlanDetail.getExpectTime() == null) {
				testPlanDetail.setExpectTime(testPlanDetail.getStandardTime());
			}
			if (testPlanDetail.getExpectTime() == null) {
				LOGGER.error("expect time not set for:"+testUnit);
				throw new TestPlanCaculateException(ERROR_CODE_NO_EXPECT_TIME ,testUnit.getName());
			}
		}
	}

	/**
	 * 单个试验项目排期
	 * @param testPlan
	 * @param testPlanDetail
	 * @return
	 */
	private TestPlan oneTestItem(Date startDate,TestPlan testPlan, TestPlanDetail testPlanDetail) {
		List<TestPlanDetail> testUnits = testPlanDetail.getChildDetail();
		if(testUnits.size() == 0){
			testUnits = getTestUnit(testPlanDetail.getId(),testPlan.getTestPlanDetailList());
		}
		if (testUnits.size() == 0) {
			testPlanDetail.setPlanStartDate(startDate);
			testPlanDetail.setPlanEndDate(startDate);
			return testPlan;
		}
//		List<String> useDeviceIds = SetUniqueList.decorate(new ArrayList<String>());
		List<Shebei> useDeviceList = SetUniqueList.decorate(new ArrayList<Shebei>());//统计用到的设备
		for (TestPlanDetail testUnit : testUnits) {
			if(!testUnit.isCaculated){
				testUnit.setSampleList(testPlanDetail.getSampleList());
				oneTestUnit(startDate,testPlan,testUnit);
			}
			startDate = testUnit.getPlanEndDate();
//			useDeviceIds.addAll(Arrays.asList(testUnit.getDeviceId().split(",")));
			useDeviceList.addAll(testUnit.getDeviceList());
		}
		testPlanDetail.setPlanStartDate(testUnits.get(0).getPlanStartDate());
		testPlanDetail.setPlanEndDate(testUnits.get(testUnits.size() - 1).getPlanEndDate());
//		testPlanDetail.setDeviceId(StringUtils.join(useDeviceIds,","));
		testPlanDetail.setDeviceList(useDeviceList);
		return testPlan;
	}

	/**
	 * 单个试验项排期
	 * @param testPlan
	 * @param testPlanDetail
	 */
	private void oneTestUnit(Date startDate,TestPlan testPlan, TestPlanDetail testPlanDetail){
		String id = testPlanDetail.getAbilityId();
		if (StringUtils.isNotBlank(id)) {
			id = id.split(EntrustTestGroupService.UNIT_PRE)[0];
			TestUnit testUnit = testUnitService.get(id);
			List<Shebei> shebeiList = getShebeis(testUnit,testPlan.getLabId());
			if (testPlanDetail.getExpectTime() == null) {
				testPlanDetail.setExpectTime(testPlanDetail.getStandardTime());
			}
			if (testPlanDetail.getExpectTime() == null) {
				LOGGER.error("expect time not set for:"+testUnit);
				throw new TestPlanCaculateException(ERROR_CODE_NO_EXPECT_TIME ,testUnit.getName());
			}
			long needTime = (long)(testPlanDetail.getExpectTime() * 3600000);//需要时长
			if (testPlanDetail.getSampleList() == null || testPlanDetail.getSampleList().size() == 0) {
				LOGGER.error("no sample to test for testunit :"+testUnit);
				throw new TestPlanCaculateException(ERROR_CODE_NO_SAMPLE ,testUnit.getName());
			}
			List<TestPlanExecuteDetail> unitExecuteList = new ArrayList<TestPlanExecuteDetail>();
			TestUnitExecutePlanInfo executePlanInfo = new TestUnitExecutePlanInfo();
			loopSample : for (EntrustSampleGroupItem sample : testPlanDetail.getSampleList()) {//遍历所有样品，进行样品排期
				Date fastestStartDate = null;
				TestPlanExecuteDetail execute = new TestPlanExecuteDetail();
				Integer sampleType = sample.getType();
				execute.setSampleCode(sample.getSn());
				execute.setSampleType(sampleType);
				if (StringUtils.isNotEmpty(testPlanDetail.getId())) {
					execute.setPlanDetailId(testPlanDetail.getId());
				} else {
					execute.setPlanDetailId(id);
				}
				execute.setPlanId(testPlan.getId());
				execute.setEntrustId(testPlan.getEntrustId());
				loopDevice : for (Shebei shebei : shebeiList) {//得到设备最快开始时间
					List<TestPlanExecuteDetail> executeDetails = testPlanExecuteDetailService.findByDeviceBetweenTime(startDate, null,shebei.getId(), sampleType);
					if (StringUtils.isEmpty(shebei.getKeshiyanypsl())) {//没有工位的设备跳过
						break loopDevice;
					}
					if (executeDetails == null || executeDetails.size() == 0) {//没有计划的空闲设备
						fastestStartDate = startDate;
						executePlanInfo.setExecuteDevice(execute,shebei,1);//默认使用工位1
						break loopDevice;
					}
					List<List<TestPlanExecuteDetail>> staionExecutionDetails = groupByStation(executeDetails, Integer.valueOf(shebei.getKeshiyanypsl()));
					//没有计划的空闲工位
					for (int i = 0; i < staionExecutionDetails.size(); i++) {
						List<TestPlanExecuteDetail> staionExecutionDetail = staionExecutionDetails.get(i);
						if (staionExecutionDetail == null || staionExecutionDetail.size() == 0) {
							fastestStartDate = startDate;
							executePlanInfo.setExecuteDevice(execute,shebei,i+1);
							break loopDevice;
						}
					}
					for (List<TestPlanExecuteDetail> staionExecutionDetail : staionExecutionDetails) {//得到最快完成工位及开始、完成时间
						for (int i = 0; i < staionExecutionDetail.size(); i++) {//从左往右找能塞下的空闲时间段，得到此工位最快开始时间
							TestPlanExecuteDetail curr = staionExecutionDetail.get(i);
							if (i == 0) {//第一次空闲在左边
								if (curr.getPlanStartDate().getTime() > startDate.getTime()) {
									long time = curr.getPlanStartDate().getTime() - startDate.getTime();
									if (time > needTime) {
										fastestStartDate = startDate;
										executePlanInfo.setExecuteDevice(execute,shebei,curr.getStation());
										break loopDevice;
									}
								}
							}
							//空闲时间段在右边
							if (i < staionExecutionDetail.size() - 1) {
								TestPlanExecuteDetail next = staionExecutionDetail.get(i + 1);
								long time = next.getPlanStartDate().getTime() - curr.getPlanEndDate().getTime();
								if (time > needTime && (fastestStartDate == null || fastestStartDate.getTime() > curr.getPlanEndDate().getTime())){
									fastestStartDate  = curr.getPlanEndDate();
									executePlanInfo.setExecuteDevice(execute,shebei,curr.getStation());
									break;
								}
							}else{//最后一个，前面的时间段没有能塞下的
								if(fastestStartDate == null || fastestStartDate.getTime() > curr.getPlanEndDate().getTime()){
									fastestStartDate  = curr.getPlanEndDate();
									executePlanInfo.setExecuteDevice(execute,shebei,curr.getStation());
									break;
								}
							}
						}
					}
				}
				if (fastestStartDate == null) {
					throw new TestPlanCaculateException(ERROR_CODE_NO_DEVICE, testUnit.getName());
				}
				execute.setPlanStartDate(fastestStartDate);
				execute.setPlanEndDate(new Date(fastestStartDate.getTime() + needTime));
				unitExecuteList.add(execute);
				testPlanExecuteDetailService.save(execute);
			}
			testPlanDetail.setPlanStartDate(unitExecuteList.get(0).getPlanStartDate());
			testPlanDetail.setPlanEndDate(unitExecuteList.get(unitExecuteList.size() - 1).getPlanEndDate());
//			testPlanDetail.setDeviceId(StringUtils.join(executePlanInfo.useDeviceIds,","));
			testPlanDetail.setDeviceList(executePlanInfo.useDeviceList);
			testPlanDetail.setTestPlanExecuteDetails(unitExecuteList);
		}
	}

	/**
	 * 获取试验室 此试验项 可用的设备
	 * @param testUnit
	 * @param labId
	 * @return
	 */
	public List<Shebei> getShebeis(TestUnit testUnit, String labId) {
		List<Aptitude> deviceCredentialsList = testUnit.getDeviceCredentialsList();
		if(deviceCredentialsList == null || deviceCredentialsList.size() == 0){
			LOGGER.error("test unit has no device credentials when caculate plan:"+testUnit);
			throw new TestPlanCaculateException(ERROR_CODE_NO_DEVICE_CREDENTIAL, testUnit.getName());
		}
		String[] ids = new String[deviceCredentialsList.size()];
		for (int i = 0; i < deviceCredentialsList.size(); i++) {
			Aptitude aptitude = deviceCredentialsList.get(i);
			ids[i] = aptitude.getId();
		}
		List<Shebei> shebeiList = getShebeisByZizhi(StringUtils.join(ids, ","),labId);
		if (shebeiList == null || shebeiList.size() == 0) {
			LOGGER.error("no device find for credential:"+StringUtils.join(ids, ","));
			throw new TestPlanCaculateException(ERROR_CODE_NO_DEVICE , testUnit.getName());
		}
		return shebeiList;
	}

	private List<Shebei> getShebeisByZizhi(String idStr,String labId){
		List<Shebei> result = SetUniqueList.decorate(new ArrayList<>());
		if (StringUtils.isEmpty(idStr)) {
			return result;
		}
		for (String id : idStr.split(",")) {
			result.addAll(shebeiDao.findByZizhi(id,labId));
		}
		return result;
	}

	/**
	 * 按工位对计划分组
	 * @param executeDetails
	 * @param count  工位数量
	 * @return
	 */
	public static List<List<TestPlanExecuteDetail>> groupByStation(List<TestPlanExecuteDetail> executeDetails,Integer count) {
		Map<Integer,List<TestPlanExecuteDetail>> deviceExecuteDetails = new HashMap<Integer, List<TestPlanExecuteDetail>>();//所有工位的占用
		for (int i = 1; i <= count; i++) {
			deviceExecuteDetails.put(i, new ArrayList<TestPlanExecuteDetail>());
		}
		for (TestPlanExecuteDetail executeDetail : executeDetails) {
			List<TestPlanExecuteDetail> staionExecuteDetails = deviceExecuteDetails.get(executeDetail.getStation());//一个工位的占用情况
			if (staionExecuteDetails == null) {
				staionExecuteDetails = new ArrayList<TestPlanExecuteDetail>();
				deviceExecuteDetails.put(executeDetail.getStation(),staionExecuteDetails);
			}
			staionExecuteDetails.add(executeDetail);
		}
		ArrayList<List<TestPlanExecuteDetail>> result = new ArrayList<>(deviceExecuteDetails.values());
		/*for (List<TestPlanExecuteDetail> testPlanExecuteDetails : result) {
			Collections.sort(testPlanExecuteDetails, (o1, o2) -> {
				if (o1.getPlanStartDate() == null && o2.getPlanStartDate() == null) {
					return 0;
				}
				long c = o2.getPlanStartDate().getTime() - o1.getPlanStartDate().getTime();
				int res = 0;
				if (c > 0) {
					res = -1;
				}
				if (c < 0) {
					res = 1;
				}
				return res;
			});
		}*/
		return result;
	}

	/**
	 * 记录试验项对应 TestPlanExecuteDetail  的设备、工位信息
	 */
	class TestUnitExecutePlanInfo{
//		List<TestPlanExecuteDetail> unitExecuteList = new ArrayList<TestPlanExecuteDetail>();
		List<String> useDeviceIds = new ArrayList<String>();
		List<Shebei> useDeviceList = new ArrayList<Shebei>();

		/**
		 * 确定使用的设备、工位
		 * @param execute
		 * @param shebei
		 * @param station
		 */
		void setExecuteDevice(TestPlanExecuteDetail execute, Shebei shebei, Integer station){
			execute.setDeviceId(shebei.getId());
			execute.setDeviceCode(shebei.getShebeibh());
			execute.setStation(station);
			if(!useDeviceIds.contains(shebei.getId())){
				useDeviceIds.add(shebei.getId());
				useDeviceList.add(shebei);
			}
		}
	}

	/**
	 * 获取项目下的试验项计划信息
	 * @param itemId
	 * @param list
	 * @return
	 */
	private List<TestPlanDetail> getTestUnit(String itemId,List<TestPlanDetail> list){
		List<TestPlanDetail> testUnits = new ArrayList<TestPlanDetail>();
		for (TestPlanDetail testPlanDetail : list) {
			if(testPlanDetail.getAbilityType() == EntrustConstants.Ability_Type.UNIT && itemId.equals(testPlanDetail.getParentId())){
				testUnits.add(testPlanDetail);
			}
		}
//		Collections.sort(testUnits, Comparator.comparingInt(TestPlanDetail::getSort));//@reason 因为前端传递的数组是有序的，不再后台排序
		return testUnits;
	}

	public static boolean isUnit(TestPlanDetail testPlanDetail){
		if (testPlanDetail.getAbilityType() == EntrustConstants.Ability_Type.UNIT) {
			return true;
		}
		return false;
	}

	public static boolean isItem(TestPlanDetail testPlanDetail){
		if (testPlanDetail.getAbilityType() == EntrustConstants.Ability_Type.ITEM) {
			return true;
		}
		return false;
	}
	public static boolean isSequence(TestPlanDetail testPlanDetail){
		if (testPlanDetail.getAbilityType() == EntrustConstants.Ability_Type.SEQUENCE) {
			return true;
		}
		return false;
	}

	/**
	 * 根据平铺数据生成 树结构
	 */
	static class SequenceTreeGenerator {
		private List<TestPlanDetail> list;
		private TestPlanDetail root;
		public SequenceTreeGenerator(List<TestPlanDetail> list, String rootId) {
			this.list = list;
			this.root = findById(rootId);
		}

		private TestPlanDetail findById(String rootId) {
			for (TestPlanDetail testPlanDetail : list) {
				if (testPlanDetail.getId().equals(rootId)) {
					return testPlanDetail;
				}
			}
			return null;
		}
		public TestPlanDetail findParent(String parentId) {
			for (TestPlanDetail testPlanDetail : list) {
				if (testPlanDetail.getId().equals(parentId)) {
					return testPlanDetail;
				}
			}
			return null;
		}
		private void setChildren(TestPlanDetail parent){
			List<TestPlanDetail> children = new ArrayList<TestPlanDetail>();
			for (TestPlanDetail testPlanDetail : list) {
				if (StringUtils.isEmpty(testPlanDetail.getParentId()) && !testPlanDetail.getId().equals(root.getId())) {
					testPlanDetail.setParentId(root.getId());
				}
				if (parent.getId().equals(testPlanDetail.getParentId())) {
					children.add(testPlanDetail);
					setChildren(testPlanDetail);
				}
			}
//			Collections.sort(children,Comparator.comparingInt(TestPlanDetail::getSort));//@reason 因为前端传递的数组是有序的，不再后台排序
			parent.setChildDetail(children);
		}
		public void list2tree() {
			setChildren(root);
		}

		/**
		 * 是否有子试验项目
		 * @param parent
		 * @return
		 */
		public static boolean hasItem(TestPlanDetail parent) {
			for (TestPlanDetail testPlanDetail : parent.getChildDetail()) {
				if (isItem(testPlanDetail)) {
					return true;
				}
			}
			return false;
		}

		public List<TestPlanDetail> getList() {
			return list;
		}

		public TestPlanDetail getRoot() {
			return root;
		}

	}

	/**
	 * 计算异常，条件不符合等已知异常
	 */
	public static class TestPlanCaculateException extends IllegalArgumentException{
		private String code;
		private String message;

		public TestPlanCaculateException(String code,String message){
			super(message);
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public String toString() {
			return code + ":" +message;
		}
	}

	/**
	 * 表示计算成功，计算过程中插入到数据库的数据等，通过此异常回滚
	 */
	public static class CaculateSuccessException extends Exception{
		private TestPlan data;

		public CaculateSuccessException(TestPlan testPlan) {
			this.data = testPlan;
		}

		public TestPlan getData() {
			return data;
		}

		public void setData(TestPlan data) {
			this.data = data;
		}
	}
}