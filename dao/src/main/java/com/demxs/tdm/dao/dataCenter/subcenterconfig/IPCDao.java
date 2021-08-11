package com.demxs.tdm.dao.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.IPC;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:44
 * @Description:
 */
@MyBatisDao
public interface IPCDao extends CrudDao<IPC> {

    /**
    * @author Jason
    * @date 2020/6/17 16:47
    * @params [testTaskId]
    * 根据试验任务id查询工控机
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.subcenterconfig.IPC>
    */
    List<IPC> findByTestTaskId(String testTaskId);

    /**
    * @author Jason
    * @date 2020/8/28 15:16
    * @params [ipc]
    * 根据试验室id查询
    * @return com.demxs.tdm.domain.dataCenter.subcenterconfig.IPC
    */
    IPC getByLabId(IPC ipc);
}
