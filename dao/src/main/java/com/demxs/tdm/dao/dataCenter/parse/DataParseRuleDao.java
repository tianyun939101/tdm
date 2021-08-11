package com.demxs.tdm.dao.dataCenter.parse;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;

/**
 * 版本号DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface DataParseRuleDao extends CrudDao<DataParseRule> {
	
}