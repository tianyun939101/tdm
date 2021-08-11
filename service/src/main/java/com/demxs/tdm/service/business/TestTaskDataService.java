package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskDataDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.TestTaskData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验任务数据Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskDataService extends CrudService<TestTaskDataDao, TestTaskData> {

	@Override
	public TestTaskData get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTaskData> findList(TestTaskData testTaskData) {
		return super.findList(testTaskData);
	}
	
	@Override
	public Page<TestTaskData> findPage(Page<TestTaskData> page, TestTaskData testTaskData) {
		return super.findPage(page, testTaskData);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTaskData testTaskData) {
		super.save(testTaskData);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTaskData testTaskData) {
		super.delete(testTaskData);
	}
	
}