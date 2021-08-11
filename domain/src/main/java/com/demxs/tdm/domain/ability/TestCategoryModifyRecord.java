package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/8/3 18:02
 * @Description: 能力修改记录
 */
public class TestCategoryModifyRecord extends DataEntity<TestCategoryModifyRecord> {

    private static final long serialVersionUID = 1L;
    /**
     * 版本id
     */
    private String vId;
    /**
     * 修改申请id
     */
    private String rId;
    /**
     * 行为（0：新增，1：修改，2：删除）
     */
    private String action;
    public static final String INSERT = "0";
    public static final String UPDATE = "1";
    public static final String DELETE = "2";
    /**
     * 详细信息
     */
    private String info;
    /**
     * 试验能力id
     */
    private String cId;
    /**
     * 修改信息id
     */
    private String mId;
    /**
     * 状态（0：未应用，1：已应用，2：驳回）
     */
    private String status;

    public TestCategoryModifyRecord() {
    }

    public TestCategoryModifyRecord(String id) {
        super(id);
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryModifyRecord setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public String getrId() {
        return rId;
    }

    public TestCategoryModifyRecord setrId(String rId) {
        this.rId = rId;
        return this;
    }

    public String getAction() {
        return action;
    }

    public TestCategoryModifyRecord setAction(String action) {
        this.action = action;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public TestCategoryModifyRecord setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getcId() {
        return cId;
    }

    public TestCategoryModifyRecord setcId(String cId) {
        this.cId = cId;
        return this;
    }

    public String getmId() {
        return mId;
    }

    public TestCategoryModifyRecord setmId(String mId) {
        this.mId = mId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TestCategoryModifyRecord setStatus(String status) {
        this.status = status;
        return this;
    }
}
