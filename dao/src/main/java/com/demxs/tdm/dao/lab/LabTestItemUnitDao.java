package com.demxs.tdm.dao.lab;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabTestItemUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验项目试验项DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface LabTestItemUnitDao extends CrudDao<LabTestItemUnit> {
	/**
	 * 查找试验项目的关联项
	 * @param labItemId
	 * @return
	 */
	List<LabTestItemUnit> findByItem(@Param("labItemId") String labItemId);

	/**
	 * 删除试验项目所有关联项
	 * @param labItemId
	 */
	void deleteByItem(@Param("labItemId") String labItemId);
}