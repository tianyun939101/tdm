package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.WordUtils;
import com.demxs.tdm.dao.ability.TestCategoryModifyRecordDao;
import com.demxs.tdm.dao.ability.TestCategoryModifyRequestDao;
import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.domain.ability.TestCategoryChange;
import com.demxs.tdm.domain.ability.TestCategoryModify;
import com.demxs.tdm.domain.ability.TestCategoryModifyRecord;
import com.demxs.tdm.domain.ability.TestCategoryModifyRequest;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.demxs.tdm.domain.ability.constant.ModifyType;
import com.demxs.tdm.domain.ability.constant.TestCategoryModifyRequestStatus;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author: Jason
 * @Date: 2020/8/4 16:03
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class TestCategoryModifyRequestService extends CrudService<TestCategoryModifyRequestDao, TestCategoryModifyRequest> {

    @Autowired
    private AuditInfoDao auditInfoDao;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TestCategoryModifyService modifyService;
    @Autowired
    private TestCategoryModifyRecordDao recordDao;
    @Autowired
    private TestCategoryService testCategoryService;
    final static String REJECTED = "0";
    final static String NEED_JOIN_SIGN = "1";
    final static String UN_WANTED_JOIN_SIGN = "2";

    @Override
    public TestCategoryModifyRequest get(String id) {
        TestCategoryModifyRequest request = super.get(id);
        if(null != request && StringUtils.isNotBlank(request.getCoSignerIds())){
            String[] split = request.getCoSignerIds().split(",");
            List<User> list = new ArrayList<>(split.length);
            for (String s : split) {
                list.add(UserUtils.get(s));
            }
            request.setCoSignerUserList(list);
        }
        return request;
    }

    @Override
    public TestCategoryModifyRequest get(TestCategoryModifyRequest entity) {
        TestCategoryModifyRequest request = super.get(entity);
        if(null != request && StringUtils.isNotBlank(request.getCoSignerIds())){
            String[] split = request.getCoSignerIds().split(",");
            List<User> list = new ArrayList<>(split.length);
            for (String s : split) {
                list.add(UserUtils.get(s));
            }
            request.setCoSignerUserList(list);
        }
        return request;
    }

    @Override
    public void save(TestCategoryModifyRequest request) {
        request.setStatus(TestCategoryModifyRequestStatus.NOT_SUBMITTED.getCode());
        super.save(request);
    }

    public int changeStatus(TestCategoryModifyRequest request){
        return this.dao.changeStatus(request);
    }

    public int changeCoSingerUser(TestCategoryModifyRequest request){
        return this.dao.changeCoSingerUser(request);
    }

    public int changeSingedUser(TestCategoryModifyRequest request){
        return this.dao.changeSingedUser(request);
    }

    public int updateActive(TestCategoryModifyRequest request){
        request.preUpdate();
        return this.dao.updateActive(request);
    }

    public int changeAuditUser(TestCategoryModifyRequest request){
        return this.dao.changeAuditUser(request);
    }

    public TestCategoryModifyRequest getByCreatorId(TestCategoryModifyRequest request){
        return this.dao.getByCreatorId(request);
    }

    /**
    * @author Jason
    * @date 2020/8/5 14:55
    * @params [request]
    * 提交
    * @return void
    */
    public void commit(TestCategoryModifyRequest request){
        super.save(request);

        User auditUser = UserUtils.get(request.getAuditUserId());
        User createBy = UserUtils.getUser();
        request = this.get(request);
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(request.getId()).active().singleResult();
        //activity查询不到，代表此申请未提交过
        if(null == task){
            //启动流程
            Map<String,Object> model = new HashMap<>(1);
            model.put("userName",createBy.getName());
            String title = FreeMarkers.renderString(AbilityConstants.TestCategoryModifyAuditProcess.AUDIT,model);
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiStartProcess(AbilityConstants.TestCategoryModifyAuditProcess.PROCESS,
                    auditUser.getLoginName(), request.getId(), title, vars);
        }else{
            //审批
            Map<String,Object> model = new HashMap<>(1);
            model.put("userName",createBy);
            String title = FreeMarkers.renderString(AbilityConstants.TestCategoryModifyAuditProcess.AUDIT,model);

            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiComplete(request.getId(),"","", auditUser.getLoginName(),vars);
        }
        //修改状态
        request.setStatus(TestCategoryModifyRequestStatus.UNDER_REVIEW.getCode());
        this.changeStatus(request);
    }

    /**
    * @author Jason
    * @date 2020/8/5 14:55
    * @params [request]
    * 审核通过
    * @return void
    */
    public void audit(TestCategoryModifyRequest request){
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryModifyRequestStatus.UNDER_REVIEW.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        //审核结果
        String result = String.valueOf(auditInfo.getResult());

        String coSingerIds = request.getCoSignerIds();
        //判断是否需要会签
        boolean needJoinSign = StringUtils.isNotBlank(coSingerIds);
        User auditUser = UserUtils.get(request.getAuditUserId());
        request = this.get(request.getId());
        //申请创建人
        User createBy = request.getCreateBy();
        StringBuilder assigneeLoginName;
        if(needJoinSign){
            if(!REJECTED.equals(result)){
                result = NEED_JOIN_SIGN;
            }
            assigneeLoginName = new StringBuilder(StringUtils.EMPTY);
            for (String s : coSingerIds.split(",")) {
                assigneeLoginName.append(UserUtils.get(s).getLoginName()).append(";");
            }
            if(assigneeLoginName.length() > 0){
                assigneeLoginName.deleteCharAt(assigneeLoginName.length() - 1);
            }
        }else{
            if(!REJECTED.equals(result)){
                result = UN_WANTED_JOIN_SIGN;
            }
            assigneeLoginName = new StringBuilder(auditUser.getLoginName());
        }
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy.getName());
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryModifyAuditProcess.JOINTLY_SING,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),result, assigneeLoginName.toString(),vars);

        if(needJoinSign){
            request.setStatus(TestCategoryModifyRequestStatus.JOINTLY_SIGN.getCode());
        }else{
            request.setStatus(TestCategoryModifyRequestStatus.EXAMINATION.getCode());
        }
        request.setAuditUserId(auditUser.getId());
        request.setCoSignerIds(coSingerIds);
        request.preUpdate();
        this.dao.updateActive(request);
        //清空已会签人名单
        this.dao.changeSingedUser(request.setSigned(null));
    }

    /**
    * @author Jason
    * @date 2020/8/31 10:42
    * @params [request]
    * 会签
    * @return void
    */
    public void jointlySign(TestCategoryModifyRequest request){
        User curUser = UserUtils.getUser();
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(curUser);
        auditInfo.setRemarks(TestCategoryModifyRequestStatus.JOINTLY_SIGN.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        request = this.get(request.getId());
        String signed = request.getSigned() == null ? curUser.getId() : request.getSigned() + "," + curUser.getId();

        //判断是否所有人员都会签完毕
        if(getAsciiCode(request.getCoSignerIds()) == getAsciiCode(signed)){
            User auditUser = UserUtils.get(request.getAuditUserId());
            User createBy = request.getCreateBy();
            //审批
            Map<String,Object> model = new HashMap<>(1);
            model.put("userName",createBy);
            String title = FreeMarkers.renderString(AbilityConstants.TestCategoryModifyAuditProcess.EXAMINATION,model);

            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                    auditUser.getLoginName(),vars);

            //下一步：审批
            request.setStatus(TestCategoryModifyRequestStatus.EXAMINATION.getCode())
                    .setAuditUserId(auditUser.getId())
                    .setSigned(signed);
            request.preUpdate();
            this.dao.updateActive(request);
        }else{
            //否则写入已会签人
            if(request.getSigned() == null || !request.getSigned().contains(curUser.getId())){
                this.changeSingedUser(new TestCategoryModifyRequest(request.getId()).setSigned(signed));
            }
        }
    }

    final static char SYMBOL = ',';
    /**
    * @author Jason
    * @date 2020/9/5 12:14
    * @params [charSequence]
    * 返回字符串ASCII码之和
    * @return int
    */
    private static int getAsciiCode(CharSequence charSequence){
        if(null != charSequence){
            int result = 0;
            for (int i = 0; i < charSequence.length(); i++) {
                char c = charSequence.charAt(i);
                if(c != SYMBOL){
                    result += (int) c;
                }
            }
            return result;
        }else{
            return -1;
        }
    }

    /**
    * @author Jason
    * @date 2020/8/31 10:46
    * @params [request]
    * 审批
    * @return void
    */
    public void examination(TestCategoryModifyRequest request){
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryModifyRequestStatus.EXAMINATION.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        User auditUser = UserUtils.get(request.getAuditUserId());
        request = this.get(request.getId());
        User createBy = request.getCreateBy();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryModifyAuditProcess.APPROVAL,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                auditUser.getLoginName(),vars);

        //下一步：批准
        request.setStatus(TestCategoryModifyRequestStatus.UNDER_APPROVED.getCode());
        this.dao.changeStatus(request);
        this.dao.changeAuditUser(request.setAuditUserId(auditUser.getId()));
    }

    /**
    * @author Jason
    * @date 2020/8/5 14:55
    * @params [request]
    * 单位审批
    * @return void
    */
    public void approval(TestCategoryModifyRequest request){
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryModifyRequestStatus.UNDER_APPROVED.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        User auditUser = UserUtils.get(request.getAuditUserId());
        request = this.get(request.getId());
        User createBy = request.getCreateBy();
        //审批
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",createBy);
        String title = FreeMarkers.renderString(AbilityConstants.TestCategoryModifyAuditProcess.APPROVAL,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                auditUser.getLoginName(),vars);

        //下一步：批准
        request.setStatus(TestCategoryModifyRequestStatus.CENTER_APPROVED.getCode());
        this.dao.changeStatus(request);
        this.dao.changeAuditUser(request.setAuditUserId(auditUser.getId()));
    }

    /**
    * @author Jason
    * @date 2020/9/5 13:29
    * @params [request]
    * 验证中心批准，流程结束
    * @return void
    */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void centerApproval(TestCategoryModifyRequest request){
        User curUser = UserUtils.getUser();
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(curUser);
        auditInfo.setRemarks(TestCategoryModifyRequestStatus.CENTER_APPROVED.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        //流程结束
        actTaskService.apiComplete(request.getId(), auditInfo.getReason(),
                String.valueOf(auditInfo.getResult()), null, null);

        request.setStatus(TestCategoryModifyRequestStatus.APPROVED.getCode());
        this.dao.changeStatus(request);

        //生成中间版本表数据
        /*List<TestCategoryModify> modifyList = modifyService.findByRequestIdDistinct(request.getId());
        for (TestCategoryModify modify : modifyList) {
            TestCategoryChange change = new TestCategoryChange();
            ModifyType type;
            change.setcId(modify.getcId());
            change.setvId(modify.getvId());
            //删除和修改只需要cvr id，新增需要全部信息
            if(modify.isAddAction()){
                type = ModifyType.INSERT;
                change.setCode(modify.getCode());
                change.setName(modify.getName());
                change.setParentId(modify.getParentId());
                change.setParentIds(modify.getParentIds());
                change.setRemarks(modify.getRemarks());
                change.setStandard(modify.getStandard());
            }else if(modify.isToDeleted()){
                change.setrId(modify.getrId());
                type = ModifyType.DELETE;
            }else{
                change.setrId(modify.getrId());
                type = ModifyType.UPDATE;
            }
            change.setUpdateBy(curUser);
            testCategoryService.recordCategoryChange(change,type);
        }*/
        //应用修改
        //List<TestCategoryModify> modifyList = modifyService.findByRequestIdReturnOriginal(request.getId());
        //modifyService.batchApply(modifyList);
    }

    /**
    * @author Jason
    * @date 2020/8/5 14:55
    * @params [request]
    * 驳回
    * @return void
    */
    public void reject(TestCategoryModifyRequest request) {
        AuditInfo auditInfo = request.getAuditInfo();
        request = this.get(request.getId());
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.setRemarks(TestCategoryModifyRequestStatus.get(request.getStatus()) == null ?
                TestCategoryModifyRequestStatus.REJECTED.getTitle() : TestCategoryModifyRequestStatus.get(request.getStatus()).getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        User createBy = UserUtils.get(request.getCreateBy().getId());

        //审批
        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", AbilityConstants.TestCategoryModifyAuditProcess.REJECT);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                createBy.getLoginName(),vars);

        request.setStatus(TestCategoryModifyRequestStatus.REJECTED.getCode());
        this.changeStatus(request);
        this.dao.changeAuditUser(new TestCategoryModifyRequest(request.getId()).setAuditUserId(null));
    }

    public int deleteByVersionId(TestCategoryModifyRequest request){
        return this.dao.deleteByVersionId(request);
    }
    public int enabled(TestCategoryModifyRequest request){
        return this.dao.enabled(request);
    }
    public int disabled(TestCategoryModifyRequest request){
        return this.dao.disabled(request);
    }
    public int effect(TestCategoryModifyRequest request){
        return this.dao.effect(request);
    }

    @Override
    public void delete(TestCategoryModifyRequest entity) {
        modifyService.deleteByRequestId(new TestCategoryModify().setrId(entity.getId()));
        recordDao.deleteByRequestId(new TestCategoryModifyRecord().setrId(entity.getId()));
        super.delete(entity);
    }

    public List<TestCategoryModifyRequest> findByVersionId(TestCategoryModifyRequest request){
        return this.dao.findByVersionId(request);
    }

    /**
     * @Describe:试验室能力变更申请
     * @Author:WuHui
     * @Date:16:48 2020/12/1
     * @param modifyRequest
     * @param response
     * @return:void
     */
    public void downloadAbilityChange(TestCategoryModifyRequest modifyRequest, HttpServletResponse response){
        String labName ="" ,center="" ,reason = "", content="";
        Set<String> action = new HashSet<>();
        List<TestCategoryModifyRequest> list = this.findList(modifyRequest);
        for(TestCategoryModifyRequest req:list){
            req = this.get(req.getId());
            labName = req.getLabInfo().getName();
            center = req.getAffiliatedOffice().getName();
            reason += req.getReason();
            for(TestCategoryModifyRecord modify : req.getRecordList()){
                content += modify.getInfo() + "\r\n";
            }
            if(StringUtils.isNotBlank(req.getModifyType())){
                for(String type:req.getModifyType().split(",")){
                    action.add(type);
                }
            }
        }
        XWPFDocument document = null;
        try {
            Map<String,String> params = new HashMap<>();
            params.put("name",labName);
            params.put("date", DateUtils.getDate());
            params.put("center",center);
            params.put("reason",reason);
            params.put("content",content);
            for(int i=0;i<5;i++){
               params.put("type_"+i,action.contains(String.valueOf(i)) ? "[√]" : "[]");
            }
            String url= TestCategoryAssessRequestService.class.getClassLoader().getResource("template/试验验证能力图谱变更申请表.docx").getPath();
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
