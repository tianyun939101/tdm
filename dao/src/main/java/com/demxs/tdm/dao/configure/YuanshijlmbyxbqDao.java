package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Yuanshijlmbyxbq;
import org.apache.ibatis.annotations.Param;

/**
 * 原始记录已选标签DAO接口
 * @author 张仁
 * @version 2017-07-24
 */
@MyBatisDao
public interface YuanshijlmbyxbqDao extends CrudDao<Yuanshijlmbyxbq> {


    void deleteByRkId(@Param("mbid")String mbid,@Param("rkId")String rkId);
	
}