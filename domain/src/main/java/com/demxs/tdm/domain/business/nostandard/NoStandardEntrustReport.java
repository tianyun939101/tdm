package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.NoStandardReportEnum;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/9 18:25
 * @Description:非标报告entity
 */

public class NoStandardEntrustReport extends DataEntity<NoStandardEntrustReport> {

    private static final long serialVersionUID = 1L;
    //申请单编号
    private String entrustCode;
    //申请单id
    private String entrustId;
    //申请单对象
    private NoStandardEntrustInfo entrustInfo;
    //申请人id
    private String clientId;
    //申请人
    private User client;
    //试验负责人id
    private String testManagerId;
    //试验负责人名称
    private String testManagerName;
    //试验性质
    private String testNature;
    //状态
    private String status;
    //处理人
    private String opUser;
    private List<User> otherUsers;
    //编号
    private String code;
    //审核人id
    private String auditUserId;
    //审核人名称
    private String auditUserName;
    //批准人
    private String approvalUserId;
    //批准人名称
    private String approvalUserName;
    //文件
    private String file;
    //试验执行id
    private String executionId;
    //关联试验执行对象
    private NoStandardExecution execution;
    //审核信息
    private List<AuditInfo> auditInfoList;
    //关联试验项目
    private List<NoStandardExecutionItem> testItemList;
    //关联试验问题
    private List<NoStandardProblem> problemList;
    //关联试验日志
    private List<NoStandardTestLog> testLogList;
    //当前审核人
    private String curAuditUser;
    /**
     * 转换后的pdf
     */
    private String pdfFilePath;

    //视图传递对象
    private AuditInfo auditInfo;

    public NoStandardEntrustReport(){
        super();
    }

    public NoStandardEntrustReport(String id){
        super(id);
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public NoStandardEntrustReport setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
        return this;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public NoStandardEntrustReport setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public NoStandardEntrustInfo getEntrustInfo() {
        return entrustInfo;
    }

    public NoStandardEntrustReport setEntrustInfo(NoStandardEntrustInfo entrustInfo) {
        this.entrustInfo = entrustInfo;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public NoStandardEntrustReport setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public User getClient() {
        return client;
    }

    public NoStandardEntrustReport setClient(User client) {
        this.client = client;
        return this;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public NoStandardEntrustReport setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
        return this;
    }

    public String getTestManagerName() {
        return testManagerName;
    }

    public NoStandardEntrustReport setTestManagerName(String testManagerName) {
        this.testManagerName = testManagerName;
        return this;
    }

    public String getTestNatrue() {
        return testNature;
    }

    public NoStandardEntrustReport setTestNatrue(String testNatrue) {
        this.testNature = testNatrue;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NoStandardEntrustReport setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOpUser() {
        StringBuilder sb = new StringBuilder();
        if(NoStandardReportEnum.EDIT.getCode().equals(status) || NoStandardReportEnum.REJECT.getCode().equals(status)){
            String[] split = opUser.split(",");
            for(int i = 0; i < split.length ; i++){
                User user = UserUtils.get(split[i]);
                if(null != user){
                    sb.append(user.getName()).append(",");
                }
            }
        }else if(NoStandardReportEnum.TO_BE_AUDITED.getCode().equals(status)){
            String[] split = auditUserId.split(",");
            for(int i = 0; i < split.length ; i++){
                User user = UserUtils.get(split[i]);
                if(null != user){
                    sb.append(user.getName()).append(",");
                }
            }

        }else if(NoStandardReportEnum.TO_BE_APPROVED.getCode().equals(status)){
            User user = UserUtils.get(approvalUserId);
            if(null != user){
                sb.append(user.getName()).append(",");
            }
        }

        if(sb.length() > 0){
            sb.deleteCharAt(sb.length()-1);
        }

        return sb.toString();
    }

    public NoStandardEntrustReport setOpUser(String opUser) {
        this.opUser = opUser;
        return this;
    }

    public String getCode() {
        return code;
    }

    public NoStandardEntrustReport setCode(String code) {
        this.code = code;
        return this;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public NoStandardEntrustReport setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
        return this;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public NoStandardEntrustReport setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
        return this;
    }

    public String getApprovalUser() {
        return approvalUserId;
    }

    public NoStandardEntrustReport setApprovalUser(String approvalUser) {
        this.approvalUserId = approvalUser;
        return this;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public NoStandardEntrustReport setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
        return this;
    }

    public String getTestNature() {
        return testNature;
    }

    public NoStandardEntrustReport setTestNature(String testNature) {
        this.testNature = testNature;
        return this;
    }

    public String getApprovalUserId() {
        return approvalUserId;
    }

    public NoStandardEntrustReport setApprovalUserId(String approvalUserId) {
        this.approvalUserId = approvalUserId;
        return this;
    }

    public String getFile() {
        return file;
    }

    public NoStandardEntrustReport setFile(String file) {
        this.file = file;
        return this;
    }

    public List<User> getOtherUsers() {
        return otherUsers;
    }

    public NoStandardEntrustReport setOtherUsers(List<User> otherUsers) {
        this.otherUsers = otherUsers;
        return this;
    }

    public List<NoStandardExecutionItem> getTestItemList() {
        return testItemList;
    }

    public NoStandardEntrustReport setTestItemList(List<NoStandardExecutionItem> testItemList) {
        this.testItemList = testItemList;
        return this;
    }

    public String getExecutionId() {
        return executionId;
    }

    public NoStandardEntrustReport setExecutionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public List<NoStandardProblem> getProblemList() {
        return problemList;
    }

    public NoStandardEntrustReport setProblemList(List<NoStandardProblem> problemList) {
        this.problemList = problemList;
        return this;
    }

    public List<NoStandardTestLog> getTestLogList() {
        return testLogList;
    }

    public NoStandardEntrustReport setTestLogList(List<NoStandardTestLog> testLogList) {
        this.testLogList = testLogList;
        return this;
    }

    public NoStandardExecution getExecution() {
        return execution;
    }

    public NoStandardEntrustReport setExecution(NoStandardExecution execution) {
        this.execution = execution;
        return this;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public NoStandardEntrustReport setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
        return this;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public NoStandardEntrustReport setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public String getCurAuditUser() {
        return curAuditUser;
    }

    public NoStandardEntrustReport setCurAuditUser(String curAuditUser) {
        this.curAuditUser = curAuditUser;
        return this;
    }

    public boolean getEditFlag(){
        User user = UserUtils.getUser();
        if(StringUtils.isNotBlank(opUser)){
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
        if(StringUtils.isNotBlank(auditUserId)){
            String[] split = auditUserId.split(",");
            for(String s : split){
                if(s.equals(user.getId())){
                    return true;
                }
            }
            return false;
        }else {
            return false;
        }
    }

    public boolean getApprovalFlag(){
        User user = UserUtils.getUser();
        if(user.getId().equals(approvalUserId)) {
            return true;
        }else{
            return false;
        }
    }

    public String getOpUserId() {
        return opUser;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public NoStandardEntrustReport setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
        return this;
    }
}
