package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustTestItemCoditionDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.EntrustTestItemCodition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请单试验项目试验条件Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustTestItemCoditionService extends CrudService<EntrustTestItemCoditionDao, EntrustTestItemCodition> {

	@Override
	public EntrustTestItemCodition get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustTestItemCodition> findList(EntrustTestItemCodition entrustTestItemCodition) {
		return super.findList(entrustTestItemCodition);
	}
	
	@Override
	public Page<EntrustTestItemCodition> findPage(Page<EntrustTestItemCodition> page, EntrustTestItemCodition entrustTestItemCodition) {
		return super.findPage(page, entrustTestItemCodition);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustTestItemCodition entrustTestItemCodition) {
		super.save(entrustTestItemCodition);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustTestItemCodition entrustTestItemCodition) {
		super.delete(entrustTestItemCodition);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByEntrustId(String entrustId) throws ServiceException{
		this.dao.deleteByEntrustId(entrustId);
	}

	/**
	 * 根据试验组ID与试验项目ID获取试验项目的试验条件参数
	 * @param testGroupId   试验组ID
	 * @param itemId    试验项目ID
	 * @return
	 */
	public List<EntrustTestItemCodition> listByTestGroupIdAndItemId(String testGroupId,String itemId) throws
			ServiceException{
		return this.dao.listByTestGroupIdAndItemId(testGroupId,itemId);
	}

	/**
	 * 根据能力ID 查询关联条件
	 * @param entrustAbilityId
	 * @return
	 * @throws ServiceException
	 */
	public List<EntrustTestItemCodition> listByEntrustAbilityId(String entrustAbilityId) throws
			ServiceException{
		return this.dao.listByEntrustAbilityId(entrustAbilityId);
	}
}