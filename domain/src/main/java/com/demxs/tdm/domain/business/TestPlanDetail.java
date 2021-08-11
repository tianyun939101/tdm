package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 试验组试验计划详情Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestPlanDetail extends DataEntity<TestPlanDetail> {
	
	private static final long serialVersionUID = 1L;
	private String entrustCode;		// 申请单号
	private String entrustId;		// 申请单ID
	private String sampleId;		// 样品id
	private Integer sampleAmount;	// 样品数量
	private List<EntrustSampleGroupItem> sampleList;
//	private String deviceId;
	private List<Shebei> deviceList;
	private String tGroupId;		// 试验组ID
	private String planId;		// 计划ID
	private String abilityId;		// 试验能力ID
	private String abilityName;		// 试验能力名称
	private String parentId;		// 上级ID
	private String parentIds;		// 所有上级ID
	private Integer abilityType;		// 试验能力类别，1：试验序列/分支，2：试验项目，3：试验项
	private Double standardTime;		// 标准用时
	private Double expectTime;		// 预计用时
	private Date planStartDate;		// 计划开始时间
	private Date planEndDate;		// 计划结束时间
	private Integer sort;	//顺序
	private List<TestPlanDetail> childDetail = new ArrayList<TestPlanDetail>();	//下级计划
	private List<TestPlanExecuteDetail> testPlanExecuteDetails;	//试验计划试验项执行详情
	private TestPlan testPlan;
	private String entrustAbilityId;
	private TestTask task;
	public boolean isCaculated = false;//是否已计算，用于计算判断
	
	public TestPlanDetail() {
		super();
	}

	public TestPlanDetail(String id){
		super(id);
	}

	@Length(min=0, max=100, message="申请单号长度必须介于 0 和 100 之间")
	public String getEntrustCode() {
		return entrustCode;
	}

	public void setEntrustCode(String entrustCode) {
		this.entrustCode = entrustCode;
	}
	
	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=64, message="样品id长度必须介于 0 和 64 之间")
	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public Integer getSampleAmount() {
		return sampleAmount;
	}

	public void setSampleAmount(Integer sampleAmount) {
		this.sampleAmount = sampleAmount;
	}

	public List<EntrustSampleGroupItem> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<EntrustSampleGroupItem> sampleList) {
		this.sampleList = sampleList;
	}

	public String getDeviceId() {
		List<Shebei> deviceList = getDeviceList();
		if (deviceList != null) {
			List<String> ids = new ArrayList<>();
			for (Shebei shebei : deviceList) {
				if(!ids.contains(shebei.getId())){
					ids.add(shebei.getId());
				}
			}
			return StringUtils.join(ids, ",");
		}
		return null;
	}

//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}

	public List<Shebei> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Shebei> deviceList) {
		this.deviceList = deviceList;
	}


	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String gettGroupId() {
		return tGroupId;
	}

	public void settGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}

	@Length(min=0, max=64, message="计划ID长度必须介于 0 和 64 之间")
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Length(min=0, max=64, message="试验能力ID长度必须介于 0 和 64 之间")
	public String getAbilityId() {
		return abilityId;
	}

	public void setAbilityId(String abilityId) {
		this.abilityId = abilityId;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Length(min=0, max=1000, message="所有上级ID长度必须介于 0 和 1000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(Integer abilityType) {
		this.abilityType = abilityType;
	}

	public Double getStandardTime() {
		return standardTime;
	}

	public void setStandardTime(Double standardTime) {
		this.standardTime = standardTime;
	}
	
	public Double getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(Double expectTime) {
		this.expectTime = expectTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<TestPlanDetail> getChildDetail() {
		return childDetail;
	}

	public void setChildDetail(List<TestPlanDetail> childDetail) {
		this.childDetail = childDetail;
	}

	public void addChildDetail(TestPlanDetail testPlanDetail) {
		this.childDetail.add(testPlanDetail);
	}
	public List<TestPlanExecuteDetail> getTestPlanExecuteDetails() {
		return testPlanExecuteDetails;
	}

	public void setTestPlanExecuteDetails(List<TestPlanExecuteDetail> testPlanExecuteDetails) {
		this.testPlanExecuteDetails = testPlanExecuteDetails;
	}

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public String getEntrustAbilityId() {
		return entrustAbilityId;
	}

	public void setEntrustAbilityId(String entrustAbilityId) {
		this.entrustAbilityId = entrustAbilityId;
	}

	public TestTask getTask() {
		return task;
	}

	public void setTask(TestTask task) {
		this.task = task;
	}
}