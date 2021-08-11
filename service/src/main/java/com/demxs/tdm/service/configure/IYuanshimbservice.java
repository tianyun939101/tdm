package com.demxs.tdm.service.configure;

/**
 * @Author： 谭冬梅
 * @Description：
 * @Date：Create in 2017-07-17 15:15
 * @Modefied By：
 */
public interface IYuanshimbservice {
    /**
     * 获取原始记录模板名称
     * @param yuanshijlmbid
     * @return
     */
    public String getYuanshijlmbmc(String yuanshijlmbid);

    /**
     * 获取原始记录模板的配置的采集规则id
     * @param yuanshijlmbid
     * @return
     */
    public String getYuanshijlcaijigzid(String yuanshijlmbid);
}
