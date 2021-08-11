package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.SoftwareLibrary;

@MyBatisDao
public interface SoftwareLibraryDao extends CrudDao<SoftwareLibrary> {
}
