package com.demxs.tdm.dao.resource.changshangygys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;


/**
 * 厂商、供应商信息DAO接口
 * @author zhangdengcai
 * @version 2017-06-10
 */
@MyBatisDao
public interface ChangshanggysxxDao extends CrudDao<Changshanggysxx> {

	/**
	 * 支持批量删除
	 * @param changshanggysxx
	 */
	public void deleteMore(Changshanggysxx changshanggysxx);
}