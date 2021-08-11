package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.constant.NoStandardEntrustInfoEnum;
import com.demxs.tdm.domain.business.nostandard.NoStandardEntrustInfo;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface NoStandardEntrustInfoDao extends CrudDao<NoStandardEntrustInfo> {

    int changeStatus(NoStandardEntrustInfo noStandardEntrustInfo);

    NoStandardEntrustInfo getByCode(String code);

    int updateActive(NoStandardEntrustInfo entrustInfo);

    NoStandardEntrustInfo getBase(String id);

    NoStandardEntrustInfo getById(String id);

    Integer selectStatusCount(NoStandardEntrustInfo entrustInfo);

    Integer selectIncompleteCount(NoStandardEntrustInfo entrustInfo);

    /**
    * @author Jason
    * @date 2020/6/8 10:44
    * @params []
    * 获取任务数，阻燃大屏数据支持
    * @return java.lang.Integer
    */
    Integer selectTotalCount(@Param("status") String status);

    /**
    * @author Jason
    * @date 2020/6/8 11:21
    * @params [code]
    * 查询正在进行中的任务，阻燃大屏数据支持
    * @return java.lang.Integer
    */
    Integer selectHandingCount(String status);
}
