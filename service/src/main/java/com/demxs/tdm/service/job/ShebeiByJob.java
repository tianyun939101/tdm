package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.shebei.ShebeiByjhService;
import com.demxs.tdm.service.resource.shebei.ShebeiByjlService;
import com.demxs.tdm.service.sys.SysQuartzLogService;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.ShebeiByjl;
import com.demxs.tdm.domain.sys.SysQuartzLog;
import com.demxs.tdm.comac.common.constant.SysConstants;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ShebeiByJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ShebeiByJob.class);

    private static ExternalApi externalApi = SpringContextHolder.getBean(ExternalApi.class);
    private static ShebeiByjhService shebeiByjhService = SpringContextHolder.getBean(ShebeiByjhService.class);
    private static ShebeiByjlService shebeiByjlService = SpringContextHolder.getBean(ShebeiByjlService.class);
    private static SysQuartzLogService sysQuartzLogService = SpringContextHolder.getBean(SysQuartzLogService.class);
    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.error("设备保养任务执行开始....");
        SysQuartzLog quartzLog = new SysQuartzLog();
        quartzLog.setStartDate(new Date());
        quartzLog.setType(SysConstants.QUARTZ_TYPE.BY);
        quartzLog.setResult(SysConstants.QUARTZ_RESULT.SUCCESS);
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jihuaid = (String)jobDataMap.get("jihuaid");
        String jiluid = (String)jobDataMap.get("jiluid");
        String shebeiid = (String)jobDataMap.get("shebeiid");
        String shebeibh = (String)jobDataMap.get("shebeibh");
        String loginName = (String)jobDataMap.get("loginName");
        String txDate = (String)jobDataMap.get("txDate");
        String jobId = (String)jobDataMap.get("id");
        quartzJobService.updateExecuteType(jobId);
        //判断该记录有没有定检
        ShebeiByjl jl = shebeiByjlService.get(jiluid);
        if(!StringUtils.isNotBlank(jl.getBaoyangjg())){
            //未定检
            shebeiByjhService.sendByTodo(shebeiid,shebeibh,loginName, DateUtils.parseDate(txDate),jihuaid);
        }
        quartzLog.setEndDate(new Date());
        sysQuartzLogService.save(quartzLog);

    }
}
