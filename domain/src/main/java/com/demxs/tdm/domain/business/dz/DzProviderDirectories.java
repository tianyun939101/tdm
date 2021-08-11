package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;

import java.io.Serializable;
import java.util.Date;

public class DzProviderDirectories extends DataEntity<DzProviderDirectories> {
    
    private String id;
    
    private String name;

    private String contacts;

    private String telephone;

    private String address;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String mail;
    private String remarks;

    private String fileIds;

    private String state;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String phone;

    private String password;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContacts() {
        return contacts;
    }
    
    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }
    
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds == null ? null : fileIds.trim();
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}