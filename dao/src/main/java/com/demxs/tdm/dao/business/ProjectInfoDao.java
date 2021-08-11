package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ProjectInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 试验项目DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface ProjectInfoDao extends CrudDao<ProjectInfo> {

    ProjectInfo getProjectByIdCode(@Param("id")String id, @Param("code")String code);
	
}