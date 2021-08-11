package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: zwm
 * @Date: 2020/10/29 13:25
 * @Description: 设备信息与试验关系
 */
public class EquipmentTest extends DataEntity<EquipmentTest> {

    private static final long serialVersionUID = 1774L;
    //设备ID
    private String equipmentId;
    //试验ID
    private String testId;
    //数量及编号
    private String quantityNum;
    //有资质设备操作的人员
    private String staffNum;

    private String  equipmentName;

    private String  modelNorm;


    private String  measureScope;

    private String  code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EquipmentTest() {
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

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getQuantityNum() {
        return quantityNum;
    }

    public void setQuantityNum(String quantityNum) {
        this.quantityNum = quantityNum;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }
}
