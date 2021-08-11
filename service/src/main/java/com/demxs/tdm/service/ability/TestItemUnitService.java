package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestItemUnitDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestItemUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 试验项目试验项Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestItemUnitService extends CrudService<TestItemUnitDao, TestItemUnit> {

	@Override
	public TestItemUnit get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestItemUnit> findList(TestItemUnit testItemUnit) {
		return super.findList(testItemUnit);
	}
	
	@Override
	public Page<TestItemUnit> findPage(Page<TestItemUnit> page, TestItemUnit testItemUnit) {
		return super.findPage(page, testItemUnit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestItemUnit testItemUnit) {
		super.save(testItemUnit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestItemUnit testItemUnit) {
		super.delete(testItemUnit);
	}
	/**
	 * 删除试验项目所有关联项
	 * @param itemId
	 */
	public void deleteByItem(String itemId){
		dao.deleteByItem(itemId);
	}
}