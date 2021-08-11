package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 统计条件Entity
 * @author wbq
 * @version 2017-08-07
 */
public class Tongjitj extends DataEntity<Tongjitj> {
	
	private static final long serialVersionUID = 1L;
	//private String tongjiid;		// 统计ID
	private String cnname;		// 字段名称
	private String enname;		// 英文名称
	private String ziduanlx;		// 字段类型
	private String morenz;		// 默认值
	private String sqlstr;		// SQL语句
	private String zidianlxz;     //字典类型值
	private String tongjilx; //统计类型
	private String shifoudx; //是否多选 true 是  false  否

	private String shifouyx;// 是否有效 1是  0 否

	public String getShifouyx() {
		return shifouyx;
	}

	public String getTongjilx() {
		return tongjilx;
	}

	public void setTongjilx(String tongjilx) {
		this.tongjilx = tongjilx;
	}

	public String getShifoudx() {
		return shifoudx;
	}

	public void setShifoudx(String shifoudx) {
		this.shifoudx = shifoudx;
	}

	public void setShifouyx(String shifouyx) {
		this.shifouyx = shifouyx;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getZidianlxz() {
		return zidianlxz;
	}

	public void setZidianlxz(String zidianlxz) {
		this.zidianlxz = zidianlxz;
	}

	public Tongjitj() {
		super();
	}

	public Tongjitj(String id){
		super(id);
	}


	@Length(min=0, max=100, message="字段名称长度必须介于 0 和 100 之间")
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	@Length(min=0, max=100, message="字段类型长度必须介于 0 和 100 之间")
	public String getZiduanlx() {
		return ziduanlx;
	}

	public void setZiduanlx(String ziduanlx) {
		this.ziduanlx = ziduanlx;
	}
	
	@Length(min=0, max=100, message="默认值长度必须介于 0 和 100 之间")
	public String getMorenz() {
		return morenz;
	}

	public void setMorenz(String morenz) {
		this.morenz = morenz;
	}
	
	@Length(min=0, max=4000, message="SQL语句长度必须介于 0 和 4000 之间")
	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	
}