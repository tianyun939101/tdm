package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目试验条件Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestItemCodition extends DataEntity<TestItemCodition> {
	
	private static final long serialVersionUID = 1L;
	private String itemId;		// 试验项目ID
	private String conditionId;		// 试验条件
	private TestCondition condition;
	private String parameter;		// 参数
	
	public TestItemCodition() {
		super();
	}

	public TestItemCodition(String id){
		super(id);
	}

	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=64, message="试验条件长度必须介于 0 和 64 之间")
	public String getConditionId() {
		if (condition != null) {
			return condition.getId();
		}
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public TestCondition getCondition() {
		if (condition == null && StringUtils.isNotEmpty(conditionId)) {
			condition = new TestCondition();
			condition.setId(conditionId);
		}
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