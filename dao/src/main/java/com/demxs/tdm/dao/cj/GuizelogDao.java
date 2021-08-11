package com.demxs.tdm.dao.cj;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.cj.Guizelog;

/**
 * 采集规则日志DAO接口
 * @author 张仁
 * @version 2017-08-11
 */
@MyBatisDao
public interface GuizelogDao extends CrudDao<Guizelog> {
	
}