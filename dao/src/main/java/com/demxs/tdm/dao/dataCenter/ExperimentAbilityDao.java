package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.reportstatement.LabAbilityCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface ExperimentAbilityDao {

    List<Map<String, Object>> find(@Param("centerName") String name);
    List<Map<String, Object>> findCount(@Param("centerName") String name);
    List<Map<String, Object>> getCenterAbilityLevel(@Param("centerName") String name);
    List<Map<String, Object>> abilityBuild(@Param("centerName") String name);
    List<Map<String, Object>> findStatus(@Param("centerName") String name);
    List<String> centerName();
    //
    List<LabAbilityCount> labAbilityCount(@Param("status") String status);
    List<LabAbilityCount> verificationCapabilityCount(@Param("status") String status);
    List<LabAbilityCount> abilityBUildCount(@Param("status") String status,@Param("centerName") String centerName);
}
