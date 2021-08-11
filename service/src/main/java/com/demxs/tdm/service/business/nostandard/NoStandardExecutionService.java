package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustInfoDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardExecutionDao;
import com.demxs.tdm.dao.resource.shebei.EquipmentUseRecordDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationDeviceArticle;
import com.demxs.tdm.domain.business.configuration.ConfigurationFacilityArticle;
import com.demxs.tdm.domain.business.configuration.ConfigurationVO;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.constant.NoStandardEntrustInfoEnum;
import com.demxs.tdm.domain.business.constant.NoStandardExecutionEnum;
import com.demxs.tdm.domain.business.constant.NoStandardReportEnum;
import com.demxs.tdm.domain.business.nostandard.NoStandardEntrustInfo;
import com.demxs.tdm.domain.business.nostandard.NoStandardEntrustReport;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecution;
import com.demxs.tdm.domain.business.nostandard.NoStandardOtherUser;
import com.demxs.tdm.domain.resource.shebei.EquipmentUseRecord;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiTaiTao;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Jason
 * @Date: 2020/3/6 09:15
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class NoStandardExecutionService extends CrudService<NoStandardExecutionDao, NoStandardExecution> {

    @Autowired
    private NoStandardEntrustInfoDao infoDao;
    @Autowired
    private NoStandardEntrustReportService reportService;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private EquipmentUseRecordDao equipmentUseRecordDao;

    public NoStandardExecution getBaseByEntrustId(String entrustId){
        return this.dao.getBaseByEntrustId(entrustId);
    }

    @Transactional(readOnly = false)
    public int changeStatus(NoStandardExecution execution){
        if(NoStandardExecutionEnum.EXECUTING.getCode().equals(execution.getStatus())){
            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(execution.getEntrustId());
            entrustInfo.setStatus(NoStandardEntrustInfoEnum.EXECUTION.getCode());
            entrustInfo.setTestUsers(execution.getOtherUserName());
            infoDao.updateActive(entrustInfo);
            this.dao.updateActive(execution.setStartDate(new Date()));
        }
        return this.dao.changeStatus(execution);
    }

    /**
    * @author Jason
    * @date 2020/7/2 16:51
    * @params [execution]
    * 完成任务
    * @return void
    */
    public void completeTask(NoStandardExecution execution){
        if(NoStandardExecutionEnum.COMPLETE.getCode().equals(execution.getStatus())){
            execution = super.get(execution.getId());
            execution.setStatus(NoStandardExecutionEnum.COMPLETE.getCode());

            NoStandardEntrustInfo entrustInfo = new NoStandardEntrustInfo(execution.getEntrustId());
            entrustInfo.setStatus(NoStandardEntrustInfoEnum.COMPILE.getCode());
            infoDao.changeStatus(entrustInfo);

            NoStandardEntrustReport report = new NoStandardEntrustReport();

            report.setEntrustCode(execution.getEntrustCode());
            report.setEntrustId(execution.getEntrustId());
            report.setExecutionId(execution.getId());
            report.setTestNatrue(execution.getEntrustInfo().getTestNature());
            report.setTestManagerId(execution.getTestManager().getId());
            report.setClientId(execution.getEntrustInfo().getClient().getId());
            report.setCode(getCode());
            report.setStatus(NoStandardReportEnum.EDIT.getCode());

            NoStandardEntrustInfo baseInfo = infoDao.getBase(entrustInfo.getId());
            List<User> authorList = systemService.getUserByLabRole(baseInfo.getLabId(), "ReportAuthor");
            List<User> technicalDirectorList = systemService.getUserByLabRole(baseInfo.getLabId(), "TechnicalDirector");

            if(authorList.isEmpty()){
                throw new ServiceException("所在试验室没有找到试验报告攥写人");
            }
            if(technicalDirectorList.isEmpty()){
                throw new ServiceException("所在试验室没有找到试验室技术负责人");
            }
            StringBuilder sb = new StringBuilder();
            StringBuilder assigneeLoginName = new StringBuilder();
            StringBuilder technicalDirectorId = new StringBuilder();
            for(User u : authorList){
                sb.append(u.getId()).append(",");
                assigneeLoginName.append(u.getLoginName()).append(";");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length()-1);
            }
            for(User u : technicalDirectorList){
                technicalDirectorId.append(u.getId()).append(",");
            }
            report.setOpUser(sb.toString());
            report.setAuditUserId(technicalDirectorId.toString());
            report.setApprovalUserId(baseInfo.getLabManager().getId());

            reportService.save(report);

            NoStandardEntrustInfo newEntrustInfo = new NoStandardEntrustInfo(entrustInfo.getId());
            newEntrustInfo.setCurAuditUser(sb.toString());
            infoDao.updateActive(newEntrustInfo);

            User createBy = systemService.getUser(execution.getEntrustInfo().getClient().getId());

            //审批
            Map<String,Object> model = new HashMap<>();
            model.put("code",execution.getEntrustCode());
            model.put("userName",createBy.getLoginName());
            String title = FreeMarkers.renderString(EntrustConstants.MessageTemplate.NO_STANDARD_EXECUTION,model);


            Map<String,Object> vars = new HashMap<>();
            vars.put("message", title);
            actTaskService.apiComplete(execution.getEntrustId(),"","",
                    assigneeLoginName.toString(),vars);

            this.dao.updateActive(execution.setCompleteDate(new Date()));
            ConfigurationVO configuration = execution.getConfiguration();
            List<ConfigurationDeviceArticle> shebeiList = configuration.getShebeiList();
            List<ConfigurationFacilityArticle> sheShiList = configuration.getSheShiList();
            if(null != shebeiList && !shebeiList.isEmpty()){
                for (ConfigurationDeviceArticle deviceArticle : shebeiList) {
                    EquipmentUseRecord useRecord = new EquipmentUseRecord();
                    useRecord.setEntrustId(execution.getEntrustId());
                    useRecord.setEntrustType(EntrustConstants.EntrustType.NO_STANDARD);
                    useRecord.setConfigId(configuration.getBaseId());
                    useRecord.setCvId(configuration.getId());
                    useRecord.setStartDate(execution.getStartDate());
                    useRecord.setCompleteDate(execution.getCompleteDate());
                    useRecord.setTestManager(execution.getTestManager().getUser().getId());
                    useRecord.setOtherUser(execution.getOpUser());
                    useRecord.setEquType(EquipmentUseRecord.EQUIPMENT);
                    useRecord.setEquId(deviceArticle.getDeviceId());
                    useRecord.preInsert();
                    equipmentUseRecordDao.insert(useRecord);
                }
            }
            if(null != sheShiList && !sheShiList.isEmpty()){
                for (ConfigurationFacilityArticle facilityArticle : sheShiList) {
                    ShebeiTaiTao shebeiTaiTao = facilityArticle.getShebeiTaiTao();
                    EquipmentUseRecord useRecord = new EquipmentUseRecord();
                    useRecord.setEntrustId(execution.getEntrustId());
                    useRecord.setEntrustType(EntrustConstants.EntrustType.NO_STANDARD);
                    useRecord.setConfigId(configuration.getBaseId());
                    useRecord.setCvId(configuration.getId());
                    useRecord.setStartDate(execution.getStartDate());
                    useRecord.setCompleteDate(execution.getCompleteDate());
                    useRecord.setTestManager(execution.getTestManager().getUser().getId());
                    useRecord.setOtherUser(execution.getOpUser());
                    useRecord.setEquType(EquipmentUseRecord.SET);
                    useRecord.setEquId(shebeiTaiTao.getId());
                    useRecord.preInsert();
                    equipmentUseRecordDao.insert(useRecord);
                }
            }
        }
        this.dao.changeStatus(execution);
    }

    /**
    * @author Jason
    * @date 2020/7/2 17:11
    * @params [id]
    * 获得单表基础信息
    * @return com.demxs.tdm.domain.business.nostandard.NoStandardExecution
    */
    public NoStandardExecution getBase(String id){
        return this.dao.getBase(id);
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

    /**
     * 数据中心
     * @param entrustId
     * @return
     */
    public NoStandardExecution getByEntrustId(String entrustId){
        return super.dao.getByEntrustId(entrustId);
    }
}
