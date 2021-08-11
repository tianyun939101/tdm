package com.demxs.tdm.dao.lab;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabTestSequenceCondition;
import org.apache.ibatis.annotations.Param;


/**
 * 试验序列试验条件DAO接口
 */
@MyBatisDao
public interface LabTestSequenceConditionDao extends CrudDao<LabTestSequenceCondition> {
	/**
	 * 删除试验序列所有关联项
	 * @param labSeqId
	 */
	void deleteBySequence(@Param("labSeqId") String labSeqId);
}