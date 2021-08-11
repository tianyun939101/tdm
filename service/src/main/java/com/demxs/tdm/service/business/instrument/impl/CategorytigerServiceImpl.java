package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.instrument.DzCategoryDao;
import com.demxs.tdm.domain.business.instrument.DzCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategorytigerServiceImpl  extends TreeService<DzCategoryDao, DzCategory> {
    @Autowired
    DzCategoryDao dzCategoryDao;
    @Override
    public List<DzCategory> findList(DzCategory entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public Page<DzCategory> findPage(Page<DzCategory> page, DzCategory dzAbilityClass) {
        dzAbilityClass.getSqlMap().put("dsf", dataScopeFilter(dzAbilityClass.getCurrentUser(), "o", "u8"));
        Page<DzCategory> Ability = super.findPage(page, dzAbilityClass);
        return Ability;
    }

    public List<DzCategory> Abilitylist(String parentIds) {
        List<DzCategory>  Abilitylists = dzCategoryDao.selectByList(parentIds);
        return Abilitylists;
    }

    @Override
    public DzCategory get(String id) {
        DzCategory dzCategory = super.dao.get(id);
        if(dzCategory.getParentId() != null &&
                StringUtils.isNotBlank(dzCategory.getParentId())){
            dzCategory.setParent(super.dao.get(dzCategory.getParentId()));
        }
        return dzCategory;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzCategory dzCategory) {
        if(StringUtils.isNotBlank(dzCategory.getParent().getId())){
            dzCategory.setParentId(dzCategory.getParent().getId());
        }
        super.save(dzCategory);
    }
}
