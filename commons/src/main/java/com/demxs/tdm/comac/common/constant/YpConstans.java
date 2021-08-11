package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author： 詹小梅
 * @Description：
 * @Date：Create in 2017-06-29 14:48
 * @Modefied By：
 */
public class YpConstans {
    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : YpConstans.class.getClasses()) {
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
     * 样品状态
     */
    public static final class optStatus{
        @ConstAnnotation("待入库")
        public static final String DAIRUKU ="0";//单个样品无此状态
        @ConstAnnotation("在库")
        public static final String ZAIKU ="0.1";
        @ConstAnnotation("待检")
        public static final String DAIJIAN="1";
        @ConstAnnotation("在检")
        public static final String ZAIJIAN="2";
        @ConstAnnotation("已检")
        public static final String YIJIAN="3";
        @ConstAnnotation("已归还")
        public static final String YIGUIHUAN="4";
        @ConstAnnotation("留样")
        public static final String LIUYANG="5";
        @ConstAnnotation("已消耗")
        public static final String YIXIAOHAO="6";
        @ConstAnnotation("已处理")
        public static final String YICHULI="7";
    }

    /**
     * 是否
     * 状态 0：否 1：是
     * */
    public static final class YesOrNo{
        @ConstAnnotation("是")
        public static final String YES ="1";
        @ConstAnnotation("否")
        public static final String NO="0";
    }

    /**
     * 样品操作
     */
    public static final class optYangpincz{
        @ConstAnnotation("入库")
        public static final String RUKU="1";
        @ConstAnnotation("出库")
        public static final String CHUKU="2";
        @ConstAnnotation("返库")
        public static final String FANKU="3";
        @ConstAnnotation("自行处理")
        public static final String ZIXINGCHULI="4";
        @ConstAnnotation("归还")
        public static final String GUIHUAN="5";

    }

    /**
     * 样品组状态
     */
    public static final class optStatusYpz{
        @ConstAnnotation("暂存")
        public static final String ZANCUN="-1";
        @ConstAnnotation("待入库")
        public static final String DAIRUKU="0";
    }

    /**
     * 样品类型（样品入库/出库试验/返库/归还/自行处理）
     * leixing
     */
    public static final class optStatusYplz{
        @ConstAnnotation("入库")
        public static final String RUKU="0";
        @ConstAnnotation("出库试验")
        public static final String CHUKUJIANCE="1";
        @ConstAnnotation("返库")
        public static final String FANKU="2";
        @ConstAnnotation("归还")
        public static final String GUIHUAN="3";
        @ConstAnnotation("自行处理")
        public static final String ZIXINGCHULI="4";
    }

    /**
     * 有效性
     */
    public static final class optYouxiaox{
        @ConstAnnotation("有效")
        public static final String YOUXIAO ="1";
        @ConstAnnotation("无效")
        public static final String WUXIAO="0";
    }

    /**
     * 数据标签
     */
    public static final class optShuJuBq{
        @ConstAnnotation("当前项目可用")
        public static final String DANGQIANXMKY="0";
        @ConstAnnotation("待深入研究")
        public static final String DAISHENRYJ="1";
        @ConstAnnotation("可供参考")
        public static final String KEGONGCANKAO="2";
        @ConstAnnotation("典型")
        public static final String DIANXING="3";
        @ConstAnnotation("无效")
        public static final String WUXIAO="4";
    }




}
