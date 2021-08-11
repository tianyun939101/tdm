package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

public class ExperimentAbility extends DataEntity<ExperimentAbility> {
       private String levell;
       private String status;




    public String getLevell() {
        return levell;
    }

    public void setLevell(String levell) {
        this.levell = levell;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
