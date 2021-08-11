package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiByjh;

import java.util.List;

/**
 * 设备保养计划DAO接口
 * @author zhangdengcai
 * @version 2017-07-15
 */
@MyBatisDao
public interface ShebeiByjhDao extends CrudDao<ShebeiByjh> {

    /**
     * 批量删除
     * @param shebeiByjh
     */
    public void deleteMore(ShebeiByjh shebeiByjh);

    /**
     * 查询全部设备的保养计划
     * @param shebeiByjh
     * @return
     */
    @Override
    public List<ShebeiByjh> findAllList(ShebeiByjh shebeiByjh);
}