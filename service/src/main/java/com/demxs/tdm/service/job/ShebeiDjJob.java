package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.shebei.ShebeiJljdjhService;
import com.demxs.tdm.service.resource.shebei.ShebeiJljdjlService;
import com.demxs.tdm.service.sys.SysQuartzLogService;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjl;
import com.demxs.tdm.domain.sys.SysQuartzLog;
import com.demxs.tdm.comac.common.constant.SysConstants;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ShebeiDjJob implements Job {
    private Logger logger = LoggerFactory.getLogger(ShebeiDjJob.class);

    private static ExternalApi externalApi = SpringContextHolder.getBean(ExternalApi.class);
    private static ShebeiJljdjhService shebeiJljdjhService = SpringContextHolder.getBean(ShebeiJljdjhService.class);
    private static ShebeiJljdjlService shebeiJljdjlService = SpringContextHolder.getBean(ShebeiJljdjlService.class);
    private static SysQuartzLogService sysQuartzLogService = SpringContextHolder.getBean(SysQuartzLogService.class);

    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("设备定检任务执行开始....");
        SysQuartzLog quartzLog = new SysQuartzLog();
        quartzLog.setStartDate(new Date());
        quartzLog.setType(SysConstants.QUARTZ_TYPE.DJ);
        quartzLog.setResult(SysConstants.QUARTZ_RESULT.SUCCESS);

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobId = (String)jobDataMap.get("id");
        String jihuaid = (String)jobDataMap.get("jihuaid");
        String jiluid = (String)jobDataMap.get("jiluid");
        String shebeiid = (String)jobDataMap.get("shebeiid");
        String shebeibh = (String)jobDataMap.get("shebeibh");
        String loginName = (String)jobDataMap.get("loginName");
        String txDate = (String)jobDataMap.get("txDate");
       /* long l = Long.valueOf(String.valueOf(jobDataMap.get("jiluDate"))).longValue();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        Date jiluDate = c.getTime();*/
        //判断该记录有没有定检
        ShebeiJljdjl jl = shebeiJljdjlService.get(jiluid);
        if(!StringUtils.isNotBlank(jl.getJiliangjdjg())){
            //未定检
            shebeiJljdjhService.sendDjTodo(shebeiid,shebeibh,loginName, DateUtils.parseDate(txDate),jihuaid);
        }
        quartzJobService.updateExecuteType(jobId);
        quartzLog.setEndDate(new Date());
        sysQuartzLogService.save(quartzLog);

    }
}
