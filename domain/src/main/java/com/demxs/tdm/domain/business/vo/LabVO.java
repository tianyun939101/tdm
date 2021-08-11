package com.demxs.tdm.domain.business.vo;

import java.io.Serializable;

/**
 * 申请单选择试验室
 * User: wuliepeng
 * Date: 2017-12-14
 * Time: 下午5:29
 */
public class LabVO implements Serializable {
    private String labId;   //试验室ID
    private String labName; //试验室名称
    private String endTime; //最早结束时间
    private String cannotItem;  //无所完成试验项目
    private Integer waitEntrustCount;//排队申请单数量

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCannotItem() {
        return cannotItem;
    }

    public void setCannotItem(String cannotItem) {
        this.cannotItem = cannotItem;
    }

    public Integer getWaitEntrustCount() {
        return waitEntrustCount;
    }

    public void setWaitEntrustCount(Integer waitEntrustCount) {
        this.waitEntrustCount = waitEntrustCount;
    }
}
