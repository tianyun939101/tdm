package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ProjectUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验项目用户DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface ProjectUserDao extends CrudDao<ProjectUser> {

    /**
     * 根据项目id获取项目成员
     * @param projectId
     * @return
     */
    List<ProjectUser> getProjectUsersByProjectId(@Param("projectId") String projectId,@Param("codeFlag") Integer coreFlag);

    /**
     * 根据项目id删除项目成员
     * @param projectId
     */
    void deleteByProjectId(@Param("projectId")String projectId);
	
}