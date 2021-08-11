package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 关联表Entity
 * @author wbq
 * @version 2017-08-05
 */
public class Joininfo extends DataEntity<Joininfo> {
	
	private static final long serialVersionUID = 1L;
	private String shujujid;		// 数据集ID
	private String col;		// 主表列
	private String ref;		// 引用表
	private String refkey;		// 引用表ID
	private String jtype;		// 连接类型
	private String force;		// 强制连接
	
	public Joininfo() {
		super();
	}

	public Joininfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="数据集ID长度必须介于 1 和 64 之间")
	public String getShujujid() {
		return shujujid;
	}

	public void setShujujid(String shujujid) {
		this.shujujid = shujujid;
	}
	
	@Length(min=1, max=100, message="主表列长度必须介于 1 和 100 之间")
	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}
	
	@Length(min=1, max=100, message="引用表长度必须介于 1 和 100 之间")
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Length(min=1, max=100, message="引用表ID长度必须介于 1 和 100 之间")
	public String getRefkey() {
		return refkey;
	}

	public void setRefkey(String refkey) {
		this.refkey = refkey;
	}
	
	@Length(min=1, max=100, message="连接类型长度必须介于 1 和 100 之间")
	public String getJtype() {
		return jtype;
	}

	public void setJtype(String jtype) {
		this.jtype = jtype;
	}
	
	@Length(min=1, max=100, message="强制连接长度必须介于 1 和 100 之间")
	public String getForce() {
		return force;
	}

	public void setForce(String force) {
		this.force = force;
	}
	
}