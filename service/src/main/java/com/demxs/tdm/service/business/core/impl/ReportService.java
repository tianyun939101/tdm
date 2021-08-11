package com.demxs.tdm.service.business.core.impl;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.dataCenter.DataBaseInfo;
import com.demxs.tdm.domain.dataCenter.DataTestInfo;
import com.demxs.tdm.service.business.TestTaskService;
import com.demxs.tdm.service.dataCenter.DataBaseInfoService;
import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.lab.LabTestItemService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import com.demxs.tdm.common.file.convertor.impl.AsposeConvertor;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.business.EntrustInfoService;
import com.demxs.tdm.service.business.EntrustReportService;
import com.demxs.tdm.service.business.core.IReportService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.CommonEntityEventListener;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * 试验报告服务
 * User: wuliepeng
 * Date: 2017-11-14
 * Time: 下午1:39
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ReportService implements IReportService {

    @Autowired
    private EntrustReportService reportService;
    @Autowired
    private EntrustInfoService entrustInfoService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private LabInfoService labInfoService;
    @Resource
    private ActTaskService actTaskService;
    @Resource
    private IExternalApi externalApi;
    @Resource
    private FileStore fileStore;
    @Resource
    private AssetInfoService assetInfoService;
    @Autowired
    private DataBaseInfoService baseInfoService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TestTaskService testTaskService;
    @Autowired
    private LabTestItemService labTestItemService;

    /**
     * 提交报告
     * @param entrustId 申请单ID
     * @param reportFile 报告文件地址
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveReport(String entrustId,String reportFile) throws ServiceException {
        EntrustReport report = reportService.getByEntrustId(entrustId);
        if(report == null){
            throw new ServiceException("申请单报告不存在");
        }
        try {
            report.setReportFile(reportFile);
            report.setCreateBy(UserUtils.getUser());
            reportService.save(report);
        }catch (Exception e){
            throw new ServiceException("保存报告出错",e);
        }
    }

    /**
     * 提交报告
     * @param entrustId 申请单ID
     * @param reportFile 报告文件地址
     * @throws ServiceException
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void submitReport(String entrustId,String reportFile) throws ServiceException {
        EntrustReport report = reportService.getByEntrustId(entrustId);
        if(report == null){
            throw new ServiceException("申请单报告不存在");
        }
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            entrustInfo.setStatus(EntrustConstants.ReportStage.AUDIT);
            entrustInfoService.save(entrustInfo);
            report.setStatus(EntrustConstants.ReportStatus.AUDIT);
            report.setReportFile(reportFile);
            report.setDrawUser(UserUtils.getUser());
            report.setDrawDate(new Date());
            report.setCreateBy(UserUtils.getUser());
            // TODO: 18/1/12 设备报告审核(试验室技术负责人)与批准人(试验室主管)
            List<User> technicalDirector = systemService.getUserByLabRole(entrustInfo.getLabId(), "TechnicalDirector");
            if(technicalDirector.isEmpty()){
                throw new ServiceException("所在试验室没有找到试验室技术负责人");
            }

            StringBuilder userNameSb = new StringBuilder();
            StringBuilder userIdSb = new StringBuilder();
            for (User user : technicalDirector) {
                userIdSb.append(user.getId()).append(",");
                userNameSb.append(user.getName()).append(",");
            }
            report.setOwner(userIdSb.substring(0, userIdSb.length() - 1));
            report.setOwnerName(userNameSb.substring(0,userNameSb.length()-1));
            reportService.save(report);
            AuditInfo auditInfo = new AuditInfo();
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfo.setResult(EntrustConstants.AuditResult.APPLY);
            auditInfo.setType(EntrustConstants.AuditType.REPORT_RECORD);
            auditInfo.setReason("编制报告");
            auditInfo.setBusinessKey(entrustId);
            auditInfoService.save(auditInfo);
            //流程  试验室技术负责人 审核
            Map<String,Object> vars = new HashMap<>();
            Map<String,Object> model = new HashMap<>();
            model.put("code",entrustInfo.getCode());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Report_Audit,model);
            vars.put("message", title);
            List<String> userLoginNames = new ArrayList<>();
            for (User u : technicalDirector) {
                userLoginNames.add(u.getLoginName());
            }
            actTaskService.apiComplete(entrustInfo.getId(),"", Global.YES, StringUtils.join(userLoginNames,";"),vars);
        }catch (Exception e){
            throw new ServiceException("保存报告出错",e);
        }
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void auditReport(String entrustId,String reportFile,AuditInfo auditInfo) throws ServiceException{
        EntrustReport report = reportService.getByEntrustId(entrustId);
        if(report == null){
            throw new ServiceException("报告不存在");
        }
        /**
         * 更新报告状态
         */
        EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
        if(EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
            entrustInfo.setStatus(EntrustConstants.ReportStage.PASS);
            report.setStatus(EntrustConstants.ReportStatus.PASS);
            LabInfo labInfo = labInfoService.get(entrustInfo.getLabId());
            report.setReportFile(reportFile);
            report.setOwner(labInfo.getLeader().getId());
            report.setOwnerName(labInfo.getLeader().getName());
            report.setAuditUser(UserUtils.getUser());
            report.setAuditDate(new Date());
            //流程  试验室负责人 批准
            Map<String,Object> vars = new HashMap<>();
            Map<String,Object> model = new HashMap<>();
            model.put("code",entrustInfo.getCode());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Report_PASS,model);
            vars.put("message", title);
            actTaskService.apiComplete(entrustInfo.getId(),auditInfo.getReason(), Global.YES, labInfo.getLeader().getLoginName(),vars);
        }else{
            reportReject(auditInfo, report, entrustInfo);
        }

        entrustInfoService.save(entrustInfo);
        reportService.save(report);
        /**
         * 设置审批信息
         */
        auditInfo.setType(EntrustConstants.AuditType.REPORT_RECORD);
        auditInfo.setBusinessKey(entrustId);
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfoService.save(auditInfo);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void paasReport(String entrustId,String reportFile,AuditInfo auditInfo) throws ServiceException{
        try {
            EntrustReport report = reportService.getByEntrustId(entrustId);
            if (report == null) {
                throw new ServiceException("报告不存在");
            }
            /**
             * 更新报告状态
             */
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                entrustInfo.setStatus(EntrustConstants.FinishStage.DONE);
                entrustInfo.setFinishDate(new Date());
                report.setStatus(EntrustConstants.ReportStatus.DONE);
                report.setApprovalUser(UserUtils.getUser());
                report.setApprovalDate(new Date());
                report.setReportFile(reportFile);
                report.setOwner("");
                report.setOwnerName("");

                /*AttachmentInfo attachment = assetInfoService.get(reportFile);
                if(attachment!=null) {
                    DocConvertor convertor = new AsposeConvertor();
                    //下载文档到本地
                    //String filePath = fileStore.downloadFile(attachment.getPath());
                    String fileType = "pdf";
                    //转换后的文档
                    File targetFile = FileUtils.createTempFile(fileType);
                    convertor.run(Global.getConfig("sourceDir")+attachment.getPath(), targetFile.getPath());
                    String pdfFilePath = fileStore.saveFile(report.getCode()+".pdf", FileUtils.readFileToByteArray(targetFile));
                    //FileUtils.forceDelete(new File(Global.getConfig("sourceDir")+attachment.getPath()));
                    FileUtils.forceDelete(targetFile);
                    report.setPdfFile(pdfFilePath);
                }

                //流程 通过
                if (CommonEntityEventListener.SEND_EXTERNAL_MESSAGE) {//发申请人待阅
                    SendTodoParam sendTodoParam = new SendTodoParam();
                    sendTodoParam.setType(2);
                    Map<String, Object> model1 = new HashMap<>();
                    model1.put("code", entrustInfo.getCode());
                    sendTodoParam.setSubject(FreeMarkers.renderString(EntrustConstants.MessageTemplate.Complete, model1));
                    sendTodoParam.setLink(Global.getConfig("activiti.form.server.url") + "/a/tdm/business/entrust/detail?id=" + entrustId);
                    sendTodoParam.setCreateTime(new Date());
                    sendTodoParam.setModelId(IdGen.uuid());
                    sendTodoParam.addTarget(systemService.getUser(entrustInfo.getUser().getId()).getLoginName());
                    externalApi.sendTodo(sendTodoParam);
                }*/
                actTaskService.apiComplete(entrustInfo.getId(), auditInfo.getReason(), Global.YES, null, null);

                //归档至数据中心
                this.file(entrustInfo);
            } else {
                reportReject(auditInfo, report, entrustInfo);
            }
            entrustInfoService.save(entrustInfo);
            reportService.save(report);
            /**
             * 设置审批信息
             */
            auditInfo.setType(EntrustConstants.AuditType.REPORT_RECORD);
            auditInfo.setBusinessKey(entrustId);
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfoService.save(auditInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 归档
     * @param entrustInfo
     */
    private void file(EntrustInfo entrustInfo) {

        String baseId = IdGen.uuid();
        DataBaseInfo dataBaseInfo = new DataBaseInfo(baseId);
        dataBaseInfo.setIsNewRecord(true);
        dataBaseInfo.setEntrustId(entrustInfo.getId());
        dataBaseInfo.setEntrustCode(entrustInfo.getCode());
        //设置申请单类型
        dataBaseInfo.setEntrustType(EntrustConstants.EntrustType.STANDARD);
        //申请人
        dataBaseInfo.setApplicant(entrustInfo.getUser().getId());
        //申请部门
        dataBaseInfo.setEntrustOrg(entrustInfo.getOrg().getId());
        //申请单位
        //dataBaseInfo.setCompany(entrustInfo.getCompany().getId());
        //试验室负责人
        dataBaseInfo.setLabLeader(entrustInfo.getLabManager().getId());
        //试验负责人
        dataBaseInfo.setTestLeader(entrustInfo.getTestCharge().getId());
        //任务书编号
        dataBaseInfo.setTaskNo(entrustInfo.getTaskNo());
        //计划编号
        dataBaseInfo.setPlanNo(entrustInfo.getPlanNo());
        //参品型号
        dataBaseInfo.setProductModel(entrustInfo.getModel());
        //试验性质
        dataBaseInfo.setTestNature(entrustInfo.getNature());
        //完成日期
        dataBaseInfo.setApplyDate(entrustInfo.getFinishDate());
        //实验目的
        dataBaseInfo.setTestPurpose(entrustInfo.getSummary());
        //报告编号
        dataBaseInfo.setReportCode(entrustInfo.getReport().getCode());
        //试验室id
        dataBaseInfo.setLabId(entrustInfo.getLabId());
        //dataBaseInfo.setTestName(entrustInfo.getTestName());

        //试验任务
        List<DataTestInfo> testInfoList = new ArrayList<>();
        List<TestTask> testTaskList = entrustInfoService.findTestTaskByEntrustId(entrustInfo.getId());
        if(null != testTaskList){
            for (TestTask testTask : testTaskList) {
                DataTestInfo testInfo = new DataTestInfo();
                testInfo.setBaseId(baseId);
                //查询出试验项目
                TestItem testItem = testTaskService.getTestItemById(testTask.getId());
                testInfo.setTestItemId(testItem.getId());
                testInfo.setTestItemName(testItem.getName());
                testInfo.setLabLeader(entrustInfo.getLabManager().getId());
                testInfo.setTestLeader(entrustInfo.getTestCharge().getId());
                testInfo.setTester(testTask.getOwner());
                testInfo.setOriginalRecord(testTask.getOriginRecordId());
                String imgIds = testTask.getImgIds();
                //图片数据
                if(StringUtils.isNotBlank(imgIds)){
                    String[] split = imgIds.split(",");
                    StringBuilder imgId = new StringBuilder(imgIds.length() + split.length);
                    for (String s : split) {
                        imgId.append(s).append(",");
                    }
                    if(imgId.length() > 0){
                        imgId.deleteCharAt(imgId.length()-1);
                    }
                    testInfo.setImgData(imgId.toString());
                }
                //其他文件数据
                List<AttachmentInfo> fileList = assetInfoService.getAttachListByBusiness(testTask.getId());
                if(null != fileList){
                    StringBuilder sb = new StringBuilder();
                    for (AttachmentInfo attachmentInfo : fileList) {
                        sb.append(attachmentInfo.getId()).append(",");
                    }
                    if(sb.length() > 0){
                        sb.deleteCharAt(sb.length()-1);
                    }
                    testInfo.setTestData(sb.toString());
                }
                testInfoList.add(testInfo);
            }
        }
        //ata章节
        List<EntrustAtaChapter> ataChapterList = entrustInfo.getAtaChapterList();
        StringBuilder ataIds = new StringBuilder();
        if(!Collections3.isEmpty(ataChapterList)){
            for(EntrustAtaChapter ataChapter : ataChapterList){
                if(StringUtils.isNoneBlank(ataChapter.getAtaId())){
                    ataIds.append(ataChapter.getAtaId()).append(",");
                }
            }
            if(ataIds.length() > 0){
                ataIds.deleteCharAt(ataIds.length()-1);
            }
        }
        dataBaseInfo.setAtaChapter(ataIds.toString());
        dataBaseInfo.setTestInfoList(testInfoList);
        baseInfoService.saveFromOther(dataBaseInfo);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public String applyReport(String entrustId) throws ServiceException {
        try {
            EntrustInfo entrustInfo = entrustInfoService.get(entrustId);
            List<User> dataManagers = systemService.getUserByLabRole(null, "DataManager");
            StringBuilder userIdSb = new StringBuilder();
            StringBuilder userNameSb = new StringBuilder();
            for (User user : dataManagers) {
                userIdSb.append(user.getLoginName()).append(",");
                userNameSb.append(user.getName()).append(",");
            }
            if (StringUtils.isEmpty(userIdSb)) {
                throw new ServiceException("此申请单所在的试验室未分配数据管理员");
            }

            SendTodoParam sendTodoParam = new SendTodoParam();
            sendTodoParam.setType(2);
            Map<String, Object> model1 = new HashMap<>();
            model1.put("applyUser", UserUtils.getUser().getName());
            model1.put("code",entrustInfo.getCode());
            sendTodoParam.setSubject(FreeMarkers.renderString(EntrustConstants.MessageTemplate.APPLY_VIEW_REPORT, model1));
            sendTodoParam.setLink(Global.getConfig("activiti.form.server.url") + "/a/tdm/business/entrust/detail?id=" + entrustId);
            sendTodoParam.setCreateTime(new Date());
            sendTodoParam.setModelId(IdGen.uuid());
            sendTodoParam.addTarget(userIdSb.substring(0, userIdSb.length() - 1));
            externalApi.sendTodo(sendTodoParam);
            return userNameSb.substring(0, userNameSb.length() - 1);
        } catch (Exception e) {
            throw new ServiceException("申请查看失败", e);
        }
    }

    private void reportReject(AuditInfo auditInfo, EntrustReport report, EntrustInfo entrustInfo) {
        entrustInfo.setStatus(EntrustConstants.ReportStage.REJECT);
        report.setStatus(EntrustConstants.ReportStatus.REJECT);
        report.setOwner(report.getDrawUser().getId());
        report.setOwnerName(report.getDrawUser().getName());
        //流程 驳回到提交人
        Map<String,Object> vars = new HashMap<>();
        Map<String,Object> model = new HashMap<>();
        model.put("code",entrustInfo.getCode());
        String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.Report_ReDraw,model);
        vars.put("message", title);
        actTaskService.apiComplete(entrustInfo.getId(),auditInfo.getReason(), Global.NO, systemService.getUser(report.getDrawUser().getId()).getLoginName(),vars);
    }
}
