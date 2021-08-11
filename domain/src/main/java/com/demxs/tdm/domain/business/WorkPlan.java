package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: 佚名
 * @Date: 2020/11/3 9:21
 * @Description: 任务单-任务关系表
 */
public class WorkPlan extends DataEntity<WorkPlan> {

    private static final long serialVersionUID = 1776L;
    //任务单表ID
    private String workOrderID;
    //任务信息ID(标识一张excel表)
    private String workID;
    //上传的文件名
    private String workName;


    //预留字段
    private String attribute2;

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(String workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getWorkID() {
        return workID;
    }

    public void setWorkID(String workID) {
        this.workID = workID;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}
