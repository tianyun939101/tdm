package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.dataCenter.DataBaseInfoDao;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.dataCenter.*;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.h2.mvstore.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class DataServerInfoService extends CrudService<DataBaseInfoDao, DataBaseInfo> {

    @Autowired
    private DataBasePermService dataBasePermService;
    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private DataTestInfoService dataTestInfoService;
    @Autowired
    private ATAChapterService ataChapterService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private AttachmentInfoService attachmentInfoService;

    @Autowired
    DcServerProvideService dcServerProvideService;

    @Autowired
    private AssetInfoService assetInfoService;
    @Autowired
    private FileStore fileStore;
    @Autowired
    private TestInfoAttrValueService testInfoAttrValueService;

    private static String DATA_REPORT_TMP_PATH = Global.getCreateFilePath()+"/dataReport/";

    private static String DATA_REPORT_TMP_PATH1 ="E:/app/dataReport/";

    @Override
    @Transactional(readOnly = true)
    public DataBaseInfo get(String baseId){
        DataBaseInfo dataBaseInfo = super.dao.get(baseId);
        TestInfoAttrValue testInfoAttrValue = new TestInfoAttrValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List <DataTestInfo>  list=dataTestInfoService.getByBaseId(baseId);
        dataBaseInfo.setTestInfoList(list);
        String startDate="";
        String  endDate="";
        String  timeRange="";
        //????????????????????????
        if(!CollectionUtils.isEmpty(list)){
            for(DataTestInfo dataTestInfo:dataBaseInfo.getTestInfoList()){
                List<String> orList = new ArrayList<>();
                List<String> tdList = new ArrayList<>();
                List<String> tlList = new ArrayList<>();
                List<String> vdList = new ArrayList<>();
                List<String> adList = new ArrayList<>();
                List<String> imList = new ArrayList<>();
                testInfoAttrValue.setTestInfoId(dataTestInfo.getId());
                if(null !=dataTestInfo.getStartDate()){
                     startDate=dateFormat.format(dataTestInfo.getStartDate());
                }
                if(null !=dataTestInfo.getEndDate()){
                    endDate=dateFormat.format(dataTestInfo.getEndDate());
                }
               if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
                   timeRange=startDate+"-"+endDate;
                   dataTestInfo.setTimeRange(timeRange);
               }
                List<TestInfoAttrValue> fields = testInfoAttrValueService.findList(testInfoAttrValue);
                dataTestInfo.setFields(fields);
                for(DataInfo dataInfo:dataTestInfo.getDatalist()){
                    switch (dataInfo.getDataType()==null?"0":dataInfo.getDataType()){
                        case DataCenterConstants.DataType.ORIGINAL_RECORD://???????????????
                            orList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.TEST_DATA://????????????
                            tdList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.TEST_LOG://????????????
                            tlList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.VIDEO://????????????
                            vdList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.AUDIO://????????????
                            adList.add(dataInfo.getTestData());
                            break;
                        case DataCenterConstants.DataType.IMAGE://????????????
                            imList.add(dataInfo.getTestData());
                            break;
                        default:
                            break;
                    }
                }
                dataTestInfo.setOriginalRecord(StringUtils.join(orList,","));
                dataTestInfo.setTestData(StringUtils.join(tdList,","));
                dataTestInfo.setTestLog(StringUtils.join(tlList,","));
                dataTestInfo.setVideoData(StringUtils.join(vdList,","));
                dataTestInfo.setAudioData(StringUtils.join(adList,","));
                dataTestInfo.setImgData(StringUtils.join(imList,","));
            }
        }
        //??????ATA????????????
        String ataChapter = dataBaseInfo.getAtaChapter();
        if(StringUtils.isNotBlank(ataChapter)){
            List<ATAChapter> ataList = new ArrayList<>();
            String[] ataArr = ataChapter.split(",");
            for(String ataId:ataArr){
                ATAChapter ata = ataChapterService.get(ataId);
                if(ata!=null){
                    ataList.add(ata);
                }
            }
            dataBaseInfo.setAtaList(ataList);
        }
        return dataBaseInfo;
    }

    private static String getCode(){
        Date date = new Date();
        String format = new SimpleDateFormat("yyyyMMdd").format(date);
        String time = date.getTime()+"";
        String suffix = time.substring(time.length() - 2);
        String e1 = time.substring(time.length()-4,time.length()-3);
        String e2 = time.substring(time.length()-3,time.length()-2);
        String result = Integer.parseInt(e1) * Integer.parseInt(e2)+"";

        return format+result+suffix;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(DataBaseInfo dataBaseInfo){
        Boolean isNewRecord = false;
        if(StringUtils.isBlank(dataBaseInfo.getId())){
            isNewRecord = true;
        }
        //??????????????????????????????
        dataBaseInfo.setEntrustType(DataCenterConstants.EntrustType.UPLOAD);//??????????????????1
        dataBaseInfo.setEntrustCode(getCode());//????????????
        dataBaseInfo.setReportCode(dataBaseInfo.getEntrustCode());
        dataBaseInfo.setTestLeader(UserUtils.getUser().getId());//????????????????????????????????????
        dataBaseInfo.setApplicant(UserUtils.getUser().getId());//??????????????????????????????
        dataBaseInfo.setApplyDate(new Date());//????????????????????????
        DcServerProvide  d=dcServerProvideService.selectListInfo(UserUtils.getUser().getLoginName());
        dataBaseInfo.setProviderId(d.getId());
        dataBaseInfo.setProviderContact(d.getName());
        super.save(dataBaseInfo);
        //????????????????????????
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
            test.setBaseId(dataBaseInfo.getId());
            dataInfoService.deleteByTestId(test.getId());
            test.setId("");
            //??????????????????????????????
            /*if(StringUtils.isNotBlank(test.getId())){
                test.setIsNewRecord(true);
            }*/
            dataTestInfoService.save(test);
            saveTestData(test);
            //??????????????????????????????
            testInfoAttrValueService.saveAttrValues(test);


        }
        //??????????????????????????????????????????
        if(isNewRecord){
            try{
                DataBasePerm dataBasePerm = new DataBasePerm();
                dataBasePerm.setBaseId(dataBaseInfo.getId());
                dataBasePerm.setAuthorizationId(UserUtils.getUser().getId());
                dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.REPORT);//???????????? 1???????????? 2?????????????????????
                dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//???????????? 1?????? 2 ??????
                dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);//???????????? 1?????? 2?????????
                dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//????????????????????????
                dataBasePermService.save(dataBasePerm);
            }catch (Exception  e){
                e.printStackTrace();
            }

        }
    }

    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveInfo(DataBaseInfo dataBaseInfo){
        Boolean isNewRecord = false;
        if(StringUtils.isBlank(dataBaseInfo.getId())){
            isNewRecord = true;
        }
        //??????????????????????????????
        dataBaseInfo.setEntrustType(DataCenterConstants.EntrustType.UPLOAD);//??????????????????1
        dataBaseInfo.setEntrustCode(getCode());//????????????
        dataBaseInfo.setReportCode(dataBaseInfo.getEntrustCode());
        dataBaseInfo.setTestLeader(UserUtils.getUser().getId());//????????????????????????????????????
        dataBaseInfo.setApplicant(UserUtils.getUser().getId());//??????????????????????????????
        dataBaseInfo.setApplyDate(new Date());//????????????????????????
        super.save(dataBaseInfo);
        //????????????????????????
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
            test.setBaseId(dataBaseInfo.getId());
            dataInfoService.deleteByTestId(test.getId());
            test.setId("");
            //??????????????????????????????
            /*if(StringUtils.isNotBlank(test.getId())){
                test.setIsNewRecord(true);
            }*/
            dataTestInfoService.save(test);
            saveTestData(test);
            //??????????????????????????????
            testInfoAttrValueService.saveAttrValues(test);


        }
        //??????????????????????????????????????????
        if(isNewRecord){
            try{
                DataBasePerm dataBasePerm = new DataBasePerm();
                dataBasePerm.setBaseId(dataBaseInfo.getId());
                dataBasePerm.setAuthorizationId(UserUtils.getUser().getId());
                dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.REPORT);//???????????? 1???????????? 2?????????????????????
                dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//???????????? 1?????? 2 ??????
                dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);//???????????? 1?????? 2?????????
                dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//????????????????????????
                dataBasePermService.save(dataBasePerm);
            }catch (Exception  e){
                e.printStackTrace();
            }

        }
    }
    /**
     * ??????????????????
     */
    private void saveTestData(DataTestInfo test) {
        //???????????????
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.ORIGINAL_RECORD,test.getOriginalRecord());
        //???????????????????????????
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.TEST_DATA,test.getTestData());
        //????????????
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.TEST_LOG,test.getTestLog());
        //????????????
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.VIDEO,test.getVideoData());
        //????????????
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.AUDIO,test.getAudioData());
        //????????????
        saveTestDataByType(test.getId(),DataCenterConstants.DataType.IMAGE,test.getImgData());
    }


    private void saveTestDataByType(String testId,String dataType, String tsetData){
        if(StringUtils.isBlank(tsetData)){
            return;
        }
        String[] arr = tsetData.split(",");
        for(String fileId:arr){
            DataInfo data = new DataInfo();
            data.setTestInfoId(testId);
            data.setDataType(dataType);
            data.setTestData(fileId);
            dataInfoService.save(data);
        }
    }
    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(DataBaseInfo dataBaseInfo){
        DataBaseInfo baseInfo = super.dao.get(dataBaseInfo.getId());
        //??????????????????????????????
        for(DataTestInfo test :baseInfo.getTestInfoList()){
            DataTestInfo dataTestInfo = new DataTestInfo();
            dataTestInfo.setId(test.getId());
            dataTestInfoService.delete(dataTestInfo);
            dataInfoService.deleteByTestId(test.getId());
        }
        //????????????????????????
        dataTestInfoService.deleteByBaseId(baseInfo.getId());
        //??????????????????
        dataBasePermService.detetePermByBaseId(baseInfo.getId());
        //??????????????????
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setBusinessKey(dataBaseInfo.getId());
        auditInfoService.delete(auditInfo);
        //??????????????????????????????
        super.delete(baseInfo);
    }

    /**
     * ??????????????????
     * @param dataBaseInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveAuthority(DataBaseInfo dataBaseInfo) {
        //???????????????????????????
        dataBasePermService.detetePermByBaseId(dataBaseInfo.getId());
        String reportUserStr = dataBaseInfo.getReportUserStr();
        String reportOfficeStr = dataBaseInfo.getReportOfficeStr();
        String searchUserStr = dataBaseInfo.getSearchUserStr();
        String searchOfficeStr = dataBaseInfo.getSearchOfficeStr();
        Date date = dataBaseInfo.getSearchEndDate();
        saveAuthorityByStr(dataBaseInfo.getId(),reportUserStr,DataCenterConstants.PermissionType.REPORT,DataCenterConstants.AuthorizationType.PERSON,null);
        saveAuthorityByStr(dataBaseInfo.getId(),reportOfficeStr,DataCenterConstants.PermissionType.REPORT,DataCenterConstants.AuthorizationType.ORG,null);
        saveAuthorityByStr(dataBaseInfo.getId(),searchUserStr,DataCenterConstants.PermissionType.SEARCH,DataCenterConstants.AuthorizationType.PERSON,date);
        saveAuthorityByStr(dataBaseInfo.getId(),searchOfficeStr,DataCenterConstants.PermissionType.SEARCH,DataCenterConstants.AuthorizationType.ORG,date);
    }

    private void saveAuthorityByStr(String baseId,String str,String permType, String authorizationType,Date date){
        if(StringUtils.isBlank(str)){
            return;
        }
        String[] arr = str.split(",");
        for(String authorizationId:arr){
            DataBasePerm dataBasePerm = new DataBasePerm();
            dataBasePerm.setBaseId(baseId);
            dataBasePerm.setPermissionType(permType);
            dataBasePerm.setAuthorizationId(authorizationId);
            dataBasePerm.setAuthorizationType(authorizationType);
            dataBasePerm.setEndDate(date);
            dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//????????????????????????
            dataBasePermService.save(dataBasePerm);
        }
    }
    /**
     * ??????????????????ID????????????????????????
     * @param dataBaseInfo
     * @return
     */
    @Transactional(readOnly = true)
    public DataBaseInfo getAuthorityByBaseId(DataBaseInfo dataBaseInfo) {
        List<User> reportUser = new ArrayList<>();//????????????????????????
        List<Office> reportOffice = new ArrayList<>();;//???????????????????????????
        List<User> searchUser = new ArrayList<>();;//????????????????????????
        List<Office> searchOffice = new ArrayList<>();;//???????????????????????????
        Date searchEndDate = null;//????????????????????????
        //????????????????????????
        List<DataBasePerm> userPermList = dataBasePermService.getUserPermByBaseId(dataBaseInfo.getId());
        for(DataBasePerm data:userPermList){
            if("1".equals(data.getPermissionType())){//????????????
                searchUser.add(data.getUser());
                searchEndDate = data.getEndDate();//????????????????????????????????????
            }else if("2".equals(data.getPermissionType())){//????????????
                reportUser.add(data.getUser());
            }
        }
        //????????????????????????
        List<DataBasePerm> officePermList = dataBasePermService.getOfficePermByBaseId(dataBaseInfo.getId());
        for(DataBasePerm data:officePermList){
            if("1".equals(data.getPermissionType())){//????????????
                searchOffice.add(data.getOffice());
            }else if("2".equals(data.getPermissionType())){//????????????
                reportOffice.add(data.getOffice());
            }

        }
        dataBaseInfo.setReportUser(reportUser);
        dataBaseInfo.setReportOffice(reportOffice);
        dataBaseInfo.setSearchUser(searchUser);
        dataBaseInfo.setSearchOffice(searchOffice);
        dataBaseInfo.setSearchEndDate(searchEndDate);

        return dataBaseInfo;
    }

    /**
     *
     * @param dataBaseInfo
     */
    public void saveFromOther(DataBaseInfo dataBaseInfo) {
        super.save(dataBaseInfo);
        //????????????????????????
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
            test.setBaseId(dataBaseInfo.getId());
            dataTestInfoService.save(test);
            //??????????????????????????????
            dataInfoService.deleteByTestId(test.getId());
            saveTestData(test);
        }
        //????????????????????????
        dataBasePermService.save(setDataBasePerm(dataBaseInfo.getId(),dataBaseInfo.getApplicant())); //?????????
        if(!dataBaseInfo.getApplicant().equals(dataBaseInfo.getTestLeader())){
            dataBasePermService.save(setDataBasePerm(dataBaseInfo.getId(),dataBaseInfo.getTestLeader()));//???????????????
        }
