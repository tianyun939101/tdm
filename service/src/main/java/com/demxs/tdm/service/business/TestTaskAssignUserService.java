package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskAssignUserDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.TestTaskAssignUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验任务人员分配记录Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskAssignUserService extends CrudService<TestTaskAssignUserDao, TestTaskAssignUser> {

	@Override
	public TestTaskAssignUser get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTaskAssignUser> findList(TestTaskAssignUser testTaskAssignUser) {
		return super.findList(testTaskAssignUser);
	}
	
	@Override
	public Page<TestTaskAssignUser> findPage(Page<TestTaskAssignUser> page, TestTaskAssignUser testTaskAssignUser) {
		return super.findPage(page, testTaskAssignUser);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTaskAssignUser testTaskAssignUser) {
		super.save(testTaskAssignUser);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTaskAssignUser testTaskAssignUser) {
		super.delete(testTaskAssignUser);
	}

	public List<TestTaskAssignUser> listByTaskId(String taskId) throws ServiceException{
		return this.dao.findByTaskId(taskId);
	}

	/**
	 * 获取任务最后一次的分配信息
	 * @param taskId 任务ID
	 * @return
	 */
	public TestTaskAssignUser getByLast(String taskId){
		return this.dao.getByLast(taskId);
	}
}