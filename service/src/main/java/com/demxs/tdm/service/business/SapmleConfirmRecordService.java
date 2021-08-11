package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.SapmleConfirmRecordDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.SapmleConfirmRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品确认记录Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class SapmleConfirmRecordService extends CrudService<SapmleConfirmRecordDao, SapmleConfirmRecord> {

	@Override
	public SapmleConfirmRecord get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<SapmleConfirmRecord> findList(SapmleConfirmRecord sapmleConfirmRecord) {
		return super.findList(sapmleConfirmRecord);
	}
	
	@Override
	public Page<SapmleConfirmRecord> findPage(Page<SapmleConfirmRecord> page, SapmleConfirmRecord sapmleConfirmRecord) {
		return super.findPage(page, sapmleConfirmRecord);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(SapmleConfirmRecord sapmleConfirmRecord) {
		super.save(sapmleConfirmRecord);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(SapmleConfirmRecord sapmleConfirmRecord) {
		super.delete(sapmleConfirmRecord);
	}
	
}