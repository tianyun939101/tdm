package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.FlightBindTestInfoData;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/5/15 11:28
 * @Description:
 */
@MyBatisDao
public interface FlightBindTestInfoDataDao extends CrudDao<FlightBindTestInfoData> {

    /**
    * @author Jason
    * @date 2020/5/15 11:28
    * @params []
    * 根据试验id获取试飞绑定信息
    * @return com.demxs.tdm.domain.dataCenter.FlightBindTestInfoData
    */
    //List<FlightBindTestInfoData> findByTestInfoId(FlightBindTestInfoData bindTestInfoData);

    /**
    * @author Jason
    * @date 2020/6/16 10:32
    * @params [bindTestInfoData]
    * 根据试验id查询其他ftp服务器中的绑定信息
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.FlightBindTestInfoData>
    */
    List<FlightBindTestInfoData> findFtpFileByTestInfoId(FlightBindTestInfoData bindTestInfoData);

    /**
    * @author Jason
    * @date 2020/5/15 13:03
    * @params [fileId]
    * 根据试飞文件id清除绑定信息
    * @return int
    */
    int deleteByTestInfoIdAndDirId(FlightBindTestInfoData bindTestInfoData);

    /**
    * @author Jason
    * @date 2020/5/15 15:10
    * @params [bindTestInfoData]
    * 根据试验信息和文件id查询绑定信息
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.FlightBindTestInfoData>
    */
    List<FlightBindTestInfoData> findByTestInfoIdAndDirId(FlightBindTestInfoData bindTestInfoData);
}
