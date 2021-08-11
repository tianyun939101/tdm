package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.external.convertor.SendTodoParamConvertor;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.external.SendTodoParam;
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

public class SendMessageJob implements JobRunner {
    private static final Logger log = LoggerFactory.getLogger(DocConvertorJob.class);

    @Autowired
    private IExternalApi externalApi;

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        log.error("发送信息任务开始...");
        //业务日志
        BizLogger bizLogger = jobContext.getBizLogger();
        //任务信息
        Job job = jobContext.getJob();
        String param = job.getParam("message");
        Message message = (Message) JsonMapper.fromJsonString(param,Message.class);
        SendTodoParam sendTodoParam = new SendTodoParam();
        sendTodoParam = SendTodoParamConvertor.convertBean(message,sendTodoParam);
        try {
            externalApi.sendTodo(sendTodoParam);
        } catch (ServiceException e) {
            log.error("消息推送失败: {}",e.getMessage());
            bizLogger.error(message +"消息推送失败: "+e.getMessage());
            bizLogger.error(String.format("消息: %s ,推送失败: %s",param,e.getMessage()));
            return new Result(Action.EXECUTE_FAILED,String.format("消息: %s ,推送失败",param));
        }
        return new Result(Action.EXECUTE_SUCCESS,String.format("消息: %s ,推送成功",param));

    }
}
