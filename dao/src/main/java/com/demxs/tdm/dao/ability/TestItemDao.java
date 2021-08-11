package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestItem;

import java.util.List;

/**
 * 试验项目DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestItemDao extends CrudDao<TestItem> {
	/**
	 * 继承 findlist ,返回结果包含试验条件信息
	 * @param testItem
	 * @return
	 */
	List<TestItem> listWithCondition(TestItem testItem);

    /**
     * 根据选择实验室，过滤试验项目
     * @param testItem
     * @return
     */
	List<TestItem> noStandardJoinFind(TestItem testItem);
}