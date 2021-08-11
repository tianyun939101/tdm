package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestSequenceConditionDao;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.ability.TestSequenceCondition;
import org.springframework.stereotype.Service;

/**
 * Created by chenjinfan on 2017/12/21.
 */
@Service
public class TestSequenceConditionService extends CrudService<TestSequenceConditionDao, TestSequenceCondition> {

	/**
	 * 删除试验序列所有关联项
	 *
	 * @param seqId
	 */
	public void deleteBySequence(String seqId) {
		dao.deleteBySequence(seqId);
	}
}
