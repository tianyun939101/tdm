package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiByjl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备保养记录DAO接口
 * @author zhangdengcai
 * @version 2017-07-15
 */
@MyBatisDao
public interface ShebeiByjlDao extends CrudDao<ShebeiByjl> {

    /**
     * 根据保养计划id获取保养记录
     * @param shebeiByjl
     * @return
     */
    public List<ShebeiByjl> listByByjhid(ShebeiByjl shebeiByjl);

    /**
     * 批量插入
     * @param shebeiByjls
     */
    public void batchInsert(List<ShebeiByjl> shebeiByjls);

    /**
     * 单个设备的保养记录
     * @param shebeiByjl
     * @return
     */
    public List<ShebeiByjl> dangesbsyjl(ShebeiByjl shebeiByjl);

    /**
     * 全部设备的保养记录
     * @param shebeiByjl
     * @return
     */
    @Override
    public List<ShebeiByjl> findAllList(ShebeiByjl shebeiByjl);

    /**
     * 获取所有设备保养任务
     * @param shebeiByjl
     * @return
     */
    public List<Map<String, Object>> getAllbyrw(ShebeiByjl shebeiByjl);


    /**
     * 根据保养计划id删除所有记录
     * @param byjhid 保养计划id
     */
    void deleteByByjhid(@Param("byjhid")String byjhid);
}