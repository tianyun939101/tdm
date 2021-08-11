package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.LabInfoState;
import com.demxs.tdm.domain.lab.LabInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/10/28 11:02
 **/
@MyBatisDao
public interface LabInfoStateDao extends CrudDao<LabInfoState> {

    LabInfoState getByEntity(@Param("state") LabInfoState state);

    List<Map<String,String>> countAbilityByLabId(@Param("labId") String labId);

    LabInfoState findLabInfoStateByLabId(@Param("labId") String labId);
}
