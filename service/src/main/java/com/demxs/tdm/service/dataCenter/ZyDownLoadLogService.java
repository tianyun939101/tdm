package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.ZyIDownLoadLogDao;
import com.demxs.tdm.domain.dataCenter.ZyDownLoadLog;
import com.demxs.tdm.domain.dataCenter.ZyInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyDownLoadLogService extends  CrudService<ZyIDownLoadLogDao, ZyDownLoadLog> {


    public Page<ZyDownLoadLog> list(Page<ZyDownLoadLog> page, ZyDownLoadLog entity) {
        Page<ZyDownLoadLog> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyDownLoadLog> findPage(Page<ZyDownLoadLog> page, ZyDownLoadLog entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
}


    public ZyDownLoadLog get(String id) {
        ZyDownLoadLog equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyDownLoadLog entity) {
        super.save(entity);

    }


}
