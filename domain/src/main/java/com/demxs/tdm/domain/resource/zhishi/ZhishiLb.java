package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 知识类别Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class ZhishiLb extends TreeEntity<ZhishiLb> {
	
	private static final long serialVersionUID = 1L;
	private String leixingmc;		// 类型名称
	private String youxiaox;		// 有效性
	private ZhishiLb parent;
	private String parentIds;        // parent_ids\

	private String name;
	private String isSysDefault;

/*	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

	public User getDangqianR() {
		return dangqianR;
	}*/
	public ZhishiLb() {
	super();
}

	public ZhishiLb(String id){
		super(id);
	}

	public String getLeixingmc() {
		return leixingmc;
	}

	public void setLeixingmc(String leixingmc) {
		this.leixingmc = leixingmc;
	}

	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}

	@JsonBackReference
	public ZhishiLb getParent() {
		return parent;
	}

	public void setParent(ZhishiLb parent) {
		this.parent = parent;
	}

	@Length(min = 0, max = 2000, message = "parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	@Override
	public String getName() {
		return leixingmc;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

    public String getIsSysDefault() {
        return isSysDefault;
    }

    public ZhishiLb setIsSysDefault(String isSysDefault) {
        this.isSysDefault = isSysDefault;
        return this;
    }
}