package com.demxs.tdm.domain.business.testoutline;

import com.demxs.tdm.common.persistence.DataEntity;

public class TestOutlineReport extends DataEntity<TestOutlineReport> {

    private static final long serialVersionUID = 1L;

    //版本id
    private String tvId;

    public TestOutlineReport(){
        super();
    }

    public TestOutlineReport(String id){
        super(id);
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }
}
