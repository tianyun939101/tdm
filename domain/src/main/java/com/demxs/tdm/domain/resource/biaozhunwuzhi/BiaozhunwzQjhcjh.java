package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质期间核查计划Entity
 * @author zhangdengcai
 * @version 2017-07-19
 */
public class BiaozhunwzQjhcjh extends DataEntity<BiaozhunwzQjhcjh> {
	
	private static final long serialVersionUID = 1L;
	private String biaozhunwzzj;		// 标准物质主键
	private String qijianhczq;		// 期间核查周期 天
	private String hechatxts;		// 核查提醒天数
	private String hechanr;		// 核查内容
	private String hechayj;		// 核查依据
	private String hechaff;		// 核查方法
	private String hechayq;		// 核查要求
	private String jihuazxkssj;		// 计划执行开始时间
	private String jihuazxjssj;		// 计划执行结束时间
	private String hechajhzt;		// 核查计划状态：新增时，系统默认为&ldquo;未执行&rdquo;，生成核查执行任务后，系统自动更新成&ldquo;执行中&rdquo;；执行完成并生成&ldquo;核查记录&rdquo;后，系统自动更改状态为&ldquo;已执行&rdquo;。
	private String xiacihcrq;		// 下次核查日期：系统根据核查结束日期+核查周期，自动计算出下次核查日期
	private String jihuarid;		// 计划人ID
	private String jihuar;		// 计划人
	private List<BiaozhunwzQjhcjl> hechajl = new ArrayList<BiaozhunwzQjhcjl>();

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public BiaozhunwzQjhcjh() {
		super();
	}

	public BiaozhunwzQjhcjh(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标准物质主键长度必须介于 0 和 200 之间")
	public String getBiaozhunwzzj() {
		return biaozhunwzzj;
	}

	public void setBiaozhunwzzj(String biaozhunwzzj) {
		this.biaozhunwzzj = biaozhunwzzj;
	}
	
	@Length(min=0, max=200, message="期间核查周期 天长度必须介于 0 和 200 之间")
	public String getQijianhczq() {
		return qijianhczq;
	}

	public void setQijianhczq(String qijianhczq) {
		this.qijianhczq = qijianhczq;
	}
	
	@Length(min=0, max=200, message="核查提醒天数长度必须介于 0 和 200 之间")
	public String getHechatxts() {
		return hechatxts;
	}

	public void setHechatxts(String hechatxts) {
		this.hechatxts = hechatxts;
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
	
	@Length(min=0, max=200, message="计划执行开始时间长度必须介于 0 和 200 之间")
	public String getJihuazxkssj() {
		return jihuazxkssj;
	}

	public void setJihuazxkssj(String jihuazxkssj) {
		this.jihuazxkssj = jihuazxkssj;
	}
	
	@Length(min=0, max=200, message="计划执行结束时间长度必须介于 0 和 200 之间")
	public String getJihuazxjssj() {
		return jihuazxjssj;
	}

	public void setJihuazxjssj(String jihuazxjssj) {
		this.jihuazxjssj = jihuazxjssj;
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

	public List<BiaozhunwzQjhcjl> getHechajl() {
		return hechajl;
	}

	public void setHechajl(List<BiaozhunwzQjhcjl> hechajl) {
		this.hechajl = hechajl;
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
}