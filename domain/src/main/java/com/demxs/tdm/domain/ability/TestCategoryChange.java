package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @Describe:
 * @Author:WuHui
 * @Date:14:20 2020/9/8
*/
public class TestCategoryChange extends DataEntity<TestCategoryChange> {

    private static final long serialVersionUID = 1L;

    private String vId; //版本编号
    private String cId; //能力编号
    private String parentId; //父级编号
    private String parentIds;//层级编号
    private String code;//编码
    private String name;//名称
    private String remarks;//备注
    private String standard;//标准
    private String rId;//申请编号
    private Integer type;//类型 0 新增 1修改 2 删除

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
