package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.dto.BaseInfoApplyDTO;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.BaseInfoApplyDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.ApproveStatus;
import com.demxs.tdm.domain.business.constant.DataTaskEnum;
import com.demxs.tdm.domain.business.constant.NoStandardTestCheckEnum;
import com.demxs.tdm.domain.dataCenter.BaseInfoApply;
import com.demxs.tdm.domain.dataCenter.DataBaseInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class BaseInfoApplyService extends CrudService<BaseInfoApplyDao, BaseInfoApply> {

    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private DataBaseInfoService dataBaseInfoService;
    @Autowired
    private SystemService systemService;
    @Resource
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;

    /**
     * @Describe:获取明细数据
     * @Author:WuHui
     * @Date:10:58 2020/9/27
     * @param baseInfoApply
     * @return:com.demxs.tdm.domain.dataCenter.BaseInfoApply
    */
    public BaseInfoApply detail(BaseInfoApply baseInfoApply){
        if(!StringUtils.isBlank(baseInfoApply.getId())){
            baseInfoApply = this.get(baseInfoApply.getId());
        }else{
            baseInfoApply.setApplicant(UserUtils.getUser().getId());
            baseInfoApply.setLabId(UserUtils.getUser().getLabInfoId());

            baseInfoApply.setApplyDate(new Date());
        }
        DataBaseInfo dataBaseInfo = dataBaseInfoService.get(baseInfoApply.getBaseId());
        LabInfo labInfo = labInfoService.get(baseInfoApply.getLabId());
        User user = systemService.getUser(baseInfoApply.getApplicant());
        baseInfoApply.setApplyUser(user);
        baseInfoApply.setLabInfo(labInfo);
        if(dataBaseInfo != null){
            baseInfoApply.setBaseContent(dataBaseInfo.getTestName());
        }
        return  baseInfoApply;
    }

    /**
     * @Describe:提交审批
     * @Author:WuHui
     * @Date:10:58 2020/9/27
     * @param baseInfoApply
     * @return:void
    */
    public void submit(BaseInfoApply baseInfoApply){
        baseInfoApply.setApproveStatus(ApproveStatus.APPROVAL.getCode());
        User user = UserUtils.getUser();
        baseInfoApply.setLabId(user.getLabInfoId());
        this.save(baseInfoApply);
        //流程审批
        String title = "\"" +baseInfoApply.getBaseContent()+"\"数据下载申请";
        Map<String,Object> vars = new HashMap<>();
        vars.put("message", title);
        actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.TEST_DATA_DOWNLOAD,"admin",baseInfoApply.getId(),title,vars);
    }

    /**
     * @Describe:提交审批
     * @Author:WuHui
     * @Date:10:58 2020/9/27
     * @param baseInfoApply
     * @return:void
     */
    public void submitInfo(BaseInfoApply baseInfoApply){
        baseInfoApply.setApproveStatus(DataTaskEnum.MANAGER_AUDIT.getCode());
        this.save(baseInfoApply);
        User user = UserUtils.getUser();
        baseInfoApply.setLabId(user.getLabInfoId());
        List<User> list=new ArrayList<>();
        List<String> userLoginNames = new ArrayList<>();
        list=systemService.getUserByRoleName("数据中心");
        StringBuilder userNameSb = new StringBuilder();
        StringBuilder userIdSb = new StringBuilder();
        for(User u:list){
            userIdSb.append(u.getId()).append(",");
            userNameSb.append(u.getName()).append(",");
            userLoginNames.add(u.getLoginName());
        }
        AuditInfo auditInfo = new AuditInfo(baseInfoApply.getId(),UserUtils.getUser(),1,"发起审批");
        auditInfo.setBusinessKey(baseInfoApply.getId());
        auditInfoService.save(auditInfo);
        String title = "请处理'"+UserUtils.getUser().getName()+"'提交的数据下载申请！";
        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiStartProcess("data_downLoad",StringUtils.join(userLoginNames,";"), baseInfoApply.getId(), title, vars);

    }

    /**
     * @Describe:审批
     * @Author:WuHui
     * @Date:10:58 2020/9/27
     * @param applyDTO
     * @return:void
    */
    public void approve(BaseInfoApplyDTO applyDTO){
        BaseInfoApply baseInfoApply = this.get(applyDTO.getId());
        //修改权限
        if(applyDTO.isAgree()){
            User user = systemService.getUser(baseInfoApply.getApplicant());
            dataBaseInfoService.addOperateData(user,baseInfoApply.getBaseId());
        }
        ApproveStatus approveStatus = applyDTO.isAgree() ? ApproveStatus.AGREE : ApproveStatus.REJEDTED;
        baseInfoApply.setUpdateBy(UserUtils.getUser());
        baseInfoApply.setApproveStatus(approveStatus.getCode());
        this.save(baseInfoApply);
        //审批
        String result = applyDTO.isAgree() ? Global.YES : Global.NO;
        actTaskService.apiComplete(baseInfoApply.getId(),applyDTO.getOpinion(),  result,"",null);
    }


    /**
     * @Describe:审批
     * @Author:WuHui
     * @Date:10:58 2020/9/27
     * @param applyDTO
     * @return:void
     */
    public void approveInfo(BaseInfoApplyDTO applyDTO){
        BaseInfoApply baseInfoApply = this.get(applyDTO.getId());
        //修改权限
        if(applyDTO.isAgree()){
            User user = systemService.getUser(baseInfoApply.getApplicant());
            String title = "请处理'"+user.getName()+"'提交的数据下载申请！";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiComplete(applyDTO.getId(),applyDTO.getOpinion(), Global.YES, null,vars);
            //审批履历
            AuditInfo auditInfo = new AuditInfo(applyDTO.getId(),UserUtils.getUser(),1,applyDTO.getOpinion());
            auditInfo.setBusinessKey(baseInfoApply.getId());
            auditInfoService.save(auditInfo);
            baseInfoApply.setApproveStatus(DataTaskEnum.AUDIT.getCode());
            this.save(baseInfoApply);
        }else{
           //驳回提交者
            User auditUser = UserUtils.get(baseInfoApply.getApplicant());
            Map<String,Object> vars = new HashMap<>(1);
            //审批
            String title = "您提交的数据下载申请，已被'"+UserUtils.getUser().getName()+"'驳回！";
            vars.put("message", title);
            String assignee = auditUser== null ? "" : auditUser.getLoginName();
            actTaskService.apiComplete(baseInfoApply.getId(),applyDTO.getOpinion(), Global.NO, assignee,vars);
            //审批履历
            AuditInfo auditInfo = new AuditInfo(applyDTO.getId(),auditUser,0,applyDTO.getOpinion());
            auditInfo.setBusinessKey(baseInfoApply.getId());
            baseInfoApply.setApproveStatus(DataTaskEnum.CHECK.getCode());
            auditInfoService.save(auditInfo);
            this.save(baseInfoApply);
        }

    }

     public  int getDownLoadAhory(BaseInfoApply  baseInfoApply){
        return this.dao.getDownLoadAhory(baseInfoApply);
    }

     public  void updateDelete(BaseInfoApply  baseInfoApply){
          this.dao.updateDelete(baseInfoApply);
     }

}
