package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryProject;

/**
 * @author: Jason
 * @Date: 2020/9/29 16:27
 * @Description:
 */
@MyBatisDao
public interface TestCategoryProjectDao extends CrudDao<TestCategoryProject> {

    /**
    * @author Jason
    * @date 2020/9/29 16:36
    * @params [project]
    * 根据试验室和试验能力id查询
    * @return com.demxs.tdm.domain.ability.TestCategoryProject
    */
    TestCategoryProject getByLabIdAndCId(TestCategoryProject project);

    /**
    * @author Jason
    * @date 2020/9/29 16:37
    * @params [project]
    * 根据试验室和试验能力id删除
    * @return int
    */
    int deleteByLabIdAndCId(TestCategoryProject project);
}
