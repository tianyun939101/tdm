package com.demxs.tdm.dao.resource.consumeables;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.consumeables.Haocaiku;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 耗材库DAO接口
 * @author sunjunhui
 * @version 2017-11-30
 */
@MyBatisDao
public interface HaocaiInfoDao extends CrudDao<Haocaiku> {

    /**
     * 修改库存数量
     * @param id
     * @param kucun
     */
    void updateKucun(@Param("id")String id, @Param("kucun")Integer kucun,@Param("update")Date update);


    /**
     * 获取最大耗材编号
     */
    public String getMaxHaocaibh(@Param("haocaibh")String haocaibh);


    /**
     * 根据编码获取
     * @param code
     * @return
     */
    Haocaiku getByCode(@Param("code")String code);
	
}