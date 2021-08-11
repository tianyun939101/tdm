package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 耗材常量类
 * Created by zhangdengcai
 * Create 2017/7/17.
 */
public class XiangmuConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : XiangmuConstans.class.getClasses()) {
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
     * 项目状态
     */
    public static final class Xiangmuzt{
        @ConstAnnotation("进行中")
        public static final String JINXINGZHONG ="0";
        @ConstAnnotation("正常完结")
        public static final String ZHENGCHANGWANJIE ="1";
        @ConstAnnotation("暂停")
        public static final String ZANTING ="2";
        @ConstAnnotation("异常终止")
        public static final String YICHANGZHOGNZHI ="3";
    }
    /**
     * 项目操作类型
     */
    public static final class Caozuolx{
        @ConstAnnotation("新建项目")
        public static final String XINJIAN ="0";
        @ConstAnnotation("继续")
        public static final String JIXU ="1";
        @ConstAnnotation("完结")
        public static final String WANJIE ="2";
        @ConstAnnotation("暂停")
        public static final String ZANTING ="3";
        @ConstAnnotation("异常终止")
        public static final String YICHANGZHOGNZHI ="4";

    }

}
