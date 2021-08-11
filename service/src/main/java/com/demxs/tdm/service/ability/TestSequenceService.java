package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestSequenceDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestSequence;
import com.demxs.tdm.domain.ability.TestSequenceCondition;
import com.demxs.tdm.domain.ability.TestSequenceItem;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 试验序列Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestSequenceService extends CrudService<TestSequenceDao, TestSequence> {

	@Resource
	private TestSequenceItemService testSequenceItemService;
	@Resource
	private TestSequenceConditionService testSequenceConditionService;
	@Override
	public TestSequence get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestSequence> findList(TestSequence testSequence) {
		return super.findList(testSequence);
	}

	/**
	 * @param page
	 * @param testSequence
	 * @param filter 其他过滤信息    userCredential:人员资质
	 * @return
	 */
	public Page<TestSequence> findPage(Page<TestSequence> page, TestSequence testSequence,Map<String,Object> filter) {
//		return super.findPage(page, testSequence);
		testSequence.setPage(page);
		page.setList(dao.findList(testSequence,page,filter));
		return page;
	}

	/**
	 * @param page
	 * @param testSequence
	 * @param filter
	 * @return
	 */
	public Page<TestSequence> listWithCondition(Page<TestSequence> page, TestSequence testSequence,Map<String,Object> filter) {
		testSequence.setPage(page);
		page.setList(dao.listWithCondition(testSequence,page,filter));
		return page;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestSequence testSequence) {
		if (!testSequence.getIsNewRecord()) {
			testSequenceItemService.deleteBySeqId(testSequence.getId());
			testSequenceConditionService.deleteBySequence(testSequence.getId());
		}
		super.save(testSequence);
		List<TestSequenceItem> testItemsList = testSequence.getTestItemsList();
		if (testItemsList != null) {
			for (int i = 0; i < testItemsList.size(); i++) {
				TestSequenceItem tsi = testItemsList.get(i);
				tsi.setSeqId(testSequence.getId());
				tsi.setId("");
				if(tsi.getSort() == null){
					tsi.setSort(i+1);
				}
//				tsi.setIsNewRecord(true);
				testSequenceItemService.save(tsi);
			}
		}
		List<TestSequenceCondition> conditionsList = testSequence.getConditionsList();
		if (conditionsList != null) {
			for (int i = 0; i < conditionsList.size(); i++) {
				TestSequenceCondition tsc = conditionsList.get(i);
				tsc.setSeqId(testSequence.getId());
				tsc.setId("");
				testSequenceConditionService.save(tsc);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestSequence testSequence) {
		testSequenceConditionService.deleteBySequence(testSequence.getId());
		super.delete(testSequence);
	}

	/**
	 * 根据序列ID 获取序列项（详细）内容
	 * @param id
	 * @return
	 */
	public List<TestSequenceItem> listItem(String id){
		return testSequenceItemService.findBySeqId(id);
	}

	/**
	 * 序列基本详情（树结构）
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> itemTree(String id){
		List<TestSequenceItem> testSequenceItems = this.listItem(id);
		List<Map<String, Object>> mapList = convertToTreeData(testSequenceItems);
		return mapList;
	}

	/**
	 * 序列详情（树结构），试验项目包含试验项等具体内容
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> itemTreeDetail(String id){
		List<TestSequenceItem> testSequenceItems = testSequenceItemService.detailBySeqId(id);
		List<Map<String, Object>> mapList = convertToTreeData(testSequenceItems,id);
		return mapList;
	}

	/**
	 * 把序列试验信息转成树结构（扁平）
	 * @param testSequenceItems
	 * @return
	 */
	public List<Map<String, Object>> convertToTreeData(List<TestSequenceItem> testSequenceItems) {
		return this.convertToTreeData(testSequenceItems, "");
	}
	public List<Map<String, Object>> convertToTreeData(List<TestSequenceItem> testSequenceItems,String parentId) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for (TestSequenceItem t : testSequenceItems){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", t.getId());
			map.put("nodeId", t.getNodeId());
			map.put("pId", t.getParent()!=null ? t.getParent().getNodeId() : parentId);
			map.put("type",t.getFlag());
			map.put("flag",t.getFlag());
			map.put("sort",t.getSort());
			if(AbilityConstants.TestSequenceItemFlag.ITEM.equals(t.getFlag())){
				map.put("item",t.getItem());
//				map.put("children",t.getItem().getTestUnitsList());
			}
			map.put("name", t.getName());
			mapList.add(map);
		}
		return mapList;
	}
}