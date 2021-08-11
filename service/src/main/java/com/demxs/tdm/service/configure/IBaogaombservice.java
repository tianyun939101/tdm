package com.demxs.tdm.service.configure;

import java.util.List;
import java.util.Map;

/**
 * @Author： 谭冬梅
 * @Description：
 * @Date：Create in 2017-07-12 10:41
 * @Modefied By：
 */
public interface IBaogaombservice {
    /**
     * @Author：谭冬梅
     * @param： * @param arrIds
     * @Description：根据模板ids 获取 模板集合
     * @Date：10:43 2017/7/12
     * @Return：
     * @Exception：
     */
    public List<Map> getBaogaomblist(String arrIds);

}
