package com.demxs.tdm.domain.resource.consumeables;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材类型Entity
 * @author zhangdengcai
 * @version 2017-07-15
 */
public class HaocaiLx extends TreeEntity<HaocaiLx> {
	
	private static final long serialVersionUID = 1L;
	private String leixingbh;		// 类型编号
	private String leixingmc;		// 类型名称
	private String youxiaox;		// 有效性
	private HaocaiLx parent;        // parent_id
	private String parentIds;        // parent_ids
	private String name;

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public HaocaiLx() {
		super();
	}

	public HaocaiLx(String id){
		super(id);
	}

	public String getLeixingbh() {
		return leixingbh;
	}

	public void setLeixingbh(String leixingbh) {
		this.leixingbh = leixingbh;
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

	public User getDangqianR() {
		return dangqianR;
	}

	public void setDangqianR(User dangqianR) {
		this.dangqianR = dangqianR;
	}

	public String getCaozuoqx() {
		return caozuoqx;
	}

	public void setCaozuoqx(String caozuoqx) {
		this.caozuoqx = caozuoqx;
	}

	@Override
	@JsonBackReference
	public HaocaiLx getParent() {
		return parent;
	}

	@Override
	public void setParent(HaocaiLx parent) {
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