package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class AdjustPlanLog extends DataEntity<AdjustPlanLog> {


    private static final long serialVersionUID = 1L;

    private String entrustId;

    private String entrustCode;

    private String userName;

    private String adjustName;

    private Date adjustStartDate;

    private Date adjustEndDate;

    private Integer status;


    private String dateBetweens;

    private Date searchStartDate;

    private Date searchEndDate;

    private String userId;

    private String labId;

    public AdjustPlanLog() {
        super();
    }
    public AdjustPlanLog(EntrustInfo entrustInfo) {
        this.entrustCode = entrustInfo.getCode();
        this.entrustId = entrustInfo.getId();
        this.userName = entrustInfo.getUser()==null?"":entrustInfo.getUser().getName();
        this.status = entrustInfo.getStatus();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdjustName() {
        return adjustName;
    }

    public void setAdjustName(String adjustName) {
        this.adjustName = adjustName;
    }

    public Date getAdjustStartDate() {
        return adjustStartDate;
    }

    public void setAdjustStartDate(Date adjustStartDate) {
        this.adjustStartDate = adjustStartDate;
    }

    public Date getAdjustEndDate() {
        return adjustEndDate;
    }

    public void setAdjustEndDate(Date adjustEndDate) {
        this.adjustEndDate = adjustEndDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDateBetweens() {
        if(adjustEndDate==null || adjustStartDate==null){
            return "";
        }else{
            String s1 = DateUtils.formatDateByPattern(adjustStartDate,"yyyy-MM-dd HH:mm:ss");
            String s2 = DateUtils.formatDateByPattern(adjustEndDate,"yyyy-MM-dd HH:mm:ss");
            return  s1+" - "+s2;
        }

    }

    public void setDateBetweens(String dateBetweens) {
        this.dateBetweens = dateBetweens;
    }


    @JsonIgnore
    public Date getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(Date searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    @JsonIgnore
    public Date getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(Date searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
