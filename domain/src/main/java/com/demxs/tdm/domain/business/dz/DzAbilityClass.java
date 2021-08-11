package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class DzAbilityClass extends TreeEntity<DzAbilityClass> {
    
    private String id;
    
    private String name;

    private String ename;

    private String code;

    private DzAbilityClass parent;

    public DzAbilityClass(){
        super();
    }

    public DzAbilityClass(String id){
        super(id);
    }

    @Override
    @JsonBackReference
    public DzAbilityClass getParent() {
        return parent;
    }

    @Override
    @Length(min = 0, max = 2000, message = "parent_ids长度必须介于 0 和 2000 之间")
    public void setParent(DzAbilityClass parent) {
        this.parent = parent;
    }

    private String parentId;

    private String parentIds;

    private Date createDate;

    private Date updateDate;

    private String state;
    
    private static final long serialVersionUID = 1L;
    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
    @Override
    public String getParentIds() {
        return parentIds;
    }
    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}