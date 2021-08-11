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
public class ZhishiConstans {
    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : ZhishiConstans.class.getClasses()) {
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
     * 知识状态
     */
    public static final class optStatus{
        @ConstAnnotation("待提交")
        public static final String DAITIJIAO ="0";
        @ConstAnnotation("待审核")
        public static final String DAISHENHE="1";
        @ConstAnnotation("审核通过")
        public static final String YISHENHE="2";
        @ConstAnnotation("驳回")
        public static final String BOHUI="3";
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
     * 审核结果
     */
    public static final class optIsPass{
        @ConstAnnotation("通过")
        public static final String TONGGUO ="1";
        @ConstAnnotation("不通过")
        public static final String BUTONGGUO="0";
    }
}
