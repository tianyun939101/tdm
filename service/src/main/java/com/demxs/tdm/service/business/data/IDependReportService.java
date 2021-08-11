package com.demxs.tdm.service.business.data;


import java.util.List;
import java.util.Map;

/**
 * 可靠性报表数据
 */
public interface IDependReportService {

    /**
     *
     * @param sampleCode 样品编号
     * @param entrustId 申请单id
     * @param testId  试验项目id
     */
    Map<String,Map<String,Object>> getDependDate(String taskId,String sampleCode, String entrustId, String testId);

    String getChushigonglv(String taskId,String sampleCode, String entrustId,String testId);


    List<String> getAllSampleCodes(String taskId, String entrustId, String testId);

}
