package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

/**
 * 设备盘点清单（记录）Entity
 * @author zhangdengcai
 * @version 2017-09-01
 */
public class ShebeiPdqd extends DataEntity<ShebeiPdqd> {
	
	private static final long serialVersionUID = 1L;
	private String pandianrid;		// 盘点人ID
	private String pandianr;		// 盘点人
	private String pandianrq;		// 盘点日期
	private String fujianid;		////导入的盘点信息表产生的附件id
	private Attachment daorudpdb = new Attachment();	//导入的盘点信息表产生的附件（限定一次上传一张附件）
	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public ShebeiPdqd() {
		super();
	}

	public ShebeiPdqd(String id){
		super(id);
	}

	@Length(min=0, max=200, message="盘点人ID长度必须介于 0 和 200 之间")
	public String getPandianrid() {
		return pandianrid;
	}

	public void setPandianrid(String pandianrid) {
		this.pandianrid = pandianrid;
	}
	
	@Length(min=0, max=200, message="盘点人长度必须介于 0 和 200 之间")
	public String getPandianr() {
		return pandianr;
	}

	public void setPandianr(String pandianr) {
		this.pandianr = pandianr;
	}
	
	@Length(min=0, max=200, message="盘点日期长度必须介于 0 和 200 之间")
	public String getPandianrq() {
		return pandianrq;
	}

	public void setPandianrq(String pandianrq) {
		this.pandianrq = pandianrq;
	}

	public String getFujianid() {
		return fujianid;
	}

	public void setFujianid(String fujianid) {
		this.fujianid = fujianid;
	}

	public Attachment getDaorudpdb() {
		return daorudpdb;
	}

	public void setDaorudpdb(Attachment daorudpdb) {
		this.daorudpdb = daorudpdb;
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