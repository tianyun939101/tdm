package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;

import java.io.Serializable;
import java.util.Date;

public class DzDevelopmentPlan extends DataEntity<DzDevelopmentPlan> {
    
    private String id;

    
    private String linkId;

    
    private String name;

    
    private String fileIds;

    
    private String createBy;

    
    private Date createDate;

    
    private String updateBy;

    
    private Date updateDate;

    
    private String examineBy;

    
    private Date examineDate;

    
    private String examineOpinion;

    
    private String examineResult;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    private String serialNumber;

    
    private static final long serialVersionUID = 1L;

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    
    public String getLinkId() {
        return linkId;
    }

    
    public void setLinkId(String linkId) {
        this.linkId = linkId == null ? null : linkId.trim();
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getFileIds() {
        return fileIds;
    }

    
    public void setFileIds(String fileIds) {
        this.fileIds = fileIds == null ? null : fileIds.trim();
    }

    
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    
    public Date getCreateDate() {
        return createDate;
    }

    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    
    public Date getUpdateDate() {
        return updateDate;
    }

    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    
    public String getExamineBy() {
        return examineBy;
    }

    
    public void setExamineBy(String examineBy) {
        this.examineBy = examineBy == null ? null : examineBy.trim();
    }

    
    public Date getExamineDate() {
        return examineDate;
    }

    
    public void setExamineDate(Date examineDate) {
        this.examineDate = examineDate;
    }

    
    public String getExamineOpinion() {
        return examineOpinion;
    }

    
    public void setExamineOpinion(String examineOpinion) {
        this.examineOpinion = examineOpinion == null ? null : examineOpinion.trim();
    }

    
    public String getExamineResult() {
        return examineResult;
    }

    
    public void setExamineResult(String examineResult) {
        this.examineResult = examineResult == null ? null : examineResult.trim();
    }
}