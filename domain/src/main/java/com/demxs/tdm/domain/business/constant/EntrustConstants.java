package com.demxs.tdm.domain.business.constant;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 试验科目常量类
 * User: wuliepeng
 * Date: 2017-10-31
 * Time: 下午5:44
 */
public class EntrustConstants {
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

    //试验阶段定义
    public static final class Stage{
        //申请
        public static final Integer ENTRUST = 10;
        //调度
        public static final Integer DISPATCH = 20;
        //试验
        public static final Integer TASK = 30;
        //报告
        public static final Integer REPORT = 40;
        //结束
        public static final Integer FINISH = 50;
    }

    //申请状态定义
    public static final class EntrustStage{
        //草稿
        public static final Integer DRAFT = 10100;
        //审核
        public static final Integer AUDIT = 10200;
        //退回
        public static final Integer AUDIT_RETURN = 10300;
        //接收
        public static final Integer ACCEPT = 10400;
        //不接收
        public static final Integer ACCEPT_RETURN = 10500;
    }

    //调度阶段状态定义
    public static final class DispatchStage{
        //确认样品
        public static final Integer CONFIRM_SAMPLE = 20100;
        //修改样品
        public static final Integer MODIFY_SAMPLE = 20200;
        //编制计划
        public static final Integer CREATE_PLAN = 20300;
        //分配任务
        public static final Integer ASSIGN_TASK = 20400;
    }

    //试验任务阶段状态定义
    public static final class TaskStage{
        //未分配
        public static final Integer NOASSIGN = 30100;
        //已分配
        public static final Integer ASSIGN = 30200;
        //待执行
        public static final Integer TODO = 30300;
        //执行中
        public static final Integer EXECUTING = 30400;
        //已完成
        public static final Integer DONE = 30500;
        //已终止
        public static final Integer STOP = 30600;
    }

    //试验任务执行状态定义
    public static final class TaskStatus{
        //未分配
        public static final Integer NOASSIGN = 1;
        //已分配
        public static final Integer ASSIGN = 2;
        //待执行
        public static final Integer TODO = 3;
        //执行中
        public static final Integer EXECUTING = 4;
        //暂停
        public static final Integer SUSPEND = 5;
        //原始记录单审核
        public static final Integer ORIGIN_RECORD_EXAMINE = 6;
        //完成
        public static final Integer DONE = 7;
        //终止
        public static final Integer STOP = 8;
    }

    //执行干道状态定义
    public static final class ExecutionStatus{
        //未开始
        public static final Integer NOSTART = 1;
        //执行中
        public static final Integer EXECUTING = 2;
        //完成
        public static final Integer DONE = 3;

    }

    //报告阶段
    public static final class ReportStage{
        //编制报告
        public static final Integer DRAW = 40100;
        //审核
        public static final Integer AUDIT = 40200;
        //驳回
        public static final Integer REJECT = 40300;
        //批准
        public static final Integer PASS = 40400;
    }

    //报告状态
    public static final class ReportStatus{
        //编制
        public static final Integer DRAW = 1;
        //审核
        public static final Integer AUDIT = 2;
        //批准
        public static final Integer PASS = 3;
        //驳回
        public static final Integer REJECT = 4;
        //完成
        public static final Integer DONE = 5;
    }

    //申请结束阶段
    public static final class FinishStage{
        //完成
        public static final Integer DONE = 50100;
        //撤消
        public static final Integer UNDO = 50200;
        //终止
        public static final Integer STOP = 50300;
    }

    //试验能力类别
    public static final class Ability_Type{
        //试验项
        public static final Integer UNIT = 1;
        //试验项目
        public static final Integer ITEM = 2;
        //试验序列
        public static final Integer SEQUENCE = 3;
        //自定义序列
        public static final Integer CUSTOM_SEQUENCE = 4;
    }

    //样品类型
    public static final class Sample_Type{
        //成品
        public static final Integer PRODUCT = 1;
        //半成品
        public static final Integer PART_PRODUCT = 2;
        //材料
        public static final Integer SCIENCE = 3;
    }

