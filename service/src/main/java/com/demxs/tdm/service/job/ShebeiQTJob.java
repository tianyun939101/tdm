package com.demxs.tdm.service.job;

import com.demxs.tdm.service.business.TestPlanExecuteDetailService;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.shebei.ShebeiQitingjlService;
import com.demxs.tdm.service.resource.shebei.ShebeiService;
import com.demxs.tdm.common.utils.SpringContextHolder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * 设备启停任务
 */
public class ShebeiQTJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ShebeiQTJob.class);

    private static ExternalApi externalApi = SpringContextHolder.getBean(ExternalApi.class);
    private static TestPlanExecuteDetailService testPlanExecuteDetailService = SpringContextHolder.getBean(TestPlanExecuteDetailService.class);
    private static ShebeiService shebeiService = SpringContextHolder.getBean(ShebeiService.class);
    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);
    private static ShebeiQitingjlService shebeiQitingjlService = SpringContextHolder.getBean(ShebeiQitingjlService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("设备启停任务执行开始....");
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String shebeiid = (String)jobDataMap.get("shebeiid");
        String remarks = (String)jobDataMap.get("remarks");
        long l = Long.valueOf(String.valueOf(jobDataMap.get("qtTime"))).longValue();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        Date qtDate = c.getTime();
        String type = (String)jobDataMap.get("type");
        String jobId = (String)jobDataMap.get("id");
        String jlid = (String)jobDataMap.get("jlid");//使用记录id
        quartzJobService.updateExecuteType(jobId);
        shebeiService.upQtStatus(shebeiid,type,qtDate,remarks);
        shebeiQitingjlService.updateValidById(jlid);

    }

}
