package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.ProjectTypeDao;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.ProjectType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目类型Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ProjectTypeService extends TreeService<ProjectTypeDao, ProjectType> {

	@Override
	public ProjectType get(String id) {
		ProjectType p = this.dao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public List<ProjectType> findList(ProjectType projectType) {
		if (StringUtils.isNotBlank(projectType.getParentIds())){
			projectType.setParentIds(","+projectType.getParentIds()+",");
		}
		return super.findList(projectType);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ProjectType projectType) {
		super.save(projectType);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ProjectType projectType) {
		super.delete(projectType);
	}


}