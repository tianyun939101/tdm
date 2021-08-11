package com.demxs.tdm.domain.resource.xiangmu;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 项目类别Entity
 * @author 詹小梅
 * @version 2017-06-23
 */
public class XiangmuLb extends DataEntity<XiangmuLb> {
	
	private static final long serialVersionUID = 1L;
	private String leixingbh;		// 类型编号
	private String leixingmc;		// 类型名称
	private String youxiaox;		// 有效性
	private String fuzhuj;		// 父主键
	private String fuzhujs;    //父主键s
	private String nianfen;		// 年份
	private String subcount; // 子数量
	private String dsf;

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限


	public String getSubcount() {
		return subcount;
	}

	public void setSubcount(String subcount) {
		this.subcount = subcount;
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

	public String getFuzhujs() {
		return fuzhujs;
	}

	public void setFuzhujs(String fuzhujs) {
		this.fuzhujs = fuzhujs;
	}

	public XiangmuLb() {
		super();
	}

	public XiangmuLb(String id){
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
	
	@Length(min=0, max=200, message="父主键长度必须介于 0 和 200 之间")
	public String getFuzhuj() {
		return fuzhuj;
	}

	public void setFuzhuj(String fuzhuj) {
		this.fuzhuj = fuzhuj;
	}
	
	@Length(min=0, max=200, message="年份长度必须介于 0 和 200 之间")
	public String getNianfen() {
		return nianfen;
	}

	public void setNianfen(String nianfen) {
		this.nianfen = nianfen;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}