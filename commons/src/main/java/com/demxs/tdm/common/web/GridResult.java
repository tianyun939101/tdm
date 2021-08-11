package com.demxs.tdm.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * easyui 数据表格数据封装
 */
public class GridResult<T> implements Serializable {
    private Long total;
    private List<T> rows;

    public GridResult() {
    }

    public GridResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonInclude
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
