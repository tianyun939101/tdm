package com.demxs.tdm.service.lab;

import com.demxs.tdm.dao.lab.LabTestUnitDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.lab.LabTestUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验室试验项Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabTestUnitService extends CrudService<LabTestUnitDao, LabTestUnit> {

	@Override
	public LabTestUnit get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<LabTestUnit> findList(LabTestUnit labTestUnit) {
		return super.findList(labTestUnit);
	}
	
	@Override
	public Page<LabTestUnit> findPage(Page<LabTestUnit> page, LabTestUnit labTestUnit) {
		return super.findPage(page, labTestUnit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(LabTestUnit labTestUnit) {
		super.save(labTestUnit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(LabTestUnit labTestUnit) {
		super.delete(labTestUnit);
	}
	
}