package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 试验室能力迁移
 * @author wuhui
 * @date 2020/12/16 18:55
 **/
public class AbilityRemoval extends DataEntity<AbilityRemoval> {
    //能力版本编号
    private String vId;
    //能力编号
    private String cId;
    //变更前试验室
    private String beforeLabId;
    //变更后试验室
    private String afterLabId;
    //迁移类型 01:拆分 02:复制
    private String type;

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getBeforeLabId() {
        return beforeLabId;
    }

    public void setBeforeLabId(String beforeLabId) {
        this.beforeLabId = beforeLabId;
    }

    public String getAfterLabId() {
        return afterLabId;
    }

    public void setAfterLabId(String afterLabId) {
        this.afterLabId = afterLabId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
