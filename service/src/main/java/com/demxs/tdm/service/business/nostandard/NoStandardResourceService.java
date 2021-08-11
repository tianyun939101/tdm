package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustInfoDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustResourceDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.*;
import com.demxs.tdm.domain.business.nostandard.*;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class NoStandardResourceService extends CrudService<NoStandardEntrustResourceDao, NoStandardEntrustResource> {


    @Autowired
    private NoStandardOtherUserService otherUserService;
    @Autowired
    private NoStandardBeforeTestService beforeTestService;
    @Autowired
    private NoStandardEntrustInfoDao infoDao;
    @Autowired
    private NoStandardExecutionService executionService;
    @Autowired
    private NoStandardExecutionItemService itemService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private SystemService systemService;

    @Transactional(readOnly = false)
    public void saveResource(NoStandardEntrustResource resource){
        if(StringUtils.isBlank(resource.getId())){
            return;
        }
        super.save(resource);
        String resourceId = resource.getId();

        if("1".equals(resource.getSubmit())){

            //试验前检查状态
            otherUserService.deleteByResourceId(resourceId);
            List<NoStandardOtherUser> otherUserList = resource.getOtherUsers();
            if(null != otherUserList){
                for(int i = 0;i<otherUserList.size();i++){
                    NoStandardOtherUser otherUser = otherUserList.get(i);
                    otherUser.setResourceId(resourceId);
                    otherUserService.save(otherUser);
                }
            }
            //试验前检查信息
            NoStandardBeforeTest beforeTest = new NoStandardBeforeTest(resource.getBeforeId());
            beforeTest.setEntrustId(resource.getEntrustId());
            beforeTest.setTestOutlineId(resource.getTestoutlineId());
            beforeTest.setTvId(resource.getTvId());
            beforeTest.setConfigurationId(resource.getConfigurationId());
            beforeTest.setCvId(resource.getCvId());
            beforeTest.setTestManagerId(resource.getTestManagerId());
            beforeTest.setResourceId(resourceId);
            beforeTest.setData(resource.getBeforeTest().getData());
            beforeTest.setTestOutlineType(resource.getTestOutlineType());
            beforeTest.setTestOutlineCode(resource.getTestOutlineCode());
            beforeTest.setTestOutlineFile(resource.getTestOutlineFile());

            beforeTestService.save(beforeTest);

            List<NoStandardOtherUser> list = resource.getOtherUsers();
            StringBuilder sb = new StringBuilder();
            for(NoStandardOtherUser u : list){
                sb.append(u.getName()).append(",");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length()-1);
            }

            resource = this.get(resource.getId());
            User u = systemService.getUser(resource.getTestManagerId());
            String createBy = resource.getEntrustInfo().getLabManager().getName();

            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(resource.getEntrustId());
            entrustInfo.setTestManager(resource.getTestManagerId());
            entrustInfo.setTestUsers(sb.toString());
            //流程
            Map<String,Object> model = new HashMap<>();
            String title;
            Map<String,Object> vars = new HashMap<>();
            if(NoStandardResourceEnum.INSPECT.getCode().equals(resource.getStatus())){
                //流程流转至试验前检查
                model.put("code",resource.getEntrustCode());
                model.put("userName",createBy);
                title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_RESOURCE,model);
                vars.put("message", title);
                actTaskService.apiComplete(resource.getEntrustId(),"","",
                        u.getLoginName(),vars);

                entrustInfo.setStatus(NoStandardEntrustInfoEnum.INSPECT.getCode());
                entrustInfo.setCurAuditUser(u.getId());
            }else if(NoStandardResourceEnum.AUDIT_PASS.getCode().equals(resource.getStatus())){
                //流程流转至 试验前检查审核
                model.put("code",resource.getEntrustCode());
                model.put("userName",resource.getEntrustInfo().getCreateBy().getName());
                title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Audit,model);

                User leader = resource.getEntrustInfo().getLabInfo().getLeader();
                String assignee = leader.getLoginName();
                vars.put("message", title);
                actTaskService.apiComplete(resource.getEntrustId(),"",
                        "",assignee,vars);
                super.dao.updateActive(resource.setCurAuditUser(leader.getId()));
                //修改申请单信息
                NoStandardEntrustInfo ei = new NoStandardEntrustInfo(resource.getEntrustId());

                entrustInfo.setStatus(NoStandardEntrustInfoEnum.EXECUTION.getCode());
                entrustInfo.setCurAuditUser(leader.getId());
            }
            infoDao.updateActive(entrustInfo);
        }else{
            //保存草稿
            otherUserService.deleteByResourceId(resourceId);
            List<NoStandardOtherUser> otherUserList = resource.getOtherUsers();
            if(null != otherUserList){
                for(int i = 0;i<otherUserList.size();i++){
                    NoStandardOtherUser otherUser = otherUserList.get(i);
                    otherUser.setResourceId(resourceId);
                    otherUserService.save(otherUser);
                }
            }
            //试验前检查信息
            NoStandardBeforeTest beforeTest = new NoStandardBeforeTest(resource.getBeforeId());
            beforeTest.setEntrustId(resource.getEntrustId());
            beforeTest.setTestOutlineId(resource.getTestoutlineId());
            beforeTest.setTvId(resource.getTvId());
            beforeTest.setConfigurationId(resource.getConfigurationId());
            beforeTest.setCvId(resource.getCvId());
            beforeTest.setTestManagerId(resource.getTestManagerId());
            beforeTest.setResourceId(resourceId);
            beforeTest.setData(resource.getBeforeTest().getData());
            beforeTest.setTestOutlineType(resource.getTestOutlineType());
            beforeTest.setTestOutlineCode(resource.getTestOutlineCode());
            beforeTest.setTestOutlineFile(resource.getTestOutlineFile());
            beforeTestService.save(beforeTest);
        }

    }

    @Transactional(readOnly = false)
    public void audit(NoStandardEntrustResource resource){
        AuditInfo auditInfo = resource.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfoService.save(auditInfo);
        this.changeStatus(resource);
        resource = this.get(resource.getId());
        if(NoStandardResourceEnum.AUDIT_PASS.getCode().equals(resource.getStatus())){

            //审核通过
            Map<String,Object> model = new HashMap<>();
            model.put("code",resource.getEntrustCode());
            model.put("userName",resource.getEntrustInfo().getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Audit,model);
            //审批
            User leader = resource.getEntrustInfo().getLabInfo().getLeader();
            String assignee = leader.getLoginName();
            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(resource.getEntrustId(),auditInfo.getReason(),
                    auditInfo.getResult()+"",
                    assignee,
                    vars);

            super.dao.updateActive(resource.setCurAuditUser(leader.getId()));

            //修改申请单信息
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(resource.getEntrustId());
            entrustInfo.setCurAuditUser(leader.getId());
            infoDao.updateActive(entrustInfo);
        }else if(NoStandardResourceEnum.AUDIT_FAILED.getCode().equals(resource.getStatus())){
            //审核驳回

            User updateBy = systemService.getUser(resource.getUpdateBy().getId());
            String assignee = updateBy.getLoginName();
            Map<String,Object> model = new HashMap<>();
            model.put("code",resource.getEntrustCode());
            model.put("userName",resource.getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_PASS,model);
            //审批
            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(resource.getEntrustId(),auditInfo.getReason(),
                    auditInfo.getResult()+"",
                    assignee,
                    vars);

            super.dao.updateActive(resource.setCurAuditUser(updateBy.getId()));

            //修改申请单信息
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(resource.getEntrustId());
            entrustInfo.setStatus(NoStandardEntrustInfoEnum.RESOURCE.getCode());
            entrustInfo.setCurAuditUser(updateBy.getId());
            infoDao.updateActive(entrustInfo);
        }
    }

    @Transactional(readOnly = false)
    public void approval (NoStandardEntrustResource resource){
        AuditInfo auditInfo = resource.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfoService.save(auditInfo);
        this.changeStatus(resource);

        resource = this.get(resource.getId());
        if(NoStandardResourceEnum.APPROVAL.getCode().equals(resource.getStatus())){
            //审批通过
            NoStandardEntrustInfo info = infoDao.get(resource.getEntrustId());

            NoStandardExecution execution = new NoStandardExecution();
            execution.setResourceId(resource.getId());
            execution.setEntrustId(resource.getEntrustId());
            execution.setEntrustCode(resource.getEntrustCode());
            execution.setTaskNo(info.getTaskNo());
            execution.setTaskName(info.getTaskName());
            execution.setConfigId(resource.getConfigurationId());
            execution.setConfigVersion(resource.getcVersion());
            execution.setTestOutlineId(resource.getTestoutlineId());
            execution.setTestOutlineVersion(resource.gettVersion());
            execution.setCvId(resource.getCvId());
            execution.setTvId(resource.getTvId());
            execution.setTestOutlineType(resource.getTestOutlineType());
            execution.setTestOutlineFile(resource.getTestOutlineFile());
            execution.setTestOutlineFileCode(resource.getTestOutlineFileCode());
            execution.setTestOutlineFileVersion(resource.getTestOutlineFileVersion());
            execution.setTestOutlineFileName(resource.getTestOutlineFileName());
            execution.setTestOutlineCode(resource.getTestOutlineCode());
            execution.setTestOutlineCodeVersion(resource.getTestOutlineCodeVersion());
            execution.setTestOutlineCodeName(resource.getTestOutlineCodeName());
            execution.setTestOutlineSysCode(resource.getTestOutlineSysCode());

            execution.setStatus(NoStandardExecutionEnum.WAIT.getCode());
            execution.setTestManager(new Yuangong(resource.getTestManagerId()));

            StringBuilder othersId = new StringBuilder();
            StringBuilder othersLoginName = new StringBuilder();

            //设值执行任务的成员
            List<NoStandardOtherUser> otherUsers = resource.getOtherUsers();
            if(null != otherUsers){
                for(int i=0;i<otherUsers.size();i++){
                    NoStandardOtherUser otherUser = otherUsers.get(i);
                    othersId.append(otherUser.getUserId()).append(",");
                    othersLoginName.append(otherUser.getUser().getUser().getLoginName()).append(";");
                }
            }
            execution.setOpUser(othersId.toString());
            executionService.save(execution);

            List<NoStandardTestItem> testItemList = info.getTestItemList();
            if(null != testItemList){
                StringBuilder others = new StringBuilder();
                if(null != otherUsers){
                    for(int i=0;i<otherUsers.size();i++){
                        NoStandardOtherUser otherUser = otherUsers.get(i);
                        others.append(otherUser.getName()).append(",");
                    }
                }
                if(others.length() > 0){
                    others.deleteCharAt(others.length()-1);
                }
                itemService.deleteByExecutionId(execution.getId());

                for(NoStandardTestItem testItem : testItemList){
                    NoStandardExecutionItem executionItem = new NoStandardExecutionItem();
                    executionItem.setTestItemId(testItem.getItemId());
                    executionItem.setEntrustId(execution.getEntrustId());
                    executionItem.setExecutionId(execution.getId());
                    executionItem.setSort(testItem.getSort());
                    executionItem.setOpUser(others.toString());
                    executionItem.setStatus(NoStandardExecutionItemEnum.UN_AUDITED.getCode());

                    itemService.save(executionItem);
                }
            }



            Map<String,Object> model = new HashMap<>();
            model.put("code",resource.getEntrustCode());
            model.put("userName",resource.getEntrustInfo().getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.EXECUTION,model);
            //审批
            String assignee = resource.getTestManager().getUser().getLoginName();
            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            othersLoginName.append(assignee).append(";");
            actTaskService.apiComplete(resource.getEntrustId(),auditInfo.getReason(),
                    auditInfo.getResult()+"",
                    othersLoginName.toString(),
                    vars);

            //修改申请单信息
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(execution.getEntrustId());
            entrustInfo.setStatus(NoStandardEntrustInfoEnum.EXECUTION.getCode());
            entrustInfo.setCurAuditUser(resource.getTestManager().getUser().getId());
            infoDao.updateActive(entrustInfo);

        }else if(NoStandardResourceEnum.AUDIT_FAILED.getCode().equals(resource.getStatus())){
            //审批不通过

            User createBy = resource.getEntrustInfo().getCreateBy();
            String assignee = createBy.getLoginName();
            Map<String,Object> model = new HashMap<>();
            model.put("code",resource.getEntrustCode());
            model.put("userName",resource.getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_PASS,model);
            //审批
            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(resource.getEntrustId(),auditInfo.getReason(),
                    auditInfo.getResult()+"",
                    assignee,
                    vars);

            super.dao.updateActive(resource.setCurAuditUser(createBy.getId()));

            //修改申请单信息
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(resource.getEntrustId());
            entrustInfo.setStatus(NoStandardEntrustInfoEnum.RESOURCE.getCode());
            entrustInfo.setCurAuditUser(createBy.getId());
            infoDao.updateActive(entrustInfo);
        }
    }


    @Transactional(readOnly = false)
    public int changeStatus(NoStandardEntrustResource resource){
        return super.dao.changeStatus(resource);
    }

    @Transactional(readOnly = false)
    public NoStandardEntrustResource getByEntrustId(String id){
        return super.dao.getByEntrustId(id);
    }

    public NoStandardEntrustResource getOtherUsers(String id) {
        return this.dao.getOtherUsers(id);
    }
}
