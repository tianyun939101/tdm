package com.demxs.tdm.domain.resource.xiangmu;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 项目模块信息维护Entity
 * @author 詹小梅
 * @version 2017-06-14
 */
public class XiangmuMk extends DataEntity<XiangmuMk> {
	
	private static final long serialVersionUID = 1L;
	private String mokuaimc;		// 模块名称
	private String mokuaifzr;		// 模块负责人
	private String mokuaifzrid;		// 模块负责人ID
	private String xiangmuzj;		// 项目主键
	private String fuzhuj;		// 父主键
	private String fuzhujs;     //父主键s
	private String mokuaimcs;		// 模块名称
	private String suoshuxmlb;   //项目类别id
	private String youxiaox;
	private String dsf;
	private String xiangmufzr;		// 项目负责人
	private String xiangmufzrid;		// 项目负责人ID
	private String xiangmucyids;		// 项目成员IDS
	private String xiangmucy;		// 项目成员
	private String subcount;//子节点数量
	private String wtcount;//申请数量

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	private String delFlagMe; //前端是否删除 1 删除

	public String getWtcount() {
		return wtcount;
	}

	public void setWtcount(String wtcount) {
		this.wtcount = wtcount;
	}

	public String getDelFlagMe() {
		return delFlagMe;
	}

	public void setDelFlagMe(String delFlagMe) {
		this.delFlagMe = delFlagMe;
	}

	public String getSubcount() {
		return subcount;
	}

	public void setSubcount(String subcount) {
		this.subcount = subcount;
	}

	public String getXiangmufzr() {
		return xiangmufzr;
	}

	public void setXiangmufzr(String xiangmufzr) {
		this.xiangmufzr = xiangmufzr;
	}

	public String getXiangmufzrid() {
		return xiangmufzrid;
	}

	public void setXiangmufzrid(String xiangmufzrid) {
		this.xiangmufzrid = xiangmufzrid;
	}

	public String getXiangmucyids() {
		return xiangmucyids;
	}

	public void setXiangmucyids(String xiangmucyids) {
		this.xiangmucyids = xiangmucyids;
	}

	public String getXiangmucy() {
		return xiangmucy;
	}

	public void setXiangmucy(String xiangmucy) {
		this.xiangmucy = xiangmucy;
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
	public String getMokuaimcs() {
		return mokuaimcs;
	}

	public void setMokuaimcs(String mokuaimcs) {
		this.mokuaimcs = mokuaimcs;
	}

	public void setFuzhujs(String fuzhujs) {
		this.fuzhujs = fuzhujs;
	}

	public String getFuzhujs() {

		return fuzhujs;
	}

	public XiangmuMk() {
		super();
	}

	public XiangmuMk(String id){
		super(id);
	}

	@Length(min=0, max=200, message="模块名称长度必须介于 0 和 200 之间")
	public String getMokuaimc() {
		return mokuaimc;
	}

	public void setMokuaimc(String mokuaimc) {
		this.mokuaimc = mokuaimc;
	}
	
	@Length(min=0, max=200, message="模块负责人长度必须介于 0 和 200 之间")
	public String getMokuaifzr() {
		return mokuaifzr;
	}

	public void setMokuaifzr(String mokuaifzr) {
		this.mokuaifzr = mokuaifzr;
	}
	
	@Length(min=0, max=200, message="模块负责人ID长度必须介于 0 和 200 之间")
	public String getMokuaifzrid() {
		return mokuaifzrid;
	}

	public void setMokuaifzrid(String mokuaifzrid) {
		this.mokuaifzrid = mokuaifzrid;
	}
	
	@Length(min=0, max=200, message="项目主键长度必须介于 0 和 200 之间")
	public String getXiangmuzj() {
		return xiangmuzj;
	}

	public void setXiangmuzj(String xiangmuzj) {
		this.xiangmuzj = xiangmuzj;
	}
	
	@Length(min=0, max=200, message="父主键长度必须介于 0 和 200 之间")
	public String getFuzhuj() {
		return fuzhuj;
	}

	public void setFuzhuj(String fuzhuj) {
		this.fuzhuj = fuzhuj;
	}

	public String getSuoshuxmlb() {
		return suoshuxmlb;
	}

	public void setSuoshuxmlb(String suoshuxmlb) {
		this.suoshuxmlb = suoshuxmlb;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}

	public String getYouxiaox() {
		return youxiaox;
	}

	public void setYouxiaox(String youxiaox) {
		this.youxiaox = youxiaox;
	}
}