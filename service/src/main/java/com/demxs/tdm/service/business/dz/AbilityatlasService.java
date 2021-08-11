package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.domain.business.dz.DzAbilityAtlas;

import java.util.List;
import java.util.Map;

public interface AbilityatlasService {
    List<DzAbilityAtlas> AbilityAtlaslist(DzAbilityAtlas dzAbilityAtlas);
    void save(DzAbilityAtlas zAbilityAtlas);
    Map<String,Object> getone(String id);
    List<DzAbilityAtlas> AbilityAtlaspggllist(DzAbilityAtlas dzAbilityAtlas);
    List<DzAbilityAtlas> AbilityAtlasJslist(DzAbilityAtlas dzAbilityAtlas);
    void updateAbilityState(String id, String state);
}
