package com.demxs.tdm.dao.resource.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdengcai
 * Create Data 2017/7/31.
 */
public class HuanjingSjFinalDto {
    private String fangjian;
    private String wendusx;		// 温度上限
    private String wenduxx;		// 温度下限
    private String shidusx;		// 湿度上限
    private String shiduxx;		// 湿度下限
    private List<Map<String, Object>> shuju = new ArrayList<Map<String, Object>>();

    public List<Map<String, Object>> getShuju() {
        return shuju;
    }

    public void setShuju(List<Map<String, Object>> shuju) {
        this.shuju = shuju;
    }

    public String getFangjian() {
        return fangjian;
    }

    public void setFangjian(String fangjian) {
        this.fangjian = fangjian;
    }

    public String getWendusx() {
        return wendusx;
    }

    public void setWendusx(String wendusx) {
        this.wendusx = wendusx;
    }

    public String getWenduxx() {
        return wenduxx;
    }

    public void setWenduxx(String wenduxx) {
        this.wenduxx = wenduxx;
    }

    public String getShidusx() {
        return shidusx;
    }

    public void setShidusx(String shidusx) {
        this.shidusx = shidusx;
    }

    public String getShiduxx() {
        return shiduxx;
    }

    public void setShiduxx(String shiduxx) {
        this.shiduxx = shiduxx;
    }
}
