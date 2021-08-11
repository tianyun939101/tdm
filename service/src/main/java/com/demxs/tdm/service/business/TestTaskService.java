package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.business.vo.TaskExecuteVO;
import com.demxs.tdm.domain.business.vo.TaskParam;
import com.demxs.tdm.domain.business.vo.TaskVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 试验任务分配Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskService extends CrudService<TestTaskDao, TestTask> {

	@Resource
	private TestPlanDetailService testPlanDetailService;

	@Override
	public TestTask get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTask> findList(TestTask testTask) {
		return super.findList(testTask);
	}
	
	@Override
	public Page<TestTask> findPage(Page<TestTask> page, TestTask testTask) {
		return super.findPage(page, testTask);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTask testTask) {
		super.save(testTask);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTask testTask) {
		super.delete(testTask);
	}

	/**
	 * 终止所有子任务
	 * @param taskId	任务ID
	 * @throws ServiceException
     */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void stopSubTask(String taskId) throws ServiceException{
		List<TestTask> testTasks = this.listByParent(taskId);
		for (TestTask testTask : testTasks) {
			testPlanDetailService.deleteById(testTask.getPlanDetailId());
		}
		this.dao.stopSubTask(taskId);
	}

	/**
	 * 重置所有任务状态为待执行
	 * @param taskId	任务ID
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void resetSubTask(String taskId) throws ServiceException{
		this.dao.resetSubTask(taskId);
	}

	/**
	 * 获取执行干道上的所有任务列表
	 * @param executionId	执行干道ID
	 * @return
     */
	public List<TestTask> listByExecution(String executionId) throws ServiceException{
		return this.dao.findByExecutionId(executionId);
	}

	/**
	 * 获取下级任务
	 * @param parentId 上级任务ID
	 * @return
	 * @throws ServiceException
	 */
	public List<TestTask> listByParent(String parentId) throws ServiceException{
		return this.dao.findByParentId(parentId);
	}

	/**
	 * 根据试验组ID,查询任务执行情况
	 * @param testGroupId 试验组ID
	 * @return
	 * @throws ServiceException
	 */
	public List<TaskExecuteVO> findExecuteDetailByTestGroup(String testGroupId) throws ServiceException{
		return this.dao.findExecuteDetailByTestGroup(testGroupId);
	}

	/**
	 * 获取任务分配列表
	 * @param page
	 * @param taskParam 任务查询参数
     * @return
     */
	public Page<TaskVO> findPage(Page<TaskVO> page, TaskParam taskParam) {
		List<TaskVO> data = this.dao.findPage(page,taskParam);
		page.setList(data);
		return page;
	}


	/**
	 * 获取试验项目打印
	 * @param page
	 * @param taskParam
	 * @return
	 */
	public Page<TestTask> findCheckPage(Page<TestTask> page, TestTask taskParam){
		taskParam.setPage(page);
		taskParam.getSqlMap().put("dsf", dataScopeFilter(taskParam.getCurrentUser(), "o", "u8"));
		page.setList(dao.findCheckList(taskParam));
		return page;
	}

	public void updateImgIds(String taskId,String imgIds){
		this.dao.updateImgIds(taskId,imgIds);
	}

	public List<TestTask> listByBusinessKey(String businessKey) {
		return dao.findByBusinessKey(businessKey);
	}

	/**
	* @author Jason
	* @date 2020/6/30 15:39
	* @params [testTaskId]
	* 根据试验任务id查询试验项目
	* @return com.demxs.tdm.domain.ability.TestItem
	*/
    public TestItem getTestItemById(String testTaskId) {
        return this.dao.getTestItemById(testTaskId);
    }
}