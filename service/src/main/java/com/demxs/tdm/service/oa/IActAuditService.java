package com.demxs.tdm.service.oa;

import java.util.Map;

/**
 * Created by guojinlong on 2017/8/5.
 */
public interface IActAuditService {

    /**
     * 启动流程
     * @param businessKey 业务主键
     * @param actType 流程类型
     */
    public void start(String businessKey, String actType);

    void start(String businessKey,String actType,String title);

    void start(String businessKey, String actType, String title, String comment, Map<String, Object> variables);

    String start(String businessKey, String actType, String title, Map<String, Object> variables);
}
