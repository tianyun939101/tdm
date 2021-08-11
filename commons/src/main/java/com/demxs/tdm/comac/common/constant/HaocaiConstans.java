package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 耗材常量类
 * Created by zhangdengcai
 * Create 2017/7/17.
 */
public class HaocaiConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : HaocaiConstans.class.getClasses()) {
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
     * 耗材流转类型
     */
    public static final class haoCaiLzlx{
        @ConstAnnotation("入库")
        public static final String RUKU ="0";
        @ConstAnnotation("出库")
        public static final String CHUKU ="1";
        @ConstAnnotation("返库")
        public static final String FANKU ="2";
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
     * 耗材采购单状态
     */
    public static final class haoCaicgdzt{
        @ConstAnnotation("待提交")
        public static final String DAITIJ ="0";
        @ConstAnnotation("待审核")
        public static final String DAISHENH ="1";
        @ConstAnnotation("已审核")
        public static final String YISHENH ="2";
    }

    /**
     * 耗材采购单审核结果
     */
    public static final class haoCaishjg {
        @ConstAnnotation("通过")
        public static final String TONGGUO = "1";
        @ConstAnnotation("不通过")
        public static final String BUTONGG = "0";
    }

    /**
     * 数据状态
     */
    public static final class haoCaisjzt {
        @ConstAnnotation("待提交")
        public static final String DAITIJ = "0";
        @ConstAnnotation("已提交")
        public static final String YITIJ = "1";
    }
}
