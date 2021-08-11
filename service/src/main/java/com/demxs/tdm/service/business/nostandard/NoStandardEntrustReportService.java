package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import com.demxs.tdm.common.file.convertor.impl.AsposeConvertor;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustInfoDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustReportDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustResourceDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardExecutionDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.constant.NoStandardEntrustInfoEnum;
import com.demxs.tdm.domain.business.constant.NoStandardReportEnum;
import com.demxs.tdm.domain.business.nostandard.*;
import com.demxs.tdm.domain.dataCenter.DataBaseInfo;
import com.demxs.tdm.domain.dataCenter.DataTestInfo;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.dataCenter.DataBaseInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: Jason
 * @Date: 2020/3/9 18:34
 * @Description:
 */
@Service
@Transactional(readOnly = false)
public class NoStandardEntrustReportService extends CrudService<NoStandardEntrustReportDao, NoStandardEntrustReport> {

    @Autowired
    private NoStandardEntrustInfoDao infoDao;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private DataBaseInfoService dataBaseInfoService;
    @Autowired
    private NoStandardEntrustResourceDao resourceDao;
    @Autowired
    private NoStandardExecutionDao executionDao;
    @Autowired
    private DataBaseInfoService baseInfoService;
    @Autowired
    private AssetInfoService assetInfoService;
    @Autowired
    private FileStore fileStore;

    public NoStandardEntrustReport getBaseByEntrustId(String id){
        return this.dao.getBaseByEntrustId(id);
    }

    @Transactional(readOnly = false)
    public int changeStatus(NoStandardEntrustReport report){
        if(NoStandardReportEnum.APPROVED.getCode().equals(report.getStatus())){
            NoStandardEntrustInfo info = new NoStandardEntrustInfo(report.getEntrustId());
            info.setStatus(NoStandardEntrustInfoEnum.FILE.getCode());
            infoDao.updateActive(info);
        }
        return super.dao.changeStatus(report);
    }

    public NoStandardEntrustReport getDetail(String id){
        return super.dao.getDetail(id);
    }

