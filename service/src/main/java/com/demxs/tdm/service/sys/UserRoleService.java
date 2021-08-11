package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.UserRoleDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.sys.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色人员关系Service
 * @author 詹小梅
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class UserRoleService extends CrudService<UserRoleDao, UserRole> {

	@Override
	public UserRole get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<UserRole> findList(UserRole userRole) {
		return super.findList(userRole);
	}
	
	@Override
	public Page<UserRole> findPage(Page<UserRole> page, UserRole userRole) {
		return super.findPage(page, userRole);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(UserRole userRole) {
		super.save(userRole);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(UserRole userRole) {
		super.delete(userRole);
	}
	
}