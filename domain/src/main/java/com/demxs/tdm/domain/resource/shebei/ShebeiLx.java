package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 设备类型Entity
 * @author zhangdengcai
 * @version 2017-06-15
 */
public class ShebeiLx extends TreeEntity<ShebeiLx> {

	private static final long serialVersionUID = 1L;
	private String leixingmc;        // 类型名称
	private String youxiaox;        // 有效性
	private ShebeiLx parent;        // parent_id
	private String parentIds;        // parent_ids

	private String name;

	public ShebeiLx() {
		super();
	}

	public ShebeiLx(String id) {
		super(id);
	}

	@Length(min = 0, max = 200, message = "类型名称长度必须介于 0 和 200 之间")
	public String getLeixingmc() {
		return leixingmc;
	}

	public void setLeixingmc(String leixingmc) {
		this.leixingmc = leixingmc;
	}

	@Length(min = 0, max = 200, message = "有效性长度必须介于 0 和 200 之间")
	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}

	@Override
	@JsonBackReference
	public ShebeiLx getParent() {
		return parent;
	}

	@Override
	public void setParent(ShebeiLx parent) {
		this.parent = parent;
	}

	@Override
	@Length(min = 0, max = 2000, message = "parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	@Override
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Override
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	@Override
	public String getName() {
		return leixingmc;
	}
}