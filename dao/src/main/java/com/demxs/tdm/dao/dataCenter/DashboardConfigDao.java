package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DashboardConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author: Jason
 * @Date: 2020/6/2 10:30
 * @Description:
 */
@MyBatisDao
public interface DashboardConfigDao extends CrudDao<DashboardConfig> {

    /**
    * @author Jason
    * @date 2020/6/2 10:32
    * @params [config]
    * 根据类型查找
    * @return com.demxs.tdm.domain.dataCenter.DashboardConfig
    */
    DashboardConfig getByType(DashboardConfig config);

    /**
    * @author Jason
    * @date 2020/6/2 11:28
    * @params [config]
    * 根据类型删除
    * @return int
    */
    int deleteByType(DashboardConfig config);


    void updateStatus(@Param("type") String type,@Param("status") String status);

    List<Map<String,String>> getLabDetailList(@Param("center") String center);
}
