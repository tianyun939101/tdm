package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryObserver;

/**
 * @author: Jason
 * @Date: 2020/9/1 14:41
 * @Description:
 */
@MyBatisDao
public interface TestCategoryObserverDao extends CrudDao<TestCategoryObserver> {

    /**
    * @author Jason
    * @date 2020/9/2 15:04
    * @params [observer]
    * 根据用户id和试验能力id删除
    * @return int
    */
    int deleteByUserIdAndCId(TestCategoryObserver observer);
}
