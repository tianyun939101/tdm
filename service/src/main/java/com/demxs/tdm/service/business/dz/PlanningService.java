package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.domain.business.dz.DzDevelopmentPlan;
import com.demxs.tdm.domain.business.dz.DzDevelopmentPlanning;

import java.util.List;
import java.util.Map;

public interface PlanningService {
    //Page<DzDevelopmentPlanning> list(Page<DzDevelopmentPlanning> page, DzDevelopmentPlanning dzDevelopmentPlanning);
    void SavePlanning(DzDevelopmentPlan dzDevelopmentPlan);
    Map<String,Object> getplanning(String id);
    int updatePlanning(DzDevelopmentPlan dzDevelopmentPlan);
    void updateByPrimaryKeyPlanning(DzDevelopmentPlanning dzDevelopmentPlanning);
    List<DzDevelopmentPlan> getplanningfilesList(String id);
    List<DzDevelopmentPlan> getplanningfilesList2(String id);
    List<DzDevelopmentPlanning>  selectList(String code, String name, String state);
}
