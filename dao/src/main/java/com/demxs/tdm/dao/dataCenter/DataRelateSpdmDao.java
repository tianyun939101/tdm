package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataRelateSpdm;

import java.util.List;

/**
 * 关联SPDM DAO
 */
@MyBatisDao
public interface DataRelateSpdmDao extends CrudDao<DataRelateSpdm> {

    List<DataRelateSpdm> getByTestId(String testId);

    List<DataRelateSpdm> getBySpdm(DataRelateSpdm dataRelateSpdm);
}