package com.demxs.tdm.domain.dataCenter;

import java.util.List;

public class OaParent {

    private String size;

    private String count;

    private String stat;

    private String msg;

    private String type;

    private List<OaEntity> flowData;

    private String lable;

    private String moreUrl;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OaEntity> getFlowData() {
        return flowData;
    }

    public void setFlowData(List<OaEntity> flowData) {
        this.flowData = flowData;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }
}
