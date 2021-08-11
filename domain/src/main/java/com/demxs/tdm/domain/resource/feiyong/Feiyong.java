package com.demxs.tdm.domain.resource.feiyong;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;


/**
 * 试验费用Entity
 * @author sunjunhui
 * @version 2017-11-07
 */
public class Feiyong extends DataEntity<Feiyong> {
	
	private static final long serialVersionUID = 1L;
	private String itemId;		// 试验项目ID
	private String itemName;		// 试验项目名称
	private String testMoney;		// 试验费用
	private String weights;		// 工时考核权重


	private String upValue;//页面传值
	
	public Feiyong() {
		super();
	}

	public Feiyong(String id){
		super(id);
	}

	@Length(min=0, max=64, message="试验项目ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=64, message="试验项目名称长度必须介于 0 和 64 之间")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Length(min=0, max=64, message="试验费用长度必须介于 0 和 64 之间")
	public String getTestMoney() {
		return testMoney;
	}

	public void setTestMoney(String testMoney) {
		this.testMoney = testMoney;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	@JsonIgnore
	public String getUpValue() {
		return upValue;
	}

	public void setUpValue(String upValue) {
		this.upValue = upValue;
	}
}