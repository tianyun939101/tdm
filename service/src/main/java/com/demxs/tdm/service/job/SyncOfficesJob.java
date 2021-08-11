package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SysQuartzLogService;
import com.demxs.tdm.domain.external.EhrDeptResult;
import com.demxs.tdm.domain.sys.SysQuartzLog;
import com.demxs.tdm.comac.common.constant.SysConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SyncOfficesJob {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OfficeService officeService;
    @Autowired
    private IExternalApi externalApi;
    @Autowired
    private SysQuartzLogService sysQuartzLogService;
    public  void  work() {
        logger.error("同步机构数据开始----");

        List<SysQuartzLog> sysQuartzLogs = sysQuartzLogService.getTodayDataByType(SysConstants.QUARTZ_TYPE.SYNCOFFICES);
        if(CollectionUtils.isEmpty(sysQuartzLogs)){
            SysQuartzLog quartzLog = new SysQuartzLog();
            quartzLog.setStartDate(new Date());
            quartzLog.setType(SysConstants.QUARTZ_TYPE.SYNCOFFICES);
            String apiAddress = "http://ehr.longi-silicon.com:8200/psc/ps/EMPLOYEE/HRMS/s/WEBLIB_LGI.ISCRIPT1.FieldFormula.IScript_Main?postDatabin=y";
            String userName = "PSAPPS";
            String password = "PSAPPS";
            String synData = getPreDate();
            try {
                List<EhrDeptResult> depts = externalApi.getEhrDeptList(apiAddress,userName,password,synData,"M");
                if(CollectionUtils.isNotEmpty(depts)){
                    officeService.saveFromPS(depts);
                }
                quartzLog.setResult(SysConstants.QUARTZ_RESULT.SUCCESS);
            } catch (Exception e) {
                quartzLog.setResult(SysConstants.QUARTZ_RESULT.FAIL);
                quartzLog.setExcepts(e.getMessage());
                logger.error("同步机构数据错误",e);
            }
            quartzLog.setEndDate(new Date());
            sysQuartzLogService.save(quartzLog);
        }

    }

    private String  getPreDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return  sdf.format(date);
    }
}
