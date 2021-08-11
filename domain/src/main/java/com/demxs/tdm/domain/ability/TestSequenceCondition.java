package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目试验条件Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestSequenceCondition extends DataEntity<TestSequenceCondition> {

	private static final long serialVersionUID = 1L;
	private String seqId;		// 试验项目ID
	private String conditionId;		// 试验条件
	private TestCondition condition;
	private String parameter;		// 参数

	public TestSequenceCondition() {
		super();
	}

	public TestSequenceCondition(String id){
		super(id);
	}

	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	@Length(min=0, max=64, message="试验条件长度必须介于 0 和 64 之间")
	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public TestCondition getCondition() {
		return condition;
	}

	public void setCondition(TestCondition condition) {
		this.condition = condition;
	}

	@Length(min=0, max=50, message="参数长度必须介于 0 和 50 之间")
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
}