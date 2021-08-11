package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.RoleDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.dao.ability.EquipmentDao;
import com.demxs.tdm.dao.ability.EquipmentTestDao;
import com.demxs.tdm.dao.dataCenter.DcServerProvideDao;
import com.demxs.tdm.domain.ability.Equipment;
import com.demxs.tdm.domain.ability.EquipmentTest;
import com.demxs.tdm.domain.dataCenter.DcServerProvide;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EquipmentService extends  CrudService<EquipmentDao, Equipment> {

    @Autowired
    EquipmentDao equipmentDao;



    public Page<Equipment> list(Page<Equipment> page, Equipment equipment) {
        Page<Equipment> Page = super.findPage(page, equipment);
        return Page;
    }

    public Page<Equipment> findPage(Page<Equipment> page, Equipment entity) {
        entity.setPage(page);
        page.setList(dao.findAllList(entity));
        return page;
    }


    public Equipment get(String id) {
        Equipment equipment = super.dao.get(id);
        return equipment;
    }


    public void save(Equipment equipment) {

        super.save(equipment);

    }


}
