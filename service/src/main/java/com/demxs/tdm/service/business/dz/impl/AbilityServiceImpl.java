package com.demxs.tdm.service.business.dz.impl;

import com.alibaba.fastjson.JSON;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.dz.DzAbilityClassDao;
import com.demxs.tdm.domain.business.dz.DzAbilityClass;
import com.demxs.tdm.service.business.dz.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AbilityServiceImpl extends TreeService<DzAbilityClassDao, DzAbilityClass>  implements AbilityService {
    @Autowired
    DzAbilityClassDao dzAbilityClassDao;
    @Override
    public List<DzAbilityClass> findList(DzAbilityClass entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public Page<DzAbilityClass> findPage(Page<DzAbilityClass> page, DzAbilityClass dzAbilityClass) {
        dzAbilityClass.getSqlMap().put("dsf", dataScopeFilter(dzAbilityClass.getCurrentUser(), "o", "u8"));
        Page<DzAbilityClass> Ability = super.findPage(page, dzAbilityClass);
        return Ability;
    }

    @Override
    public  List<DzAbilityClass> Abilitylist(String parentIds) {
        List<DzAbilityClass>  Abilitylists = dzAbilityClassDao.selectByList(parentIds);
        return Abilitylists;
    }

    @Override
    public DzAbilityClass get(String id) {
        DzAbilityClass dzAbilityClass = super.dao.get(id);
        if(dzAbilityClass.getParentId() != null &&
                StringUtils.isNotBlank(dzAbilityClass.getParentId())){
            dzAbilityClass.setParent(super.dao.get(dzAbilityClass.getParentId()));
        }
        return dzAbilityClass;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzAbilityClass dzAbilityClass) {
        if(StringUtils.isNotBlank(dzAbilityClass.getParent().getId())){
            dzAbilityClass.setParentId(dzAbilityClass.getParent().getId());
        }
        super.save(dzAbilityClass);
    }
}
