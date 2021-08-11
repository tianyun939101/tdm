package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.dz.DzDevelopmentPlanDao;
import com.demxs.tdm.dao.business.dz.DzDevelopmentPlanningDao;
import com.demxs.tdm.domain.business.dz.DzDevelopmentPlan;
import com.demxs.tdm.domain.business.dz.DzDevelopmentPlanning;
import com.demxs.tdm.service.business.dz.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlanningServiceImpl extends CrudService<DzDevelopmentPlanningDao, DzDevelopmentPlanning> implements PlanningService {
    @Autowired
    DzDevelopmentPlanningDao dzDevelopmentPlanningDao;
    @Autowired
    DzDevelopmentPlanDao dzDevelopmentPlanDao;
    @Override
    public List<DzDevelopmentPlanning>  selectList (String code,String name,String state) {
        List<DzDevelopmentPlanning>  list = dzDevelopmentPlanningDao.selectList(code,name,state);
        return list;
    }

    @Override
    public Map<String,Object> getplanning(String id) {
        Map<String,Object> dzDevelopmentPlanning1 = dzDevelopmentPlanningDao.getplanning(id);
        return dzDevelopmentPlanning1;
    }

    @Override
    public List<DzDevelopmentPlan> getplanningfilesList(String id) {
        List<DzDevelopmentPlan> dzDevelopmentPlanning2 = dzDevelopmentPlanDao.getplanningfilesList(id);
        return dzDevelopmentPlanning2;
    }

    @Override
    public List<DzDevelopmentPlan> getplanningfilesList2(String id) {
        List<DzDevelopmentPlan> dzDevelopmentPlanning3 = dzDevelopmentPlanDao.getplanningfilesList2(id);
        return dzDevelopmentPlanning3;
    }

    @Override
    public int updatePlanning(DzDevelopmentPlan dzDevelopmentPlan) {
        int  dzDevelopmentPlanning = dzDevelopmentPlanDao.updatePlanning(dzDevelopmentPlan);
        return dzDevelopmentPlanning;
    }

    @Override
    public void updateByPrimaryKeyPlanning(DzDevelopmentPlanning dzDevelopmentPlanning) {
        dzDevelopmentPlanningDao.updateByPrimaryKey(dzDevelopmentPlanning);
    }

    @Override
    public void save(DzDevelopmentPlanning dzDevelopmentPlanning) {
        dzDevelopmentPlanningDao.insert(dzDevelopmentPlanning);
    }

    @Override
    public void SavePlanning(DzDevelopmentPlan dzDevelopmentPlan) {
        dzDevelopmentPlanDao.insert(dzDevelopmentPlan);
    }
}
