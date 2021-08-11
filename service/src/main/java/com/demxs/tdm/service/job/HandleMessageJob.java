package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.external.convertor.DeleteTodoParamConvertor;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.external.DeleteTodoParam;
import com.demxs.tdm.domain.oa.Message;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HandleMessageJob implements JobRunner {

    private static final Logger log = LoggerFactory.getLogger(HandleMessageJob.class);

    @Autowired
    private IExternalApi externalApi;

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        log.error("处理信息任务开始...");
        //业务日志
        BizLogger bizLogger = jobContext.getBizLogger();
        //任务信息
        Job job = jobContext.getJob();
        String param = job.getParam("message");
        Message message = (Message) JsonMapper.fromJsonString(param,Message.class);
        DeleteTodoParam deleteTodoParam = new DeleteTodoParam();
        deleteTodoParam = DeleteTodoParamConvertor.convertBean(message,deleteTodoParam);
        try {
            externalApi.deleteTodo(deleteTodoParam);
        } catch (ServiceException e) {
            log.error("消息处理失败: {}",e.getMessage());
            bizLogger.error(message +"消息处理失败: "+e.getMessage());
            bizLogger.error(String.format("消息: %s ,处理失败: %s",param,e.getMessage()));
            return new Result(Action.EXECUTE_FAILED,String.format("消息: %s ,处理失败",param));
        }
        return new Result(Action.EXECUTE_SUCCESS,String.format("消息: %s ,处理成功",param));
    }
}
