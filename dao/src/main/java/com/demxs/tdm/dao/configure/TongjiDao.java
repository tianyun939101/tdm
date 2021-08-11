package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Tongji;

import java.util.List;
import java.util.Map;

/**
 * 统计配置DAO接口
 * @author 张仁
 * @version 2017-08-07
 */
@MyBatisDao
public interface TongjiDao extends CrudDao<Tongji> {
	public List<Map> findListBySql(String sql);
}