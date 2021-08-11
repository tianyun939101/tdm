package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/8/18 16:37
 * @Description: 分中心
 */
public class SubCenter extends DataEntity<SubCenter> {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;

    private String sortType;

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public SubCenter() {
    }

    public SubCenter(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public SubCenter setName(String name) {
        this.name = name;
        return this;
    }
}
