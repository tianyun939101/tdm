package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.TestCondition;
import org.hibernate.validator.constraints.Length;

/**
 * 申请单试验项目试验条件Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustTestItemCodition extends DataEntity<EntrustTestItemCodition> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单id
	private String tGroupId;		// 试验组id
	private String itemId;		// 试验项目ID
	private TestCondition testCondition;	//试验条件
	private String parameter;		// 参数
	private String entrustAbilityId;//关联申请试验能力
	
	public EntrustTestItemCodition() {
		super();
	}

	public EntrustTestItemCodition(String id){
		super(id);
	}

	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public TestCondition getTestCondition() {
		return testCondition;
	}

	public void setTestCondition(TestCondition testCondition) {
		this.testCondition = testCondition;
	}

	@Length(min=0, max=64, message="试验组id长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}
	
	@Length(min=0, max=50, message="参数长度必须介于 0 和 50 之间")
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	@Length(min=0, max=64, message="申请单id长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}

	public String getEntrustAbilityId() {
		return entrustAbilityId;
	}

	public void setEntrustAbilityId(String entrustAbilityId) {
		this.entrustAbilityId = entrustAbilityId;
	}
}