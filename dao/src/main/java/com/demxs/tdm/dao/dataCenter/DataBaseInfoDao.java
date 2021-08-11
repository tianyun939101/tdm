package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBaseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据中心基础信息DAO
 */
@MyBatisDao
public interface DataBaseInfoDao extends CrudDao<DataBaseInfo> {

    List<DataBaseInfo> findProviderList(DataBaseInfo entity);

    List<DataBaseInfo> findBaseList(DataBaseInfo entity);

    List<DataBaseInfo>   findCheckList(DataBaseInfo entity);

    List<DataBaseInfo> findReportList(DataBaseInfo entity);

    List<DataBaseInfo> findBaseAllList(DataBaseInfo entity);

    int updateByProvider(DataBaseInfo dataBaseInfo);

    /**
    * @author Jason
    * @date 2020/7/27 17:44
    * @params [dataBaseInfo]
    * 根据试验室id查询该试验室的所有试验信息
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.DataBaseInfo>
    */
    List<DataBaseInfo> findByLabId(DataBaseInfo dataBaseInfo);

    /*
     * @Describe:实验室数据 树形结构
     * @Author:WuHui
     * @Date:16:39 2020/9/23
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String,Object>> findLabBaseInfoTree(@Param("info") DataBaseInfo dataBaseInfo);

    /**
     * @Describe:章节数据 树形结构
     * @Author:WuHui
     * @Date:10:26 2020/9/24
     * @param dataBaseInfo
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String,Object>> findATABaseInfoTree(@Param("info") DataBaseInfo dataBaseInfo);


    List<DataBaseInfo> findAllBaseList(DataBaseInfo entity);

    /**
     * @Describe:根据类型获取提报数据
     * @Author:WuHui
     * @Date:10:36 2020/11/4
     * @param company
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.DataBaseInfo>
    */
    List<DataBaseInfo> findAllByCompany(@Param("company") String company);

    List<DataBaseInfo>   findDataByInfo(DataBaseInfo dataBaseInfo);

    void changeStatus(@Param("id") String id,@Param("status") String status);

    void changeInsertUser(@Param("id") String id,@Param("insertUser") String insertUser);

    void changeAuditUser(DataBaseInfo dataBaseInfo);


    String  getDataList(@Param("flag") String flag,@Param("quarterDate") String quarterDate,@Param("center") String center);

    List<String> getQurater();

    String getTaskDataList(@Param("flag") String flag,@Param("quarterDate") String quarterDate,@Param("center") String center);

    String getCenterDataList(@Param("centerFlag") String centerFlag,@Param("dateInfo") String dateInfo);

    String getServerDataList(@Param("dateInfo") String dateInfo);


    List<Map<String,String>>  getCenterDataMess();

    List<Map<String,String>>  getServerDataMess();

    List<DataBaseInfo> findAll();

    List<DataBaseInfo> selectInfo(DataBaseInfo entity);

    int testCount(DataBaseInfo entity);
    int testFlyCount(DataBaseInfo entity);

    String getRoleByUser(String userId);
}