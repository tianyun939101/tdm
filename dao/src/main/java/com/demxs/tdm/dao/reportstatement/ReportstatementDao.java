package com.demxs.tdm.dao.reportstatement;

import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.reportstatement.CheckUserTimeRate;
import com.demxs.tdm.domain.reportstatement.ShebeiGzReport;
import com.demxs.tdm.domain.reportstatement.TestEngineer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjinfan on 2018/2/26.
 */
@MyBatisDao
public interface ReportstatementDao {
	/**
	 * 获取时间段有工作的人
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String, Object>> getHasWorkUser(@Param("start") Date start, @Param("end") Date end);

	/**
	 * 试验费用统计（按部门或项目）
	 * @param type  1：部门；   2： 项目；
	 * @param labIdgetUserWork
	 * @param userIds  用户ID逗号拼接字符串，过滤申请人
	 * @param start
	 * @param end
	 * @return
	 */
	List<Map<String, Object>> testMoneyStatistic(@Param("type") int type, @Param("labId") String labId, @Param("userIds") String userIds, @Param("start") Date start, @Param("end") Date end,@Param("user")String user);

	List<Map<String, Object>> getUserWork(@Param("labId")String labId, @Param("owner") String onwer,@Param("start") Date start, @Param("end") Date end,@Param("userId") String
			userId);

	List<CheckUserTimeRate> getCheckUserTimeRate(@Param("checkUsers")String checkUsers,@Param("start")Date start,@Param("end")Date end);

	List<CheckUserTimeRate> getLabTimeRate(@Param("labIds")String labIds,@Param("start")Date start,@Param("end")Date end,@Param("user")String user);

	List<Map<String,Object>> consumablesReport(@Param("haocaiLxId")String haocaiLxId,@Param("labId")String labId,@Param("start")Date start,@Param("end")Date end,@Param("user")String user);

	List<Map<String,Object>> shebeiDJ(@Param("shebeiid")String shebeiid,@Param("start")Date start,@Param("end")Date end);

	List<ShebeiGzReport> shebeiGuzhang(@Param("labId")String labId, @Param("shebeiids")String shebeiids, @Param("start")Date start, @Param("end")Date end,@Param("user")String user);


	List<Map<String,Object>> shebeiBaoyang(@Param("shebeiId")String shebeiId,@Param("start")Date start,@Param("end")Date end);


	List<TestEngineer> getTestEngineers(@Param("user")String user,@Param("start")Date start,@Param("end")Date end);

	List<Map<String,Object>> getItemOwners(@Param("entrustCode")String entrustCode);

	List<Map<String,Object>> emergentEntrustStatistic(@Param("labId")String labId,@Param("start") Date start, @Param("end") Date end,@Param("user")String user);

	List<Map<String, Object>> reliabilityTest(@Param("labId")String labId,@Param("entrustCode") String entrustCode, @Param("testType") String testType,
	                                          @Param("sampleType") String sampleType, @Param("projectCode") String projectCode,
	                                          @Param("sampleCode") String sampleCode, @Param("testItems") String testItems,
	                                          @Param("start") Date start, @Param("end") Date end,@Param("user")String user);
}
