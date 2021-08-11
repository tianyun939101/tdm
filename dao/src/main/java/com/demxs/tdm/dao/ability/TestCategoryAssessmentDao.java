package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryAssessment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/9/17 10:34
 * @Description:
 */
@MyBatisDao
public interface TestCategoryAssessmentDao extends CrudDao<TestCategoryAssessment> {

    /**
    * @author Jason
    * @date 2020/9/17 10:56
    * @params [assessment]
    * 根据试验室id和试验能力id查询
    * @return com.demxs.tdm.domain.ability.TestCategoryAssessment
    */
    TestCategoryAssessment getByLabIdAndCId(TestCategoryAssessment assessment);

    /**
    * @author Jason
    * @date 2020/9/23 13:33
    * @params [assessment]
    * 修改申请批准后应用生效
    * @return int
    */
    int apply(TestCategoryAssessment assessment);

    /**
    * @author Jason
    * @date 2020/9/24 16:44
    * @params [assessment]
    * 改变应用状态
    * @return int
    */
    int changeDataStatus(TestCategoryAssessment assessment);

    /**
    * @author Jason
    * @date 2020/9/24 15:44
    * @params [assessment]
    * 根据版本id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryAssessment>
    */
    List<TestCategoryAssessment> findByVersionId(TestCategoryAssessment assessment);

    /**
    * @author Jason
    * @date 2020/9/24 15:51
    * @params [setvId]
    * 根据版本删除
    * @return int
    */
    int deleteByVersionId(TestCategoryAssessment setvId);

    /**
    * @author Jason
    * @date 2020/9/27 14:40
    * @params [setvId]
    * 根据修改申请删除
    * @return int
    */
    int deleteByRequestId(TestCategoryAssessment setvId);

    /**
    * @author Jason
    * @date 2020/9/27 9:58
    * @params [request]
    * 根据评估申请id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryAssessment>
    */
    List<TestCategoryAssessment> findByRequestId(TestCategoryAssessment request);

    /**
     * @Describe:获取试验室上一版本能力状态
     * @Author:WuHui
     * @Date:19:14 2020/11/10
     * @param cId
     * @param labId
     * @return:java.lang.String
    */
    String findPreVersionAbilityStatus(@Param("cId") String cId, @Param("labId") String labId);
}
