package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.business.vo.TaskExecuteVO;
import com.demxs.tdm.domain.dataCenter.ZyTestMethod;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 申请试验需求Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustTestGroup extends DataEntity<EntrustTestGroup> {
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private List<EntrustSampleGroup> sampleGroups;	//样品组列表
	private List<EntrustTestGroupAbility> abilityList;	//试验能力列表
	private Integer abilityType;		// 能力类别，1：试验项，2：试验项目，3：试验序列，4：自定义序列
	private Integer beforProcess;	//是否需要预处理
	private TestPlan testPlan;	//试验计划
	private List<TaskExecuteVO> taskExecuteList;
	private Integer sort;
	private Integer disableFlag;//是否终止试验

	
	public EntrustTestGroup() {
		super();
	}

	public EntrustTestGroup(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	public Integer getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(Integer abilityType) {
		this.abilityType = abilityType;
	}

	public List<EntrustSampleGroup> getSampleGroups() {
		return sampleGroups;
	}

	public void setSampleGroups(List<EntrustSampleGroup> sampleGroups) {
		this.sampleGroups = sampleGroups;
	}

	public List<EntrustTestGroupAbility> getAbilityList() {
		return abilityList;
	}

	public void setAbilityList(List<EntrustTestGroupAbility> abilityList) {
		this.abilityList = abilityList;
	}

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public Integer getBeforProcess() {
		return beforProcess;
	}

	public void setBeforProcess(Integer beforProcess) {
		this.beforProcess = beforProcess;
	}

	public List<TaskExecuteVO> getTaskExecuteList() {
		return taskExecuteList;
	}

	public void setTaskExecuteList(List<TaskExecuteVO> taskExecuteList) {
		this.taskExecuteList = taskExecuteList;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(Integer disableFlag) {
		this.disableFlag = disableFlag;
	}
}