package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.ProjectInfoDao;
import com.demxs.tdm.dao.business.ProjectUserDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.ProjectInfo;
import com.demxs.tdm.domain.business.ProjectUser;
import com.demxs.tdm.service.sys.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验项目Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ProjectInfoService extends CrudService<ProjectInfoDao, ProjectInfo> {

	@Autowired
	private ProjectUserDao projectUserDao;
	@Autowired
	private  ProjectUserService projectUserService;
	@Autowired
	private OfficeService officeService;
	@Override
	public ProjectInfo get(String id) {
		ProjectInfo projectInfo = super.get(id);
		//获取项目成员信息
		projectInfo.setUsers(projectUserDao.getProjectUsersByProjectId(projectInfo.getId(),null));
		return projectInfo;
	}
	
	@Override
	public List<ProjectInfo> findList(ProjectInfo projectInfo) {
		List<ProjectInfo> projectInfos = super.findList(projectInfo);
		if(!Collections3.isEmpty(projectInfos)){
			for(ProjectInfo p:projectInfos){
				p.setUsers(projectUserDao.getProjectUsersByProjectId(projectInfo.getId(),null));
			}
		}
		return projectInfos;
	}
	
	@Override
	public Page<ProjectInfo> findPage(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		projectInfo.getSqlMap().put("dsf", dataScopeFilter(projectInfo.getCurrentUser(), "o", "u8"));
		Page<ProjectInfo> pages = super.findPage(page,projectInfo);
		if(!Collections3.isEmpty(pages.getList())){
			for(ProjectInfo p:pages.getList()){
				p.setUsers(projectUserDao.getProjectUsersByProjectId(projectInfo.getId(),null));
			}
		}
		return pages;
	}
	public Page<ProjectInfo> findAll(Page<ProjectInfo> page, ProjectInfo projectInfo) {
		projectInfo.setPage(page);
		page.setList(dao.findAllList(projectInfo));
		return page;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ProjectInfo projectInfo) {
		//保存项目信息
		super.save(projectInfo);
		//保存项目-成员信息
		//1先删除项目成员信息
		projectUserService.deleteByProjectInfo(projectInfo.getId());
		//2保存新的成员信息
		//核心成员
		saveProjectUsers(projectInfo.getId(),projectInfo.getMainUserIds(),ProjectUser.MAINUSER);
		//普通成员
		saveProjectUsers(projectInfo.getId(),projectInfo.getNormalUserIds(),ProjectUser.NORMALUSER);
	}

	/**
	 * 保存项目成员
	 * @param projectId 项目id
	 * @param userIds 成员ids
	 * @param flag 是否核心成员
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveProjectUsers(String projectId,String userIds,Integer flag){
		if(StringUtils.isNotBlank(userIds)){
			if(userIds.indexOf(",")>-1){
				String[] idArr = userIds.split(",");
				for(String id:idArr){
					projectUserService.save(new ProjectUser(null,projectId,id,flag));
				}
			}else{
				projectUserService.save(new ProjectUser(null,projectId,userIds,flag));
			}
		}
	}
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ProjectInfo projectInfo) {
		super.delete(projectInfo);
		//删除项目成员关系
		List<ProjectUser> projectUsers = projectInfo.getUsers();
		if(!Collections3.isEmpty(projectUsers)){
			for(ProjectUser projectUser:projectUsers){
				projectUserService.delete(projectUser);
			}
		}
	}


	public Boolean checkCodeUnique(String id,String code){
		ProjectInfo projectInfo = this.dao.getProjectByIdCode(id,code);
		if(projectInfo==null || StringUtils.isBlank(projectInfo.getId())){
			return true;
		}else{
			return false;
		}
	}
	
}