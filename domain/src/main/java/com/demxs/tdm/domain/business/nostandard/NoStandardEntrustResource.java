package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.configuration.BaseConfiguration;
import com.demxs.tdm.domain.business.configuration.ConfigurationVO;
import com.demxs.tdm.domain.business.constant.NoStandardTestOutlineTypeEnum;
import com.demxs.tdm.domain.business.testoutline.BaseTestOutline;
import com.demxs.tdm.domain.business.testoutline.TestOutlineVersion;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;

import java.util.Date;
import java.util.List;

/**
 * 非标业务资源分配entity
 */
public class NoStandardEntrustResource extends DataEntity<NoStandardEntrustResource> {

    private static final long serialVersionUID = 1L;
    //申请单编号
    private String entrustCode;
    private String entrustId;
    //申请人
    private String applicant;
    //接收时间
    private Date acceptDate;
    //状态
    private String status;
    //所属科室
    private String labId;
    private String labName;

    private NoStandardEntrustInfo entrustInfo;
    //构型基础信息
    private String configurationId;
    private BaseConfiguration configuration;
    //大纲基础信息
    private String testOutlineId;
    private BaseTestOutline testOutline;

    //保存时使用的构型版本id
    private String cvId;
    private String cVersion;
    private ConfigurationVO configCurVersion;

    //保存时使用的大纲版本id
    private String tvId;
    private String tVersion;
    private TestOutlineVersion outLineCurVersion;
    //大纲类型
    private String testOutlineType;
    //上传大纲
    private String testOutlineFile;
    //上传大纲时编号
    private String testOutlineFileCode;
    //上传大纲时版本
    private String testOutlineFileVersion;
    //上传大纲时名称
    private String testOutlineFileName;
    //输入大纲编号
    private String testOutlineCode;
    //输入大纲编号时版本
    private String testOutlineCodeVersion;
    //输入大纲编号时名称
    private String testOutlineCodeName;
    //选择系统大纲时编号
    private String testOutlineSysCode;

    //试验负责人
    private String testManagerId;
    private Yuangong testManager;
    //其他人员
    private List<NoStandardOtherUser> otherUsers;
    //当前审核人
    private String curAuditUser;

    //试验前检查
    private String beforeId;
    private NoStandardBeforeTest beforeTest;
    //审核信息
    private List<AuditInfo> auditInfoList;
    //视图传递对象
    private AuditInfo auditInfo;
    //流程提交
    private String submit; //1是  2否

    public NoStandardEntrustResource() {
        super();
    }

