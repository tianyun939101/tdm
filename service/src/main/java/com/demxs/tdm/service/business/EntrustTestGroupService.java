package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustSampleGroupItemDao;
import com.demxs.tdm.dao.business.EntrustTestGroupDao;
import com.demxs.tdm.dao.lab.LabTestItemDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.ability.TestItemUnit;
import com.demxs.tdm.domain.ability.TestSequence;
import com.demxs.tdm.domain.ability.TestSequenceItem;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.comac.common.constant.SysConstants;
import com.demxs.tdm.service.ability.TestItemService;
import com.demxs.tdm.service.ability.TestSequenceItemService;
import com.demxs.tdm.service.ability.TestSequenceService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 申请试验需求Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustTestGroupService extends CrudService<EntrustTestGroupDao, EntrustTestGroup> {

	public static final String UNIT_PRE = "_unit_";
	@Resource
	private EntrustTestGroupAbilityService entrustTestGroupAbilityService;
	@Resource
	private TestSequenceService testSequenceService;
	@Resource
	private TestSequenceItemService testSequenceItemService;
	@Resource
	private EntrustSampleGroupItemDao entrustSampleGroupItemDao;
	@Resource
	private EntrustInfoService entrustInfoService;
	@Resource
	private TestItemService testItemService;
	@Resource
	private LabInfoService labInfoService;
	@Resource
	private LabTestItemDao labTestItemDao;

	@Override
	public EntrustTestGroup get(String id) {
		return super.get(id);
	}

	/**
	 * 根据试验组ID 生成计划初始信息
	 * @param testGroupId
	 * @return
	 */
	public TestPlan getTestplanInitInfo(String testGroupId){
		EntrustTestGroup testGroup = this.get(testGroupId);
		List<EntrustTestGroupAbility> entrustTestGroupAbilities = entrustTestGroupAbilityService.listDetailByTestGroupId(testGroupId);
		testGroup.setAbilityList(entrustTestGroupAbilities);
		EntrustInfo entrustInfo = entrustInfoService.get(testGroup.getEntrustId());
		TestPlan testPlan = generateTestPlan(testGroup,entrustInfo.getLabId(),entrustInfo.getSampleType());
		return testPlan;
	}

	/**
	 * 根据试验组生成试验计划信息
	 * @param testGroup
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public TestPlan generateTestPlan(EntrustTestGroup testGroup,String labId,Integer sampleType) {
		String testGroupId = testGroup.getId();
		//		List<Map<String, Object>> mapList = Lists.newArrayList();
		TestPlan testPlan = new TestPlan();
		testPlan.setLabId(labId);
		testPlan.setTGroupId(testGroupId);
		List<TestPlanDetail> testPlanDetails = Lists.newArrayList();
		testPlan.setTestPlanDetailList(testPlanDetails);
		testPlan.setEntrustId(testGroup.getEntrustId());
		int sortStart = 0;
		if(StringUtils.isNotEmpty(testGroup.getEntrustId())) {
			EntrustInfo entrustInfo = entrustInfoService.get(testGroup.getEntrustId());
			testPlan.setEntrustCode(entrustInfo.getCode());
		}
		List<EntrustTestGroupAbility> entrustTestGroupAbilities = testGroup.getAbilityList();
		List<EntrustSampleGroupItem> sampleList = new ArrayList<EntrustSampleGroupItem>();
		if (testGroup.getSampleGroups() != null && testGroup.getSampleGroups().size() > 0) {
			//如果有样品组信息，根据样品组生成样品信息，所有试验项执行所有样品；（申请单 试验室计算时 未生成样品信息）
			for (EntrustSampleGroup sampleGroup : testGroup.getSampleGroups()) {
				EntrustSampleGroupItem sample = new EntrustSampleGroupItem();
				sample.setType(sampleGroup.getType());
				sample.setSn(IdGen.uuid());
				sampleList.add(sample);
			}
		}else{
			Page<EntrustSampleGroupItem> page = new Page<>(1,100);
			getSamples(page, testGroup.getId(),"");
			sampleList = page.getList();
		}
		if (StringUtils.isEmpty(labId)) {
			throw new IllegalArgumentException("试验组没有对应试验室，无法计算排期！");
		}
		//预处理，前置试验
		LabInfo labInfo = labInfoService.get(labId);
		TestItem preItem = labInfo.getPreItem();
		String rootId = "0";
		if(testGroup.getBeforProcess() == SysConstants.YES_NO.YES){//预处理
			TestItem preHandleItem = labInfo.getPreHandleItem();
			if (preHandleItem != null) {
				sortStart++;
				addPreItemOrPreHandleItem(testGroup, labId, testGroupId, testPlan, testPlanDetails, sampleList, preHandleItem, rootId, sortStart);
			} else {
				throw new DevicePlanCaculator.TestPlanCaculateException(DevicePlanCaculator.ERROR_CODE_NO_PRE_HANDLE,"预处理");
			}
		}
		if (preItem != null && sampleType == EntrustConstants.Sample_Type.PRODUCT) {//前置试验
			//自定义序列第一项就是前置试验时不加入
			if (testGroup.getAbilityType() == EntrustConstants.Ability_Type.CUSTOM_SEQUENCE &&
					entrustTestGroupAbilities.get(0).getItem().getId().equals(preItem.getId())) {

			}else{
				sortStart++;
				addPreItemOrPreHandleItem(testGroup, labId, testGroupId, testPlan, testPlanDetails, sampleList, preItem, rootId, sortStart);
			}
		}
		if(testGroup.getAbilityType() == EntrustConstants.Ability_Type.SEQUENCE){
			EntrustTestGroupAbility testGroupAbility = entrustTestGroupAbilities.get(0);
			TestSequence testSequence = testSequenceService.get(testGroupAbility.getSeqId());
			TestPlanDetail seq = new TestPlanDetail();
			seq.setId(IdGen.uuid());
			seq.setAbilityType(EntrustConstants.Ability_Type.SEQUENCE);
			seq.setAbilityId(testGroupAbility.getSeqId());
			seq.setAbilityName(testSequence.getName());
			seq.setEntrustAbilityId(testGroupAbility.getId());
			seq.setEntrustId(testGroupAbility.getEntrustId());
			seq.setEntrustCode(testPlan.getEntrustCode());
			seq.settGroupId(testGroupAbility.getTGroupId());
			seq.setSort(++sortStart);
			seq.setParentId(rootId);
			testPlanDetails.add(seq);
			List<TestSequenceItem> testSequenceItems = getTestSequenceItems(testGroupAbility);
			for (TestSequenceItem node : testSequenceItems) {
				TestPlanDetail tpd = new TestPlanDetail();
				tpd.setId(node.getNodeId());//试验序列通过nodeID，parentId 维护父子关系
				if (AbilityConstants.TestSequenceItemFlag.ITEM.equals(node.getFlag())) {
					tpd.setAbilityType(EntrustConstants.Ability_Type.ITEM );
					tpd.setAbilityId(node.getItem().getId());//没有子节点，只有试验项，设为itemId
					tpd.setEntrustAbilityId(testGroupAbility.getId());
				}else{
					tpd.setAbilityType(EntrustConstants.Ability_Type.SEQUENCE );
					tpd.setAbilityId(node.getSeqId());
				}
				tpd.setAbilityName(node.getName());
				tpd.setEntrustId(testGroupAbility.getEntrustId());
				tpd.setEntrustCode(testPlan.getEntrustCode());
				tpd.settGroupId(testGroupAbility.getTGroupId());
				if (node.getParent() != null) {
//					TestSequenceItem parent = testSequenceItemService.getByNodeId(d.getParent().getNodeId());
//					tpd.setParent(parent.getId());
					tpd.setParentId(node.getParent().getNodeId());
				}else {
					tpd.setParentId(seq.getId());
				}
				tpd.setSort(node.getSort());
				tpd.setSampleList(sampleList);
				testPlanDetails.add(tpd);
				putItemUnit(testPlan, testGroupAbility, node.getItem(), tpd.getId(), labId);
			}
		}else{
			for (int i = 0; i < entrustTestGroupAbilities.size(); i++) {
				EntrustTestGroupAbility t = entrustTestGroupAbilities.get(i);
				if(t.getItem()==null){
					t.setItem(testItemService.get(t.getItemId()));
				}
				TestPlanDetail tpd = new TestPlanDetail();
				tpd.setId(IdGen.uuid());
				tpd.setAbilityType(EntrustConstants.Ability_Type.ITEM );
				tpd.setAbilityId(t.getItem().getId());
				tpd.setAbilityName(t.getItem().getName());
				tpd.setEntrustAbilityId(t.getId());
				tpd.setEntrustId(t.getEntrustId());
				tpd.setEntrustCode(testPlan.getEntrustCode());
				tpd.settGroupId(t.getTGroupId());
				tpd.setParentId("");
				tpd.setSort(t.getSort()+sortStart);
				/*if(testGroup.getAbilityType() == EntrustConstants.Ability_Type.ITEM){
					tpd.setSort(1);
				}else if(testGroup.getAbilityType() == EntrustConstants.Ability_Type.CUSTOM_SEQUENCE){
					tpd.setSort(i);
				}*/
				tpd.setSampleList(sampleList);
				testPlanDetails.add(tpd);
				putItemUnit(testPlan, t, t.getItem(),tpd.getId(),labId);
			}
		}
		return testPlan;
	}

	/**
	 * 加入预处理、前测到 试验序列
	 * @param testGroup
	 * @param labId
	 * @param testGroupId
	 * @param testPlan
	 * @param testPlanDetails
	 * @param sampleList
	 * @param preItem
	 * @param rootId
	 * @param sort
	 */
	private void addPreItemOrPreHandleItem(EntrustTestGroup testGroup, String labId, String testGroupId, TestPlan testPlan, List<TestPlanDetail> testPlanDetails, List<EntrustSampleGroupItem> sampleList, TestItem preItem, String rootId, int sort) {
		preItem = testItemService.get(preItem.getId());
		TestPlanDetail preItemPlan = new TestPlanDetail();
		preItemPlan.setId(IdGen.uuid());
		preItemPlan.setAbilityType(EntrustConstants.Ability_Type.ITEM);
		preItemPlan.setAbilityId(preItem.getId());
		preItemPlan.setAbilityName(preItem.getName());
		preItemPlan.setEntrustId(testGroup.getEntrustId());
		preItemPlan.setEntrustCode(testPlan.getEntrustCode());
		preItemPlan.settGroupId(testGroup.getId());
		preItemPlan.setSort(sort);
		preItemPlan.setParentId(rootId);
		preItemPlan.setSampleList(sampleList);
		testPlanDetails.add(preItemPlan);
		EntrustTestGroupAbility tmp = new EntrustTestGroupAbility();
		tmp.setEntrustId(testGroup.getEntrustId());
		tmp.setTGroupId(testGroupId);
		putItemUnit(testPlan, tmp, preItem, preItemPlan.getId(),labId);
	}

	/**
	 * 需要把nodeId  设置为生成的UUID， 并维护树结构 父子关系
	 * @param testGroupAbility
	 * @return
	 */
	private List<TestSequenceItem> getTestSequenceItems(EntrustTestGroupAbility testGroupAbility) {
		List<TestSequenceItem> testSequenceItems = testSequenceItemService.detailBySeqId(testGroupAbility.getSeqId());
		for (TestSequenceItem testSequenceItem : testSequenceItems) {
			String oldId = testSequenceItem.getNodeId();
			testSequenceItem.setNodeId(IdGen.uuid());
			//更改子节点的父ID为新的ID
			for (TestSequenceItem tsi1 : testSequenceItems) {
				if (tsi1.getParent() != null && tsi1.getParent().getNodeId().equals(oldId)) {
					tsi1.getParent().setNodeId(testSequenceItem.getNodeId());
				}
			}
		}
		return testSequenceItems;
	}

	/**
	 * 把试验项添加到 计划中
	 * @param testPlan
	 * @param testGroupAbility
	 * @param item
	 * @param parentId
	 * @param labId
	 */
	private void putItemUnit(TestPlan testPlan, EntrustTestGroupAbility testGroupAbility, TestItem item, String parentId, String labId) {
		if (item != null) {
			LabTestItem labItem = labTestItemDao.getLabItem(labId, item.getId());
//			labTestItemUnitDao.findByItem(labItem.getId())
			List<TestItemUnit> testUnitsList = item.getTestUnitsList();
			/*if (labItem != null) {
				testUnitsList = new ArrayList<>();
				testUnitsList.addAll(labItem.getLabTestUnitsList());
			}*/
			for (int i = 0; i < testUnitsList.size(); i++) {
				TestItemUnit tiu = testUnitsList.get(i);
				TestPlanDetail child = new TestPlanDetail();
				child.setId(IdGen.uuid());
				child.setAbilityType(EntrustConstants.Ability_Type.UNIT);
				child.setAbilityName(tiu.getUnit().getName());
				child.setAbilityId(tiu.getUnit().getId());
//				child.setAbilityId(tiu.getUnit().getId() + UNIT_PRE + item.getId());
				child.setEntrustId(testGroupAbility.getEntrustId());
				child.setEntrustCode(testPlan.getEntrustCode());
				child.settGroupId(testGroupAbility.getTGroupId());
				child.setParentId(parentId);
//				child.setSort(tiu.getSort());
				child.setSort(i+1);
				if (tiu.getDuration() != null) {
					child.setStandardTime(Double.valueOf(tiu.getDuration().toString()));
				}
				testPlan.getTestPlanDetailList().add(child);
			}
		}
	}

	@Override
	public List<EntrustTestGroup> findList(EntrustTestGroup entrustTestGroup) {
		return super.findList(entrustTestGroup);
	}
	
	@Override
	public Page<EntrustTestGroup> findPage(Page<EntrustTestGroup> page, EntrustTestGroup entrustTestGroup) {
		return super.findPage(page, entrustTestGroup);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustTestGroup entrustTestGroup) {
		super.save(entrustTestGroup);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustTestGroup entrustTestGroup) {
		super.delete(entrustTestGroup);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByEntrustId(String entrustId) throws ServiceException{
		this.dao.deleteByEntrustId(entrustId);
	}

	/**
	 * 根据申请单ID加载试验组数据
	 * @param entrustId 申请单ID
	 * @return
	 */
	public List<EntrustTestGroup> listByEntrustId(String entrustId) throws ServiceException{
		return this.dao.listByEntrustId(entrustId);
	}

	/**
	 * 查询试验组样品
	 * @param tGroupId
	 * @return
	 */
	public Page<EntrustSampleGroupItem> getSamples(Page<EntrustSampleGroupItem> page,String tGroupId,String sn){
		page.setList(entrustSampleGroupItemDao.findByTGroupId(page, tGroupId, sn));
		return page;
	}
}