package com.demxs.tdm.service.resource.huanjing;

/**
 * 环境数据接口
 * Created by zhangdengcai
 * Create date 2017/7/20.
 */
public interface IHuanjingSj {

    /**
     * 保存环境数据
     * @param shebieid;		// 设备id
     * @param shijian;		// 时间
     * @param wendu;		// 温度
     * @param shidu;		// 湿度
     */
    public void saveSj(String shebieid, String shijian, String wendu, String shidu);
}
