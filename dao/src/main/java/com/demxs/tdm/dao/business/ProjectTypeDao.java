package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ProjectType;

/**
 * 试验项目类型DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface ProjectTypeDao extends TreeDao<ProjectType> {
	
}