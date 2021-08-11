package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustOtherInfoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.EntrustOtherInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请其他信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustOtherInfoService extends CrudService<EntrustOtherInfoDao, EntrustOtherInfo> {

	@Override
	public EntrustOtherInfo get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustOtherInfo> findList(EntrustOtherInfo entrustOtherInfo) {
		return super.findList(entrustOtherInfo);
	}
	
	@Override
	public Page<EntrustOtherInfo> findPage(Page<EntrustOtherInfo> page, EntrustOtherInfo entrustOtherInfo) {
		return super.findPage(page, entrustOtherInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustOtherInfo entrustOtherInfo) {
		super.save(entrustOtherInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustOtherInfo entrustOtherInfo) {
		super.delete(entrustOtherInfo);
	}

	/**
	 * 根据申请单加载申请单其他信息
	 * @param entrustId 申请单ID
	 * @return
	 */
	public EntrustOtherInfo getByEntrustId(String entrustId) throws ServiceException{
		return this.dao.getByEntrustId(entrustId);
	}
	
}