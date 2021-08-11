package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Baobiao;

/**
 * 报表DAO接口
 * @author 张仁
 * @version 2017-08-04
 */
@MyBatisDao
public interface BaobiaoDao extends CrudDao<Baobiao> {
	
}