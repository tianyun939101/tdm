package com.demxs.tdm.domain.business.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务执行实体
 * User: wuliepeng
 * Date: 2017-12-27
 * Time: 上午10:47
 * @author wuliepeng
 */
public class TaskExecuteVO implements Serializable {
    /**
     * ID
     */
    private String id;
    /**
     * 父ID
     */
    private String pid;
    /**
     * 试验组ID
     */
    private String testGroupId;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 图片ID
     */
    private String imgIds;
    /**
     * 任务类型
     */
    private Integer type;
    /**
     * 试验能力ID
     */
    private String abilityId;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务状态
     */
    private Integer status;
    /**
     * 任务原始记录模版ID
     */
    private String templateId;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 任务原始记录模版名称

     */
    private String templateName;
    /**
     * 任务原始记录
     */
    private String originRecordId;
    /**
     * 样品ID
     */
    private String sampleId;
    /**
     * 任务计划开始时间
     */
    private Date planStartDate;
    /**
     * 任务计划结束时间
     */
    private Date planEndDate;
    /**
     * 任务开始时间
     */
    private Date startDate;
    /**
     * 任务结束时间
     */
    private Date endDate;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 报告已选标签
     */
    private Map baogaoYxbq;

    /**
     *  form 入库数据
     */
    private Map formrkdata;

    public Map getBaogaoYxbq() {
        return baogaoYxbq;
    }

    public void setBaogaoYxbq(Map baogaoYxbq) {
        this.baogaoYxbq = baogaoYxbq;
    }

    public Map getFormrkdata() {
        return formrkdata;
    }

    public void setFormrkdata(Map formrkdata) {
        this.formrkdata = formrkdata;
    }

    public List<Map> getListrkdata() {
        return listrkdata;
    }

    public void setListrkdata(List<Map> listrkdata) {
        this.listrkdata = listrkdata;
    }

    /**
     *  list 入库数据
     */

    private List<Map> listrkdata;

    /**
     *
     */
    private List<TaskExecuteVO> subTasks = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOriginRecordId() {
        return originRecordId;
    }

    public void setOriginRecordId(String originRecordId) {
        this.originRecordId = originRecordId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<TaskExecuteVO> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<TaskExecuteVO> subTasks) {
        this.subTasks = subTasks;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getTestGroupId() {
        return testGroupId;
    }

    public void setTestGroupId(String testGroupId) {
        this.testGroupId = testGroupId;
    }

    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("pid", pid)
                .append("taskId", taskId)
                .append("type", type)
                .append("name", name)
                .append("status", status)
                .append("originRecordId", originRecordId)
                .append("planStartDate", planStartDate)
                .append("planEndDate", planEndDate)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .toString();
    }
}
