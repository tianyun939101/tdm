package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yangpin.YangpinCfwz;

/**
 * 样品存放位置DAO接口
 * @author 詹小梅
 * @version 2017-06-15
 */
@MyBatisDao
public interface YangpinCfwzDao extends CrudDao<YangpinCfwz> {
    /**
     * 更新有效性
     * @param entity
     */
    public void modifyYxxByfzj(YangpinCfwz entity);
	
}