package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.nostandard.NoStandardTestCheckDao;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.NoStandardTestCheckEnum;
import com.demxs.tdm.domain.business.nostandard.NoStandardOtherUser;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck;
import com.demxs.tdm.domain.dataCenter.DataBaseInfo;
import com.demxs.tdm.domain.dataCenter.DataConstant;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.Lab;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.business.configuration.ConfigurationVOService;
import com.demxs.tdm.service.business.util.CodeUtil;
import com.demxs.tdm.service.dataCenter.DataBaseInfoService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.yuangong.YuangongService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wuhui
 * @date 2020/12/9 15:49
 **/
@Service
@Transactional(readOnly = true)
public class NoStandardTestCheckService extends CrudService<NoStandardTestCheckDao, NoStandardTestCheck> {

    @Autowired
    private NoStandardOtherUserService otherUserService;
    @Autowired
    private DataBaseInfoService dataBaseInfoService;
    @Autowired
    private ConfigurationVOService configurationVOService;
    @Autowired
    private ATAChapterService ataChapterService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private YuangongService yuangongService;
    @Autowired
    private NoStandardOtherUserService noStandardOtherUserService;

    @Autowired
    private SystemService systemService;
    @Autowired
    private NoStandardTestCheckDao noStandardTestCheckDao;

    /**
     * @Describe:??????
     * @Author:WuHui
     * @Date:10:01 2020/12/11
     * @param testCheck
     * @return:void
    */
    public void saveTestCheck(NoStandardTestCheck testCheck) {
        //????????????????????????
        if(StringUtils.isEmpty(testCheck.getCode())){
            testCheck.setCode(CodeUtil.getNoNewStandardEntrustCode());
        }
        testCheck.setStatus(NoStandardTestCheckEnum.CHECK.getCode());
        testCheck.setTestManagerId(testCheck.getTestManager().getId());
        this.save(testCheck);
        //????????????????????????
        otherUserService.deleteByResourceId(testCheck.getId());
        for(NoStandardOtherUser otherUser:testCheck.getOtherUsers() ){
            otherUser.setResourceId(testCheck.getId());
            otherUserService.save(otherUser);
        }
        //??????????????????????????? ??????????????????
        if(StringUtils.isEmpty(testCheck.getReportId())){
            LabInfo labinfo = labInfoService.get(testCheck.getLabId());
            DataBaseInfo dataBaseInfo = new DataBaseInfo(testCheck.getTestName(),testCheck.getTaskNo(),
                    testCheck.getTaskVersion(),testCheck.getTaskView(),testCheck.getPlanNo(),
                    testCheck.getTestNature(),testCheck.getProductModel(),
                    testCheck.getAtaChapter(),testCheck.getTestOutlineCode(),testCheck.getTestOutlineVersion()
                    ,testCheck.getTestView(),labinfo);
            dataBaseInfo.setCompany("2");//?????????????????????????????????
            dataBaseInfoService.save(dataBaseInfo);
            testCheck.setReportId(dataBaseInfo.getId());
        }
        //?????????????????????
        if(testCheck.getSubmit()){
            User auditUser = UserUtils.get(testCheck.getTestManagerId());
            String title = "?????????'"+UserUtils.getUser().getName()+"'?????????????????????????????????";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            actTaskService.apiStartProcess("testCheckFlow", auditUser.getLoginName(), testCheck.getId(), title, vars);
            testCheck.setStatus(NoStandardTestCheckEnum.MANAGER_AUDIT.getCode());
            //????????????
            AuditInfo auditInfo = new AuditInfo(testCheck.getId(),UserUtils.getUser(),1,"????????????");
            auditInfoService.save(auditInfo);
        }
        this.save(testCheck);
    }

