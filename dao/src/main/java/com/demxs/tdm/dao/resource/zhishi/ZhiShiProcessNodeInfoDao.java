package com.demxs.tdm.dao.resource.zhishi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.zhishi.ZhiShiProcessNodeInfo;

/**
 * @author: Jason
 * @Date: 2020/9/4 10:15
 * @Description:
 */
@MyBatisDao
public interface ZhiShiProcessNodeInfoDao extends CrudDao<ZhiShiProcessNodeInfo> {

    /**
    * @author Jason
    * @date 2020/9/4 10:16
    * @params [nodeInfo]
    * 根据知识信息id和节点id查询
    * @return com.demxs.tdm.domain.resource.zhishi.ZhiShiProcessNodeInfo
    */
    ZhiShiProcessNodeInfo getByDefIdAndNodeId(ZhiShiProcessNodeInfo nodeInfo);

    /**
    * @author Jason
    * @date 2020/9/4 10:28
    * @params [nodeInfo]
    * 根据知识信息id和节点id修改
    * @return int
    */
    int updateByDefIdAndNodeId(ZhiShiProcessNodeInfo nodeInfo);
}
