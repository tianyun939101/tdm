package com.demxs.tdm.service.lab;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.lab.LabEquipmentBindDao;
import com.demxs.tdm.domain.lab.LabEquipmentBind;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/6/10 11:23
 * @Description:
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabEquipmentBindService extends CrudService<LabEquipmentBindDao, LabEquipmentBind> {

    public LabEquipmentBind getByLabIdAndEquipId(LabEquipmentBind labEquipmentBind){
        return this.dao.getByLabIdAndEquipId(labEquipmentBind);
    }
}
