package com.demxs.tdm.dao.lab;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryLab;
import com.demxs.tdm.domain.lab.LabInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试验室信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface LabInfoDao extends CrudDao<LabInfo> {

	LabInfo getBaseInfo(@Param("id") String id);

	List<LabInfo> getByName(String name);

	/**
	* @author Jason
	* @date 2020/8/10 17:10
	* @params [lab]
	* 查询名称，能力图谱模块
	* @return java.util.List<com.demxs.tdm.domain.lab.LabInfo>
	*/
	List<LabInfo> findName(TestCategoryLab lab);

	List<LabInfo> findAllListByCondition(@Param("where") String where);

	LabInfo getLabInfoByUserId(@Param("userId") String userId);

	/**
	 * @Describe:查询所有正式试验室
	 * @Author:WuHui
	 * @Date:15:02 2020/11/11
	 * @param centerId
	 * @return:java.util.List<java.lang.String>
	*/
    List<String> findAllEnalbeLab(@Param("centerId") String centerId);
	List<LabInfo> findAllEnalbeLabAll();

	List<String> getLabIdByCenter(@Param("centerId") String centerId);

	List<String> findCenterByCenterName(@Param("centerName") String name);

	List<String> test(@Param("newLab") String newLab);

    List<Map<String, Object>> getNew(LabInfo labInfo);
	List<Map<String, Object>> getOld(LabInfo labInfo);
}