package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardTestLogDao;
import com.demxs.tdm.dao.dataCenter.DataBaseInfoDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestLog;
import com.demxs.tdm.domain.dataCenter.*;
import com.demxs.tdm.domain.lab.SubCenter;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.business.nostandard.NoStandardTestCheckService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.lab.SubCenterService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.sys.DictService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import com.demxs.tdm.web.dataCenter.webservice.WordForPDFUtils;
import net.sf.json.JSONObject;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.axis.client.Call;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class DataBaseInfoService extends CrudService<DataBaseInfoDao, DataBaseInfo> {

    private Logger  log= LoggerFactory.getLogger(DataBaseInfoService.class);

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
    private TaskService taskService;
    @Autowired
    private AttachmentInfoService attachmentInfoService;
    @Autowired
    private AssetInfoService assetInfoService;
    @Autowired
    private FileStore fileStore;
    @Autowired
    private TestInfoAttrValueService testInfoAttrValueService;
    @Autowired
    private OfficeService officeService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuditInfoDao auditInfoDao;
    @Autowired
    private NoStandardTestCheckService testCheckService;

    @Autowired
    private  DataBaseInfoDao  dataBaseInfoDao;

    @Autowired
    private DictService dictService;


    @Autowired
    private NoStandardTestLogDao noStandardTestLogDao;


    @Autowired
    DataInterfaceService dataInterfaceService;

    @Autowired
    private ShebeiDao shebeiDao;

    @Autowired
    SubCenterService  subCenterService;




    private final static String ROOT_PATH = "D:\\temp\\temp";
   // private final static String ROOT_PATH_TEXT = "E:\\testTdm";

    private static String DATA_REPORT_TMP_PATH = Global.getCreateFilePath()+"/dataReport/";

    private static final String BASE_DIR = Global.getConfig("sourceDir");


    @Transactional(readOnly = true)
    public DataBaseInfo get(String baseId){
        DataBaseInfo dataBaseInfo = super.dao.get(baseId);
        if(dataBaseInfo == null) return null;
        TestInfoAttrValue testInfoAttrValue = new TestInfoAttrValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //??????????????????
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
        User user=UserUtils.getUser();
        //??????????????????????????????
        dataBaseInfo.setEntrustType(DataCenterConstants.EntrustType.UPLOAD);//??????????????????1
        dataBaseInfo.setEntrustCode(getCode());//????????????
        dataBaseInfo.setReportCode(dataBaseInfo.getReportCode());
        if(UserUtils.getUser().getId() !=null){
            dataBaseInfo.setTestLeader(UserUtils.getUser().getId());//????????????????????????????????????
            dataBaseInfo.setApplicant(UserUtils.getUser().getId());//??????????????????????????????
        }else{
            user=systemService.getUser(dataBaseInfo.getApplicant());
            dataBaseInfo.setTestLeader(user.getId());//????????????????????????????????????
            dataBaseInfo.setApplicant(user.getId());//??????????????????????????????
        }

        dataBaseInfo.setApplyDate(new Date());//????????????????????????
        dataBaseInfo.setInsertUser(UserUtils.getUser().getName());
        if(dataBaseInfo.getAuditUser() != null){
            dataBaseInfo.setAuditUserId(dataBaseInfo.getAuditUser().getId());
        }
        super.save(dataBaseInfo);
        //????????????????????????
        dataTestInfoService.deleteByBaseId(dataBaseInfo.getId());   //??????????????????
        if(dataBaseInfo.getTestInfoList() != null){
            for(DataTestInfo test :dataBaseInfo.getTestInfoList()){
                test.setBaseId(dataBaseInfo.getId());
                dataInfoService.deleteByTestId(test.getId());   //??????????????????
                test.setId("");
                //??????????????????????????????
                dataTestInfoService.save(test);
                saveTestData(test);
                //??????????????????????????????
                testInfoAttrValueService.saveAttrValues(test);
            }
        }

        //????????????????????????????????????
        NoStandardTestCheck testCheck = testCheckService.getTestCheckByReportId(dataBaseInfo.getId());
        if(testCheck != null && "1".equals(dataBaseInfo.getIsFlag())){//?????????????????????
            String auditId = dataBaseInfo.getAuditUser().getId();
            ApproveDTO approveDto = new ApproveDTO(testCheck.getId(),"",auditId);
            testCheckService.approve(approveDto);
        }else if("1".equals(dataBaseInfo.getIsFlag())){//??????????????????
            dataBaseInfo.setInsertUser(UserUtils.getUser().getName());
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(dataBaseInfo.getId()).active().singleResult();
            User createBy = UserUtils.getUser();
            User auditUser = userDao.get(dataBaseInfo.getAuditUser());
            if(null == task){
                User  ss=userDao.get(dataBaseInfo.getUpdateBy());
                Map<String, Object> map = new HashMap<String, Object>();
                List<String> auditList = new ArrayList<>();

                if(null !=auditUser){
                    Map<String,Object> model = new HashMap<>(1);
                    model.put("userName",createBy.getName());
                    String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);
                    Map<String,Object> vars = new HashMap<>(1);
                    vars.put("message", title);
                    actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.DATA_PROCESS,
                            auditUser.getLoginName(), dataBaseInfo.getId(), title, vars);
                }
                AuditInfo auditInfo = new AuditInfo();
                auditInfo.setBusinessKey(dataBaseInfo.getId());
                auditInfo.setAuditUser(UserUtils.getUser());
                auditInfo.setResult(1);
                // auditInfo.setReason("???????????????");
                auditInfo.setType(Integer.parseInt(DataConstant.dataConstantStatus.APPLY));
                auditInfoService.save(auditInfo);
                String  status="2";
                this.dao.changeStatus(dataBaseInfo.getId(),status);
                this.dao.changeInsertUser(dataBaseInfo.getId(),UserUtils.getUser().getName());
                //  this.dao.changeAuditUser(dataBaseInfo);
            }else{
                //??????
                Map<String,Object> model = new HashMap<>(1);
                model.put("userName",createBy);
                String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);

                Map<String,Object> vars = new HashMap<>(1);
                vars.put("message", title);
                actTaskService.apiComplete(dataBaseInfo.getId(),"","", auditUser.getLoginName(),vars);
                String  status="2";
                this.dao.changeStatus(dataBaseInfo.getId(),status);
                this.dao.changeInsertUser(dataBaseInfo.getId(),UserUtils.getUser().getName());
            }

        }

        //??????????????????????????????????????????
        //????????????????????? ??????????????????????????????
        if(isNewRecord && testCheck == null){
            //??????????????????????????????
            if(user.getId()==null){
               user=systemService.getUser(dataBaseInfo.getApplicant());
            }
            this.addDataBasePerm(dataBaseInfo.getId(),user);
        }
    }
    //
    public void addDataBasePerm(String id,User user){

        DataBasePerm dataBasePerm = new DataBasePerm();
        dataBasePerm.setBaseId(id);
        dataBasePerm.setAuthorizationId(user.getId());
        dataBasePerm.setPermissionType(DataCenterConstants.PermissionType.REPORT);//???????????? 1???????????? 2?????????????????????
        dataBasePerm.setAuthorizationType(DataCenterConstants.AuthorizationType.PERSON);//???????????? 1?????? 2 ??????
        dataBasePerm.setAuthorizationScope(DataCenterConstants.AuthorizationScope.ALL);//???????????? 1?????? 2?????????
        dataBasePerm.setAuthorizer(user.getId());//????????????????????????
        dataBasePermService.save(dataBasePerm);
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
        List<Office> reportOffice = new ArrayList<>();//???????????????????????????
        List<User> searchUser = new ArrayList<>();//????????????????????????
        List<Office> searchOffice = new ArrayList<>();//???????????????????????????
        Date searchEndDate = null;//????????????????????????
        //????????????????????????
        List<DataBasePerm> userPermList = dataBasePermService.getUserPermByBaseId(dataBaseInfo.getId());
        //???????????? ?????????????????????????????????
        for(DataBasePerm data:userPermList){
            if("1".equals(data.getPermissionType())){//????????????
                //searchUser.add(data.getUser());
                //searchEndDate = data.getEndDate();//????????????????????????????????????
            }else if("2".equals(data.getPermissionType())){//????????????
                reportUser.add(data.getUser());
            }
        }
        //????????????????????????
        List<DataBasePerm> officePermList = dataBasePermService.getOfficePermByBaseId(dataBaseInfo.getId());
        for(DataBasePerm data:officePermList){
            if("1".equals(data.getPermissionType())){//????????????
                //searchOffice.add(data.getOffice());
            }else if("2".equals(data.getPermissionType())){//????????????
                reportOffice.add(data.getOffice());
            }
        }
        dataBaseInfo.setReportUser(reportUser);
        dataBaseInfo.setReportOffice(reportOffice);
        dataBaseInfo.setSearchUser(searchUser);
        dataBaseInfo.setSearchOffice(searchOffice);
        dataBaseInfo.setSearchEndDate(searchEndDate);
        //????????????????????????
        DataBasePerm bp = new DataBasePerm();
        bp.setBaseId(dataBaseInfo.getId());
        bp.setPermissionType("1");
        List<DataBasePerm> perms = dataBasePermService.findList(bp);
        for(DataBasePerm perm:perms){
            if("1".equals(perm.getAuthorizationType())){//???
                perm.setUser(UserUtils.get(perm.getAuthorizer()));
            }else{
                perm.setOffice(officeService.get(perm.getAuthorizer()));
            }
        }
        dataBaseInfo.setPermList(perms);
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



    public List<DataBaseInfo> findReportList(DataBaseInfo dataBaseInfo){
        return dataBaseInfoDao.findReportList(dataBaseInfo);
    }


    public List<DataBaseInfo> findBaseAllList(DataBaseInfo dataBaseInfo){
        return dataBaseInfoDao.findBaseAllList(dataBaseInfo);
    }


    public List<DataBaseInfo> findCheckList(DataBaseInfo dataBaseInfo){
        return dataBaseInfoDao.findCheckList(dataBaseInfo);
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

    public List<DataBaseInfo> findAll(){
        return dao.findAll();
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
            String taskPath = basePath + dataBaseInfo.getTaskNo() ;
            //?????????????????????
            File baseFile = new File(taskPath);
            if(!baseFile.exists()) {
                baseFile.mkdirs(); //????????????
            }
            //??????xml??????
            DataReportXml dataInfo = new DataReportXml(dataBaseInfo);
            //????????????
            if(!StringUtils.isBlank(dataBaseInfo.getTestProgram())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTestProgram(),taskPath,null);
                if(data != null)  dataInfo.setOutline(data);
            }
            //????????????
            if(!StringUtils.isBlank(dataBaseInfo.getConfig())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getConfig(),taskPath,null);
                if(data != null) dataInfo.setConfig(data);
            }
            //???????????????
            if(!StringUtils.isBlank(dataBaseInfo.getTaskNum())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTaskNum(),taskPath,null);
                if(data != null) dataInfo.setTaskNum(data);
            }
            //??????????????????
            if(!StringUtils.isBlank(dataBaseInfo.getTestReport())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTestReport(),taskPath,null);
                if(data != null) dataInfo.setTestReport(data);
            }
            //???????????????
            if(!StringUtils.isBlank(dataBaseInfo.getPlanFile())){
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getPlanFile(),taskPath,null);
                if(data != null) dataInfo.setPlanFile(data);
            }
            //????????????
            List<DataReportXml.Task> tasks = new ArrayList<>();
            if(dataBaseInfo.getTestInfoList() != null){
                for(DataTestInfo info: dataBaseInfo.getTestInfoList()){
                    String dataPath = taskPath + "/" + info.getId();
                    List<DataReportXml.Data> datas = new ArrayList<>();
                    for(DataInfo di:info.getDatalist()){
                        DataReportXml.Data data = this.buildReportData(di.getTestData(),dataPath,di.getDataType());
                        if(data != null) datas.add(data);
                    }
                    DataReportXml.Task task = new DataReportXml.Task(info,datas);
                    tasks.add(task);
                }
            }
            dataInfo.setTask(tasks);
            //??????xml??????
            taskPath = basePath + dataBaseInfo.getTaskNo() + "/" + dataBaseInfo.getTaskNo() +".xml";
            JaxbUtil.createXml(dataInfo,taskPath,"utf-8");
        }

        //????????????
       // String zipPath = "/Users/zhengweiming/Downloads/"+System.currentTimeMillis()+".zip";
        String zipPath = System.getProperty("java.io.tmpdir")+ File.separator  + System.currentTimeMillis()+".zip";
        basePath = basePath.substring(0,basePath.length()-1);
        FileUtils.zipFiles(basePath,"",zipPath);
        //??????????????????
        FileUtils.deleteDirectory(basePath);
        //????????????????????????
        return zipPath;
    }

    /**
     * ???????????????
     * @return
     */
    public List<DataBaseInfo> sum(List<DataBaseInfo> dataBaseInfos){
        List<DataBaseInfo> pie =new ArrayList<DataBaseInfo>();
        for(DataBaseInfo dataBaseInfo: dataBaseInfos) {
            String basePath = System.getProperty("java.io.tmpdir")+ File.separator + System.currentTimeMillis() + "/";
            String taskPath = basePath + dataBaseInfo.getTaskNo();
            //?????????????????????
            File baseFile = new File(taskPath);
            if (!baseFile.exists()) {
                baseFile.mkdirs(); //????????????
            }
            //??????xml??????
            DataReportXml dataInfo = new DataReportXml(dataBaseInfo);
            //????????????
            if (!StringUtils.isBlank(dataBaseInfo.getTestProgram())) {
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTestProgram(), taskPath, null);
                if (data != null) dataInfo.setOutline(data);
            }
            //????????????
            if (!StringUtils.isBlank(dataBaseInfo.getConfig())) {
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getConfig(), taskPath, null);
                if (data != null) dataInfo.setConfig(data);
            }
            //???????????????
            if (!StringUtils.isBlank(dataBaseInfo.getTaskNum())) {
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTaskNum(), taskPath, null);
                if (data != null) dataInfo.setTaskNum(data);
            }
            //??????????????????
            if (!StringUtils.isBlank(dataBaseInfo.getTestReport())) {
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getTestReport(), taskPath, null);
                if (data != null) dataInfo.setTestReport(data);
            }
            //???????????????
            if (!StringUtils.isBlank(dataBaseInfo.getPlanFile())) {
                DataReportXml.Data data = this.buildReportData(dataBaseInfo.getPlanFile(), taskPath, null);
                if (data != null) dataInfo.setPlanFile(data);
            }
            //????????????
            List<DataReportXml.Task> tasks = new ArrayList<>();
            if (dataBaseInfo.getTestInfoList() != null) {
                for (DataTestInfo info : dataBaseInfo.getTestInfoList()) {
                    String dataPath = taskPath + "/" + info.getId();
                    List<DataReportXml.Data> datas = new ArrayList<>();
                    for (DataInfo di : info.getDatalist()) {
                        DataReportXml.Data data = this.buildReportData(di.getTestData(), dataPath, di.getDataType());
                        if (data != null) datas.add(data);
                    }
                    DataReportXml.Task task = new DataReportXml.Task(info, datas);
                    tasks.add(task);
                }
            }
            dataInfo.setTask(tasks);
            //??????xml??????
            taskPath = basePath + dataBaseInfo.getTaskNo() + "/" + dataBaseInfo.getTaskNo() + ".xml";
            JaxbUtil.createXml(dataInfo, taskPath, "utf-8");

            //????????????
            String zipPath = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis() + ".zip";
            try {
                basePath = basePath.substring(0, basePath.length() - 1);
                FileUtils.zipFiles(basePath, "", zipPath);
                //??????????????????
                FileUtils.deleteDirectory(basePath);
                InputStream is = new FileInputStream(zipPath);
                int available = is.available();
                String size = getSize(available);
                dataBaseInfo.setLogSum(size);
                if("2".equals(dataBaseInfo.getCompany())){
                    dataBaseInfo.setBelong("?????????????????????");
                }else if("4".equals(dataBaseInfo.getCompany())){
                    dataBaseInfo.setBelong("?????????????????????");
                }
                pie.add(dataBaseInfo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pie;
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
                DataReportXml dataInfo = JaxbUtil.readXml(filePath,DataReportXml.class);

                if(null != dataInfo){
                    DataBaseInfo dataBaseInfo = new DataBaseInfo(dataInfo);
                    dataBaseInfo.setLabInfo(labInfoService.get(dataBaseInfo.getLabId()));
                    //??????copy
                    //????????????
                    DataReportXml.Data data = dataInfo.getOutline();
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
                    //???????????????
                    data = dataInfo.getPlanFile();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = infoPath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setPlanFile(attach.getId());
                    }
                    //????????????
                    String dataFile = "";
                    List<DataTestInfo> tests = new ArrayList<>();
                    for(DataReportXml.Task task : dataInfo.getTask()){
                        dataFile = infoPath + task.getId();
                        DataTestInfo test = new DataTestInfo(task);
                        test.setCurrentUser(UserUtils.getUser());
                        test.setCreateDate(now);
                        test.setUpdateBy(UserUtils.getUser());
                        test.setUpdateDate(now);
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
                        if(CollectionUtils.isNotEmpty(task.getData())){
                            String orin="";
                            String testData="";
                            String testLog="";
                            String videoData="";
                            String AudioData="";
                            String ImgData="";
                            for(DataReportXml.Data dat :task.getData()){
                                filePath = dataFile + "/"+ dat.getPath();
                                AttachmentInfo attach = this.saveDataReportAttach(filePath,dat.getPath());
                                switch (dat.getType()){
                                    case DataCenterConstants.DataType.ORIGINAL_RECORD:
                                        if(StringUtils.isEmpty(orin)){
                                            orin=attach.getId();
                                        }else{
                                            orin=orin+","+attach.getId();
                                        }
                                        test.setOriginalRecord(orin);
                                        break;
                                    case DataCenterConstants.DataType.TEST_DATA:
                                        if(StringUtils.isEmpty(testData)){
                                            testData=attach.getId();
                                        }else{
                                            testData=testData+","+attach.getId();
                                        }
                                        test.setTestData(testData);
                                        break;
                                    case DataCenterConstants.DataType.TEST_LOG:
                                        if(StringUtils.isEmpty(testLog)){
                                            testLog=attach.getId();
                                        }else{
                                            testLog=testLog+","+attach.getId();
                                        }
                                        test.setTestLog(testLog);
                                        break;
                                    case DataCenterConstants.DataType.VIDEO:
                                        if(StringUtils.isEmpty(videoData)){
                                            videoData=attach.getId();
                                        }else{
                                            videoData=videoData+","+attach.getId();
                                        }
                                        test.setVideoData(videoData);
                                        break;
                                    case DataCenterConstants.DataType.AUDIO:
                                        if(StringUtils.isEmpty(AudioData)){
                                            AudioData=attach.getId();
                                        }else{
                                            AudioData=AudioData+","+attach.getId();
                                        }
                                        test.setAudioData(AudioData);
                                        break;
                                    case DataCenterConstants.DataType.IMAGE:
                                        if(StringUtils.isEmpty(ImgData)){
                                            ImgData=attach.getId();
                                        }else{
                                            ImgData=ImgData+","+attach.getId();
                                        }
                                        test.setImgData(ImgData);
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
                    this.save(dataBaseInfo);
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

    /**
     * @Describe:??????????????????????????????
     * @Author:WuHui
     * @Date:10:36 2020/11/4
     * @param company
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.DataBaseInfo>
    */
    public List<DataBaseInfo> findAllByCompany(String company) {
        return this.dao.findAllByCompany(company);
    }

    /**
     * @Describe:??????????????????????????????
     * @Author:WuHui
     * @Date:10:36 2020/11/4
     * @param dataBaseInfo
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.DataBaseInfo>
     */
    public List<DataBaseInfo> findDataByInfo(DataBaseInfo  dataBaseInfo) {
        return this.dao.findDataByInfo(dataBaseInfo);
    }

    public  int getIndex(String str, String s,int j){
        int  x=str.indexOf(str);
        for(int i=0;i<j;i++){
            x=str.indexOf(s,x+1);
        }
        return x;
    }


    /**
     * @author Jason
     * @date 2020/9/27 9:48
     * @params [request]
     * ??????????????????????????????
     * @return void
     */
    public void reject(DataBaseInfo request) {
        AuditInfo auditInfo = request.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        request = this.dao.get(request);
        DataBaseInfo assreq = this.dao.get(request.getId());
        //User initiator = UserUtils.get(assreq.getCreateBy());
        //??????
        String userId=assreq.getCreateBy().getId();
        User user=systemService.getUser(userId);
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",user);
        String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.AUDIT_RETURN,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(request.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                user.getLoginName(),vars);
        request.preUpdate();
        String  status=DataConstant.dataConstantStatus.REJECT;
        this.dao.changeStatus(request.getId(),status);
        request.setStatus(status);
        request.setAuditUser(UserUtils.getUser());
        if(null ==request.getAuditUser()){
            request.setAuditUserId(assreq.getCreateBy().getId());
        }else{
            request.setAuditUserId(assreq.getCreateBy().getId());
        }

        this.dao.changeAuditUser(request);
    }

    /**
     * @author Jason
     * @date 2020/9/27 9:47
     * @params [request]
     * ????????????????????????????????????????????????
     * @return void
     */
    public void labPass(DataBaseInfo dataBaseInfo) {
        AuditInfo auditInfo = dataBaseInfo.getAuditInfo();
        User  ss=UserUtils.getUser();
        auditInfo.setAuditUser(UserUtils.getUser());
       // auditInfo.setRemarks(TestCategoryAssessRequestEnum.LAB_AUDIT.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);
        DataBaseInfo assreq = this.dao.get(dataBaseInfo.getId());
       // User auditUser = UserUtils.get(assreq.getTecDir());
       // User createBy = UserUtils.getUser();
        //??????
        Map<String,Object> model = new HashMap<>(1);
        model.put("userName",UserUtils.getUser().getName());
        String title = FreeMarkers.renderString(DataConstant.dataConstantMessage.DEAL_WITH,model);

        Map<String,Object> vars = new HashMap<>(1);
        vars.put("message", title);
        actTaskService.apiComplete(dataBaseInfo.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                UserUtils.getUser().getLoginName(),vars);
        dataBaseInfo.preUpdate();
       // User updateBy = UserUtils.get(assreq.getTecDir());
        //this.dao.changeStatus(request.setStatus(TestCategoryAssessRequestEnum.COMPANY_AUDIT.getCode())
        dataBaseInfo.setAuditUser(ss);
       // request.setAuditUserId(assreq.getTecDir());
        dataBaseInfo.setStatus(DataConstant.dataConstantStatus.RECORD);
        dataBaseInfo.setAuditUserId(dataBaseInfo.getAuditUser().getId());
        this.dao.changeAuditUser(dataBaseInfo);
    }
    /**
     * @author Jason
     * @date 2020/9/27 9:48
     * @params [request]
     * ???????????????????????????????????????
     * @return void
     */
    public void centerApproval(DataBaseInfo dataBaseInfo) {
        AuditInfo auditInfo = dataBaseInfo.getAuditInfo();
        auditInfo.setAuditUser(UserUtils.getUser());
       // auditInfo.setRemarks(TestCategoryAssessRequestEnum.CENTER_APPROVAL.getTitle());
        auditInfo.preInsert();
        auditInfoDao.insert(auditInfo);

        actTaskService.apiComplete(dataBaseInfo.getId(),auditInfo.getReason(),String.valueOf(auditInfo.getResult()),
                null,null);
        dataBaseInfo.preUpdate();
        dataBaseInfo.setStatus(DataConstant.dataConstantStatus.FINISH);

        this.dao.changeStatus(dataBaseInfo.getId(),dataBaseInfo.getStatus());
        this.dao.changeAuditUser(dataBaseInfo);

    }

    public DashboardConfig getDataSum(String center){
        long l = FileUtils.sizeOfDirectory(new File(ROOT_PATH));
        double cbrt = 1024*1024*1024;
        DecimalFormat df = new DecimalFormat("######0.00");
        List<String> dataList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        DashboardConfig  config=new DashboardConfig();
        //??????????????????????????????
        String  dataYear=dataBaseInfoDao.getDataList("year","",center);
        //??????????????????????????????
        String  dataYearSum=dataBaseInfoDao.getTaskDataList("year","",center);
        double yearData = (Long.parseLong(dataYearSum)+l) / cbrt;
        String yearDataFormat = df.format(yearData);
        //??????????????????????????????
        String  dataMonth=dataBaseInfoDao.getDataList("month","",center);
       //???????????????????????????
        String  dataMonthSum=dataBaseInfoDao.getTaskDataList("month","",center);
        double monthData = (Long.parseLong(dataMonthSum)+l) / cbrt;
        String monthDataFormat = df.format(monthData);
        //??????????????????????????????
        String  dataDay=dataBaseInfoDao.getDataList("day","",center);
        //???????????????????????????
        String  dataDaySum=dataBaseInfoDao.getTaskDataList("day","",center);
        double dayData = (Long.parseLong(dataDaySum)+l) / cbrt;
        String dayDataFormat = df.format(dayData);
        //??????????????????????????????
        List<String> list=dataBaseInfoDao.getQurater();
        String data=dataBaseInfoDao.getDataList("quarter",null,center);
        String dataSum=dataBaseInfoDao.getTaskDataList("quarter",null,center);
        double dataSumDouble = (Long.parseLong(dataSum)+l) / cbrt;
        String dataSumDoubleFormat = df.format(dataSumDouble);
        /*Integer i=0;
        Integer Isum=0;
         for(String s:list){
            String data=dataBaseInfoDao.getDataList("quarter",s,center);
            String dataSum=dataBaseInfoDao.getTaskDataList("quarter",s,center);
            Integer intge=Integer.parseInt(data);
            Integer intgeSum=Integer.parseInt(dataSum);
            i+=intge;
            Isum+=intgeSum;
        }*/
        /*String  dataQuarter=String.valueOf(i);
        String  dataQuarterSum=String.valueOf(Isum);*/
        list.add(dataYear);
        list.add(yearDataFormat);
        list.add(data);
        list.add(dataSumDoubleFormat);
        list.add(dataMonth);
        list.add(monthDataFormat);
        list.add(dataDay);
        list.add(dayDataFormat);
        String ss=dataYear+","+yearDataFormat+","+data+","+dataSumDoubleFormat+","+dataMonth+","+monthDataFormat+","+dataDay+","+dayDataFormat;
        config.setValueList(list);
        config.setValue(ss);
        config.setType("task");
        config.setStatus("1");
        return config;
    }

    //????????????????????????
    public DashboardConfig  getTaskDataList(){
        DashboardConfig  config=new DashboardConfig();
        List<String> dataList=new ArrayList<>();
        List<String> centerList=new ArrayList<>();
        centerList.add("?????????");
        centerList.add("????????????");
        centerList.add("????????????");
        centerList.add("????????????");
        centerList.add("????????????");
        centerList.add("???????????????");
        String data="";
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String dataInfo="";
        String value="";
        for(String center:centerList){
            String info="";
            String info1="";
            if(!"???????????????".equals(center)){
                //12????????????????????????
                for(int i=1;i<13;i++){
                    if(i<10){
                        dataInfo=year+"-0"+i;
                    }else{
                        dataInfo=year+"-"+i;
                    }
                    String count=dataBaseInfoDao.getCenterDataList(center,dataInfo);
                    if(i==12){
                      info+=count;
                    }else{
                        info=info+count+",";
                    }
                }
               value=value+info+"/-/";
                dataList.add(info);
            }else{
                //???????????????????????????
                //12????????????????????????
                for(int i=1;i<13;i++){
                    if(i<10){
                        dataInfo=year+"-0"+i;
                    }else{
                        dataInfo=year+"-"+i;
                    }
                    String count=dataBaseInfoDao.getServerDataList(dataInfo);
                    if(i==12){
                        info1+=count;
                    }else{
                        info1=info1+count+",";
                    }

                }
                dataList.add(info1);
                value=value+info1;
            }

        }
        System.out.println(value);
        config.setType("taskStatistics");
        config.setValueList(dataList);
        config.setValue(value);
        config.setStatus("1");
        return config;
    }
    //????????????????????????
    public DashboardConfig  getTaskMessList(){
        DashboardConfig  config=new DashboardConfig();
        List<String> dataList=new ArrayList<>();
        List<String> centerList=new ArrayList<>();
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String u= UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getLoginName();
        if(StringUtils.isNotEmpty(u)){
            String subCenter=systemService.getUserBelongTestCenter(u);
            centerList.add(subCenter);
        }else{
            centerList.add("?????????");
            centerList.add("????????????");
            centerList.add("????????????");
            centerList.add("????????????");
            centerList.add("????????????");
        }
        String value="";
        String dataTime="";
        //???????????????????????????
        List<Map<String,String>> centerDataList=dataBaseInfoDao.getCenterDataMess();
        //????????????????????????????????????
        List<Map<String,String>> serverDataList=dataBaseInfoDao.getServerDataMess();
        //???????????????????????????0
        if(CollectionUtils.isEmpty(centerDataList)){
            for(int i=0;i<centerList.size();i++){
                String value1="";
                for(int j=0;j<12;j++){
                    String count="0";
                    if(i==11){
                       value1+=count;
                    }else{
                       value1=value1+count+",";
                    }
                }
                dataList.add(value1);
                value=value+value1+"/-/";
            }
        }else{
            for(int i=0;i<centerList.size();i++){
                String center=centerList.get(i);
                //dateList ---?????? ??????   dataInfoList ---?????? ??????
                List<String> dateList=new ArrayList<>();
                List<String> dataInfoList=new ArrayList<>();
                for(int j=0;j<centerDataList.size();j++){
                    String centerName=String.valueOf(centerDataList.get(j).get("centerName"));
                    if(center.equals(centerName)){
                        String dateInfo=String.valueOf(centerDataList.get(j).get("dateInfo"));
                        String dataSum=String.valueOf(centerDataList.get(j).get("dataSum"));
                        dateList.add(dateInfo);
                        dataInfoList.add(dataSum);
                    }else{
                        continue;
                    }
                }
                if(CollectionUtils.isEmpty(dateList)){
                    String value1="";
                    for(int j=0;j<12;j++){
                        String count="0";
                        if(j==11){
                            value1+=count;
                        }else{
                            value1=value1+count+",";
                        }
                    }
                    dataList.add(value1);
                    value=value+value1+"/-/";
                }else{
                    //??????????????????12???????????????
                    String value1="";
                    int index=0;
                        for(int s=1;s<13;s++){
                            if(s<10){
                                dataTime=year+"-0"+s;
                            }else{
                                dataTime=year+"-"+s;
                            }
                            String count="0";
                            if(s<12){
                                if(index==dateList.size()){
                                    index=index-1;
                                }
                                for(int j=index;j<dateList.size();j++){
                                    if(dateList.get(j).equals(dataTime)){
                                        count=dataInfoList.get(j);
                                        value1=value1+count+",";
                                        index=(j+1);
                                        break;
                                    }else{
                                        value1=value1+count+",";
                                        break;
                                    }
                                }

                            }else{
                                if(index==dateList.size()){
                                    index=index-1;
                                }
                                for(int j=index;j<dateList.size();j++){
                                    if(dateList.get(j).equals(dataTime)){
                                        count=dataInfoList.get(j);
                                        value1=value1+count;
                                        index=(j+1);
                                        break;
                                    }else{
                                        value1=value1+count;
                                        break;
                                    }
                            }

                        }
                    }
                    dataList.add(value1);
                    value=value+value1+"/-/";
                }
            }


        }
        //?????????????????????????????????????????????
        if(CollectionUtils.isEmpty(serverDataList)){
            String value2="";
            for(int j=0;j<12;j++){
                String count="0";
                if(j==11){
                    value2+=count;
                }else{
                    value2=value2+count+",";
                }
            }
            dataList.add(value2);
            value+=value2;
        }else{
            List<String> dateList=new ArrayList<>();
            List<String> dataInfoList=new ArrayList<>();
            for(int j=0;j<serverDataList.size();j++){
                String dateInfo=String.valueOf(serverDataList.get(j).get("dateInfo"));
                String dataSum=String.valueOf(serverDataList.get(j).get("dataSum"));
                dateList.add(dateInfo);
                dataInfoList.add(dataSum);
            }
            String value1="";
            int index=0;
            for(int s=1;s<13;s++){
                if(s<10){
                    dataTime=year+"-0"+s;
                }else{
                    dataTime=year+"-"+s;
                }
                String count="0";
                if(s==12){
                    if(index==dateList.size()){
                        index=index-1;
                    }
                    for(int j=index;j<dateList.size();j++){
                        if(dateList.get(j).equals(dataTime)){
                            count=dataInfoList.get(j);
                            value1=value1+count;
                            index=(j+1);
                            break;
                        }else{
                            value1=value1+count;
                            break;
                        }
                    }

                }else{
                    if(index==dateList.size()){
                        index=index-1;
                    }
                    for(int j=index;j<dateList.size();j++){
                        if(dateList.get(j).equals(dataTime)){
                            count=dataInfoList.get(j);
                            value1=value1+count+",";
                            index=(j+1);
                            break;
                        }else{
                            value1=value1+count+",";
                            break;
                        }
                    }
                }
            }
            dataList.add(value1);
            value=value+value1;

        }

        config.setType("taskStatistics");
        config.setValueList(dataList);
        config.setValue(value);
        if(centerList.size()==1){
            config.setValueName(centerList.get(0));
        }else{
            config.setValueName("");
        }
        config.setStatus("1");
        return config;
    }


    /**
     * @Describe:??????????????????
     * @Author:WuHui
     * @Date:18:54 2020/9/21
     * @param ziPath
     * @return:void
     */
    public Map<String,String> upDataReport(String ziPath,String applyUser){
        Map<String,String>  map=new HashMap<>();
        map.put("status","200");
        map.put("message","success");
        User user=systemService.getUserByLoginName(applyUser);
        if(user ==null){
            map.put("status","400");
            map.put("message","?????????????????????");
            return map;
        }
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
            String filePath =basePath+infoFile.getName();
            DataReportXml dataInfo=null;
                if(filePath.contains("xml")){
                     dataInfo = JaxbUtil.readXml(filePath,DataReportXml.class);
                }else{
                    continue;
                }
                if(null != dataInfo){
                    DataBaseInfo dataBaseInfo = new DataBaseInfo(dataInfo);
                    dataBaseInfo.setLabInfo(labInfoService.get(dataBaseInfo.getLabId()));
                    dataBaseInfo.setLabInfoName(labInfoService.get(dataBaseInfo.getLabId()).getName());
                    //??????copy
                    //????????????
                    DataReportXml.Data data = dataInfo.getOutline();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = basePath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTestProgram(attach.getId());
                    }
                    //????????????
                    data = dataInfo.getConfig();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = basePath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setConfig(attach.getId());
                    }
                    //????????????
                    data = dataInfo.getTaskNum();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = basePath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTaskNum(attach.getId());
                    }
                    //??????????????????
                    data = dataInfo.getTestReport();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = basePath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setTestReport(attach.getId());
                    }
                    //???????????????
                    data = dataInfo.getPlanFile();
                    if( data != null && !StringUtils.isBlank(data.getPath())){
                        filePath = basePath + data.getPath();
                        AttachmentInfo attach = this.saveDataReportAttach(filePath,data.getPath());
                        dataBaseInfo.setPlanFile(attach.getId());
                    }
                    SubCenter  sName=new SubCenter();
                    sName.setName("?????????");
                    sName= subCenterService.getByName(sName);
                    if(sName!=null){
                        dataBaseInfo.setTestCenter(sName.getId());
                    }
                    //????????????
                    String dataFile = "";
                    List<DataTestInfo> tests = new ArrayList<>();
                    for(DataReportXml.Task task : dataInfo.getTask()){
                        dataFile = infoPath + task.getId();
                        DataTestInfo test = new DataTestInfo(task);
                      //  test.setTestItemName(task.getTestItem());
                        test.setCurrentUser(UserUtils.getUser());
                        if(StringUtils.isNotEmpty(task.getTester())){
                            String[]  ss=task.getTester().split(",");
                            String ab="";
                            for(int i=0;i<ss.length;i++){
                                User u=systemService.getUserByLoginName(ss[i]);
                                if(u !=null){
                                  if(i==0){
                                      ab=u.getId();
                                    }else{
                                      ab=ab+","+u.getId();
                                  }
                                }
                            }
                            test.setTester(ab);
                        }
                        test.setCreateDate(now);
                        test.setUpdateBy(UserUtils.getUser());
                        test.setUpdateDate(now);
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
                        if(CollectionUtils.isNotEmpty(task.getData())){
                            String orin="";
                            String testData="";
                            String testLog="";
                            String videoData="";
                            String AudioData="";
                            String ImgData="";
                            for(DataReportXml.Data dat :task.getData()){
                                filePath = basePath+ dat.getPath();
                                AttachmentInfo attach = this.saveDataReportAttach(filePath,dat.getPath());
                                switch (dat.getType()){
                                    case DataCenterConstants.DataType.ORIGINAL_RECORD:
                                        if(StringUtils.isEmpty(orin)){
                                            orin=attach.getId();
                                        }else{
                                            orin=orin+","+attach.getId();
                                        }
                                        test.setOriginalRecord(orin);
                                        break;
                                    case DataCenterConstants.DataType.TEST_DATA:
                                        if(StringUtils.isEmpty(testData)){
                                            testData=attach.getId();
                                        }else{
                                            testData=testData+","+attach.getId();
                                        }
                                        test.setTestData(testData);
                                        break;
                                    case DataCenterConstants.DataType.TEST_LOG:
                                        if(StringUtils.isEmpty(testLog)){
                                            testLog=attach.getId();
                                        }else{
                                            testLog=testLog+","+attach.getId();
                                        }
                                        test.setTestLog(testLog);
                                        break;
                                    case DataCenterConstants.DataType.VIDEO:
                                        if(StringUtils.isEmpty(videoData)){
                                            videoData=attach.getId();
                                        }else{
                                            videoData=videoData+","+attach.getId();
                                        }
                                        test.setVideoData(videoData);
                                        break;
                                    case DataCenterConstants.DataType.AUDIO:
                                        if(StringUtils.isEmpty(AudioData)){
                                            AudioData=attach.getId();
                                        }else{
                                            AudioData=AudioData+","+attach.getId();
                                        }
                                        test.setAudioData(AudioData);
                                        break;
                                    case DataCenterConstants.DataType.IMAGE:
                                        if(StringUtils.isEmpty(ImgData)){
                                            ImgData=attach.getId();
                                        }else{
                                            ImgData=ImgData+","+attach.getId();
                                        }
                                        test.setImgData(ImgData);
                                        break;
                                }
                            }
                        }
                        tests.add(test);
                    }
                    dataBaseInfo.setTestInfoList(tests);
                    dataBaseInfo.setApplicant(user.getId());
                    dataBaseInfo.setCreateBy(user);
                    dataBaseInfo.setCurrentUser(user);
                    dataBaseInfo.setCreateDate(now);
                    dataBaseInfo.setUpdateBy(user);
                    dataBaseInfo.setUpdateDate(now);
                    dataBaseInfo.setCompany("2");
                    //?????????????????????
                    this.save(dataBaseInfo);
                }
           // }
        }
        //?????????????????????
        //FileUtils.deleteDirectory(basePath);
        //??????????????????
        FileUtils.deleteFile(ziPath);

        return map;
    }
    //ftp??????
    public   Map<String,String> uploadFtp(DataBaseInfo dataBaseInfo){
        log.info("--------start----------");
        NoStandardTestCheck  s= testCheckService.getTestCheckByReportId(dataBaseInfo.getId());
        Document doc = null;
        String host="";
        int port=21;
        String password="";
        String userName="";
        String ftpPath="";
        Map<String,String> map=new HashMap<>();
        map.put("status","success");
        try{
            //??????ftp??????
            String method = "GetFtpConfig";
            String endpoint = "";
            String qName="";
            String output;
            endpoint=Global.getConfig("pdm.webservice.url");
            qName="http://tempuri.org/";
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(endpoint));
            call.setOperationName(new QName(qName, method));
            call.setReturnType(XMLType.XSD_STRING);
            call.setUseSOAPAction(true);
            output = (String) call.invoke(new Object[]{});
            doc = DocumentHelper.parseText(output);
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
                Element element = it.next();
                if("SERVERNAME".equals(element.getName().toUpperCase())){
                    host=element.getTextTrim();
                }
                if("UserName".equals(element.getName())){
                    userName=element.getTextTrim();
                }
                if("Password".equals(element.getName())){
                    password=element.getTextTrim();
                }
                if("Port".equals(element.getName())){
                    port=Integer.parseInt(element.getTextTrim());
                }
                if("VirtualPath".equals(element.getName())){
                    ftpPath=element.getTextTrim();
                }
            }
            map.put("url",ftpPath);
            OutputStream os = null;
            if(s !=null){
                //???????????????ID
                List<String> tLogData=new ArrayList<>();
                //?????????
                //?????????ID
                String  checkId=s.getData();
                log.info("--------check----------");
                if(StringUtils.isNotEmpty(checkId)){
                    String[] cechArry=checkId.split(",");
                    for(int i=0;i<cechArry.length;i++){
                        tLogData.add(cechArry[i]);
                    }
                }
                //????????????????????????
                List <DataTestInfo>  list=dataTestInfoService.getByBaseId(dataBaseInfo.getId());
                if(!CollectionUtils.isEmpty(list)){
                    for(DataTestInfo dataTestInfo:list){
                        List<String> orList = new ArrayList<>();
                        for(DataInfo dataInfo:dataTestInfo.getDatalist()){
                            String data=dataInfo.getTestData();
                            if(org.apache.commons.lang.StringUtils.isNotEmpty(data)){
                                String[] dataArry=data.split(",");
                                for(int i=0;i<dataArry.length;i++){
                                    tLogData.add(dataArry[i]);
                                }
                            }
                        }
                    }
                }
                log.info("--------shuju----------");
                //??????????????????
                NoStandardTestLog  tlog=new NoStandardTestLog();
                tlog.setExecutionId(dataBaseInfo.getId());
                List<NoStandardTestLog> tLogList=noStandardTestLogDao.getByExecutionId(tlog);
                log.info("--------log----------");
                if(CollectionUtils.isNotEmpty(tLogList)){
                    for(NoStandardTestLog  st:tLogList){
                        String ss=st.getFile();
                        if(StringUtils.isNotEmpty(ss)){
                            String[] arry=ss.split(",");
                            for(int i=0;i<arry.length;i++){
                                tLogData.add(arry[i]);
                            }
                        }
                    }
                }
                byte[] bs = new byte[1024];
                int len;
                if(CollectionUtils.isNotEmpty(tLogData)){
                    for(String attachId:tLogData){
                        AttachmentInfo attach = attachmentInfoService.get(attachId);
                        String filePath = BASE_DIR + attach.getPath();
                        String fileName=attach.getName();
                        String suffix=fileName.substring(fileName.indexOf("."),fileName.length());
                        UUID uuid = UUID.randomUUID();
                        if(".doc".equals(suffix)|| ".docx".equals(suffix)){
                            String newFileName=uuid+suffix;
                            String laterFileName=uuid+".pdf";
                            String oldfilePath = System.getProperty("java.io.tmpdir");
                            File tempFile = new File(oldfilePath);
                            if (!tempFile.exists()) {
                                tempFile.mkdirs();
                            }
                            InputStream is = fileStore.downloadFileToStream(filePath);
                            os = new FileOutputStream(tempFile.getPath() + File.separator + newFileName);
                            // ????????????
                            while ((len = is.read(bs)) != -1) {
                                os.write(bs, 0, len);
                            }
                             os.flush();
                            WordForPDFUtils.word2pdf(tempFile.getPath()+File.separator + newFileName,tempFile.getPath()+File.separator +laterFileName);
                            filePath=tempFile.getPath() + File.separator +laterFileName;
                            InputStream is1 = new FileInputStream(new File(filePath));
                            //???????????????ftp
                            FtpUtils.uploadFile(host,userName,password,port,ftpPath,laterFileName,is1);
                          //  FtpUtils.uploadFile("192.168.8.104","document","123456",21,"E:\\ftp\\document",laterFileName,is1);
                            map.put(attachId,laterFileName);
                            map.put(laterFileName,oldfilePath+"/"+laterFileName);
                        }else{
                            String newFileName=uuid+suffix;
                            String oldfilePath = System.getProperty("java.io.tmpdir");
                            File tempFile = new File(oldfilePath);
                            if (!tempFile.exists()) {
                                tempFile.mkdirs();
                            }
                            InputStream is = fileStore.downloadFileToStream(filePath);
                            os = new FileOutputStream(tempFile.getPath() + File.separator + newFileName);
                            // ????????????
                            while ((len = is.read(bs)) != -1) {
                                os.write(bs, 0, len);
                            }
                            InputStream is1 = fileStore.downloadFileToStream(filePath);
                            //???????????????ftp
                            FtpUtils.uploadFile(host,userName,password,port,ftpPath,newFileName,is1);
                           //  FtpUtils.uploadFile("192.168.8.104","document","123456",21,"E:\\ftp\\document",newFileName,is1);
                            map.put(attachId,newFileName);
                            map.put(newFileName,oldfilePath+"/"+newFileName);
                        }
                        os.flush();
                        os.close();

                    }
                }
            }
        }catch (Exception  e){
            e.printStackTrace();
            log.info("--------zhixingbaocuo----------");
            map.put("status","error");
            return map;
        }
        log.info("--------end----------");
        return map;
    }

    public  String   getDataReportInfo(NoStandardTestCheck noStandardTestCheck){
        String  status="success";
        NoStandardTestCheck  s= testCheckService.get(noStandardTestCheck.getId());
        //File file=new File("/Users/zhengweiming/Downloads/dom4j.xml");
        if(s !=null){
            //??????ftp??????
            DataBaseInfo dataBaseInfo =  dataBaseInfoDao.get(s.getReportId());
            Map<String,String> map=uploadFtp(dataBaseInfo);
            if("error".equals(map.get("status").toString())){
                status="error";
                return status;
            }
            //????????????
            List<String> tLogData=new ArrayList<>();
            //?????????
            List<String> tCheckData=new ArrayList<>();
            //????????????
            List<String> tData=new ArrayList<>();
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            String nowDate=sdf.format(date);
            //???????????????
            String code=s.getCode();
            //????????????
            String testName=s.getTestName();
            //id
            String id=s.getId();
            //?????????ID
            String  checkId=s.getData();
            Date  d=s.getCreateDate();
            //????????????
            String createDate=sdf.format(d);
            if(StringUtils.isNotEmpty(checkId)){
                String[] cechArry=checkId.split(",");
                for(int i=0;i<cechArry.length;i++){
                    tCheckData.add(cechArry[i]);
                }
            }
            //????????????????????????
            List <DataTestInfo>  list=dataTestInfoService.getByBaseId(dataBaseInfo.getId());
            if(!CollectionUtils.isEmpty(list)){
                for(DataTestInfo dataTestInfo:list){
                    for(DataInfo dataInfo:dataTestInfo.getDatalist()){
                        String data=dataInfo.getTestData();
                        if(org.apache.commons.lang.StringUtils.isNotEmpty(data)){
                            String[] dataArry=data.split(",");
                            for(int i=0;i<dataArry.length;i++){
                                tData.add(dataArry[i]);
                            }
                        }
                    }
                }
            }
            //??????????????????
            NoStandardTestLog  tlog=new NoStandardTestLog();
            tlog.setExecutionId(dataBaseInfo.getId());
            List<NoStandardTestLog> tLogList=noStandardTestLogDao.getByExecutionId(tlog);
            if(CollectionUtils.isNotEmpty(tLogList)){
                for(NoStandardTestLog  st:tLogList){
                    String ss=st.getFile();
                    if(StringUtils.isNotEmpty(ss)){
                        String[] arry=ss.split(",");
                         for(int i=0;i<arry.length;i++){
                             tLogData.add(arry[i]);
                         }
                    }
                }
            }

            System.out.println(tCheckData+"-------"+tData+"-------"+tLogData);
            //??????
            String model=s.getProductModel();

            Dict dict = new Dict();
            dict.setType("product_model");
            dict.setValue(model);
            List<Dict>  l=dictService.findList(dict);
            String productModel="";
            if(CollectionUtils.isNotEmpty(l)){
                productModel=l.get(0).getLabel();
            }
            User user=UserUtils.getUser();
            String  creator=user.getName()+"("+user.getLoginName()+")";
            //???????????????
            String  userID=s.getTestManagerId();
            String manager="";
            if(StringUtils.isNotEmpty(userID)){
                manager=UserUtils.get(userID).getName()+"("+UserUtils.get(userID).getLoginName()+")";
            }
          //  String  creator="?????????(206508)";
            try {
                // ????????????Document??????
                Document doc = DocumentHelper.createDocument();
                // ???????????????
                Element root = doc.addElement("Documents");
                // ??????????????????????????????????????? Document
                Element document= root.addElement("Document");
                // ???Document????????????????????????????????? attribute
                Element attribute= document.addElement("Attribute");
                attribute.addElement("ajh").addText(code.trim());
                attribute.addElement("tm").addText(testName.trim());
                attribute.addElement("gddw").addText("??????????????????".trim());
                attribute.addElement("gdsj").addText(nowDate.trim());
                attribute.addElement("GWLX").addText("TDM".trim());
                attribute.addElement("OAID").addText(id.trim());
                attribute.addElement("xcsj").addText(nowDate.trim());
                attribute.addElement("projnum").addText(productModel.trim());
                attribute.addElement("ZRZ").addText(manager.trim());
                attribute.addElement("MJ").addText("??????".trim());
                attribute.addElement("BGQX").addText("??????".trim());
                //Attribute?????????????????? Files
                 /*Element files= attribute.addElement("Files");
                 Element file1= files.addElement("File");
                file1.addElement("filename").addText("");
                file1.addElement("size").addText("");
                file1.addElement("url").addText("??????????????????");
                file1.addElement("ftpname").addText(nowDate);
                file1.addElement("hashcode").addText("");*/

                // ???Document????????????????????????????????? orderList
                Element orderList= document.addElement("orderList");

                // ???orderList????????????????????????????????? orderList  --1--?????????
                Element order= orderList.addElement("order");
                order.addElement("ajh").addText(code.trim());
                order.addElement("tm").addText(testName.trim());
                order.addElement("XCSJ").addText(createDate.trim());
                order.addElement("gddw").addText("??????????????????".trim());
                order.addElement("gdsj").addText(nowDate.trim());
                order.addElement("docnumber").addText("");
                order.addElement("docname").addText("?????????".trim());
                order.addElement("docver").addText("");
                order.addElement("doctype").addText("????????????".trim());
                order.addElement("projnum").addText(productModel.trim());
                order.addElement("createtime").addText(nowDate.trim());
                order.addElement("creator").addText(creator.trim());
                order.addElement("language").addText("??????".trim());
                order.addElement("secret").addText("??????".trim());
                order.addElement("updatetime").addText(nowDate.trim());
                order.addElement("updatename").addText(creator.trim());
                order.addElement("archivetime").addText(nowDate.trim());
                order.addElement("archivename").addText(creator.trim());
                order.addElement("remark").addText("");
                order.addElement("department").addText("");
                order.addElement("rep_number").addText("");
                order.addElement("replacetime").addText("");
                order.addElement("cancel_number").addText("");
                order.addElement("canceltime").addText("");
                order.addElement("rel_number").addText("");
                order.addElement("revision").addText("");
                order.addElement("GWLX").addText("TDM".trim());
                order.addElement("OAID").addText(id.trim());
                //order?????????????????? Files
                Element orderFile1= order.addElement("Files");
                if(CollectionUtils.isNotEmpty(tCheckData)){
                    for(int i=0;i<tCheckData.size();i++){
                        AttachmentInfo attach = attachmentInfoService.get(tCheckData.get(i));
                        Element orderFile11= orderFile1.addElement("File");
                        orderFile11.addElement("filename").addText(attach.getName().trim());
                        orderFile11.addElement("size").addText(String.valueOf(attach.getLength()).trim());
                        orderFile11.addElement("url").addText(map.get("url").toString().trim());
                        orderFile11.addElement("ftpname").addText(map.get(tCheckData.get(i)).toString().trim());
                        String filePath = BASE_DIR + attachmentInfoService.get(tCheckData.get(i)).getPath();
                        //???????????????????????????
                        String fileValue=map.get(map.get(tCheckData.get(i)).toString());
                        orderFile11.addElement("hashcode").addText(getFileSHA1(new File(fileValue)).trim());
                    }
                }


                Element order1= orderList.addElement("order"); //2---????????????
                order1.addElement("ajh").addText(code.trim());
                order1.addElement("tm").addText(testName.trim());
                order1.addElement("XCSJ").addText(createDate.trim());
                order1.addElement("gddw").addText("??????????????????".trim());
                order1.addElement("gdsj").addText(nowDate.trim());
                order1.addElement("docnumber").addText("");
                order1.addElement("docname").addText("????????????".trim());
                order1.addElement("docver").addText("");
                order1.addElement("doctype").addText("????????????".trim());
                order1.addElement("projnum").addText(productModel.trim());
                order1.addElement("createtime").addText(nowDate.trim());
                order1.addElement("creator").addText(creator.trim());
                order1.addElement("language").addText("??????".trim());
                order1.addElement("secret").addText("??????".trim());
                order1.addElement("updatetime").addText(nowDate.trim());
                order1.addElement("updatename").addText(creator.trim());
                order1.addElement("archivetime").addText(nowDate.trim());
                order1.addElement("archivename").addText(creator.trim());
                order1.addElement("remark").addText("");
                order1.addElement("department").addText("");
                order1.addElement("rep_number").addText("");
                order1.addElement("replacetime").addText("");
                order1.addElement("cancel_number").addText("");
                order1.addElement("canceltime").addText("");
                order1.addElement("rel_number").addText("");
                order1.addElement("revision").addText("");
                order1.addElement("GWLX").addText("TDM".trim());
                order1.addElement("OAID").addText(id.trim());
                //order?????????????????? Files
                Element orderFile2= order1.addElement("Files");
                if(CollectionUtils.isNotEmpty(tData)){
                    for(int i=0;i<tData.size();i++){
                        AttachmentInfo attach = attachmentInfoService.get(tData.get(i));
                        Element orderFile22= orderFile2.addElement("File");
                        orderFile22.addElement("filename").addText(attach.getName().trim());
                        orderFile22.addElement("size").addText(String.valueOf(attach.getLength()).trim());
                        orderFile22.addElement("url").addText(map.get("url").toString().trim());
                        orderFile22.addElement("ftpname").addText(map.get(tData.get(i)).toString().trim());
                        String filePath = BASE_DIR + attachmentInfoService.get(tData.get(i)).getPath();
                        String fileValue=map.get(map.get(tData.get(i)).toString());
                        orderFile22.addElement("hashcode").addText(getFileSHA1(new File(fileValue)).trim());
                    }
                }

                Element order2= orderList.addElement("order");//3---????????????
                order2.addElement("ajh").addText(code.trim());
                order2.addElement("tm").addText(testName.trim());
                order2.addElement("XCSJ").addText(createDate.trim());
                order2.addElement("gddw").addText("??????????????????".trim());
                order2.addElement("gdsj").addText(nowDate.trim());
                order2.addElement("docnumber").addText("");
                order2.addElement("docname").addText("????????????".trim());
                order2.addElement("docver").addText("");
                order2.addElement("doctype").addText("????????????".trim());
                order2.addElement("projnum").addText(productModel.trim());
                order2.addElement("createtime").addText(nowDate.trim());
                order2.addElement("creator").addText(creator.trim());
                order2.addElement("language").addText("??????".trim());
                order2.addElement("secret").addText("??????".trim());
                order2.addElement("updatetime").addText(nowDate.trim());
                order2.addElement("updatename").addText(creator.trim());
                order2.addElement("archivetime").addText(nowDate.trim());
                order2.addElement("archivename").addText(creator.trim());
                order2.addElement("remark").addText("");
                order2.addElement("department").addText("");
                order2.addElement("rep_number").addText("");
                order2.addElement("replacetime").addText("");
                order2.addElement("cancel_number").addText("");
                order2.addElement("canceltime").addText("");
                order2.addElement("rel_number").addText("");
                order2.addElement("revision").addText("");
                order2.addElement("GWLX").addText("TDM".trim());
                order2.addElement("OAID").addText(id.trim());
                //order?????????????????? Files
                Element orderFile3= order2.addElement("Files");
                if(CollectionUtils.isNotEmpty(tLogData)){
                    for(int i=0;i<tLogData.size();i++){
                        AttachmentInfo attach = attachmentInfoService.get(tLogData.get(i));
                        Element orderFile33= orderFile3.addElement("File");
                        orderFile33.addElement("filename").addText(attach.getName());
                        orderFile33.addElement("size").addText(String.valueOf(attach.getLength()).trim());
                        orderFile33.addElement("url").addText(map.get("url").toString().trim());
                        orderFile33.addElement("ftpname").addText(map.get(tLogData.get(i)).toString().trim());
                        String filePath = BASE_DIR + attachmentInfoService.get(tLogData.get(i)).getPath();
                        String fileValue=map.get(map.get(tLogData.get(i)).toString());
                        orderFile33.addElement("hashcode").addText(getFileSHA1(new File(fileValue)).trim());
                    }
                }
                // ?????????xml??????
               /* OutputFormat format = new OutputFormat();
                format.setIndentSize(2);  // ?????????
                format.setNewlines(true); // ?????????????????????
                format.setTrimText(true); // ????????????
                format.setPadText(true);
                format.setNewLineAfterDeclaration(false);*/ // ??????xml??????????????????????????????
                String method = "DataToArchive";
                String endpoint = "";
                String qName="";
                String output;
                try{
                    endpoint=Global.getConfig("pdm.webservice.url");
                    String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+root.asXML().trim().replaceAll(" ", "");;
                    xml=URLEncoder.encode(xml,"utf-8");
                    URL url = new URL(endpoint);
                    URLConnection conn = url.openConnection();
                    HttpURLConnection con = (HttpURLConnection) conn;
                    con.setDoInput(true); // ???????????????
                    con.setDoOutput(true); // ???????????????
                    con.setRequestMethod("POST"); // ??????????????????
                    con.setRequestProperty("content-type", "text/xml;charset=UTF-8");
                    con.setRequestProperty("SOAPAction", "http://tempuri.org/DataToArchive");
                    String  requestBody ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <tem:DataToArchive>\n" +
                            "         <!--Optional:-->\n" +
                            "         <tem:xmlFragment><![CDATA["+xml+"]]></tem:xmlFragment>\n" +
                            "      </tem:DataToArchive>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";
                    OutputStream out = con.getOutputStream();
                    System.out.println(requestBody);
                    out.write(requestBody.getBytes());
                    out.close();
                    int code123 = con.getResponseCode();
                    if (code123 == 200) {// ?????????????????????
                        InputStream is = con.getInputStream();
                        byte[] b = new byte[1024];
                        StringBuffer sb = new StringBuffer();
                        int len = 0;
                        while ((len = is.read(b)) != -1) {
                            String str = new String(b, 0, len, "UTF-8");
                            sb.append(str);
                        }
                        System.out.println(sb.toString());
                        String result=sb.toString();
                        String statusCode = sb.substring(result.indexOf("returnValue")+1,result.indexOf("/returnValue"));
                        if(statusCode.contains("0")){

                            DataInterface  dif=new DataInterface();
                            dif.setDataId(id);
                            List<DataInterface>  dataList=dataInterfaceService.findList(dif);
                            if(CollectionUtils.isNotEmpty(dataList)){
                                dif.setId(dataList.get(0).getId());
                            }
                            dif.setDataNo(code);
                            dif.setDataType("????????????");
                            dif.setStatus("??????");
                            dataInterfaceService.save(dif);
                        }else{
                            DataInterface  dif=new DataInterface();
                            dif.setDataId(id);
                            List<DataInterface>  dataList=dataInterfaceService.findList(dif);
                            if(CollectionUtils.isNotEmpty(dataList)){
                                dif.setId(dataList.get(0).getId());
                            }
                            dif.setDataNo(code);
                            dif.setDataType("????????????");
                            dif.setStatus("??????");
                            dataInterfaceService.save(dif);
                        }

                        is.close();
                    }
                    con.disconnect();
                  //  System.out.println("dom4j CreateDom4j success!"+code123);
                    FileUtils.deleteFile(System.getProperty("java.io.tmpdir"));
                    // ??????xml??????
                  //  System.out.println("dom4j CreateDom4j success!");
                }catch (Exception  e){
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                status="error";
                return status;
            }
        }

        return  status;
    }


    /**
     * ??????SHA1???
     *
     *
     * @return
     * @throws OutOfMemoryError
     * @throws IOException
     * @throws NoSuchAlgorithmException //FileInputStream in,Long size
     */
    public  String getSha1(File file) throws OutOfMemoryError, IOException, NoSuchAlgorithmException {
        MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /**
     * @Description ?????????????????????     * @return String
     */
    private  String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E','F' };
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
            char c1 = hexDigits[bytes[l] & 0xf];
            stringbuffer.append(c0);
            stringbuffer.append(c1);
        }
        return stringbuffer.toString();
    }

    public String getFileSHA1(File file) {
        MessageDigest md = null;
        FileInputStream fis = null;
        StringBuilder sha1Str = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            MappedByteBuffer mbb = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            md = MessageDigest.getInstance("SHA-1");
            md.update(mbb);
            byte[] digest = md.digest();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    sha1Str.append(0);
                }
                sha1Str.append(shaHex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return sha1Str.toString();
    }


    public  String   getStatus(String   id){

        String status="success";
        Shebei  sb=shebeiDao.get(id);
        String param =sb.getChuchangbh();
        try{
            String method = "sendResultToempiricalData";
            String endpoint=Global.getConfig("jl.webservice.url");
            String output;
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(endpoint));
            call.setOperationName(new QName("http://service.empiricalData.interface.third.gsm.norteksoft.com", method));
            call.addParameter("in0", XMLType.XSD_INTEGER, ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);
            call.setUseSOAPAction(true);
            output = (String) call.invoke(new Object[]{param});
            JSONObject  json=JSONObject.fromObject(output);
            String code=json.get("code")==null?"":json.getString("code");
            String examineResult=json.get("examineResult")==null?"":json.getString("examineResult");
            if("1".equals(code)){
                 if("1".equals(examineResult)){
                     shebeiDao.updateStatus("1",id);
                 }else{
                     shebeiDao.updateStatus("0",id);
                 }
            }
        }catch (Exception  e){
            e.printStackTrace();
            status="error";
            return status;
        }

        return status;
    }

    /**
     * ????????????????????????
     * @param page
     * @param entity
     * @return
     */
    public Page<DataBaseInfo> selectInfo(Page<DataBaseInfo> page, DataBaseInfo entity) {
        entity.setPage(page);
        page.setList(dao.selectInfo(entity));
        return page;
    }

    public List<Map<String, Object>> pie(DataBaseInfo entity){
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        if("2".equals(entity.getCompany())){
            int testCount = dao.testCount(entity);//??????
            if(testCount!=0){
                map.put("name","???????????????");
                map.put("count",testCount);
                result.add(map);
            }
        }else if("4".equals(entity.getCompany())){
            int testFlyCount = dao.testFlyCount(entity);
            if(testFlyCount!=0){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("name","???????????????");
                map1.put("count",testFlyCount);
                result.add(map1);
            }
        }else{
            int testFlyCount = dao.testFlyCount(entity);
            int testCount = dao.testCount(entity);//??????
            if(testCount!=0){
                map.put("name","???????????????");
                map.put("count",testCount);
                result.add(map);
            }
            if(testFlyCount!=0){
                Map<String,Object> map1 = new HashMap<>();
                map1.put("name","???????????????");
                map1.put("count",testFlyCount);
                result.add(map1);
            }
        }
        return result;
    }

    /**
     * ???????????? B-KB-MB-GB
     * @param o
     * @return
     */
    public static String getSize(int o){
        double d1 = o;
        long mb = 1024 * 1024;
        double size = d1/mb;
        String s = Double.toString(size);
        BigDecimal bd   =   new   BigDecimal(s);
        bd   =   bd.setScale(2,BigDecimal.ROUND_HALF_UP);
        String s1 = bd.toString();
        return s1;
    }

    public String getRoleByUser(String userId){
        return this.dao.getRoleByUser(userId);
    }

}
