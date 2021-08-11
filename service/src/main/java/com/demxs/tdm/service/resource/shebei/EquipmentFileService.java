package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.resource.shebei.EquipmentFileDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentFile;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
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
public class EquipmentFileService extends CrudService<EquipmentFileDao, EquipmentFile> {


    @Autowired
    private EquipmentFileDao  equipmentFileDao;

    @Autowired
    private AttachmentInfoService attachmentInfoService;

    /**
     * 删除设备维护信息
     * @param  equipmentFile
     * @return
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public  void  deleteInfo(EquipmentFile equipmentFile){
        equipmentFileDao.delete(equipmentFile);
    }

    /**
     * 设备维护---通过设备equipmentId获取设备操作文件信息
     * @param  equipmentId
     * @return  EquipmentFile
     */
    public List<EquipmentFile> getEquipmentFileByEpId(String  equipmentId){
        EquipmentFile  ei=new EquipmentFile();
        ei.setEquipmentId(equipmentId);
        ei.setDelFlag("0");
        List<EquipmentFile> equipmentFileList = equipmentFileDao.findAll(ei);
        return   equipmentFileList;
    }

    public void saveEquipmentDetail(EquipmentFile equipmentFile){
        String fileName =attachmentInfoService.get(equipmentFile.getFileCode()).getName();
        String filePath=attachmentInfoService.get(equipmentFile.getFileCode()).getPath();
        User user = UserUtils.getUser();
        equipmentFile.setFileName(fileName);
        equipmentFile.setFileAddress(filePath);
        equipmentFile.setFileVersion("1");
        super.save(equipmentFile);

    }

    @Override
    public void save(EquipmentFile entity) {
        if (entity.getIsNewRecord()){
            entity.preInsert();
            if(StringUtils.isEmpty(entity.getParentId())){
                entity.setParentId(entity.getId());
            }
            entity.setCurrentVersion("1");
            dao.insert(entity);
        }else{
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * @Describe:版本升级
     * @Author:WuHui
     * @Date:15:01 2020/11/9
     * @param equipmentFile
     * @return:com.demxs.tdm.domain.resource.shebei.EquipmentFile
    */
    @Transactional
    public EquipmentFile versionUpdate(EquipmentFile equipmentFile) {
        //更新旧版本
        EquipmentFile old = this.get(equipmentFile);
        old.setCurrentVersion("0");
        this.save(old);
        //新增新版本
        Integer version = Integer.valueOf(old.getFileVersion()) + 1;
        equipmentFile.setFileVersion(version.toString());
        equipmentFile.setId("");
        this.save(equipmentFile);
        return this.get(equipmentFile.getId());
    }

    /**
     * @Describe:根据父版本编号查询历史版本
     * @Author:WuHui
     * @Date:14:46 2020/11/24
     * @param equipmentFile
     * @return:java.util.List<com.demxs.tdm.domain.resource.shebei.EquipmentFile>
    */
    public List<EquipmentFile> getEquipmentFileByParentId(EquipmentFile equipmentFile){
        List<EquipmentFile> equipmentFileList = equipmentFileDao.findEquipmentFileByParentId(equipmentFile);
        return   equipmentFileList;
    }
}
