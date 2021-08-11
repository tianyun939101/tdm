package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.TestSequence;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 试验室试验序列Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class LabTestSequence extends DataEntity<LabTestSequence> {
	
	private static final long serialVersionUID = 1L;
	private String labId;
	private String seqId;		// 试验序列ID
	private TestSequence sequence;
	private List<LabTestSequenceCondition> labTestSequenceConditions;

	public LabTestSequence() {
		super();
	}

	public LabTestSequence(String id){
		super(id);
	}

	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	@Length(min=0, max=64, message="试验序列ID长度必须介于 0 和 64 之间")
	public String getSeqId() {
		if (StringUtils.isEmpty(seqId) && sequence != null) {
			return sequence.getId();
		}
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public TestSequence getSequence() {
		if (sequence == null && StringUtils.isNotEmpty(seqId)) {
			sequence = new TestSequence();
			sequence.setId(seqId);
		}
		return sequence;
	}

	public void setSequence(TestSequence sequence) {
		this.sequence = sequence;
	}

	public List<LabTestSequenceCondition> getLabTestSequenceConditions() {
		return labTestSequenceConditions;
	}

	public void setLabTestSequenceConditions(List<LabTestSequenceCondition> labTestSequenceConditions) {
		this.labTestSequenceConditions = labTestSequenceConditions;
	}
}