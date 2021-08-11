package com.demxs.tdm.dao.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.SubCenterTestTask;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:45
 * @Description:
 */
@MyBatisDao
public interface SubCenterTestTaskDao extends CrudDao<SubCenterTestTask> {

    /**
    * @author Jason
    * @date 2020/6/17 16:48
    * @params [labId]
    * 根据试验室id查询所有试验任务
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.subcenterconfig.TestTask>
    */
    List<SubCenterTestTask> findByLabId(String labId);
}
