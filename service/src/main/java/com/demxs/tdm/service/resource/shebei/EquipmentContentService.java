package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.resource.shebei.EquipmentContentDao;
import com.demxs.tdm.dao.resource.shebei.EquipmentFileDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentContent;
import com.demxs.tdm.domain.resource.shebei.EquipmentFile;
import com.demxs.tdm.domain.resource.shebei.EquipmentInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:51
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class EquipmentContentService extends CrudService<EquipmentContentDao, EquipmentContent> {

    @Autowired
    private EquipmentContentDao  equipmentContentDao;

    /**
     * 删除设备维护信息
     * @param  equipmentContent
     * @return
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public  void  deleteInfo(EquipmentContent equipmentContent){

        equipmentContentDao.deleteInfo(equipmentContent);
    }


    /**
     * 维护信息---通过维护信息defendId获取维护明细信息
     * @param  defendIdId
     * @return  EquipmentContent
     */
    public List<EquipmentContent>  getEqContentByEpId(String  defendIdId){
        EquipmentContent  ei = new EquipmentContent();
        ei.setDefendId(defendIdId);
        ei.setDelFlag("0");
        List<EquipmentContent> equipmentContentList=equipmentContentDao.findAll(ei);
        return   equipmentContentList;
    }

    //设备维护状态更新
    public void updateStatus(EquipmentContent equipmentContent){
        equipmentContent = this.get(equipmentContent.getId());
        equipmentContent.setStatus("02");
        this.save(equipmentContent);
    }


    /**
     * 一键维护
     * @param
     * @return
     */
    public void  maintain( String  defendId, String time){
       equipmentContentDao.maintain(defendId,time);
    }
}
