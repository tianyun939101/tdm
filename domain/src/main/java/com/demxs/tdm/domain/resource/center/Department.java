package com.demxs.tdm.domain.resource.center;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.ability.TestCategory;

import java.util.List;

public class Department extends TreeEntity<Department> {
    //编号
    private String code;
    //组织ID
    private Office org;
    //负责人
    private User charge;
    //关键职责(一级)
    private String duty;
    //关键职责(二级)
    private String secDuty;
    //类型,1.中心，2.部门，3.科室，4.岗位
    private String type;

    /**
     * 负责人集合ID
     */
    private String chargeIds;

    /**
     * 负责人集合
     */
    private List<User> chargeList;
    /**
     * 负责人名称
     */
    private String chargeName;

    public String getChargeIds() {
        return chargeIds;
    }

    public void setChargeIds(String chargeIds) {
        this.chargeIds = chargeIds;
    }

    public List<User> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<User> chargeList) {
        this.chargeList = chargeList;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Department() {
    }

    public Department(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Office getOrg() {
        return org;
    }

    public void setOrg(Office org) {
        this.org = org;
    }

    public User getCharge() {
        return charge;
    }

    public void setCharge(User charge) {
        this.charge = charge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getSecDuty() {
        return secDuty;
    }

    public void setSecDuty(String secDuty) {
        this.secDuty = secDuty;
    }

    @Override
    public Department getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Department parent) {
        this.parent = parent;
    }
}
