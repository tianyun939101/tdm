package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 拆样任务Entity
 * @author 谭冬梅
 * @version 2017-07-16
 */
public class YangpinCyrw extends DataEntity<YangpinCyrw> {
	
	private static final long serialVersionUID = 1L;
	private String chaifensl;		// 拆分数量
	private String chaiyangsm;		// 拆样说明
	private String gongchengsid;		// 工程师id
	private String gongchengsmc;		// 工程师名称
	private String jihuajssj;		// 计划结束时间
	private String jihuakssj;		// 计划开始时间
	private String shebeiid;		// 设备id
	private String shebeimc;		// 设备名称
	private String yangpinid;		// 拆样id（样品id）
	private String yangpinmc;		// 样品名称
	private String yewuid;		// 业务id
	private YangpinCygcs[] gongchengshilist;


	/**-
	 * 以下关联查询使用
	 */
	private String yangpinbh;//样品编号
	private String chushibm;//初始编码
	private String yewudh;//业务单号
	private String yewufqr;//业务发起人
	
	public YangpinCyrw() {
		super();
	}

	public YangpinCyrw(String id){
		super(id);
	}

	@Length(min=0, max=10, message="拆分数量长度必须介于 0 和 10 之间")
	public String getChaifensl() {
		return chaifensl;
	}

	public void setChaifensl(String chaifensl) {
		this.chaifensl = chaifensl;
	}
	
	@Length(min=0, max=1000, message="拆样说明长度必须介于 0 和 1000 之间")
	public String getChaiyangsm() {
		return chaiyangsm;
	}

	public void setChaiyangsm(String chaiyangsm) {
		this.chaiyangsm = chaiyangsm;
	}
	
	@Length(min=0, max=100, message="工程师id长度必须介于 0 和 100 之间")
	public String getGongchengsid() {
		return gongchengsid;
	}

	public void setGongchengsid(String gongchengsid) {
		this.gongchengsid = gongchengsid;
	}
	
	@Length(min=0, max=100, message="工程师名称长度必须介于 0 和 100 之间")
	public String getGongchengsmc() {
		return gongchengsmc;
	}

	public void setGongchengsmc(String gongchengsmc) {
		this.gongchengsmc = gongchengsmc;
	}
	
	@Length(min=0, max=100, message="计划结束时间长度必须介于 0 和 100 之间")
	public String getJihuajssj() {
		return jihuajssj;
	}

	public void setJihuajssj(String jihuajssj) {
		this.jihuajssj = jihuajssj;
	}
	
	@Length(min=0, max=100, message="计划开始时间长度必须介于 0 和 100 之间")
	public String getJihuakssj() {
		return jihuakssj;
	}

	public void setJihuakssj(String jihuakssj) {
		this.jihuakssj = jihuakssj;
	}
	
	@Length(min=0, max=100, message="设备id长度必须介于 0 和 100 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=100, message="设备名称长度必须介于 0 和 100 之间")
	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}
	
	@Length(min=0, max=100, message="拆样id（样品id）长度必须介于 0 和 100 之间")
	public String getYangpinid() {
		return yangpinid;
	}

	public void setYangpinid(String yangpinid) {
		this.yangpinid = yangpinid;
	}
	
	@Length(min=0, max=100, message="样品名称长度必须介于 0 和 100 之间")
	public String getYangpinmc() {
		return yangpinmc;
	}

	public void setYangpinmc(String yangpinmc) {
		this.yangpinmc = yangpinmc;
	}
	
	@Length(min=0, max=100, message="业务id长度必须介于 0 和 100 之间")
	public String getYewuid() {
		return yewuid;
	}

	public void setYewuid(String yewuid) {
		this.yewuid = yewuid;
	}

	public String getYangpinbh() {
		return yangpinbh;
	}

	public void setYangpinbh(String yangpinbh) {
		this.yangpinbh = yangpinbh;
	}

	public String getChushibm() {
		return chushibm;
	}

	public void setChushibm(String chushibm) {
		this.chushibm = chushibm;
	}

	public String getYewudh() {
		return yewudh;
	}

	public void setYewudh(String yewudh) {
		this.yewudh = yewudh;
	}

	public String getYewufqr() {
		return yewufqr;
	}

	public void setYewufqr(String yewufqr) {
		this.yewufqr = yewufqr;
	}

	public YangpinCygcs[] getGongchengshilist() {
		return gongchengshilist;
	}

	public void setGongchengshilist(YangpinCygcs[] gongchengshilist) {
		this.gongchengshilist = gongchengshilist;
	}
}