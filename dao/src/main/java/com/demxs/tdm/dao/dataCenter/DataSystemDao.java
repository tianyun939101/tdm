package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataSystem;

import java.util.List;

@MyBatisDao
public interface DataSystemDao extends CrudDao<DataSystem> {

    List<DataSystem> findDataList(DataSystem DataSystem);

    DataSystem   getDataSystem(DataSystem DataSystem);
}