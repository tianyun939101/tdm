package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.TestCategoryAssessmentDao;
import com.demxs.tdm.dao.ability.TestCategoryDao;
import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.dao.resource.knowledge.BestPracticesDao;
import com.demxs.tdm.dao.resource.knowledge.TechnologyBwtDao;
import com.demxs.tdm.domain.ability.TestCategoryAssessment;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessRequestEnum;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.resource.kowledge.*;
import com.demxs.tdm.service.ability.LabInfoStateService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BestPracticesService extends CrudService<BestPracticesDao, BestPractices> {
    @Resource
    private ActTaskService actTaskService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuditInfoDao auditInfoDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TestCategoryAssessmentDao assessmentDao;
    @Autowired
    private TestCategoryDao categoryDao;
    @Autowired
    private LabInfoStateService labInfoStateService;
    @Autowired
    private TechnologyBwtDao technologyBwtDao;
    @Autowired
    private KnowledgeMapService knowledgeMapService;
    @Autowired
    private KnowledgeConnectService knowledgeConnectService;

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public String  submit(BestPractices bestPractices,String auditFlag) {
                User createBy = UserUtils.getUser();
               // bestPractices.setAuditId("1");
                //审核人
        //技术图谱
        String technology = bestPractices.getTechnology();
        String beloneId = new String();
        if("audit".equals(auditFlag)){
            //是否存在
            BestPractices bestPractices1 = super.get(bestPractices.getId());
            KnowledgeConnect audit = knowledgeConnectService.getAudit(bestPractices.getId());
            beloneId = audit.getBeloneId();
            if(bestPractices1 == null){//不存在插入
                bestPractices.setStatus("待编");
                bestPractices.setCreateBy(UserUtils.getUser());
                bestPractices.setCreateDate(new Date());
                Date time = audit.getTime();
                bestPractices.setPlan(time);
                super.insert(bestPractices);
            }else{//存在更新
                Date time = audit.getTime();
                bestPractices1.setPlan(time);
                bestPractices1.setStatus("待编");
                super.save(bestPractices);
            }
            knowledgeConnectService.updataFlag(bestPractices.getId());
        }else{
            super.save(bestPractices);
        }
        String id = bestPractices.getId();
        //技术图谱存入中间表
        TechnologyBwt technologyBwt = new TechnologyBwt();
        technologyBwt.setKnowledgeBestId(id);
        if(StringUtils.isNotEmpty(technology)){
            List<TechnologyBwt> select = technologyBwtDao.select(technologyBwt);
            if(CollectionUtils.isNotEmpty(select)){
                technologyBwtDao.delete(technologyBwt);
            }
            String[] split =  technology.split(",");
            for(int i = 0;i < split.length; i++){
                technologyBwt.setId(UUID.randomUUID().toString());
                technologyBwt.setTechnologyId(split[i]);
                technologyBwtDao.insert(technologyBwt);
            }

        }
        KnowledgeMap knowledgeMap = bestPractices.getKnowledgeMap();
        if(knowledgeMap!=null){
            knowledgeMap.setUpdateDate(new Date());
            knowledgeMap.setPricticesId(id); //关联ID
            knowledgeMapService.save(knowledgeMap);
            String id1 = knowledgeMap.getId();
            bestPractices.setProviderId(id1);
            super.save(bestPractices);
        }
return beloneId;
                    /*Map<String, Object> map = new HashMap<String, Object>();
                    map.put("message", String.format(MessageConstants.dxalAmend.AMEND, bestPractices.getCreateByName(), bestPractices.getName()));
                    String taskTitle =auditUser.getName() + "," + MessageConstants.dxalAmend.AMEND;
                    actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.AMEND, auditUser.getLoginName(), bestPractices.getId(), taskTitle, map);*/
   }


   public void shenhe(BestPractices bestPractices){
       User auditUser = new User();
       if(StringUtils.isNotBlank(bestPractices.getAuditId())){
           auditUser = userDao.get(bestPractices.getAuditId());
       }else{
           auditUser = userDao.get("1");
       }
       Map<String, Object> map = new HashMap<String, Object>();
       map.put("message", String.format(MessageConstants.dxalAmend.AMEND, bestPractices.getCreateByName(), bestPractices.getName()));
       String taskTitle =auditUser.getName() + "," + MessageConstants.dxalAmend.AMEND;
       actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.AMEND, auditUser.getLoginName(), bestPractices.getId(), taskTitle, map);
   }




    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void audit(BestPractices bestPractices) {
        List<KnowledgeMap> knowledgeMapList = bestPractices.getKnowledgeMapList();
        String technology = bestPractices.getTechnology();
        super.save(bestPractices);
        String id = bestPractices.getId();
        //技术图谱存入中间表
        TechnologyBwt technologyBwt = new TechnologyBwt();
        technologyBwt.setKnowledgeBestId(id);
        if(StringUtils.isNotEmpty(technology)){
            List<TechnologyBwt> select = technologyBwtDao.select(technologyBwt);
            if(CollectionUtils.isNotEmpty(select)){
                technologyBwtDao.delete(technologyBwt);
            }
            String[] split =  technology.split(",");
            for(int i = 0;i < split.length; i++){
                technologyBwt.setId(UUID.randomUUID().toString());
                technologyBwt.setTechnologyId(split[i]);
                technologyBwtDao.insert(technologyBwt);
            }

        }
        KnowledgeMap knowledgeMap = bestPractices.getKnowledgeMap();
        if(knowledgeMap!=null){
            knowledgeMap.setUpdateDate(new Date());
            knowledgeMap.setPricticesId(id); //关联ID
            knowledgeMapService.save(knowledgeMap);
            String id1 = knowledgeMap.getId();
            bestPractices.setProviderId(id1);
            super.save(bestPractices);
        }

    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void saveImport(BestPractices bestPractices) {
        super.save(bestPractices);
    }


  /*  *//**
     * 提交，下一节点审批人审批
     * @return void
     *//*
    public void submit(BestPractices request) {
        //获取审批人
        User auditUser = UserUtils.get(request.getLabMin());
        User createBy = UserUtils.getUser();
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(request.getId()).active().singleResult();
        //activity查询不到，代表此申请未提交过
        if(null == task){
            //启动流程
            Map<String,Object> model = new HashMap<>(1);
            model.put("userName",createBy.getName());
            String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.GENERIC,model);
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiStartProcess(AbilityConstants.TestCategoryAssessmentAuditProcess.PROCESS,
                    auditUser.getLoginName(), request.getId(), title, vars);
        }else{
            //审批
            Map<String,Object> model = new HashMap<>(1);
            model.put("userName",createBy);
            String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.GENERIC,model);

            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiComplete(request.getId(),"","", auditUser.getLoginName(),vars);
        }

        request.preUpdate();
        request.setInitiator(UserUtils.getUser().getId());
        request.setStatus(TestCategoryAssessRequestEnum.LAB_AUDIT.getCode());
        request.setAuditUserId(request.getLabMin());
        this.dao.update(request);
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.LAB_AUDIT.getCode()));
        //this.dao.changeAuditUser(request);
    }

    *//**
     * 审批人进行审批
     * @return void
     *//*
    public void caseAudit(BestPractices request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.CENTER_APPROVAL.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,null);
        request.preUpdate();
        //修改状态
        request.setStatus(BestPracticesEnum.APPROVED.getCode());
        this.dao.changeStatus(request);
        this.dao.changeAuditUser(request);
        //生效该申请单全部评估
        assessmentDao.apply(new TestCategoryAssessment().setrId(request.getId()).setStatus(TestCategoryAssessment.APPLIED));
        //更新试验室整体情况描述
        request = this.get(request.getId());
        labInfoStateService.updateLabinfoDescribe(request.getvId(),request.getLabId());
    }
*/
}
