package com.demxs.tdm.domain.reportstatement;

import java.io.Serializable;

public class LabAbilityCount  implements Serializable {
    private String name;
    private Integer A;
    private Integer B;
    private Integer C;
    private Integer D;
    private Integer total;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getA() {
        return A;
    }

    public void setA(Integer a) {
        A = a;
    }

    public Integer getB() {
        return B;
    }

    public void setB(Integer b) {
        B = b;
    }

    public Integer getC() {
        return C;
    }

    public void setC(Integer c) {
        C = c;
    }

    public Integer getD() {
        return D;
    }

    public void setD(Integer d) {
        D = d;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
