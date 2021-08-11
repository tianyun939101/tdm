package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestItemCodition;
import org.apache.ibatis.annotations.Param;

/**
 * 试验项目试验条件DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestItemCoditionDao extends CrudDao<TestItemCodition> {
	/**
	 * 删除试验项目所有关联项
	 * @param itemId
	 */
	void deleteByItem(@Param("itemId") String itemId);
}