package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataVedio;

import java.util.List;

@MyBatisDao
public interface DataVedioDao extends CrudDao<DataVedio> {

    List<DataVedio> findDataList(DataVedio  dataVedio);

}