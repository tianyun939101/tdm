package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.dataCenter.ZyTestReportDao;
import com.demxs.tdm.dao.dataCenter.ZyTestTaskDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.TestTaskEnum;
import com.demxs.tdm.domain.dataCenter.ZyTestReport;
import com.demxs.tdm.domain.dataCenter.ZyTestTask;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyTestReportService extends  CrudService<ZyTestReportDao, ZyTestReport> {

    @Autowired
    ZyTestReportDao zyTestReportDao;

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;

    @Autowired
    private SystemService systemService;


    public Page<ZyTestReport> list(Page<ZyTestReport> page, ZyTestReport entity) {
        Page<ZyTestReport> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyTestReport> findPage(Page<ZyTestReport> page, ZyTestReport entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyTestReport get(String id) {
        ZyTestReport equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyTestReport entity) {
        super.save(entity);
        if("1".equals(entity.getFlag())){
            User auditUser = UserUtils.getUser();
            String title = "请处理'"+UserUtils.getUser().getName()+"'提交的软件构型报告申请！";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiStartProcess("task_Report", auditUser.getLoginName(), entity.getId(), title, vars);
            entity.setStatus(TestTaskEnum.MANAGER_AUDIT.getCode());
            //审批履历
            AuditInfo auditInfo = new AuditInfo(entity.getId(),UserUtils.getUser(),1,"发起审批");
            auditInfoService.save(auditInfo);
            super.save(entity);
        }
    }


    /**
     * @Describe:审批
     * @Author:zwm
     * @Date:10:01 2021/01/29
     * @param approveDto
     * @return:void
     */
    public void approve(ApproveDTO approveDto) {
        //获取数据
        ZyTestReport zyTestTask = this.get(approveDto.getId());
        String status=zyTestTask.getStatus();
        zyTestTask.setId(approveDto.getId());
        User auditUser=null;
        Map<String,Object> vars = new HashMap<>(1);
        List<String> userLoginNames = new ArrayList<>();
        //获取下一节点审批人
        if("04".equals(status)){
            auditUser=systemService.getUser(zyTestTask.getCreateBy().getId());
        }else{
            auditUser = UserUtils.get(approveDto.getNextUser());
        }
        //审批
        String title = "请处理'"+zyTestTask.getCreateBy().getName()+"'提交的软件构型报告检查申请！";
        vars.put("message", title);
        vars.put("reportId",zyTestTask.getId());
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(zyTestTask.getId(),approveDto.getOpinion(), Global.YES, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
        if(auditUser!=null)
            zyTestTask.setApprovalUser(auditUser.getId());
        switch(TestTaskEnum.get(zyTestTask.getStatus())){
            case CHECK:
                status = TestTaskEnum.MANAGER_AUDIT.getCode();
                break;
            case MANAGER_AUDIT:
                status = TestTaskEnum.REPORT.getCode();
                break;
            case REPORT:
                status = TestTaskEnum.AUDIT.getCode();
                break;
            default:
                break;
        }
        zyTestTask.setStatus(status);
        this.save(zyTestTask);
    }
    /**
     * @Describe:驳回
     * @Author:zwm
     * @Date:10:38 2021/01/29
     * @param approveDto
     * @return:void
     */
    public void reject(ApproveDTO approveDto) {
        //获取数据
        ZyTestReport zyTestTask = this.get(approveDto.getId());
        zyTestTask.setId(approveDto.getId());
        //驳回提交者
        User auditUser = UserUtils.get(zyTestTask.getCreateBy().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "您提交的软件构型报告检查申请，已被'"+UserUtils.getUser().getName()+"'驳回！";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(zyTestTask.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
        zyTestTask.setApprovalUser(UserUtils.getUser().getId());
        zyTestTask.setStatus("06");
        this.save(zyTestTask);
    }

}
