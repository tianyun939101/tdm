package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.AbilityPlanDao;
import com.demxs.tdm.domain.ability.AbilityPlan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuhui
 * @date 2020/12/15 16:37
 **/
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class AbilityPlanService extends CrudService<AbilityPlanDao, AbilityPlan> {
}
