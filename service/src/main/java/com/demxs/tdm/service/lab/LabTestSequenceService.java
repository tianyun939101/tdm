package com.demxs.tdm.service.lab;

import com.demxs.tdm.dao.lab.LabTestSequenceConditionDao;
import com.demxs.tdm.dao.lab.LabTestSequenceDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.domain.lab.LabTestSequence;
import com.demxs.tdm.domain.lab.LabTestSequenceCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 试验室试验序列Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabTestSequenceService extends CrudService<LabTestSequenceDao, LabTestSequence> {

	@Resource
	private LabTestSequenceConditionDao labTestSequenceConditionDao;

	@Override
	public LabTestSequence get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<LabTestSequence> findList(LabTestSequence labTestSequence) {
		return super.findList(labTestSequence);
	}
	
	@Override
	public Page<LabTestSequence> findPage(Page<LabTestSequence> page, LabTestSequence labTestSequence) {
		return super.findPage(page, labTestSequence);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(LabTestSequence labTestSequence) {
		super.save(labTestSequence);
		if (labTestSequence.getLabTestSequenceConditions() != null) {
			for (LabTestSequenceCondition labTestSequenceCondition : labTestSequence.getLabTestSequenceConditions()) {
				labTestSequenceCondition.setId(IdGen.uuid());
				labTestSequenceCondition.setLabSeqId(labTestSequence.getId());
				labTestSequenceConditionDao.insert(labTestSequenceCondition);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(LabTestSequence labTestSequence) {
		super.delete(labTestSequence);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByLabId(String labId) {
		List<LabTestSequence> labTestSequences = dao.findByLab(labId);
		for (LabTestSequence labTestSequence : labTestSequences) {
			labTestSequenceConditionDao.deleteBySequence(labTestSequence.getId());
		}
		dao.deleteByLabId(labId);
	}
}