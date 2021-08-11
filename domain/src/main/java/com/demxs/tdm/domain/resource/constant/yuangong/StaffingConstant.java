package com.demxs.tdm.domain.resource.constant.yuangong;

/**
 *试验人力资源调配
 */
public class StaffingConstant {

	//试验人力资源调配状态
	public static final class staffingStatus{

		//用户申请
		public static final String APPLY = "1";
		//用工审批
		public static final String AUDIT = "2";
		//编制报告
		public static final String RECORD = "3";
		//完成
		public static final String FINISH = "4";
	}

	public static final class MessageTemplate{

		public static final String AUDIT_RETURN = "您的[${code}]试验人员资源调配已被审批人驳回，请重新提交申请";
		public static final String AUDIT_PASS = "您的[${code}]试验人员资源调配已经审批通过";
		public static final String DEAL_WITH = "请处理,${userName}提交的[${code}]试验人员资源调配";
		public static final String RECORD = "您的[${code}]试验人员资源调配已经完成编制报告";
	}

	//科室间用工协调状态
	public static final class laborStaffingStatus{

		//需求科室申请
		public static final String APPLY = "1";
		//需求科室审批
		public static final String AUDIT = "2";
		//响应科室申审批
		public static final String APPROVAL = "3";
		//完成
		public static final String FINISH = "4";
	}

	public static final class LaborStaffingMessage{

		public static final String AUDIT_RETURN = "您的科室用工申请已被审批人驳回，请重新提交申请";
		public static final String AUDIT_PASS = "您的科室用工申请已经审批通过";
		public static final String DEAL_WITH = "请处理,${userName}提交的科室用工申请";
	}
}
