package com.demxs.tdm.dao.ability;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestSequenceCondition;
import org.apache.ibatis.annotations.Param;


/**
 * 试验序列试验条件DAO接口
 */
@MyBatisDao
public interface TestSequenceConditionDao extends CrudDao<TestSequenceCondition> {
	/**
	 * 删除试验序列所有关联项
	 * @param seqId
	 */
	void deleteBySequence(@Param("seqId") String seqId);
}