    //试验数据类型
    public static final class Test_Data_Type{
        //数据文件
        public static final Integer FILE = 1;
        //图片
        public static final Integer IMAGE = 2;
    }

    //审核类型
    public static final class AuditType{
        //申请单提交
        public static final Integer ENTRUST_APPLY = 1;
        //申请单审核
        public static final Integer ENTRUST_AUDIT = 2;
        //申请单接收
        public static final Integer ENTRUST_ACCEPT = 3;
        //申请单样品确认
        public static final Integer SAMPLE_CONFIRM = 4;
        //原始记录单审核
        public static final Integer ORIGIN_RECORD = 5;
        //报告审核
        public static final Integer REPORT_RECORD = 6;
        //信息反馈
        public static final Integer ENTRUST_FEEDBACK = 7;
    }

    //审核结果类型
    public static final class AuditResult{
        //通过
        public static final Integer PASS = 1;
        //拒绝
        public static final Integer RETURN = 0;
        //提交
        public static final Integer APPLY = -1;
        //信息反馈
        public static final Integer FEEDBACK = 2;
    }

    public static final class MessageTemplate{
        //申请单审批
        public static final String Audit = "请处理,${userName}提交的[${code}]试验申请申请";
        //审批通过后提示申请人
        public static final String Audit_Pass = "您的试验申请申请[${code}]已经审批通过，请及时送样";
        //审批驳回提示申请人
        public static final String Audit_Return = "您的试验申请申请[${code}]已被审批人驳回，请重新提交申请";
        //确认样品
        public static final String Confirm_Sample = "申请单[${code}]需要您进行样品确认，请尽快处理";
        //修改样品
        public static final String Modify_Sample = "您的试验申请申请[${code}]样品存在问题，请修改";
        //排期
        public static final String Plan = "您有新的申请单[${code}]需要进行试验排期，请尽快处理";
        //任务分配
        public static final String Task_Assign = "申请单[${code}]需要您负责执行和管理分配";
        //任务处理
        public static final String Task_Execute = "您有新的试验任务[${taskCode}]，请尽快处理";

        public static final String Task_Audit = "您有新的试验数据需要审核[${taskCode}]，请尽快处理";
        //原始记录驳回
        public static final String Task_Return = "您的试验数据[${taskCode}]被审核人驳回，请重新处理";
        //编制报告
        public static final String Report_Draw = "申请单[${code}]试验已经完成，请尽快生成报告";
        //报告驳回
        public static final String Report_ReDraw = "您提交的申请单[${code}]的试验报告被驳回，请重新处理";
        //审核报告
        public static final String Report_Audit = "申请单[${code}]的试验报告需要您审核，请尽快生成报告";

        public static final String Report_PASS = "申请单[${code}]的试验报告需要您批准，请尽快生成报告";
        //申请完成
        public static final String Complete = "申请单[${code}]试验已完成";

        public static final String APPLY_TASK = "请审批试验数据";

        public static final String NOTICE_PUSH_SAMPLE = "请尽快与试验负责人${userName}联系，确认申请单[${code}]样品送检时间";

        /**
         * 申请查看报告
         */
        public static final String APPLY_VIEW_REPORT = "${applyUser}申请查看报告，报告编号：${code}，请提供该资料";

        public static final String EXECUTION = "申请单[${code}]已通过审核，请执行试验任务";

        public static final String NO_PASS = "申请单[${code}]被驳回，请重新制定资源分配计划";

        public static final String NO_STANDARD_AUDIT = "请处理,${userName}提交的[${code}]非标试验申请";

        public static final String NO_STANDARD_RESOURCE = "请处理,${userName}提交的[${code}]资源分配计划";

        public static final String NO_STANDARD_EXECUTION = "请处理,${userName}提交的[${code}]任务执行单";

        public static final String NO_STANDARD_REPORT = "请处理,${userName}提交的[${code}]试验报告";
    }

    //申请单类型
    public static final class EntrustType  {
        //提报
        public static final String UPLOAD = "1";
        //标准
        public static final String STANDARD = "2";
        //非标
        public static final String NO_STANDARD = "3";

    }

}
