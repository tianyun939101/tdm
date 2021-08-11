package com.demxs.tdm.domain.ability.constant;

/**
 * Created by chenjinfan on 2017/11/9.
 */
public class AbilityConstants {
	/**
	 * 试验序列中项的类型
	 */
	public static final class TestSequenceItemFlag{
		/**
		 * 序列
		 */
		public static final String SEQ = "1";
		/**
		 * 试验项目
		 */
		public static final String ITEM = "0";
	}

    /**
     * 能力图谱修改流程
     */
	public static final class TestCategoryModifyAuditProcess{
        /**
         * 流程标识
         */
        public static final String PROCESS = "testCategoryModify";
        public static final String REJECT = "能力图谱修改申请被驳回，请重新修改";
        public static final String AUDIT = "请处理：${userName}提交的能力图谱修改申请";
        public static final String JOINTLY_SING = "会签：${userName}提交的能力图谱修改申请";
        public static final String EXAMINATION = "请审批：${userName}提交的能力图谱修改申请";
        public static final String APPROVAL = "请批准：${userName}提交的能力图谱修改申请";
    }

    /**
     * 能力评估申请流程
     */
    public static final class TestCategoryAssessmentAuditProcess{
        /**
         * activity唯一标识
         */
	    public static final String PROCESS = "abilityAssessment";
        public static final String REJECT = "能力评估申请被驳回，请重新修改";
        public static final String GENERIC = "请处理：${userName}提交的能力评估申请";
    }
}
