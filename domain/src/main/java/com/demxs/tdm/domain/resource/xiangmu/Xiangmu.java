package com.demxs.tdm.domain.resource.xiangmu;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;


/**
 * 项目信息，树维护Entity
 * @author 詹小梅
 * @version 2017-06-14
 */
public class Xiangmu extends DataEntity<Xiangmu> {
	
	private static final long serialVersionUID = 1L;
	private String xiangmubh;		// 项目编号
	private String yanfalxbh;		// 研发立项编号
	private String xiangmumc;		// 项目名称
	private String xiangmulx;		// 项目类型
	private String xiangmuzt;		// 项目状态
	private String zantingyy;		// 暂停原因
	private String yichangzzyy;		// 异常终止原因
	private String xiangmufzr;		// 项目负责人
	private String xiangmufzrid;		// 项目负责人ID
	private String fuzerbm;		// 负责人部门
	private String fuzerbmid;		// 负责人部门ID
	private String xiangmucyids;		// 项目成员IDS
	private String xiangmucy;		// 项目成员
	private String jieshusj;		// 结束时间
	private String xiangmujj;		// 项目简介
	private String yushiyfflbids;		// 预使用方法类别IDS
	private String yushiyfflb;		// 预使用方法类别
	private String yushiysb;		// 预使用设备
	private String yushiysbids;		// 预使用设备IDS
	private String zhuyaoxmjs;		// 主要项目介绍
	//private String xiangmuwj;// 项目文件
	private Attachment[] xiangmuwj;		// 项目文件
	private String xiangmuglzj;		// 项目关联主键
	private String xiangmulb;		// 项目类别
	private String xiangmulbmc;		// 项目类别名称
	private String lixiangsj;		// 立项时间
	private String jihuajxsj;		// 计划结项时间
	private String jiexiangsj;		// 结项时间
	private String nianfen;		// 年份
	private String fuzhujs;     //父主键s
	private String xiangmumcs;		// 项目名称
	private String xiangmuxlh;  //項目序列號
	private String date;
	private String dsf;
	private Boolean isXiangMuXG;//是否项目相关
	private String subcount; // 模块数量
	private String wtcount; //申请数量
	private String caozuolx; // 操作类型
	private XiangmuMk[] onemklist; //项目下的一级模块
	private String xiangmulbfzjs;		// 父项目类别

	private String xiangmugly; //项目管理员
	private String chanyegw; //产业顾问




	private String jieshusjStart;		// 结束时间开始
	private String jieshusjEnd;		// 结束时间结束

	private String createDateStart;		// 创建时间开始
	private String createDateEnd;		// 创建时间结束

	public String getXiangmugly() {
		return xiangmugly;
	}

	public void setXiangmugly(String xiangmugly) {
		this.xiangmugly = xiangmugly;
	}

	public String getChanyegw() {
		return chanyegw;
	}

	public void setChanyegw(String chanyegw) {
		this.chanyegw = chanyegw;
	}

	public String getJieshusjStart() {
		return jieshusjStart;
	}

	public void setJieshusjStart(String jieshusjStart) {
		this.jieshusjStart = jieshusjStart;
	}

	public String getJieshusjEnd() {
		return jieshusjEnd;
	}

