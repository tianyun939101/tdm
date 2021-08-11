package com.demxs.tdm.dao.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.Biaozhunwz;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzKc;

import java.util.List;

/**
 * 标准物质DAO接口
 * @author zhangdengcai
 * @version 2017-06-16
 */
@MyBatisDao
public interface BiaozhunwzDao extends CrudDao<Biaozhunwz> {

	/**
	 * 批量删除
	 * @param biaozhunwz
	 */
	public void deleteMore(Biaozhunwz biaozhunwz);

	/**
	 * 库存
	 * @param biaozhunwzKc
	 * @return
	 */
	public List<BiaozhunwzKc> kucun(BiaozhunwzKc biaozhunwzKc);

	/**
	 * 根据标准物质编号 获取标准物质
	 * @param biaozhunwz
	 * @return
	 */
	public List<Biaozhunwz> getByBianh(Biaozhunwz biaozhunwz);

	/**
	 * 批量修改标准物质状态
	 * @param biaozhunwzs
	 */
	public void piliangXgzt(List<Biaozhunwz> biaozhunwzs);

	/**
	 * 将过“有效期至”小于当当前日期，且状态不为“已过期”的改为"已过期"
	 * @param biaozhunwz
	 */
	public void changeStatusToGuoqi(Biaozhunwz biaozhunwz);

	/**
	 * 出库两天后，“是否消耗”为是、且状态仍为“已出库”的，状态改为“已消耗”
	 * @param biaozhunwz
	 */
	public void changeStatusToXiaohao(Biaozhunwz biaozhunwz);

	/**
	 * 出库超过两天、“是否消耗”为是、且状态仍为“使用中”的标准物质id字符串
	 * @param biaozhunwz
	 * @return
	 */
	public String changeToXhIds(Biaozhunwz biaozhunwz);
}