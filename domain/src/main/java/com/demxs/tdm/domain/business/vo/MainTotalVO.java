package com.demxs.tdm.domain.business.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 首页统计VO.
 * User: wuliepeng
 * Date: 2017-12-15
 * Time: 上午10:11
 * @author wuliepeng
 */
public class MainTotalVO implements Serializable {
    private Integer total;  //总申请单数
    private Integer auditTotal; //审核中
    private Integer sampleConfirmTotal; //已确认样品
    private Integer goTotal;    //正在进行中的
    private Integer doneTotal;  //已经完成的
    private Integer noPlanTotal;  //未编制计划

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAuditTotal() {
        return auditTotal;
    }

    public void setAuditTotal(Integer auditTotal) {
        this.auditTotal = auditTotal;
    }

    public Integer getSampleConfirmTotal() {
        return sampleConfirmTotal;
    }

    public void setSampleConfirmTotal(Integer sampleConfirmTotal) {
        this.sampleConfirmTotal = sampleConfirmTotal;
    }

    public Integer getGoTotal() {
        return goTotal;
    }

    public void setGoTotal(Integer goTotal) {
        this.goTotal = goTotal;
    }

    public Integer getDoneTotal() {
        return doneTotal;
    }

    public void setDoneTotal(Integer doneTotal) {
        this.doneTotal = doneTotal;
    }

    public Integer getNoPlanTotal() {
        return noPlanTotal;
    }

    public void setNoPlanTotal(Integer noPlanTotal) {
        this.noPlanTotal = noPlanTotal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("total", total)
                .append("auditTotal", auditTotal)
                .append("sampleConfirmTotal", sampleConfirmTotal)
                .append("goTotal", goTotal)
                .append("doneTotal", doneTotal)
                .append("noPlanTotal", noPlanTotal)
                .toString();
    }
}
