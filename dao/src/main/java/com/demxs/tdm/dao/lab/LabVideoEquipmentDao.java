package com.demxs.tdm.dao.lab;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabVideoEquipment;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/11 16:59
 * @Description:
 */
@MyBatisDao
public interface LabVideoEquipmentDao extends CrudDao<LabVideoEquipment> {

    /**
    * @author Jason
    * @date 2020/6/11 17:00
    * @params [videoEquipment]
    * 根据试验室id查找关联的视频设备
    * @return java.util.List<com.demxs.tdm.domain.lab.LabVideoEquipment>
    */
    List<LabVideoEquipment> findByLabId(String labId);

    /**
    * @author Jason
    * @date 2020/8/28 15:16
    * @params [video]
    * 根据试验室id查询
    * @return com.demxs.tdm.domain.lab.LabVideoEquipment
    */
    LabVideoEquipment getByLabId(LabVideoEquipment video);
}
