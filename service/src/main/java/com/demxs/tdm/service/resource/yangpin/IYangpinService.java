package com.demxs.tdm.service.resource.yangpin;

import java.util.List;
import java.util.Map;

/**
 * @Author： 詹小梅
 * @Description：
 * @Date：Create in 2017-07-19 15:48
 * @Modefied By：
 */
public interface IYangpinService {
    /**
     * 获取出库的样品信息 （样品编号、样品名称、样品ID、样品分类、附件列表（shujulist））
     * @param renwuzj
     * @return
     */
    public List<Map<String,Object>> getYangpinxx(String renwuzj);

    /**
     * 任务完成后更新样品状态 已检 或 已消耗
     * 更新依据：已消耗：当样品执行的任务使用的方法中，“是否消耗样品”字段为“是”时，任务完成后，样品状态置为“已消耗”。
     * @param renwuzj   任务主键
     * @param fangfazj  方法主键
     */
    public void updateYpzt(String renwuzj, String fangfazj);

    /**
     * 任务完成后更新样品状态 在检
     * @param renwuzj   任务主键
     * @param fangfazj  方法主键
     */
    public void updateYpztZJ(String renwuzj, String fangfazj);

    /**
     * 根据任务id获取出库任务样品数量
     * @param renwuzj
     * @return
     */
    public int getChukuYpslByrwid(String renwuzj);

    /**
     * 根据任务id s 获取样品图片
     * @param renwuids
     * @return
     */
    public List<Map> getYanpginPic(List<String> renwuids);





}
