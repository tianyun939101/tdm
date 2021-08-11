package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:25
 * @Description:
 */
public class EquipmentFile extends DataEntity<EquipmentFile> {
    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private String equipmentId;
    /**
     * 文件编号
     */
    private String fileCode;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     *  文件类型
     */
    private String fileType;
    /**
     * 文件版本
     */
    private String fileVersion = "1";
    /**
     * 文件地址
     */
    private String fileAddress;

    private String  currentVersion;


    private String  parentId;


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
