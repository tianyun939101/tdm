package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationVO;

@MyBatisDao
public interface ConfigurationVODao extends CrudDao<ConfigurationVO> {

    ConfigurationVO getCurVersionDetail(String baseId);

    ConfigurationVO getBaseWithShebei(String id);
}
