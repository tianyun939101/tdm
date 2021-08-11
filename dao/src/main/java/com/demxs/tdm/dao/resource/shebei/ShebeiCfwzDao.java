package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiCfwz;

import java.util.List;

/**
 * 设备存放位置DAO接口
 * @author zhangdengcai
 * @version 2017-06-14
 */
@MyBatisDao
public interface ShebeiCfwzDao extends TreeDao<ShebeiCfwz> {

	/**
	 * 批量删除
	 * @param shebeiCfwz
	 */
	public void deleteMore(ShebeiCfwz shebeiCfwz);

	/**
	 * 获取子位置列表
	 * @param shebeiCfwz
	 * @return
	 */
	public List<ShebeiCfwz> getChildren(ShebeiCfwz shebeiCfwz);

	List<ShebeiCfwz> getByName(String name);
}