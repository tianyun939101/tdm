package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataAlgorithmParameter;

import java.util.List;

/**
 * 数据中心数据DAO
 */
@MyBatisDao
public interface DataAlgorithmParameterDao extends CrudDao<DataAlgorithmParameter> {

    void deteteByAlgorithmId(String algorithmId);

    List<DataAlgorithmParameter> getAlgorithmId(String algorithmId);
}