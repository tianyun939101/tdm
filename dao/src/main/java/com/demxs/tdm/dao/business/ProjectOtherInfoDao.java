package com.demxs.tdm.dao.business;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ProjectOtherInfo;

/**
 * 试验项目其他信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface ProjectOtherInfoDao extends CrudDao<ProjectOtherInfo> {
	
}