package com.demxs.tdm.common.sys.dao;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);

	Date getLastUpdateTime();
	String findSortByType(@Param("type") String type);
}
