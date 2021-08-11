package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzAbilityAssess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzAbilityAssessDao   extends CrudDao<DzAbilityAssess> {
    
    int deleteByPrimaryKey(String id);

    List<DzAbilityAssess> selectList(@Param("code") String code, @Param("name") String name, @Param("inOut") String inOut, @Param("laboratory") String laboratory);

    List<Map<String,Object>> selectListBysimilar(@Param("similar") String similar);

    Map<String,Object> selectById(@Param("abilityId") String abilityId);

    int deleteinsert(@Param("id") String id);

    DzAbilityAssess selectAuditInfolist(@Param("id") String id);

    int deletesimilar(@Param("similar") String similar);

    int insert(DzAbilityAssess record);

    
    int insertSelective(DzAbilityAssess record);

    
    DzAbilityAssess selectByPrimaryKey(String id);

    
    int updateByPrimaryKeySelective(DzAbilityAssess record);

    
    int updateByPrimaryKey(DzAbilityAssess record);
}