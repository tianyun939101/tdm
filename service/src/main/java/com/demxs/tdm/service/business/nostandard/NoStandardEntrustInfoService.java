package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustInfoDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustReportDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardExecutionDao;
import com.demxs.tdm.dao.resource.attach.AttachmentInfoDao;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.constant.NoStandardEntrustInfoEnum;
import com.demxs.tdm.domain.business.constant.NoStandardResourceEnum;
import com.demxs.tdm.domain.business.nostandard.*;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.util.CodeUtil;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class NoStandardEntrustInfoService extends CrudService<NoStandardEntrustInfoDao, NoStandardEntrustInfo> {

    @Autowired
    private NoStandardTestItemService  testItemService;
    @Autowired
    private NoStandardATAChapterService ataChapterService;
    @Autowired
    private ATAChapterService chapterService;
    @Autowired
    private NoStandardBeforeTestService beforeTestSerevice;
    @Autowired
    private NoStandardResourceService resourceService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private LabInfoService labInfoService;
    @Autowired
    private NoStandardExecutionDao executionDao;
    @Autowired
    private NoStandardEntrustReportDao reportDao;
    @Autowired
    private AttachmentInfoDao attachmentInfoDao;

    @Override
    public NoStandardEntrustInfo get(String id) {
        NoStandardEntrustInfo entrustInfo = super.get(id);
        if(null != entrustInfo){
            entrustInfo.setResource(resourceService.getOtherUsers(id));
            entrustInfo.setExecution(executionDao.getByEntrustId(id));
            entrustInfo.setReport(reportDao.getBaseByEntrustId(id));
            List<NoStandardATAChapter> ataChapterList = entrustInfo.getATAChapterList();
            if(null != ataChapterList && !ataChapterList.isEmpty()){
                //设置ata章节完全限定名
                StringBuilder sb = new StringBuilder();
                for (NoStandardATAChapter ataChapter : ataChapterList) {
                    if(null != ataChapter && StringUtils.isNotBlank(ataChapter.getAtaChapter().getParentId())){
                        String parentId = ataChapter.getAtaChapter().getParentId();
                        List<String> list = chapterService.getParentName(new ArrayList<>(), parentId);
                        for (int i = list.size() -1; i >= 0; i--) {
                            sb.append(list.get(i)).append("：");
                        }
                        sb.append(ataChapter.getName()).append("。");
                    }
                }
                entrustInfo.setAtaChapterFullName(sb.toString());
            }
        }
        return entrustInfo;
    }

    /**
    * @author Jason
    * @date 2020/4/14 17:18
    * @params [entrustInfo]
    * 查询指定状态条目数
    * @return java.lang.Integer
    */
    public Integer selectStatusCount(NoStandardEntrustInfo entrustInfo){
        return this.dao.selectStatusCount(entrustInfo);
    }

    /**
    * @author Jason
    * @date 2020/4/14 17:18
    * @params [entrustInfo]
    * 查询数据缺失条目数
    * @return java.lang.Integer
    */
    public Integer selectIncompleteCount(NoStandardEntrustInfo entrustInfo){
        return this.dao.selectIncompleteCount(entrustInfo);
    }

    /**
    * @author Jason
    * @date 2020/4/14 17:23
    * @params [entrustInfo]
    * 查询数据缺失项
    * @return java.util.List<com.demxs.tdm.domain.business.nostandard.NoStandardEntrustInfo>
    */
    public List<NoStandardEntrustInfo> selectIncomplete(NoStandardEntrustInfo entrustInfo){
        List<NoStandardEntrustInfo> list = this.testMonitorList(entrustInfo);
        list.removeIf(o -> o.getReport() != null && StringUtils.isNotBlank(o.getReport().getFile()));
        return list;
    }

    /**
    * @author Jason
    * @date 2020/4/14 17:18
    * @params [entrustInfo]
    * 试验监控list页面
    * @return java.util.List<com.demxs.tdm.domain.business.nostandard.NoStandardEntrustInfo>
    */
    public List<NoStandardEntrustInfo> testMonitorList(NoStandardEntrustInfo entrustInfo){
        List<NoStandardEntrustInfo> list = this.dao.findList(entrustInfo);
        for(NoStandardEntrustInfo info : list){
            //设置任务执行信息
            NoStandardExecution execution = executionDao.getByEntrustId(info.getId());
            if(null != execution){
                StringBuilder fileName = new StringBuilder();
                List<NoStandardExecutionItem> itemList = execution.getTestItemList();
                //设置每一个item上传的文件的名称
                for(NoStandardExecutionItem item : itemList){
                    if(StringUtils.isNotBlank(item.getFile())){
                        String[] split = item.getFile().split(",");
                        for(String s: split){
                            if(StringUtils.isNotBlank(s)){
                                AttachmentInfo attachmentInfo = attachmentInfoDao.get(s);
                                if(null != attachmentInfo){
                                    fileName.append(attachmentInfo.getName()).append("，");
                                }
                            }
                        }
                    }
                }
                if(fileName.length() > 0){
                    fileName.deleteCharAt(fileName.length()-1);
                }
                execution.setFileName(fileName.toString());
            }
            info.setExecution(execution);
            //设置报告
            info.setReport(reportDao.getBaseByEntrustId(info.getId()));
            //设置ATA章节信息
            info.setATAChapterList(ataChapterService.getByEntrustId(info.getId()));
        }
        return list;
    }

    @Transactional(readOnly = false)
    public int changeStatus(NoStandardEntrustInfo entrustInfo){
        return super.dao.changeStatus(entrustInfo);
    }

    @Transactional(readOnly = false)
    public void save(NoStandardEntrustInfo entrustInfo){
        if(null == entrustInfo.getOrg() || StringUtils.isBlank(entrustInfo.getOrg().getId())){
            User user = UserUtils.getUser();
            entrustInfo.setOrg(user.getOffice());
        }
        if(NoStandardEntrustInfoEnum.RESOURCE.getCode().equals(entrustInfo.getStatus())){
            entrustInfo.setCode(CodeUtil.getNoStandardEntrustCode());
        }
        super.save(entrustInfo);
        testItemService.deleteByEntrustId(entrustInfo.getId());
        List<NoStandardTestItem> testItemList = entrustInfo.getTestItemList();
        if(null != testItemList){
            for(int i = 0;i < testItemList.size();i++){
                NoStandardTestItem testItem = testItemList.get(i);
                testItem.setEntrustId(entrustInfo.getId());
                testItemService.save(testItem);
            }
        }

        ataChapterService.deleteByEntrustId(entrustInfo.getId());
        List<NoStandardATAChapter> ataChapterList = entrustInfo.getATAChapterList();
        if(null != ataChapterList){
            for(int i = 0;i < ataChapterList.size();i++){
                NoStandardATAChapter ataChapter = ataChapterList.get(i);
                ataChapter.setEntrustId(entrustInfo.getId());
                ataChapterService.save(ataChapter);
            }
        }

        if(NoStandardEntrustInfoEnum.RESOURCE.getCode().equals(entrustInfo.getStatus())){
            //实验室负责人
            User u = labInfoService.get(entrustInfo.getLabId()).getLeader();

            //资源分配状态
            String resourceId = IdGen.uuid();
            String beforeId = IdGen.uuid();

            //添加试验前检查信息
            NoStandardBeforeTest beforeTest = new NoStandardBeforeTest(beforeId);
            beforeTest.setIsNewRecord(true);
            beforeTest.setEntrustId(entrustInfo.getId());
            beforeTest.setResourceId(resourceId);

            //添加资源分配信息
            NoStandardEntrustResource resource = new NoStandardEntrustResource(resourceId);
            resource.setIsNewRecord(true);
            resource.setEntrustId(entrustInfo.getId());
            resource.setEntrustCode(entrustInfo.getCode());
            resource.setStatus(NoStandardResourceEnum.RESOURCE.getCode());
            resource.setBeforeId(beforeId);
            resource.setApplicant(entrustInfo.getApplicant());
            resource.setLabId(entrustInfo.getLabId());
            resource.setCurAuditUser(u.getId());

            resourceService.save(resource);
            beforeTestSerevice.save(beforeTest);


            User createBy = entrustInfo.getCreateBy();
            if(null == createBy){
                entrustInfo = this.dao.getBase(entrustInfo.getId());
                createBy = entrustInfo.getCreateBy();
            }
            String taskTile = createBy.getName() +"("+resource.getEntrustCode()+")";

            //启动流程
            Map<String,Object> model = new HashMap<>();
            model.put("userName",createBy.getName());
            model.put("code",resource.getEntrustCode());

            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_AUDIT,model);

            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            vars.put("code", entrustInfo.getCode());
            actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.NO_STANDARD_PROCESS,
                    u.getLoginName(),resource.getEntrustId(),taskTile,vars);

            NoStandardEntrustInfo newEntrustInfo = new NoStandardEntrustInfo(entrustInfo.getId());
            newEntrustInfo.setCurAuditUser(u.getId());
            this.updateActive(newEntrustInfo);
        }else if(NoStandardEntrustInfoEnum.EDIT.getCode().equals(entrustInfo.getStatus())){
            //正在编辑状态
            //do something
        }

    }

    @Transactional(readOnly = false)
    public int updateActive(NoStandardEntrustInfo entrustInfo){
        return super.dao.updateActive(entrustInfo);
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

    @Transactional(readOnly = true)
    public NoStandardEntrustInfo getByEntrustId(String id){
        return super.dao.getById(id);
    }

}
