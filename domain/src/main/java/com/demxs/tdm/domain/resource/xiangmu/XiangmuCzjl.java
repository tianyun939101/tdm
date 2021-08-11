package com.demxs.tdm.domain.resource.xiangmu;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 项目操作记录Entity
 * @author 谭冬梅
 * @version 2017-09-04
 */
public class XiangmuCzjl extends DataEntity<XiangmuCzjl> {
	
	private static final long serialVersionUID = 1L;
	private String xiangmuid;		// 项目id
	private String caozuolx;		// 操作类型（新增、继续、暂停、完结、异常终止）
	private String caozuosj;		// 操作时间
	private String yuanyin;		// 原因
	private String caozuor;//操作人

	public String getCaozuor() {
		return caozuor;
	}

	public void setCaozuor(String caozuor) {
		this.caozuor = caozuor;
	}

	public XiangmuCzjl() {
		super();
	}

	public XiangmuCzjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="项目id长度必须介于 0 和 200 之间")
	public String getXiangmuid() {
		return xiangmuid;
	}

	public void setXiangmuid(String xiangmuid) {
		this.xiangmuid = xiangmuid;
	}
	
	@Length(min=0, max=200, message="操作类型（新增、继续、暂停、完结、异常终止）长度必须介于 0 和 200 之间")
	public String getCaozuolx() {
		return caozuolx;
	}

	public void setCaozuolx(String caozuolx) {
		this.caozuolx = caozuolx;
	}
	
	@Length(min=0, max=200, message="操作时间长度必须介于 0 和 200 之间")
	public String getCaozuosj() {
		return caozuosj;
	}

	public void setCaozuosj(String caozuosj) {
		this.caozuosj = caozuosj;
	}
	
	@Length(min=0, max=2000, message="原因长度必须介于 0 和 2000 之间")
	public String getYuanyin() {
		return yuanyin;
	}

	public void setYuanyin(String yuanyin) {
		this.yuanyin = yuanyin;
	}
	
}