package com.demxs.tdm.domain.business.vo;

import java.util.Date;

/**
 * 试验任务查询查数
 * User: wuliepeng
 * Date: 2017-12-04
 * Time: 下午4:25
 */
public class TaskParam {
    /**
     * 试验项目ID
     */
    private String itemId;
    /**
     * 试验负责人
     */
    private String testCharge;
    /**
     * 申请单编号
     */
    private String entrustCode;
    /**
     * 任务所属人,试验员
     */
    private String taskOwner;
    /**
     * 任务编号
     */
    private String taskCode;
    /**
     * 任务状态
     */
    private String status;
    /**
     * 计划开始时间
     */
    private Date planStartDate;
    /**
     * 计划结束时间
     */
    private Date planEndDate;


    public String getTestCharge() {
        return testCharge;
    }

    public void setTestCharge(String testCharge) {
        this.testCharge = testCharge;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
