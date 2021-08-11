package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.domain.business.dz.DzAbilityClass;

import java.util.List;

public interface AbilityService {
    List<DzAbilityClass> Abilitylist(String parentIds);
}
