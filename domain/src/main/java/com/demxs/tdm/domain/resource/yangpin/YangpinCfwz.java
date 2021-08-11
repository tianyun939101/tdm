package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 样品存放位置Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class YangpinCfwz extends DataEntity<YangpinCfwz> {
	
	private static final long serialVersionUID = 1L;
	private String weizhimc;		// 位置名称
	private String youxiaox;		// 有效性
	private String jibieh;		// 级别号
	private String fuzhuj;		// 父主键
	private String fujiweizhimc; //父级名称
	private String weizhimcs;		// 位置名称s
	private String dsf;
	private String subcount; //子节点数量

	public String getSubcount() {
		return subcount;
	}

	public void setSubcount(String subcount) {
		this.subcount = subcount;
	}
	public String getWeizhimcs() {
		return weizhimcs;
	}

	public void setWeizhimcs(String weizhimcs) {
		this.weizhimcs = weizhimcs;
	}

	public String getFuzhujs() {
		return fuzhujs;
	}

	public void setFuzhujs(String fuzhujs) {
		this.fuzhujs = fuzhujs;
	}

	private String fuzhujs;		// 父主键
	
	public YangpinCfwz() {
		super();
	}

	public YangpinCfwz(String id){
		super(id);
	}

	@Length(min=0, max=200, message="位置名称长度必须介于 0 和 200 之间")
	public String getWeizhimc() {
		return weizhimc;
	}

	public void setWeizhimc(String weizhimc) {
		this.weizhimc = weizhimc;
	}
	
	@Length(min=0, max=200, message="有效性长度必须介于 0 和 200 之间")
	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}
	
	@Length(min=0, max=200, message="级别号长度必须介于 0 和 200 之间")
	public String getJibieh() {
		return jibieh;
	}

	public void setJibieh(String jibieh) {
		this.jibieh = jibieh;
	}
	
	@Length(min=0, max=200, message="父主键长度必须介于 0 和 200 之间")
	public String getFuzhuj() {
		return fuzhuj;
	}

	public void setFuzhuj(String fuzhuj) {
		this.fuzhuj = fuzhuj;
	}

	public String getFujiweizhimc() {
		return fujiweizhimc;
	}

	public void setFujiweizhimc(String fujiweizhimc) {
		this.fujiweizhimc = fujiweizhimc;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}