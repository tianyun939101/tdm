package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.dz.DzSystemClassDao;
import com.demxs.tdm.domain.business.dz.DzSystemClass;
import com.demxs.tdm.service.business.dz.SystemclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SystemclassServiceImpl extends TreeService<DzSystemClassDao, DzSystemClass> implements SystemclassService {
    @Autowired
    DzSystemClassDao dzSystemClassDao;
    @Override
    public List<DzSystemClass> findList(DzSystemClass entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public Page<DzSystemClass> findPage(Page<DzSystemClass> page, DzSystemClass dzAbilityClass) {
        dzAbilityClass.getSqlMap().put("dsf", dataScopeFilter(dzAbilityClass.getCurrentUser(), "o", "u8"));
        Page<DzSystemClass> Ability = super.findPage(page, dzAbilityClass);
        return Ability;
    }

    @Override
    public List<DzSystemClass> Abilitylist(String parentIds) {
        List<DzSystemClass>  Abilitylists = dzSystemClassDao.selectByList(parentIds);
        return Abilitylists;
    }

    @Override
    public DzSystemClass get(String id) {
        DzSystemClass dzAbilityClass = super.dao.get(id);
        if(dzAbilityClass.getParentId() != null &&
                StringUtils.isNotBlank(dzAbilityClass.getParentId())){
            dzAbilityClass.setParent(super.dao.get(dzAbilityClass.getParentId()));
        }
        return dzAbilityClass;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzSystemClass dzProjectclass) {
        if(StringUtils.isNotBlank(dzProjectclass.getParent().getId())){
            dzProjectclass.setParentId(dzProjectclass.getParent().getId());
        }
        super.save(dzProjectclass);
    }
}
