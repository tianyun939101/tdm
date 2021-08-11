package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.anno.ExcelField;
import com.demxs.tdm.domain.business.configuration.ConfigurationVO;
import com.demxs.tdm.domain.business.constant.NoStandardTestOutlineTypeEnum;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 非标试验执行
 */
@ExcelField(startRow = 4,sheetIndex = 0)
public class NoStandardExecution extends DataEntity<NoStandardExecution> {

    private static final long serialVersionUID = 1L;
    //申请单号
    private String entrustCode;
    private String entrustId;
    //申请单对象
    private NoStandardEntrustInfo entrustInfo;
    //任务书号
    private String taskNo;
    //任务名称
    private String taskName;
    //大纲主键
    private String testOutlineId;
    //大纲名称
    private String testOutlineName;
    //大纲版本号
    private String testOutlineVersion;
    //大纲版本主键
    private String tvId;
    //大纲类型
    private String testOutlineType;
    //上传大纲
    private String testOutlineFile;
    //上传大纲编号
    private String testOutlineFileCode;
    //上传大纲版本
    private String testOutlineFileVersion;
    //上传大纲名称
    private String testOutlineFileName;
    //输入大纲编号
    private String testOutlineCode;
    //输入大纲编号时版本
    private String testOutlineCodeVersion;
    //输入大纲编号是名称
    private String testOutlineCodeName;
    //选择系统大纲时编号
    private String testOutlineSysCode;
    //构型主键
    private String configId;
    //构型名称
    private String configName;
    //构型版本号
    private String configVersion;
    //构型版本主键
    private String cvId;
    //构型版本关联对象
    private ConfigurationVO configuration;
    //任务状态
    private String status;
    //资源分配ID
    private String resourceId;
    private List<NoStandardOtherUser> otherUsers;
    private String opUser;
    //试验负责人
    private Yuangong testManager;
    //试验日志
    private List<NoStandardTestLog> testLogList;
    //问题记录
    private List<NoStandardProblem> problemList;
    //试验项目
    private List<NoStandardExecutionItem> testItemList;
    /**
     * 任务开始时间
     */
    private Date startDate;
    /**
     * 任务完成时间
     */
    private Date completeDate;


    //视图传递对象
    private String report;
    private String img;
    private String file;
    private String problemId;
    private NoStandardProblem problem;
    private String testLogId;
    private NoStandardTestLog testLog;
    private String otherUserName;
    private String testManagerName;
    private String itemId;
    private NoStandardExecutionItem testItem;
    private String fileName;
    private MultipartFile excelFile;

    public NoStandardExecution(){
        super();
    }

