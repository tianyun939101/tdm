package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCkfkd;

/**
 * 标准物质出库、返库DAO接口
 * @author zhangdengcai
 * @version 2017-06-17
 */
@MyBatisDao
public interface BiaozhunwzCkfkdDao extends CrudDao<BiaozhunwzCkfkd> {
	/**
	 * 批量删除
	 * @param biaozhunwzCkfkd
	 */
	public void deleteMore(BiaozhunwzCkfkd biaozhunwzCkfkd);

}