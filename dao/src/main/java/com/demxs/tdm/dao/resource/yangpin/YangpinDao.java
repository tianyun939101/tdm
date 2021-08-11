package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.dao.resource.dto.YangPinDto;
import com.demxs.tdm.domain.resource.yangpin.Yangpin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 样品信息操作DAO接口
 * @author 詹小梅
 * @version 2017-06-15
 */
@MyBatisDao
public interface YangpinDao extends CrudDao<Yangpin> {

    public List<YangPinDto> findYangPinDtoList(Yangpin entity);

    /**
     * 根据样品编号获取样品列表
     * @param entity
     * @return
     */
    public Yangpin getYangpinxxByYpbh(Yangpin entity);
    /**
     * 批量保存样品组
     * @param yangpins
     */
    public void batchInsert(@Param(value = "yangpins") Yangpin[] yangpins);
    /**
     * @Author：詹小梅
     * @param： * @param yangpinzids
     * @Description：出库批量更新样品状态/【在检】
     * @Date：21:01 2017/6/19
     * @Return：
     * @Exception：
     */
    public void batchUpdate(@Param("yangpinids") List yangpinids,
                            @Param("yangpinzt") String yangpinzt);
    /**
     * @Author：詹小梅
     * @param： * @param yangpinzids
     * @Description：返库批量更新样品状态/在库  和 存放位置
     * @Date：21:01 2017/6/19
     * @Return：
     * @Exception：
     */
    public void batchUpdateForFK(@Param("yangpinids") List yangpinids,
                                 @Param("yangpinzt") String yangpinzt, @Param("cunfangwz") String cunfangwz, @Param("cunfangwzid") String cunfangwzid);
    /**
     * @Author：詹小梅
     * @param： * @param yangpinzids
     * @Description：出库批量更新是否留样
     * @Date：21:01 2017/6/19
     * @Return：
     * @Exception：
     */
    public void batchUpdateLY(@Param("yangpinids") List yangpinids,
                              @Param("shifouly") String shifouly);
    /**
     * 删除申请单关联样品组
     * @param weituodzj
     */
    public void deleteYangpinzu(@Param("weituodzj") String weituodzj);

    public List<Yangpin> findList(@Param("fangfazjl") String fangfazjl,
                                  @Param("weituodzj") String weituodzj,
                                  @Param("shifouwtdypz") String shifouwtdypz);

    /**
     * 临时样品库中的样品返库 更新样品状态[待检],存放位置
     * @param id
     */
    public void updateYpzt(@Param("id") String id,
                           @Param("yangpinzt") String yangpinzt,
                           @Param("cunfangwzid") String cunfangwzid);

    /**
     * 获取样品的拆样最大编号
     * @return
     */
    public int getChaiyangmaxno(String yangpinbh);

    /**
     * 批量保存样品信息
     * @param list
     */
    public void batchInsertYangpin(@Param(value = "list") List<Yangpin> list);

    /**
     * 获取样品信息（单条）
     * @param id
     * @return
     */
    public Map<String,String> getMapData(@Param("id") String id);



}