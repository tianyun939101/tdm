package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.ZyInterfaceDao;
import com.demxs.tdm.dao.dataCenter.ZyLabPersonDao;
import com.demxs.tdm.domain.dataCenter.ZyInterface;
import com.demxs.tdm.domain.dataCenter.ZyLabPerson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyLabPersonService extends  CrudService<ZyLabPersonDao, ZyLabPerson> {


    public Page<ZyLabPerson> list(Page<ZyLabPerson> page, ZyLabPerson entity) {
        Page<ZyLabPerson> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyLabPerson> findPage(Page<ZyLabPerson> page, ZyLabPerson entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyLabPerson get(String id) {
        ZyLabPerson equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyLabPerson entity) {
        super.save(entity);

    }


}
