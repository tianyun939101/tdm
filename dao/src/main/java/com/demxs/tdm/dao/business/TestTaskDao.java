package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.business.vo.TaskExecuteVO;
import com.demxs.tdm.domain.business.vo.TaskParam;
import com.demxs.tdm.domain.business.vo.TaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 试验任务分配DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestTaskDao extends CrudDao<TestTask> {

    /**
     * 查询执行干道中的任务
     * @param executionId 执行干道ID
     * @return
     */
    List<TestTask> findByExecutionId(@Param("executionId")String executionId);

    /**
     * 更新所有子任务的状态为终止
     * @param taskId
     */
    void stopSubTask(@Param("taskId")String taskId);

    /**
     * 更新所有子任务的状态为待执行
     * @param taskId
     */
    void resetSubTask(@Param("taskId")String taskId);

    /**
     * 查询父任务下的子任务
     * @param parentId 父任务ID
     * @return
     */
    List<TestTask> findByParentId(@Param("parentId")String parentId);

    /**
     * 查询任务分配列表
     * @param page 分页对象
     * @param taskParam 任务试验参数
     * @return
     */
    List<TaskVO> findPage(@Param("page") Page<TaskVO> page, @Param("param") TaskParam taskParam);

    /**
     * 根据试验组ID,查询任务执行情况
     * @param testGroupId 试验组ID
     * @return
     */
    List<TaskExecuteVO> findExecuteDetailByTestGroup(@Param("testGroupId") String testGroupId);


    /**
     * 获取试验项目打印
     * @param testTask
     * @return
     */
    List<TestTask> findCheckList(TestTask testTask);


    /**
     * 修改采集文件ids
     * @param taskId
     * @param imgIds
     */
    void updateImgIds(@Param("taskId")String taskId, @Param("imgIds")String imgIds);

    List<TestTask> findByBusinessKey(@Param("businessKey")String businessKey);

    /**
    * @author Jason
    * @date 2020/6/8 18:01
    * @params [testTaskId]
    * 根据id关联查询出试验步骤信息，阻燃大屏试验信息面板数据支持
    * @return java.util.List<com.demxs.tdm.domain.ability.TestUnit>
    */
    TestItem getTestItemById(String testTaskId);
}