    @Transactional(readOnly = false)
    public void audit(NoStandardEntrustReport report) {
        this.changeStatus(report);
        AuditInfo info = report.getAuditInfo();
        info.setAuditUser(UserUtils.getUser());

        auditInfoService.save(info);

        if(NoStandardReportEnum.TO_BE_APPROVED.getCode().equals(report.getStatus())){
            report = this.getDetail(report.getId());

            //转换pdf文件
            AttachmentInfo attachment = assetInfoService.get(report.getFile());
            if(null != attachment){
                DocConvertor convertor = new AsposeConvertor();
                File targetFile = FileUtils.createTempFile("pdf");
                convertor.run(Global.getConfig("sourceDir")+attachment.getPath(), targetFile.getPath());
                try {
                    String pdfFilePath = fileStore.saveFile(report.getId()+".pdf", FileUtils.readFileToByteArray(targetFile));
                    FileUtils.forceDelete(targetFile);
                    NoStandardEntrustReport newReport = new NoStandardEntrustReport(report.getId());
                    newReport.setPdfFilePath(pdfFilePath);
                    this.updateActive(newReport);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            User user = systemService.getUser(report.getApprovalUserId());
            String assignee = user.getLoginName();

            //审批
            Map<String,Object> model = new HashMap<>();
            model.put("code",report.getEntrustCode());
            model.put("userName",report.getEntrustInfo().getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_REPORT,model);


            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(report.getEntrustId(),
                    info.getReason(),
                    info.getResult()+"",
                    assignee,vars);

            //修改申请单信息
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(report.getEntrustId());
            entrustInfo.setCurAuditUser(user.getId());
            infoDao.updateActive(entrustInfo);

        }else if(NoStandardReportEnum.REJECT.getCode().equals(report.getStatus())){
            //驳回
            report = this.getDetail(report.getId());

            StringBuilder assigneeLoginName = new StringBuilder();
            String[] split = report.getOpUserId().split(",");
            for(int i = 0 ;i<split.length;i++){
                User user = systemService.getUser(split[i]);
                if(null != user){
                    assigneeLoginName.append(user.getLoginName()).append(";");
                }
            }

            //审批
            Map<String,Object> model = new HashMap<>();
            model.put("code",report.getEntrustCode());
            model.put("userName",report.getEntrustInfo().getCreateBy().getName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_REPORT,model);


            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(report.getEntrustId(),
                    info.getReason(),
                    info.getResult()+"",
                    assigneeLoginName.toString(),vars);

            //修改申请单信息
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(report.getEntrustId());
            entrustInfo.setCurAuditUser(report.getOpUserId());
            infoDao.updateActive(entrustInfo);
        }
    }

    @Transactional(readOnly = false)
    public void approval(NoStandardEntrustReport report){
        this.changeStatus(report);
        AuditInfo info = report.getAuditInfo();
        info.setAuditUser(UserUtils.getUser());

        auditInfoService.save(info);

        if(NoStandardReportEnum.APPROVED.getCode().equals(report.getStatus())){
            actTaskService.apiComplete(report.getEntrustId(), info.getReason(), info.getResult()+"", null, null);
            this.file(report);
        }
    }

    public NoStandardEntrustReport getByEntrustId(String id) {
        return super.dao.getByEntrustId(id);
    }

    public void commit(NoStandardEntrustReport report) {
        this.changeStatus(report);
        report = this.dao.getDetail(report.getId());

        String[] split = report.getAuditUserId().split(",");
        StringBuilder assigneeLoginName = new StringBuilder();
        for(int i = 0; i < split.length;i++){
            User user = systemService.getUser(split[i]);
            if(null != user){
                assigneeLoginName.append(user.getLoginName()).append(";");
            }
        }

        //审批
        Map<String,Object> model = new HashMap<>();
        model.put("code",report.getEntrustCode());
        model.put("userName",report.getEntrustInfo().getCreateBy().getName());
        String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_REPORT,model);


        Map<String,Object> vars = new HashMap<>();
        vars.put("message", title);
        actTaskService.apiComplete(report.getEntrustId(),"","",
                assigneeLoginName.toString(),vars);

        //修改申请单信息
        NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(report.getEntrustId());
        entrustInfo.setCurAuditUser(report.getAuditUserId());
        infoDao.updateActive(entrustInfo);
    }

    //归档至数据中心
    public void file (NoStandardEntrustReport report){

        NoStandardEntrustInfo entrustInfo = infoDao.get(report.getEntrustId());
        NoStandardEntrustResource resource = resourceDao.getByEntrustId(report.getEntrustId());
        NoStandardExecution execution = executionDao.getByEntrustId(report.getEntrustId());

        String baseId = IdGen.uuid();
        DataBaseInfo dataBaseInfo = new DataBaseInfo(baseId);
        dataBaseInfo.setIsNewRecord(true);
        dataBaseInfo.setEntrustId(entrustInfo.getId());
        dataBaseInfo.setEntrustCode(entrustInfo.getCode());
        //设置申请单类型
        dataBaseInfo.setEntrustType(EntrustConstants.EntrustType.NO_STANDARD);
        dataBaseInfo.setApplicant(entrustInfo.getClient().getId());
        dataBaseInfo.setApplyDate(entrustInfo.getCreateDate());
        dataBaseInfo.setTestPurpose(entrustInfo.getTestPurpose());
        dataBaseInfo.setEntrustOrg(entrustInfo.getOrg().getId());
        dataBaseInfo.setReportCode(entrustInfo.getCode());
        //dataBaseInfo.setCompany(entrustInfo.getOrg().getCompanyId());
        dataBaseInfo.setLabLeader(entrustInfo.getLabManager().getId());
        dataBaseInfo.setTestLeader(entrustInfo.getTestManagerId());
        dataBaseInfo.setTaskNo(entrustInfo.getTaskNo());
        dataBaseInfo.setPlanNo(entrustInfo.getPlanNo());
        dataBaseInfo.setTestName(entrustInfo.getTaskName());
        dataBaseInfo.setLabId(entrustInfo.getLabInfo().getId());
        //ata章节
        List<NoStandardATAChapter> ataChapterList = entrustInfo.getATAChapterList();
        StringBuilder ataIds = new StringBuilder();
        if(!Collections3.isEmpty(ataChapterList)){
            for(NoStandardATAChapter ataChapter : ataChapterList){
                if(StringUtils.isNoneBlank(ataChapter.getAtaId())){
                    ataIds.append(ataChapter.getAtaId()).append(",");
                }
            }
            if(ataIds.length() > 0){
                ataIds.deleteCharAt(ataIds.length()-1);
            }
        }
        dataBaseInfo.setAtaChapter(ataIds.toString());
        dataBaseInfo.setTestNature(entrustInfo.getTestNature());
        dataBaseInfo.setProductModel(entrustInfo.getProduct());
        dataBaseInfo.setTestName(entrustInfo.getTaskName());
        //大纲类型
        dataBaseInfo.setTestProgramType(resource.getTestOutlineType());
        //大纲文件
        dataBaseInfo.setTestProgram(resource.getTestOutlineFile());
        //大纲编码
        dataBaseInfo.setTestProgramNo(resource.getCurTestOutlineCode());
        //大纲版本id
        dataBaseInfo.setTvId(resource.getTvId());
        //大纲名称
        dataBaseInfo.setTestProgramName(resource.getTestOutlineName());
        //构型基础id
        dataBaseInfo.setConfig(resource.getConfigurationId());
        //构型版本id
        dataBaseInfo.setCvId(resource.getCvId());
        //构型名称
        dataBaseInfo.setConfigName(resource.getConfigCurVersion().getBaseConfiguration().getName());

        //试验项目
        List<NoStandardExecutionItem> testItemList = execution.getTestItemList();
        List<DataTestInfo> newTestItemList = new ArrayList<>();
        if(!Collections3.isEmpty(testItemList)){
            for(NoStandardExecutionItem testItem : testItemList){
                if(null != testItem.getTestItem()){
                    DataTestInfo testInfo = new DataTestInfo();
                    testInfo.setBaseId(baseId);
                    testInfo.setTestItemId(testItem.getTestItemId());
                    testInfo.setTestItemName(testItem.getTestItem().getName());
                    testInfo.setLabLeader(entrustInfo.getLabManager().getId());
                    testInfo.setTestLeader(entrustInfo.getTestManagerId());
                    testInfo.setTester(execution.getOpUser());

                    //试验日志
                    List<NoStandardTestLog> testLogList = execution.getTestLogList();
                    StringBuilder logId = new StringBuilder();
                    if(!Collections3.isEmpty(testLogList)){
                        for(NoStandardTestLog log : testLogList){
                            logId.append(log.getId()).append(",");
                        }
                        if(logId.length() > 0){
                            logId.deleteCharAt(logId.length() - 1);
                        }
                    }
                    //试验日志
                    testInfo.setTestLog(logId.toString());
                    //原始记录
                    testInfo.setOriginalRecord(testItem.getReport());
                    testInfo.setAudioData(testItem.getAudioData());
                    testInfo.setVideoData(testItem.getVideoData());
                    //试验数据（处理后）
                    testInfo.setTestData(testItem.getFile());
                    testInfo.setImgData(testItem.getImg());

                    newTestItemList.add(testInfo);
                }
            }
        }

        dataBaseInfo.setTestInfoList(newTestItemList);
        dataBaseInfoService.saveFromOther(dataBaseInfo);
    }

    public void updateActive(NoStandardEntrustReport report) {
        this.dao.updateActive(report);
    }
}
