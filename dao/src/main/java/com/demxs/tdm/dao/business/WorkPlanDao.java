package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.WorkPlan;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/25 15:19
 * @Description:
 */
@MyBatisDao
public interface WorkPlanDao extends CrudDao<WorkPlan> {

    void  deleteInfo(WorkPlan workPlanDao);


    List<WorkPlan> getWorkPlan(String  testId);
}
