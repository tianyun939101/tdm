package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.DataFileDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataFileService extends  CrudService<DataFileDao, DataFile> {

    @Autowired
    DataFileDao dataFileDao;



    public Page<DataFile> list(Page<DataFile> page, DataFile entity) {
        Page<DataFile> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<DataFile> findPage(Page<DataFile> page, DataFile entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public DataFile get(String id) {
        DataFile equipment = super.dao.get(id);
        return equipment;
    }


    public void save(DataFile entity) {
        super.save(entity);

    }


}
