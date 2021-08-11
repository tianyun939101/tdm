package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.dz.DzProviderDirectories;
import com.demxs.tdm.domain.business.nostandard.NoStandardProblem;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestLog;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.SubCenter;
import com.demxs.tdm.domain.resource.kowledge.Standard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据中心基础数据实体类
 */
public class DataBaseInfo extends DataEntity<DataBaseInfo>{

    private static final long serialVersionUID = 1L;

    private String entrustId;//申请单id

    private String entrustCode;//申请单code

    private String entrustType;//申请单类型

    private String applicant;//申请人

    private String entrustOrg;//申请单部门

    private String company;//所属公司

    private String labLeader;//试验室负责人

    private String testLeader;//试验负责人

    private String taskNo;//任务书编号

    private String planNo;//计划编号

    private String ataChapter;//ATA章节

    private String testNature;//试验性质

    private String productModel;//产品型号

    private String testName;//试验名称

    private String testProgramType;//试验大纲类型

    private String testProgram;//试验大纲

    private String configType;//构型类型

    private String config;//构型

    private String testProgramName;//试验大纲名

    private String testProgramNo;//试验大纲编号

    private String tvId;//试验大纲版本

    private String cvId;//构型版本

    private String configName;//构型名字

    private String labId;//所属试验室id

    private Date applyDate;//申请时间

    private String testPurpose;//试验目的

    private String reportCode;//试验报告

    private LabInfo labInfo;//所属试验室

    private SubCenter subCenter;

    private DataTestInfo dataTestInfo;

    private List<AuditInfo> auditInfoList;//审核记录

    private List<DataTestInfo> testInfoList;//试验项目信息

    private List<ATAChapter> ataList;//ATA章节信息

    private List<DataBasePerm> permList;//试验权限信息

    private List<User> reportUser;//提报权限用户集合

    private List<Office> reportOffice;//提报权限部部门集合

    private List<User> searchUser;//查看权限用户集合

    private List<Office> searchOffice;//查看权限部部门集合

    private String reportUserStr;//提报权限用户Str

    private String reportOfficeStr;//提报权限部部门Str

    private String searchUserStr;//查看权限用户Str

    private String searchOfficeStr;//查看权限部部门Str

    private Date searchEndDate;//查看权限截至日期

    private String isPerm;

    protected User apply;	//申请人

    protected User leader;	// 负责人

    private String opUser;//处理人

    private User searchBy;

    private String providerId;

    private String providerContact;

    private DzProviderDirectories dzProviderDirectories;//试验服务商

    private DcServerProvide dcServerProvide;//试验服务商

    private boolean enableOperate;//允许操作

    private String testDetailName;//试验报告

    //本次新增


    private String  taskNum;//任务书信息

    private String  planFile;//计划书信息

    private String  testReport;//试验报告信息

    private String  taskNumType;//任务书类型

    private String  testReportType;//试验报告类型

    private String  timeRange;


    private  String  status;


    //审核人
    private User auditUser;

    private String insertUser;


    private String  isFlag;

    private String  reason;

    private String result;

    //流程发起人
    private String initiator;
    private User initiatorUser;

    //用户审批
    private String tecDir;
    private User tecDirUser;

    //数据管理员审批
    private String tecMin;
    private User tecMinUser;

    private String auditUserId;


    private String taskVersion;

    private String  taskView;

    private String  planVersion;

    private String  reportVersion;

    private  String configVersion;

    private String  outLineVersion;

    private String  outLineView;

    private String configNo;

    private String reportFlag;


    private String reportInfo;

    private String checkId;


    private String approveStatus;


    private String applicationApply;

    private String companyName; //公司名称

    private String flag; //进入页面

    private String outFile;

    //标准规范体系
    private String standard;
    private List<Standard> standardList;

    //课题名称
    private String taskName;
    //课题号
    private String taskCode;
    //承担单位
    private String responsibleUnit;


    private String belong;
    private String logSum;

    private String testCenter;//所属中心


    private  String  dataCenter;

    /**
     * 预先研究专业分类
     */
    private String profesClass;

    private String  startTime;

    private String endTime;

    private String planDataRange;

    public String getPlanDataRange() {
        return planDataRange;
    }

