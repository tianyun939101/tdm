package com.demxs.tdm.job;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 总入口,在taskTracker.setJobRunnerClass(JobRunnerDispatcher.class)
 * JobClient 提交任务时指定 Job 类型 job.setParam("type","aType")
 * User: wuliepeng
 * Date: 2017-08-04
 * Time: 下午3:57
 */
public class JobRunnerDispatcher implements JobRunner,ApplicationContextAware {
    private static final ConcurrentHashMap<String,JobRunner> JOB_RUNNER_MAP = new ConcurrentHashMap<String,JobRunner>();

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        //根据type选择对应的JobRunner运行
        Job job = jobContext.getJob();
        String type = job.getParam("type");
        return JOB_RUNNER_MAP.get(type).run(jobContext);
    }

    /**
     * 从IOC容器中将JobRunner类型的bean放入map中
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,JobRunner> map = applicationContext.getBeansOfType(JobRunner.class);
        for (String type : map.keySet()){
            JOB_RUNNER_MAP.put(type,map.get(type));
        }
    }
}
