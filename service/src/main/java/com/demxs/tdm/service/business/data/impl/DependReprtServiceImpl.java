package com.demxs.tdm.service.business.data.impl;

import com.demxs.tdm.dao.data.DependReprtDao;
import com.demxs.tdm.dao.lab.LabTestItemDao;
import com.demxs.tdm.dao.lab.LabTestItemUnitDao;
import com.demxs.tdm.service.configure.YuanshijlmbyxbqService;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.EntrustTestGroup;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.vo.TaskExecuteVO;
import com.demxs.tdm.domain.configure.Yuanshijlmbyxbq;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.domain.lab.LabTestItemUnit;
import com.demxs.tdm.service.business.EntrustTestGroupService;
import com.demxs.tdm.service.business.core.impl.EntrustService;
import com.demxs.tdm.service.business.core.impl.TaskService;
import com.demxs.tdm.service.business.data.IDependReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DependReprtServiceImpl implements IDependReportService {
    @Autowired
    private EntrustService entrustService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private LabTestItemDao labTestItemDao;
    @Autowired
    private LabTestItemUnitDao labTestItemUnitDao;

    @Autowired
    private EntrustTestGroupService entrustTestGroupService;

    @Autowired
    private YuanshijlmbyxbqService yuanshijlmbyxbqService;
    @Autowired
    private DependReprtDao dependReprtDao;

    @Override
    public String getChushigonglv(String taskId,String sampleCode, String entrustId,String testId) {
        TaskExecuteVO taskExecuteVO = getTaskExecuteVO(taskId,entrustId,testId);
         String a=null;
        Map map = getPretreatmentData(sampleCode,taskExecuteVO);
        if(map != null && map.size()>0){
            a=  (String)map.get("CHUSHIGONGLV");
        }

        return a;
    }

    @Override
    public List<String> getAllSampleCodes(String taskId, String entrustId, String testId) {
        List<String> allSamples = SetUniqueList.decorate(new ArrayList<>());
        TaskExecuteVO taskExecuteVO = getTaskExecuteVO(taskId,entrustId,testId);
        if(taskExecuteVO != null){

            //获取外观的样品
            allSamples.addAll(getAppearenceSamples(taskExecuteVO));
            allSamples.addAll(getMaxPowerSamples(taskExecuteVO));
            allSamples.addAll(getInslutionSamples(taskExecuteVO));
            allSamples.addAll(getSLDDataSamples(taskExecuteVO));
            allSamples.addAll(getPretreatmentSamples(taskExecuteVO));

        }
        return allSamples;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String,Map<String,Object>> getDependDate(String taskId,String sampleCode, String entrustId, String testId) {
        Map<String,Map<String,Object>> result = new HashMap<>();
        TaskExecuteVO taskExecuteVO = getTaskExecuteVO(taskId,entrustId,testId);
        if(taskExecuteVO !=null){
            //外观
            result.put("waiguan", getAppearenceDate(sampleCode,taskExecuteVO));
            //最大功率
            result.put("gonglv",getMaxPowerData(sampleCode,taskExecuteVO));
            //绝缘
            result.put("jueyuan",getInslutionData(sampleCode,taskExecuteVO));
            //湿漏电
            result.put("sld",getSLDData(sampleCode,taskExecuteVO));
            return result;
        }
        return null;
    }


    /**
     * 获取外观
     * 外观检查记录表
     */
    private Map<String,Object> getAppearenceDate(String sampleCode,TaskExecuteVO taskExecuteVO){

        String biaom = "";
        String sampleCodeFiled = "";
        String waiguanFiled = "";
        String taskId = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("外观")){
                taskId = t.getTaskId();
                    //获取这个原始记录单的数据
                    //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){

                           biaom = y.getBiaom();
                           sampleCodeFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("无可视缺陷")){
                            waiguanFiled = y.getRkenname();
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+waiguanFiled+" AS waiguan from "+biaom +" where "+sampleCodeFiled+"='"+sampleCode+"' and renwuid = '"+taskId+"'";
            List<Map<String,Object>> results = dependReprtDao.executeSql(sql);
            if(CollectionUtils.isNotEmpty(results)){
                return results.get(0);
            }else{
                return null;
            }
        }else {
            return null;
        }

    }


    private List<String> getAppearenceSamples(TaskExecuteVO taskExecuteVO){
        List<String> samples = new ArrayList<>();
        String biaom = "";
        String sampleCodeFiled = "";
        String taskId = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("外观")){
                taskId = t.getTaskId();
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){

                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }

                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+sampleCodeFiled+" from "+biaom;
            samples = dependReprtDao.executeSampleCodeSql(sql);
        }
        return samples;
    }

    /**
     * 获取最大功率
     *标准测试条件下的性能记录表
     */
    private Map<String,Object> getMaxPowerData(String sampleCode,TaskExecuteVO taskExecuteVO){

        String taskId = "";
        String biaom = "";
        String sampleCodeFiled = "";
        String vocFiled = "";
        String vpmFiled = "";
        String iscFiled = "";
        String ipmFiled = "";
        String ffFiled = "";
        String pmaxFiled = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("标准")){
                taskId = t.getTaskId();
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){
                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("Voc")){
                            vocFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("Vpm")){
                            vpmFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("Isc")){
                            iscFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("Ipm")){
                            ipmFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("FF")){
                            ffFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("Pmax")){
                            pmaxFiled = y.getRkenname();
                        }
                    }

                }
            }
        }

        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+vocFiled+" AS voc,"+vpmFiled+" as vpm,"+iscFiled+" as isc,"+ipmFiled+" as ipm,"+ffFiled+" as ff, "+pmaxFiled+" as pmax from "+biaom +" where "+sampleCodeFiled+"='"+sampleCode+"'  and renwuid = '"+taskId+"'";
            List<Map<String,Object>> results = dependReprtDao.executeSql(sql);
            if(CollectionUtils.isNotEmpty(results)){
                return results.get(0);
            }
        }
        return null;

    }



    private List<String> getMaxPowerSamples(TaskExecuteVO taskExecuteVO){
        List<String> samples = new ArrayList<>();
        String biaom = "";
        String sampleCodeFiled = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("标准")){
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){

                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }

                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+sampleCodeFiled+" from "+biaom;
            samples = dependReprtDao.executeSampleCodeSql(sql);
        }
        return samples;
    }

    /**
     * 获取绝缘
     * 绝缘试验记录表
     */
    private Map<String,Object> getInslutionData(String sampleCode,TaskExecuteVO taskExecuteVO){

        String taskId = "";
        Map result = new HashMap();
        String biaom = "";
        String sampleCodeFiled = "";
        String resultField = "";

        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("绝缘")){
                taskId = t.getTaskId();
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){
                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("测量值")){
                            resultField = y.getRkenname();
                        }
                    }
                }
            }
        }

        return executeResult(taskId,sampleCode, biaom, sampleCodeFiled, resultField);
    }





    private List<String> getInslutionSamples(TaskExecuteVO taskExecuteVO){
        List<String> samples = new ArrayList<>();
        String biaom = "";
        String sampleCodeFiled = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("绝缘")){
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){

                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }

                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+sampleCodeFiled+" from "+biaom;
            samples = dependReprtDao.executeSampleCodeSql(sql);
        }
        return samples;
    }

    private Map<String,Object> executeResult(String taskId,String sampleCode, String biaom, String sampleCodeFiled, String resultField) {
        if(StringUtils.isNotBlank(biaom) && StringUtils.isNotBlank(resultField)){
            String sql = "select "+resultField+" AS jieguo from "+biaom +" where "+sampleCodeFiled+"='"+sampleCode+"'  and renwuid = '"+taskId+"'";
            List<Map<String,Object>> results = dependReprtDao.executeSql(sql);
            if(CollectionUtils.isNotEmpty(results)){
                return results.get(0);
            }else{
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * 获取湿漏电
     * 湿漏电流试验记录表
     */
    private Map getSLDData(String sampleCode,TaskExecuteVO taskExecuteVO){

        String taskId = "";
        Map result = new HashMap();
        String biaom = "";
        String sampleCodeFiled = "";
        String resultField = "";

        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("湿漏电流")){
                taskId = t.getTaskId();
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){
                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("测量值")){
                            resultField = y.getRkenname();
                        }
                    }
                }
            }
        }

        return executeResult(taskId,sampleCode, biaom, sampleCodeFiled, resultField);
    }



    private List<String> getSLDDataSamples(TaskExecuteVO taskExecuteVO){
        List<String> samples = new ArrayList<>();
        String biaom = "";
        String sampleCodeFiled = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("湿漏电流")){
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){

                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }

                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+sampleCodeFiled+" from "+biaom;
            samples = dependReprtDao.executeSampleCodeSql(sql);
        }
        return samples;
    }


    /**
     * 预处理试验记录表
     * 外观检查记录表
     */
    private Map<String,Object> getPretreatmentData(String sampleCode,TaskExecuteVO taskExecuteVO){

        String biaom = "";
        String sampleCodeFiled = "";
        String chushiGonglv = "";
        String taskId = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().equals("预处理试验记录表")){
                taskId = t.getTaskId();
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){
                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }
                        if(y.getRkcnname().equals("初始-IV")){
                            chushiGonglv = y.getRkenname();
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+chushiGonglv+" AS chushiGonglv from "+biaom +" where "+sampleCodeFiled+"='"+sampleCode+"' and renwuid = '"+taskId+"'";
            List<Map<String,Object>> results = dependReprtDao.executeSql(sql);
            if(CollectionUtils.isNotEmpty(results)){
                return results.get(0);
            }else{
                return null;
            }
        }else {
            return null;
        }

    }


    private List<String> getPretreatmentSamples(TaskExecuteVO taskExecuteVO){
        List<String> samples = new ArrayList<>();
        String biaom = "";
        String sampleCodeFiled = "";
        List<TaskExecuteVO> subs = taskExecuteVO.getSubTasks();
        for(TaskExecuteVO t:subs){
            if(t.getTemplateName().contains("预处理试验记录表")){
                //获取这个原始记录单的数据
                //获取原始记录的配置
                Map bqResult = yuanshijlmbyxbqService.getAllYuanshijlmbyxbqJson(t.getTemplateId());
                List<Yuanshijlmbyxbq> bqList = (List<Yuanshijlmbyxbq>)bqResult.get("list");
                for(Yuanshijlmbyxbq y:bqList){
                    if (StringUtils.isNotBlank(y.getRkcnname())) {
                        if(y.getRkcnname().equals("条形码")){

                            biaom = y.getBiaom();
                            sampleCodeFiled = y.getRkenname();
                        }

                    }
                }
            }
        }
        if(StringUtils.isNotBlank(biaom)){
            String sql = "select "+sampleCodeFiled+" from "+biaom;
            samples = dependReprtDao.executeSampleCodeSql(sql);
        }
        return samples;
    }

    public static void main(String[] args) {
        Map result = new HashMap();
        Map waiguan = new HashMap();
        Map sslMap = new HashMap();
        Map jueyuanMap = new HashMap();
        Map maxPower = new HashMap();
        waiguan.put("waiguan","良好");
        sslMap.put("jieguo","sld");
        jueyuanMap.put("jieguo","jueyuan");
        maxPower.put("voc","voc");
        maxPower.put("vpm","vpm");
        maxPower.put("isc","isc");
        maxPower.put("ipm","ipm");
        maxPower.put("ff","ff");
        maxPower.put("pmax","pmax");
        result.put("waiguan",waiguan);
        result.put("gonglv",maxPower);
        result.put("jueyuan",jueyuanMap);
        result.put("sld",sslMap);
        System.out.println(JsonMapper.toJsonString(result));

    }


    private TaskExecuteVO getTaskExecuteVO(String taskId,String entrustId,String testId){
        TaskExecuteVO taskExecuteVO = null;
        EntrustInfo entrustInfo = entrustService.detail(entrustId);
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for(EntrustTestGroup testGroup : testGroupList){
            List<TaskExecuteVO> taskExecuteList = testGroup.getTaskExecuteList();
            for (TaskExecuteVO itemTask : taskExecuteList){
                if(itemTask.getAbilityId().equals(testId) && taskId.equals(itemTask.getTaskId())){
                    if(EntrustConstants.TaskStatus.DONE.equals(itemTask.getStatus())){
                        for (TaskExecuteVO unitTask : taskExecuteList){
                            if(unitTask.getPid().equals(itemTask.getId())){
                                LabTestItem testItem = labTestItemDao.getLabItem(entrustInfo.getLabId(),itemTask.getAbilityId());
                                List<LabTestItemUnit> testItemUnits = labTestItemUnitDao.findByItem(testItem.getId());

                                for(LabTestItemUnit unit : testItemUnits){
                                    if(unit.getUnit().getId().equals(unitTask.getAbilityId())){
                                        if(unit.getRecordTemplate()!=null) {
                                            unitTask.setTemplateId(unit.getRecordTemplate().getId());
                                            unitTask.setTemplateName(unit.getRecordTemplate().getName());
                                            itemTask.getSubTasks().add(unitTask);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        taskExecuteVO = itemTask;
                    }
                    break;
                }else{
                    continue;
                }
            }


        }
        return taskExecuteVO;
    }
}
