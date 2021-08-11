package com.demxs.tdm.comac.common.constant;

/**
 * 系统中常量定义
 * User: wuliepeng
 * Date: 2017-10-31
 * Time: 下午7:30
 */
public class SysConstants {

    public static final String ROOT = "0";

    //是或否
    public static final class YES_NO{
        @ConstAnnotation("是")
        public static final Integer YES = 1;
        @ConstAnnotation("否")
        public static final Integer NO = 0;
    }


    public static final class QUARTZ_TYPE{
        @ConstAnnotation("同步组织")
        public static final String SYNCOFFICES = "1";
        @ConstAnnotation("同步用户")
        public static final String SYNCUSERS = "2";
        @ConstAnnotation("定检")
        public static final String DJ = "3";
        @ConstAnnotation("保养")
        public static final String BY = "4";
    }

    public static final class QUARTZ_RESULT{
        @ConstAnnotation("成功")
        public static final String SUCCESS = "1";
        @ConstAnnotation("失败")
        public static final String FAIL = "2";
    }
}
