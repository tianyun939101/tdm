package com.demxs.tdm.domain.business.instrument;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DzInstrumentsApparatuses extends DataEntity<DzInstrumentsApparatuses> {

    private String id;

    private String categoryId;

    private String code;

    private String name;

    private String model;

    private String sN;

    private String manufactor;

    private String positionId;

    private String manageId;

    private String departments;

    //新增--量计有效日期

    private Date effectiveDate;

    private String lossUser;

    private Date lossDate;

    private User LossUserInfo;

    private String status;

    private String toolName;


    private String remarks;

    private String performanceIndex;    //性能指标

    private String subCenter;


    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    private String current;
    private String state;

    private String flagInfo;

    public Office getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(Office labInfo) {
        this.labInfo = labInfo;
    }

    private Office labInfo;

    private ApparatusCategory category;

    public String getLossUser() {
        return lossUser;
    }

    public User getLossUserInfo() {
        return LossUserInfo;
    }

    public void setLossUserInfo(User lossUserInfo) {
        LossUserInfo = lossUserInfo;
    }

    public void setLossUser(String lossUser) {
        this.lossUser = lossUser;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getFlagInfo() {
        return flagInfo;
    }

    public void setFlagInfo(String flagInfo) {
        this.flagInfo = flagInfo;
    }

    public ApparatusLocation getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(ApparatusLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    private ApparatusLocation storageLocation;

    public ApparatusCategory getCategory() {
        return category;
    }

    public void setCategory(ApparatusCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    private static final long serialVersionUID = 1L;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }


    public String getsN() {
        return sN;
    }

    public void setsN(String sN) {
        this.sN = sN == null ? null : sN.trim();
    }


    public String getManufactor() {
        return manufactor;
    }


    public void setManufactor(String manufactor) {
        this.manufactor = manufactor == null ? null : manufactor.trim();
    }


    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    public String getManageId() {
        return manageId;
    }


    public void setManageId(String manageId) {
        this.manageId = manageId == null ? null : manageId.trim();
    }


    public String getDepartments() {
        return departments;
    }


    public void setDepartments(String departments) {
        this.departments = departments == null ? null : departments.trim();
    }

    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }


    public String getPerformanceIndex() {
        return performanceIndex;
    }

    public void setPerformanceIndex(String performanceIndex) {
        this.performanceIndex = performanceIndex;
    }
}