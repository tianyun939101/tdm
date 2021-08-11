package com.demxs.tdm.service.job;

import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.shebei.ShebeiJljdjhService;
import com.demxs.tdm.service.resource.shebei.ShebeiJljdjlService;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShebeiDjTxJob implements Job {

    private static ShebeiJljdjhService shebeiJljdjhService = SpringContextHolder.getBean(ShebeiJljdjhService.class);
    private static com.demxs.tdm.service.resource.shebei.ShebeiJljdjlService ShebeiJljdjlService = SpringContextHolder.getBean(ShebeiJljdjlService.class);
    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);
    private Logger logger = LoggerFactory.getLogger(ShebeiDjTxJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.error("设备定检提醒任务执行开始....");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jihuaid = (String)jobDataMap.get("jihuaid");
        String shebeiid = (String)jobDataMap.get("shebeiid");
        String shebeibh = (String)jobDataMap.get("shebeibh");
        String loginName = (String)jobDataMap.get("loginName");
        String txDate = (String)jobDataMap.get("txDate");
        String jobId = (String)jobDataMap.get("id");
        quartzJobService.updateExecuteType(jobId);
        shebeiJljdjhService.sendDjTxTodo(shebeiid,shebeibh,loginName, DateUtils.parseDate(txDate),jihuaid);

    }
}
