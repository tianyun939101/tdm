package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/9/3 13:33
 * @Description: 知识流程
 */
public class ZhiShiProcess extends DataEntity<ZhiShiProcess> {

    private static final long serialVersionUID = 1L;
    /**
     * 知识信息id
     * @see com.demxs.tdm.domain.resource.zhishi.ZhishiXx
     */
    private String defId;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 流程配置json字符串
     */
    private String json;
    /**
     * 原因
     */
    private String reason;
    /**
     * 知识类型id
     * @see com.demxs.tdm.domain.resource.zhishi.ZhishiLb
     */
    private String typeId;
    /**
     * 知识类型名称
     * @see com.demxs.tdm.domain.resource.zhishi.ZhishiLb
     */
    private String typeName;
    /**
     * 视图传递
     */
    private String model;
    private String modelId;
    private String defJson;

    public ZhiShiProcess() {
    }

    public ZhiShiProcess(String id) {
        super(id);
    }

    public String getDefId() {
        return defId;
    }

    public ZhiShiProcess setDefId(String defId) {
        this.defId = defId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ZhiShiProcess setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ZhiShiProcess setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getJson() {
        return json;
    }

    public ZhiShiProcess setJson(String json) {
        this.json = json;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public ZhiShiProcess setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public ZhiShiProcess setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public ZhiShiProcess setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ZhiShiProcess setModel(String model) {
        this.model = model;
        return this;
    }

    public String getModelId() {
        return modelId;
    }

    public ZhiShiProcess setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public String getDefJson() {
        return defJson;
    }

    public ZhiShiProcess setDefJson(String defJson) {
        this.defJson = defJson;
        return this;
    }
}
