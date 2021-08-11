package com.demxs.tdm.dao.oa;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.oa.Act;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	/**
	 * 设置业务表中的 流程processId
	 * @param act
	 * @return
	 */
	public int updateProcInsIdByBusinessId(Act act);
	
}
