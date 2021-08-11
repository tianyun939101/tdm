package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.UserLabDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.sys.UserLab;
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
public class UserLabService extends CrudService<UserLabDao, UserLab> {

	@Override
	public UserLab get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<UserLab> findList(UserLab userLab) {
		return super.findList(userLab);
	}
	
	@Override
	public Page<UserLab> findPage(Page<UserLab> page, UserLab userLab) {
		return super.findPage(page, userLab);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(UserLab userLab) {
		super.save(userLab);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(UserLab userLab) {
		super.delete(userLab);
	}
	
}