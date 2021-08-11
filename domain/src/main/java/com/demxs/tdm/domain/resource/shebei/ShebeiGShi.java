package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 设备工时、故障率、利用率
 */
public class ShebeiGShi extends DataEntity<ShebeiGShi> {
    private String sheibeiId;

    private String quarter; //季度
    private String planHours; //计划工时
    private String practicalHours;  //实际工时
    private String userRatio;   //利用率
    private String failure; //故障率


    public String getSheibeiId() {
        return sheibeiId;
    }

    public void setSheibeiId(String sheibeiId) {
        this.sheibeiId = sheibeiId;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getPlanHours() {
        return planHours;
    }

    public void setPlanHours(String planHours) {
        this.planHours = planHours;
    }

    public String getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(String practicalHours) {
        this.practicalHours = practicalHours;
    }

    public String getUserRatio() {
        return userRatio;
    }

    public void setUserRatio(String userRatio) {
        this.userRatio = userRatio;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }
}
