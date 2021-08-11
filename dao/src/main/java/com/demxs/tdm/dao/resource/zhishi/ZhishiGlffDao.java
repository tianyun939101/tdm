package com.demxs.tdm.dao.resource.zhishi;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.zhishi.ZhishiGlff;

/**
 * 知识关联方法DAO接口
 * @author 詹小梅
 * @version 2017-06-17
 */
@MyBatisDao
public interface ZhishiGlffDao extends CrudDao<ZhishiGlff> {
    /**
     * @Author：詹小梅
     * @param： * @param null
     * @Description：删除知识关联方法
     * @Date：18:08 2017/6/24
     * @Return：
     * @Exception：
     */
    public void deleteZhishiGlff(String zhishiid);

	
}