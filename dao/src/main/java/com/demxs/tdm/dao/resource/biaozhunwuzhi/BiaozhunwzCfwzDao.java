package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCfwz;

import java.util.List;

/**
 * 标准物质存放位置DAO接口
 * @author zhangdengcai
 * @version 2017-06-17
 */
@MyBatisDao
public interface BiaozhunwzCfwzDao extends CrudDao<BiaozhunwzCfwz> {
	/**
	 * 批量删除
	 * @param biaozhunwzCfwz
	 */
	public void deleteMore(BiaozhunwzCfwz biaozhunwzCfwz);

	/**
	 * 获取子位置列表
	 * @param biaozhunwzCfwz
	 * @return
	 */
	public List<BiaozhunwzCfwz> getChildren(BiaozhunwzCfwz biaozhunwzCfwz);

}