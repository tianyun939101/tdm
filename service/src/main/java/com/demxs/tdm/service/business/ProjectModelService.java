package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.ProjectModelDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.ProjectModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目模块Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ProjectModelService extends CrudService<ProjectModelDao, ProjectModel> {

	@Override
	public ProjectModel get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ProjectModel> findList(ProjectModel projectModel) {
		return super.findList(projectModel);
	}
	
	@Override
	public Page<ProjectModel> findPage(Page<ProjectModel> page, ProjectModel projectModel) {
		return super.findPage(page, projectModel);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ProjectModel projectModel) {
		super.save(projectModel);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ProjectModel projectModel) {
		super.delete(projectModel);
	}
	
}