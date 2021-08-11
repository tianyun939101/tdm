package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustTestGroupAbilityDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.EntrustTestGroupAbility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验组试验能力Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustTestGroupAbilityService extends CrudService<EntrustTestGroupAbilityDao, EntrustTestGroupAbility> {

	@Override
	public EntrustTestGroupAbility get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustTestGroupAbility> findList(EntrustTestGroupAbility entrustTestGroupAbility) {
		return super.findList(entrustTestGroupAbility);
	}
	
	@Override
	public Page<EntrustTestGroupAbility> findPage(Page<EntrustTestGroupAbility> page, EntrustTestGroupAbility entrustTestGroupAbility) {
		return super.findPage(page, entrustTestGroupAbility);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustTestGroupAbility entrustTestGroupAbility) {
		super.save(entrustTestGroupAbility);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustTestGroupAbility entrustTestGroupAbility) {
		super.delete(entrustTestGroupAbility);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByEntrustId(String entrustId) throws ServiceException{
		this.dao.deleteByEntrustId(entrustId);
	}

	/**
	 * 根据试验组ID加载试验能力数据
	 * @param testGroupId 试验组ID
	 * @return
	 */
	public List<EntrustTestGroupAbility> listByTestGroupId(String testGroupId) throws ServiceException{
		return this.dao.listByTestGroupId(testGroupId);
	}
	/**
	 * 根据试验组ID加载试验能力数据，包含试验项目详情（试验序列只返回ID）
	 * @param testGroupId 试验组ID
	 * @return
	 */
	public List<EntrustTestGroupAbility> listDetailByTestGroupId(String testGroupId) throws ServiceException{
		return this.dao.listDetailByTestGroupId(testGroupId);
	}
}