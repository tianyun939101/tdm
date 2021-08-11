package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Tongjibt;

/**
 * 统计标题DAO接口
 * @author 张仁
 * @version 2017-08-07
 */
@MyBatisDao
public interface TongjibtDao extends CrudDao<Tongjibt> {
	public Object getMaxShunxu(String tongjiid);
}