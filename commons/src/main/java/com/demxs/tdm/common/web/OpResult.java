package com.demxs.tdm.common.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台操作提示类
 */
public class OpResult implements Serializable {
    private static final long serialVersionUID = -5217263946675824590L;
    public static final boolean OP_SUCCESS = true;
    public static final boolean OP_FAILED = false;
    private boolean status;//1 成功  0失败
    private String message;//提示信息
    private Object dataValue;//数据值

    public OpResult(){}

    public OpResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public static OpResult buildSuccess(){
        return new OpResult(OP_SUCCESS,"");
    }
    public static OpResult buildError(String message){
        return new OpResult(OP_FAILED,message);
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDataValue() {
        return dataValue;
    }

    public void setDataValue(Object dataValue) {
        Map<Object,Object> value = new HashMap<Object, Object>();
        if(dataValue instanceof com.demxs.tdm.common.persistence.Page){
            com.demxs.tdm.common.persistence.Page page = (com.demxs.tdm.common.persistence.Page) dataValue;
            value.put("page",new Page(page.getPageNo(),page.getPageSize(),page.getTotalPage(),page.getCount()));
            value.put("rows",page.getList());
            this.dataValue = value;
            return;
        }else if(dataValue instanceof java.util.List){
            value.put("rows",dataValue);
            this.dataValue = value;
            return;
        }
        this.dataValue = dataValue;
    }

    /**
     * 设置data，并返回当前对象，以支持链式调用
     * @param dataValue
     * @return
     */
    public OpResult setData(Object dataValue) {
        this.setDataValue(dataValue);
        return this;
    }

    /**
     * 添加data 项，并返回当前对象，以支持链式调用
     * @param key
     * @param value
     * @return
     */
    public OpResult addData(String key, Object value) {
        if (this.dataValue == null) {
            this.dataValue = new HashMap<Object, Object>();
        }
        ((Map<Object, Object>) this.dataValue).put(key, value);
        return this;
    }

    private class Page{
        Integer pageNo;
        Integer pageSize;
        Integer totalPage;
        Long totalCount;

        public Page() {
        }

        public Page(Integer pageNo, Integer pageSize, Integer totalPage,Long totalCount) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.totalPage = totalPage;
            this.totalCount = totalCount;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Long totalCount) {
            this.totalCount = totalCount;
        }

        public Integer getTotalPages() {
            return totalPage;
        }
    }

    public static final class OpMsg {
        public static final String SAVE_SUCCESS = "保存成功";
        public static final String SAVE_FAIL = "保存失败";
        public static final String MODIFY_SUCCESS = "修改成功";
        public static final String MODIFY_FAIL = "修改失败";
        public static final String DELETE_SUCCESS = "删除成功";
        public static final String DELETE_FAIL = "删除失败";
        public static final String OP_SUCCESS = "操作成功";
        public static final String OP_FAIL = "操作失败";

        public static final String DELETE_FAIL_JURI = "删除失败,只能编辑自己的数据！";
    }
}
