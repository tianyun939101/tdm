package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 样品方案Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class YangpinFa extends DataEntity<YangpinFa> {
	
	private static final long serialVersionUID = 1L;
	private String chuangjianrid;		// 创建人ID
	private String chuangjiansj;		// 创建时间
	private String caozuorid;		// 操作人ID
	private String caozuosj;		// 操作时间
	private String shanchubs;		// 删除标示
	private String yangpinlbid;		// 样品类别ID
	private String yangpinsxid;		// 样品属性ID
	
	public YangpinFa() {
		super();
	}

	public YangpinFa(String id){
		super(id);
	}

	@Length(min=0, max=200, message="创建人ID长度必须介于 0 和 200 之间")
	public String getChuangjianrid() {
		return chuangjianrid;
	}

	public void setChuangjianrid(String chuangjianrid) {
		this.chuangjianrid = chuangjianrid;
	}
	
	@Length(min=0, max=200, message="创建时间长度必须介于 0 和 200 之间")
	public String getChuangjiansj() {
		return chuangjiansj;
	}

	public void setChuangjiansj(String chuangjiansj) {
		this.chuangjiansj = chuangjiansj;
	}
	
	@Length(min=0, max=200, message="操作人ID长度必须介于 0 和 200 之间")
	public String getCaozuorid() {
		return caozuorid;
	}

	public void setCaozuorid(String caozuorid) {
		this.caozuorid = caozuorid;
	}
	
	@Length(min=0, max=200, message="操作时间长度必须介于 0 和 200 之间")
	public String getCaozuosj() {
		return caozuosj;
	}

	public void setCaozuosj(String caozuosj) {
		this.caozuosj = caozuosj;
	}
	
	@Length(min=0, max=2, message="删除标示长度必须介于 0 和 2 之间")
	public String getShanchubs() {
		return shanchubs;
	}

	public void setShanchubs(String shanchubs) {
		this.shanchubs = shanchubs;
	}
	
	@Length(min=0, max=100, message="样品类别ID长度必须介于 0 和 100 之间")
	public String getYangpinlbid() {
		return yangpinlbid;
	}

	public void setYangpinlbid(String yangpinlbid) {
		this.yangpinlbid = yangpinlbid;
	}
	
	@Length(min=0, max=100, message="样品属性ID长度必须介于 0 和 100 之间")
	public String getYangpinsxid() {
		return yangpinsxid;
	}

	public void setYangpinsxid(String yangpinsxid) {
		this.yangpinsxid = yangpinsxid;
	}
	
}