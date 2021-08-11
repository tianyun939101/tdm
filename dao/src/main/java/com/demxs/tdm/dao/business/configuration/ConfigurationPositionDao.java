package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationPosition;

@MyBatisDao
public interface ConfigurationPositionDao extends TreeDao<ConfigurationPosition> {
}
