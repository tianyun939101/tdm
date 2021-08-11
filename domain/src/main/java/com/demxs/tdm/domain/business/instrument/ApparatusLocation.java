package com.demxs.tdm.domain.business.instrument;

import com.demxs.tdm.common.persistence.TreeEntity;

/**
 * @author: Jason
 * @Date: 2020/5/6 16:55
 * @Description: 仪器仪表存方位置
 */
public class ApparatusLocation extends TreeEntity<ApparatusLocation> {

    private static final long serialVersionUID = 1L;
    /**
     * 父级id集合
     */
    private String parentIds;
    /**
     * 父级名称
     */
    private String parentName;

    public ApparatusLocation() {
        super();
    }

    public ApparatusLocation(String id) {
        super(id);
    }

    @Override
    public ApparatusLocation getParent() {
        return parent;
    }

    @Override
    public void setParent(ApparatusLocation parent) {
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

    public ApparatusLocation setParentName(String parentName) {
        this.parentName = parentName;
        return this;
    }
}
