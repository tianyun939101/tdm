package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.business.dz.DzPorsvisileDao;
import com.demxs.tdm.dao.business.dz.DzProjectDao;
import com.demxs.tdm.domain.business.dz.DzPorsvisile;
import com.demxs.tdm.domain.business.dz.DzProject;
import com.demxs.tdm.service.business.dz.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl  extends CrudService<DzProjectDao, DzProject> implements ProjectService {
    @Autowired
    DzProjectDao dzProjectDao;
    @Autowired
    DzPorsvisileDao dzPorsvisileDao;
    @Override
    public Page<DzProject> findPage(Page<DzProject> page, DzProject dzSystem) {
        dzSystem.getSqlMap().put("dsf", dataScopeFilter(dzSystem.getCurrentUser(), "o", "u8"));
        Page<DzProject> Ability = super.findPage(page, dzSystem);
        return Ability;
    }

    @Override
    public DzProject get(String id) {
        DzProject dzSystem = super.dao.get(id);
        return dzSystem;
    }

    @Override
    public DzProject getAuditInfolist(String id) {
        DzProject selectlist  = dzProjectDao.selectAuditInfolist(id);
        return selectlist;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzProject dzSystem) {
        super.save(dzSystem);
    }
    @Override
    public void delectsvisile(String id) {
        dzPorsvisileDao.delect(id);
    }
    @Override
    public void insertsvisile(DzPorsvisile dzPorsvisile) {
        dzPorsvisileDao.insert(dzPorsvisile);
    }
}
