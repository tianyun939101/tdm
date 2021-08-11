package com.demxs.tdm.domain.business.testoutline;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TestOutlineVersion extends DataEntity<TestOutlineVersion> {

    private static final long serialVersionUID = 1L;

    //基础信息id
    private String baseId;
    //版本号
    private String version;
    //版本状态
    private String status;
    //是否当前生效版本
    private String curVersion;
    //审核前大纲
    private String localReport;
    //审核后大纲
    private String auditReport;
    //报告生成时间
    private Date reportTime;
    //大纲编号
    private String code;

    public TestOutlineVersion(){
        super();
    }

    public TestOutlineVersion(String id){
        super(id);
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurVersion() {
        return curVersion;
    }

    public void setCurVersion(String curVersion) {
        this.curVersion = curVersion;
    }

    public String getLocalReport() {
        return localReport;
    }

    public void setLocalReport(String localReport) {
        this.localReport = localReport;
    }

    public String getAuditReport() {
        return auditReport;
    }

    public void setAuditReport(String auditReport) {
        this.auditReport = auditReport;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getCode() {
        return code;
    }

    public TestOutlineVersion setCode(String code) {
        this.code = code;
        return this;
    }
}
