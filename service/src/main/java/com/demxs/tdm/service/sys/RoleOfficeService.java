package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.RoleOfficeDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.sys.RoleOffice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色组织关系Service
 * @author 詹小梅
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RoleOfficeService extends CrudService<RoleOfficeDao, RoleOffice> {

	@Override
	public RoleOffice get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<RoleOffice> findList(RoleOffice roleOffice) {
		return super.findList(roleOffice);
	}
	
	@Override
	public Page<RoleOffice> findPage(Page<RoleOffice> page, RoleOffice roleOffice) {
		return super.findPage(page, roleOffice);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(RoleOffice roleOffice) {
		super.save(roleOffice);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(RoleOffice roleOffice) {
		super.delete(roleOffice);
	}
	
}