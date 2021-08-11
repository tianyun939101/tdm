package com.demxs.tdm.comac.common.constant;


import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 消息规则
 */
public class MessageConstants {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();


    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : MessageConstants.class.getClasses()) {
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
    public static final class shebeiMessage{
        public static final String ADD = "您有新增设备申请，请尽快处理。";
        public static final String OVER = "您有报废设备申请，请尽快处理。";
        public static final String REPAIR = "您有维修设备申请，请尽快处理。";

    }

    public static final class zhishiMessage{
        public static final String ADD = "您有新增知识申请，请尽快处理。";
    }

    public static final class dxalAmend{
        public static final String AMEND = "您有修改典型案例申请，请尽快处理。";
    }

    public static final class zhishiAuditMessage{
        public static final String ADD = "%s提交的%s知识申请，请尽快处理。";
    }

    public static final class gzzdAuditMessage{
        public static final String ADD = "%s提交的%s新增规章制度申请，请尽快处理。";
    }


    public static final class auditMessage{
        public static final String ADD = "新增申请";
        public static final String OVER = "报废申请";
        public static final String REPAIR = "维修申请";
    }

    public static final class qtMessage{
        public static final String QT = "%s编号%s设备于%s时间%s，请查收";
        public static final String TX = "%s编号%s设备于%s时间%s，已超期,请查收";

    }

    public static void main(String[] args) {
        System.out.println(String.format(qtMessage.QT,"123","456","11","stop"));
    }
}
