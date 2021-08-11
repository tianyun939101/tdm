package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataFile;
import com.demxs.tdm.domain.dataCenter.DataReport;

@MyBatisDao
public interface DataFileDao extends CrudDao<DataFile> {

}