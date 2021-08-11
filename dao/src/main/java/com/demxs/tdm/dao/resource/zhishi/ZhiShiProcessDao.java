package com.demxs.tdm.dao.resource.zhishi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.zhishi.ZhiShiProcess;

/**
 * @author: Jason
 * @Date: 2020/9/3 13:36
 * @Description: 知识流程dao
 */
@MyBatisDao
public interface ZhiShiProcessDao extends CrudDao<ZhiShiProcess> {

    /**
    * @author Jason
    * @date 2020/9/3 13:37
    * @params [defId]
    * 根据知识信息id查询流程
    * @return com.demxs.tdm.domain.resource.zhishi.ZhiShiProcess
    */
    ZhiShiProcess getByDefId(String defId);

    /**
    * @author Jason
    * @date 2020/9/3 13:53
    * @params [process]
    * 根据知识信息id修改流程
    * @return int
    */
    int updateByDefId(ZhiShiProcess process);
}
