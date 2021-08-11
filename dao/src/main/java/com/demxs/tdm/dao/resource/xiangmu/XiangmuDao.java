package com.demxs.tdm.dao.resource.xiangmu;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.xiangmu.Xiangmu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目信息，树维护DAO接口
 * @author 詹小梅
 * @version 2017-06-14
 */
@MyBatisDao
public interface XiangmuDao extends CrudDao<Xiangmu> {
    /**
     * @Author：詹小梅
     * @param： * @param null
     * @Description：
     * @Date：9:41 2017/6/21
     * @Return：
     * @Exception：
     */
    public List<Xiangmu> findNianfenList(Xiangmu entity);
    /**
     * @Author：詹小梅
     * @param： * @param null
     * @Description：
     * @Date：9:40 2017/6/21
     * @Return：
     * @Exception：
     */
    public List<Xiangmu> findXiangmuList(Xiangmu entity);

    /**
     * 获取项目编号当前值
     * @param entity
     * @return
     */
    public List<Xiangmu> findxlhList(Xiangmu entity);

    public String findxlhList1(@Param("date") String date);

    /**
     * 获取数据中心项目列表
     * @param page
     * @return
     */
    public List<Xiangmu> findXMList(@Param("page") Page page);


}