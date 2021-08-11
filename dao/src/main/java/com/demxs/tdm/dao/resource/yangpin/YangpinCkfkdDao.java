package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yangpin.YangpinCkfkd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 样品入库/出库/返库操作DAO接口
 * @author 詹小梅
 * @version 2017-06-15
 */
@MyBatisDao
public interface YangpinCkfkdDao extends CrudDao<YangpinCkfkd> {
    /**
     * 批量保存样品出库/返库记录
     * @param yangpinlzs
     */
    public void batchInsert(@Param(value = "yangpinlzs") List<YangpinCkfkd> yangpinlzs);

    /**
     * 删除样品出库记录
     * @param yangpinzid 样品组id
     */
    public void deleteYangpinrkjl(@Param("yangpinzid") String yangpinzid);

    public List<YangpinCkfkd> yangpinck(YangpinCkfkd entity);

    /**
     * 根据任务id获取样品出库记录
     * @param entity
     * @return
     */
    public List<YangpinCkfkd> findckd(YangpinCkfkd entity);

    /**
     * 获取出库单中属性字段后缀最大值
     * @param filed 属性
     * @return
     */
    public String getMaxshuxing(@Param("filed") String filed);

    /**
     * 出库单中加一列
     * @param newColumn
     */
    public void addColumn(@Param("newColumn") String newColumn, @Param("strLength") String strLength);
    /**
     * 出库单中加的一列注释
     * @param newColumn
     */
    public void addComment(@Param("newColumn") String newColumn);

    /**
     * 获取属性对应value
     * @param filedNames 属性字段名称
     * @param yangpinzj  样品id
     * @param reneuzj    任务id
     * @return
     */
    public String getFiledValue(@Param("filedNames") String filedNames, @Param("yangpinzj") String yangpinzj, @Param("reneuzj") String reneuzj);

    /**
     * 保存样品属性（试验任务保存）
     * @param fileds
     * @param values
     * @param chukdid
     */
    public void saveFiledValue(@Param("filed") String fileds, @Param("value") String values, String chukdid);

    /**
     * 更新样品属性值（任务样品出库时 更新）
     * @param filedName  属性
     * @param filedValue 属性值
     * @param id         出库单id
     */
    public void updateFiledValues(@Param("filedName") String filedName, @Param("filedValue") String filedValue, @Param("id") String id);

    /**
     * 根据任务id获取出库任务样品数量
     * @param renwuzj 任务主键
     * @return
     */
    public String getChukuYpslByrwid(@Param("renwuzj") String renwuzj);

    /**
     * 更新出库任务数据标签
     * @param id 出库单id
     */
    public void updateCkShujubq(@Param("id") String id);

}