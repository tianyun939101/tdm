package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.resource.shebei.EquipmentContentDao;
import com.demxs.tdm.dao.resource.shebei.EquipmentInfoDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentContent;
import com.demxs.tdm.domain.resource.shebei.EquipmentInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class EquipmentInfoService extends CrudService<EquipmentInfoDao, EquipmentInfo> {

    @Autowired
    private EquipmentInfoDao  equipmentInfoDao;
    @Autowired
    private EquipmentContentService  equipmentContentService;
    @Autowired
    private EquipmentContentDao equipmentContentDao;

    /**
     * 设备维护---通过ID获取设备信息和内容明显
     * @param  id
     * @return  EquipmentInfo
     */
    public EquipmentInfo  getEquipment(String  id){
        EquipmentInfo equipmentInfo=equipmentInfoDao.get(id);
        EquipmentContent  ec=new EquipmentContent();
        ec.setDefendId(id);
        List<EquipmentContent>  list= equipmentContentDao.findAll(ec);
        equipmentInfo.setEquipmentContentList(list);
        return   equipmentInfo;
    }


    /**
     * 设备维护---通过设备equipmentId获取维护信息
     * @param  ei
     * @return  EquipmentInfo
     */
    public List<EquipmentInfo>  getEquipmentByEpId(EquipmentInfo  ei){

        List<EquipmentInfo> equipmentInfo = equipmentInfoDao.findAll(ei);
        return   equipmentInfo;
    }
    /**
     * 设备维护---通过设备维护ID删除设备信息和内容明细
     * @param  equipment
     * @return
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public  void  deleteInfo(EquipmentInfo equipment){
        EquipmentInfo equipmentInfo=equipmentInfoDao.get(equipment.getId());
        if(null !=equipmentInfo){
            EquipmentContent  ec=new EquipmentContent();
            ec.setDefendId(equipment.getId());
            List<EquipmentContent>  list=equipmentContentDao.findList(ec);
            if(!CollectionUtils.isEmpty(list)){
                for(EquipmentContent e:list){
                    equipmentContentDao.deleteInfo(e);
                }
            }
            equipmentInfoDao.deleteInfo(equipmentInfo);
        }
    }

    /**
     * @Describe:保存维护计划
     * @Author:WuHui
     * @Date:14:58 2020/11/13
     * @param equipment
     * @return:void
    */
    public void savePlan(EquipmentInfo equipment){
        //校验维护计划日期是否存在
        List<EquipmentInfo> list = this.dao.findAll(equipment);
        if(StringUtils.isBlank(equipment.getId()) && list.size() > 0){
           throw new ServiceException("填写年度维护计划已存在！");
        }
        //存储维护计划
        this.save(equipment);
        //存储维护内容
        equipmentContentDao.deleteByInfoId(equipment.getId());
        for(EquipmentContent content :equipment.getEquipmentContentList()){
            content.setDefendId(equipment.getId());
            equipmentContentService.save(content);
        }
    }

}
