package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 设备信息DAO接口
 * @author zhangdengcai
 * @version 2017-06-14
 */
@MyBatisDao
public interface ShebeiDao extends CrudDao<Shebei> {

	/**
	 * 修改设备状态
	 * @param shebeiid
	 * @param status
	 */
	void updateShebeiStatus(@Param("shebeiid")String shebeiid,@Param("status")String status);

	/**
	 * 修改设备启停状态
	 * @param shebeiid
	 * @param qitingStatus
	 */
	void updateShebeiQTStatus(@Param("shebeiid")String shebeiid,@Param("qitingStatus")String qitingStatus);

	/**
	 * 根据设备资质查找设备
	 * @param zizhiId
	 * @param labId
	 * @return
	 */
	List<Shebei> findByZizhi(@Param("zizhiId") String zizhiId, @Param("labId") String labId);


	/**
	 * 根据ids 逗号分隔的获取设备信息
	 * @param ids
	 * @return
	 */
	List<Shebei> findByIds(@Param("ids")String ids);

	Shebei getShebeiByCodeId(@Param("id")String id, @Param("code")String code);

	/**
	 * 获取有效设备
	 * @param shebei
	 * @return
	 */
	List<Shebei> findValidShebeis(Shebei shebei);

	/**
	 * @return 设备及设备当天任务数量
	 */
	List<Map> listWithTaskCount(@Param("userId")String userId);

	/**
	 * @param deviceId
	 * @return
	 */
	Integer testingCount(@Param("deviceId") String deviceId);

	Shebei getByCode(@Param("code")String code);

	Shebei getByNewCode(String newCode);

	List<Shebei> getKind(@Param("id") String id,@Param("tt") String tt);


	/**
	* @author Jason
	* @date 2020/6/5 12:36
	* @params [labIdSet]
	* 根据试验室id查询设备数量，大屏数据支持
	* @return void
	*/
    int findCountByLabId(List<String> labIdSet);


    /**
     * @Describe:根据分中心id查询设备数量
     * @Author:WuHui
     * @Date:9:21 2020/8/31
     * @param centerId
     * @return:java.lang.Integer
    */
	Integer countShebeiByCenter(@Param("centerId") String centerId);

	/**
	 * @Describe:根据分中心id查询设备数据
	 * @Author:WuHui
	 * @Date:9:37 2020/8/31
	 * @param page
	 * @param centerId
	 * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.resource.shebei.Shebei>
	*/
	List<Shebei> findShebeiByCenter(@Param("page") Page<Shebei> page,@Param("centerId") String centerId, @Param("labinfoId") String labinfoId);

	Integer removeShebeiParent(@Param("equipmentId") String equipmentId);

	Integer updateShebeiParent(@Param("equipmentId") String equipmentId
			,@Param("subList") List<Shebei> subList);

	String getEquipmentSum(@Param("status") String status,@Param("dataInfo") String dataInfo,@Param("center") String center);

	void updateStatus(@Param("status") String status,@Param("id") String id);


	String findContent(@Param("id") String id,@Param("time") String time);

	/**
	 * 批量修改
	 * @param shebei
	 */
	void batchSave(Shebei shebei);

}