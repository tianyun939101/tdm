package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author: Jason
 * @Date: 2020/5/28 14:25
 * @Description:
 */
public class EquipmentUseRecord extends DataEntity<EquipmentUseRecord> {
    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private String equId;
    /**
     * 申请单id
     */
    private String entrustId;
    /**
     * 申请单类型
     */
    private String entrustType;
    /**
     *  任务开始时间
     */
    private Date startDate;
    /**
     * 任务完成时间
     */
    private Date completeDate;
    /**
     * 构型id
     */
    private String configId;
    /**
     * 构型版本id
     */
    private String cvId;
    /**
     * 试验负责人
     */
    private String testManager;
    /**
     * 其他试验人员
     */
    private String otherUser;
    /**
     * 设备类型：0设备，1设备台套
     */
    private String equType;
    /**
     * 设备
     */
    public final static String EQUIPMENT = "0";
    /**
     * 设备台套
     */
    public final static String SET = "1";

    public EquipmentUseRecord() {
    }

    public EquipmentUseRecord(String id){
        super(id);
    }

    public String getEquId() {
        return equId;
    }

    public EquipmentUseRecord setEquId(String equId) {
        this.equId = equId;
        return this;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public EquipmentUseRecord setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public EquipmentUseRecord setEntrustType(String entrustType) {
        this.entrustType = entrustType;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public EquipmentUseRecord setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public EquipmentUseRecord setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
        return this;
    }

    public String getConfigId() {
        return configId;
    }

    public EquipmentUseRecord setConfigId(String configId) {
        this.configId = configId;
        return this;
    }

    public String getCvId() {
        return cvId;
    }

    public EquipmentUseRecord setCvId(String cvId) {
        this.cvId = cvId;
        return this;
    }

    public String getTestManager() {
        return testManager;
    }

    public EquipmentUseRecord setTestManager(String testManager) {
        this.testManager = testManager;
        return this;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public EquipmentUseRecord setOtherUser(String otherUser) {
        this.otherUser = otherUser;
        return this;
    }

    public String getEquType() {
        return equType;
    }

    public EquipmentUseRecord setEquType(String equType) {
        this.equType = equType;
        return this;
    }
}
