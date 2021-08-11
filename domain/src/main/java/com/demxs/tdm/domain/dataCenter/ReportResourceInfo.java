package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 数据中心数据实体类
 */
public class ReportResourceInfo extends DataEntity<ReportResourceInfo>{

    private static final long serialVersionUID = 1L;

    private String labName;//试验室名称

    private String testUserSum;//试验人员数量

    private String testEquipmentSum;//试验设备信息

    private String knowlegeSum;//标准体系数量

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getTestUserSum() {
        return testUserSum;
    }

    public void setTestUserSum(String testUserSum) {
        this.testUserSum = testUserSum;
    }

    public String getTestEquipmentSum() {
        return testEquipmentSum;
    }

    public void setTestEquipmentSum(String testEquipmentSum) {
        this.testEquipmentSum = testEquipmentSum;
    }

    public String getKnowlegeSum() {
        return knowlegeSum;
    }

    public void setKnowlegeSum(String knowlegeSum) {
        this.knowlegeSum = knowlegeSum;
    }
}