package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategorySub;

import java.util.List;

@MyBatisDao
public interface TestCategorySubDao  extends CrudDao<TestCategorySub> {

    /**
     * @author Jason
     * @date 2020/9/8 11:30
     * @params [attr]
     * 根据试验能力id、试验能力版本id、试验室id查询
     * @return com.demxs.tdm.domain.ability.TestCategoryAttr
     */
    TestCategorySub getByCvlId(TestCategorySub sub);

    /**
     * @author Jason
     * @date 2020/9/8 11:30
     * @params [testCategoryAttr]
     * 根据试验能力id查询
     * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryAttr>
     */
    List<TestCategorySub> findAttrByCategoryId(TestCategorySub testCategorySub);

    /**
     * @author Jason
     * @date 2020/9/24 19:00
     * @params [attr]
     * 根据版本id删除
     * @return int
     */
    int deleteByVersionId(TestCategorySub sub);
}
