package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 试验序列详情Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestSequenceItem extends DataEntity<TestSequenceItem> {
	
	private static final long serialVersionUID = 1L;
	private String nodeId;      //节点ID，parent.id 指向此ID
	private String seqId;		// 试验序列ID
	private TestSequenceItem parent;		// 上级分支ID
	private String parentIds;		// 所有上级分支ID
	private String itemId;		// 试验项目ID
	private TestItem item;
	private String name;		// 分支名称
	private String ename;		// 英文名称
	private String flag;		// 是否分支
	private Integer sort;		// 序号
	
	public TestSequenceItem() {
		super();
	}

	public TestSequenceItem(String id){
		super(id);
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Length(min=0, max=64, message="试验序列ID长度必须介于 0 和 64 之间")
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	@JsonBackReference
	public TestSequenceItem getParent() {
		return parent;
	}

	public void setParent(TestSequenceItem parent) {
		this.parent = parent;
		if(parent != null){
			String parentParentIds = parent.getParentIds();
			if (StringUtils.isEmpty(parentParentIds)) {
				parentParentIds = "0";
			}
			this.setParentIds(parentParentIds +","+parent.getNodeId()+",");
		}
	}
	
	@Length(min=0, max=2000, message="所有上级分支ID长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		if (item != null) {
			return item.getId();
		}
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public TestItem getItem() {
		if (item == null && StringUtils.isNotEmpty(itemId)) {
			item = new TestItem();
			item.setId(itemId);
		}
		return item;
	}

	public void setItem(TestItem item) {
		this.item = item;
	}

	@Length(min=0, max=200, message="分支名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="英文名称长度必须介于 0 和 200 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=1, message="是否分支长度必须介于 0 和 1 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}