package com.demxs.tdm.dao.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:45
 * @Description:
 */
@MyBatisDao
public interface TestInfoDao extends CrudDao<TestInfo> {

    /**
    * @author Jason
    * @date 2020/6/17 16:47
    * @params [testTaskId]
    * 根据试验任务id查询试验信息
    * @return com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo
    */
    TestInfo getByTestTaskId(String testTaskId);

    /**
    * @author Jason
    * @date 2020/8/28 15:12
    * @params [testTaskId]
    * 根据试验室id查找
    * @return com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo
    */
    TestInfo getByLabId(TestInfo testInfo);

}
