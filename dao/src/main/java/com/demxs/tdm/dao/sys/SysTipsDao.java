package com.demxs.tdm.dao.sys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.sys.SysTips;

import java.util.List;
import java.util.Map;

/**
 * 系统提示信息配置DAO接口
 * @author 谭冬梅
 * @version 2017-09-06
 */
@MyBatisDao
public interface SysTipsDao extends CrudDao<SysTips> {
    public List<Map> findListBySql(String sql);
	
}