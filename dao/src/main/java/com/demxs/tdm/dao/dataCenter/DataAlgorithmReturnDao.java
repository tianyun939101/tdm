package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataAlgorithmReturn;

import java.util.List;

/**
 * 算法返回值
 */
@MyBatisDao
public interface DataAlgorithmReturnDao extends CrudDao<DataAlgorithmReturn> {

    void deleteByAlgorithmId(String algorithmId);

    List<DataAlgorithmReturn> getByAlgorithmId(String algorithmId);
}