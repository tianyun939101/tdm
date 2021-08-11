package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.ZyLabPersonDao;
import com.demxs.tdm.dao.dataCenter.ZyLabTionDao;
import com.demxs.tdm.domain.dataCenter.ZyLabPerson;
import com.demxs.tdm.domain.dataCenter.ZyLabTion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyLabTionService extends  CrudService<ZyLabTionDao, ZyLabTion> {


    public Page<ZyLabTion> list(Page<ZyLabTion> page, ZyLabTion entity) {
        Page<ZyLabTion> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyLabTion> findPage(Page<ZyLabTion> page, ZyLabTion entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyLabTion get(String id) {
        ZyLabTion equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyLabTion entity) {
        super.save(entity);

    }


}
