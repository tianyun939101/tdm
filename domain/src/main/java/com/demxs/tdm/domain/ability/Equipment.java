package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

/**
 * @author: zwm
 * @Date: 2020/10/29 13:25
 * @Description: 设备信息维护
 */
public class Equipment extends DataEntity<Equipment> {

    private static final long serialVersionUID = 1774L;
    //设备名称
    private String equipmentName;
    //规格型号
    private String modelNorm;
    //测量范围或主要参数
    private String measureScope;

    //测量范围或主要参数
    private String code;
    //备注
    private String remark;

    private String labId;
    private LabInfo labInfo;

    private String name;//过滤条件


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Equipment() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getModelNorm() {
        return modelNorm;
    }

    public void setModelNorm(String modelNorm) {
        this.modelNorm = modelNorm;
    }

    public String getMeasureScope() {
        return measureScope;
    }

    public void setMeasureScope(String measureScope) {
        this.measureScope = measureScope;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }
}
