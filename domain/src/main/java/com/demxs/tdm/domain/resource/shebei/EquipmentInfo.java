package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * @author: zwm
 * @Date: 2020/11/3 14:25
 * @Description:
 */
public class EquipmentInfo extends DataEntity<EquipmentInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private String equipmentId;
    /**
     * 维护年份
     */
    private String defendYear;
    /**
     * 维护周期
     */
    private String defendPeriod;
    /**
     *  维护提醒
     */
    private Integer defendWarn;

    private List<EquipmentContent> equipmentContentList;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDefendYear() {
        return defendYear;
    }

    public void setDefendYear(String defendYear) {
        this.defendYear = defendYear;
    }

    public String getDefendPeriod() {
        return defendPeriod;
    }

    public void setDefendPeriod(String defendPeriod) {
        this.defendPeriod = defendPeriod;
    }

    public Integer getDefendWarn() {
        return defendWarn;
    }

    public void setDefendWarn(Integer defendWarn) {
        this.defendWarn = defendWarn;
    }

    public List<EquipmentContent> getEquipmentContentList() {
        return equipmentContentList;
    }

    public void setEquipmentContentList(List<EquipmentContent> equipmentContentList) {
        this.equipmentContentList = equipmentContentList;
    }
}
