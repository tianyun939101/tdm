package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskExecutionDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.TestTaskExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验任务执行Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskExecutionService extends CrudService<TestTaskExecutionDao, TestTaskExecution> {

	@Override
	public TestTaskExecution get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTaskExecution> findList(TestTaskExecution testTaskExecution) {
		return super.findList(testTaskExecution);
	}
	
	@Override
	public Page<TestTaskExecution> findPage(Page<TestTaskExecution> page, TestTaskExecution testTaskExecution) {
		return super.findPage(page, testTaskExecution);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTaskExecution testTaskExecution) {
		super.save(testTaskExecution);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTaskExecution testTaskExecution) {
		super.delete(testTaskExecution);
	}

	/**
	 * 获取下级执行干道
	 * @param parentId 上级执行干道ID
	 * @return
	 * @throws ServiceException
     */
	public List<TestTaskExecution> listByParent(String parentId) throws ServiceException{
		return this.dao.findByParentId(parentId);
	}

	/**
	 * 查询业务主键的所有主干道
	 * @param businessKey 业务主键
	 * @return
	 * @throws ServiceException
	 */
	public List<TestTaskExecution> listByBusinessKey(String businessKey) throws ServiceException{
		return this.dao.findByBusinessKey(businessKey);
	}
}