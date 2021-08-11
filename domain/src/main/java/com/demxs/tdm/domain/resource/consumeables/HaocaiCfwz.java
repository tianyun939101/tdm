package com.demxs.tdm.domain.resource.consumeables;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材存放位置Entity
 * @author zhangdengcai
 * @version 2017-07-16
 */
public class HaocaiCfwz extends TreeEntity<HaocaiCfwz> {
	
	private static final long serialVersionUID = 1L;
	private String weizhimc;		// 位置名称
	private String youxiaox;		// 有效性
	private HaocaiCfwz parent;      // parent_id
	private String parentIds;       // parent_ids

	private LabInfo labInfo;        //所属试验室

	private String name;
	public HaocaiCfwz() {
		super();
	}

	public HaocaiCfwz(String id){
		super(id);
	}

	@Length(min=0, max=200, message="位置名称长度必须介于 0 和 200 之间")
	public String getWeizhimc() {
		return weizhimc;
	}

	public void setWeizhimc(String weizhimc) {
		this.weizhimc = weizhimc;
	}

	@Length(min=0, max=10, message="有效性长度必须介于 0 和 10 之间")
	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}

	@Override
	@JsonBackReference
	public HaocaiCfwz getParent() {
		return parent;
	}

	@Override
	public void setParent(HaocaiCfwz parent) {
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
		return weizhimc;
	}

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}
}