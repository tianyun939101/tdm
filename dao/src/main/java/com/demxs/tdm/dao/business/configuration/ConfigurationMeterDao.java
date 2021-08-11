package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationMeter;

import java.util.List;

/**
 * 构型仪器仪表
 * @author wuhui
 * @date 2021/2/2 19:11
 **/
@MyBatisDao
public interface ConfigurationMeterDao extends CrudDao<ConfigurationMeter> {

    List<ConfigurationMeter> findByVersionId(String cvId);

    void deleteByVersionId(String cvId);
}
