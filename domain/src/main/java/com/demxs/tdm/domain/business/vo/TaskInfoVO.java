package com.demxs.tdm.domain.business.vo;

import com.demxs.tdm.domain.ability.TestItemUnit;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.sys.SysDataChangeLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 任务信息VO
 * User: wuliepeng
 * Date: 2017-12-04
 * Time: 下午6:20
 */
public class TaskInfoVO implements Serializable {
    /**
     * 申请信息
     */
    private EntrustInfo entrust;
    /**
     * 任务信息
     */
    private TestTask task;
    /**
     * 当前试验任务
     */
    private TestTask currentTask;
    /**
     * 计划信息
     */
    private TestPlan plan;
    /**
     * 计划详情信息
     */
    private TestPlanDetail planDetail;
    /**
     * 子任务信息
     */
    private List<TestTask> subTask;

    /**
     * 当前任务所关联的试验项
     */
    private TestItemUnit testItemUnit;

    private String imgIds;


    /**
     * 任务审核信息
     */
    private List<AuditInfo> auditInfos = new ArrayList<>();

    /**
     * 调整记录
     */
    private List<SysDataChangeLog> changeLogs = new ArrayList<>();

    public TaskInfoVO() {
    }

    public TaskInfoVO(EntrustInfo entrust, TestTask task, TestTask currentTask, TestPlan plan, TestPlanDetail planDetail, List<TestTask> subTask,TestItemUnit
            testItemUnit) {
        this.entrust = entrust;
        this.task = task;
        this.currentTask = currentTask;
        this.plan = plan;
        this.planDetail = planDetail;
        this.subTask = subTask;
        this.testItemUnit = testItemUnit;
    }

    public EntrustInfo getEntrust() {
        return entrust;
    }

    public void setEntrust(EntrustInfo entrust) {
        this.entrust = entrust;
    }

    public TestTask getTask() {
        return task;
    }

    public void setTask(TestTask task) {
        this.task = task;
    }

    public TestPlan getPlan() {
        return plan;
    }

    public void setPlan(TestPlan plan) {
        this.plan = plan;
    }

    public TestPlanDetail getPlanDetail() {
        return planDetail;
    }

    public void setPlanDetail(TestPlanDetail planDetail) {
        this.planDetail = planDetail;
    }

    public List<TestTask> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<TestTask> subTask) {
        this.subTask = subTask;
    }

    public TestItemUnit getTestItemUnit() {
        return testItemUnit;
    }

    public void setTestItemUnit(TestItemUnit testItemUnit) {
        this.testItemUnit = testItemUnit;
    }

    public TestTask getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(TestTask currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * 获取试验人员
     * @return
     */
    public List<String> getTestUsers(){
        return Arrays.asList(currentTask.getOwnerName().split(","));
    }

    public List<AuditInfo> getAuditInfos() {
        return auditInfos;
    }

    public void setAuditInfos(List<AuditInfo> auditInfos) {
        this.auditInfos = auditInfos;
    }

    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    public List<SysDataChangeLog> getChangeLogs() {
        return changeLogs;
    }

    public void setChangeLogs(List<SysDataChangeLog> changeLogs) {
        this.changeLogs = changeLogs;
    }
}
