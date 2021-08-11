package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiQitingjl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备启停记录DAO接口
 * @author sunjunhui
 * @version 2017-11-03
 */
@MyBatisDao
public interface ShebeiQitingjlDao extends CrudDao<ShebeiQitingjl> {


    public List<ShebeiQitingjl> findListByType(@Param("shebeiid")String shebeiid, @Param("type")String type);


    void updateValidById(@Param("id")String id);


    List<ShebeiQitingjl> findListByTypeValid(@Param("shebeiid")String shebeiid, @Param("type")String type);


	
}