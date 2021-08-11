package com.demxs.tdm.service.job;


import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文档转换任务
 * User: wuliepeng
 * Date: 2017-08-02
 * Time: 上午9:44
 */
public class DocConvertorJob implements JobRunner {
    private static final Logger log = LoggerFactory.getLogger(DocConvertorJob.class);
    @Autowired
    private AssetInfoService assetInfoService;

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        //业务日志
        BizLogger bizLogger = jobContext.getBizLogger();
        //任务信息
        Job job = jobContext.getJob();
        String attachmentId = job.getParam("attachmentId");
        Boolean indexable = Boolean.getBoolean(job.getParam("indexable"));
        try {
            assetInfoService.convert(attachmentId,indexable);
        }catch (Exception e){
            log.error("文档转换失败: {}",e.getMessage());
            bizLogger.error(attachmentId +"文档转换失败: "+e.getMessage());
            bizLogger.error(String.format("附件ID: %s ,转换失败: %s",attachmentId,e.getMessage()));
            return new Result(Action.EXECUTE_FAILED,String.format("附件ID: %s ,转换失败",attachmentId));
        }
        return new Result(Action.EXECUTE_SUCCESS,String.format("附件ID: %s ,转换成功",attachmentId));
    }
}
