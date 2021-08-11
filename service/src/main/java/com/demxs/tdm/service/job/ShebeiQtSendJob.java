package com.demxs.tdm.service.job;

import com.demxs.tdm.service.business.TestPlanExecuteDetailService;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.shebei.ShebeiService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.SpringContextHolder;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShebeiQtSendJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ShebeiQtSendJob.class);

    private static TestPlanExecuteDetailService testPlanExecuteDetailService = SpringContextHolder.getBean(TestPlanExecuteDetailService.class);
    private static ShebeiService shebeiService = SpringContextHolder.getBean(ShebeiService.class);
    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("设备启停发消息任务执行开始....");
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jlid = (String)jobDataMap.get("jlid");
        String shebeiid = (String)jobDataMap.get("shebeiid");
        String shebeibh = (String)jobDataMap.get("shebeibh");
        String shebeimc = (String)jobDataMap.get("shebeimc");
        String type = (String)jobDataMap.get("type");
        long l = Long.valueOf(String.valueOf(jobDataMap.get("qtTime"))).longValue();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);
        Date qtDate = c.getTime();
        List<User> users = testPlanExecuteDetailService.getTestChargeByDeviceuse(shebeibh,qtDate);
        if(CollectionUtils.isNotEmpty(users)){
            for(User u:users){
                if(u!=null){
                    shebeiService.sendTodo(shebeiid,shebeibh,type,u,qtDate,shebeimc);
                }

            }
        }
        String jobId = (String)jobDataMap.get("id");
        quartzJobService.updateExecuteType(jobId);
    }
}
