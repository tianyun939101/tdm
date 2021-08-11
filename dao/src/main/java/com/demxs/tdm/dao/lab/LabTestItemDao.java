package com.demxs.tdm.dao.lab;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabTestItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验室试验项目DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface LabTestItemDao extends CrudDao<LabTestItem> {
	/**
	 * 根据试验室ID，清除试验项目
	 * @param labId
	 */
	void deleteByLabId(@Param("labId") String labId) ;

	/**
	 * 查找试验室试验项目
	 * @param labId
	 * @return
	 */
	List<LabTestItem> findByLab(@Param("labId") String labId);

	/**
	 * 查找指定试验室试验项目
	 * @param labId
	 * @param itemId
	 * @return
	 */
	LabTestItem getLabItem(@Param("labId") String labId,@Param("itemId") String itemId);
}