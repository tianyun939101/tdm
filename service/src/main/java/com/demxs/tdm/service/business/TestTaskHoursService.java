package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskHoursDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.TestTaskHours;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验任务工时Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskHoursService extends CrudService<TestTaskHoursDao, TestTaskHours> {

	@Override
	public TestTaskHours get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTaskHours> findList(TestTaskHours testTaskHours) {
		return super.findList(testTaskHours);
	}
	
	@Override
	public Page<TestTaskHours> findPage(Page<TestTaskHours> page, TestTaskHours testTaskHours) {
		return super.findPage(page, testTaskHours);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTaskHours testTaskHours) {
		super.save(testTaskHours);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTaskHours testTaskHours) {
		super.delete(testTaskHours);
	}
	
}