package com.demxs.tdm.common.sys.dao;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.FtpConfig;

/**
 * @author: Jason
 * @Date: 2020/7/17 14:13
 * @Description:
 */
@MyBatisDao
public interface FtpConfigDao extends CrudDao<FtpConfig> {

    /**
    * @author Jason
    * @date 2020/7/17 14:14
    * @params [type]
    * 根据类型查询
    * @return com.demxs.tdm.domain.dataCenter.pr.FtpConfig
    */
    FtpConfig getByType(String type);

    /**
    * @author Jason
    * @date 2020/7/17 14:14
    * @params [type]
    * 根据类型删除
    * @return int
    */
    int deleteByType(String type);
}
