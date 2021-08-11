package com.demxs.tdm.domain.bpm.constants;

import com.demxs.tdm.comac.common.constant.ConstAnnotation;

/**
 * 流程使用变量
 */
public class BpmConst {
    /**
     * 上一步的组织ID。
     */
    public static final String PRE_ORG_ID="preOrgId";
    /**
     * 发起人的组织ID。
     */
    public static final String START_ORG_ID="startOrgId";

    /**
     * 开始变量
     */
    public static final String StartUser="startUser";
    /**
     * 上一个用户变量。
     */
    public static final String PrevUser="prevUser";

    /**
     * executionid
     */
    public static String EXECUTION_ID_="executionId";


    //是或否
    public static final class DEF_STATUS{
        @ConstAnnotation("是")
        public static final Integer UNDEPLOYED = 0;
        @ConstAnnotation("否")
        public static final Integer DEPLOYED = 1;
    }
}
