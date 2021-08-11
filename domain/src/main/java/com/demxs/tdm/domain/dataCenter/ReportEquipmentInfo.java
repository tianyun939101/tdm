package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 数据中心数据实体类
 */
public class ReportEquipmentInfo extends DataEntity<ReportEquipmentInfo>{

    private static final long serialVersionUID = 1L;

    private String equipmentSum;//设备数量

    private String newEquipment;//新增设备

    private String inUseEquipment;//使用设备

    private String freeEquipment;//空闲设备


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEquipmentSum() {
        return equipmentSum;
    }

    public void setEquipmentSum(String equipmentSum) {
        this.equipmentSum = equipmentSum;
    }

    public String getNewEquipment() {
        return newEquipment;
    }

    public void setNewEquipment(String newEquipment) {
        this.newEquipment = newEquipment;
    }

    public String getInUseEquipment() {
        return inUseEquipment;
    }

    public void setInUseEquipment(String inUseEquipment) {
        this.inUseEquipment = inUseEquipment;
    }

    public String getFreeEquipment() {
        return freeEquipment;
    }

    public void setFreeEquipment(String freeEquipment) {
        this.freeEquipment = freeEquipment;
    }
}