package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;


/**
 * 设备存放位置Entity
 * @author zhangdengcai
 * @version 2017-06-14
 */
public class ShebeiCfwz extends TreeEntity<ShebeiCfwz> {
	
	private static final long serialVersionUID = 1L;
	private String weizhimc;		// 位置名称
	private String youxiaox;		// 有效性
	private ShebeiCfwz parent;        // parent_id
	private String parentIds;        // parent_ids
	private LabInfo labInfo;//所属试验室

	private String name;//后面的页面弹框问题

	public ShebeiCfwz() {

	}


	public ShebeiCfwz(String id, String weizhimc) {
		super(id);
		this.weizhimc = weizhimc;
	}

	public ShebeiCfwz(String id) {
		super(id);
	}

	public String getWeizhimc() {
		return weizhimc;
	}

	public void setWeizhimc(String weizhimc) {
		this.weizhimc = weizhimc;
	}

	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}

	@Override
	@JsonBackReference
	public ShebeiCfwz getParent() {
		return parent;
	}

	@Override
	public void setParent(ShebeiCfwz parent) {
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
	    if(StringUtils.isNoneBlank(weizhimc)){
	        return weizhimc;
        }
		return super.name;
	}

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}
}