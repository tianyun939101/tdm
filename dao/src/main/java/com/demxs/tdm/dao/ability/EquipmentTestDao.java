package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.EquipmentTest;

import java.util.List;

@MyBatisDao
public interface EquipmentTestDao extends CrudDao<EquipmentTest> {


      void  deleteInfo(EquipmentTest equipmentTest);


      List<EquipmentTest>  getEquipmentTest(String  testId);

      /**
       * @Describe:查询所有能力评估设备数据
       * @Author:WuHui
       * @Date:10:45 2020/11/3
       * @return:java.util.List<com.demxs.tdm.domain.ability.EquipmentTest>
      */
      List<EquipmentTest> findAllEquipment();
}