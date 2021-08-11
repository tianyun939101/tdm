package com.demxs.tdm.common.sys.service;


import java.util.Map;

/**
 * 提供报告 原始记录 表单数据
 * @Author sunjunhui
 * @Date 2017-12-20
 */
public interface IDataService {

    /**
     * 根据申请单ID获取申请单表单相关数据
     * @param entrustId 申请单ID
     * @return
     */
    Map<String,Object> getDataForWTD(String entrustId);


    /**
     * 根据申请单id获取报告相关表单数据
     * @param entrustId
     * @return
     */
    public Map<String,Object> getDataForBG(String entrustId);

    /**
     * 根据任务id获取原始记录相关表单数据
     * @param taskId
     * @return
     */
    public Map<String,Object> getDataForYS(String taskId);
}
