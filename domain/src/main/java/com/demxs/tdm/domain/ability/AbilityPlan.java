package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 能力规划
 * @author wuhui
 * @date 2020/12/15 16:19
 **/
public class AbilityPlan extends DataEntity<AbilityPlan> {
    private String name;//规划名称
    private String enable;//当前规划  0：否  1：是

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
