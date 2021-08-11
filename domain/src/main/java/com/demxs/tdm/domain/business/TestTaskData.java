package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 试验任务数据Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestTaskData extends DataEntity<TestTaskData> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String tGroupId;		// 试验组ID
	private String sampleId;		// 样品id
	private String seqId;		// 试验序列ID
	private String itemId;		// 试验项目ID
	private String unitId;		// 试验项ID
	private String taskId;		// 任务ID
	private String sampleCode;		//样品编号
	private Integer type;		// 数据类型(1:数据文件,2:图片)
	private String fileId;		// 数据文件
	
	public TestTaskData() {
		super();
	}

	public TestTaskData(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}
	
	@Length(min=0, max=64, message="样品id长度必须介于 0 和 64 之间")
	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	
	@Length(min=0, max=64, message="试验序列ID长度必须介于 0 和 64 之间")
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=64, message="试验项ID长度必须介于 0 和 64 之间")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@Length(min=0, max=64, message="任务ID长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=0, max=100, message="样品编号长度必须介于 0 和 100 之间")
	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=64, message="数据文件长度必须介于 0 和 64 之间")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}