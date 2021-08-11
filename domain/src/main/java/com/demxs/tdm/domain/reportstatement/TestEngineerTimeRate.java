package com.demxs.tdm.domain.reportstatement;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestEngineerTimeRate implements Serializable {


    private String checkUser;//试验负责人

    private String checkUserName;//试验负责人名称

    private Integer totalEntrust;//申请单数量

    private Integer comNum;//完成数量

    private Integer noComNum;//未完成

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

    public Integer getTotalEntrust() {
        return totalEntrust;
    }

    public void setTotalEntrust(Integer totalEntrust) {
        this.totalEntrust = totalEntrust;
    }

    public Integer getComNum() {
        return comNum;
    }

    public void setComNum(Integer comNum) {
        this.comNum = comNum;
    }

    public Integer getNoComNum() {
        return noComNum;
    }

    public void setNoComNum(Integer noComNum) {
        this.noComNum = noComNum;
    }

    public String getRate() {

        if(comNum>0){
            BigDecimal comP = new BigDecimal(comNum).multiply(new BigDecimal(100));
            BigDecimal all = new BigDecimal(totalEntrust);

            return comP.divide(all,2, RoundingMode.HALF_UP).toString();
        }else{
            return "0";
        }

    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
