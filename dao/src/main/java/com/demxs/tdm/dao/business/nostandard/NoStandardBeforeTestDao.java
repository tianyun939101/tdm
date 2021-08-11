package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardBeforeTest;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 15:22
 * @Description:
 */

@MyBatisDao
public interface NoStandardBeforeTestDao extends CrudDao<NoStandardBeforeTest> {

    NoStandardBeforeTest getByResourceId(String resourceId);
}
