package com.demxs.tdm.service.quartz;

import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.domain.quartz.QuartzJob;
import com.demxs.tdm.service.job.QuartzManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class InitQuartzBeanListenner implements ApplicationListener<ContextRefreshedEvent> {

    private static QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        if(event.getApplicationContext().getParent()==null){//root application context 没有parent，他就是老大. 避免执行两次
            QuartzJob quartzJob = new QuartzJob();
            quartzJob.setExecuType("0");
            List<QuartzJob> quartzJobList  = quartzJobService.findList(quartzJob);
            if(CollectionUtils.isNotEmpty(quartzJobList)){
                for(QuartzJob job:quartzJobList){
                    if(DateUtils.compare(job.getExecuteDate(),new Date())<=0){
                        job.setExecuteDate(new Date());
                        job.setCron(DateUtils.getCron(new Date()));
                        QuartzManager.addJob(job);
                    }else{
                        QuartzManager.addJob(job);
                    }

                }
            }
        }

    }
}
