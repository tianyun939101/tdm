package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardMalfunction;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface NoStandardMalfunctionDao extends CrudDao<NoStandardMalfunction> {
    NoStandardMalfunction findByTaskId(@Param("taskId") String taskId);
}
