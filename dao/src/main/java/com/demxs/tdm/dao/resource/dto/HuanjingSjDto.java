package com.demxs.tdm.dao.resource.dto;

/**
 * 环境数据统计数据
 * Created by zhangdengcai
 * Create date 2017/7/24.
 */
public class HuanjingSjDto {
    private String fangjianid;		// 房间id（设备存放位置的id）
    private String fangjian;		// 房间名称（设备存放位置的名称）
    private String shijian;		// 时间
    private String wendu;		// 温度
    private String shidu;		// 湿度
    private String wendusx;		// 温度上限
    private String wenduxx;		// 温度下限
    private String shidusx;		// 湿度上限
    private String shiduxx;		// 湿度下限

    public String getFangjianid() {
        return fangjianid;
    }

    public void setFangjianid(String fangjianid) {
        this.fangjianid = fangjianid;
    }

    public String getFangjian() {
        return fangjian;
    }

    public void setFangjian(String fangjian) {
        this.fangjian = fangjian;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
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
