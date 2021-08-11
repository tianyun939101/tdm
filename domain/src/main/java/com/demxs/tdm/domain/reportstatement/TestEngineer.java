package com.demxs.tdm.domain.reportstatement;

import java.io.Serializable;
import java.util.Date;

public class TestEngineer implements Serializable {


    private String enrustId;

    private String entrustCode;

    private Date completeDate;

    private Date planDate;

    public String getEnrustId() {
        return enrustId;
    }

    public void setEnrustId(String enrustId) {
        this.enrustId = enrustId;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
}
