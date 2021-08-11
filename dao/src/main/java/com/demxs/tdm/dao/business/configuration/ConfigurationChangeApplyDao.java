package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationChangeApply;

@MyBatisDao
public interface ConfigurationChangeApplyDao extends CrudDao<ConfigurationChangeApply> {
}
