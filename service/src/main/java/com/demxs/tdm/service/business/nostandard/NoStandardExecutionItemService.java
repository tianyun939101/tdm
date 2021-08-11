package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.dao.business.nostandard.NoStandardExecutionItemDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.constant.NoStandardExecutionEnum;
import com.demxs.tdm.domain.business.constant.NoStandardExecutionItemEnum;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecution;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecutionItem;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Jason
 * @Date: 2020/3/6 09:16
 * @Description:
 */

@Service
@Transactional(readOnly = true)
public class NoStandardExecutionItemService extends CrudService<NoStandardExecutionItemDao, NoStandardExecutionItem> {

    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private NoStandardExecutionService executionService;

    @Transactional(readOnly =  false)
    public int deleteByExecutionId(String id){
        return super.dao.deleteByExecutionId(id);
    }

    @Transactional(readOnly = false)
    public int updateActive(NoStandardExecutionItem executionItem){
        return super.dao.updateActive(executionItem);
    }

    @Transactional(readOnly = false)
    public int changeStatus(NoStandardExecutionItem executionItem){
        //提交任务
        if(NoStandardExecutionItemEnum.IN_AUDIT.getCode().equals(executionItem.getStatus())){
            NoStandardExecution execution = executionService.getBase(executionItem.getExecutionId());
            String testManagerId = execution.getTestManager().getUserIds();
            User u = UserUtils.get(testManagerId);
            User createBy = UserUtils.getUser();
            List<AuditInfo> auditInfoList = auditInfoService.getByKey(executionItem.getId());
            if(null == auditInfoList || auditInfoList.isEmpty()){
                //开启流程
                String taskTile = createBy.getName() +"("+execution.getEntrustCode()+")";
                Map<String,Object> model = new HashMap<>(2);
                model.put("userName",createBy.getName());
                model.put("code",execution.getEntrustCode());
                String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_EXECUTION,model);

                Map<String,Object> vars = new HashMap<>(2);
                vars.put("message", title);
                vars.put("code", execution.getEntrustCode());
                actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.NO_STANDARD_EXECUTION,
                        u.getLoginName(),executionItem.getId(),taskTile,vars);
            }else{
                //说明该流程已开启
                Map<String,Object> model = new HashMap<>(2);
                model.put("code",execution.getEntrustCode());
                model.put("userName",UserUtils.get(execution.getCreateBy().getId()).getName());
                String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_EXECUTION,model);
                //审批
                Map<String,Object> vars = new HashMap<>(2);
                vars.put("message", title);
                actTaskService.apiComplete(executionItem.getId(),"", "", u.getLoginName(), vars);
            }
        }
        return super.dao.changeStatus(executionItem);
    }

    @Transactional(readOnly = false)
    public String audit(NoStandardExecutionItem executionItem){
        AuditInfo info = executionItem.getAuditInfo();
        info.setAuditUser(UserUtils.getUser());
        auditInfoService.save(info);
        this.changeStatus(executionItem);
        if(NoStandardExecutionItemEnum.AUDIT_PASS.getCode().equals(executionItem.getStatus())){
            //审批通过，流程结束
            NoStandardExecution execution = executionService.get(executionItem.getExecutionId());
            List<NoStandardExecutionItem> testItemList = execution.getTestItemList();
            boolean flag = true;
            for (NoStandardExecutionItem testItem : testItemList) {
                if(!NoStandardExecutionItemEnum.AUDIT_PASS.getCode().equals(testItem.getStatus())){
                    flag = false;
                    break;
                }
            }
            actTaskService.apiComplete(executionItem.getId(), info.getReason(), info.getResult()+"", null, null);
            if(flag){
                //如果所有试验任务均已完成，则自动完成本次试验执行单，进入编制报告阶段
                execution.setStatus(NoStandardExecutionEnum.COMPLETE.getCode());
                executionService.completeTask(execution);
                return "complete";
            }
        }else if(NoStandardExecutionItemEnum.AUDIT_FAILED.getCode().equals(executionItem.getStatus())){
            NoStandardExecution execution = executionService.getBase(executionItem.getExecutionId());
            //审批不通过
            String[] opUserIds = execution.getOpUser().split(",");
            StringBuilder opUserLoginName = new StringBuilder();
            for (String opUserId : opUserIds) {
                User u = UserUtils.get(opUserId);
                opUserLoginName.append(u.getLoginName()).append(";");
            }
            opUserLoginName.append(UserUtils.get(execution.getTestManager().getUserIds()).getLoginName()).append(";");
            Map<String,Object> model = new HashMap<>(2);
            model.put("code",execution.getEntrustCode());
            model.put("userName",execution.getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_PASS,model);
            //审批
            Map<String,Object> vars = new HashMap<>(2);
            vars.put("message", title);
            actTaskService.apiComplete(executionItem.getId(),info.getReason(),
                    info.getResult()+"",
                    opUserLoginName.toString(),
                    vars);
        }
        return null;
    }
}
