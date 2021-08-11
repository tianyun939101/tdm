package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestItemCoditionDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestItemCodition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目试验条件Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestItemCoditionService extends CrudService<TestItemCoditionDao, TestItemCodition> {

	@Override
	public TestItemCodition get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestItemCodition> findList(TestItemCodition testItemCodition) {
		return super.findList(testItemCodition);
	}
	
	@Override
	public Page<TestItemCodition> findPage(Page<TestItemCodition> page, TestItemCodition testItemCodition) {
		return super.findPage(page, testItemCodition);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestItemCodition testItemCodition) {
		super.save(testItemCodition);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestItemCodition testItemCodition) {
		super.delete(testItemCodition);
	}
	/**
	 * 删除试验项目所有关联项
	 * @param itemId
	 */
	public void deleteByItem(String itemId){
		dao.deleteByItem(itemId);
	}
	
}