package com.demxs.tdm.dao.sys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.sys.RoleOffice;

/**
 * 角色组织关系DAO接口
 * @author 詹小梅
 * @version 2017-06-26
 */
@MyBatisDao
public interface RoleOfficeDao extends CrudDao<RoleOffice> {
	
}