    /*
     * @Describe:??????????????????
     * @Author:WuHui
     * @Date:10:01 2020/12/11
     * @param id
     * @param action
     * @return:com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck
    */
    public NoStandardTestCheck getById(String id,String action){
        NoStandardTestCheck testCheck = null;
        if(org.apache.commons.lang3.StringUtils.isNotBlank(id)){
            testCheck = noStandardTestCheckDao.get(id);
        }else {
            testCheck = new NoStandardTestCheck();
        }
        if("create".equals(action)){
            User applicant = UserUtils.getUser();
            testCheck.setApplicant(applicant.getId());
            testCheck.setOrg(applicant.getOffice().getId());
            testCheck.setApplicantUser(applicant);
            testCheck.setOffice(applicant.getOffice());
            testCheck.setStatus(NoStandardTestCheckEnum.CHECK.getCode());
        }else{
            //??????????????????
            testCheck.setConfiguration(configurationVOService.get(testCheck.getConfigurationId()));
            testCheck.setApplicantUser(UserUtils.get(testCheck.getApplicant()));
            //??????ATA??????
            ATAChapter ataChapter = ataChapterService.get(testCheck.getAtaChapter());
            testCheck.getATAChapterList().add(ataChapter);
        }
        if(testCheck.getTestManager() != null){
            testCheck.setTestManagerId(testCheck.getTestManager().getId());
        }
        testCheck.setId(id);
        LabInfo labinfo = labInfoService.get(testCheck.getLabId());
        testCheck.setLabInfo(labinfo);
        testCheck.setAuditList(auditInfoService.getByKey(testCheck.getId()));
        testCheck.setTestManager(yuangongService.getByUserId(testCheck.getTestManagerId()));
        testCheck.setOtherUsers(noStandardOtherUserService.getByResourceId(testCheck.getId()));
        return testCheck;
    }

    /**
     * @Describe:??????
     * @Author:WuHui
     * @Date:10:01 2020/12/11
     * @param approveDto
     * @return:void
    */
    public void approveTest(ApproveDTO approveDto) {
        //????????????
        NoStandardTestCheck testCheck = this.get(approveDto.getId());
        String status=testCheck.getStatus();

        testCheck.setId(approveDto.getId());
        User auditUser=null;
        //???????????????????????????
        if("06".equals(status)){
            auditUser=systemService.getUser(testCheck.getCreateBy().getId());
        }else {
            auditUser = UserUtils.get(approveDto.getNextUser());
        }
        Map<String,Object> vars = new HashMap<>(1);
        //??????
        String title = "?????????'"+testCheck.getApplicantUser().getName()+"'?????????????????????????????????";
        vars.put("message", title);
        vars.put("reportId",testCheck.getReportId());
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(testCheck.getId(),approveDto.getOpinion(), Global.YES, assignee,vars);
        //????????????
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //??????????????????????????????
        if(auditUser!=null)
            testCheck.setCurAudItUser(auditUser.getId());
        switch(NoStandardTestCheckEnum.get(testCheck.getStatus())){
            case MANAGER_AUDIT:
                status = NoStandardTestCheckEnum.MANAGER_CHECK.getCode();
                break;
            case MANAGER_CHECK:
                status = NoStandardTestCheckEnum.REPORT.getCode();
                //????????????????????????
                dataBaseInfoService.addDataBasePerm(testCheck.getReportId(),auditUser);
                break;
            case REPORT:
                status = NoStandardTestCheckEnum.AUDIT.getCode();
                break;
            case AUDIT:
                status = NoStandardTestCheckEnum.APPROVE.getCode();
                break;
            default:
                break;
        }
        testCheck.setStatus(status);
        this.save(testCheck);
    }

