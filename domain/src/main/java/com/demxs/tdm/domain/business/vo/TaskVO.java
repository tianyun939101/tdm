package com.demxs.tdm.domain.business.vo;

import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.domain.business.EntrustSampleGroupItem;
import com.demxs.tdm.domain.business.TestTask;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务列表VO
 * User: wuliepeng
 * Date: 2017-11-30
 * Time: 下午4:52
 * @author wuliepeng
 */
public class TaskVO implements Serializable {
    /**
     * 任务ID
     */
    private String id;
    /**
     * 申请单ID
     */
    private String entrustId;
    /**
     * 试验室ID
     */
    private String labId;
    /**
     * 业务主键,申请单编号
     */
    private String businessKey;
    /**
     * 序列名称
     */
    private String sequenceName;
    /**
     * 试验项目ID
     */
    private String itemId;
    /**
     * 试验项目名称
     */
    private String itemName;
    /**
     * 样品数量
     */
    private Integer sampleAmount;
    /**
     * 样品
     */
    private List<EntrustSampleGroupItem> sampleList;
    /**
     * 任务编号
     */
    private String taskCode;
    /**
     * 任务状态
     */
    private String status;
    /**
     * 任务执行人名称
     */
    private String ownerName;
    /**
     * 计划开始时间
     */
    private Date planStartDate;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 计划结束时间
     */
    private Date planEndDate;

    /**
     * 当前执行的试验项
     */
    private TestTask currentTask;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSampleAmount() {
        return sampleAmount;
    }

    public void setSampleAmount(Integer sampleAmount) {
        this.sampleAmount = sampleAmount;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        if(this.getEndTime() == null && this.getStartTime()!=null){
            return DateUtils.formatDateTime(System.currentTimeMillis() - this.getStartTime().getTime());
        }else if(this.getEndTime() != null && this.getStartTime()!=null){
            return DateUtils.formatDateTime(this.getEndTime().getTime() - this.getStartTime().getTime());
        }
        return "";
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public TestTask getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(TestTask currentTask) {
        this.currentTask = currentTask;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public List<EntrustSampleGroupItem> getSampleList() {
        return sampleList;
    }

    public TaskVO setSampleList(List<EntrustSampleGroupItem> sampleList) {
        this.sampleList = sampleList;
        return this;
    }

    /**
    * @author Jason
    * @date 2020/6/30 10:38
    * @params []
    * 去重、获取样品名称
    * @return java.lang.String
    */
    public String getSamplesName(){
        StringBuilder sb = new StringBuilder();
        if(null != sampleList){
            for (EntrustSampleGroupItem sample : sampleList) {
                if(sb.indexOf(sample.getName()) == -1){
                    sb.append(sample.getName()).append(",");
                }
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("businessKey", businessKey)
                .append("sequenceName", sequenceName)
                .append("itemName", itemName)
                .append("taskCode", taskCode)
                .append("status", status)
                .append("ownerName", ownerName)
                .append("planStartDate", planStartDate)
                .append("startTime", startTime)
                .append("planEndDate", planEndDate)
                .toString();
    }
}
