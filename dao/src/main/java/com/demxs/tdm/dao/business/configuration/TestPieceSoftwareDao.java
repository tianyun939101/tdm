package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.TestPiceSoftware;
import com.demxs.tdm.domain.business.configuration.TestPiece;

@MyBatisDao
public interface TestPieceSoftwareDao extends CrudDao<TestPiceSoftware> {
}
