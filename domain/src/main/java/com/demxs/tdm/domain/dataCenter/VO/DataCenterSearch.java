package com.demxs.tdm.domain.dataCenter.VO;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

/**
 * 数据中心查询实体类
 */
public class DataCenterSearch {

    private static final long serialVersionUID = 1L;

    private String org;

    private String configId;

    private String taskNo;

    private String testoutlineCode;

    private Date createDate;

    private Date createStartDate;

    private Date createEndDate;
    /**
     * 数据视图类型（0：ata章节树 1：试验室列表）
     */
    private String viewType;

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTestoutlineCode() {
        return testoutlineCode;
    }

    public void setTestoutlineCode(String testoutlineCode) {
        this.testoutlineCode = testoutlineCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(Date createStartDate) {
        this.createStartDate = createStartDate;
    }

    public Date getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(Date createEndDate) {
        this.createEndDate = createEndDate;
    }

    public String getViewType() {
        return viewType;
    }

    public DataCenterSearch setViewType(String viewType) {
        this.viewType = viewType;
        return this;
    }
}
