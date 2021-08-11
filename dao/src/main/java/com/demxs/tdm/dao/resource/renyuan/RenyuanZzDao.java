package com.demxs.tdm.dao.resource.renyuan;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.renyuan.RenyuanZz;
import org.apache.ibatis.annotations.Param;

/**
 * 人员资质(方法,设备)DAO接口
 * @author 詹小梅
 * @version 2017-06-20
 */
@MyBatisDao
public interface RenyuanZzDao extends CrudDao<RenyuanZz> {
    /**
     * 删除人员资质信息
     * @param renyuanid
     */
    //public void deleteRyzz(List<String> list);
    public void deleteRyzz(@Param("renyuanid") String renyuanid);

    /**
     * 删除人员资质信息
     * @param zizhiid
     */
    public void deleteRyzzByZizhiid(@Param("zizhiid") String zizhiid);
	
}