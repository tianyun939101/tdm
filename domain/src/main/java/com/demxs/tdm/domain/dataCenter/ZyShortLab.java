package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ZyShortLab extends DataEntity<ZyShortLab> {

    private static final long serialVersionUID = 1L;

    private String nature;

    private String labCode;

    private String reportCode;

    private String remarks;


    private String  status;


    private String start;

    private String end;


    private String time;

    private User user;

    private String parentId;

    private String isImport;
    //子节点
    private List<ZyShortLab> children;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectDate;

    public String getIsImport() {
        return isImport;
    }

    public void setIsImport(String isImport) {
        this.isImport = isImport;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<ZyShortLab> getChildren() {
        return children;
    }

    public void setChildren(List<ZyShortLab> children) {
        this.children = children;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getLabCode() {
        return labCode;
    }

    public void setLabCode(String labCode) {
        this.labCode = labCode;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
