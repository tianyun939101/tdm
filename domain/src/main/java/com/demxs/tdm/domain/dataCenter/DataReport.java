package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;

import java.util.List;

/**
 * 数据提报
 */
public class DataReport extends DataEntity<DataReport>{

    private static final long serialVersionUID = 1L;

    private String testName;//试验项目名称

    private String testNature;//试验性质

    private String productModel;//产品型号

    private String testPurpose;//试验目的

    private String testData;//试验数据清单

    private String status;//状态

    private String dataFile;//数据文件

    private String viewUserStr;

    private String operatingUserStr;

    private List<User> operatingUserList;

    private List<User> viewUserList;

    private List<AuditInfo> AuditInfos;

    private String searchUserId;

    private String type;

    /**
     * 审核人
     */
    private User auditUser;
    /**
     * 下载历史记录
     */
    private List<DataReportDownloadHistory> downloadHistoryList;

    private List<DataReportPermission> dataReportPermissions;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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

    public String getTestPurpose() {
        return testPurpose;
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getViewUserStr() {
        return viewUserStr;
    }

    public void setViewUserStr(String viewUserStr) {
        this.viewUserStr = viewUserStr;
    }

    public String getOperatingUserStr() {
        return operatingUserStr;
    }

    public void setOperatingUserStr(String operatingUserStr) {
        this.operatingUserStr = operatingUserStr;
    }

    public List<User> getOperatingUserList() {
        return operatingUserList;
    }

    public void setOperatingUserList(List<User> operatingUserList) {
        this.operatingUserList = operatingUserList;
    }

    public List<User> getViewUserList() {
        return viewUserList;
    }

    public void setViewUserList(List<User> viewUserList) {
        this.viewUserList = viewUserList;
    }

    public List<AuditInfo> getAuditInfos() {
        return AuditInfos;
    }

    public void setAuditInfos(List<AuditInfo> auditInfos) {
        AuditInfos = auditInfos;
    }

    public String getSearchUserId() {
        return searchUserId;
    }

    public void setSearchUserId(String searchUserId) {
        this.searchUserId = searchUserId;
    }

    public List<DataReportPermission> getDataReportPermissions() {
        return dataReportPermissions;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public void setDataReportPermissions(List<DataReportPermission> dataReportPermissions) {
        this.dataReportPermissions = dataReportPermissions;
    }

    public String getOpUser() {
        if (DataCenterConstants.DataReportStatus.audit.equals(this.getStatus())) {
            /*User user = UserUtils.get(this.createBy.getId());
            return user==null?"":user.getOffice().getPrimaryPerson().getName();*/
            if(null != this.auditUser && StringUtils.isNotBlank(this.auditUser.getName())){
                return this.auditUser.getName();
            }
        }
        return "";
    }

    public String getApplyUser() {
        User user = UserUtils.get(this.createBy.getId());
        return user==null?"":user.getName();
    }

    public Boolean getAuditFlag() {
        if (DataCenterConstants.DataReportStatus.audit.equals(this.getStatus()) && null != this.auditUser) {
            User user = UserUtils.get(this.auditUser.getId());
            if(UserUtils.getUser().getId().equals(user.getId())){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public DataReport setAuditUser(User auditUser) {
        this.auditUser = auditUser;
        return this;
    }

    public List<DataReportDownloadHistory> getDownloadHistoryList() {
        return downloadHistoryList;
    }

    public DataReport setDownloadHistoryList(List<DataReportDownloadHistory> downloadHistoryList) {
        this.downloadHistoryList = downloadHistoryList;
        return this;
    }
}