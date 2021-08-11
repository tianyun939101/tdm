package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.ZyFileCheckDao;
import com.demxs.tdm.dao.dataCenter.ZyFileCheckRelationDao;
import com.demxs.tdm.domain.dataCenter.ZyFileCheck;
import com.demxs.tdm.domain.dataCenter.ZyFileCheckRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyFileCheckRelationService extends  CrudService<ZyFileCheckRelationDao, ZyFileCheckRelation> {

    @Autowired
    ZyFileCheckRelationDao zyFileCheckRelationDao;


    public Page<ZyFileCheckRelation> list(Page<ZyFileCheckRelation> page, ZyFileCheckRelation entity) {
        Page<ZyFileCheckRelation> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyFileCheckRelation> findPage(Page<ZyFileCheckRelation> page, ZyFileCheckRelation entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyFileCheckRelation get(String id) {
        ZyFileCheckRelation equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyFileCheckRelation entity) {
        super.save(entity);

    }

}