    public void setPlanDataRange(String planDataRange) {
        this.planDataRange = planDataRange;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getTestCenter() {
        return testCenter;
    }

    public void setTestCenter(String testCenter) {
        this.testCenter = testCenter;
    }

    public String getLogSum() {
        return logSum;
    }

    public void setLogSum(String logSum) {
        this.logSum = logSum;
    }

    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public String getApplicationApply() {
        return applicationApply;
    }

    public void setApplicationApply(String applicationApply) {
        this.applicationApply = applicationApply;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getTaskView() {
        return taskView;
    }

    public void setTaskView(String taskView) {
        this.taskView = taskView;
    }

    public String getOutLineView() {
        return outLineView;
    }

    public void setOutLineView(String outLineView) {
        this.outLineView = outLineView;
    }

    public String getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(String reportInfo) {
        this.reportInfo = reportInfo;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
    }

    //试验日志
    private List<NoStandardTestLog> testLogList;
    //问题记录
    private List<NoStandardProblem> problemList;

    public List<NoStandardTestLog> getTestLogList() {
        return testLogList;
    }

    public void setTestLogList(List<NoStandardTestLog> testLogList) {
        this.testLogList = testLogList;
    }

    public List<NoStandardProblem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<NoStandardProblem> problemList) {
        this.problemList = problemList;
    }

    public String getConfigNo() {
        return configNo;
    }

    public void setConfigNo(String configNo) {
        this.configNo = configNo;
    }

    public String getTaskVersion() {
        return taskVersion;
    }

    public void setTaskVersion(String taskVersion) {
        this.taskVersion = taskVersion;
    }

    public String getPlanVersion() {
        return planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion;
    }

    public String getReportVersion() {
        return reportVersion;
    }

    public void setReportVersion(String reportVersion) {
        this.reportVersion = reportVersion;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getOutLineVersion() {
        return outLineVersion;
    }

    public void setOutLineVersion(String outLineVersion) {
        this.outLineVersion = outLineVersion;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public User getInitiatorUser() {
        return initiatorUser;
    }

    public void setInitiatorUser(User initiatorUser) {
        this.initiatorUser = initiatorUser;
    }

    public String getTecDir() {
        return tecDir;
    }

    public void setTecDir(String tecDir) {
        this.tecDir = tecDir;
    }

    public User getTecDirUser() {
        return tecDirUser;
    }

    public void setTecDirUser(User tecDirUser) {
        this.tecDirUser = tecDirUser;
    }

    public String getTecMin() {
        return tecMin;
    }

    public void setTecMin(String tecMin) {
        this.tecMin = tecMin;
    }

    public User getTecMinUser() {
        return tecMinUser;
    }

    public void setTecMinUser(User tecMinUser) {
        this.tecMinUser = tecMinUser;
    }

    //
    private AuditInfo auditInfo;

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public DcServerProvide getDcServerProvide() {
        return dcServerProvide;
    }

    public void setDcServerProvide(DcServerProvide dcServerProvide) {
        this.dcServerProvide = dcServerProvide;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public String getTaskNumType() {
        return taskNumType;
    }

    public void setTaskNumType(String taskNumType) {
        this.taskNumType = taskNumType;
    }

    public String getTestReportType() {
        return testReportType;
    }

    public void setTestReportType(String testReportType) {
        this.testReportType = testReportType;
    }

    public DataBaseInfo() {
        super();
    }

    public DataBaseInfo(String testName,String taskNo,String taskVersion,String taskView,
                        String planNo,  String testNature, String productModel,
                        String ataChapter,String testProgramNo,String outLineVersion,String outLineView, LabInfo labInfo) {
        this.testName = testName;
        this.taskNo = taskNo;
        this.taskVersion=taskVersion;
        this.taskView=taskView;
        this.outLineVersion=outLineVersion;
        this.testProgramNo=testProgramNo;
        this.outLineView=outLineView;
        this.planNo = planNo;
        this.testNature = testNature;
        this.productModel = productModel;
        this.ataChapter = ataChapter;
        this.labInfo = labInfo;
    }

    public DataBaseInfo(DataReportXml xml) {
        this.testName = xml.getTestName();
        this.taskNo = xml.getTaskNo();
        this.planNo = xml.getPlanNo();
        this.reportCode = xml.getReportNo();
        this.productModel = xml.getProductModel();
        this.testNature = xml.getTestNature();
        this.company = xml.getCompany();
        this.labId = xml.getLab();
        this.ataChapter = xml.getAta();
        this.testPurpose =  xml.getTestPurpose();
        this.testDetailName=xml.getTestDetailName();
        this.testCenter=xml.getTestCenter();
    }

    public DataBaseInfo(DataServerXml xml) {
        this.testName = xml.getTestName();
        this.providerId=xml.getProviderId();
        this.providerContact=xml.getProviderContact();
       // this.taskNo = xml.getTaskNo();
      //  this.planNo = xml.getPlanNo();
       // this.reportCode = xml.getReportNo();
       // this.productModel = xml.getProductModel();
        this.testNature = xml.getTestNature();
        this.company = xml.getCompany();
        this.labId = xml.getLab();
      //  this.ataChapter = xml.getAta();
        this.testPurpose =  xml.getTestPurpose();
       // this.testCenter=xml.getTestCenter();
    }

    public String getPlanFile() {
        return planFile;
    }

    public void setPlanFile(String planFile) {
        this.planFile = planFile;
    }

    public String getTestDetailName() {
        return testDetailName;
    }

    public void setTestDetailName(String testDetailName) {
        this.testDetailName = testDetailName;
    }

    public DataBaseInfo(String id){
        super(id);
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getEntrustOrg() {
        return entrustOrg;
    }

    public void setEntrustOrg(String entrustOrg) {
        this.entrustOrg = entrustOrg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLabLeader() {
        return labLeader;
    }

    public void setLabLeader(String labLeader) {
        this.labLeader = labLeader;
    }

    public String getTestLeader() {
        return testLeader;
    }

    public void setTestLeader(String testLeader) {
        this.testLeader = testLeader;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getAtaChapter() {
        return ataChapter;
    }

    public void setAtaChapter(String ataChapter) {
        this.ataChapter = ataChapter;
    }

    public String getTestNature() {
        return testNature;
    }

    public void setTestNature(String testNature) {
        this.testNature = testNature;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestProgramType() {
        return testProgramType;
    }

    public void setTestProgramType(String testProgramType) {
        this.testProgramType = testProgramType;
    }

    public String getTestProgram() {
        return testProgram;
    }

    public void setTestProgram(String testProgram) {
        this.testProgram = testProgram;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public List<DataTestInfo> getTestInfoList() {
        return testInfoList;
    }

    public void setTestInfoList(List<DataTestInfo> testInfoList) {
        this.testInfoList = testInfoList;
    }

    public String getAtaNames(){
        if(ataList == null || ataList.isEmpty()){
            return "";
        }
        List<String> names = new ArrayList<>();
        for(ATAChapter ataChapter : ataList){
            names.add(ataChapter.getName());
        }
        return Collections3.convertToString(names,",");
    }

    public List<ATAChapter> getAtaList() {
        return ataList;
    }

    public void setAtaList(List<ATAChapter> ataList) {
        this.ataList = ataList;
    }

    public List<DataBasePerm> getPermList() {
        return permList;
    }

    public void setPermList(List<DataBasePerm> permList) {
        this.permList = permList;
    }

    public List<User> getReportUser() {
        return reportUser;
    }

    public void setReportUser(List<User> reportUser) {
        this.reportUser = reportUser;
    }

    public List<Office> getReportOffice() {
        return reportOffice;
    }

    public void setReportOffice(List<Office> reportOffice) {
        this.reportOffice = reportOffice;
    }

    public List<User> getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(List<User> searchUser) {
        this.searchUser = searchUser;
    }

    public List<Office> getSearchOffice() {
        return searchOffice;
    }

    public void setSearchOffice(List<Office> searchOffice) {
        this.searchOffice = searchOffice;
    }

    public Date getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(Date searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public String getReportUserStr() {
        return reportUserStr;
    }

    public void setReportUserStr(String reportUserStr) {
        this.reportUserStr = reportUserStr;
    }

    public String getReportOfficeStr() {
        return reportOfficeStr;
    }

    public void setReportOfficeStr(String reportOfficeStr) {
        this.reportOfficeStr = reportOfficeStr;
    }

    public String getSearchUserStr() {
        return searchUserStr;
    }

    public void setSearchUserStr(String searchUserStr) {
        this.searchUserStr = searchUserStr;
    }

    public String getSearchOfficeStr() {
        return searchOfficeStr;
    }

    public void setSearchOfficeStr(String searchOfficeStr) {
        this.searchOfficeStr = searchOfficeStr;
    }

    public DataTestInfo getDataTestInfo() {
        return dataTestInfo;
    }

    public void setDataTestInfo(DataTestInfo dataTestInfo) {
        this.dataTestInfo = dataTestInfo;
    }

    public User getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(User searchBy) {
        this.searchBy = searchBy;
    }

    public DzProviderDirectories getDzProviderDirectories() {
        return dzProviderDirectories;
    }

    public void setDzProviderDirectories(DzProviderDirectories dzProviderDirectories) {
        this.dzProviderDirectories = dzProviderDirectories;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderContact() {
        return providerContact;
    }

    public void setProviderContact(String providerContact) {
        this.providerContact = providerContact;
    }

    public String getIsPerm() {
        return isPerm;
    }

    public void setIsPerm(String isPerm) {
        this.isPerm = isPerm;
    }

    public String getLabId() {
        return labId;
    }

    public DataBaseInfo setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getTestPurpose() {
        return testPurpose;
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public User getApply() {
        return apply;
    }

    public void setApply(User apply) {
        this.apply = apply;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

    public String getTestProgramName() {
        return testProgramName;
    }

    public void setTestProgramName(String testProgramName) {
        this.testProgramName = testProgramName;
    }

    public String getTestProgramNo() {
        return testProgramNo;
    }

    public void setTestProgramNo(String testProgramNo) {
        this.testProgramNo = testProgramNo;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public String getCvId() {
        return cvId;
    }

    public void setCvId(String cvId) {
        this.cvId = cvId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"entrustId\":\"")
                .append(entrustId).append('\"');
        sb.append(",\"entrustCode\":\"")
                .append(entrustCode).append('\"');
        sb.append(",\"entrustType\":\"")
                .append(entrustType).append('\"');
        sb.append(",\"applicant\":\"")
                .append(applicant).append('\"');
        sb.append(",\"entrustOrg\":\"")
                .append(entrustOrg).append('\"');
        sb.append(",\"company\":\"")
                .append(company).append('\"');
        sb.append(",\"labLeader\":\"")
                .append(labLeader).append('\"');
        sb.append(",\"testLeader\":\"")
                .append(testLeader).append('\"');
        sb.append(",\"taskNo\":\"")
                .append(taskNo).append('\"');
        sb.append(",\"planNo\":\"")
                .append(planNo).append('\"');
        sb.append(",\"ataChapter\":\"")
                .append(ataChapter).append('\"');
        sb.append(",\"testNature\":\"")
                .append(testNature).append('\"');
        sb.append(",\"productModel\":\"")
                .append(productModel).append('\"');
        sb.append(",\"testName\":\"")
                .append(testName).append('\"');
        sb.append(",\"testProgramType\":\"")
                .append(testProgramType).append('\"');
        sb.append(",\"testProgram\":\"")
                .append(testProgram).append('\"');
        sb.append(",\"configType\":\"")
                .append(configType).append('\"');
        sb.append(",\"config\":\"")
                .append(config).append('\"');
        sb.append(",\"labId\":\"")
                .append(labId).append('\"');
        sb.append(",\"applyDate\":\"")
                .append(applyDate).append('\"');
        sb.append(",\"testPurpose\":\"")
                .append(testPurpose).append('\"');
        sb.append(",\"reportCode\":\"")
                .append(reportCode).append('\"');
        sb.append(",\"labInfo\":")
                .append(labInfo);
        sb.append(",\"dataTestInfo\":")
                .append(dataTestInfo);
        sb.append(",\"auditInfoList\":")
                .append(auditInfoList);
        sb.append(",\"testInfoList\":")
                .append(testInfoList);
        sb.append(",\"ataList\":")
                .append(ataList);
        sb.append(",\"permList\":")
                .append(permList);
        sb.append(",\"reportUser\":")
                .append(reportUser);
        sb.append(",\"reportOffice\":")
                .append(reportOffice);
        sb.append(",\"searchUser\":")
                .append(searchUser);
        sb.append(",\"searchOffice\":")
                .append(searchOffice);
        sb.append(",\"reportUserStr\":\"")
                .append(reportUserStr).append('\"');
        sb.append(",\"reportOfficeStr\":\"")
                .append(reportOfficeStr).append('\"');
        sb.append(",\"searchUserStr\":\"")
                .append(searchUserStr).append('\"');
        sb.append(",\"searchOfficeStr\":\"")
                .append(searchOfficeStr).append('\"');
        sb.append(",\"searchEndDate\":\"")
                .append(searchEndDate).append('\"');
        sb.append(",\"isPerm\":\"")
                .append(isPerm).append('\"');
        sb.append(",\"apply\":")
                .append(apply);
        sb.append(",\"leader\":")
                .append(leader);
        sb.append(",\"searchBy\":")
                .append(searchBy);
        sb.append(",\"providerId\":\"")
                .append(providerId).append('\"');
        sb.append(",\"providerContact\":\"")
                .append(providerContact).append('\"');
        sb.append(",\"dzProviderDirectories\":")
                .append(dzProviderDirectories);
        sb.append('}');
        return sb.toString();
    }

    public boolean isEnableOperate() {
        return enableOperate;
    }

    public void setEnableOperate(boolean enableOperate) {
        this.enableOperate = enableOperate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public List<Standard> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Standard> standardList) {
        this.standardList = standardList;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getResponsibleUnit() {
        return responsibleUnit;
    }

    public void setResponsibleUnit(String responsibleUnit) {
        this.responsibleUnit = responsibleUnit;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }


    public SubCenter getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(SubCenter subCenter) {
        this.subCenter = subCenter;
    }

    public String getProfesClass() {
        return profesClass;
    }

    public void setProfesClass(String profesClass) {
        this.profesClass = profesClass;
    }
}