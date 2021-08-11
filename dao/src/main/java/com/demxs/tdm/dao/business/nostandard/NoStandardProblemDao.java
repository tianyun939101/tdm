package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardProblem;

import java.util.List;

@MyBatisDao
public interface NoStandardProblemDao extends CrudDao<NoStandardProblem> {

    NoStandardProblem getByExecutionId(String executionId);


    List<NoStandardProblem> getListByExecutionId(String executionId);
}
