package com.demxs.tdm.common.config;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by guojinlong on 2017/4/28.
 */
public class GlobalConstans {

    public static final Map<String, Map<String, String>> allConstAlias = new LinkedHashMap<String, Map<String, String>>();

    /**
     * 初始化所有常量
     */
    static {
        try {
            for (Class cls : GlobalConstans.class.getClasses()) {
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
     * 流程定义类型
     * */
    public static final class ActProcDefKey{
        public static final String SYGZZD ="syyzgzzdsp";//知识审核
        public static final String AMEND ="dianxinganlisp";//典型案例
        public static final String ZHISHISH ="zhishish";//知识审核
//        public static final String YICHANGSH ="yichangsh";//异常审核
        public static final String SHEBEIXZSH ="shebeixzsh";//设备新增审核
        public static final String SHEBEIBFSH ="shebeibfsh";//设备报废审核
        public static final String SHEBEIWXSH ="shebeiwxsh";//设备维修审核
        public static final String WEITUODSH ="weituodsh";//申请单审核
        public static final String SHIYANRW ="shiyanrw";//试验任务流程
        public static final String NO_STANDARD_PROCESS = "noStandardProcess";
        public static final String DATA_REPORT = "dataReport";
        public static final String STAFFING = "staffing";
        public static final String LABOR_STAFFING = "laborStaffing";
        public static final String REPORT = "report";
        public static final String NO_STANDARD_EXECUTION = "noStandardExecution";
        public static final String TEST_DATA_DOWNLOAD = "testDataDownAduit";//测试数据下载审批
        public static final String DATA_PROCESS ="dataProcess";//数据提报审批
        public static final String PROJECT_MANAGER ="project_manager";//项目建设
        public static final String DATA_QTG_PROCESS ="data_qtg_process";//数据提报审批data_qtg_process
        public static final String DATA_TEST_PROCESS ="dataTestProcess";//数据提报审批data_qtg_process
    }

}
