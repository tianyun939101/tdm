package com.demxs.tdm.dao.oa;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.oa.Message;

/**
 * 消息管理DAO接口
 * @author sunjunhui
 * @version 2017-12-04
 */
@MyBatisDao
public interface MessageDao extends CrudDao<Message> {
	
}