package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryAttr;

import java.util.List;

/**
 * @Describe:
 * @Author:WuHui
 * @Date:15:49 2020/9/5
 * @return:
*/

@MyBatisDao
public interface TestCategoryAttrDao extends CrudDao<TestCategoryAttr> {

    /**
    * @author Jason
    * @date 2020/9/8 11:30
    * @params [attr]
    * 根据试验能力id、试验能力版本id、试验室id查询
    * @return com.demxs.tdm.domain.ability.TestCategoryAttr
    */
    TestCategoryAttr getByCvlId(TestCategoryAttr attr);

    /**
    * @author Jason
    * @date 2020/9/8 11:30
    * @params [testCategoryAttr]
    * 根据试验能力id查询
    * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryAttr>
    */
    List<TestCategoryAttr> findAttrByCategoryId(TestCategoryAttr testCategoryAttr);

    /**
    * @author Jason
    * @date 2020/9/24 19:00
    * @params [attr]
    * 根据版本id删除
    * @return int
    */
    int deleteByVersionId(TestCategoryAttr attr);
}
