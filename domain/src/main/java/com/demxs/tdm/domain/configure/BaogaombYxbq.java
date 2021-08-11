package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 报告模板已选标签Entity
 * @author TDM
 * @version 2017-12-13
 */
public class BaogaombYxbq extends DataEntity<BaogaombYxbq> {
	
	private static final long serialVersionUID = 1L;
	private String mbid;		// 模板ID
	private String bqid;		// 标签ID 或 表格标签模板ID
	private String bqenname;		// 标签英文名称
	private String bqcnname;		// 标签中文名称
	private String biaoqianfs;		// 标签方式：value 赋值标签、table 表格标签
	private String rkzdid;		// 入库字段id
	private String biaoqianlx;		// 标签类型： form 表单, list 列表
	private String biaoqiansx; //标签属性

	private int rowindex; //标签所在横坐标
	private int colindex; //标签所在纵坐标

	private String sfhb ; //是否合并

	public String getSfhb() {
		return sfhb;
	}

	public void setSfhb(String sfhb) {
		this.sfhb = sfhb;
	}

	public int getRowindex() {
		return rowindex;
	}

	public void setRowindex(int rowindex) {
		this.rowindex = rowindex;
	}

	public int getColindex() {
		return colindex;
	}

	public void setColindex(int colindex) {
		this.colindex = colindex;
	}

	public String getBiaoqiansx() {
		return biaoqiansx;
	}

	public void setBiaoqiansx(String biaoqiansx) {
		this.biaoqiansx = biaoqiansx;
	}

	private List<BaogaombYxbq> yxbqFormlist;
	private List<BaogaombYxbq> yxbqListlist;

	public List<BaogaombYxbq> getYxbqFormlist() {
		return yxbqFormlist;
	}

	public void setYxbqFormlist(List<BaogaombYxbq> yxbqFormlist) {
		this.yxbqFormlist = yxbqFormlist;
	}

	public List<BaogaombYxbq> getYxbqListlist() {
		return yxbqListlist;
	}

	public void setYxbqListlist(List<BaogaombYxbq> yxbqListlist) {
		this.yxbqListlist = yxbqListlist;
	}

	public BaogaombYxbq() {
		super();
	}

	public BaogaombYxbq(String id){
		super(id);
	}

	@Length(min=0, max=64, message="模板ID长度必须介于 0 和 64 之间")
	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	
	@Length(min=0, max=64, message="标签ID 或 表格标签模板ID长度必须介于 0 和 64 之间")
	public String getBqid() {
		return bqid;
	}

	public void setBqid(String bqid) {
		this.bqid = bqid;
	}
	
	@Length(min=0, max=200, message="标签英文名称长度必须介于 0 和 200 之间")
	public String getBqenname() {
		return bqenname;
	}

	public void setBqenname(String bqenname) {
		this.bqenname = bqenname;
	}
	
	@Length(min=0, max=200, message="标签中文名称长度必须介于 0 和 200 之间")
	public String getBqcnname() {
		return bqcnname;
	}

	public void setBqcnname(String bqcnname) {
		this.bqcnname = bqcnname;
	}
	
	@Length(min=0, max=64, message="标签方式：value 赋值标签、table 表格标签长度必须介于 0 和 64 之间")
	public String getBiaoqianfs() {
		return biaoqianfs;
	}

	public void setBiaoqianfs(String biaoqianfs) {
		this.biaoqianfs = biaoqianfs;
	}
	
	@Length(min=0, max=64, message="入库字段id长度必须介于 0 和 64 之间")
	public String getRkzdid() {
		return rkzdid;
	}

	public void setRkzdid(String rkzdid) {
		this.rkzdid = rkzdid;
	}
	
	@Length(min=0, max=64, message="标签类型： form 表单, list 列表长度必须介于 0 和 64 之间")
	public String getBiaoqianlx() {
		return biaoqianlx;
	}

	public void setBiaoqianlx(String biaoqianlx) {
		this.biaoqianlx = biaoqianlx;
	}
	
}