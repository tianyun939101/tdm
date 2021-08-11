package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Joininfo;

/**
 * 关联表DAO接口
 * @author 张仁
 * @version 2017-08-05
 */
@MyBatisDao
public interface JoininfoDao extends CrudDao<Joininfo> {
	
}