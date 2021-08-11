package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.ATAChapter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 试飞架次信息实体类
 */
public class FlightTestInfo extends DataEntity<FlightTestInfo>{

    private static final long serialVersionUID = 1L;

    private String flightTestInfo;//试飞架次信息

    private String productModel;//飞机型号

    private Date flightTestDate;//试飞时间

    private String dataDescription;//数据说明

    private String dataFile;//数据上传

    private String ataChapter;//ATA章节

    private Date permValidDate;//查看权限有效日期

    private List<ATAChapter> ataList;//ATA章节信息

    private List<User> reportUser;//操作权限用户集合

    private List<Office> reportOffice;//操作权限部部门集合

    private List<User> searchUser;//查看权限用户集合

    private List<Office> searchOffice;//查看权限部部门集合

    private String reportUserStr;//操作权限用户Str

    private String reportOfficeStr;//操作权限部部门Str

    private String searchUserStr;//查看权限用户Str

    private String searchOfficeStr;//查看权限部部门Str

    private User searchBy;//查看权限部部门Str

    private String permissionType;//权限类型

    public String getFlightTestInfo() {
        return flightTestInfo;
    }

    public void setFlightTestInfo(String flightTestInfo) {
        this.flightTestInfo = flightTestInfo;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getFlightTestDate() {
        return flightTestDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setFlightTestDate(Date flightTestDate) {
        this.flightTestDate = flightTestDate;
    }

    public String getDataDescription() {
        return dataDescription;
    }

    public void setDataDescription(String dataDescription) {
        this.dataDescription = dataDescription;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getAtaChapter() {
        return ataChapter;
    }

    public void setAtaChapter(String ataChapter) {
        this.ataChapter = ataChapter;
    }

    public List<ATAChapter> getAtaList() {
        return ataList;
    }

    public void setAtaList(List<ATAChapter> ataList) {
        this.ataList = ataList;
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

    public Date getPermValidDate() {
        return permValidDate;
    }

    public void setPermValidDate(Date permValidDate) {
        this.permValidDate = permValidDate;
    }

    public User getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(User searchBy) {
        this.searchBy = searchBy;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }
}