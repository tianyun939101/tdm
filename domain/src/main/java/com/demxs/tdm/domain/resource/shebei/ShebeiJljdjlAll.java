package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 全部设备的计量检定记录Entity
 * @author zhangdengcai
 * @version 2017-08-02
 */
public class ShebeiJljdjlAll extends DataEntity<ShebeiJljdjlAll> {

	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String shebeimc;		// 设备名称
	private String shebeibh;		// 设备编号
	private String jiliangjdzq;		// 计量检定周期(天)
	private String jiliangjddw;		// 计量检定单位
	private String jiliangjdlxr;		// 计量检定联系人
	private String jiliangjdlxfs;		// 计量检定联系方式
	private String jiliangjdnr;		// 计量检定内容
	private String jihuar;		// 计划人
	private String jihuarid;		// 计划人ID
	private String jiliangfs;		//计量方式
	private String jihuajljdrq;		// 如果设备没有计量检定记录，则计划计量检定日期默认带出登记入库的日期+计量检定周期；计划计量检定日期：如果该设备有计量检定记录，则计划计量检定日期为上次计量检定日期+计量检定周期；计划计量检定日期可手动修改。
	private String jiliangjdtxts;		// 计量检定提醒天数
	private String jilinagjdjhzt;		// 计量检定计划状态：新增时，系统默认为&ldquo;未执行&rdquo;，生成计量检定执行任务后，系统自动更新成&ldquo;执行中&rdquo;；执行完成并生成&ldquo;计量检定记录&rdquo;后，系统自动更改状态为&ldquo;已执行&rdquo;。
	private String xiacijdrq;		// 下次检定日期。系统根据计量检定结束日期+计量检定周期自动计算得出

	private String jiliangjdjhid;		// 计量计划ID
	private String jiliangjdkssj;		// 计量检定开始时间
	private String jiliangjdjssj;		// 计量检定结束时间
	private String jiliangjdzl;		// 计量检定资料
	private String jiliangjdjg;		// 计量检定结果
	private String jiliangjdzsh;		// 计量检定证书号
	private String shijijljdrq;		// 实际计量检定日期
	private String jilur;		// 记录人
	private String jilurid;		// 记录人ID

	public ShebeiJljdjlAll() {
		super();
	}

	public ShebeiJljdjlAll(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=200, message="计量计划ID长度必须介于 0 和 200 之间")
	public String getJiliangjdjhid() {
		return jiliangjdjhid;
	}

	public void setJiliangjdjhid(String jiliangjdjhid) {
		this.jiliangjdjhid = jiliangjdjhid;
	}
	
	@Length(min=0, max=200, message="计量检定开始时间长度必须介于 0 和 200 之间")
	public String getJiliangjdkssj() {
		return jiliangjdkssj;
	}

	public void setJiliangjdkssj(String jiliangjdkssj) {
		this.jiliangjdkssj = jiliangjdkssj;
	}
	
	@Length(min=0, max=200, message="计量检定结束时间长度必须介于 0 和 200 之间")
	public String getJiliangjdjssj() {
		return jiliangjdjssj;
	}

	public void setJiliangjdjssj(String jiliangjdjssj) {
		this.jiliangjdjssj = jiliangjdjssj;
	}
	
	@Length(min=0, max=200, message="计量检定联系人长度必须介于 0 和 200 之间")
	public String getJiliangjdlxr() {
		return jiliangjdlxr;
	}

	public void setJiliangjdlxr(String jiliangjdlxr) {
		this.jiliangjdlxr = jiliangjdlxr;
	}
	
	@Length(min=0, max=200, message="计量检定联系方式长度必须介于 0 和 200 之间")
	public String getJiliangjdlxfs() {
		return jiliangjdlxfs;
	}

	public void setJiliangjdlxfs(String jiliangjdlxfs) {
		this.jiliangjdlxfs = jiliangjdlxfs;
	}
	
	@Length(min=0, max=3000, message="计量检定内容长度必须介于 0 和 3000 之间")
	public String getJiliangjdnr() {
		return jiliangjdnr;
	}

	public void setJiliangjdnr(String jiliangjdnr) {
		this.jiliangjdnr = jiliangjdnr;
	}

	public String getJiliangjdzl() {
		return jiliangjdzl;
	}

	public void setJiliangjdzl(String jiliangjdzl) {
		this.jiliangjdzl = jiliangjdzl;
	}
	
	@Length(min=0, max=200, message="计量检定结果长度必须介于 0 和 200 之间")
	public String getJiliangjdjg() {
		return jiliangjdjg;
	}

	public void setJiliangjdjg(String jiliangjdjg) {
		this.jiliangjdjg = jiliangjdjg;
	}
	
	@Length(min=0, max=200, message="计量检定证书号长度必须介于 0 和 200 之间")
	public String getJiliangjdzsh() {
		return jiliangjdzsh;
	}

	public void setJiliangjdzsh(String jiliangjdzsh) {
		this.jiliangjdzsh = jiliangjdzsh;
	}
	
	@Length(min=0, max=200, message="实际计量检定日期长度必须介于 0 和 200 之间")
	public String getShijijljdrq() {
		return shijijljdrq;
	}

	public void setShijijljdrq(String shijijljdrq) {
		this.shijijljdrq = shijijljdrq;
	}
	
	@Length(min=0, max=200, message="记录人长度必须介于 0 和 200 之间")
	public String getJilur() {
		return jilur;
	}

	public void setJilur(String jilur) {
		this.jilur = jilur;
	}
	
	@Length(min=0, max=200, message="记录人ID长度必须介于 0 和 200 之间")
	public String getJilurid() {
		return jilurid;
	}

	public void setJilurid(String jilurid) {
		this.jilurid = jilurid;
	}

	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}

	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
	}

	public String getJiliangjdzq() {
		return jiliangjdzq;
	}

	public void setJiliangjdzq(String jiliangjdzq) {
		this.jiliangjdzq = jiliangjdzq;
	}

	public String getJiliangjddw() {
		return jiliangjddw;
	}

	public void setJiliangjddw(String jiliangjddw) {
		this.jiliangjddw = jiliangjddw;
	}

	public String getJihuar() {
		return jihuar;
	}

	public void setJihuar(String jihuar) {
		this.jihuar = jihuar;
	}

	public String getJihuarid() {
		return jihuarid;
	}

	public void setJihuarid(String jihuarid) {
		this.jihuarid = jihuarid;
	}

	public String getJiliangfs() {
		return jiliangfs;
	}

	public void setJiliangfs(String jiliangfs) {
		this.jiliangfs = jiliangfs;
	}

	public String getJihuajljdrq() {
		return jihuajljdrq;
	}

	public void setJihuajljdrq(String jihuajljdrq) {
		this.jihuajljdrq = jihuajljdrq;
	}

	public String getJiliangjdtxts() {
		return jiliangjdtxts;
	}

	public void setJiliangjdtxts(String jiliangjdtxts) {
		this.jiliangjdtxts = jiliangjdtxts;
	}

	public String getJilinagjdjhzt() {
		return jilinagjdjhzt;
	}

	public void setJilinagjdjhzt(String jilinagjdjhzt) {
		this.jilinagjdjhzt = jilinagjdjhzt;
	}

	public String getXiacijdrq() {
		return xiacijdrq;
	}

	public void setXiacijdrq(String xiacijdrq) {
		this.xiacijdrq = xiacijdrq;
	}
}