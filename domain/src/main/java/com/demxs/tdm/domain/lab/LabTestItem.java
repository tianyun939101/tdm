package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.TestItem;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 试验室试验项目Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class LabTestItem extends DataEntity<LabTestItem> {
	
	private static final long serialVersionUID = 1L;
	private String labId;
	private String itemId;		// 试验项ID
	private TestItem item;

	private List<LabTestItemUnit> labTestUnitsList;
	private List<LabTestItemCondition>  labTestItemConditions;


	public LabTestItem() {
		super();
	}

	public LabTestItem(String id){
		super(id);
	}

	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	@Length(min=0, max=64, message="试验项ID长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public TestItem getItem() {
		return item;
	}

	public void setItem(TestItem item) {
		this.item = item;
	}

	public List<LabTestItemUnit> getLabTestUnitsList() {
		return labTestUnitsList;
	}

	public void setLabTestUnitsList(List<LabTestItemUnit> labTestUnitsList) {
		this.labTestUnitsList = labTestUnitsList;
	}

	public List<LabTestItemCondition> getLabTestItemConditions() {
		return labTestItemConditions;
	}

	public void setLabTestItemConditions(List<LabTestItemCondition> labTestItemConditions) {
		this.labTestItemConditions = labTestItemConditions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LabTestItem)) return false;
//		if (!super.equals(o)) return false;

		LabTestItem that = (LabTestItem) o;

		if (!labId.equals(that.labId)) return false;
		return item.getId().equals(that.item.getId());
	}

	@Override
	public int hashCode() {
		int result = labId.hashCode();
		result = 31 * result + item.getId().hashCode();
		return result;
	}
}