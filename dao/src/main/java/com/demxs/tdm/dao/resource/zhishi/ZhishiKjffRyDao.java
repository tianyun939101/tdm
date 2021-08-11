package com.demxs.tdm.dao.resource.zhishi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.zhishi.ZhishiKjffRy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识可见范围-人员DAO接口
 * @author 詹小梅
 * @version 2017-07-12
 */
@MyBatisDao
public interface ZhishiKjffRyDao extends CrudDao<ZhishiKjffRy> {

    /**
     * 根据知识id，删除可见人员
     * @param zhishiKjffRy
     */
    public void deleteZhishiKjffRy(ZhishiKjffRy zhishiKjffRy);


    /**
     * 获取此人有权限的设备id
     * @param userId
     * @return
     */
    List<String> getShebeiidsByUser(@Param("userId")String userId);
}