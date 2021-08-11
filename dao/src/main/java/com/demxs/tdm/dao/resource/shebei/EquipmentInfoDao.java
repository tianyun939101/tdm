package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentFile;
import com.demxs.tdm.domain.resource.shebei.EquipmentInfo;

import java.util.List;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:50
 * @Description:
 */
@MyBatisDao
public interface EquipmentInfoDao extends CrudDao<EquipmentInfo> {

    void deleteInfo(EquipmentInfo equipmentInfo);

    List<EquipmentInfo>  findAll(EquipmentInfo  equipmentInfo);

}
