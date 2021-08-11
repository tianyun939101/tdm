package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestItemUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验项目试验项DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestItemUnitDao extends CrudDao<TestItemUnit> {
	/**
	 * 查找试验项目的关联项
	 * @param itemId
	 * @return
	 */
	List<TestItemUnit> findByItem(@Param("itemId") String itemId);

	/**
	 * 删除试验项目所有关联项
	 * @param itemId
	 */
	void deleteByItem(@Param("itemId") String itemId);
}