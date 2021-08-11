package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.DataSystemDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataSystemService extends  CrudService<DataSystemDao, DataSystem> {

    @Autowired
    DataSystemDao dataSystemDao;



    public List<DataSystem> list(DataSystem entity) {
        List<DataSystem> Page = dataSystemDao.findDataList(entity);
        return Page;
    }


    public DataSystem getDataSystem(DataSystem entity) {
        DataSystem dataSystem= dataSystemDao.getDataSystem(entity);
        return dataSystem;
    }

    public DataSystem get(String id) {
        DataSystem equipment = super.dao.get(id);
        return equipment;
    }


    public void save(DataSystem entity) {
        super.save(entity);

    }


}
