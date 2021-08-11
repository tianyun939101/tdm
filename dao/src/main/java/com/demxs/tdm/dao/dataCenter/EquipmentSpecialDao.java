package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.EquipmentSpecial;

import java.util.List;

@MyBatisDao
public interface EquipmentSpecialDao extends CrudDao<EquipmentSpecial> {

    List<EquipmentSpecial> findDataList(EquipmentSpecial equipmentSpecial);
}