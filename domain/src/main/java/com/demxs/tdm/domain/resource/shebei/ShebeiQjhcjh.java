package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备期间核查计划Entity
 * @author zhangdengcai
 * @version 2017-07-13
 */
public class ShebeiQjhcjh extends DataEntity<ShebeiQjhcjh> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备主键
	private String qijianhczq;		// 期间核查周期 天
	private String hechanr;		// 核查内容
	private String hechayj;		// 核查依据
	private String hechaff;		// 核查方法
	private String hechayq;		// 核查要求
	private String jihuarid;		// 计划人ID
	private String jihuar;		// 计划人
	private String hechatxts;		// 核查提醒天数
	private String jihuahcrq;		// 如果该设备没有期间核查记录，则计划核查日期默认带出登记入库的日期+期间核查周期；计划核查日期：如果该标准物质有期间核查记录，则计划核查日期为上次期间核查日期+期间核查周期；计划核查日期可手动修改。
	private String hechajhzt;		// 核查计划状态：新增时，系统默认为&ldquo;未执行&rdquo;，生成核查执行任务后，系统自动更新成&ldquo;执行中&rdquo;；执行完成并生成&ldquo;核查记录&rdquo;后，系统自动更改状态为&ldquo;已执行&rdquo;。
	private String xiacihcrq;		// 下次核查日期：系统根据核查结束日期+核查周期，自动计算出下次核查日期
	private String hechajlbz;		// 核查记录备注
	private String guanlianrwid;		// 关联任务ID
	private List<ShebeiQjhcjl> hechajl = new ArrayList<ShebeiQjhcjl>();//核查记录

	//以下字段仅供装载、传输数据信息
	private String shebeimc;	//设备名称
	private String shebeibh;	//设备编号

	public ShebeiQjhcjh() {
		super();
	}

	public ShebeiQjhcjh(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备主键长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=200, message="期间核查周期 天长度必须介于 0 和 200 之间")
	public String getQijianhczq() {
		return qijianhczq;
	}

	public void setQijianhczq(String qijianhczq) {
		this.qijianhczq = qijianhczq;
	}
	
	@Length(min=0, max=3000, message="核查内容长度必须介于 0 和 3000 之间")
	public String getHechanr() {
		return hechanr;
	}

	public void setHechanr(String hechanr) {
		this.hechanr = hechanr;
	}
	
	@Length(min=0, max=3000, message="核查依据长度必须介于 0 和 3000 之间")
	public String getHechayj() {
		return hechayj;
	}

	public void setHechayj(String hechayj) {
		this.hechayj = hechayj;
	}
	
	@Length(min=0, max=1000, message="核查方法长度必须介于 0 和 1000 之间")
	public String getHechaff() {
		return hechaff;
	}

	public void setHechaff(String hechaff) {
		this.hechaff = hechaff;
	}
	
	@Length(min=0, max=3000, message="核查要求长度必须介于 0 和 3000 之间")
	public String getHechayq() {
		return hechayq;
	}

	public void setHechayq(String hechayq) {
		this.hechayq = hechayq;
	}
	
	@Length(min=0, max=200, message="计划人ID长度必须介于 0 和 200 之间")
	public String getJihuarid() {
		return jihuarid;
	}

	public void setJihuarid(String jihuarid) {
		this.jihuarid = jihuarid;
	}
	
	@Length(min=0, max=200, message="计划人长度必须介于 0 和 200 之间")
	public String getJihuar() {
		return jihuar;
	}

	public void setJihuar(String jihuar) {
		this.jihuar = jihuar;
	}
	
	@Length(min=0, max=200, message="核查提醒天数长度必须介于 0 和 200 之间")
	public String getHechatxts() {
		return hechatxts;
	}

	public void setHechatxts(String hechatxts) {
		this.hechatxts = hechatxts;
	}
	
	@Length(min=0, max=200, message="如果该设备没有期间核查记录，则计划核查日期默认带出登记入库的日期+期间核查周期；计划核查日期：如果该标准物质有期间核查记录，则计划核查日期为上次期间核查日期+期间核查周期；计划核查日期可手动修改。长度必须介于 0 和 200 之间")
	public String getJihuahcrq() {
		return jihuahcrq;
	}

	public void setJihuahcrq(String jihuahcrq) {
		this.jihuahcrq = jihuahcrq;
	}
	
	@Length(min=0, max=200, message="核查计划状态：新增时，系统默认为&ldquo;未执行&rdquo;，生成核查执行任务后，系统自动更新成&ldquo;执行中&rdquo;；执行完成并生成&ldquo;核查记录&rdquo;后，系统自动更改状态为&ldquo;已执行&rdquo;。长度必须介于 0 和 200 之间")
	public String getHechajhzt() {
		return hechajhzt;
	}

	public void setHechajhzt(String hechajhzt) {
		this.hechajhzt = hechajhzt;
	}
	
	@Length(min=0, max=200, message="下次核查日期：系统根据核查结束日期+核查周期，自动计算出下次核查日期长度必须介于 0 和 200 之间")
	public String getXiacihcrq() {
		return xiacihcrq;
	}

	public void setXiacihcrq(String xiacihcrq) {
		this.xiacihcrq = xiacihcrq;
	}
	
	@Length(min=0, max=200, message="核查记录备注长度必须介于 0 和 200 之间")
	public String getHechajlbz() {
		return hechajlbz;
	}

	public void setHechajlbz(String hechajlbz) {
		this.hechajlbz = hechajlbz;
	}
	
	@Length(min=0, max=200, message="关联任务ID长度必须介于 0 和 200 之间")
	public String getGuanlianrwid() {
		return guanlianrwid;
	}

	public void setGuanlianrwid(String guanlianrwid) {
		this.guanlianrwid = guanlianrwid;
	}

	public List<ShebeiQjhcjl> getHechajl() {
		return hechajl;
	}

	public void setHechajl(List<ShebeiQjhcjl> hechajl) {
		this.hechajl = hechajl;
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
}