package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskUserHoursDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.TestTaskUserHours;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验人员任务工时Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskUserHoursService extends CrudService<TestTaskUserHoursDao, TestTaskUserHours> {

	@Override
	public TestTaskUserHours get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTaskUserHours> findList(TestTaskUserHours testTaskUserHours) {
		return super.findList(testTaskUserHours);
	}
	
	@Override
	public Page<TestTaskUserHours> findPage(Page<TestTaskUserHours> page, TestTaskUserHours testTaskUserHours) {
		return super.findPage(page, testTaskUserHours);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTaskUserHours testTaskUserHours) {
		super.save(testTaskUserHours);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTaskUserHours testTaskUserHours) {
		super.delete(testTaskUserHours);
	}
	
}