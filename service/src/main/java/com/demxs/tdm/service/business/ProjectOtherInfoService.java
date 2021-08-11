package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.ProjectOtherInfoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.ProjectOtherInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目其他信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ProjectOtherInfoService extends CrudService<ProjectOtherInfoDao, ProjectOtherInfo> {

	@Override
	public ProjectOtherInfo get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ProjectOtherInfo> findList(ProjectOtherInfo projectOtherInfo) {
		return super.findList(projectOtherInfo);
	}
	
	@Override
	public Page<ProjectOtherInfo> findPage(Page<ProjectOtherInfo> page, ProjectOtherInfo projectOtherInfo) {
		return super.findPage(page, projectOtherInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ProjectOtherInfo projectOtherInfo) {
		super.save(projectOtherInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ProjectOtherInfo projectOtherInfo) {
		super.delete(projectOtherInfo);
	}
	
}