package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * ATA章节实体类
 */
public class ATAChapter extends TreeEntity<ATAChapter> {

    private static final long serialVersionUID = 1L;
    //章节名称
    private String name;
    //英文名称
    private String ename;

    private String parentId;

    private ATAChapter parent;

    private String code;

    private String model;

    public ATAChapter(){
        super();
    }

    public ATAChapter(String id){
        super(id);
    }

    @Override
    @JsonBackReference
    public ATAChapter getParent() {
        return parent;
    }

    @Override
    @Length(min = 0, max = 2000, message = "parent_ids长度必须介于 0 和 2000 之间")
    public void setParent(ATAChapter parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
