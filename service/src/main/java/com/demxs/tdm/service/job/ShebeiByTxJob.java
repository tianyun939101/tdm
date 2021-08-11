package com.demxs.tdm.service.job;

import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.shebei.ShebeiByjhService;
import com.demxs.tdm.service.resource.shebei.ShebeiByjlService;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShebeiByTxJob implements Job {

    private static ShebeiByjhService shebeiByjhService = SpringContextHolder.getBean(ShebeiByjhService.class);
    private static ShebeiByjlService shebeiByjlService = SpringContextHolder.getBean(ShebeiByjlService.class);
    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);


    private Logger logger = LoggerFactory.getLogger(ShebeiDjTxJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.error("设备保养提醒任务执行开始....");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jihuaid = (String)jobDataMap.get("jihuaid");
        String shebeiid = (String)jobDataMap.get("shebeiid");
        String shebeibh = (String)jobDataMap.get("shebeibh");
        String loginName = (String)jobDataMap.get("loginName");
        String txDate = (String)jobDataMap.get("txDate");
        String jobId = (String)jobDataMap.get("id");
        quartzJobService.updateExecuteType(jobId);
        shebeiByjhService.sendByTxTodo(shebeiid,shebeibh,loginName, DateUtils.parseDate(txDate),jihuaid);
    }
}
