package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.AuditInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DzAbilityAssess extends DataEntity<DzAbilityAssess> {
    
    private String id;
    
    private String abilityId;

    private String state;

    public String getSimilar() {
        return similar;
    }

    public List<AuditInfo> getAuditInfoList() {
        return this.auditInfoList = auditInfoList == null ? null : auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList == null ? null : auditInfoList;
    }

    private List<AuditInfo> auditInfoList;

    public void setSimilar(String similar) {
        this.similar = similar;
    }

    private String similar;

    private String createBy;
    
    private Date createDate;

    private String updateBy;
    
    private String updateDate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId == null ? null : abilityId.trim();
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate == null ? null : updateDate.trim();
    }
}