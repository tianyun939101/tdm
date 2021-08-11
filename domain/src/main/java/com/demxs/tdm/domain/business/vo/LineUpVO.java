package com.demxs.tdm.domain.business.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: wuliepeng
 * Date: 2018-01-27
 * Time: 上午9:23
 */
public class LineUpVO implements Serializable {
    private String labId;
    private Integer total;

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
