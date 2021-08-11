package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.external.convertor.EhrEmployeeConvertor;
import com.demxs.tdm.service.sys.SystemService;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.external.EhrEmployeeResult;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * 同步用户任务
 */
public class SyncUserJob implements JobRunner {

    private static final Logger log = LoggerFactory.getLogger(SyncUserJob.class);

    @Autowired
    private IExternalApi externalApi;
    @Autowired
    private SystemService systemService;

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        log.error("同步用户开始...");
        //业务日志
        BizLogger bizLogger = jobContext.getBizLogger();
        //任务信息
        Job job = jobContext.getJob();
        String param = job.getParam("param");
        Map<String,String> map = (Map) JsonMapper.fromJsonString(param,Map.class);
        try {
            List<EhrEmployeeResult> employees = externalApi.getEhrEmployeeList(map.get("apiAddress"),map.get("userName"),map.get("password"),map.get("synDate"),map.get("synFlag"));
            if(CollectionUtils.isNotEmpty(employees)){
                List<User> users = Lists.newArrayList();
                for(EhrEmployeeResult em:employees){
                    User u = new User();
                    u = EhrEmployeeConvertor.convertBean(em,u);
                    users.add(u);
                }
                systemService.syncUserFromPS(users);
            }
        } catch (Exception e) {
            log.error("同步用户失败: {}",e.getMessage());
            bizLogger.error(param +"同步用户失败: "+e.getMessage());
            bizLogger.error(String.format("同步用户: %s ,失败: %s",param,e.getMessage()));
            return new Result(Action.EXECUTE_FAILED,String.format("同步用户: %s ,同步失败",param));
        }
        return null;
    }
}
