package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 报告模板标签Entity
 * @author wbq
 * @version 2017-06-20
 */
public class Baogaombbq extends DataEntity<Baogaombbq> {
	
	private static final long serialVersionUID = 1L;
	private String tablename;		// 表名
	private String enname;		// 英文名称
	private String cnname;		// 中文名称
	private Long shunxu;		// 顺序
	private String biaoqianlb; //标签类别 form 表单，list 列表, tool 工具



	public String getBiaoqianlb() {
		return biaoqianlb;
	}

	public void setBiaoqianlb(String biaoqianlb) {
		this.biaoqianlb = biaoqianlb;
	}

	private boolean sfsz;//是否设置 true 是

	public boolean isSfsz() {
		return sfsz;
	}

	public void setSfsz(boolean sfsz) {
		this.sfsz = sfsz;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBiaoqianlx() {
		return biaoqianlx;
	}

	public void setBiaoqianlx(String biaoqianlx) {
		this.biaoqianlx = biaoqianlx;
	}

	private String biaoqianlx;

	public Baogaombbq() {
		super();
	}

	public Baogaombbq(String id){
		super(id);
	}

	@Length(min=0, max=100, message="表名长度必须介于 0 和 100 之间")
	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	public Long getShunxu() {
		return shunxu;
	}

	public void setShunxu(Long shunxu) {
		this.shunxu = shunxu;
	}
	
}