    public NoStandardEntrustResource(String id) {
        super(id);
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public NoStandardEntrustInfo getEntrustInfo() {
        return entrustInfo;
    }

    public void setEntrustInfo(NoStandardEntrustInfo entrustInfo) {
        this.entrustInfo = entrustInfo;
    }

    public BaseConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(BaseConfiguration configuration) {
        this.configuration = configuration;
    }

    public BaseTestOutline getTestOutline() {
        return testOutline;
    }

    public void setTestOutline(BaseTestOutline testOutline) {
        this.testOutline = testOutline;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public void setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
    }

    public Yuangong getTestManager() {
        return testManager;
    }

    public void setTestManager(Yuangong testManager) {
        this.testManager = testManager;
    }

    public List<NoStandardOtherUser> getOtherUsers() {
        return otherUsers;
    }

    public void setOtherUsers(List<NoStandardOtherUser> otherUsers) {
        this.otherUsers = otherUsers;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public NoStandardEntrustResource setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
        return this;
    }

    public String getTestoutlineId() {
        return testOutlineId;
    }

    public NoStandardEntrustResource setTestoutlineId(String testoutlineId) {
        this.testOutlineId = testoutlineId;
        return this;
    }

    public ConfigurationVO getConfigCurVersion() {
        return configCurVersion;
    }

    public NoStandardEntrustResource setConfigCurVersion(ConfigurationVO configCurVersion) {
        this.configCurVersion = configCurVersion;
        return this;
    }

    public TestOutlineVersion getTestOutlineVersion() {
        return outLineCurVersion;
    }

    public NoStandardEntrustResource setTestOutlineVersion(TestOutlineVersion outLineCurVersion) {
        this.outLineCurVersion = outLineCurVersion;
        return this;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public NoStandardEntrustResource setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public TestOutlineVersion getOutLineCurVersion() {
        return outLineCurVersion;
    }

    public NoStandardEntrustResource setOutLineCurVersion(TestOutlineVersion outLineCurVersion) {
        this.outLineCurVersion = outLineCurVersion;
        return this;
    }

    public String getTestOutlineType() {
        return testOutlineType;
    }

    public NoStandardEntrustResource setTestOutlineType(String testOutlineType) {
        this.testOutlineType = testOutlineType;
        return this;
    }

    public String getTestOutlineFile() {
        return testOutlineFile;
    }

    public NoStandardEntrustResource setTestOutlineFile(String testOutlineFile) {
        this.testOutlineFile = testOutlineFile;
        return this;
    }

    public String getTestOutlineCode() {
        return testOutlineCode;
    }

    public NoStandardEntrustResource setTestOutlineCode(String testOutlineCode) {
        this.testOutlineCode = testOutlineCode;
        return this;
    }

    public String getBeforeId() {
        return beforeId;
    }

    public NoStandardEntrustResource setBeforeId(String beforeId) {
        this.beforeId = beforeId;
        return this;
    }

    public NoStandardBeforeTest getBeforeTest() {
        return beforeTest;
    }

    public NoStandardEntrustResource setBeforeTest(NoStandardBeforeTest beforeTest) {
        this.beforeTest = beforeTest;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public NoStandardEntrustResource setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getTestOutlineId() {
        return testOutlineId;
    }

    public NoStandardEntrustResource setTestOutlineId(String testOutlineId) {
        this.testOutlineId = testOutlineId;
        return this;
    }

    public String getcVersion() {
        return cVersion;
    }

    public NoStandardEntrustResource setcVersion(String cVersion) {
        this.cVersion = cVersion;
        return this;
    }

    public String gettVersion() {
        return tVersion;
    }

    public NoStandardEntrustResource settVersion(String tVersion) {
        this.tVersion = tVersion;
        return this;
    }

    public String getTestOutlineFileCode() {
        return testOutlineFileCode;
    }

    public NoStandardEntrustResource setTestOutlineFileCode(String testOutlineFileCode) {
        this.testOutlineFileCode = testOutlineFileCode;
        return this;
    }

    public String getTestOutlineFileVersion() {
        return testOutlineFileVersion;
    }

    public NoStandardEntrustResource setTestOutlineFileVersion(String testOutlineFileVersion) {
        this.testOutlineFileVersion = testOutlineFileVersion;
        return this;
    }

    public String getTestOutlineCodeVersion() {
        return testOutlineCodeVersion;
    }

    public NoStandardEntrustResource setTestOutlineCodeVersion(String testOutlineCodeVersion) {
        this.testOutlineCodeVersion = testOutlineCodeVersion;
        return this;
    }

    public String getTestOutlineSysCode() {
        return testOutlineSysCode;
    }

    public NoStandardEntrustResource setTestOutlineSysCode(String testOutlineSysCode) {
        this.testOutlineSysCode = testOutlineSysCode;
        return this;
    }

    public String getTestOutlineFileName() {
        return testOutlineFileName;
    }

    public NoStandardEntrustResource setTestOutlineFileName(String testOutlineFileName) {
        this.testOutlineFileName = testOutlineFileName;
        return this;
    }

    public String getTestOutlineCodeName() {
        return testOutlineCodeName;
    }

    public NoStandardEntrustResource setTestOutlineCodeName(String testOutlineCodeName) {
        this.testOutlineCodeName = testOutlineCodeName;
        return this;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public NoStandardEntrustResource setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public String getTestOutlineName() {
        if(NoStandardTestOutlineTypeEnum.INPUT_CODE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineCodeName)){
            return testOutlineCodeName;
        }else if(NoStandardTestOutlineTypeEnum.UPLOAD_FILE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineFileName)){
            return testOutlineFileName;
        }else if(NoStandardTestOutlineTypeEnum.SYS.getCode().equals(testOutlineType) &&
                null != testOutline && StringUtils.isNotBlank(testOutline.getName())){
            return testOutline.getName();
        }
        return "";
    }

    public String getCurTestOutlineCode() {
        if(NoStandardTestOutlineTypeEnum.INPUT_CODE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineCodeName)){
            return testOutlineCode;
        }else if(NoStandardTestOutlineTypeEnum.UPLOAD_FILE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineFileName)){
            return testOutlineFileCode;
        }else if(NoStandardTestOutlineTypeEnum.SYS.getCode().equals(testOutlineType) &&
                null != testOutline && StringUtils.isNotBlank(testOutline.getName())){
            return testOutlineSysCode;
        }
        return null;
    }

    public String getCurAuditUser() {
        return curAuditUser;
    }

    public NoStandardEntrustResource setCurAuditUser(String curAuditUser) {
        this.curAuditUser = curAuditUser;
        return this;
    }

    public boolean getAuditFlag(){
        if(StringUtils.isBlank(curAuditUser)){
            return false;
        }
        User user = UserUtils.getUser();
        if(user.getId().equals(curAuditUser)){
            return true;
        }else {
            return false;
        }
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }
}
