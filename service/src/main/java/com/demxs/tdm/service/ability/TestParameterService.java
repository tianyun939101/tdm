package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestParameterDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 试验参数Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestParameterService extends CrudService<TestParameterDao, TestParameter> {

	@Override
	public TestParameter get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestParameter> findList(TestParameter testParameter) {
		return super.findList(testParameter);
	}
	
	@Override
	public Page<TestParameter> findPage(Page<TestParameter> page, TestParameter testParameter) {
		return super.findPage(page, testParameter);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestParameter testParameter) {
		super.save(testParameter);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestParameter testParameter) {
		super.delete(testParameter);
	}
	
}