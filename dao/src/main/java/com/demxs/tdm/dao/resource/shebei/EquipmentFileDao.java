package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentFile;

import java.util.List;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:50
 * @Description:
 */
@MyBatisDao
public interface EquipmentFileDao extends CrudDao<EquipmentFile> {

    List<EquipmentFile> findAll(EquipmentFile  equipmentFile);

    List<EquipmentFile> findEquipmentFileByParentId(EquipmentFile  equipmentFile);

    void  updateVersionInfo(String id);
}
