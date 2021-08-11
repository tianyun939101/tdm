package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Mailmaster;

/**
 * 邮件配置DAO接口
 * @author 张仁
 * @version 2017-08-09
 */
@MyBatisDao
public interface MailmasterDao extends CrudDao<Mailmaster> {
	public void tingyongqt(String id);
}