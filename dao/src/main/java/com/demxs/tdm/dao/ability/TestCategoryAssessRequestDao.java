package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryAssessRequest;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/9/23 10:25
 * @Description:
 */
@MyBatisDao
public interface TestCategoryAssessRequestDao extends CrudDao<TestCategoryAssessRequest> {

    /**
    * @author Jason
    * @date 2020/9/23 10:38
    * @params [request]
    * 修改状态
    * @return int
    */
    int changeStatus(TestCategoryAssessRequest request);

    /**
    * @author Jason
    * @date 2020/9/23 10:38
    * @params [request]
    * 修改审核人
    * @return int
    */
    int changeAuditUser(TestCategoryAssessRequest request);

    /**
    * @author Jason
    * @date 2020/9/23 10:47
    * @params [request]
    * 根据试验室id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryAssessRequest>
    */
    List<TestCategoryAssessRequest> findByLabId(TestCategoryAssessRequest request);

    /**
    * @author Jason
    * @date 2020/9/24 17:15
    * @params [setvId]
    * 根据版本id删除
    * @return int
    */
    int deleteByVersionId(TestCategoryAssessRequest setvId);
}
