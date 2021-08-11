package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestTypeDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.TestType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验类型管理Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTypeService extends CrudService<TestTypeDao, TestType> {

	@Override
	public TestType get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestType> findList(TestType testType) {
		return super.findList(testType);
	}
	
	@Override
	public Page<TestType> findPage(Page<TestType> page, TestType testType) {
		return super.findPage(page, testType);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestType testType) {
		if (StringUtils.isEmpty(testType.getValid())) {
			testType.setValid("1");
		}
		super.save(testType);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestType testType) {
		super.delete(testType);
	}
	
}