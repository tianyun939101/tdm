package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestSequence;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试验序列DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestSequenceDao extends CrudDao<TestSequence> {
	List<TestSequence> findList(@Param("entity") TestSequence entity, @Param("page")Page page, @Param("filter") Map<String,Object> filter);

	List<TestSequence> listWithCondition(@Param("entity") TestSequence entity, @Param("page")Page page, @Param("filter") Map<String,Object> filter);
}