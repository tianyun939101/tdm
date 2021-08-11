package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.AlgorithmType;

/**
 * 算法类别DAO
 */
@MyBatisDao
public interface AlgorithmTypeDao extends TreeDao<AlgorithmType> {
	
}