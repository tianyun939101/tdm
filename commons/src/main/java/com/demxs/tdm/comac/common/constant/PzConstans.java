package com.demxs.tdm.comac.common.constant;

import com.demxs.tdm.common.config.ConstAnnotation;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设备常量
 * Created by zhangdengcai
 * Create date 2017/7/16.
 */
public class PzConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : PzConstans.class.getClasses()) {
                Map<String, String> constMap = new LinkedHashMap<String, String>();
                for (Field fd : cls.getDeclaredFields()) {
                    com.demxs.tdm.common.config.ConstAnnotation ca = fd.getAnnotation(com.demxs.tdm.common.config.ConstAnnotation.class);
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
     * 图统计类型
     */
    public static final class tutongjilx{
        @com.demxs.tdm.common.config.ConstAnnotation("柱状图")
        public static final String ZHUZHUANG ="10";
        @com.demxs.tdm.common.config.ConstAnnotation("折线图")
        public static final String ZHEXIAN ="20";
        @com.demxs.tdm.common.config.ConstAnnotation("饼图")
        public static final String BINGTU ="30";

    }
    /**
     * 图序列
     */
    public static final class xulie {
        @com.demxs.tdm.common.config.ConstAnnotation("天")
        public static final String TIAN ="10";

    }
    /**
     * 图统计方向
     */
    public static final class tutongjifx {
        @com.demxs.tdm.common.config.ConstAnnotation("横向")
        public static final String HENGXIANG ="10";
        @com.demxs.tdm.common.config.ConstAnnotation("纵向")
        public static final String ZONGXIANG ="20";
    }

    /**
     * 是否
     * 状态 0：否 1：是
     * */
    public static final class YesOrNo{
        @com.demxs.tdm.common.config.ConstAnnotation("是")
        public static final String YES ="1";
        @com.demxs.tdm.common.config.ConstAnnotation("否")
        public static final String NO="0";
    }

    /**
     * 字段类型
     * 状态 liebiao：列表 biaodan：表单
     * */
    public static final class ZiduanLx{
        @com.demxs.tdm.common.config.ConstAnnotation("表单")
        public static final String BIAODAN ="BD";
        @com.demxs.tdm.common.config.ConstAnnotation("列表")
        public static final String LIEBIAO ="LB";
    }

    /**
     * 标签类型
     * 状态 list：列表 form：表单 tool：工具
     * */
    public static final class Biaoqianlx{
        @com.demxs.tdm.common.config.ConstAnnotation("列表")
        public static final String LIEBIAO ="list";
        @com.demxs.tdm.common.config.ConstAnnotation("表单")
        public static final String BIAODAN="form";
        @com.demxs.tdm.common.config.ConstAnnotation("工具")
        public static final String GONGJU="tool";
    }
    /**
     * 标签方式
     * 状态 value：赋值 ，table ：表格
     * */
    public static final class Biaoqianfs{
        @com.demxs.tdm.common.config.ConstAnnotation("赋值")
        public static final String FUZHI ="value";
        @com.demxs.tdm.common.config.ConstAnnotation("表格")
        public static final String BIAOGE="table";
    }
    /**
     * 报告类型
     * 报告类型，申请单wtd，报告bg,试验项jcx,列表标签lbbq
     * */
    public static final class Baogaolx{
        @com.demxs.tdm.common.config.ConstAnnotation("申请单")
        public static final String WTD ="wtd";
        @com.demxs.tdm.common.config.ConstAnnotation("报告")
        public static final String BG="bg";
        @com.demxs.tdm.common.config.ConstAnnotation("试验项")
        public static final String JCX="jcx";
        @ConstAnnotation("列表标签")
        public static final String LBBQ="lbbq";

    }
}
