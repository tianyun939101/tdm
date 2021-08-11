package com.demxs.tdm.service.business;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.EquipmentTestDao;
import com.demxs.tdm.dao.business.WorkPlanDao;
import com.demxs.tdm.domain.ability.EquipmentTest;
import com.demxs.tdm.domain.business.WorkPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class WorkPlanService extends  CrudService<WorkPlanDao, WorkPlan> {

    @Autowired
    WorkPlanDao workPlanDao;


    public Page<WorkPlan> list(Page<WorkPlan> page, WorkPlan workPlan) {
        Page<WorkPlan> Page = super.findPage(page, workPlan);
        return Page;
    }

    public Page<WorkPlan> findPage(Page<WorkPlan> page, WorkPlan entity) {
        entity.setPage(page);
        page.setList(dao.findAllList(entity));
        return page;
    }

    public WorkPlan get(String id) {
        WorkPlan workPlan = super.dao.get(id);
        return workPlan;
    }

   public List<WorkPlan> getWorkPlan(String testId) {
        List<WorkPlan> equipmentTest = workPlanDao.getWorkPlan(testId);
        return equipmentTest;
    }


    public void save(WorkPlan workPlan) {

        super.save(workPlan);

    }

    public void save(List<WorkPlan> workPlanList) {
        for(WorkPlan  workPlan:workPlanList){
            super.save(workPlan);
        }

    }

    public void deleteAllList(List<WorkPlan> workPlanList) {

        for(WorkPlan  workPlan:workPlanList){
            if(StringUtils.isNotEmpty(workPlan.getId())){
                workPlanDao.deleteInfo(workPlan);

            }
        }

    }


}
