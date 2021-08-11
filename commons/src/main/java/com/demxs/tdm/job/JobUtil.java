package com.demxs.tdm.job;

import com.demxs.tdm.common.utils.SpringContextHolder;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobtracker.JobTracker;
import com.github.ltsopensource.tasktracker.TaskTracker;


/**
 * 任务工具类
 * User: wuliepeng
 * Date: 2017-08-04
 * Time: 下午3:29
 */
public class JobUtil {

    /**
     * 获得jobClient,采用spring配置方式
     * @return
     */
    public static JobClient getJobClient(){
        JobClient jobClient = SpringContextHolder.getBean("jobClient");
        //JobClientFactoryBean jobClientFactoryBean = SpringContextHolder.getBean("jobClient");
        jobClient.start();
        return jobClient;
    }

    /**
     * 获得JobTracker,采用spring配置方式
     * @return
     */
    public static JobTracker getJobTracker(){
        return SpringContextHolder.getBean("jobTracker");
    }

    /**
     * 获得TaskTracker,采用spring配置方式
     * @return
     */
    public static TaskTracker getTaskTracker(){
        return SpringContextHolder.getBean("taskTracker");
    }
}
