package com.demxs.tdm.domain.quartz;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

public class QuartzJob extends DataEntity<QuartzJob> {


    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String classType;
    private String cron;
    private Date executeDate;

    private String json;

    private String execuType;//执行类型


    public QuartzJob(){

    }
    public QuartzJob(String id){

        super.id = id;
    }
    public QuartzJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String classType, String cron, Date executeDate, String json, String execuType) {
        this.jobName = jobName;
        this.jobGroupName = jobGroupName;
        this.triggerName = triggerName;
        this.triggerGroupName = triggerGroupName;
        this.classType = classType;
        this.cron = cron;
        this.executeDate = executeDate;
        this.json = json;
        this.execuType = execuType;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Date getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(Date executeDate) {
        this.executeDate = executeDate;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getExecuType() {
        return execuType;
    }

    public void setExecuType(String execuType) {
        this.execuType = execuType;
    }
}
