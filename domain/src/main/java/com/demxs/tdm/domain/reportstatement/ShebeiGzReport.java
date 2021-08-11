package com.demxs.tdm.domain.reportstatement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShebeiGzReport implements Serializable {

    private String name;

    private String code;

    private String total;

    private String allTotal;

    private String rate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(String allTotal) {
        this.allTotal = allTotal;
    }

    public String getRate() {
        BigDecimal all = new BigDecimal(allTotal);
        if(all.compareTo(BigDecimal.ZERO)==0){
            return "0";
        }else{
            BigDecimal comP = new BigDecimal(total).multiply(new BigDecimal(100));
            return comP.divide(all,2, RoundingMode.HALF_UP).toString();
        }

    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
