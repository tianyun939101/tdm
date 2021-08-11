package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 统计配置Entity
 * @author wbq
 * @version 2017-08-07
 */
public class Tongji extends DataEntity<Tongji> {
	
	private static final long serialVersionUID = 1L;
	private String biaoti;		// 统计标题
	private String tjtype;		// 统计类型
	private String isheji;		// 是否需要合计
	private String sqlstr;		// sql语句
	private String tutongjilx; // 图统计类型
	private String tutongjifx; // 图统计方向
	private String tiaojianids;		// 条件IDs
	private String tiaojiannames;		// 条件名称s
	private String shifoujcb; //是否交叉表
	private String hengzuobid; // 横坐标id
	private String iszitu; //是否子图
	private String zitutj ;// 传递给子图的条件
	private String zitutjname ;// 条件名
	private String iszuanqu; //是否钻取
	private String zitudz; //子图地址
	private String canshudz;  //  子图条件
	private String zitudzname; // 子图地址名字
	private String canshudzname;  //  子图条件名字

	public String getZitutjname() {
		return zitutjname;
	}

	public void setZitutjname(String zitutjname) {
		this.zitutjname = zitutjname;
	}

	public String getIszitu() {
		return iszitu;
	}

	public void setIszitu(String iszitu) {
		this.iszitu = iszitu;
	}

	public String getZitutj() {
		return zitutj;
	}

	public void setZitutj(String zitutj) {
		this.zitutj = zitutj;
	}

	public String getZitudzname() {
		return zitudzname;
	}

	public void setZitudzname(String zitudzname) {
		this.zitudzname = zitudzname;
	}

	public String getCanshudzname() {
		return canshudzname;
	}

	public void setCanshudzname(String canshudzname) {
		this.canshudzname = canshudzname;
	}

	public String getIszuanqu() {
		return iszuanqu;
	}

	public void setIszuanqu(String iszuanqu) {
		this.iszuanqu = iszuanqu;
	}

	public String getZitudz() {
		return zitudz;
	}

	public void setZitudz(String zitudz) {
		this.zitudz = zitudz;
	}

	public String getHengzuobid() {
		return hengzuobid;
	}

	public void setHengzuobid(String hengzuobid) {
		this.hengzuobid = hengzuobid;
	}

	public String getShifoujcb() {
		return shifoujcb;
	}

	public void setShifoujcb(String shifoujcb) {
		this.shifoujcb = shifoujcb;
	}

	public String getTiaojiannames() {
		return tiaojiannames;
	}

	public void setTiaojiannames(String tiaojiannames) {
		this.tiaojiannames = tiaojiannames;
	}

	public Tongji() {
		super();
	}

	public Tongji(String id){
		super(id);
	}

	public String getTiaojianids() {
		return tiaojianids;
	}

	public void setTiaojianids(String tiaojianids) {
		this.tiaojianids = tiaojianids;
	}

	public String getTutongjilx() {
		return tutongjilx;
	}

	public void setTutongjilx(String tutongjilx) {
		this.tutongjilx = tutongjilx;
	}

	public String getTutongjifx() {
		return tutongjifx;
	}

	public void setTutongjifx(String tutongjifx) {
		this.tutongjifx = tutongjifx;
	}

	public String getCanshudz() {
		return canshudz;
	}

	public void setCanshudz(String canshudz) {
		this.canshudz = canshudz;
	}

	@Length(min=0, max=100, message="统计标题长度必须介于 0 和 100 之间")
	public String getBiaoti() {
		return biaoti;
	}

	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}
	
	@Length(min=0, max=100, message="统计类型长度必须介于 0 和 100 之间")
	public String getTjtype() {
		return tjtype;
	}

	public void setTjtype(String tjtype) {
		this.tjtype = tjtype;
	}
	
	@Length(min=0, max=1, message="是否需要合计长度必须介于 0 和 1 之间")
	public String getIsheji() {
		return isheji;
	}

	public void setIsheji(String isheji) {
		this.isheji = isheji;
	}
	
	public String getSqlstr() {
		return sqlstr;
	}

	public void setSqlstr(String sqlstr) {
		this.sqlstr = sqlstr;
	}
	
}