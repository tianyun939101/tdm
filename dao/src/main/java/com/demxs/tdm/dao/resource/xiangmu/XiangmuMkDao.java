package com.demxs.tdm.dao.resource.xiangmu;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.xiangmu.XiangmuMk;

/**
 * 项目模块信息维护DAO接口
 * @author 詹小梅
 * @version 2017-06-14
 */
@MyBatisDao
public interface XiangmuMkDao extends CrudDao<XiangmuMk> {
    /**
     * 更新有效性
     * @param entity
     */
    public void modifyYxxByfzj(XiangmuMk entity);
	
}