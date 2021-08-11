package com.demxs.tdm.job.handler;

import com.demxs.tdm.common.utils.DateUtils;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.github.ltsopensource.core.domain.JobResult;
import com.github.ltsopensource.jobclient.support.JobCompletedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 任务完成处理
 * User: wuliepeng
 * Date: 2017-08-23
 * Time: 上午10:07
 */
public class JobCompletedHandlerImpl implements JobCompletedHandler {
    private static final Logger log = LoggerFactory.getLogger(JobCompletedHandlerImpl.class);

    @Override
    public void onComplete(List<JobResult> jobResults) {
        // 任务执行反馈结果处理
        if (CollectionUtils.isNotEmpty(jobResults)) {
            for (JobResult jobResult : jobResults) {
                log.info("{} 任务执行完成: {}", DateUtils.getDateTime(),jobResult);
            }
        }
    }
}
