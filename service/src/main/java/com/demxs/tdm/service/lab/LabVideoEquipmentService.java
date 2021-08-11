package com.demxs.tdm.service.lab;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.lab.LabVideoEquipmentDao;
import com.demxs.tdm.domain.lab.LabVideoEquipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/11 17:00
 * @Description:
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabVideoEquipmentService extends CrudService<LabVideoEquipmentDao, LabVideoEquipment> {

    public List<LabVideoEquipment> findByLabId(String labId){
        return this.dao.findByLabId(labId);
    }

    public LabVideoEquipment getByLabId(LabVideoEquipment video) {
        return this.dao.getByLabId(video);
    }
}
