package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质期间核查记录Entity
 * @author zhangdengcai
 * @version 2017-07-19
 */
public class BiaozhunwzQjhcjl extends DataEntity<BiaozhunwzQjhcjl> {
	
	private static final long serialVersionUID = 1L;
	private String hechajhid;		// 计划id
	private String hecharid;		// 核查人ID
	private String hechar;		// 核查人
	private String jihuahcrq;		// 计划核查日期.如果该标准物质没有期间核查记录，则计划核查日期默认带出登记入库的日期+期间核查周期；计划核查日期：如果该标准物质有期间核查记录，则计划核查日期为上次期间核查日期+期间核查周期；计划核查日期可手动修改。
	private String hechakssj;		// 核查开始时间：试验开始日期
	private String hechajssj;		// 核查结束时间：试验结束日期
	private String hechajg;		// 核查结果
	private String qijianhczl;		// 期间核查资料
	private String jilurid;		// 记录人ID
	private String jilur;		// 记录人
	private String guanlianrwid;		// 关联任务ID
	private String hechagcjl;		// 核查过程记录
	private List<Attachment> ziliao = new ArrayList<Attachment>();
	private String biaozhunwzid;		//标准物质id
	
	public BiaozhunwzQjhcjl() {
		super();
	}

	public BiaozhunwzQjhcjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="计划id长度必须介于 0 和 200 之间")
	public String getHechajhid() {
		return hechajhid;
	}

	public void setHechajhid(String hechajhid) {
		this.hechajhid = hechajhid;
	}
	
	@Length(min=0, max=200, message="核查人ID长度必须介于 0 和 200 之间")
	public String getHecharid() {
		return hecharid;
	}

	public void setHecharid(String hecharid) {
		this.hecharid = hecharid;
	}
	
	@Length(min=0, max=200, message="核查人长度必须介于 0 和 200 之间")
	public String getHechar() {
		return hechar;
	}

	public void setHechar(String hechar) {
		this.hechar = hechar;
	}
	
	@Length(min=0, max=200, message="如果该标准物质没有期间核查记录，则计划核查日期默认带出登记入库的日期+期间核查周期；计划核查日期：如果该标准物质有期间核查记录，则计划核查日期为上次期间核查日期+期间核查周期；计划核查日期可手动修改。长度必须介于 0 和 200 之间")
	public String getJihuahcrq() {
		return jihuahcrq;
	}

	public void setJihuahcrq(String jihuahcrq) {
		this.jihuahcrq = jihuahcrq;
	}
	
	@Length(min=0, max=200, message="核查开始时间：试验开始日期长度必须介于 0 和 200 之间")
	public String getHechakssj() {
		return hechakssj;
	}

	public void setHechakssj(String hechakssj) {
		this.hechakssj = hechakssj;
	}
	
	@Length(min=0, max=200, message="核查结束时间：试验结束日期长度必须介于 0 和 200 之间")
	public String getHechajssj() {
		return hechajssj;
	}

	public void setHechajssj(String hechajssj) {
		this.hechajssj = hechajssj;
	}
	
	@Length(min=0, max=10, message="核查结论长度必须介于 0 和 300 之间")
	public String getHechajg() {
		return hechajg;
	}

	public void setHechajg(String hechajg) {
		this.hechajg = hechajg;
	}
	
	@Length(min=0, max=200, message="期间核查资料长度必须介于 0 和 200 之间")
	public String getQijianhczl() {
		return qijianhczl;
	}

	public void setQijianhczl(String qijianhczl) {
		this.qijianhczl = qijianhczl;
	}
	
	@Length(min=0, max=200, message="记录人ID长度必须介于 0 和 200 之间")
	public String getJilurid() {
		return jilurid;
	}

	public void setJilurid(String jilurid) {
		this.jilurid = jilurid;
	}
	
	@Length(min=0, max=200, message="记录人长度必须介于 0 和 200 之间")
	public String getJilur() {
		return jilur;
	}

	public void setJilur(String jilur) {
		this.jilur = jilur;
	}
	
	@Length(min=0, max=200, message="关联任务ID长度必须介于 0 和 200 之间")
	public String getGuanlianrwid() {
		return guanlianrwid;
	}

	public void setGuanlianrwid(String guanlianrwid) {
		this.guanlianrwid = guanlianrwid;
	}
	
	@Length(min=0, max=2000, message="核查过程记录长度必须介于 0 和 2000 之间")
	public String getHechagcjl() {
		return hechagcjl;
	}

	public void setHechagcjl(String hechagcjl) {
		this.hechagcjl = hechagcjl;
	}

	public List<Attachment> getZiliao() {
		return ziliao;
	}

	public void setZiliao(List<Attachment> ziliao) {
		this.ziliao = ziliao;
	}

	public String getBiaozhunwzid() {
		return biaozhunwzid;
	}

	public void setBiaozhunwzid(String biaozhunwzid) {
		this.biaozhunwzid = biaozhunwzid;
	}
}