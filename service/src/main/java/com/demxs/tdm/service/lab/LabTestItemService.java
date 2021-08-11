package com.demxs.tdm.service.lab;

import com.demxs.tdm.dao.lab.LabTestItemConditionDao;
import com.demxs.tdm.dao.lab.LabTestItemDao;
import com.demxs.tdm.dao.lab.LabTestItemUnitDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.domain.lab.LabTestItemCondition;
import com.demxs.tdm.domain.lab.LabTestItemUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 试验室试验项目Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabTestItemService extends CrudService<LabTestItemDao, LabTestItem> {

	@Resource
	private LabTestItemUnitDao labTestItemUnitDao;
	@Resource
	private LabTestItemConditionDao labTestItemConditionDao;

	@Override
	public LabTestItem get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<LabTestItem> findList(LabTestItem labTestItem) {
		return super.findList(labTestItem);
	}
	
	@Override
	public Page<LabTestItem> findPage(Page<LabTestItem> page, LabTestItem labTestItem) {
		return super.findPage(page, labTestItem);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(LabTestItem labTestItem) {
		super.save(labTestItem);
		if (labTestItem.getLabTestUnitsList() != null) {
			for (LabTestItemUnit labTestItemUnit : labTestItem.getLabTestUnitsList()) {
				labTestItemUnit.setId(IdGen.uuid());
				labTestItemUnit.setLabItemId(labTestItem.getId());
				labTestItemUnitDao.insert(labTestItemUnit);
			}
		}
		if (labTestItem.getLabTestItemConditions() != null) {
			for (LabTestItemCondition labTestItemCondition : labTestItem.getLabTestItemConditions()) {
				labTestItemCondition.setId(IdGen.uuid());
				labTestItemCondition.setLabItemId(labTestItem.getId());
				labTestItemConditionDao.insert(labTestItemCondition);
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(LabTestItem labTestItem) {
		super.delete(labTestItem);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByLabId(String labId) {
		List<LabTestItem> labTestItems = dao.findByLab(labId);
		for (LabTestItem labTestItem : labTestItems) {
			String labItemId = labTestItem.getId();
			labTestItemUnitDao.deleteByItem(labItemId);
			labTestItemConditionDao.deleteByItem(labItemId);
		}
		dao.deleteByLabId(labId);
	}

}