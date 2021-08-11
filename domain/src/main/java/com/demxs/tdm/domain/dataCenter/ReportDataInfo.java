package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 数据中心数据实体类
 */
public class ReportDataInfo extends DataEntity<ReportDataInfo>{

    private static final long serialVersionUID = 1L;

    private String Level;//试验信息ID

    private String labName;//试验数据类型

    private String status;//试验数据

    private String centerName;//单位

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}