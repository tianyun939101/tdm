package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.EquipmentSpecialDao;
import com.demxs.tdm.domain.dataCenter.EquipmentSpecial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EquipmentSpecialService extends  CrudService<EquipmentSpecialDao, EquipmentSpecial> {

    @Autowired
    EquipmentSpecialDao equipmentSpecialDao;



    public Page<EquipmentSpecial> list(Page<EquipmentSpecial> page, EquipmentSpecial entity) {
        Page<EquipmentSpecial> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<EquipmentSpecial> findPage(Page<EquipmentSpecial> page, EquipmentSpecial entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public EquipmentSpecial get(String id) {
        EquipmentSpecial equipment = super.dao.get(id);
        return equipment;
    }


    public void save(EquipmentSpecial entity) {
        super.save(entity);

    }


}
