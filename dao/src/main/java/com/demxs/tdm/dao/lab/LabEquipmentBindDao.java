package com.demxs.tdm.dao.lab;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabEquipmentBind;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/10 11:23
 * @Description:
 */
@MyBatisDao
public interface LabEquipmentBindDao extends CrudDao<LabEquipmentBind> {

    /**
    * @author Jason
    * @date 2020/6/10 13:03
    * @params [labEquipmentBind]
    * 根据试验室id和设备id查询绑定信息
    * @return com.demxs.tdm.domain.lab.LabEquipmentBind
    */
    LabEquipmentBind getByLabIdAndEquipId(LabEquipmentBind labEquipmentBind);

    /**
    * @author Jason
    * @date 2020/6/17 17:02
    * @params [labId]
    * 根据试验室id查询绑定信息
    * @return java.util.List<com.demxs.tdm.domain.lab.LabEquipmentBind>
    */
    List<LabEquipmentBind> findByLabId(String labId);
}
