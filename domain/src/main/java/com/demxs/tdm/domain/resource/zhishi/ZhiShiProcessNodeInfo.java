package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/9/4 10:11
 * @Description: 知识流程节点配置信息
 */
public class ZhiShiProcessNodeInfo extends DataEntity<ZhiShiProcessNodeInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 知识信息id
     * @see com.demxs.tdm.domain.resource.zhishi.ZhishiXx
     */
    private String defId;
    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 节点描述
     */
    private String description;
    /**
     * 关联文件id（质量体系、标准规范等等）
     * @see com.demxs.tdm.domain.resource.zhishi.ZhishiXx
     */
    private String relatedDocId;
    private ZhishiXx relatedDoc;

    public ZhiShiProcessNodeInfo() {
    }

    public ZhiShiProcessNodeInfo(String id) {
        super(id);
    }

    public String getDefId() {
        return defId;
    }

    public ZhiShiProcessNodeInfo setDefId(String defId) {
        this.defId = defId;
        return this;
    }

    public String getNodeId() {
        return nodeId;
    }

    public ZhiShiProcessNodeInfo setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ZhiShiProcessNodeInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getRelatedDocId() {
        return relatedDocId;
    }

    public ZhiShiProcessNodeInfo setRelatedDocId(String relatedDocId) {
        this.relatedDocId = relatedDocId;
        return this;
    }

    public ZhishiXx getRelatedDoc() {
        return relatedDoc;
    }

    public ZhiShiProcessNodeInfo setRelatedDoc(ZhishiXx relatedDoc) {
        this.relatedDoc = relatedDoc;
        return this;
    }
}
