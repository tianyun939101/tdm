package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.TestEquipment;

import java.util.List;

@MyBatisDao
public interface TestEquipmentDao extends CrudDao<TestEquipment> {

    List<TestEquipment> findDataList(TestEquipment testEquipment);
}