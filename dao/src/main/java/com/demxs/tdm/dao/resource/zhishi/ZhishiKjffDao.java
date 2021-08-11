package com.demxs.tdm.dao.resource.zhishi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.zhishi.ZhishiKjff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识可见范围DAO接口
 * @author 詹小梅
 * @version 2017-06-17
 */
@MyBatisDao
public interface ZhishiKjffDao extends CrudDao<ZhishiKjff> {

    /**
     * 根据知识id，删除可见范围
     * @param zhishiKjff
     */
    public void deleteZhishiKjff(ZhishiKjff zhishiKjff);

    List<String> getShebeiidsByOffice(@Param("officeId")String officeId,@Param("userId")String userId);
}