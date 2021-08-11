package com.demxs.tdm.dao.sys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.sys.SysMessage;

/**
 * 系统消息DAO接口
 * @author 谭冬梅
 * @version 2017-09-06
 */
@MyBatisDao
public interface SysMessageDao extends CrudDao<SysMessage> {
	
}