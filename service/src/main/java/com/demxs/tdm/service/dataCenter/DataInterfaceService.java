package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.DataInterfaceDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataInterfaceService extends  CrudService<DataInterfaceDao, DataInterface> {

    public Page<DataInterface> list(Page<DataInterface> page, DataInterface entity) {
        Page<DataInterface> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<DataInterface> findPage(Page<DataInterface> page, DataInterface entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public DataInterface get(String id) {
        DataInterface equipment = super.dao.get(id);
        return equipment;
    }


    public void save(DataInterface entity) {
        super.save(entity);

    }


}
