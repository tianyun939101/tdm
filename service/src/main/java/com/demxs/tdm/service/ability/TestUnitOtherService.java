package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestUnitOtherDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestUnitOther;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项其它信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestUnitOtherService extends CrudService<TestUnitOtherDao, TestUnitOther> {

	@Override
	public TestUnitOther get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestUnitOther> findList(TestUnitOther testUnitOther) {
		return super.findList(testUnitOther);
	}
	
	@Override
	public Page<TestUnitOther> findPage(Page<TestUnitOther> page, TestUnitOther testUnitOther) {
		return super.findPage(page, testUnitOther);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestUnitOther testUnitOther) {
		super.save(testUnitOther);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestUnitOther testUnitOther) {
		super.delete(testUnitOther);
	}
	
}