//        for(DataTestInfo test:dataBaseInfo.getTestInfoList()){
            DataTestInfo test = dataBaseInfo.getTestInfoList().get(0);
            if(StringUtils.isNotBlank(test.getTester())){
                String[] split = test.getTester().split(",");
                for(String s : split){
                    if(StringUtils.isNotBlank(s)){
                        if(!s.equals(dataBaseInfo.getApplicant()) || !s.equals(dataBaseInfo.getTestLeader())) {
                            dataBasePermService.save(setDataBasePerm(dataBaseInfo.getId(), s));//?????????
                        }
                    }
                }

//            }
        }

    }

    private DataBasePerm setDataBasePerm(String baseId, String personId) {
        DataBasePerm dataBasePerm = new DataBasePerm();
        dataBasePerm.setBaseId(baseId);
        dataBasePerm.setAuthorizationId(personId);
        dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.SEARCH);//???????????? 1???????????? 2?????????????????????
        dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//???????????? 1?????? 2 ??????
        dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);// 1??????2?????????
        dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//????????????????????????
        return dataBasePerm;
    }

    public List<DataBaseInfo> findAllList(DataBaseInfo dataBaseInfo){
        return super.dao.findAllList(dataBaseInfo);
    }





    /**
     * ?????????????????????
     * @param page ????????????
     * @param entity
     * @return
     */
    public Page<DataBaseInfo> findProviderPage(Page<DataBaseInfo> page, DataBaseInfo entity) {
        entity.setPage(page);
        page.setList(super.dao.findProviderList(entity));
        return page;
    }


    /**
     * ???????????????
     * @param dataBaseInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void saveByProvider(DataBaseInfo dataBaseInfo){
        //??????????????????????????????
        dataBaseInfo.setEntrustType(DataCenterConstants.EntrustType.PROVIDER);//???????????????
        this.dao.updateByProvider(dataBaseInfo);//?????????????????????ID
        //????????????????????????
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());
        for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
            test.setBaseId(dataBaseInfo.getId());
            dataTestInfoService.save(test);
            //??????????????????????????????
            dataInfoService.deleteByTestId(test.getId());
            saveTestData(test);
        }

    }

    public Page<DataBaseInfo> findBasePage(Page<DataBaseInfo> dataBaseInfoPage, DataBaseInfo dataBaseInfo) {
        dataBaseInfo.setPage(dataBaseInfoPage);
        dataBaseInfoPage.setList(dao.findBaseList(dataBaseInfo));
        return dataBaseInfoPage;
    }

    /**
     * ??????????????????
     * @param dataBaseInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void applyPermission(DataBaseInfo dataBaseInfo,String reason) {
        //??????????????????
        User apply = UserUtils.getUser();
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setBusinessKey(dataBaseInfo.getId()+","+apply.getId());
        auditInfo.setReason(reason);
        auditInfo.setResult(1);
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfoService.save(auditInfo);
        //????????????
        User audit = labInfoService.get(dataBaseInfo.getLabId()).getLeader();
        String taskTile = dataBaseInfo.getCreateBy().getName() +"???????????????????????????"+dataBaseInfo.getEntrustCode()+"????????????";;
        Map<String,Object> model = new HashMap<>();
        model.put("userName",dataBaseInfo.getCreateBy().getName());
        model.put("code",dataBaseInfo.getTestName()==null?"":dataBaseInfo.getTestName());
        String title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate.DATA_REPORT_APPLY,model);

        Map<String,Object> vars = new HashMap<>();
        vars.put("message", title);
        vars.put("code", dataBaseInfo.getEntrustCode()==null?"":dataBaseInfo.getEntrustCode());
        actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.DATA_REPORT,
                audit.getLoginName(),dataBaseInfo.getId()+","+apply.getId(),taskTile,vars);

    }

    /**
     * ??????
     * @param
     * @param auditInfo
     */
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void audit(String id, AuditInfo auditInfo) {
        String[] arr = id.split(",");
        String baseId = arr[0];
        String userId = arr[1];
        DataBaseInfo dataBaseInfo = super.dao.get(baseId);
        Map<String, Object> model = new HashMap<>();
        model.put("code", dataBaseInfo.getEntrustCode()==null?"":dataBaseInfo.getEntrustCode());
        User createUser = systemService.getUser(dataBaseInfo.getCreateById());
        model.put("userName", createUser.getName());
        String title = "";
        if (dataBaseInfo != null) {
            //????????????
            if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
                auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason()) ? "??????" : auditInfo.getReason());
                title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate.AUDIT_PASS, model);
                //??????????????????
                DataBasePerm dataBasePerm = new DataBasePerm();
                dataBasePerm.setBaseId(baseId);
                dataBasePerm.setAuthorizationId(userId);
                dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.SEARCH);//???????????? 1???????????? 2?????????????????????
                dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//???????????? 1?????? 2 ??????
                dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);// 1??????2?????????
                dataBasePerm.setAuthorizer(UserUtils.getUser().getId());//????????????????????????
                dataBasePermService.save(dataBasePerm);
            }
            //???????????????
            if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
                title = FreeMarkers.renderString(DataCenterConstants.MessageTemplate.AUDIT_RETURN, model);
            }
            //??????????????????
            auditInfo.setBusinessKey(id);
            //auditInfo.setType(EntrustConstants.AuditType.ENTRUST_AUDIT);
            auditInfo.setAuditUser(UserUtils.getUser());
            auditInfoService.save(auditInfo);
            //??????
            Map<String, Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(id,auditInfo.getReason(),auditInfo.getResult()+"",null,null);
        }
    }

    public List<DataBaseInfo> findByLabId(DataBaseInfo dataBaseInfo){
        return this.dao.findByLabId(dataBaseInfo);
    }


    /**??????????????????
     * @Describe:
     * @Author:WuHui
     * @Date:16:44 2020/9/18
     * @param dataBaseInfos
     * @return:java.lang.String
    */
    public String buildDataReport(List<DataBaseInfo> dataBaseInfos){
        //??????????????????
        String basePath = System.getProperty("java.io.tmpdir")+ File.separator + System.currentTimeMillis() + "/";
        for(DataBaseInfo dataBaseInfo: dataBaseInfos){
            String taskPath = basePath + dataBaseInfo.getReportCode() ;
            //?????????????????????
            File baseFile = new File(taskPath);
            if(!baseFile.exists()) {
                baseFile.mkdirs(); //????????????
            }
            //??????xml??????
            DataServerXml dataInfo = new DataServerXml(dataBaseInfo);
            //????????????
            if(!StringUtils.isBlank(dataBaseInfo.getTestProgram())){
                DataServerXml.Data data = this.buildReportDataXml(dataBaseInfo.getTestProgram(),taskPath,null);
                if(data != null)  dataInfo.setOutline(data);
            }
            //????????????
            if(!StringUtils.isBlank(dataBaseInfo.getConfig())){
                DataServerXml.Data data = this.buildReportDataXml(dataBaseInfo.getConfig(),taskPath,null);
                if(data != null) dataInfo.setConfig(data);
            }
            //???????????????
            if(!StringUtils.isBlank(dataBaseInfo.getTaskNum())){
                DataServerXml.Data data = this.buildReportDataXml(dataBaseInfo.getTaskNum(),taskPath,null);
                if(data != null) dataInfo.setTaskNum(data);
            }
            //??????????????????
            if(!StringUtils.isBlank(dataBaseInfo.getTestReport())){
                DataServerXml.Data data = this.buildReportDataXml(dataBaseInfo.getTestReport(),taskPath,null);
                if(data != null) dataInfo.setTestReport(data);
            }
            //????????????
            List<DataServerXml.Task> tasks = new ArrayList<>();
            for(DataTestInfo info: dataBaseInfo.getTestInfoList()){
                String dataPath = taskPath + "/" + info.getId();
                List<DataServerXml.Data> datas = new ArrayList<>();
                for(DataInfo di:info.getDatalist()){
                    DataServerXml.Data data = this.buildReportDataXml(di.getTestData(),dataPath,di.getDataType());
                    if(data != null) datas.add(data);
                }
                DataServerXml.Task task = new DataServerXml.Task(info,datas);
                tasks.add(task);
            }
            dataInfo.setTask(tasks);
            //??????xml??????
            taskPath = basePath + dataBaseInfo.getReportCode() + "/" + dataBaseInfo.getReportCode() +".xml";
            JaxbUtil.createXml(dataInfo,taskPath,"utf-8");
        }

        //????????????
       //String zipPath = "/Users/zhengweiming/Downloads/"+System.currentTimeMillis()+".zip";
        String zipPath = DATA_REPORT_TMP_PATH1+System.currentTimeMillis()+".zip";
        basePath = basePath.substring(0,basePath.length()-1);
        FileUtils.zipFiles(basePath,"",zipPath);
        //??????????????????
        FileUtils.deleteDirectory(basePath);
        //????????????????????????
        return zipPath;
    }

    /**
     * @Describe:???????????? ????????????
     * @Author:WuHui
     * @Date:14:58 2020/9/18
     * @param attachId
     * @param type
     * @return:com.demxs.tdm.domain.dataCenter.DataReportXml.Data
    */
    private DataReportXml.Data buildReportData(String attachId,String path,String type){
        AttachmentInfo attach = attachmentInfoService.get(attachId);
        DataReportXml.Data data = null;
        //??????????????????
        if(attach!=null){
            String target = path + "/" + attach.getName();
            String name = attach.getName();
            data =  new DataReportXml.Data(name,name.substring(0,name.lastIndexOf(".")));
            if(!StringUtils.isBlank(type)){
                data.setType(type);
            }
            String attachPath = Global.getCreateFilePath() + attach.getPath();
            InputStream is =  fileStore.downloadFileToStream(attachPath);
            //????????????
            try {
                File file = new File(target);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                IOUtil.writeByteToFile(is,target);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * @Describe:???????????? ????????????
     * @Author:WuHui
     * @Date:14:58 2020/9/18
     * @param attachId
     * @param type
     * @return:com.demxs.tdm.domain.dataCenter.DataReportXml.Data
     */
    private DataServerXml.Data buildReportDataXml(String attachId,String path,String type){
        AttachmentInfo attach = attachmentInfoService.get(attachId);
        DataServerXml.Data data = null;
        //??????????????????
        if(attach!=null){
            String target = path + "/" + attach.getName();
            String name = attach.getName();
            data =  new DataServerXml.Data(name,name.substring(0,name.lastIndexOf(".")));
            if(!StringUtils.isBlank(type)){
                data.setType(type);
            }
            String attachPath = Global.getCreateFilePath() + attach.getPath();
            InputStream is =  fileStore.downloadFileToStream(attachPath);
            //????????????
            try {
                File file = new File(target);
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                IOUtil.writeByteToFile(is,target);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * @Describe:??????????????????
     * @Author:WuHui
     * @Date:18:54 2020/9/21
     * @param ziPath
     * @return:void
    */
    public void uploadDataReport(String ziPath){
        List<DataBaseInfo> dataBaseInfos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        //??????????????????
        String basePath = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + "/";
        //???zip?????????????????????
        FileUtils.unZipFiles(ziPath,basePath);
        File folder = new File(basePath);
        //??????????????????
        File[] list = folder.listFiles();
        for (File infoFile : list) {
            String infoPath = "";
            if (infoFile.isDirectory()) {
                infoPath = basePath + infoFile.getName() + "/";
                //??????xml??????
                String filePath = infoPath + infoFile.getName() +".xml";
                DataServerXml dataInfo = JaxbUtil.readXml(filePath,DataServerXml.class);
                if(null !=dataInfo){
                    DataBaseInfo dataBaseInfo = new DataBaseInfo(dataInfo);
                    dataBaseInfo.setLabInfo(labInfoService.get(dataBaseInfo.getLabId()));
                    //??????copy
                    //????????????
                    DataServerXml.Data data = dataInfo.getOutline();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTestProgram(attach.getId());
                    }
                    //????????????
                    data = dataInfo.getConfig();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setConfig(attach.getId());
                    }
                    //????????????
                    data = dataInfo.getTaskNum();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTaskNum(attach.getId());
                    }
                    //??????????????????
                    data = dataInfo.getTestReport();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTestReport(attach.getId());
                    }
                    //????????????
                    String dataFile = "";
                    List<DataTestInfo> tests = new ArrayList<>();

                    for(DataServerXml.Task task : dataInfo.getTask()){
                        dataFile = infoPath + task.getId();
                        DataTestInfo test = new DataTestInfo(task);
                        test.setCurrentUser(UserUtils.getUser());
                        test.setCreateDate(now);

                        if(StringUtils.isNotEmpty(test.getTimeRange())){
                          int  s=  getIndex(test.getTimeRange(),"-",3);
                          String start=test.getTimeRange().substring(0,s).trim();
                          String end=test.getTimeRange().substring(s+1,test.getTimeRange().length()).trim();
                          try{
                              test.setStartDate(sdf.parse(start));
                              test.setEndDate(sdf.parse(end));
                          }catch (Exception e){
                              e.printStackTrace();
                          }

                        }
                        test.setUpdateBy(UserUtils.getUser());
                        test.setUpdateDate(now);
                        test.setTestItemName(task.getTestItemName());
                        if(CollectionUtils.isNotEmpty(task.getData())){
                            for(DataServerXml.Data dat :task.getData()){
                                filePath = dataFile + "/"+ dat.getPath();
                                AttachmentInfo attach = this.saveDataReportAttach(filePath,dat.getPath());
                                //dataBaseInfo.setTestProgram(attach.getId());
                                switch (dat.getType()){
                                    case DataCenterConstants.DataType.ORIGINAL_RECORD:
                                        test.setOriginalRecord(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.TEST_DATA:
                                        test.setTestData(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.TEST_LOG:
                                        test.setTestLog(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.VIDEO:
                                        test.setVideoData(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.AUDIO:
                                        test.setAudioData(attach.getId());
                                        break;
                                    case DataCenterConstants.DataType.IMAGE:
                                        test.setImgData(attach.getId());
                                        break;
                                }
                            }
                        }
                        tests.add(test);
                    }
                    dataBaseInfo.setTestInfoList(tests);
                    dataBaseInfo.setCurrentUser(UserUtils.getUser());
                    dataBaseInfo.setCreateDate(now);
                    dataBaseInfo.setUpdateBy(UserUtils.getUser());
                    dataBaseInfo.setUpdateDate(now);
                    //?????????????????????
                    this.saveInfo(dataBaseInfo);
                }

            }
        }
        //?????????????????????
        //FileUtils.deleteDirectory(basePath);
        //??????????????????
        FileUtils.deleteFile(ziPath);
    }


    private AttachmentInfo saveDataReportAttach(String path,String fileName){
        File file = new File(path);
        //???????????????????????????ftp?????????
        path =  DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+"/"+fileName;
        fileStore.saveFile(file);
        //??????????????????
        AttachmentInfo attachment = assetInfoService.save(fileName, path, file.length());
        return attachment;
    }

    /**
     * @Describe:??????????????? ???????????? ????????????
     * @Author:WuHui
     * @Date:16:42 2020/9/23
     * @param
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String,Object>> findLabBaseInfoTree(DataBaseInfo dataBaseInfo){
        return dao.findLabBaseInfoTree(dataBaseInfo);
    }

    /**
     * @Describe: ATA ???????????? ????????????
     * @Author:WuHui
     * @Date:14:29 2020/9/24
     * @param dataBaseInfo
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String,Object>> findATADatatree(DataBaseInfo dataBaseInfo){
        return dao.findATABaseInfoTree(dataBaseInfo);
    }

    /**
     * @Describe:??????????????????????????????
     * @Author:WuHui
     * @Date:14:35 2020/9/24
     * @param dataBaseInfo
     * @return:boolean
    */
    public boolean isSearchData(DataBaseInfo dataBaseInfo){
        boolean enable = false;
        User user = UserUtils.getUser();
        List<DataBasePerm> perms = dataBasePermService.findPermByBaseId(dataBaseInfo.getId(),user);
        for(DataBasePerm perm : perms){
            if(DataCenterConstants.PermissionType.SEARCH.equals(perm.getPermissionType())){
                //?????????????????????????????????????????????
                if(perm.getAuthorizationScope() != null
                        && DataCenterConstants.AuthorizationScope.PERIOD.equals(perm.getAuthorizationScope())){
                    Date now = new Date();
                    enable = now.before(perm.getEndDate());
                }else{
                    enable = true;
                }
            }else if(DataCenterConstants.PermissionType.REPORT.equals(perm.getPermissionType())){
                //????????????????????????
                enable = true;
                break;
            }
        }
        return enable;
    }

    /**
     * @Describe:????????????????????????
     * @Author:WuHui
     * @Date:14:35 2020/9/24
     * @param dataBaseInfo
     * @return:boolean
    */
    public boolean isOperateData(DataBaseInfo dataBaseInfo){
        boolean enable = false;
        User user = UserUtils.getUser();
        List<DataBasePerm> perms = dataBasePermService.findPermByBaseId(dataBaseInfo.getId(),user);
        for(DataBasePerm perm : perms){
            if(DataCenterConstants.PermissionType.REPORT.equals(perm.getPermissionType())){
                //????????????????????????
                enable = true;
                break;
            }
        }
        return enable;
    }


    /**
     * @Describe:????????????????????????
     * @Author:WuHui
     * @Date:9:31 2020/9/27
     * @param user
     * @return:boolean
    */
    public void addOperateData(User user,String baseId){
        List<DataBasePerm> perms = dataBasePermService.findPermByBaseId(baseId,user);
        for(DataBasePerm perm:perms){
            if(DataCenterConstants.PermissionType.REPORT.equals(perm.getPermissionType())){
                return;
            }
        }
        DataBasePerm perm = new DataBasePerm();
        perm.setBaseId(baseId);
        perm.setPermissionType(DataCenterConstants.PermissionType.REPORT);
        perm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);
        perm.setAuthorizationId(user.getId());
        perm.setCreateBy(UserUtils.getUser());
        perm.setCreateDate(new Date());
        perm.setUpdateBy(UserUtils.getUser());
        perm.setUpdateDate(new Date());
        perm.setAuthorizer(UserUtils.getUser().getId());
        dataBasePermService.save(perm);
    }

    public Page<DataBaseInfo> findListPage(Page<DataBaseInfo> page, DataBaseInfo entity) {
        entity.setPage(page);
        page.setList(dao.findAllBaseList(entity));
        return page;
    }

    public  int getIndex(String str, String s,int j){
        int  x=str.indexOf(str);
        for(int i=0;i<j;i++){
            x=str.indexOf(s,x+1);
        }
        return x;
    }
}
