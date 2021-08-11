package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验组试验计划DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestPlanDao extends CrudDao<TestPlan> {
	/**
	 * 查询申请单计划
	 * @param testGroupId
	 * @return
	 */
	TestPlan getByTestGroupId(@Param("testGroupId") String testGroupId);

	/**
	 * 当前未完成的计划
	 * @return
	 */
	List<TestPlan> findNotCompleted();

	/**
	 * 根据设备ID获取试验计划
	 * @return
     */
	List<TestPlan> findByLab(@Param("labId")String labId);
}