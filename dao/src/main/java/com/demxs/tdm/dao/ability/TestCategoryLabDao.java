package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryLab;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/10 09:05
 * @Description:
 */
@MyBatisDao
public interface TestCategoryLabDao extends CrudDao<TestCategoryLab> {

    /**
    * @author Jason
    * @date 2020/8/11 10:25
    * @params [lab]
    * 根据试验能力id、版本id、试验室id查询
    * @return com.demxs.tdm.domain.ability.TestCategoryLab
    */
    TestCategoryLab getByCvlId(TestCategoryLab lab);

    /**
    * @author Jason
    * @date 2020/8/10 10:26
    * @params [lab]
    * 根据版本id和试验室id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryLab>
    */
    List<TestCategoryLab> findByVersionIdAndLabId(TestCategoryLab lab);

    /**
    * @author Jason
    * @date 2020/8/10 20:12
    * @params [lab]
    * 根据版本id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryLab>
    */
    List<TestCategoryLab> findByVersionId(TestCategoryLab lab);

    /**
    * @author Jason
    * @date 2020/8/10 16:34
    * @params [lab]
    * 根据试验类型id查询集合
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryLab>
    */
    List<TestCategoryLab> findListWithCategoryId(TestCategoryLab lab);

    /**
    * @author Jason
    * @date 2020/8/10 9:08
    * @params []
    * 根据试验室和版本id删除，清空该试验室该版本的所有能力具备级别
    * @return int
    */
    int deleteByLabIdAndVersionId(TestCategoryLab lab);

    /**
    * @author Jason
    * @date 2020/8/10 11:18
    * @params [lab]
    * 根据试验室id和试验能力id删除
    * @return int
    */
    int deleteByLabIdAndCategoryId(TestCategoryLab lab);

    /**
    * @author Jason
    * @date 2020/8/11 9:20
    * @params [lab]
    * 根据版本id删除
    * @return int
    */
    int deleteByVersionId(TestCategoryLab lab);

    /**
     * @Describe:获取建设计划做大年度
     * @Author:WuHui
     * @Date:18:46 2020/12/17
     * @param
     * @return:java.lang.Integer
    */
    Integer getAbilityPlanMaxYear();

    /**
     * @Describe:更具版本号获试验室应具备能力
     * @Author:WuHui
     * @Date:14:46 2020/12/19
     * @param lab
     * @return:java.util.List<com.demxs.tdm.domain.ability.TestCategoryLab>
    */
    List<TestCategoryLab> findLabInfoAbilityByVersion(TestCategoryLab lab);
}
