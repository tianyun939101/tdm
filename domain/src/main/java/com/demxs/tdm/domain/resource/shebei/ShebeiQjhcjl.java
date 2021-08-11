package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备期间核查记录Entity
 * @author zhangdengcai
 * @version 2017-07-13
 */
public class ShebeiQjhcjl extends DataEntity<ShebeiQjhcjl> {
	
	private static final long serialVersionUID = 1L;
	private String hechajhid;		// 核查计划主键
	private String hecharid;		// 核查人ID
	private String hechar;		// 核查人
	private String hechajhrq;		// 计划核查日期
	private String hechakssj;		// 核查开始时间：试验开始日期
	private String hechajssj;		// 核查结束时间：试验结束日期
	private String hechagcjl;		// 核查过程记录
	private String qijianhczl;		// 期间核查资料
	private String jilurid;		// 记录人ID
	private String jilur;		// 记录人
	private String qijianhcjg;		//期间核查结果
	private List<Attachment> ziliao = new ArrayList<Attachment>();

	//以下字段仅供装载、传输数据信息
	private String shebeiid;	//设备id
	private String shebeimc;	//设备名称
	private String shebeibh;	//设备编号
	
	public ShebeiQjhcjl() {
		super();
	}

	public ShebeiQjhcjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="核查计划主键长度必须介于 0 和 200 之间")
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
	
	@Length(min=0, max=3000, message="核查过程记录长度必须介于 0 和 3000 之间")
	public String getHechagcjl() {
		return hechagcjl;
	}

	public void setHechagcjl(String hechagcjl) {
		this.hechagcjl = hechagcjl;
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

	public String getQijianhcjg() {
		return qijianhcjg;
	}

	public void setQijianhcjg(String qijianhcjg) {
		this.qijianhcjg = qijianhcjg;
	}

	public List<Attachment> getZiliao() {
		return ziliao;
	}

	public void setZiliao(List<Attachment> ziliao) {
		this.ziliao = ziliao;
	}

	public String getHechajhrq() {
		return hechajhrq;
	}

	public void setHechajhrq(String hechajhrq) {
		this.hechajhrq = hechajhrq;
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

	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
}