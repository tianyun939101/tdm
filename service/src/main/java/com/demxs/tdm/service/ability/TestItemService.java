package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestItemDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.ability.TestItemCodition;
import com.demxs.tdm.domain.ability.TestItemUnit;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.service.lab.LabTestItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 试验项目Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestItemService extends CrudService<TestItemDao, TestItem> {
	@Resource
	private  TestItemUnitService testItemUnitService;
	@Resource
	private TestItemCoditionService testItemCoditionService;
	@Resource
	private LabTestItemService labTestItemService;
	@Override
	public TestItem get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestItem> findList(TestItem testItem) {
		return super.findList(testItem);
	}
	
	@Override
	public Page<TestItem> findPage(Page<TestItem> page, TestItem testItem) {
		return super.findPage(page, testItem);
	}
	/**
	 * 继承 findlist ,返回结果包含试验条件信息
	 * @param page
	 * @param testItem
	 * @return
	 */
	public Page<TestItem> listWithCondition(Page<TestItem> page, TestItem testItem){
		testItem.setPage(page);
		page.setList(dao.listWithCondition(testItem));
		return page;
	}

	public List<TestItem> noStandardJoinFind(TestItem testItem){
	    return super.dao.noStandardJoinFind(testItem);
    }

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestItem testItem) {
		if(!testItem.getIsNewRecord()){
			testItemCoditionService.deleteByItem(testItem.getId());
			testItemUnitService.deleteByItem(testItem.getId());
		}
		//存储基本数据
		super.save(testItem);
		//试验条件
		if (testItem.getConditionsList() != null) {
			for (TestItemCodition tc : testItem.getConditionsList()){
				tc.setItemId(testItem.getId());
				tc.setId("");
				testItemCoditionService.save(tc);
			}
		}
		//试验信息
		if (testItem.getTestUnitsList() != null) {
			for (int i = 0; i < testItem.getTestUnitsList().size(); i++) {
				TestItemUnit tiu = testItem.getTestUnitsList().get(i);
				tiu.setItemId(testItem.getId());	//项目id
				tiu.setId("");
				tiu.setSort(i);	//序号
				testItemUnitService.save(tiu);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestItem testItem) {
		LabTestItem filter = new LabTestItem();
		filter.setItem(testItem);
		if (!labTestItemService.findList(filter).isEmpty()) {
			throw new ServiceException("试验室关联了此试验项目，删除失败！");
		}
		testItemCoditionService.deleteByItem(testItem.getId());
		testItemUnitService.deleteByItem(testItem.getId());
		super.delete(testItem);
	}
	
}