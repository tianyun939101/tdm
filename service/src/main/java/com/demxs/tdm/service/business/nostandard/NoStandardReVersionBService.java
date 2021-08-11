package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.WordUtils;
import com.demxs.tdm.dao.business.nostandard.NoStandardMeasureCheacksDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardReVersionBDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.NoStandardTestCheckEnum;
import com.demxs.tdm.domain.business.nostandard.*;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.yuangong.YuangongService;
import org.apache.axis.components.uuid.UUIDGen;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoStandardReVersionBService extends CrudService<NoStandardReVersionBDao, NoStandardReVerSionB> {
    @Autowired
    private NoStandardReVersionBDao versionBDao;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private NoStandardOtherUserService otherUserService;
    @Autowired
    private NoStandardOtherUserService noStandardOtherUserService;
    @Autowired
    private YuangongService yuangongService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OfficeDao officeDao;
    @Autowired
    private NoStandardMeasureCheacksDao measureCheacksDao;
    @Autowired
    private NoStandardTestLogService noStandardTestLogService;
    /**
     * 保存、提交
     * @param verSionB
     * @param flag
     */
    @Transactional
    public void save(NoStandardReVerSionB verSionB, String flag) {
        String verSionBId = verSionB.getId();
        Office office = UserUtils.getUser().getOffice();
        if (StringUtils.isBlank(verSionBId)) {
            verSionBId = UUID.randomUUID().toString();
            verSionB.setId(verSionBId);
            versionBDao.insertBasic(verSionB);
        } else {
            versionBDao.updateBasic(verSionB);
        }
        ExaminationApply ex = verSionB.getEx() == null ? new ExaminationApply() : verSionB.getEx();
        ex.setBasicTable(verSionBId);
        ex.setApplyDeptId(office.getId());
        ex.setCreateBy(UserUtils.getUser());
        if (verSionB.getEx() != null && StringUtils.isBlank(verSionB.getEx().getId())) {
            //试验审批
            ex.setId(UUID.randomUUID().toString());
            versionBDao.insertExamination(ex);
        } else {
            versionBDao.updateExamination(verSionB.getEx());
        }
        CheckOutline ol = verSionB.getOl() == null ? new CheckOutline() : verSionB.getOl();
        ol.setBasicTable(verSionBId);
        ol.setAuthorizedUser(UserUtils.getUser());
        if (verSionB.getOl() != null && StringUtils.isBlank(verSionB.getOl().getId())) {
            //试验前检查
            ol.setId(UUID.randomUUID().toString());
            versionBDao.insertCheckOutLine(ol);
        } else {
            versionBDao.updateCheckOutLine(verSionB.getOl());
        }
        TestingRecord rec = verSionB.getRec() == null ? new TestingRecord() : verSionB.getRec();
        rec.setBasicTable(verSionBId);
        rec.setNotekeeper(UserUtils.getUser());
        if (verSionB.getRec() != null && StringUtils.isBlank(verSionB.getRec().getId())) {
            //试验系统调试记录
            rec.setId(UUID.randomUUID().toString());
            versionBDao.insertRecord(rec);
        } else {
            versionBDao.updateRecord(verSionB.getRec());
        }
        //计量检查
        List<MeasureCheck> measureChecks =  verSionB.getMeasure()==null?new ArrayList<MeasureCheck>():verSionB.getMeasure();
        MeasureCheck mcheck = new MeasureCheck();
        mcheck.setBaseTable(verSionBId);
        measureCheacksDao.delete(mcheck);
        if(CollectionUtils.isNotEmpty(measureChecks)){
            for(MeasureCheck measureCheck : measureChecks){
                    measureCheck.setBaseTable(verSionBId);
                    measureCheck.setId(UUID.randomUUID().toString()  );
                    measureCheacksDao.insert(measureCheck);
            }
            verSionB.setAuthorizedUser(UserUtils.getUser());
            versionBDao.updateBasic(verSionB);
        }

        //保存其他试验人员
        if (verSionB.getOtherUsers() != null) {
            otherUserService.deleteByResourceId(verSionB.getId());
            for (NoStandardOtherUser otherUser : verSionB.getOtherUsers()) {
                otherUser.setResourceId(verSionB.getId());
                otherUserService.save(otherUser);
            }
        }
        //提交,启动流程
        if ("submit".equals(flag)) {
            //试验负责任人
            User testPrincipal = UserUtils.get(verSionB.getTestPrincipalUser().getUser().getId());
            //试验审批表
            if (ex != null) {
                String title = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请（试验审批）！";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                actTaskService.apiStartProcess("baseCheckVB", testPrincipal.getLoginName(), ex.getId(), title, vars);
                ex.setAuditType("01");
                versionBDao.updateExamination(ex);
            }
            if (ol != null) {
                String title = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请（试验前检查大纲）！";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                //批准人
                User applyUser = userDao.get(ol.getApplyUser().getId());
                //试验前大纲
                actTaskService.apiStartProcess("checkOutline", applyUser.getLoginName(), ol.getId(), title, vars);
                ol.setAuditType("01");
                versionBDao.updateCheckOutLine(ol);
            }
            if (rec != null) {
                String title = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请（试验系统调试记录）！";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                User applyUser =userDao.get(rec.getApplyUser().getId());
                //试验系统调试记录
                actTaskService.apiStartProcess("testingRecord", applyUser.getLoginName(), rec.getId(), title, vars);
                rec.setAuditType("01");
                versionBDao.updateRecord(rec);
            }
            if(CollectionUtils.isNotEmpty(measureChecks) && verSionB.getMeapplyUser() != null){
                String title = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请（计量检查表）！";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                User applyUser =userDao.get(verSionB.getMeapplyUser().getId());
                //试验系统调试记录
                actTaskService.apiStartProcess("measureCheck", applyUser.getLoginName(), verSionB.getId(), title, vars);
                verSionB.setAuditType("01");
                versionBDao.updateBasic(verSionB);
            }
            //审批履历
            AuditInfo auditInfo = new AuditInfo(verSionB.getId(), UserUtils.getUser(), 1, "发起审批");
            auditInfoService.save(auditInfo);
        }
    }


    public NoStandardReVerSionB getById(NoStandardReVerSionB verSionB) {
            verSionB = versionBDao.getById(verSionB);
        if (verSionB.getEx() != null && verSionB.getEx().getApplyDept() != null) {
            ExaminationApply ex = verSionB.getEx();
            ex.setApplyDept(officeDao.get(ex.getApplyDept().getId()));
            verSionB.setEx(ex);
        }
        //试验前检查大纲
        if (verSionB.getOl() != null) {
            CheckOutline ol = verSionB.getOl();
            //检查组长
            if (ol.getExamineLeader() != null) {
                User user = userDao.get(ol.getExamineLeader().getId());
                ol.setExamineLeader(user);
            }
            //检查组成员
            if (StringUtils.isNotBlank(ol.getOthersId())) {
                String name = "";
                String[] split = ol.getOthersId().split(",");
                List<User> examineOtherUsers = ol.getExamineOtherUsers() == null ? new ArrayList<User>() : ol.getExamineOtherUsers();
                for (String otherId : split) {
                    User user = userDao.get(otherId);
                    examineOtherUsers.add(userDao.get(otherId));
                    name += user.getName()+",";
                }
                ol.setOthersName(name);
                ol.setExamineOtherUsers(examineOtherUsers);
            }
            //审核人
            if (ol.getApplyUser() != null) {
                ol.setApplyUser(userDao.get(ol.getApplyUser().getId()));
            }
            //参加部门
            if(ol.getJoinDept()!=null){
                ol.setJoinDept(officeDao.get(ol.getJoinDept().getId()));
            }
            verSionB.setOl(ol);
        }
        //试验系统调试记录
        if (verSionB.getRec() != null) {
            TestingRecord rec = verSionB.getRec();
            //批准
            if (rec.getApplyUser() != null) {
                rec.setApplyUser(userDao.get(rec.getApplyUser().getId()));
            }
            verSionB.setRec(rec);
        }
        List<MeasureCheck> byBasicId = measureCheacksDao.getByBasicId(verSionB.getId());
        verSionB.setMe(byBasicId);
        //试验负责人
        if (verSionB.getTestPrincipalUser() != null) {
            verSionB.setTestPrincipalUser(yuangongService.getByUserId(verSionB.getTestPrincipalUser().getId()));
        }
        //试验其他人员
        verSionB.setOtherUsers(noStandardOtherUserService.getByResourceId(verSionB.getId()));

        //计量检查 审批人
        if(verSionB.getMeapplyUser()!= null && StringUtils.isNotBlank(verSionB.getMeapplyUser().getId())){
            verSionB.setMeapplyUser(userDao.get(verSionB.getMeapplyUser().getId()));
        }
        if(verSionB.getCheckUser()!=null && StringUtils.isNotBlank(verSionB.getCheckUser().getId())){
            verSionB.setCheckUser(userDao.get(verSionB.getCheckUser().getId()));
        }
        return verSionB;
    }

    public void delete(String id) {
        NoStandardReVerSionB verSionB = new NoStandardReVerSionB();
        ExaminationApply examinationApply = new ExaminationApply();
        CheckOutline checkOutline = new CheckOutline();
        TestingRecord testingRecord = new TestingRecord();

        verSionB.setId(id);
        examinationApply.setBasicTable(id);
        checkOutline.setBasicTable(id);
        testingRecord.setBasicTable(id);
        versionBDao.deleteBasic(verSionB);
        versionBDao.deleteExamination(examinationApply);
        versionBDao.deleteCheckOutLine(checkOutline);
        versionBDao.deleteRecord(testingRecord);
    }

    /**
     * 审批同意/通过
     *
     * @param approveDto
     * @param flag
     */
    public void approve(ApproveDTO approveDto, String flag,String checkResult,String correctSi) {
        NoStandardReVerSionB noStandardReVerSionB = new  NoStandardReVerSionB();
        noStandardReVerSionB.setId(approveDto.getId());
        //获取数据
         noStandardReVerSionB = this.getById(noStandardReVerSionB);
        //下一节点审批人
        User user = userDao.get(approveDto.getNextUser());
        switch (flag) {
            case "base":
                ExaminationApply ex = noStandardReVerSionB.getEx();
                if (noStandardReVerSionB.getEx() != null && noStandardReVerSionB.getEx().getAuditType().equals("01")) {
                    ex.setAuditType("02");
                    ex.setDeptHeadUserId(user.getId());
                } else if (noStandardReVerSionB.getEx() != null && noStandardReVerSionB.getEx().getAuditType().equals("02")) {
                    ex.setAuditType("03");
                    ex.setTeamHeadUserId(user.getId());
                } else if (noStandardReVerSionB.getEx() != null && noStandardReVerSionB.getEx().getAuditType().equals("03")) {
                    ex.setAuditType("04");
                    ex.setApplyBasicUserId(user.getId());
                } else if (noStandardReVerSionB.getEx() != null && noStandardReVerSionB.getEx().getAuditType().equals("04")) {
                    ex.setAuditType("05");
                }
                ex.setUpdateDate(new Date());
                String title = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请（试验审批）！";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                String assignee = user == null ? "" : user.getLoginName();
                actTaskService.apiComplete(noStandardReVerSionB.getEx().getId(), approveDto.getOpinion(), Global.YES, assignee, vars);
                versionBDao.updateExamination(ex);
                break;
            case "checkOL":
                CheckOutline ol = noStandardReVerSionB.getOl();
                if (ol != null) {
                    ol.setAuditType("02");
                    ol.setUpdateDate(new Date());
                    //批准人
                   // ol.setApplyUser(user);
                    String titleOl = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请！";
                    Map<String, Object> varsOl = new HashMap<>(1);
                    varsOl.put("message", titleOl);
                    String assigneeOl = user == null ? "" : user.getLoginName();
                    actTaskService.apiComplete(noStandardReVerSionB.getOl().getId(), approveDto.getOpinion(), Global.YES, assigneeOl, varsOl);
                    versionBDao.updateCheckOutLine(ol);
                }
                break;
            case "testingRecord":
                TestingRecord rec = noStandardReVerSionB.getRec();
                if (rec != null) {
                    rec.setAuditType("02");
                    //批准人
                    //rec.setApplyUser(user);
                    rec.setUpdateDate(new Date());
                    String titleRec = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请！";
                    Map<String, Object> varsRec = new HashMap<>(1);
                    varsRec.put("message", titleRec);
                    String assigneeOl = user == null ? "" : user.getLoginName();
                    actTaskService.apiComplete(rec.getId(), approveDto.getOpinion(), Global.YES, assigneeOl, varsRec);
                    versionBDao.updateRecord(rec);
                }
                break;
            case "measureCheck":
                if(noStandardReVerSionB.getAuditType().equals("01")){
                    noStandardReVerSionB.setAuditType("02");
                    noStandardReVerSionB.setCheckUser(user);
                }else if(noStandardReVerSionB.getAuditType().equals("02")){
                    noStandardReVerSionB.setAuditType("03");
                    noStandardReVerSionB.setCheckResult(checkResult);
                    noStandardReVerSionB.setCorrectSi(correctSi);
                }
                noStandardReVerSionB.setUpdateDate(new Date());


                String titleRec = "请处理'" + UserUtils.getUser().getName() + "'提交的试验前检查申请（计量检查表）！";
                Map<String, Object> varsRec = new HashMap<>(1);
                varsRec.put("message", titleRec);
                String assigneeOl = user == null ? "" : user.getLoginName();
                actTaskService.apiComplete(noStandardReVerSionB.getId(), approveDto.getOpinion(), Global.YES, assigneeOl, varsRec);
                versionBDao.updateBasic(noStandardReVerSionB);
                break;
        }
        //审批履历
        //AuditInfo auditInfo = new AuditInfo(noStandardReVerSionB.getId(),user,1,"发起审批");
        //auditInfoService.save(auditInfo);
        System.out.println(666);

    }

    /**
     * 审批驳回
     * @param approveDto
     * @param flag
     */
    public void reject(ApproveDTO approveDto, String flag) {
        NoStandardReVerSionB noStandardReVerSionB = new  NoStandardReVerSionB();
        noStandardReVerSionB.setId(approveDto.getId());
        //获取数据
         noStandardReVerSionB = this.getById(noStandardReVerSionB);
        switch (flag) {
            case "base":
                ExaminationApply ex = noStandardReVerSionB.getEx();
                if (ex != null) {
                    User createBy = ex.getCreateBy();
                    Map<String, Object> vars = new HashMap<>(1);
                    //审批
                    String title = "您提交的试验前检查申请，已被'" + UserUtils.getUser().getName() + "'驳回！";
                    vars.put("message", title);
                    String assignee = createBy == null ? "" : createBy.getLoginName();
                    actTaskService.apiComplete(ex.getId(), approveDto.getOpinion(), Global.NO, assignee, vars);
                    //审批履历
                    AuditInfo auditInfo = new AuditInfo(approveDto.getId(), createBy, 0, approveDto.getOpinion());
                    auditInfoService.save(auditInfo);
                    ex.setAuditType("06");
                    versionBDao.updateExamination(ex);
                }
                break;
            case "checkOL":
                CheckOutline ol = noStandardReVerSionB.getOl();
                if(ol != null ){
                    User authorizedUser = ol.getAuthorizedUser();
                    Map<String, Object> vars = new HashMap<>(1);
                    //审批
                    String title = "您提交的试验前检查申请，已被'" + UserUtils.getUser().getName() + "'驳回！";
                    vars.put("message", title);
                    String assignee = authorizedUser == null ? "" : authorizedUser.getLoginName();
                    actTaskService.apiComplete(ol.getId(), approveDto.getOpinion(), Global.NO, assignee, vars);
                    //审批履历
                    AuditInfo auditInfo = new AuditInfo(approveDto.getId(), authorizedUser, 0, approveDto.getOpinion());
                    auditInfoService.save(auditInfo);
                    ol.setAuditType("06");
                    versionBDao.updateCheckOutLine(ol);
                }
                break;
            case "testingRecord":
                TestingRecord rec = noStandardReVerSionB.getRec();
                 if(rec != null){
                     User notekeeper = rec.getNotekeeper();
                     Map<String, Object> vars = new HashMap<>(1);
                     //审批
                     String title = "您提交的试验前检查申请，已被'" + UserUtils.getUser().getName() + "'驳回！";
                     vars.put("message", title);
                     String assignee = notekeeper == null ? "" : notekeeper.getLoginName();
                     actTaskService.apiComplete(rec.getId(), approveDto.getOpinion(), Global.NO, assignee, vars);
                     //审批履历
                     AuditInfo auditInfo = new AuditInfo(approveDto.getId(), notekeeper, 0, approveDto.getOpinion());
                     auditInfoService.save(auditInfo);
                     rec.setAuditType("06");
                     versionBDao.updateRecord(rec);
                 }
                break;
        }
    }

    /**
     * 试验前检查word下载
     * @param verSionB
     * @param response
     */
    public void downLoad(NoStandardReVerSionB verSionB, HttpServletResponse response ){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
        verSionB = this.getById(verSionB);

        XWPFDocument document = null;
        try {
            URL resource = NoStandardTestLogService.class.getClassLoader().getResource("template/试验前检查模板.docx");
            String url= NoStandardTestLogService.class.getClassLoader().getResource("template/试验前检查模板.docx").getPath();
            url = url.replaceFirst("/","");
            document = new XWPFDocument(POIXMLDocument.openPackage(url));
            //获取表格对象集合
            List<XWPFTable> tables = document.getTables();

            StringBuffer tableText = new StringBuffer();
            Map<String,String> params = new HashMap<>();
            List<NoStandardOtherUser> otherUsers = verSionB.getOtherUsers();
            if(CollectionUtils.isNotEmpty(otherUsers)){
                String substring = "";
                for(NoStandardOtherUser otherUser : otherUsers){
                    substring += otherUser.getName()+",";
                }
                params.put("others",substring.substring(0, substring.length() - 1));
            }


            params.put("testNameT","试验名称："+verSionB.getTestName());
            params.put("testOutIdt","试验大纲编号："+verSionB.getEx().getTestOutlineId());
            params.put("applyDeptT","组织部门："+officeDao.get(verSionB.getEx().getApplyDeptId()).getName());
            //params.put("",verSionB.getCode());
            params.put("testName",verSionB.getTestName());
           // params.put("",verSionB.getTestType());
            params.put("testPrincipalUser",userDao.get(verSionB.getTestPrincipalUser().getUser().getId()).getName());
          //  params.put("others","无");
            params.put("testOutlineId",verSionB.getEx().getTestOutlineId());
            params.put("applyDept",officeDao.get(verSionB.getEx().getApplyDeptId()).getName());
            params.put("testText",verSionB.getEx().getTestText());
            params.put("deptHeadUser","部门领导/二级IPT经理："+userDao.get(verSionB.getEx().getDeptHeadUserId()).getName());
            params.put("teamHeadUser","团队/部门质量主管："+userDao.get(verSionB.getEx().getTeamHeadUserId()).getName());
            params.put("applyBasicUser","批准："+userDao.get(verSionB.getEx().getApplyBasicUserId()).getName());
            params.put("examineGoal",verSionB.getOl().getExamineGoal());
            params.put("examineGist",verSionB.getOl().getExamineGist());
            params.put("examineLeader","检查组长："+userDao.get(verSionB.getOl().getExamineLeader().getId()).getName());
            String othersId = verSionB.getOl().getOthersId();
            if(StringUtils.isNotBlank(othersId)){
                String[] split = othersId.split(",");
                String name = "";
                for(String str : split){
                     name +=userDao.get(str)==null?"":userDao.get(str).getName()+",";
                }
                params.put("examineUsers","检查组成员："+name.substring(0,name.length()-1));
            }
            params.put("joinDept",officeDao.get(verSionB.getOl().getJoinDept().getId()).getName());
            params.put("time",format.format(verSionB.getOl().getTime()));
            params.put("space",verSionB.getOl().getSpace());
            params.put("examineTextRequre",verSionB.getOl().getExamineTextRequre());
            params.put("examineProc",verSionB.getOl().getExamineProc());
            params.put("authorizedUser",userDao.get(verSionB.getOl().getAuthorizedUser().getId()).getName());
             params.put("applyUserOL",userDao.get(verSionB.getOl().getApplyUser().getId()).getName());
            params.put("updateTime",format1.format(new Date()));
            params.put("notekeeper",userDao.get(verSionB.getRec().getNotekeeper().getId()).getName());
            params.put("applyUserRec",userDao.get(verSionB.getRec().getApplyUser().getId()).getName());
            params.put("debuggContent",verSionB.getRec().getDebuggContent());
            params.put("problem",verSionB.getRec().getProblom());
            params.put("conclu",verSionB.getRec().getConclusion());
            params.put("auther","编制："+userDao.get(verSionB.getAuthorizedUser().getId()).getName());
            params.put("applyMe","审核："+userDao.get(verSionB.getMeapplyUser().getId()).getName());
            params.put("mechecker","检查人： "+userDao.get(verSionB.getCheckUser().getId()).getName());

            params.put("checkResult",verSionB.getCheckResult());
            params.put("correctSi",verSionB.getCorrectSi());
            List<MeasureCheck> me = verSionB.getMe();

            for(int i =0; i < me.size();i++){
                MeasureCheck measureCheck = me.get(i);
                params.put(String.valueOf(i),String.valueOf(i+1));
                params.put("testSBName"+String.valueOf(i),measureCheck.getTestSBName());
                params.put("type"+String.valueOf(i),measureCheck.getType());
                params.put("jszb"+String.valueOf(i),measureCheck.getMastJszb());
                params.put("ccCode"+String.valueOf(i),measureCheck.getCcCode());
                params.put("yx"+String.valueOf(i),format.format(measureCheck.getYouXDate()));
                params.put("ret"+String.valueOf(i),measureCheck.getCheckResult());
            }

            this.changeText(document, params);
            noStandardTestLogService.eachTable(tables, tableText,params);
            //解析替换文本段落对象
           /* WordUtils.changeText(document, params);
            XWPFTable stateTable = tables.get(0);
            List<XWPFTableRow> rows = stateTable.getRows();
            //遍历表格,并替换模板
            this.eachTable(rows, params);*/
            document.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changeText(XWPFDocument document, Map<String, String> textMap){
        try{
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();

            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                String str = "";
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition()).trim();
                    str +=oneparaString;
                    if(i == runs.size()-1){
                    //替换模板原来位置
                        runs.get(i).setText(WordUtils.changeValue(runs.get(i),str, textMap),0);
                    }else{
                        runs.get(i).setText(WordUtils.changeValue(runs.get(i),"", textMap),0);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
