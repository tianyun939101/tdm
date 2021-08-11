package com.demxs.tdm.service.job;

import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.domain.external.EhrDeptResult;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * 同步组织任务
 */
public class SyncOfficeJob implements JobRunner {

    private static final Logger log = LoggerFactory.getLogger(SyncOfficeJob.class);

    @Autowired
    private IExternalApi externalApi;
    @Autowired
    private OfficeService officeService;
    @Override
    public Result run(JobContext jobContext) throws Throwable {
        log.error("同步组织开始...");
        //业务日志
        BizLogger bizLogger = jobContext.getBizLogger();
        //任务信息
        Job job = jobContext.getJob();
        String param = job.getParam("param");
        Map<String,String> map = (Map)JsonMapper.fromJsonString(param,Map.class);
        try {
            List<EhrDeptResult> depts = externalApi.getEhrDeptList(map.get("apiAddress"),map.get("userName"),map.get("password"),map.get("synDate"),map.get("synFlag"));
            if(CollectionUtils.isNotEmpty(depts)){
                officeService.saveFromPS(depts);
            }
        } catch (Exception e) {
            log.error("同步组织失败: {}",e.getMessage());
            bizLogger.error(param +"同步组织失败: "+e.getMessage());
            bizLogger.error(String.format("同步组织: %s ,失败: %s",param,e.getMessage()));
            return new Result(Action.EXECUTE_FAILED,String.format("同步组织: %s ,同步失败",param));
        }
        return null;
    }
}
