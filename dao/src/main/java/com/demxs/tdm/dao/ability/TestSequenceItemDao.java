package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestSequenceItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验序列详情DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestSequenceItemDao extends CrudDao<TestSequenceItem> {

	/**
	 * 根据nodeId 查询对应记录
	 * @param nodeId
	 * @return
	 */
	TestSequenceItem getByNodeId(@Param("nodeId") String nodeId);
	/**
	 * 根据序列ID 获取序列项内容
	 * @param seqId
	 * @return
	 */
	List<TestSequenceItem> findBySeqId(@Param("seqId") String seqId);

	/**
	 * 根据序列ID 获取序列项详细内容，试验项目包含试验项详情等内容
	 * @param seqId
	 * @return
	 */
	List<TestSequenceItem> detailBySeqId(@Param("seqId") String seqId);


	/**
	 * 删除试验项目所有关联项
	 * @param seqId
	 */
	void deleteBySeqId(@Param("seqId") String seqId);
}