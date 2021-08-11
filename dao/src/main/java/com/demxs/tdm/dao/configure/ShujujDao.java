package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Shujuj;

import java.util.List;
import java.util.Map;

/**
 * 数据集DAO接口
 * @author 张仁
 * @version 2017-08-03
 */
@MyBatisDao
public interface ShujujDao extends CrudDao<Shujuj> {
	public List<String> queryTables();

	public List<Map> queryMeta(String tname);

	public List<Map> queryDataSetMeta(String datasetid);
}