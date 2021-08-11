package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustSampleBomDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.EntrustSampleBom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请单样品BOMService
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustSampleBomService extends CrudService<EntrustSampleBomDao, EntrustSampleBom> {

	@Override
	public EntrustSampleBom get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustSampleBom> findList(EntrustSampleBom entrustSampleBom) {
		return super.findList(entrustSampleBom);
	}
	
	@Override
	public Page<EntrustSampleBom> findPage(Page<EntrustSampleBom> page, EntrustSampleBom entrustSampleBom) {
		return super.findPage(page, entrustSampleBom);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustSampleBom entrustSampleBom) {
		super.save(entrustSampleBom);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustSampleBom entrustSampleBom) {
		super.delete(entrustSampleBom);
	}

	/**
	 * 根据申请单ID删除所有样品BOM信息
	 * @param entrustId 申请单ID
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByEntrustId(String entrustId) throws ServiceException{
		this.dao.deleteByEntrustId(entrustId);
	}

	public List<EntrustSampleBom> listBySampleGroupId(String sampleGroupId) throws ServiceException{
		return this.dao.findBySampleGroupdId(sampleGroupId);
	}
}