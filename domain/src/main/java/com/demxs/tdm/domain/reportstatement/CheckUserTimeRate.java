package com.demxs.tdm.domain.reportstatement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckUserTimeRate implements Serializable {

    private String checkUser;//试验负责人

    private String checkUserName;//试验负责人名称

    private String totalEntrust;//申请单数量

    private String comNum;//完成数量

    private String noComNum;//未完成

    private String rate;//及时率

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getTotalEntrust() {
        return totalEntrust;
    }

    public void setTotalEntrust(String totalEntrust) {
        this.totalEntrust = totalEntrust;
    }

    public String getComNum() {
        return comNum;
    }

    public void setComNum(String comNum) {
        this.comNum = comNum;
    }

    public String getNoComNum() {
        return noComNum;
    }

    public void setNoComNum(String noComNum) {
        this.noComNum = noComNum;
    }

    public String getRate() {

        BigDecimal comP = new BigDecimal(comNum).multiply(new BigDecimal(100));
        BigDecimal all = new BigDecimal(totalEntrust);

        return comP.divide(all,2, RoundingMode.HALF_UP).toString();
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
