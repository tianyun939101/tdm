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
public class NengliConstans {
    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : NengliConstans.class.getClasses()) {
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
     * 方法状态
     */
    public static final class optFFStatus{
        @ConstAnnotation("待审核")
        public static final String DAISHENHE ="0";
        @ConstAnnotation("待验证")
        public static final String DAIYANZHENG="1";
        @ConstAnnotation("已验证")
        public static final String YIYANZHEN="2";
        @ConstAnnotation("无效")
        public static final String WUXIAO="3";
        @ConstAnnotation("待提交")
        public static final String DAITIJIAO="4";
    }

    /**
     * 方案状态
     */
    public static final class optFAStatus{
        @ConstAnnotation("待验证")
        public static final String DAIYANZHENG="0";
        @ConstAnnotation("已验证")
        public static final String YIYANZHEN="1";
        @ConstAnnotation("无效")
        public static final String WUXIAO="2";
        @ConstAnnotation("待提交")
        public static final String DAITIJIAO="3";

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
     * 是否消耗样品
     */
    public static final class YesOrNo{
        @ConstAnnotation("是")
        public static final String YES ="0";
        @ConstAnnotation("否")
        public static final String NO="1";
    }

    /**
     * 角色英文名称
     */
    public static final class optRolEnName{
        @ConstAnnotation("试验负责人")
        public static final String JIANCEFUZEREN ="jiancefzr";
        @ConstAnnotation("能力库管理员")
        public static final String NENGLIKUGLY="nenglikgly";
        @ConstAnnotation("试验室负责人")
        public static final String SHIYANSFZR ="shiyansfzr";
        @ConstAnnotation("业务审核人")
        public static final String YEWUSHENHEREN="yewushr";
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
