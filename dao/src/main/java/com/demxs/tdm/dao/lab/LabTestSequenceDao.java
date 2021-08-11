package com.demxs.tdm.dao.lab;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabTestSequence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验室试验序列DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface LabTestSequenceDao extends CrudDao<LabTestSequence> {
	/**
	 * 根据试验室ID，清除试验序列
	 * @param labId
	 */
	void deleteByLabId(@Param("labId") String labId) ;
	/**
	 * 查找试验室试验序列
	 * @param labId
	 * @return
	 */
	List<LabTestSequence> findByLab(@Param("labId") String labId);
}