package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestTaskAssignUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验任务数据DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestTaskAssignUserDao extends CrudDao<TestTaskAssignUser> {

    /**
     * 根据任务ID查询任务分配记录
     * @param taskId    任务ID
     * @return
     */
    List<TestTaskAssignUser> findByTaskId(@Param("taskId")String taskId);

    /**
     * 获取任务最后一次的分配信息
     * @param taskId 任务ID
     * @return
     */
    TestTaskAssignUser getByLast(@Param("taskId")String taskId);
}