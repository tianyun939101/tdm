package com.demxs.tdm.dao.lab;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabUser;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/25 10:08
 * @Description:
 */
@MyBatisDao
public interface LabUserDao extends CrudDao<LabUser> {

    /**
    * @author Jason
    * @date 2020/8/25 10:11
    * @params []
    * 根据用户id查询
    * @return java.util.List<com.demxs.tdm.domain.lab.LabUser>
    */
    List<LabUser> findByUserId(LabUser labUser);

    /**
    * @author Jason
    * @date 2020/8/25 10:12
    * @params []
    * 根据试验室id查询
    * @return java.util.List<com.demxs.tdm.domain.lab.LabUser>
    */
    List<LabUser> findByLabId(LabUser labUser);

    /**
    * @author Jason
    * @date 2020/9/9 10:29
    * @params [labUser]
    * 根据用户id删除绑定的试验室信息
    * @return int
    */
    int deleteByUserId(LabUser labUser);

    /**
     *
     */
    void changeBelone(LabUser labUser);
}
