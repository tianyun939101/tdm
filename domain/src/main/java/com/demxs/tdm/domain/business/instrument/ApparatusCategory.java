package com.demxs.tdm.domain.business.instrument;

import com.demxs.tdm.common.persistence.TreeEntity;

/**
 * @author: Jason
 * @Date: 2020/5/6 16:41
 * @Description: 仪器仪表分类
 */
public class ApparatusCategory extends TreeEntity<ApparatusCategory> {

    private static final long serialVersionUID = 1L;
    /**
     * 父级id集合
     */
    private String parentIds;
    /**
     * 父级名称
     */
    private String parentName;

    public ApparatusCategory() {
        super();
    }

    public ApparatusCategory(String id) {
        super(id);
    }

    @Override
    public ApparatusCategory getParent() {
        return parent;
    }

    @Override
    public void setParent(ApparatusCategory parent) {
        this.parent = parent;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getParentName() {
        return parentName;
    }

    public ApparatusCategory setParentName(String parentName) {
        this.parentName = parentName;
        return this;
    }
}
