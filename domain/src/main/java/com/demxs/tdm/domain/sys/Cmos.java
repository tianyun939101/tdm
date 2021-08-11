package com.demxs.tdm.domain.sys;


import com.demxs.tdm.common.persistence.DataEntity;

public class Cmos extends DataEntity<Cmos> {
    /**
     * 用户工号
     */
    private String userNo;
    /**
     * 文件编号
     */
    private String documentCode;
    /**
     * 文件版本号
     */
    private String documentVersion;
    /**
     * 来源系统
     */
    private String sourceSystem;

    /**
     * 链接
     * @return
     */
    private  String uri;


    /**
     * 用户名/密码
     * @return
     */
    private String userName;
    private String userPass;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }
}
