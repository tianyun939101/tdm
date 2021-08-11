package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.business.configuration.ConfigurationTechnologyStateDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.configuration.ConfigurationChangeApply;
import com.demxs.tdm.domain.business.configuration.ConfigurationChangeApplyEnum;
import com.demxs.tdm.domain.business.configuration.ConfigurationTechnologyState;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ConfigurationTechnologyStateService extends CrudService<ConfigurationTechnologyStateDao, ConfigurationTechnologyState> {
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;
    /**
     * 提交，编制审批（保存）
     * @param state
     */
    public void submit(ConfigurationTechnologyState state, String flag) {
        state.setStatus(ConfigurationChangeApplyEnum.APPLY.getCode());
        //申请人
        state.setProofreadUser(UserUtils.getUser());
        this.save(state);
        if("submit".equalsIgnoreCase(flag)){
            User auditUser = UserUtils.get(state.getAuthorizedMinUser().getId());
            String title = "请处理'"+UserUtils.getUser().getName()+"'提交的软件技术状态申请！";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            //启动流程
            actTaskService.apiStartProcess("technologyState", auditUser.getLoginName(), state.getId(), title, vars);
            //更新状态,提交进入编制状态
            state.setStatus(ConfigurationChangeApplyEnum.AUTHOR.getCode());
            state.setAuditUser(state.getAuthorizedMinUser());
        }
        this.save(state);
    }


    //审批
    public void approve(ApproveDTO approveDto) {
        //获取数据
        ConfigurationTechnologyState state = this.get(approveDto.getId());
        String status=state.getStatus();
        //下一节点审批人
        User auditUser = UserUtils.get(state.getAuthorizedMinUser().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "请处理'"+auditUser.getName()+"'提交的软件技术状态申请！";
        vars.put("message", title);
        actTaskService.apiComplete(state.getId(),approveDto.getOpinion(), Global.YES, auditUser.getLoginName(),vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
        state.setAuditUser(state.getAuthorizedMinUser());
        switch (status){
            case "01": status = ConfigurationChangeApplyEnum.PROO.getCode();
                break;
            case "02":status = ConfigurationChangeApplyEnum.RATIFY.getCode();
                break;
            case "03":status = ConfigurationChangeApplyEnum.EXIT.getCode();
                break;
        }
        state.setStatus(status);
        this.save(state);
    }

    //驳回
    public void reject(ApproveDTO approveDto) {
        //获取数据
        ConfigurationTechnologyState state = this.get(approveDto.getId());
        state.setId(approveDto.getId());
        //驳回提交者
        User auditUser = UserUtils.get(state.getProofreadUser().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "您提交的软件技术状态申请，已被'"+state.getAuthorizedMinUser().getName()+"'驳回！";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(state.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
        state.setAuditUser(state.getProofreadUser());
        state.setStatus(ConfigurationChangeApplyEnum.REJECT.getCode());
        this.save(state);
    }
}