	public void setJieshusjEnd(String jieshusjEnd) {
		this.jieshusjEnd = jieshusjEnd;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getXiangmulbmc() {
		return xiangmulbmc;
	}

	public String getWtcount() {
		return wtcount;
	}

	public void setWtcount(String wtcount) {
		this.wtcount = wtcount;
	}

	public String getXiangmulbfzjs() {
		return xiangmulbfzjs;
	}

	public void setXiangmulbfzjs(String xiangmulbfzjs) {
		this.xiangmulbfzjs = xiangmulbfzjs;
	}

	public void setXiangmulbmc(String xiangmulbmc) {
		this.xiangmulbmc = xiangmulbmc;
	}

	public XiangmuMk[] getOnemklist() {
		return onemklist;
	}

	public void setOnemklist(XiangmuMk[] onemklist) {
		this.onemklist = onemklist;
	}

	public String getCaozuolx() {
		return caozuolx;
	}

	public void setCaozuolx(String caozuolx) {
		this.caozuolx = caozuolx;
	}

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
	public String getXiangmumcs() {
		return xiangmumcs;
	}

	public void setXiangmumcs(String xiangmumcs) {
		this.xiangmumcs = xiangmumcs;
	}

	public Xiangmu() {
		super();
	}

	public Xiangmu(String id){
		super(id);
	}

	@Length(min=0, max=200, message="项目编号长度必须介于 0 和 200 之间")
	public String getXiangmubh() {
		return xiangmubh;
	}

	public void setXiangmubh(String xiangmubh) {
		this.xiangmubh = xiangmubh;
	}
	
	@Length(min=0, max=200, message="研发立项编号长度必须介于 0 和 200 之间")
	public String getYanfalxbh() {
		return yanfalxbh;
	}

	public void setYanfalxbh(String yanfalxbh) {
		this.yanfalxbh = yanfalxbh;
	}
	
	@Length(min=0, max=200, message="项目名称长度必须介于 0 和 200 之间")
	public String getXiangmumc() {
		return xiangmumc;
	}

	public void setXiangmumc(String xiangmumc) {
		this.xiangmumc = xiangmumc;
	}
	
	@Length(min=0, max=200, message="项目类型长度必须介于 0 和 200 之间")
	public String getXiangmulx() {
		return xiangmulx;
	}

	public void setXiangmulx(String xiangmulx) {
		this.xiangmulx = xiangmulx;
	}
	
	@Length(min=0, max=200, message="项目状态长度必须介于 0 和 200 之间")
	public String getXiangmuzt() {
		return xiangmuzt;
	}

	public void setXiangmuzt(String xiangmuzt) {
		this.xiangmuzt = xiangmuzt;
	}
	
	@Length(min=0, max=2000, message="暂停原因长度必须介于 0 和 2000 之间")
	public String getZantingyy() {
		return zantingyy;
	}

	public void setZantingyy(String zantingyy) {
		this.zantingyy = zantingyy;
	}
	
	@Length(min=0, max=2000, message="异常终止原因长度必须介于 0 和 2000 之间")
	public String getYichangzzyy() {
		return yichangzzyy;
	}

	public void setYichangzzyy(String yichangzzyy) {
		this.yichangzzyy = yichangzzyy;
	}
	
	@Length(min=0, max=200, message="项目负责人长度必须介于 0 和 200 之间")
	public String getXiangmufzr() {
		return xiangmufzr;
	}

	public void setXiangmufzr(String xiangmufzr) {
		this.xiangmufzr = xiangmufzr;
	}
	
	@Length(min=0, max=200, message="负责人部门长度必须介于 0 和 200 之间")
	public String getFuzerbm() {
		return fuzerbm;
	}

	public void setFuzerbm(String fuzerbm) {
		this.fuzerbm = fuzerbm;
	}
	
	@Length(min=0, max=1000, message="项目成员IDS长度必须介于 0 和 1000 之间")
	public String getXiangmucyids() {
		return xiangmucyids;
	}

	public void setXiangmucyids(String xiangmucyids) {
		this.xiangmucyids = xiangmucyids;
	}
	
	@Length(min=0, max=1000, message="项目成员长度必须介于 0 和 1000 之间")
	public String getXiangmucy() {
		return xiangmucy;
	}

	public void setXiangmucy(String xiangmucy) {
		this.xiangmucy = xiangmucy;
	}
	
	@Length(min=0, max=200, message="结束时间长度必须介于 0 和 200 之间")
	public String getJieshusj() {
		return jieshusj;
	}

	public void setJieshusj(String jieshusj) {
		this.jieshusj = jieshusj;
	}
	
	@Length(min=0, max=2000, message="项目简介长度必须介于 0 和 2000 之间")
	public String getXiangmujj() {
		return xiangmujj;
	}

	public void setXiangmujj(String xiangmujj) {
		this.xiangmujj = xiangmujj;
	}
	
	@Length(min=0, max=1000, message="预使用方法类别IDS长度必须介于 0 和 1000 之间")
	public String getYushiyfflbids() {
		return yushiyfflbids;
	}

	public void setYushiyfflbids(String yushiyfflbids) {
		this.yushiyfflbids = yushiyfflbids;
	}
	
	@Length(min=0, max=1000, message="预使用方法类别长度必须介于 0 和 1000 之间")
	public String getYushiyfflb() {
		return yushiyfflb;
	}

	public void setYushiyfflb(String yushiyfflb) {
		this.yushiyfflb = yushiyfflb;
	}
	
	@Length(min=0, max=1000, message="预使用设备长度必须介于 0 和 1000 之间")
	public String getYushiysb() {
		return yushiysb;
	}

	public void setYushiysb(String yushiysb) {
		this.yushiysb = yushiysb;
	}
	
	@Length(min=0, max=1000, message="预使用设备IDS长度必须介于 0 和 1000 之间")
	public String getYushiysbids() {
		return yushiysbids;
	}

	public void setYushiysbids(String yushiysbids) {
		this.yushiysbids = yushiysbids;
	}
	
	@Length(min=0, max=2000, message="主要项目介绍长度必须介于 0 和 2000 之间")
	public String getZhuyaoxmjs() {
		return zhuyaoxmjs;
	}

	public void setZhuyaoxmjs(String zhuyaoxmjs) {
		this.zhuyaoxmjs = zhuyaoxmjs;
	}

	public Attachment[] getXiangmuwj() {
		return xiangmuwj;
	}

	public void setXiangmuwj(Attachment[] xiangmuwj) {
		this.xiangmuwj = xiangmuwj;
	}

	@Length(min=0, max=1000, message="项目关联主键长度必须介于 0 和 1000 之间")
	public String getXiangmuglzj() {
		return xiangmuglzj;
	}

	public void setXiangmuglzj(String xiangmuglzj) {
		this.xiangmuglzj = xiangmuglzj;
	}
	
	@Length(min=0, max=200, message="项目类别长度必须介于 0 和 200 之间")
	public String getXiangmulb() {
		return xiangmulb;
	}

	public void setXiangmulb(String xiangmulb) {
		this.xiangmulb = xiangmulb;
	}
	
	@Length(min=0, max=200, message="立项时间长度必须介于 0 和 200 之间")
	public String getLixiangsj() {
		return lixiangsj;
	}

	public void setLixiangsj(String lixiangsj) {
		this.lixiangsj = lixiangsj;
	}
	
	@Length(min=0, max=200, message="结项时间长度必须介于 0 和 200 之间")
	public String getJiexiangsj() {
		return jiexiangsj;
	}

	public void setJiexiangsj(String jiexiangsj) {
		this.jiexiangsj = jiexiangsj;
	}
	
	@Length(min=0, max=200, message="年份长度必须介于 0 和 200 之间")
	public String getNianfen() {
		return nianfen;
	}

	public void setNianfen(String nianfen) {
		this.nianfen = nianfen;
	}

	public String getFuzhujs() {
		return fuzhujs;
	}

	public void setFuzhujs(String fuzhujs) {
		this.fuzhujs = fuzhujs;
	}

	public String getXiangmufzrid() {
		return xiangmufzrid;
	}

	public void setXiangmufzrid(String xiangmufzrid) {
		this.xiangmufzrid = xiangmufzrid;
	}

	public String getFuzerbmid() {
		return fuzerbmid;
	}

	public void setFuzerbmid(String fuzerbmid) {
		this.fuzerbmid = fuzerbmid;
	}

	public String getXiangmuxlh() {
		return xiangmuxlh;
	}

	public void setXiangmuxlh(String xiangmuxlh) {
		this.xiangmuxlh = xiangmuxlh;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}

	public Boolean getXiangMuXG() {
		return isXiangMuXG;
	}

	public void setXiangMuXG(Boolean xiangMuXG) {
		isXiangMuXG = xiangMuXG;
	}

	public String getJihuajxsj() {
		return jihuajxsj;
	}

	public void setJihuajxsj(String jihuajxsj) {
		this.jihuajxsj = jihuajxsj;
	}
}