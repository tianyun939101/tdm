package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 样品确认记录Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class SapmleConfirmRecord extends DataEntity<SapmleConfirmRecord> {
	
	private static final long serialVersionUID = 1L;
	private String sampleGroupId;		// 样品组ID
	private Integer flag;		// 是否确认
	
	public SapmleConfirmRecord() {
		super();
	}

	public SapmleConfirmRecord(String id){
		super(id);
	}

	@Length(min=0, max=64, message="样品组ID长度必须介于 0 和 64 之间")
	public String getSampleGroupId() {
		return sampleGroupId;
	}

	public void setSampleGroupId(String sampleGroupId) {
		this.sampleGroupId = sampleGroupId;
	}
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}