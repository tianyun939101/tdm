
package com.demxs.tdm.domain.business.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据中心常量类
 */
public class DataCenterConstants {
    public static final Map<String, Map<String, Integer>> allConstMap = new LinkedHashMap<String, Map<String, Integer>>();
    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : EntrustConstants.class.getClasses()) {
                Map<String, Integer> constMap = new LinkedHashMap<String, Integer>();
                for (Field fd : cls.getDeclaredFields()) {
                    try {
                        constMap.put(fd.getName(),Integer.parseInt(fd.get(cls).toString()));
                    }catch (Exception e){
                    }
                }
                allConstMap.put(cls.getSimpleName(), constMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Map<String, Integer>> getAllConstMap(){
        if(allConstMap.isEmpty()){
            for (Class cls : EntrustConstants.class.getClasses()) {
                Map<String, Integer> constMap = new LinkedHashMap<String, Integer>();
                for (Field fd : cls.getDeclaredFields()) {
                    try {
                        constMap.put(fd.getName(), Integer.parseInt(fd.get(cls).toString()));
                    }catch (Exception e){
                    }
                }
                allConstMap.put(cls.getSimpleName(), constMap);
            }
            return allConstMap;
        }
        return allConstMap;
    }
    //数据分类
    public static final class DataType{
        //原始记录单
        public static final String ORIGINAL_RECORD = "1";
        //试验数据
        public static final String TEST_DATA = "2";
        //试验日志
        public static final String TEST_LOG = "3";
        //视频数据
        public static final String VIDEO = "4";
        //音频数据
        public static final String AUDIO = "5";
        //图片数据
        public static final String IMAGE = "6";
    }

    /**
     * @author: Jason
     * @Date: 2020/7/20 11:03
     * @Description: 数据中心提报归属公司常量
     */
    public final static class DataReportCompany {
        //上飞公司
        public final static String SF = "1";
        //上飞院
        public final static String SF_INSTITUTE = "2";
        //试验服务商
        public final static String SERVICE_PROVIDER = "5";
    }

    //数据中心委托单类型
    public static final class EntrustType  {
        //提报
        public static final String UPLOAD = "1";
        //标准
        public static final String STANDARD = "2";
        //非标
        public static final String NO_STANDARD = "3";
        //服务商提报
        public static final String PROVIDER = "4";

    }

    //数据权限类型
    public static final class PermissionType{
        //1查看权限
        public static final String SEARCH = "1";
        //2提报页操作权限
        public static final String REPORT = "2";

    }

    //数据权限授权类型
    public static final class AuthorizationType{
        //1人员
        public static final String PERSON = "1";
        //2部门
        public static final String ORG = "2";

    }

    //数据权限授权范围
    public static final class AuthorizationScope{
        //1全部
        public static final String ALL = "1";
        //2时间段
        public static final String PERIOD = "2";

    }

    public static final class DataReportStatus{
        //已保存
        public static final String saved = "1";
        //审核
        public static final String audit = "2";
        //审核通过
        public static final String approved = "3";
        //重新提报
        public static final String resubmit = "4";

    }

    public static final class MessageTemplate{

        public static final String AUDIT_RETURN = "您的数据中心查看申请[${code}]已被审批人驳回，请重新提交申请";
        public static final String AUDIT_PASS = "您的数据中心查看申请[${code}]已经审批通过";
        public static final String DATA_REPORT_APPLY = "请处理,${userName}提交的[${code}]数据中心查看申请";

    }

    public static final class MessageTemplate1{

        public static final String AUDIT_RETURN = "您的数据提报申请[${code}]已被审批人驳回，请重新提交申请";
        public static final String AUDIT_PASS = "您的数据提报申请[${code}]已经审批通过";
        public static final String DATA_REPORT_APPLY = "请处理,${userName}提交的[${code}]数据提报申请";

    }
}
