package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: lzw
 * @Date: 2020/10/29 19:25
 * @Description: 任务单
 */
public class WorkOrder extends DataEntity<WorkOrder> {
    private static final long serialVersionUID = 1776L;
    //任务单名称
    private String workOrderName;
    //维护人
    private String accendant;
    //发布部门
    private String publishDepartment;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
    //创建人
    private String establishName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date establishTime ;


    //导入的excel
    private String workId;
    //状态
    //已完成
    private String finished;
    //执行中
    private String inExecution;
    //已响应
    private String response;
    //总数
    private String rante;



    //任务单跳转
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRante() {
        return rante;
    }

    public void setRante(String rante) {
        this.rante = rante;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getInExecution() {
        return inExecution;
    }

    public void setInExecution(String inExecution) {
        this.inExecution = inExecution;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getAccendant() {
        return accendant;
    }

    public void setAccendant(String accendant) {
        this.accendant = accendant;
    }

    public String getPublishDepartment() {
        return publishDepartment;
    }

    public void setPublishDepartment(String publishDepartment) {
        this.publishDepartment = publishDepartment;
    }



    public String getEstablishName() {
        return establishName;
    }

    public void setEstablishName(String establishName) {
        this.establishName = establishName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

}