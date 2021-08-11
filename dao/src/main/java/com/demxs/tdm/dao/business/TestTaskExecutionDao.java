package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestTaskExecution;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 试验执行DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestTaskExecutionDao extends CrudDao<TestTaskExecution> {

    /**
     * 根据上级执行ID查询执行干道
     * @param parentId  上级执行干道ID
     * @return
     */
    List<TestTaskExecution> findByParentId(@Param("parentId")String parentId);

    /**
     * 查询业务主键的所有主干道
     * @param businessKey  业务主键
     * @return
     */
    List<TestTaskExecution> findByBusinessKey(@Param("businessKey")String businessKey);
}