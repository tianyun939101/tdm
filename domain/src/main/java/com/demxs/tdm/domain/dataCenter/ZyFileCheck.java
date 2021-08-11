package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.Date;
import java.util.List;

public class ZyFileCheck extends DataEntity<ZyFileCheck> {

    private static final long serialVersionUID = 1L;

    private String fileType;//文件类型

    private String fileNum;//文件编号

    private String fileName;//文件名称

    private String officeId;//科室

    private String checkId;//校对人

    private Date checkDate;//校对时间


    private String  fileAttach;//fujian

    private String isFlag;//一票否决


    private String remarks;//

    private User createUser;

    private User  checkUser;

    private Office office;


    private String status;//状态

    private String flag;


    private List<ZyFileAttribute> zyFileAttributeList;


    private List<ZyFileCheckRelation> zyFileCheckRelationList;

    private List<AuditInfo> auditList;

    private List<ZyFileAttribute> zyCheckAttributeList;


    public List<ZyFileAttribute> getZyCheckAttributeList() {
        return zyCheckAttributeList;
    }

    public void setZyCheckAttributeList(List<ZyFileAttribute> zyCheckAttributeList) {
        this.zyCheckAttributeList = zyCheckAttributeList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<AuditInfo> getAuditList() {
        return auditList;
    }

    public void setAuditList(List<AuditInfo> auditList) {
        this.auditList = auditList;
    }

    public List<ZyFileCheckRelation> getZyFileCheckRelationList() {
        return zyFileCheckRelationList;
    }

    public void setZyFileCheckRelationList(List<ZyFileCheckRelation> zyFileCheckRelationList) {
        this.zyFileCheckRelationList = zyFileCheckRelationList;
    }

    public List<ZyFileAttribute> getZyFileAttributeList() {
        return zyFileAttributeList;
    }

    public void setZyFileAttributeList(List<ZyFileAttribute> zyFileAttributeList) {
        this.zyFileAttributeList = zyFileAttributeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public String getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(String fileAttach) {
        this.fileAttach = fileAttach;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
