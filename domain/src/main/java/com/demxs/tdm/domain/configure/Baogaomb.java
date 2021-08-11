package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 报告模板Entity
 * @author wbq
 * @version 2017-06-20
 */
public class Baogaomb extends DataEntity<Baogaomb> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 报告名称
	private String dizhi;		// 报告地址
	private String mobanlx;
	private String isqiyong;
	private String jiancexid; //试验项ID或列表标签英文名
	private String suoshumbid; //所属模板id
	private String isliezh; //是否列转行

	public String getIsliezh() {
		return isliezh;
	}

	public void setIsliezh(String isliezh) {
		this.isliezh = isliezh;
	}

	public String getSuoshumbid() {
		return suoshumbid;
	}

	public void setSuoshumbid(String suoshumbid) {
		this.suoshumbid = suoshumbid;
	}

	private boolean sfsz;//是否设置 true 是

	public boolean isSfsz() {
		return sfsz;
	}

	public void setSfsz(boolean sfsz) {
		this.sfsz = sfsz;
	}
	public String getJiancexid() {
		return jiancexid;
	}

	public void setJiancexid(String jiancexid) {
		this.jiancexid = jiancexid;
	}

	public String getBaogaolx() {
		return baogaolx;
	}

	public void setBaogaolx(String baogaolx) {
		this.baogaolx = baogaolx;
	}

	private String baogaolx;
	public Baogaomb() {
		super();
	}

	public Baogaomb(String id){
		super(id);
	}

	@Length(min=1, max=100, message="报告名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="报告地址长度必须介于 0 和 255 之间")
	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public String getMobanlx() {
		return mobanlx;
	}

	public void setMobanlx(String mobanlx) {
		this.mobanlx = mobanlx;
	}

	public String getIsqiyong() {
		return isqiyong;
	}

	public void setIsqiyong(String isqiyong) {
		this.isqiyong = isqiyong;
	}
	
}