package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.EquipmentDao;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.domain.ability.Equipment;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataBackUpService extends  CrudService<DataBackUpDao, DataBackUp> {

    @Autowired
    DataBackUpDao dataBackUpDao;



    public Page<DataBackUp> list(Page<DataBackUp> page, DataBackUp entity) {
        Page<DataBackUp> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<DataBackUp> findPage(Page<DataBackUp> page, DataBackUp entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public DataBackUp get(String id) {
        DataBackUp equipment = super.dao.get(id);
        return equipment;
    }


    public void save(DataBackUp entity) {
        entity.setAttribute1("0");
        super.save(entity);

    }


}
