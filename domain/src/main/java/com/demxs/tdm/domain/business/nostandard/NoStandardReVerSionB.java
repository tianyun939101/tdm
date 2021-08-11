package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;

import java.util.List;

public class NoStandardReVerSionB extends DataEntity<NoStandardReVerSionB> {
    /**
     * 申请人
     */
    private String applyUserId;
    private User appluUser;
    /**
     * 申请单号
     */
    private String code;
    /**
     *试验名称
     */
    private String testName;
    /**
     * 试验性质
     */
    private String testType;
    /**
     * 飞机型号
     */
    private String flyType;
    /**
     *任务书编号
     */
    private String assBookNo;
    /**
     *试验负责人
     */
    private String testPrincipalUserId;
    private Yuangong testPrincipalUser;
    /**
     * 其他人员
     * */
    private String otherUsersId;
    private List<NoStandardOtherUser> otherUsers;
    //试验审批表
    private ExaminationApply ex;
    //实验前检查大纲
    private CheckOutline ol;
    //试验系统调试记录
    private TestingRecord rec;

     private List<MeasureCheck> me;

    /**
     * 检查结果
     */
     private String checkResult;
    /**
     * 纠正情况
     */
    private String correctSi;
    /**
     * 编制人
     */
    private String authorizedId;
    private User authorizedUser;
    /**
     * 审核
     */
    private String meapplyUserId;
    private User meapplyUser;
    /**
     * 检查
     */
    private String checkUserId;
    private User checkUser;
    /**
     * 审批状态
     */
    private String auditType;


    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestPrincipalUserId() {
        return testPrincipalUserId;
    }

    public void setTestPrincipalUserId(String testPrincipalUserId) {
        this.testPrincipalUserId = testPrincipalUserId;
    }

    public Yuangong getTestPrincipalUser() {
        return testPrincipalUser;
    }

    public void setTestPrincipalUser(Yuangong testPrincipalUser) {
        this.testPrincipalUser = testPrincipalUser;
    }

    public List<NoStandardOtherUser> getOtherUsers() {
        return otherUsers;
    }

    public void setOtherUsers(List<NoStandardOtherUser> otherUsers) {
        this.otherUsers = otherUsers;
    }

    public ExaminationApply getEx() {
        return ex;
    }

    public void setEx(ExaminationApply ex) {
        this.ex = ex;
    }

    public CheckOutline getOl() {
        return ol;
    }

    public void setOl(CheckOutline ol) {
        this.ol = ol;
    }

    public TestingRecord getRec() {
        return rec;
    }

    public void setRec(TestingRecord rec) {
        this.rec = rec;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public User getAppluUser() {
        return appluUser;
    }

    public void setAppluUser(User appluUser) {
        this.appluUser = appluUser;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getFlyType() {
        return flyType;
    }

    public void setFlyType(String flyType) {
        this.flyType = flyType;
    }

    public String getAssBookNo() {
        return assBookNo;
    }

    public void setAssBookNo(String assBookNo) {
        this.assBookNo = assBookNo;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getOtherUsersId() {
        return otherUsersId;
    }

    public void setOtherUsersId(String otherUsersId) {
        this.otherUsersId = otherUsersId;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MeasureCheck> getMeasure() {
        return me;
    }

    public void setMeasure(List<MeasureCheck> me) {
        this.me = me;
    }

    public List<MeasureCheck> getMe() {
        return me;
    }

    public void setMe(List<MeasureCheck> me) {
        this.me = me;
    }

    public String getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        this.authorizedId = authorizedId;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public String getMeapplyUserId() {
        return meapplyUserId;
    }

    public void setMeapplyUserId(String meapplyUserId) {
        this.meapplyUserId = meapplyUserId;
    }

    public User getMeapplyUser() {
        return meapplyUser;
    }

    public void setMeapplyUser(User meapplyUser) {
        this.meapplyUser = meapplyUser;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public User getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(User checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getCorrectSi() {
        return correctSi;
    }

    public void setCorrectSi(String correctSi) {
        this.correctSi = correctSi;
    }
}
