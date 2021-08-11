package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

/**
 * 试验任务执行.
 * User: wuliepeng
 * Date: 2017-11-10
 * Time: 下午4:17
 */
public class TestTaskExecution extends DataEntity<TestTaskExecution> {
    private String businessKey; //业务键
    private String parentId;    //父级
    private String superExec;   //主干
    private String planDetailId;	//计划ID
    private Date startTime;		// 实际开始时间
    private Double duration;		// 工作时长
    private Date endTime;		// 结束时间
    private String name;    //名称
    private Integer status; //状态

    public TestTaskExecution() {
        super();
    }

    public TestTaskExecution(String id){
        super(id);
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSuperExec() {
        return superExec;
    }

    public void setSuperExec(String superExec) {
        this.superExec = superExec;
    }

    public String getPlanDetailId() {
        return planDetailId;
    }

    public void setPlanDetailId(String planDetailId) {
        this.planDetailId = planDetailId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}
