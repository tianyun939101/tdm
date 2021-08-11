package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 试验室试验项Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class LabTestUnit extends DataEntity<LabTestUnit> {
	
	private static final long serialVersionUID = 1L;
	private String unitId;		// 试验项ID
	private Double duration;		// 试验用时
	
	public LabTestUnit() {
		super();
	}

	public LabTestUnit(String id){
		super(id);
	}

	@Length(min=0, max=64, message="试验项ID长度必须介于 0 和 64 之间")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
}