package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.configure.Baogaomb;
import com.demxs.tdm.domain.configure.Yuanshijlmb;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


/**
 * 试验项目试验项Entity
 * 试验信息
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestItemUnit extends DataEntity<TestItemUnit> {
	
	private static final long serialVersionUID = 1L;
	private String itemId;		// 项目ID
	private String unitId;		// 项ID
	private TestUnit unit;
	private String sname;		// 简称
	private String ename;		// 英文名称
	private Yuanshijlmb recordTemplate;		// 原始记录模版
	private Baogaomb reportTemplate; //报告模板
	private String status;		// 状态
	private Double duration;       //时长
	/*private List<ZhishiXx> standardFileList;*/
	private List<Standard> standardFileList;
	private String standardFile;    //标准文件
	private int sort;
	public TestItemUnit() {
		super();
	}

	public TestItemUnit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=64, message="项ID长度必须介于 0 和 64 之间")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public TestUnit getUnit() {
		return unit;
	}

	public void setUnit(TestUnit unit) {
		this.unit = unit;
	}

	@Length(min=0, max=50, message="简称长度必须介于 0 和 50 之间")
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Length(min=0, max=200, message="英文名称长度必须介于 0 和 200 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=64, message="原始记录模版长度必须介于 0 和 64 之间")
	public Yuanshijlmb getRecordTemplate() {
		return recordTemplate;
	}

	public void setRecordTemplate(Yuanshijlmb recordTemplate) {
		this.recordTemplate = recordTemplate;
	}
	
	@Length(min=0, max=100, message="状态长度必须介于 0 和 100 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public List<Standard> getStandardFileList() {
		return standardFileList;
	}

	public void setStandardFileList(List<Standard> standardFileList) {
		this.standardFileList = standardFileList;
	}

	public String getStandardFile() {
		if(standardFileList != null){
			List<String> ids = new ArrayList<>();
			for (Standard standard1 : standardFileList) {
				ids.add(standard1.getId());
			}
			return StringUtils.join(ids, ",");
		}
		return standardFile;
	}

	public void setStandardFile(String standardFile) {
		this.standardFile = standardFile;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Baogaomb getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(Baogaomb reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
}