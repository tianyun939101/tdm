package com.demxs.tdm.dao.lab;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.SubCenter;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/18 16:38
 * @Description:
 */
@MyBatisDao
public interface SubCenterDao extends CrudDao<SubCenter> {

    /**
    * @author Jason
    * @date 2020/8/18 16:39
    * @params []
    * 根据名称获取
    * @return com.demxs.tdm.domain.lab.SubCenter
    */
    SubCenter getByName(SubCenter subCenter);

    /**
     * @Describe:获取中心名称
     * @Author:WuHui
     * @Date:17:39 2020/11/11
     * @param
     * @return:java.util.List<java.lang.String>
    */
    List<String> findAllSubCenterName();
}
