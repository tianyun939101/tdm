package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestParameter;

/**
 * 试验参数DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface TestParameterDao extends CrudDao<TestParameter> {
	
}