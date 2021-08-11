package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.EquipmentTest;
import com.demxs.tdm.domain.ability.StandardTest;

@MyBatisDao
public interface StandardTestDao extends CrudDao<StandardTest> {


      void  deleteInfo(StandardTest standardTest);
}