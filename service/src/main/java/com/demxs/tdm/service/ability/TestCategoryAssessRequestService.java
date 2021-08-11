package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.WordUtils;
import com.demxs.tdm.common.utils.zrutils.WordUtil;
import com.demxs.tdm.common.web.OpResult;
import com.demxs.tdm.dao.ability.TestCategoryAssessRequestDao;
import com.demxs.tdm.dao.ability.TestCategoryAssessmentDao;
import com.demxs.tdm.dao.ability.TestCategoryDao;
import com.demxs.tdm.dao.ability.TestCategoryVersionDao;
import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.dao.lab.LabUserDao;
import com.demxs.tdm.domain.ability.*;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessRequestEnum;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabUser;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jason
 * @Date: 2020/9/23 10:41
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryAssessRequestService extends CrudService<TestCategoryAssessRequestDao, TestCategoryAssessRequest> {

    @Autowired
    private LabUserDao labUserDao;
    @Autowired
    private TestCategoryVersionDao versionDao;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private AuditInfoDao auditInfoDao;
    @Autowired
    private TestCategoryAssessmentDao assessmentDao;
    @Autowired
    private TestCategoryDao categoryDao;
    @Autowired
    private LabInfoStateService labInfoStateService;
    @Autowired
    private TestCategoryService testCategoryService;
    @Autowired
    private EquipmentTestService equipmentTestService;

    public int changeStatus(TestCategoryAssessRequest request){
        return this.dao.changeStatus(request);
    }

    public int changeAuditUser(TestCategoryAssessRequest request){
        return this.dao.changeAuditUser(request);
    }

    public int deleteByVersionId(TestCategoryAssessRequest request){
        return this.dao.deleteByVersionId(request);
    }

    public List<TestCategoryAssessRequest> findByLabId(TestCategoryAssessRequest request){
        return this.dao.findByLabId(request);
    }

    @Override
    public void delete(TestCategoryAssessRequest entity) {
        assessmentDao.deleteByRequestId(new TestCategoryAssessment().setrId(entity.getId()));
        //Task task = taskService.createTaskQuery().processInstanceBusinessKey(entity.getId()).active().singleResult();
        //runtimeService.deleteProcessInstance(task.getId(),"删除能力图谱评估申请");
        super.delete(entity);
    }

    public OpResult checkExistLabRequest(){
        User user = UserUtils.getUser();
        String vId = versionDao.getEnabledVersion().getId();
        List<LabUser> list = labUserDao.findByUserId(new LabUser().setUserId(user.getId()));
        if(null != list && !list.isEmpty()){
            if(list.get(0) == null || StringUtils.isEmpty(list.get(0).getLabId())){
                return OpResult.buildError("您未分配试验室！！");
            }
            //查询该试验室所有非已批准状态的申请单
            List<TestCategoryAssessRequest> labList = this.dao.findByLabId(new TestCategoryAssessRequest()
                    .setLabId(list.get(0).getLabId()).setFilterStatus(TestCategoryAssessRequestEnum.APPROVED.getCode()));
            if (null != labList && !labList.isEmpty()) {
                return OpResult.buildError("试验室已存在申请单，请勿重复创建");
            }
            TestCategoryAssessRequest request = new TestCategoryAssessRequest();
            request.setvId(vId).setLabId(list.get(0).getLabId()).setStatus(TestCategoryAssessRequestEnum.EDIT.getCode());
            super.save(request);
            return OpResult.buildSuccess().setData(request.getId());
        }else{
            return OpResult.buildError("您未分配试验室！！");
        }
    }

    /**
    * @author Jason
    * @date 2020/9/27 9:47
    * @params [request]
    * 提交，下一节点试验室主任审核
    * @return void
    */
    public void submit(TestCategoryAssessRequest request) {
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

    /**
    * @author Jason
    * @date 2020/9/27 9:47
    * @params [request]
    * 试验室审核通过，下一节点科技部审核
    * @return void
    */
    public void labPass(TestCategoryAssessRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.LAB_AUDIT.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);


        //User auditUser = UserUtils.get(request.getAuditUserId());
        TestCategoryAssessRequest assreq = this.dao.get(request.getId());
        User auditUser = UserUtils.get(assreq.getTecDir());
        User createBy = UserUtils.getUser();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.GENERIC,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                auditUser.getLoginName(),vars);
        request.preUpdate();
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.COMPANY_AUDIT.getCode())
        request.setAuditUserId(assreq.getTecDir());
        request.setStatus(TestCategoryAssessRequestEnum.COMPANY_AUDIT.getCode());
        this.dao.changeAuditUser(request);
    }

    /**
     * @author Jason
     * @date 2020/9/27 9:47
     * @params [request]
     * 科技部审核通过，下一节点科技部审批
     * @return void
     */
    public void companyAudit(TestCategoryAssessRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.COMPANY_AUDIT.getTitle());
        auditInfo.preInsert();
        //插入审核结果
        auditInfoDao.insert(auditInfo);

        //User auditUser = UserUtils.get(request.getAuditUserId());
        TestCategoryAssessRequest assreq = this.dao.get(request.getId());
        User auditUser = UserUtils.get(assreq.getTecMin());
        User createBy = UserUtils.getUser();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.GENERIC,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                auditUser.getLoginName(),vars);
        request.preUpdate();
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.COMPANY_APPROVAL.getCode()));
        request.setAuditUserId(assreq.getTecMin());
        request.setStatus(TestCategoryAssessRequestEnum.COMPANY_APPROVAL.getCode());
        this.dao.changeAuditUser(request);
    }

    /**
    * @author Jason
    * @date 2020/9/27 9:47
    * @params [request]
    * 科技部审批通过，下一节点验证中心审核
    * @return void
    */
    public void companyApproval(TestCategoryAssessRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.COMPANY_APPROVAL.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        User auditUser = UserUtils.get(request.getAuditUserId());
        User createBy = UserUtils.getUser();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.GENERIC,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,vars);
        request.preUpdate();
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.CENTER_AUDIT.getCode()));
        request.setStatus(TestCategoryAssessRequestEnum.CENTER_AUDIT.getCode());
        this.dao.changeAuditUser(request);
    }

    /**
     * @author Jason
     * @date 2020/9/27 9:47
     * @params [request]
     * 验证中心审核通过，下一节点验证中心审批
     * @return void
     */
    public void centerAudit(TestCategoryAssessRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.CENTER_AUDIT.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        User auditUser = UserUtils.get(request.getAuditUserId());
        User createBy = UserUtils.getUser();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.GENERIC,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,vars);
        request.preUpdate();
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.CENTER_APPROVAL.getCode()));
        request.setStatus(TestCategoryAssessRequestEnum.CENTER_APPROVAL.getCode());
        this.dao.changeAuditUser(request);
    }

    /**
    * @author Jason
    * @date 2020/9/27 9:48
    * @params [request]
    * 验证中心审批通过，流程结束
    * @return void
    */
    public void centerApproval(TestCategoryAssessRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.CENTER_APPROVAL.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,null);
        request.preUpdate();
        this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.APPROVED.getCode()));
        this.dao.changeAuditUser(request);
        //生效该申请单全部评估
        assessmentDao.apply(new TestCategoryAssessment().setrId(request.getId()).setStatus(TestCategoryAssessment.APPLIED));
        //更新试验室整体情况描述
        request = this.get(request.getId());
        labInfoStateService.updateLabinfoDescribe(request.getvId(),request.getLabId());

    }

    /**
    * @author Jason
    * @date 2020/9/27 9:48
    * @params [request]
    * 驳回始终返回至创建者
    * @return void
    */
    public void reject(TestCategoryAssessRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryAssessRequestEnum.CENTER_AUDIT.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        request = this.dao.get(request);
        User updateBy = UserUtils.get(request.getUpdateById());

        TestCategoryAssessRequest assreq = this.dao.get(request.getId());
        User initiator = UserUtils.get(assreq.getInitiator());
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",initiator);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryAssessmentAuditProcess.REJECT,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                initiator.getLoginName(),vars);
        request.preUpdate();
        this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.REJECT.getCode()));
        this.dao.changeAuditUser(request.setAuditUserId(initiator.getId()));
    }

    /**
    * @author Jason
    * @date 2020/9/27 9:55
    * @params [request]
    * 检查试验室所有试验能力是否都填写数据
    * @return void
    */
    public int checkRequestStatus(TestCategory category) {
        //能力变更或新增能力必须填写自查表、能力评估表
        return categoryDao.checkRequestStatus(category);
    }

    /**
     * @Describe:能力建设计划检查
     * @Author:WuHui
     * @Date:13:34 2020/10/22
     * @param category
     * @return:int
    */
    public int checkAbilityProject(TestCategory category) {
        //能力未评估 或属于A、B 必须填写建设计划
        return categoryDao.checkAbilityProject(category);
    }

    /**
     * @Describe:试验室试验能力信息表下载
     * @Author:WuHui
     * @Date:16:46 2020/11/30
     * @param assessRequest
     * @param response
     * @return:void
    */
    public void downloadLabAbilityInfo(TestCategoryAssessRequest assessRequest, HttpServletResponse response){
        //获取申请信息数据
        assessRequest = this.get(assessRequest.getId());
        //获取试验室整体状态
        LabInfoState state = labInfoStateService.findLabInfoStateByLabId(assessRequest.getLabId());
        //获取试验室能力
        TestCategory testCategory = new TestCategory();
        testCategory.setLabId(assessRequest.getLabId());
        List<Map<String,String>> list = testCategoryService.labAbilityListWithEquipment(testCategory);
        XWPFDocument document = null;
        try {
            String url= TestCategoryAssessRequestService.class.getClassLoader().getResource("template/试验室试验能力信息表模板.docx").getPath();
            url = url.replaceFirst("/","");
            document = new XWPFDocument(POIXMLDocument.openPackage(url));
            //获取表格对象集合
            List<XWPFTable> tables = document.getTables();
            //获取试验室整体状态参数
            Map<String,String> params = new HashMap<>();
            params.put("describe",state.getDescribe());
            params.put("place",state.getPlace());
            params.put("personNorm",state.getPersonNorm());
            params.put("persionCert",state.getPersionCert());
            params.put("plan",state.getPlan());
            params.put("manageFlow",state.getManageFlow());
            if(assessRequest.getLabInfo() !=null ){
                params.put("labName",assessRequest.getLabInfo().getName());
                params.put("address",assessRequest.getLabInfo().getAddress());
            }
            if(StringUtils.isNotBlank(assessRequest.getInitiator())){
                params.put("initiator",UserUtils.get(assessRequest.getInitiator()).getName());
            }
            if(StringUtils.isNotBlank(assessRequest.getLabMin())){
                params.put("labMin",UserUtils.get(assessRequest.getLabMin()).getName());
            }
            if(StringUtils.isNotBlank(assessRequest.getTecDir())){
                params.put("tecDir",UserUtils.get(assessRequest.getTecDir()).getName());
            }
            if(StringUtils.isNotBlank(assessRequest.getTecMin())){
                params.put("tecMin",UserUtils.get(assessRequest.getTecMin()).getName());
            }
            //解析替换文本段落对象
            WordUtils.changeText(document, params);
            XWPFTable stateTable = tables.get(0);
            //遍历表格,并替换模板
            WordUtils.eachTable(stateTable.getRows(), params);
            XWPFTable table = tables.get(1);
            int[] mergeCols = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,17,18,19};
            WordUtils.writeTableRow(table,list,2,3,mergeCols);
            document.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @Describe:能力自查表下载
     * @Author:WuHui
     * @Date:16:48 2020/12/1
     * @param check
     * @param response
     * @return:void
    */
    public void downloadAbilityCheck(AbilityEvaluationCheck check, HttpServletResponse response){
        check = testCategoryService.getEvalCheck(check);
        XWPFDocument document = null;
        try {
            Map<String,String> params = new HashMap<>();
            params.put("labName",check.getLabInfo().getName());
            params.put("code",check.getTestCategory().getCode());
            params.put("name",check.getTestCategory().getName());
            params.put("accept",check.getAccept()?"√":"×");
            params.put("ripe",check.getRipe()?"√":"×");
            params.put("testStandard",check.getTestStandard()?"√":"×");
            params.put("other",check.getOther());
            params.put("install",check.getInstall()?"√":"");
            params.put("workStandard",check.getWorkStandard()?"√":"×");
            params.put("equipmentAccept",check.getEquipmentAccept()?"√":"×");
            params.put("documentA",check.getDocumentA()?"√":"×");
            params.put("documentB",check.getDocumentB()?"√":"×");
            params.put("documentC",check.getDocumentC()?"√":"×");
            String url= TestCategoryAssessRequestService.class.getClassLoader().getResource("template/试验室试验能力评估自查表.docx").getPath();
            url = url.replaceFirst("/","");
            document = new XWPFDocument(POIXMLDocument.openPackage(url));
            //获取表格对象集合
            List<XWPFTable> tables = document.getTables();
            XWPFTable stateTable = tables.get(0);
            //遍历表格,并替换模板
            WordUtils.eachTable(stateTable.getRows(), params);
            document.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
