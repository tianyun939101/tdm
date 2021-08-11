package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzAbilityAtlas;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzAbilityAtlasDao  extends CrudDao<DzAbilityAtlas> {
    int insert(DzAbilityAtlas record);
    List<DzAbilityAtlas> selectByList(@Param("levelNumber") String levelNumber, @Param("name") String name, @Param("inOut") String inOut, @Param("laboratory") String laboratory, @Param("category") String category, @Param("code") String code);
    List<DzAbilityAtlas> selectpgglByList(@Param("levelNumber") String levelNumber, @Param("name") String name, @Param("inOut") String inOut, @Param("laboratory") String laboratory, @Param("category") String category, @Param("code") String code);
    List<DzAbilityAtlas> selectJsByList(@Param("levelNumber") String levelNumber, @Param("name") String name, @Param("inOut") String inOut, @Param("laboratory") String laboratory, @Param("category") String category, @Param("code") String code);
    List<DzAbilityAtlas> selectByTgList(@Param("ids") String[] ids);
    Map<String,Object> selectByone(String id);
    int updateAbility_atlas(@Param("id") String id, @Param("state") String state);
    int updateAbilityState(@Param("id") String id, @Param("state") String state);
    int updateAbilityStateByRelationids(@Param("relationIds") String relationIds, @Param("state") String state);
    /**
    * @author Jason
    * @date 2020/6/5 10:07
    * @params [categoryId]
    * 根据试验类型查询试验室ID，大屏数据支持
    * @return java.util.List<com.demxs.tdm.domain.business.dz.DzAbilityAtlas>
    */
    List<String> findByCategoryId(List<String> categoryId);
}