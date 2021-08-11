package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.TestPlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验组试验计划详情DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestPlanDetailDao extends CrudDao<TestPlanDetail> {

    /**
     * 根据计划ID查询计划中的详细信息
     * @param planId    计划ID
     * @return
     */
    List<TestPlanDetail> findByPlan(@Param("planId")String planId);

    /**
     * 删除计划所有详情
     * @param planId
     */
    void deleteByPlanId(@Param("planId") String planId);
}