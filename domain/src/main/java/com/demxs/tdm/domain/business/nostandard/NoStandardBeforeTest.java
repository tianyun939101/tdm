package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 15:18
 * @Description:
 */
public class NoStandardBeforeTest extends DataEntity<NoStandardBeforeTest> {

    private static final long serialVersionUID = 1L;
    //申请单id
    private String entrustId;
    //大纲基础信息id
    private String testOutlineId;
    //大纲版本id
    private String tvId;
    //构型基础信息id
    private String configurationId;
    //构型版本id
    private String cvId;
    //实验负责人id
    private String testManagerId;
    //资源分配id
    private String resourceId;
    //上传附件
    private String data;
    //试验大纲类型
    private String testOutlineType;
    //上传试验大纲
    private String testOutlineFile;
    //试验大纲code
    private String testOutlineCode;
    //其他人员
    private List<NoStandardOtherUser> otherUsers;

    //审核信息
    public List<AuditInfo> auditInfoList;

    public NoStandardBeforeTest() {
        super();
    }

    public NoStandardBeforeTest(String id) {
        super(id);
    }

    public String getEntrustId() {
        return entrustId;
    }

    public NoStandardBeforeTest setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public String getTestOutlineId() {
        return testOutlineId;
    }

    public NoStandardBeforeTest setTestOutlineId(String testOutlineId) {
        this.testOutlineId = testOutlineId;
        return this;
    }

    public String getTvId() {
        return tvId;
    }

    public NoStandardBeforeTest setTvId(String tvId) {
        this.tvId = tvId;
        return this;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public NoStandardBeforeTest setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
        return this;
    }

    public String getCvId() {
        return cvId;
    }

    public NoStandardBeforeTest setCvId(String cvId) {
        this.cvId = cvId;
        return this;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public NoStandardBeforeTest setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public NoStandardBeforeTest setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public List<NoStandardOtherUser> getOtherUsers() {
        return otherUsers;
    }

    public NoStandardBeforeTest setOtherUsers(List<NoStandardOtherUser> otherUsers) {
        this.otherUsers = otherUsers;
        return this;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public NoStandardBeforeTest setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
        return this;
    }

    public String getData() {
        return data;
    }

    public NoStandardBeforeTest setData(String data) {
        this.data = data;
        return this;
    }

    public String getTestOutlineType() {
        return testOutlineType;
    }

    public NoStandardBeforeTest setTestOutlineType(String testOutlineType) {
        this.testOutlineType = testOutlineType;
        return this;
    }

    public String getTestOutlineFile() {
        return testOutlineFile;
    }

    public NoStandardBeforeTest setTestOutlineFile(String testOutlineFile) {
        this.testOutlineFile = testOutlineFile;
        return this;
    }

    public String getTestOutlineCode() {
        return testOutlineCode;
    }

    public NoStandardBeforeTest setTestOutlineCode(String testOutlineCode) {
        this.testOutlineCode = testOutlineCode;
        return this;
    }
}
