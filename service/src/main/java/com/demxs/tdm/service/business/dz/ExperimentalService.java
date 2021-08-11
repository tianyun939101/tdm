package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.domain.business.dz.DzAbilityAssess;
import com.demxs.tdm.domain.business.dz.DzAbilityAtlas;

import java.util.List;
import java.util.Map;

public interface ExperimentalService {
    List<DzAbilityAssess> selectList(String code, String name, String inOut, String laboratory);
    List<DzAbilityAtlas> AbilityAtlaslist(String[] ids);
    Map<String,Object>  selectone(String id);
    void deleteinsert(String id);
    List<Map<String,Object>> selectListBysimilar(String similar);
    void deletesimilar(String similar);
    void updateAbility_atlas(String id, String state);
    DzAbilityAssess getAuditInfolist(String id);
}
