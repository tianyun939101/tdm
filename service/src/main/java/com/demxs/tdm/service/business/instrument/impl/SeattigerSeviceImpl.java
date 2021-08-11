package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.instrument.DzStorageLocationDao;
import com.demxs.tdm.domain.business.instrument.DzStorageLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SeattigerSeviceImpl extends TreeService<DzStorageLocationDao, DzStorageLocation> {
    @Autowired
    DzStorageLocationDao dzStorageLocationDao;
    @Override
    public List<DzStorageLocation> findList(DzStorageLocation entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public Page<DzStorageLocation> findPage(Page<DzStorageLocation> page, DzStorageLocation dzStorageLocation) {
        dzStorageLocation.getSqlMap().put("dsf", dataScopeFilter(dzStorageLocation.getCurrentUser(), "o", "u8"));
        Page<DzStorageLocation> Ability = super.findPage(page, dzStorageLocation);
        return Ability;
    }

    public List<DzStorageLocation> Abilitylist(String parentIds) {
        List<DzStorageLocation>  Abilitylists = dzStorageLocationDao.selectByList(parentIds);
        return Abilitylists;
    }

    @Override
    public DzStorageLocation get(String id) {
        DzStorageLocation dzCategory = super.dao.get(id);
        if(dzCategory.getParentId() != null &&
                StringUtils.isNotBlank(dzCategory.getParentId())){
            dzCategory.setParent(super.dao.get(dzCategory.getParentId()));
        }
        return dzCategory;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzStorageLocation dzStorageLocation) {
        if(StringUtils.isNotBlank(dzStorageLocation.getParent().getId())){
            dzStorageLocation.setParentId(dzStorageLocation.getParent().getId());
        }
        super.save(dzStorageLocation);
    }
}
