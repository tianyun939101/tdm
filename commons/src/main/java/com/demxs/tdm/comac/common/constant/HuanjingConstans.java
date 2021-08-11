package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 环境常量类
 * Created by zhangdengcai
 * Create date 2017/7/16.
 */
public class HuanjingConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : HuanjingConstans.class.getClasses()) {
                Map<String, String> constMap = new LinkedHashMap<String, String>();
                for (Field fd : cls.getDeclaredFields()) {
                    ConstAnnotation ca = fd.getAnnotation(ConstAnnotation.class);
                    if (ca != null) {
                        constMap.put(fd.get(cls).toString(), ca.value());
                    } else {
                        constMap.put(fd.get(cls).toString(), fd.getName());
                    }
                }
                allConstAlias.put(cls.getSimpleName(), constMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 环境设备状态
     */
    public static final class hjsbStatus{
        @ConstAnnotation("正常")
        public static final String ZHENGCHANG ="0";
        @ConstAnnotation("销毁")
        public static final String XIAOHUI ="1";
    }

    /**
     * 环境数据来源(内部采集数据 和 录入的环境设备数据)
     */
    public static final class hjsjLy{
        @ConstAnnotation("内部采集")
        public static final String CAIJI ="1";
        @ConstAnnotation("录入数据")
        public static final String LURU ="2";
    }

    /**
     * 环境数据类型
     */
    public static final class hjsjLx{
        @ConstAnnotation("温湿度")
        public static final String WSD ="1";
        @ConstAnnotation("震动、噪声、辐射数据")
        public static final String ZZF ="2";
    }
}
