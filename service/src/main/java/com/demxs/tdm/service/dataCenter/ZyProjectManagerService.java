package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.ZyProjectManagerDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.DataConstant;
import com.demxs.tdm.domain.dataCenter.ZyProjectManager;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyProjectManagerService extends  CrudService<ZyProjectManagerDao, ZyProjectManager> {

    @Autowired
    ZyProjectManagerDao zyProjectManagerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuditInfoDao auditInfoDao;

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AuditInfoService auditInfoService;

    @Autowired
    private TaskService taskService;



    public Page<ZyProjectManager> list(Page<ZyProjectManager> page, ZyProjectManager entity) {
        Page<ZyProjectManager> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyProjectManager> findPage(Page<ZyProjectManager> page, ZyProjectManager entity) {
        entity.setPage(page);
        page.setList(zyProjectManagerDao.findList(entity));
        return page;
    }

    public Page<ZyProjectManager> findAllPage(Page<ZyProjectManager> page, ZyProjectManager entity) {
        entity.setPage(page);
        page.setList(zyProjectManagerDao.getManager(entity));
        return page;
    }


    public ZyProjectManager get(String id) {
        ZyProjectManager equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyProjectManager entity) {
        super.save(entity);

    }

    /**
     * @author Jason
     * @date 2020/9/27 9:48
     * @params [request]
     * 驳回始终返回至创建者
     * @return void
     */
    public void reject(ZyProjectManager request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        ZyProjectManager assreq = this.dao.get(request.getId());
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",UserUtils.getUser().getName());
        String title = FreeMarkers.renderString(DataConstant.dataProjectMessage.AUDIT_RETURN,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                UserUtils.getUser().getLoginName(),vars);
        request.preUpdate();
        String  status=DataConstant.dataConstantStatus.REJECT;
        this.dao.changeStatus(request.getId(),status);
        request.setStatus(status);
        request.setAuditUser(UserUtils.getUser());
        if(null ==request.getAuditUser()){
            request.setApprovalId(assreq.getCreateBy().getId());
        }else{
            request.setApprovalId(assreq.getCreateBy().getId());
        }

        this.dao.changeAuditUser(request);
    }

    public void centerApproval(ZyProjectManager zyProjectManager) {
        AuditInfo auditInfo = zyProjectManager.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        actTaskService.apiComplete(zyProjectManager.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,null);
        zyProjectManager.preUpdate();
        zyProjectManager.setStatus(DataConstant.dataProjectStatus.FINISH);

        this.dao.changeStatus(zyProjectManager.getId(),zyProjectManager.getStatus());
        this.dao.changeAuditUser(zyProjectManager);

    }


    public  void  aduit(ZyProjectManager zyProjectManager){

            Task task = taskService.createTaskQuery().processInstanceBusinessKey(zyProjectManager.getId()).active().singleResult();
            User createBy = UserUtils.getUser();
            User auditUser = userDao.get(zyProjectManager.getApprovalId());
            if(null == task){
                User  ss=userDao.get(zyProjectManager.getUpdateBy());
                Map<String, Object> map = new HashMap<String, Object>();
                List<String> auditList = new ArrayList<>();

                if(null !=auditUser){
                    Map<String,Object> model = new HashMap<>(1);
                    model.put("userName",createBy.getName());
                    String title = FreeMarkers.renderString(DataConstant.dataProjectMessage.DEAL_WITH,model);
                    Map<String,Object> vars = new HashMap<>(1);
                    vars.put("message", title);
                    actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.PROJECT_MANAGER,
                            auditUser.getLoginName(), zyProjectManager.getId(), title, vars);
                }
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setBusinessKey(zyProjectManager.getId());
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setResult(1);
                // auditInfo.setReason("提交申请单");
                auditInfo.setType(Integer.parseInt(DataConstant.dataConstantStatus.APPLY));
                auditInfoService.save(auditInfo);
                String  status="2";
                this.dao.changeStatus(zyProjectManager.getId(),status);
            }else{
                //审批
                Map<String,Object> model = new HashMap<>(1);
                model.put("userName",createBy);
                String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);

                Map<String,Object> vars = new HashMap<>(1);
                vars.put("message", title);
                actTaskService.apiComplete(zyProjectManager.getId(),"","", auditUser.getLoginName(),vars);
            }
    }

}
