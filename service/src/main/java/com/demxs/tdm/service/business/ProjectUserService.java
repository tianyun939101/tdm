package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.ProjectUserDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目用户Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ProjectUserService extends CrudService<ProjectUserDao, ProjectUser> {

	@Autowired
	private ProjectUserDao projectUserDao;
	@Override
	public ProjectUser get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ProjectUser> findList(ProjectUser projectUser) {
		return super.findList(projectUser);
	}
	
	@Override
	public Page<ProjectUser> findPage(Page<ProjectUser> page, ProjectUser projectUser) {
		return super.findPage(page, projectUser);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ProjectUser projectUser) {
		super.save(projectUser);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ProjectUser projectUser) {
		super.delete(projectUser);
	}

	/**
	 * 删除项目的成员
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByProjectInfo(String projectInfoId){
		projectUserDao.deleteByProjectId(projectInfoId);
	}
	
}