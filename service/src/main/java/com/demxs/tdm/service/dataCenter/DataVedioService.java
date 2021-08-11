package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.DataVedioDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataVedio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataVedioService extends  CrudService<DataVedioDao, DataVedio> {

    @Autowired
    DataVedioDao dataVedioDao;



    public Page<DataVedio> list(Page<DataVedio> page, DataVedio entity) {
        Page<DataVedio> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<DataVedio> findPage(Page<DataVedio> page, DataVedio entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public DataVedio get(String id) {
        DataVedio dataVedio = super.dao.get(id);
        return dataVedio;
    }


    public void save(DataVedio entity) {
        super.save(entity);
    }

    public  List<DataVedio> findDataList(DataVedio  dataVedio){
        return dataVedioDao.findDataList(dataVedio);
    }

}
