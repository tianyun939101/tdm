package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryVersion;

/**
 * @author: Jason
 * @Date: 2020/8/4 09:25
 * @Description:
 */
@MyBatisDao
public interface TestCategoryVersionDao extends CrudDao<TestCategoryVersion> {

    /**
    * @author Jason
    * @date 2020/8/4 9:43
    * @params [version]
    * 启用
    * @return int
    */
    int enabled(TestCategoryVersion version);

    /**
    * @author Jason
    * @date 2020/8/4 9:44
    * @params [version]
    * 停用全部
    * @return int
    */
    int allOutOfEnabled(TestCategoryVersion version);

    /**
    * @author Jason
    * @date 2020/8/4 9:44
    * @params [version]
    * 下发
    * @return int
    */
    int issued(TestCategoryVersion version);

    /**
    * @author Jason
    * @date 2020/8/4 9:46
    * @params [version]
    * 停止下发
    * @return int
    */
    int outOfIssued(TestCategoryVersion version);

    /**
    * @author Jason
    * @date 2020/8/5 13:30
    * @params [version]
    * 根据id查找
    * @return com.demxs.tdm.domain.ability.TestCategoryVersion
    */
    TestCategoryVersion getByCode(TestCategoryVersion version);

    /**
    * @author Jason
    * @date 2020/8/21 9:59
    * @params [version]
    * 查询指定版本下有效申请数量
    * @return int
    */
    int effectedRequestCount(TestCategoryVersion version);

    /**
    * @author Jason
    * @date 2020/8/24 14:36
    * @params []
    * 获取启用版本
    * @return com.demxs.tdm.domain.ability.TestCategoryVersion
    */
    TestCategoryVersion getEnabledVersion();
}