    /**
     * @Describe:??????
     * @Author:zwm
     * @Date:10:01 2021/01/09
     * @param approveDto
     * @return:void
     */
    public void approve(ApproveDTO approveDto) {
        //????????????
        NoStandardTestCheck testCheck = this.get(approveDto.getId());
        String status=testCheck.getStatus();
        testCheck.setId(approveDto.getId());
        User auditUser=null;
        Map<String,Object> vars = new HashMap<>(1);
        List<String> userLoginNames = new ArrayList<>();
        User  user=null;
        //???????????????????????????
        if("02".equals(status)|| "04".equals(status)){
            List<User> list=new ArrayList<>();
            switch(status){
                case "02":
                    list=systemService.getUserByRoleName("?????????????????????");
                    break;
                case "04":
                    list=systemService.getUserByRoleName("???????????????");
                    break;
            }
            StringBuilder userNameSb = new StringBuilder();
            StringBuilder userIdSb = new StringBuilder();
           for(User u:list){
               userIdSb.append(u.getId()).append(",");
               userNameSb.append(u.getName()).append(",");
               userLoginNames.add(u.getLoginName());
           }
            auditUser=UserUtils.get(userIdSb.substring(0, userIdSb.length() - 1));
           // AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
          // auditInfoService.save(auditInfo);

            testCheck.setCurAudItUser(userIdSb.substring(0, userIdSb.length() - 1));
            testCheck.setCurAudItUserName(userNameSb.substring(0,userNameSb.length()-1));
            String title = "?????????'"+testCheck.getApplicantUser().getName()+"'?????????????????????????????????";
            vars.put("message", title);
            vars.put("reportId",testCheck.getReportId());
            actTaskService.apiComplete(testCheck.getId(),approveDto.getOpinion(), Global.YES, StringUtils.join(userLoginNames,";"),vars);
        }else {
            //???????????????????????????
            if("06".equals(status)){
                auditUser=systemService.getUser(testCheck.getCreateBy().getId());
               //  AuditInfo auditInfo = new AuditInfo(testCheck.getId(), UserUtils.get(testCheck.getCurAudItUser()),1,approveDto.getOpinion());
               // auditInfoService.save(auditInfo);
            }else{
                auditUser = UserUtils.get(approveDto.getNextUser());

            }
            String[] ss=testCheck.getCurAudItUser().split(",");
            AuditInfo auditInfo = new AuditInfo(testCheck.getId(), UserUtils.get(ss[0]),1,approveDto.getOpinion());
            auditInfoService.save(auditInfo);

           /* List<AuditInfo>  list=auditInfoService.getByKey(testCheck.getId());
            if(CollectionUtils.isNotEmpty(list)){
                 list=list.stream().sorted(Comparator.comparing(AuditInfo::getCreateDate).reversed()).collect(Collectors.toList());
                AuditInfo auditInfo = new AuditInfo(testCheck.getId(), UserUtils.get(list.get(0).getAuditUser().getId()),1,approveDto.getOpinion());
                auditInfoService.save(auditInfo);
            }*/
            //??????
            String title = "?????????'"+testCheck.getApplicantUser().getName()+"'?????????????????????????????????";
            vars.put("message", title);
            vars.put("reportId",testCheck.getReportId());
            String assignee = auditUser== null ? "" : auditUser.getLoginName();
            actTaskService.apiComplete(testCheck.getId(),approveDto.getOpinion(), Global.YES, assignee,vars);
            //????????????
           // AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
           // auditInfoService.save(auditInfo);
            //??????????????????????????????
            if(auditUser!=null)
                testCheck.setCurAudItUser(auditUser.getId());
        }
        switch(NoStandardTestCheckEnum.get(testCheck.getStatus())){
            case MANAGER_AUDIT:
                status = NoStandardTestCheckEnum.MANAGER_CHECK.getCode();
                auditUser = UserUtils.get(testCheck.getTestManagerId());
                AuditInfo auditInfo = new AuditInfo(testCheck.getId(),auditUser,1,approveDto.getOpinion());
                auditInfoService.save(auditInfo);
                break;
            case MANAGER_CHECK:
                status = NoStandardTestCheckEnum.REPORT.getCode();
                //????????????????????????
                dataBaseInfoService.addDataBasePerm(testCheck.getReportId(),UserUtils.getUser());
                break;
            case REPORT:
                status = NoStandardTestCheckEnum.AUDIT.getCode();
                break;
            case AUDIT:
                status = NoStandardTestCheckEnum.APPROVE.getCode();
                break;
            default:
                break;
        }
        testCheck.setStatus(status);
        this.save(testCheck);
    }
    /**
     * @Describe:??????
     * @Author:WuHui
     * @Date:10:38 2020/12/11
     * @param approveDto
     * @return:void
    */
    public void reject(ApproveDTO approveDto) {
        //????????????
        NoStandardTestCheck testCheck = this.get(approveDto.getId());
        testCheck.setId(approveDto.getId());
        //???????????????
        User auditUser = UserUtils.get(testCheck.getApplicant());
        Map<String,Object> vars = new HashMap<>(1);
        //??????
        String title = "??????????????????????????????????????????'"+UserUtils.getUser().getName()+"'?????????";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(testCheck.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //????????????
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //??????????????????????????????
        testCheck.setCurAudItUser(auditUser.getId());
        testCheck.setStatus(NoStandardTestCheckEnum.CHECK.getCode());
        this.save(testCheck);
    }

    /**
     * @Describe:?????????????????? ?????? ?????????????????????
     * @Author:WuHui
     * @Date:14:25 2020/12/11
     * @param reportId
     * @return:com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck
    */
    public NoStandardTestCheck getTestCheckByReportId(String reportId){
        return this.dao.getTestCheckByReportId(reportId);
    }

    public  String getFileEncode(String path) {
        String charset ="asci";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(path));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "Unicode";//UTF-16LE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "Unicode";//UTF-16BE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int len = 0;
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) //????????????BF?????????????????????GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)
                            //????????? (0xC0 - 0xDF) (0x80 - 0xBF),????????????GB?????????
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { //???????????????????????????????????????
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                //TextLogger.getLogger().info(loc + " " + Integer.toHexString(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                }
            }
        }
        return charset;
    }
}