    public NoStandardExecution(String id){
        super(id);
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTestOutlineName() {
        if(NoStandardTestOutlineTypeEnum.INPUT_CODE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineCodeName)){
            return testOutlineCodeName;
        }else if(NoStandardTestOutlineTypeEnum.UPLOAD_FILE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineFileName)){
            return testOutlineFileName;
        }else if(NoStandardTestOutlineTypeEnum.SYS.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineName)){
            return testOutlineName;
        }
        return "";
    }

    public void setTestOutlineName(String testOutlineName) {
        this.testOutlineName = testOutlineName;
    }

    public String getTestOutlineVersion() {
        if(NoStandardTestOutlineTypeEnum.INPUT_CODE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineCodeVersion)){
            return testOutlineCodeVersion;
        }else if(NoStandardTestOutlineTypeEnum.UPLOAD_FILE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineFileVersion)){
            return testOutlineFileVersion;
        }else if(NoStandardTestOutlineTypeEnum.SYS.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineVersion)){
            return testOutlineVersion;
        }
        return testOutlineVersion;
    }

    public void setTestOutlineVersion(String testOutlineVersion) {
        this.testOutlineVersion = testOutlineVersion;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public ConfigurationVO getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationVO configuration) {
        this.configuration = configuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NoStandardEntrustInfo getEntrustInfo() {
        return entrustInfo;
    }

    public void setEntrustInfo(NoStandardEntrustInfo entrustInfo) {
        this.entrustInfo = entrustInfo;
    }

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

    public String getTestOutlineId() {
        return testOutlineId;
    }

    public void setTestOutlineId(String testOutlineId) {
        this.testOutlineId = testOutlineId;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public NoStandardExecution setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public List<NoStandardExecutionItem> getTestItemList() {
        return testItemList;
    }

    public NoStandardExecution setTestItemList(List<NoStandardExecutionItem> testItemList) {
        this.testItemList = testItemList;
        return this;
    }

    public String getTestOutlineType() {
        return testOutlineType;
    }

    public NoStandardExecution setTestOutlineType(String testOutlineType) {
        this.testOutlineType = testOutlineType;
        return this;
    }

    public String getTestOutlineFile() {
        return testOutlineFile;
    }

    public NoStandardExecution setTestOutlineFile(String testOutlineFile) {
        this.testOutlineFile = testOutlineFile;
        return this;
    }

    public String getTestOutlineCode() {
        if(NoStandardTestOutlineTypeEnum.INPUT_CODE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineCode)){
            return testOutlineCode;
        }else if(NoStandardTestOutlineTypeEnum.UPLOAD_FILE.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineFileCode)){
            return testOutlineFileCode;
        }else if(NoStandardTestOutlineTypeEnum.SYS.getCode().equals(testOutlineType) &&
                StringUtils.isNotBlank(testOutlineSysCode)){
            return testOutlineSysCode;
        }

        return testOutlineCode;
    }

    public String getTestOutlineFileCode() {
        return testOutlineFileCode;
    }

    public NoStandardExecution setTestOutlineFileCode(String testOutlineFileCode) {
        this.testOutlineFileCode = testOutlineFileCode;
        return this;
    }

    public NoStandardExecution setTestOutlineCode(String testOutlineCode) {
        this.testOutlineCode = testOutlineCode;
        return this;
    }

    public String getCvId() {
        return cvId;
    }

    public NoStandardExecution setCvId(String cvId) {
        this.cvId = cvId;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public NoStandardExecution setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public String getTvId() {
        return tvId;
    }

    public NoStandardExecution setTvId(String tvId) {
        this.tvId = tvId;
        return this;
    }

    public String getReport() {
        return report;
    }

    public NoStandardExecution setReport(String report) {
        this.report = report;
        return this;
    }

    public String getImg() {
        return img;
    }

    public NoStandardExecution setImg(String img) {
        this.img = img;
        return this;
    }

    public String getFile() {
        return file;
    }

    public NoStandardExecution setFile(String file) {
        this.file = file;
        return this;
    }

    public String getProblemId() {
        return problemId;
    }

    public NoStandardExecution setProblemId(String problemId) {
        this.problemId = problemId;
        return this;
    }

    public String getTestLogId() {
        return testLogId;
    }

    public NoStandardExecution setTestLogId(String testLogId) {
        this.testLogId = testLogId;
        return this;
    }

    public NoStandardProblem getProblem() {
        return problem;
    }

    public NoStandardExecution setProblem(NoStandardProblem problem) {
        this.problem = problem;
        return this;
    }

    public NoStandardTestLog getTestLog() {
        return testLog;
    }

    public NoStandardExecution setTestLog(NoStandardTestLog testLog) {
        this.testLog = testLog;
        return this;
    }

    public Yuangong getTestManager() {
        return testManager;
    }

    public NoStandardExecution setTestManager(Yuangong testManager) {
        this.testManager = testManager;
        return this;
    }

    public List<NoStandardOtherUser> getOtherUsers() {
        return otherUsers;
    }

    public NoStandardExecution setOtherUsers(List<NoStandardOtherUser> otherUsers) {
        this.otherUsers = otherUsers;
        return this;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public NoStandardExecution setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
        return this;
    }

    public String getTestOutlineFileVersion() {
        return testOutlineFileVersion;
    }

    public NoStandardExecution setTestOutlineFileVersion(String testOutlineFileVersion) {
        this.testOutlineFileVersion = testOutlineFileVersion;
        return this;
    }

    public String getTestOutlineCodeVersion() {
        return testOutlineCodeVersion;
    }

    public NoStandardExecution setTestOutlineCodeVersion(String testOutlineCodeVersion) {
        this.testOutlineCodeVersion = testOutlineCodeVersion;
        return this;
    }

    public String getTestOutlineSysCode() {
        return testOutlineSysCode;
    }

    public NoStandardExecution setTestOutlineSysCode(String testOutlineSysCode) {
        this.testOutlineSysCode = testOutlineSysCode;
        return this;
    }

    public String getTestOutlineFileName() {
        return testOutlineFileName;
    }

    public NoStandardExecution setTestOutlineFileName(String testOutlineFileName) {
        this.testOutlineFileName = testOutlineFileName;
        return this;
    }

    public String getTestOutlineCodeName() {
        return testOutlineCodeName;
    }

    public NoStandardExecution setTestOutlineCodeName(String testOutlineCodeName) {
        this.testOutlineCodeName = testOutlineCodeName;
        return this;
    }

    public String getTestManagerName() {
        return testManagerName;
    }

    public NoStandardExecution setTestManagerName(String testManagerName) {
        this.testManagerName = testManagerName;
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public NoStandardExecution setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public NoStandardExecutionItem getTestItem() {
        return testItem;
    }

    public NoStandardExecution setTestItem(NoStandardExecutionItem testItem) {
        this.testItem = testItem;
        return this;
    }

    public String getOpUser() {
        return opUser;
    }

    public NoStandardExecution setOpUser(String opUser) {
        this.opUser = opUser;
        return this;
    }

    public boolean getOpFlag(){
        User user = UserUtils.getUser();
        if(null != testManager && user.getId().equals(testManager.getUserIds())){
            return true;
        }else if(StringUtils.isNotBlank(opUser)){
            String[] split = opUser.split(",");
            for(String s : split){
                if(s.equals(user.getId())){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    public boolean getAuditFlag(){
        User user = UserUtils.getUser();
        if(null != testManager && null != testManager.getUser() &&
                user.getId().equals(testManager.getUser().getId())){
            return true;
        }else{
            return false;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public NoStandardExecution setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public MultipartFile getExcelFile() {
        return excelFile;
    }

    public NoStandardExecution setExcelFile(MultipartFile excelFile) {
        this.excelFile = excelFile;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public NoStandardExecution setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public NoStandardExecution setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
        return this;
    }
}
