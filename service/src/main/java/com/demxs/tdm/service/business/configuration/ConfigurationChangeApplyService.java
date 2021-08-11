package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.business.configuration.ConfigurationChangeApplyDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.configuration.ConfigurationChangeApply;
import com.demxs.tdm.domain.business.configuration.ConfigurationChangeApplyEnum;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ConfigurationChangeApplyService extends CrudService<ConfigurationChangeApplyDao, ConfigurationChangeApply> {

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private TaskService taskService;
    /**
     * 提交，编制审批（保存）
     * @param apply
     */
    public void submit(ConfigurationChangeApply apply,String flag) {
        //申请人
        apply.setProofreadUser(UserUtils.getUser());
        this.save(apply);
        if("submit".equalsIgnoreCase(flag)){
                User auditUser = UserUtils.get(apply.getAuthorizedMinUser().getId());
                String title = "请处理'"+UserUtils.getUser().getName()+"'提交的软件构型版本更改申请！";
                Map<String,Object> vars = new HashMap<>(1);
                vars.put("message", title);
                //启动流程
                actTaskService.apiStartProcess("changeApply", auditUser.getLoginName(), apply.getId(), title, vars);
                //更新状态
                apply.setStatus(ConfigurationChangeApplyEnum.AUTHOR.getCode());
        }
        this.save(apply);
    }

    //审批
    public void approve(ApproveDTO approveDto) {
            //获取数据
            ConfigurationChangeApply apply = this.get(approveDto.getId());
            String status=apply.getStatus();
            apply.setId(approveDto.getId());
            //下一节点审批人
            User auditUser = UserUtils.get(apply.getAuthorizedMinUser().getId());
            Map<String,Object> vars = new HashMap<>(1);
            //审批
            String title = "请处理'"+auditUser.getName()+"'提交的软件构型版本更改申请！";
            vars.put("message", title);
            actTaskService.apiComplete(apply.getId(),approveDto.getOpinion(), Global.YES, auditUser.getLoginName(),vars);
            //审批履历
            AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
            auditInfoService.save(auditInfo);
            //更新状态及当前处理人
            //当前登录用户为当前处理人
            apply.setAuditUser(UserUtils.getUser());
            switch (status){
                case "01": status = "02";
                      break;
                case "02":status = "03";
                    break;
                case "03":status = "04";
                    break;
                case "05":status = "02";
                    break;

            }
        apply.setStatus(status);
        this.save(apply);
    }
    //驳回
    public void reject(ApproveDTO approveDto) {
        //获取数据
        ConfigurationChangeApply apply = this.get(approveDto.getId());
        apply.setId(approveDto.getId());
        //驳回提交者
        User auditUser = UserUtils.get(apply.getProofreadUser().getId());
        //驳回申请人
        User initiator = UserUtils.get(apply.getProofreadUser().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "您提交的软件构型版本更改申请，已被'"+apply.getAuthorizedMinUser().getName()+"'驳回！";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(apply.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
        apply.setAuditUser(apply.getProofreadUser());
        apply.setStatus(ConfigurationChangeApplyEnum.AUTHOR.getCode());
        this.save(apply);
    }
}
