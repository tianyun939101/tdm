package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.dz.DzProjectclassDao;
import com.demxs.tdm.domain.business.dz.DzProjectclass;
import com.demxs.tdm.service.business.dz.ProjectclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectclassServiceImpl  extends TreeService<DzProjectclassDao, DzProjectclass> implements ProjectclassService {
    @Autowired
    DzProjectclassDao dzProjectclassDao;
    @Override
    public List<DzProjectclass> findList(DzProjectclass entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public Page<DzProjectclass> findPage(Page<DzProjectclass> page, DzProjectclass dzAbilityClass) {
        dzAbilityClass.getSqlMap().put("dsf", dataScopeFilter(dzAbilityClass.getCurrentUser(), "o", "u8"));
        Page<DzProjectclass> Ability = super.findPage(page, dzAbilityClass);
        return Ability;
    }

    @Override
    public List<DzProjectclass> Abilitylist(String parentIds) {
        List<DzProjectclass>  Abilitylists = dzProjectclassDao.selectByList(parentIds);
        return Abilitylists;
    }

    @Override
    public DzProjectclass get(String id) {
        DzProjectclass dzAbilityClass = super.dao.get(id);
        if(dzAbilityClass.getParentId() != null &&
                StringUtils.isNotBlank(dzAbilityClass.getParentId())){
            dzAbilityClass.setParent(super.dao.get(dzAbilityClass.getParentId()));
        }
        return dzAbilityClass;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzProjectclass dzProjectclass) {
        if(StringUtils.isNotBlank(dzProjectclass.getParent().getId())){
            dzProjectclass.setParentId(dzProjectclass.getParent().getId());
        }
        super.save(dzProjectclass);
    }
}
