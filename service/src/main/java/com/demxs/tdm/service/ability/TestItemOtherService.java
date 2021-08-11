package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestItemOtherDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestItemOther;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目其它信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestItemOtherService extends CrudService<TestItemOtherDao, TestItemOther> {

	@Override
	public TestItemOther get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestItemOther> findList(TestItemOther testItemOther) {
		return super.findList(testItemOther);
	}
	
	@Override
	public Page<TestItemOther> findPage(Page<TestItemOther> page, TestItemOther testItemOther) {
		return super.findPage(page, testItemOther);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestItemOther testItemOther) {
		super.save(testItemOther);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestItemOther testItemOther) {
		super.delete(testItemOther);
	}
	
}