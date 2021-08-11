package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.TestEquipmentDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.TestEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestEquipmentService extends  CrudService<TestEquipmentDao, TestEquipment> {

    @Autowired
    TestEquipmentDao testEquipmentDao;



    public Page<TestEquipment> list(Page<TestEquipment> page, TestEquipment entity) {
        Page<TestEquipment> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<TestEquipment> findPage(Page<TestEquipment> page, TestEquipment entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public TestEquipment get(String id) {
        TestEquipment equipment = super.dao.get(id);
        return equipment;
    }


    public void save(TestEquipment entity) {
        super.save(entity);

    }


}
