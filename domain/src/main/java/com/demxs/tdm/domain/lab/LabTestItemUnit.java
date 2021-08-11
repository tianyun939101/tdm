package com.demxs.tdm.domain.lab;

import com.demxs.tdm.domain.ability.TestItemUnit;


/**
 * 试验室试验项目关联试验项配置
 */
public class LabTestItemUnit extends TestItemUnit {

	private static final long serialVersionUID = 1L;

	private String labItemId;

	public String getLabItemId() {
		return labItemId;
	}

	public void setLabItemId(String labItemId) {
		this.labItemId = labItemId;
	}
}