package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.business.vo.LineUpVO;
import com.demxs.tdm.domain.business.vo.MainTotalVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustInfoDao extends CrudDao<EntrustInfo> {
    EntrustInfo getByCode(@Param("code") String code);

    /**
     * 首页统计项
     * @return
     */
    MainTotalVO getByMainTotal();

    List<EntrustInfo> findByReport(EntrustInfo entrustInfo);

    /**
     * 申请单入库状态更改
     * @param entrustId
     * @param status
     */
     void storageEntrust(@Param("entrustId") String entrustId,
                         @Param("status")Integer status);

    /**
     * 查询可以入库的申请单  申请状态>=20300  <40100
     * @param entrustInfo
     * @return
     */
     List<EntrustInfo> findPutList(EntrustInfo entrustInfo);


    /**
     * 查询可以入库的申请单  申请状态>=20300  <40100
     * @param entrustInfo
     * @return
     */
    List<EntrustInfo> findPlan(EntrustInfo entrustInfo);

    /**
     * 获取试验室排队中的申请单
     * @return
     */
    List<LineUpVO> getLineUp();

    /**
    * @author Jason
    * @date 2020/6/8 10:45
    * @params []
    * 获取任务数，阻燃大屏数据支持
    * @return java.lang.Integer
    */
    Integer selectTotalCount(@Param("status") String status);

    /**
    * @author Jason
    * @date 2020/6/8 13:24
    * @params [toString, toString1, toString2]
    * 获取正在执行的任务数，阻燃大屏数据支持
    * @return java.lang.Integer
    */
    Integer selectHandingCount(String ...status);

    /**
    * @author Jason
    * @date 2020/6/9 15:16
    * @params [labId]
    * 根据试验室id获取该试验室的正在执行的全部任务，阻燃大屏试验任务列表面板数据支持
    * @return java.util.List<com.demxs.tdm.domain.business.TestTask>
    */
    List<TestTask> findTestTaskByLabId(String labId);

    /**
    * @author Jason
    * @date 2020/6/30 15:30
    * @params [entrustId]
    * 根据申请单id查询该申请单所有任务
    * @return java.util.List<com.demxs.tdm.domain.business.TestTask>
    */
    List<TestTask> findTestTaskByEntrustId(String entrustId);
}