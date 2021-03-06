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
     * ???????????????
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
            //????????????
            ex.setId(UUID.randomUUID().toString());
            versionBDao.insertExamination(ex);
        } else {
            versionBDao.updateExamination(verSionB.getEx());
        }
        CheckOutline ol = verSionB.getOl() == null ? new CheckOutline() : verSionB.getOl();
        ol.setBasicTable(verSionBId);
        ol.setAuthorizedUser(UserUtils.getUser());
        if (verSionB.getOl() != null && StringUtils.isBlank(verSionB.getOl().getId())) {
            //???????????????
            ol.setId(UUID.randomUUID().toString());
            versionBDao.insertCheckOutLine(ol);
        } else {
            versionBDao.updateCheckOutLine(verSionB.getOl());
        }
        TestingRecord rec = verSionB.getRec() == null ? new TestingRecord() : verSionB.getRec();
        rec.setBasicTable(verSionBId);
        rec.setNotekeeper(UserUtils.getUser());
        if (verSionB.getRec() != null && StringUtils.isBlank(verSionB.getRec().getId())) {
            //????????????????????????
            rec.setId(UUID.randomUUID().toString());
            versionBDao.insertRecord(rec);
        } else {
            versionBDao.updateRecord(verSionB.getRec());
        }
        //????????????
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

        //????????????????????????
        if (verSionB.getOtherUsers() != null) {
            otherUserService.deleteByResourceId(verSionB.getId());
            for (NoStandardOtherUser otherUser : verSionB.getOtherUsers()) {
                otherUser.setResourceId(verSionB.getId());
                otherUserService.save(otherUser);
            }
        }
        //??????,????????????
        if ("submit".equals(flag)) {
            //??????????????????
            User testPrincipal = UserUtils.get(verSionB.getTestPrincipalUser().getUser().getId());
            //???????????????
            if (ex != null) {
                String title = "?????????'" + UserUtils.getUser().getName() + "'???????????????????????????????????????????????????";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                actTaskService.apiStartProcess("baseCheckVB", testPrincipal.getLoginName(), ex.getId(), title, vars);
                ex.setAuditType("01");
                versionBDao.updateExamination(ex);
            }
            if (ol != null) {
                String title = "?????????'" + UserUtils.getUser().getName() + "'????????????????????????????????????????????????????????????";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                //?????????
                User applyUser = userDao.get(ol.getApplyUser().getId());
                //???????????????
                actTaskService.apiStartProcess("checkOutline", applyUser.getLoginName(), ol.getId(), title, vars);
                ol.setAuditType("01");
                versionBDao.updateCheckOutLine(ol);
            }
            if (rec != null) {
                String title = "?????????'" + UserUtils.getUser().getName() + "'???????????????????????????????????????????????????????????????";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                User applyUser =userDao.get(rec.getApplyUser().getId());
                //????????????????????????
                actTaskService.apiStartProcess("testingRecord", applyUser.getLoginName(), rec.getId(), title, vars);
                rec.setAuditType("01");
                versionBDao.updateRecord(rec);
            }
            if(CollectionUtils.isNotEmpty(measureChecks) && verSionB.getMeapplyUser() != null){
                String title = "?????????'" + UserUtils.getUser().getName() + "'??????????????????????????????????????????????????????";
                Map<String, Object> vars = new HashMap<>(1);
                vars.put("message", title);
                User applyUser =userDao.get(verSionB.getMeapplyUser().getId());
                //????????????????????????
                actTaskService.apiStartProcess("measureCheck", applyUser.getLoginName(), verSionB.getId(), title, vars);
                verSionB.setAuditType("01");
                versionBDao.updateBasic(verSionB);
            }
            //????????????
            AuditInfo auditInfo = new AuditInfo(verSionB.getId(), UserUtils.getUser(), 1, "????????????");
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
        //?????????????????????
        if (verSionB.getOl() != null) {
            CheckOutline ol = verSionB.getOl();
            //????????????
            if (ol.getExamineLeader() != null) {
                User user = userDao.get(ol.getExamineLeader().getId());
                ol.setExamineLeader(user);
            }
            //???????????????
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
            //?????????
            if (ol.getApplyUser() != null) {
                ol.setApplyUser(userDao.get(ol.getApplyUser().getId()));
            }
            //????????????
            if(ol.getJoinDept()!=null){
                ol.setJoinDept(officeDao.get(ol.getJoinDept().getId()));
            }
            verSionB.setOl(ol);
        }
        //????????????????????????
        if (verSionB.getRec() != null) {
            TestingRecord rec = verSionB.getRec();
            //??????
            if (rec.getApplyUser() != null) {
                rec.setApplyUser(userDao.get(rec.getApplyUser().getId()));
            }
            verSionB.setRec(rec);
        }
        List<MeasureCheck> byBasicId = measureCheacksDao.getByBasicId(verSionB.getId());
        verSionB.setMe(byBasicId);
        //???????????????
        if (verSionB.getTestPrincipalUser() != null) {
            verSionB.setTestPrincipalUser(yuangongService.getByUserId(verSionB.getTestPrincipalUser().getId()));
        }
        //??????????????????
        verSionB.setOtherUsers(noStandardOtherUserService.getByResourceId(verSionB.getId()));

        //???????????? ?????????
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
     * ????????????/??????
     *
     * @param approveDto
     * @param flag
     */
    public void approve(ApproveDTO approveDto, String flag,String checkResult,String correctSi) {
        NoStandardReVerSionB noStandardReVerSionB = new  NoStandardReVerSionB();
        noStandardReVerSionB.setId(approveDto.getId());
        //????????????
         noStandardReVerSionB = this.getById(noStandardReVerSionB);
        //?????????????????????
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
                String title = "?????????'" + UserUtils.getUser().getName() + "'???????????????????????????????????????????????????";
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
                    //?????????
                   // ol.setApplyUser(user);
                    String titleOl = "?????????'" + UserUtils.getUser().getName() + "'?????????????????????????????????";
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
                    //?????????
                    //rec.setApplyUser(user);
                    rec.setUpdateDate(new Date());
                    String titleRec = "?????????'" + UserUtils.getUser().getName() + "'?????????????????????????????????";
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


                String titleRec = "?????????'" + UserUtils.getUser().getName() + "'??????????????????????????????????????????????????????";
                Map<String, Object> varsRec = new HashMap<>(1);
                varsRec.put("message", titleRec);
                String assigneeOl = user == null ? "" : user.getLoginName();
                actTaskService.apiComplete(noStandardReVerSionB.getId(), approveDto.getOpinion(), Global.YES, assigneeOl, varsRec);
                versionBDao.updateBasic(noStandardReVerSionB);
                break;
        }
        //????????????
        //AuditInfo auditInfo = new AuditInfo(noStandardReVerSionB.getId(),user,1,"????????????");
        //auditInfoService.save(auditInfo);
        System.out.println(666);

    }

    /**
     * ????????????
     * @param approveDto
     * @param flag
     */
    public void reject(ApproveDTO approveDto, String flag) {
        NoStandardReVerSionB noStandardReVerSionB = new  NoStandardReVerSionB();
        noStandardReVerSionB.setId(approveDto.getId());
        //????????????
         noStandardReVerSionB = this.getById(noStandardReVerSionB);
        switch (flag) {
            case "base":
                ExaminationApply ex = noStandardReVerSionB.getEx();
                if (ex != null) {
                    User createBy = ex.getCreateBy();
                    Map<String, Object> vars = new HashMap<>(1);
                    //??????
                    String title = "??????????????????????????????????????????'" + UserUtils.getUser().getName() + "'?????????";
                    vars.put("message", title);
                    String assignee = createBy == null ? "" : createBy.getLoginName();
                    actTaskService.apiComplete(ex.getId(), approveDto.getOpinion(), Global.NO, assignee, vars);
                    //????????????
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
                    //??????
                    String title = "??????????????????????????????????????????'" + UserUtils.getUser().getName() + "'?????????";
                    vars.put("message", title);
                    String assignee = authorizedUser == null ? "" : authorizedUser.getLoginName();
                    actTaskService.apiComplete(ol.getId(), approveDto.getOpinion(), Global.NO, assignee, vars);
                    //????????????
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
                     //??????
                     String title = "??????????????????????????????????????????'" + UserUtils.getUser().getName() + "'?????????";
                     vars.put("message", title);
                     String assignee = notekeeper == null ? "" : notekeeper.getLoginName();
                     actTaskService.apiComplete(rec.getId(), approveDto.getOpinion(), Global.NO, assignee, vars);
                     //????????????
                     AuditInfo auditInfo = new AuditInfo(approveDto.getId(), notekeeper, 0, approveDto.getOpinion());
                     auditInfoService.save(auditInfo);
                     rec.setAuditType("06");
                     versionBDao.updateRecord(rec);
                 }
                break;
        }
    }

    /**
     * ???????????????word??????
     * @param verSionB
     * @param response
     */
    public void downLoad(NoStandardReVerSionB verSionB, HttpServletResponse response ){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy???MM???dd???");
        verSionB = this.getById(verSionB);

        XWPFDocument document = null;
        try {
            URL resource = NoStandardTestLogService.class.getClassLoader().getResource("template/?????????????????????.docx");
            String url= NoStandardTestLogService.class.getClassLoader().getResource("template/?????????????????????.docx").getPath();
            url = url.replaceFirst("/","");
            document = new XWPFDocument(POIXMLDocument.openPackage(url));
            //????????????????????????
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


            params.put("testNameT","???????????????"+verSionB.getTestName());
            params.put("testOutIdt","?????????????????????"+verSionB.getEx().getTestOutlineId());
            params.put("applyDeptT","???????????????"+officeDao.get(verSionB.getEx().getApplyDeptId()).getName());
            //params.put("",verSionB.getCode());
            params.put("testName",verSionB.getTestName());
           // params.put("",verSionB.getTestType());
            params.put("testPrincipalUser",userDao.get(verSionB.getTestPrincipalUser().getUser().getId()).getName());
          //  params.put("others","???");
            params.put("testOutlineId",verSionB.getEx().getTestOutlineId());
            params.put("applyDept",officeDao.get(verSionB.getEx().getApplyDeptId()).getName());
            params.put("testText",verSionB.getEx().getTestText());
            params.put("deptHeadUser","????????????/??????IPT?????????"+userDao.get(verSionB.getEx().getDeptHeadUserId()).getName());
            params.put("teamHeadUser","??????/?????????????????????"+userDao.get(verSionB.getEx().getTeamHeadUserId()).getName());
            params.put("applyBasicUser","?????????"+userDao.get(verSionB.getEx().getApplyBasicUserId()).getName());
            params.put("examineGoal",verSionB.getOl().getExamineGoal());
            params.put("examineGist",verSionB.getOl().getExamineGist());
            params.put("examineLeader","???????????????"+userDao.get(verSionB.getOl().getExamineLeader().getId()).getName());
            String othersId = verSionB.getOl().getOthersId();
            if(StringUtils.isNotBlank(othersId)){
                String[] split = othersId.split(",");
                String name = "";
                for(String str : split){
                     name +=userDao.get(str)==null?"":userDao.get(str).getName()+",";
                }
                params.put("examineUsers","??????????????????"+name.substring(0,name.length()-1));
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
            params.put("auther","?????????"+userDao.get(verSionB.getAuthorizedUser().getId()).getName());
            params.put("applyMe","?????????"+userDao.get(verSionB.getMeapplyUser().getId()).getName());
            params.put("mechecker","???????????? "+userDao.get(verSionB.getCheckUser().getId()).getName());

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
            //??????????????????????????????
           /* WordUtils.changeText(document, params);
            XWPFTable stateTable = tables.get(0);
            List<XWPFTableRow> rows = stateTable.getRows();
            //????????????,???????????????
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
                    //????????????????????????
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
