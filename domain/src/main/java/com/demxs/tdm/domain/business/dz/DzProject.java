package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DzProject extends DataEntity<DzProject> {
    
    private String id;

    
    private String parentId;

    
    private String name;

    
    private String filetype;

    
    private String fileIds;

    
    private String summarys;

    
    private String browse;

    
    private String download;

    
    private String visibleD;

    
    private String visibleU;

    
    private String remarks;

    
    private String createBy;

    
    private Date createDate;

    
    private String updateBy;

    public String[] getVisibleDids() {
        return visibleDids;
    }

    public void setVisibleDids(String[] visibleDids) {
        this.visibleDids = visibleDids;
    }

    private String[] visibleDids;

    public String getVisibleUids() {
        return visibleUids;
    }

    public void setVisibleUids(String visibleUids) {
        this.visibleUids = visibleUids;
    }

    private String visibleUids;

    
    private Date updateDate;

    private String officeAllNames;//可见部门名称集合

    private String userAllNames;//可见人员名称集合

    private List<User> users = Lists.newArrayList();

    private List<Office> offices = Lists.newArrayList();

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public String getOfficeAllNames() {
        StringBuilder allName = new StringBuilder();
        if(CollectionUtils.isNotEmpty(offices)){
            for(Office o:offices){
                allName.append(o.getName()+",");
            }
        }
        return allName.toString();
    }

    public void setOfficeAllNames(String officeAllNames) {
        this.officeAllNames = officeAllNames;
    }

    public String getUserAllNames() {
        StringBuilder allName = new StringBuilder();
        if(CollectionUtils.isNotEmpty(users)){
            for(User u:users){
                allName.append(u.getName()+",");
            }
        }
        return allName.toString();
    }
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    
    private String state;
    public List<AuditInfo> getAuditInfoList() {
        return this.auditInfoList = auditInfoList == null ? null : auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList == null ? null : auditInfoList;
    }

    private List<AuditInfo> auditInfoList;
    
    private static final long serialVersionUID = 1L;

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    
    public String getParentId() {
        return parentId;
    }

    
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getFiletype() {
        return filetype;
    }

    
    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    
    public String getFileIds() {
        return fileIds;
    }

    
    public void setFileIds(String fileIds) {
        this.fileIds = fileIds == null ? null : fileIds.trim();
    }

    
    public String getSummarys() {
        return summarys;
    }

    
    public void setSummarys(String summarys) {
        this.summarys = summarys == null ? null : summarys.trim();
    }

    
    public String getBrowse() {
        return browse;
    }

    
    public void setBrowse(String browse) {
        this.browse = browse == null ? null : browse.trim();
    }

    
    public String getDownload() {
        return download;
    }

    
    public void setDownload(String download) {
        this.download = download == null ? null : download.trim();
    }

    
    public String getVisibleD() {
        return visibleD;
    }

    
    public void setVisibleD(String visibleD) {
        this.visibleD = visibleD == null ? null : visibleD.trim();
    }

    
    public String getVisibleU() {
        return visibleU;
    }

    
    public void setVisibleU(String visibleU) {
        this.visibleU = visibleU == null ? null : visibleU.trim();
    }

    
    public String getRemarks() {
        return remarks;
    }

    
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    
    public String getState() {
        return state;
    }

    
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}