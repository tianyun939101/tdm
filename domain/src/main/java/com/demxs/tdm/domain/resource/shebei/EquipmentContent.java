package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:25
 * @Description:
 */
public class EquipmentContent extends DataEntity<EquipmentContent> {
    private static final long serialVersionUID = 1L;

    /**
     * 维护id
     */
    private String defendId;
    /**
     * 维护时间
     */
    private String defendTime;
    /**
     * 维护内容
     */
    private String defendContent;

    //维护状态 01:未维护 02:已维护
    private String status;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDefendId() {
        return defendId;
    }

    public void setDefendId(String defendId) {
        this.defendId = defendId;
    }

    public String getDefendTime() {
        return defendTime;
    }

    public void setDefendTime(String defendTime) {
        this.defendTime = defendTime;
    }

    public String getDefendContent() {
        return defendContent;
    }

    public void setDefendContent(String defendContent) {
        this.defendContent = defendContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
