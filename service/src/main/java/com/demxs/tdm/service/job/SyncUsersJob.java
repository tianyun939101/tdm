package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.external.convertor.EhrEmployeeConvertor;
import com.demxs.tdm.service.sys.SysQuartzLogService;
import com.demxs.tdm.service.sys.SystemService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.external.EhrEmployeeResult;
import com.demxs.tdm.domain.sys.SysQuartzLog;
import com.demxs.tdm.comac.common.constant.SysConstants;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SyncUsersJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IExternalApi externalApi;
    @Autowired
    private SystemService systemService;
    @Autowired
    private SysQuartzLogService sysQuartzLogService;

    public  void work() {
        logger.error("同步用户数据开始----");
        //判断今天有没有数据
        List<SysQuartzLog> sysQuartzLogs = sysQuartzLogService.getTodayDataByType(SysConstants.QUARTZ_TYPE.SYNCUSERS);
        if(CollectionUtils.isEmpty(sysQuartzLogs)){
            SysQuartzLog quartzLog = new SysQuartzLog();
            quartzLog.setStartDate(new Date());
            quartzLog.setType(SysConstants.QUARTZ_TYPE.SYNCUSERS);
            String apiAddress = "http://ehr.longi-silicon.com:8200/psc/ps/EMPLOYEE/HRMS/s/WEBLIB_LGI.ISCRIPT1.FieldFormula.IScript_Main?postDatabin=y";
            String userName = "PSAPPS";
            String password = "PSAPPS";
            String synData = getPreDate();
            try {
                List<EhrEmployeeResult> employees = externalApi.getEhrEmployeeList(apiAddress,userName,password,synData,"M");
                if(CollectionUtils.isNotEmpty(employees)){
                    List<User> users = Lists.newArrayList();
                    for(EhrEmployeeResult em:employees){
                        User u = new User();
                        u = EhrEmployeeConvertor.convertBean(em,u);
                        users.add(u);
                    }
                    systemService.syncUserFromPS(users);
                }
                quartzLog.setResult(SysConstants.QUARTZ_RESULT.SUCCESS);
            } catch (Exception e) {
                quartzLog.setResult(SysConstants.QUARTZ_RESULT.FAIL);
                quartzLog.setExcepts(e.getMessage());
                logger.error("用户数据错误",e);
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
