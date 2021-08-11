package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class SampleConstants {

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
    public static final class sampleStatus{
        @ConstAnnotation("待入库")
        public static final Integer NO_IN =0;
        @ConstAnnotation("待检")
        public static final Integer WAIT_INSPECTION =1;
        @ConstAnnotation("在检")
        public static final Integer IN_INSPECTION=2;
        @ConstAnnotation("已检")
        public static final Integer CHECKED=3;
        @ConstAnnotation("已归还")
        public static final Integer RETURN =4;
        @ConstAnnotation("已报废")
        public static final Integer SCRAPPING=5;

    }


    /**
     * 样品操作类型
     */
    public static final class sampleOperateType{
        @ConstAnnotation("入库")
        public static final String STORAGE ="0";
        @ConstAnnotation("出库试验")
        public static final String OUTCHECK ="1";
        @ConstAnnotation("检毕入库")
        public static final String INCHECK="2";
        @ConstAnnotation("归还")
        public static final String RETURNED="3";
        @ConstAnnotation("试验室处理")
        public static final String LABHANDLING ="4";

    }

    public static final String SAMPLETYPE = "sample_type";

}
