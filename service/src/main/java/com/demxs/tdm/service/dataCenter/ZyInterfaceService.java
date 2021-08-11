package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.ZyInterfaceDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.ZyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyInterfaceService extends  CrudService<ZyInterfaceDao, ZyInterface> {


    public Page<ZyInterface> list(Page<ZyInterface> page, ZyInterface entity) {
        Page<ZyInterface> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyInterface> findPage(Page<ZyInterface> page, ZyInterface entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyInterface get(String id) {
        ZyInterface equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyInterface entity) {
        super.save(entity);

    }


}
