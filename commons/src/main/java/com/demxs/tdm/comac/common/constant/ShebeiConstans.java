package com.demxs.tdm.comac.common.constant;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设备常量
 * Created by zhangdengcai
 * Create date 2017/7/16.
 */
public class ShebeiConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : ShebeiConstans.class.getClasses()) {
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
     * 设备状态
     */
    public static final class shebeizt{
        @ConstAnnotation("可使用")
        public static final String KONGXIAN ="0";
        @ConstAnnotation("已停止")
        public static final String ZHANYONG ="1";
        @ConstAnnotation("已报废")
        public static final String YIBAOF ="6";
    }

    /**
     * 设备启停状态
     * 1启动 0否
     */
    public static final class ShebeiQTZT{
        @ConstAnnotation("启动")
        public static final String START ="1";
        @ConstAnnotation("停止")
        public static final String STOP="0";
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
     * 设备审核状态
     */
    public static final class sheBeicgdzt{
        @ConstAnnotation("待提交")
        public static final String DAITIJ ="0";
        @ConstAnnotation("待审核")
        public static final String DAISHENH ="1";
        @ConstAnnotation("审核通过")
        public static final String YISHENH ="2";
        @ConstAnnotation("驳回")
        public static final String BOHUI ="3";
    }

    /**
     * 设备采购单审核结果
     */
    public static final class sheBeishjg {
        @ConstAnnotation("通过")
        public static final String TONGGUO = "1";
        @ConstAnnotation("不通过")
        public static final String BUTONGG = "0";
    }

    /**
     * 角色英文名
     * */
    public static final class Jueseywm{
        public static final String SHEBEIGLY ="shebeigly";//设备管理员
    }

    /**
     * 状态
     */
    public static final class actStatus{
        @ConstAnnotation("待提交")
        public static final String DAITIJIAO ="0";
        @ConstAnnotation("待审核")
        public static final String DAISHENHE="1";
        @ConstAnnotation("审核通过")
        public static final String YISHENHE="2";
        @ConstAnnotation("审核驳回")
        public static final String BOHUI="3";
    }

    /**
     * 状态
     */
    public static final class quartzClassType{
        public static final String SHEBEI_QT_SEND ="0";
        public static final String SHEBEI_QT ="1";
        public static final String SHEBEI_DJ_TX="2";
        public static final String SHEBEI_DJ="3";
        public static final String SHEBEI_BY_TX="4";
        public static final String SHEBEI_BY="5";
    }

    public static final String SHEBEIYT ="shebeiyt";//设备用途数据字典类型

    public static final String GUOBIE  = "guobie";//国别

    public static final String OVER = "_over";

    public static String getValue(String pKey,String cKey){
        String value = "";
        Map<String, Map<String, String>>  map = ShebeiConstans.allConstAlias;
        Iterator<Map.Entry<String, Map<String, String>>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Map<String, String>> entry = entries.next();
            if(entry.getKey().equals(pKey)){
                Map<String, String> valueMap =   entry.getValue();
                Iterator<Map.Entry<String,String>>  valueEntries = valueMap.entrySet().iterator();
                while(valueEntries.hasNext()){
                    Map.Entry<String,String> valueEntity = valueEntries.next();
                  if(valueEntity.getKey().equals(cKey)){
                      System.out.println("Key = " + valueEntity.getKey()+ ", Value = " + valueEntity.getValue());
                      value=valueEntity.getValue();
                  }
                }
            }

        }
        return value;
    }

    public static void main(String[] args) {
        /*Map<String, Map<String, String>>  map = ShebeiConstans.allConstAlias;
        Iterator<Map.Entry<String, Map<String, String>>> entries = map.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<String, Map<String, String>> entry = entries.next();
            System.out.println("Key = " + entry.getKey());
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            Map<String, String> valueMap =   entry.getValue();
            Iterator<Map.Entry<String,String>>  valueEntries = valueMap.entrySet().iterator();
            while(valueEntries.hasNext()){
                Map.Entry<String,String> valueEntity = valueEntries.next();
                System.out.println("Key = " + valueEntity.getKey()+ ", Value = " + valueEntity.getValue());
            }

        }*/
        System.out.println(getValue("shebeizt","0"));
    }

}
