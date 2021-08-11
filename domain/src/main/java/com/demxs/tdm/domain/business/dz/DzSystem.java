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

public class DzSystem extends DataEntity<DzSystem> {
    //名称
    private String name;
    //类型
    private DzSystemClass type;
    //文件
    private String fileIds;
    //摘要
    private String summarys;
    private String browse;
    private String download;
    //状态
    private String state;
    //审核人
    private User auditUser;
    private List<User> auditUserList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DzSystemClass getType() {
        return type;
    }

    public void setType(DzSystemClass type) {
        this.type = type;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public String getSummarys() {
        return summarys;
    }

    public void setSummarys(String summarys) {
        this.summarys = summarys;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public List<User> getAuditUserList() {
        return auditUserList;
    }

    public void setAuditUserList(List<User> auditUserList) {
        this.auditUserList = auditUserList;
    }
}