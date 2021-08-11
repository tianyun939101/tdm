package com.demxs.tdm.common.dto;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/11/23 10:12
 **/
public class AbilityChart {
    private String name;
    private String cName;
    private String date;
    private String level;
    private String start;
    private Integer A = 0;
    private Integer B = 0;
    private Integer C = 0;
    private Integer D = 0;
    private String parent;

    List<AbilityChart> children;

    public AbilityChart() {
    }

    public AbilityChart(String name) {
        this.name = name;
    }

    public AbilityChart(Integer a, Integer b, Integer c, Integer d) {
        A = a;
        B = b;
        C = c;
        D = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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


    public List<AbilityChart> getChildren() {
        if(children == null){
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<AbilityChart> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
