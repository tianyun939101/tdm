package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 试验序列Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestSequence extends DataEntity<TestSequence> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 中文名称
	private String ename;		// 英文名称
	private String sname;		// 简称
	private String standardFile;		// 标准文件
	private List<ZhishiXx> standardFileList;
	private String summary;		// 简介
	private Version version;		// 版本号
	private String otherFile;		// 附件
	private String labId;		// 试验室id
	private String status;//状态
	private List<TestSequenceItem> testItemsList;
	private List<TestSequenceCondition> conditionsList;

	public TestSequence() {
		super();
	}

	public TestSequence(String id){
		super(id);
	}

	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=100, message="简称长度必须介于 0 和 100 之间")
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Length(min=0, max=64, message="标准文件长度必须介于 0 和 64 之间")
	public String getStandardFile() {
		return standardFile;
	}

	public void setStandardFile(String standardFile) {
		this.standardFile = standardFile;
	}
	
	@Length(min=0, max=500, message="简介长度必须介于 0 和 500 之间")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Length(min=0, max=64, message="版本号长度必须介于 0 和 64 之间")
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getOtherFile() {
		return otherFile;
	}

	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}
	
	@Length(min=0, max=64, message="试验室id长度必须介于 0 和 64 之间")
	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ZhishiXx> getStandardFileList() {
		return standardFileList;
	}

	public void setStandardFileList(List<ZhishiXx> standardFileList) {
		this.standardFileList = standardFileList;
	}

	public List<TestSequenceItem> getTestItemsList() {
		return testItemsList;
	}

	public void setTestItemsList(List<TestSequenceItem> testItemsList) {
		this.testItemsList = testItemsList;
	}

	public List<TestSequenceCondition> getConditionsList() {
		return conditionsList;
	}

	public void setConditionsList(List<TestSequenceCondition> conditionsList) {
		this.conditionsList = conditionsList;
	}
}