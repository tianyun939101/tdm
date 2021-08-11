package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 标准物质常量类
 * Created by zhangdengcai
 * Create date 2017/7/16.
 */
public class BiaozhunwzConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : BiaozhunwzConstans.class.getClasses()) {
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
     * 标准物质流转类型
     */
    public static final class biaoZhunwzLzlx{
        @ConstAnnotation("入库")
        public static final String RUKU ="0";
        @ConstAnnotation("出库试验")
        public static final String CHUKUJC ="1";
        @ConstAnnotation("出库核查")
        public static final String CHUKUHC ="2";
        @ConstAnnotation("自行处理")
        public static final String ZIXINGCHULI ="3";
        @ConstAnnotation("返库")
        public static final String FANKU ="4";
    }

    /**
     * 标准物质状态
     */
    public static final class biaoZhunwzzt{
        @ConstAnnotation("待提交")
        public static final String DAITIJ ="-1";
        @ConstAnnotation("空闲")
        public static final String KONGXIAN ="0";
        @ConstAnnotation("使用中")
        public static final String SHIYONGZ ="1";
        @ConstAnnotation("已过期")
        public static final String YIGUOQ ="2";
        @ConstAnnotation("已消耗")
        public static final String YIXIAOH ="3";
        @ConstAnnotation("已处理")
        public static final String YICHUL ="4";
    }

    /**
     * 有效性
     */
    public static final class youxiaox {
        @ConstAnnotation("有效")
        public static final String YOUXIAO ="1";
        @ConstAnnotation("无效")
        public static final String WUXIAO ="0";
    }

    /**
     * 标准物质采购单状态
     */
    public static final class biaoZhunwzcgdzt{
        @ConstAnnotation("待提交")
        public static final String DAITIJ ="0";
        @ConstAnnotation("待审核")
        public static final String DAISHENH ="1";
        @ConstAnnotation("已审核")
        public static final String YISHENH ="2";
    }

    /**
     * 标准物质采购单审核结果
     */
    public static final class biaoZhunwzshjg {
        @ConstAnnotation("通过")
        public static final String TONGGUO = "1";
        @ConstAnnotation("不通过")
        public static final String BUTONGG = "0";
    }
}
