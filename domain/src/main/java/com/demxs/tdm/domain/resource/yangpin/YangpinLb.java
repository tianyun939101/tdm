package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.comac.common.constant.NengliConstans;
import org.hibernate.validator.constraints.Length;

/**
 * 样品类型维护Entity
 * @author 詹小梅
 * @version 2017-06-16
 */
public class YangpinLb extends DataEntity<YangpinLb> {
	
	private static final long serialVersionUID = 1L;
	private String leixingbh;		// 类型编号
	private String leixingmc;		// 类型名称
	private String youxiaox;		// 有效性
	private String jibieh;		// 级别号
	private String fuzhuj;		// 父主键
	private String fuzhujs;		// 父主键s
	private String fujimc;
	private String leixingmcs;		// 类型名称s
	private YangpinLbsx[] yangpinsx;//样品属性
	private String yangpinsxmc;     //样品属性名称
	private String dsf;

	private String isShowNull;//是否显示空节点 1是 0否
	private String subcount; //子节点数量
	private String canSelect;// 只有待验证 和 已审核的 方法允许选择

	public String getCanSelect() {
		return NengliConstans.optFFStatus.DAIYANZHENG +","+ NengliConstans.optFFStatus.YIYANZHEN;
	}

	public String getSubcount() {
		return subcount;
	}

	public void setSubcount(String subcount) {
		this.subcount = subcount;
	}

	public String getIsShowNull() {
		return isShowNull;
	}

	public void setIsShowNull(String isShowNull) {
		this.isShowNull = isShowNull;
	}

	public String getLeixingmcs() {
		return leixingmcs;
	}

	public void setLeixingmcs(String leixingmcs) {
		this.leixingmcs = leixingmcs;
	}

	public String getFuzhujs() {
		return fuzhujs;
	}

	public void setFuzhujs(String fuzhujs) {
		this.fuzhujs = fuzhujs;
	}

	public YangpinLb() {
		super();
	}

	public YangpinLb(String id){
		super(id);
	}

	@Length(min=0, max=200, message="类型编号长度必须介于 0 和 200 之间")
	public String getLeixingbh() {
		return leixingbh;
	}

	public void setLeixingbh(String leixingbh) {
		this.leixingbh = leixingbh;
	}
	
	@Length(min=0, max=200, message="类型名称长度必须介于 0 和 200 之间")
	public String getLeixingmc() {
		return leixingmc;
	}

	public void setLeixingmc(String leixingmc) {
		this.leixingmc = leixingmc;
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

	public String getFujimc() {
		return fujimc;
	}

	public void setFujimc(String fujimc) {
		this.fujimc = fujimc;
	}

	public YangpinLbsx[] getYangpinsx() {
		return yangpinsx;
	}

	public void setYangpinsx(YangpinLbsx[] yangpinsx) {
		this.yangpinsx = yangpinsx;
	}

	public String getYangpinsxmc() {
		return yangpinsxmc;
	}

	public void setYangpinsxmc(String yangpinsxmc) {
		this.yangpinsxmc = yangpinsxmc;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}

}