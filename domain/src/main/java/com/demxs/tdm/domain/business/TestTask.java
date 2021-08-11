package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.comac.common.constant.SampleConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 试验任务分配Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestTask extends DataEntity<TestTask> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;	//父ID
	private String executionId;	//执行干道ID
	private String businessKey;	//业务主键
	private String taskName;	//任务名称
	private Date startTime;		// 开始时间
	/*private Double duration;		// 工作时长*/
	private Date endTime;		// 结束时间
	private Date planStartDate;		// 计划开始时间
	private Date planEndDate;		// 计划结束时间
	private String taskCode;		// 任务编号
	private Integer sort;	//任务序号
	private Integer status;		// 任务状态
	private String owner;		// 试验员ID
	private String ownerName;	//试验人员名称
	private String stopReason;	//终止原因
	private String sampleId;	//样品ID
	private String deviceId;	//设备ID
	private String planDetailId;	//计划ID
	private User auditUser;	//任务审核人
	private String originRecordId;	//原始记录ID
	private TestTask currentTask;//试验项目任务正在执行的试验项

	private Date auditDate;//审核日期
	private String deviceCodes;//设备编号
	private Integer sampleType;	//样品类型
	private List<EntrustSampleGroupItem> sampleList;	//样品
	private TestPlanDetail testPlanDetail;

	private List<EntrustTestItemCodition> conditions;


	private User user;//记录人 给原始记录单传值  简单处理

	private EntrustSampleGroupItem item;//样品 仅供打印试验项目功能使用

	private String imgIds;//采集文件IDS

	private List<Map<String,String>> filePath = new ArrayList<>();//采集文件路径

	private String fileIds;

	/**
	 * 任务样品状态 0待入库 1待检 2在检 3已检 4已归还 5已报废  数据字典
	 */
	private Integer sampleFlag;

	public TestTask() {
		super();
	}

	public TestTask(String id){
		super(id);
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(String planDetailId) {
		this.planDetailId = planDetailId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getDuration() {
		if(this.getEndTime() == null && this.getStartTime()!=null){
			return DateUtils.formatDateTime(System.currentTimeMillis() - this.getStartTime().getTime());
		}else if(this.getEndTime() != null && this.getStartTime()!=null){
			return DateUtils.formatDateTime(this.getEndTime().getTime() - this.getStartTime().getTime());
		}
		return "";
	}

	/*public void setDuration(Double duration) {
		this.duration = duration;
	}*/

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getSampleType() {
		return sampleType;
	}

	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
	}

	public List<EntrustSampleGroupItem> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<EntrustSampleGroupItem> sampleList) {
		this.sampleList = sampleList;
	}

	public String getOriginRecordId() {
		return originRecordId;
	}

	public void setOriginRecordId(String originRecordId) {
		this.originRecordId = originRecordId;
	}

	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}

	public TestPlanDetail getTestPlanDetail() {
		return testPlanDetail;
	}

	public void setTestPlanDetail(TestPlanDetail testPlanDetail) {
		this.testPlanDetail = testPlanDetail;
	}

	public TestTask getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(TestTask currentTask) {
		this.currentTask = currentTask;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getDeviceCodes() {
		return deviceCodes;
	}

	public void setDeviceCodes(String deviceCodes) {
		this.deviceCodes = deviceCodes;
	}

	public List<EntrustTestItemCodition> getConditions() {
		return conditions;
	}

	public void setConditions(List<EntrustTestItemCodition> conditions) {
		this.conditions = conditions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public EntrustSampleGroupItem getItem() {
		return item;
	}

	public void setItem(EntrustSampleGroupItem item) {
		this.item = item;
	}

	public String getImgIds() {
		return imgIds;
	}

	public void setImgIds(String imgIds) {
		this.imgIds = imgIds;
	}

	public List<Map<String, String>> getFilePath() {
		return filePath;
	}

	public void setFilePath(List<Map<String, String>> filePath) {
		this.filePath = filePath;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getNoSampleName(){
		StringBuilder sb = new StringBuilder();
		if(sampleList == null){
			return "";
		}
		for(int i=0;i<sampleList.size();i++){
			EntrustSampleGroupItem sampleGroupItem = sampleList.get(i);
			sb.append(i).append(".").append(sampleGroupItem.getName()).append("\t\n");
		}
		return sb.toString();
	}

	public String getSampleName(){
		StringBuilder sb = new StringBuilder();
		if(sampleList == null){
			return "";

		}
		for (EntrustSampleGroupItem sampleGroupItem : sampleList) {
			sb.append(sampleGroupItem.getName()).append(";");
		}
		return sb.toString();
	}

	public Integer getSampleFlag() {
		if(this.getSampleList() == null){
			return SampleConstants.sampleStatus.NO_IN;
		}
		for (EntrustSampleGroupItem sample : this.getSampleList()){
			if(SampleConstants.sampleStatus.NO_IN.equals(sample.getSampleStatus())){
				return SampleConstants.sampleStatus.NO_IN;
			}
			if(SampleConstants.sampleStatus.WAIT_INSPECTION.equals(sample.getSampleStatus())){
				return SampleConstants.sampleStatus.WAIT_INSPECTION;
			}
		}
		return SampleConstants.sampleStatus.IN_INSPECTION;
	}
}