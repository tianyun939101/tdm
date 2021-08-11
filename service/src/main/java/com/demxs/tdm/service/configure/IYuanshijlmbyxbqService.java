package com.demxs.tdm.service.configure;

import com.demxs.tdm.common.utils.zrutils.BiaoqianJson;

import java.util.List;

/**
 * @Author： 谭冬梅
 * @Description：
 * @Date：Create in 2017-07-26 17:58
 * @Modefied By：
 */
public interface IYuanshijlmbyxbqService {
    /**
     * 查询某个原始记录模板的已选标签
     * @param mbid  模板id
     * @param biaoqianlx  标签类型
     * @return
     */
    public List<BiaoqianJson> getYuanshijlmbyxbqList(String mbid, String biaoqianlx);

    /**
     * 查询某个原始记录模板的已选标签 （采集类的）
     * @param mbid  模板id
     * @param biaoqianlx  标签类型
     * @return
     */
    public List<BiaoqianJson> getCJYuanshijlmbyxbqList(String mbid, String biaoqianlx);
}
