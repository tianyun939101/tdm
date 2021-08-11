package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentContent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:50
 * @Description:
 */
@MyBatisDao
public interface EquipmentContentDao extends CrudDao<EquipmentContent> {

    void deleteInfo(EquipmentContent equipmentContent);

    List<EquipmentContent> findAll(EquipmentContent  equipmentContent);

    Integer deleteByInfoId(@Param("defendId") String  defendId);

    void  maintain(@Param("defendId") String  defendId, @Param("leaveBeginDate") String time);
}
