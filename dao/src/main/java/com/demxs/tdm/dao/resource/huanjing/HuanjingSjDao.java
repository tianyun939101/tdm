package com.demxs.tdm.dao.resource.huanjing;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.dao.resource.dto.HuanjingSjDto;
import com.demxs.tdm.domain.resource.huanjing.HuanjingSj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 环境数据DAO接口
 * @author zhangdengcai
 * @version 2017-07-19
 */
@MyBatisDao
public interface HuanjingSjDao extends CrudDao<HuanjingSj> {

    /**
     * 批量删除
     * @param huanjingSj
     */
    public void deleteMore(HuanjingSj huanjingSj);

    /**
     * 获取试验室环境数据（折线图数据.只展示温室度）
     * @return
     */
    public List<HuanjingSjDto> linedata(@Param("shujulx") String shujulx);

    /**
     * 批量保存环境数据
     * @param huanjingSjs
     */
    public void batchSave(List<HuanjingSj> huanjingSjs);
}