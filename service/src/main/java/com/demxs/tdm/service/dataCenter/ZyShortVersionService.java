package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.ZyShortLabDao;
import com.demxs.tdm.dao.dataCenter.ZyShortVersionDao;
import com.demxs.tdm.domain.dataCenter.ZyShortLab;
import com.demxs.tdm.domain.dataCenter.ZyShortVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyShortVersionService extends  CrudService<ZyShortVersionDao, ZyShortVersion> {

    @Autowired
    ZyShortVersionDao zyShortVersionDao;



    public Page<ZyShortVersion> list(Page<ZyShortVersion> page, ZyShortVersion entity) {
        Page<ZyShortVersion> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyShortVersion> findPage(Page<ZyShortVersion> page, ZyShortVersion entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyShortVersion get(String id) {
        ZyShortVersion equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyShortVersion entity) {
        super.save(entity);

    }


}
