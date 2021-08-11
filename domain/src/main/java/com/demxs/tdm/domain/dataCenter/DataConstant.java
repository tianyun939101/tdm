package com.demxs.tdm.domain.dataCenter;

/**
 *试验人力资源调配
 */
public class DataConstant {

	//试验人力资源调配状态
	public static final class staffingStatus{

		//用户申请
		public static final String APPLY = "1";
		//一级审批
		public static final String AUDIT = "2";
		//二级审批
		public static final String RECORD = "3";
		//完成
		public static final String FINISH = "4";
	}

	public static final class MessageTemplate{

		public static final String AUDIT_RETURN = "您的[${code}]试验项目已被审批人驳回，请重新提交申请";
		public static final String AUDIT_PASS = "您的[${code}]试验项目调配已经审批通过";
		public static final String DEAL_WITH = "请处理,${userName}提交的[${code}]试验项目";
		public static final String RECORD = "您的[${code}]试验项目已经完成";
	}

	//科室间用工协调状态
	public static final class dataConstantStatus{

		//用户申请
		public static final String APPLY = "1";
		//用户审批
		public static final String AUDIT = "2";
		//数据管理员审批
		public static final String RECORD = "3";

		//完成
		public static final String FINISH = "4";

		//拒绝
		public static final String REJECT = "5";
	}

	public static final class dataConstantMessage{

		public static final String AUDIT_RETURN = "您的试验项目申请已被审批人驳回，请重新提交申请";
		public static final String AUDIT_PASS = "您的试验项目申请已经审批通过";
		public static final String DEAL_WITH = "请处理${userName}提交的试验项目申请";
		public static final String PROCESS="dataProcess";
	}

	//科室间用工协调状态
	public static final class dataProjectStatus{

		//用户申请
		public static final String APPLY = "1";
		//完成
		public static final String FINISH = "4";

		//拒绝
		public static final String REJECT = "5";
	}

	public static final class dataProjectMessage{

		public static final String AUDIT_RETURN = "您的申请已被审批人驳回，请重新提交申请";
		public static final String AUDIT_PASS = "您的申请已经审批通过";
		public static final String DEAL_WITH = "请处理${userName}提交的项目建设申请";
		public static final String PROCESS="project_manager";
	}
}
