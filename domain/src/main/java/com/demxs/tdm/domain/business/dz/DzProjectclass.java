package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class DzProjectclass  extends TreeEntity<DzProjectclass> {
    
    private String id;

    
    private String name;

    
    private String parentId;

    
    private String parentIds;

    
    private String createBy;

    
    private Date createDate;

    
    private String updateBy;

    
    private Date updateDate;

    
    private String state;

    
    private static final long serialVersionUID = 1L;

    public DzProjectclass(){
        super();
    }

    public DzProjectclass(String id){
        super(id);
    }

    @Override
    @JsonBackReference
    public DzProjectclass getParent() {
        return parent;
    }

    @Override
    @Length(min = 0, max = 2000, message = "parent_ids长度必须介于 0 和 2000 之间")
    public void setParent(DzProjectclass parent) {
        this.parent = parent;
    }

    
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



    
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    
    public Date getCreateDate() {
        return createDate;
    }

    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
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