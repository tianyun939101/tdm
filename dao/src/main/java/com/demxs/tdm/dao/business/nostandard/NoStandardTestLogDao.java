package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestLog;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/7 11:00
 * @Description:
 */
@MyBatisDao
public interface NoStandardTestLogDao extends CrudDao<NoStandardTestLog> {

    int changeStatus(NoStandardTestLog testLog);

    List<NoStandardTestLog> getByExecutionId(NoStandardTestLog  noStandardTestLog);
}
