package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;

import java.util.List;

@MyBatisDao
public interface DataBackUpDao extends CrudDao<DataBackUp> {

    List<DataBackUp> findDataList(DataBackUp dataBackUp);
}