package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataTestInfo;

import java.util.List;

/**
 * 数据中心试验信息DAO
 */
@MyBatisDao
public interface DataTestInfoDao extends CrudDao<DataTestInfo> {

    void deleteByBaseId(String baseId);

    List<DataTestInfo> getByBaseId(String baseId);
}