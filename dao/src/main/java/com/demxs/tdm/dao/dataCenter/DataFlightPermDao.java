package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataFlightPerm;

import java.util.List;

/**
 * 试飞信息权限DAO
 */
@MyBatisDao
public interface DataFlightPermDao extends CrudDao<DataFlightPerm> {

    List<DataFlightPerm> getOfficePermByFlightId(String baseId);

    List<DataFlightPerm> getUserPermByFlightId(String baseId);

    void detetePermByFlightId(String baseId);

}