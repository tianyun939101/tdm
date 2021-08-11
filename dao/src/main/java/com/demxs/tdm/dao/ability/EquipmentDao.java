package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.Equipment;

import java.util.List;

@MyBatisDao
public interface EquipmentDao extends CrudDao<Equipment> {


     List<Equipment>  findAllList(Equipment  equipment);
}