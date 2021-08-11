package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestConditionDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验条件Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestConditionService extends CrudService<TestConditionDao, TestCondition> {

	@Override
	public TestCondition get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestCondition> findList(TestCondition testCondition) {
		return super.findList(testCondition);
	}
	
	@Override
	public Page<TestCondition> findPage(Page<TestCondition> page, TestCondition testCondition) {
		return super.findPage(page, testCondition);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestCondition testCondition) {
		super.save(testCondition);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestCondition testCondition) {
		super.delete(testCondition);
	}
	
}