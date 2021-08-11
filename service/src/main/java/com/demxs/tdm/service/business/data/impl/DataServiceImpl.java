package com.demxs.tdm.service.business.data.impl;

import com.demxs.tdm.dao.ability.TestItemUnitDao;
import com.demxs.tdm.dao.lab.LabTestItemDao;
import com.demxs.tdm.dao.lab.LabTestItemUnitDao;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.service.configure.BaogaombYxbqService;
import com.demxs.tdm.service.configure.YuanshijlmbRkService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.common.sys.service.IDataService;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.*;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.vo.TaskExecuteVO;
import com.demxs.tdm.domain.lab.LabTestItem;
import com.demxs.tdm.domain.lab.LabTestItemUnit;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.comac.common.constant.attach.KnowledgeType;
import com.demxs.tdm.service.ability.TestSequenceService;
import com.demxs.tdm.service.business.EntrustSampleGroupItemService;
import com.demxs.tdm.service.business.core.IEntrustService;
import com.demxs.tdm.service.business.core.ITaskService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataServiceImpl implements IDataService {


    @Autowired
    private BaogaombYxbqService baogaombYxbqService;
    @Autowired
    private IEntrustService entrustService;
    @Autowired
    private TestSequenceService testSequenceService;
    @Autowired
    private TestItemUnitDao testItemUnitDao;
    @Autowired
    private EntrustSampleGroupItemService sampleGroupItemService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private LabTestItemDao labTestItemDao;
    @Autowired
    private LabTestItemUnitDao labTestItemUnitDao;

    @Autowired
    private YuanshijlmbRkService yuanshijlmbRkService;
    @Autowired
    private AssetInfoService assetInfoService;

    @Override
    public Map<String, Object> getDataForWTD(String entrustId) {
        Map<String,Object> result = new HashMap<>();

        EntrustInfo entrustInfo = entrustService.detail(entrustId);
        if(entrustInfo == null){
            return result;
        }
        /**
         * 生成数据
         */
        List<Map<String,String>> lieData = new ArrayList<>();
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for (EntrustTestGroup testGroup : testGroupList){
            Map<String,String> lieMap = new HashMap();
            StringBuilder sampleNoSb = new StringBuilder();
            StringBuilder testItemSb = new StringBuilder();
            /**
             * 取样品sn,分成品与半成品/材料
             */
            List<EntrustSampleGroup> sampleGroups = testGroup.getSampleGroups();
            for (EntrustSampleGroup sampleGroup : sampleGroups){
                if(EntrustConstants.Sample_Type.PRODUCT.equals(entrustInfo.getSampleType())) {
                    sampleNoSb.append(sampleGroup.getSn()).append("、");
                }else{
                    EntrustSampleGroupItem sampleGroupItem = new EntrustSampleGroupItem();
                    sampleGroupItem.setSGroupId(sampleGroup.getId());
                    List<EntrustSampleGroupItem> entrustSampleGroupItems = sampleGroupItemService.findList(sampleGroupItem);
                    for (EntrustSampleGroupItem item : entrustSampleGroupItems){
                        sampleNoSb.append(item.getSn()).append("、");
                    }
                }
            }
            /**
             * 取试验项目,构造数据
             */
            List<EntrustTestGroupAbility> abilityList = testGroup.getAbilityList();
            for (EntrustTestGroupAbility ability : abilityList){
                if(EntrustConstants.Ability_Type.CUSTOM_SEQUENCE.equals(testGroup.getAbilityType())){
                    testItemSb.append(ability.getItemName()).append("、");
                }else if(EntrustConstants.Ability_Type.SEQUENCE.equals(testGroup.getAbilityType())){
                    testItemSb.append(ability.getSeqName()).append("、");
                }
            }
            lieMap.put("sort","试验组 "+testGroup.getSort());
            lieMap.put("sn",sampleNoSb.substring(0,sampleNoSb.length()-1));
            lieMap.put("testItem",testItemSb.substring(0,sampleNoSb.length()-1));
            lieData.add(lieMap);
        }
        return result;
    }

    @Override
    public Map<String, Object> getDataForBG(String entrustId) {
        Map<String,Object> returnData = new HashMap<>();
        EntrustInfo entrustInfo = entrustService.detail(entrustId);
        if(entrustInfo == null){
            return returnData;
        }

        returnData.put("entrustInfo",getWeituoResult(entrustInfo));
        /*returnData.put("shebei",getShebeiResult());
        returnData.put("yangpin",getSampleResult());*/
        /**
         * 测试结果总说明列表
         */
        returnData.put("csjgzsmlb",getTestResult("csjgzsmlb",entrustInfo));
        /**
         * 组件结构描述
         */
        returnData.put("zjjgms",getZujianResult("zjjgms",entrustInfo));
        /**
         * 测试组件清单
         */
        returnData.put("cszjqd",getTestZujianResult("cszjqd",entrustInfo));
        /**
         * 执行试验清单
         */
        returnData.put("zxjcqd",getExecuteResult("zxjcqd",entrustInfo));
        /**
         * 测试结果摘要
         */
        returnData.put("csjgzy",getTestSummaryResult("csjgzy",entrustInfo));
        /**
         * 组件图
         */
        returnData.put("zjt",getZujianPic(entrustInfo));
        /**
         * 测试结果详情
         */
        returnData.put("jcxlist",getJcxList(entrustInfo));
        return returnData;
    }

    @Override
    public Map<String, Object> getDataForYS(String taskId) {
        Map<String,Object> result = new HashMap<>();
        TestTask testTask = taskService.get(taskId);
        testTask.setUser(UserUtils.getUser());
        testTask.setCreateDate(new Date());
        EntrustInfo entrustInfo = entrustService.getByCode(testTask.getBusinessKey());
        result.put("entrustInfo",entrustInfo);
        result.put("taskInfo",testTask);
        return result;
    }

    //获取申请
    private Map<String,Object> getWeituoResult(EntrustInfo entrustInfo){
        Map<String,Object> map = new HashMap<>();
        map.put("code",entrustInfo.getCode());
        map.put("applyUser",entrustInfo.getUser().getName());
        map.put("applyOrg",entrustInfo.getOrg().getName());
        map.put("summary",entrustInfo.getSummary());
        map.put("acceptDate",DateUtils.formatDate(entrustInfo.getConfirmDate(),"yyyy-MM-dd"));

        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        if(!testGroupList.isEmpty()){
            List<TaskExecuteVO> taskExecuteList = testGroupList.get(0).getTaskExecuteList();
            if(!taskExecuteList.isEmpty()){
                TaskExecuteVO taskExecuteVO = taskExecuteList.get(0);
                map.put("testDate",DateUtils.formatDate(taskExecuteVO.getStartDate(),"yyyy-MM-dd"));
            }
        }else{
            map.put("testDate",DateUtils.formatDate(new Date(),"yyyy-MM-dd"));
        }

        if(entrustInfo.getReport() == null){
            map.put("reportDate", DateUtils.formatDate(new Date(),"yyyy-MM-dd"));
            map.put("reportUser", UserUtils.getUser().getName());
            map.put("approvalUser","");
            map.put("approvalDate","");
            map.put("auditUser","");
            map.put("auditDate","");
        }else{
            EntrustReport report = entrustInfo.getReport();
            map.put("reportDate", DateUtils.formatDate(report.getDrawDate(),"yyyy-MM-dd"));
            map.put("reportUser", report.getDrawUser()!=null?report.getDrawUser().getName():"");
            map.put("approvalUser",report.getApprovalUser()!=null?report.getApprovalUser().getName():"");
            map.put("approvalDate",report.getApprovalDate()!=null?DateUtils.formatDate(report.getApprovalDate(),"yyyy-MM-dd"):"");
            map.put("auditUser",entrustInfo.getReport().getAuditUser()!=null?entrustInfo.getReport().getAuditUser().getName():"");
            map.put("auditDate",report.getAuditDate()!=null?DateUtils.formatDate(report.getAuditDate(),"yyyy-MM-dd"):"");
        }
        return map;
    }

    //测试结果总说明列表
    private Map<String,Object> getTestResult(String key,EntrustInfo entrustInfo){
        return generatorSampleData(key,entrustInfo);
    }

    /**
     * 获取组件结构描述数据
     * @param key
     * @param entrustInfo
     * @return
     */
    private List<Map<String,Object>> getZujianResult( String key,EntrustInfo entrustInfo){
        List<Map<String,Object>> result = new ArrayList<>();

        List<Map<List<EntrustSampleBom>,List<EntrustSampleGroup>>> datas = new ArrayList<>();
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for (EntrustTestGroup testGroup : testGroupList){
            if(testGroup.getDisableFlag()!=null && testGroup.getDisableFlag().equals(1)){
              continue;
            }
            List<EntrustSampleGroup> sampleGroups = testGroup.getSampleGroups();
            for (EntrustSampleGroup sampleGroup : sampleGroups){
                if (datas.isEmpty()){
                    Map<List<EntrustSampleBom>,List<EntrustSampleGroup>> item = new HashMap<>();
                    List<EntrustSampleBom> sampleBoms = sampleGroup.getBom();
                    List<EntrustSampleGroup> itemSampleGroups = new ArrayList<>();
                    itemSampleGroups.add(sampleGroup);
                    item.put(sampleBoms,itemSampleGroups);
                    datas.add(item);
                }else {
                    List<Map<List<EntrustSampleBom>,List<EntrustSampleGroup>>> temp = new ArrayList<>();
                    for (Map<List<EntrustSampleBom>, List<EntrustSampleGroup>> item : datas) {
                        boolean flag = true;
                        Iterator<Map.Entry<List<EntrustSampleBom>, List<EntrustSampleGroup>>> iterator = item.entrySet().iterator();
                        while (iterator.hasNext()){
                            Map.Entry<List<EntrustSampleBom>, List<EntrustSampleGroup>> dataItem = iterator.next();
                            List<EntrustSampleBom> sampleBoms = dataItem.getKey();
                            List<EntrustSampleGroup> itemSampleGroups = dataItem.getValue();
                            if (eqbom(sampleBoms,sampleGroup.getBom())){
                                itemSampleGroups.add(sampleGroup);
                                flag=false;
                                /*for (Map<List<EntrustSampleBom>, List<EntrustSampleGroup>> enter : temp){
                                    if (enter.get(sampleGroup.getBom()).equals(sampleGroup)){
                                        temp.remove(enter);
                                        break;
                                    }
                                }*/
                                temp = new ArrayList<>();
                            }
                        }
                        if(flag) {
                            Map<List<EntrustSampleBom>, List<EntrustSampleGroup>> item2 = new HashMap<>();
                            List<EntrustSampleBom> sampleBoms = sampleGroup.getBom();
                            List<EntrustSampleGroup> itemSampleGroups = new ArrayList<>();
                            itemSampleGroups.add(sampleGroup);
                            item2.put(sampleBoms, itemSampleGroups);
                            temp.add(item2);
                        }
                    }
                    datas.addAll(temp);
                }
            }
        }

        if(!EntrustConstants.Sample_Type.PRODUCT.equals(entrustInfo.getSampleType())) {
            for (Map<List<EntrustSampleBom>,List<EntrustSampleGroup>> item : datas){
                Iterator<Map.Entry<List<EntrustSampleBom>, List<EntrustSampleGroup>>> iterator = item.entrySet().iterator();
                while (iterator.hasNext()){
                    List<EntrustSampleGroup> sampleGroups = iterator.next().getValue();
                    for (EntrustSampleGroup sampleGroup : sampleGroups){
                        StringBuilder snSb = new StringBuilder();
                        EntrustSampleGroupItem query = new EntrustSampleGroupItem();
                        query.setSGroupId(sampleGroup.getId());
                        List<EntrustSampleGroupItem> entrustSampleGroupItems = sampleGroupItemService.findList(query);
                        for (EntrustSampleGroupItem sampleGroupItem : entrustSampleGroupItems){
                            snSb.append(sampleGroupItem.getSn()).append((char)11);
                        }
                        sampleGroup.setSn(snSb.substring(0,snSb.length()-1));
                    }
                }
            }
        }

        /**
         * 结构化数据
         */
        List<String> snList = new ArrayList<>();
        for (Map<List<EntrustSampleBom>,List<EntrustSampleGroup>> dataItem : datas){
            Iterator<Map.Entry<List<EntrustSampleBom>, List<EntrustSampleGroup>>> iterator = dataItem.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<List<EntrustSampleBom>, List<EntrustSampleGroup>> next = iterator.next();
                List<EntrustSampleBom> sampleBoms = next.getKey();
                List<EntrustSampleGroup> sampleGroups = next.getValue();
                EntrustSampleGroup sampleGroup = sampleGroups.get(0);
                StringBuilder snSb = new StringBuilder();
                for (EntrustSampleGroup sampleGroupItem : sampleGroups){
                    snSb.append(sampleGroupItem.getSn()).append((char)11);
                }
                if(snList.contains(snSb.toString())){
                    continue;
                }
                snList.add(snSb.toString());
                Map<String,Object> item = new HashMap<>();
                Map<String,Object>  entrustInfoMap = new HashMap<>();
                entrustInfoMap.put(key+"_"+"token",sampleGroup.getToken());
                entrustInfoMap.put(key+"_"+"sampleDesc",sampleGroup.getDesc());
                //entrustInfoMap.put(key+"_"+"sampleStatus",sampleGroup.getStatus() == 0?"缺陷":"完好");
                Map<String,Object> testGroupMap = new HashMap<>();
                testGroupMap.put(key+"_"+"sampleSn",snSb.substring(0,snSb.length()-1));
                List<Map<String,Object>> liedata = new ArrayList<>();
                for (EntrustSampleBom sampleBom : sampleBoms){
                    Map<String,Object> lieMap = new HashMap<>();
                    lieMap.put(key+"_"+"name",sampleBom.getName());
                    lieMap.put(key+"_"+"manufactor",sampleBom.getManufactor());
                    lieMap.put(key+"_"+"code",sampleBom.getCode());
                    lieMap.put(key+"_"+"desc",sampleBom.getDesc());
                    lieMap.put(key+"_"+"batch",sampleBom.getSn());
                    liedata.add(lieMap);
                }
                item.put("entrustInfo",entrustInfoMap);
                item.put("testGroup",testGroupMap);
                item.put("liedata",liedata);
                result.add(item);
            }
        }
        return result;
    }

    private boolean eqbom(List<EntrustSampleBom> boms1,List<EntrustSampleBom> boms2){
        for (EntrustSampleBom bom1 : boms1){
            for (EntrustSampleBom bom2 : boms2){
                if(bom1.getName().equals(bom2.getName())){
                    if(bom1.equals(bom2)){
                        break;
                    }else{
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //测试组件清单
    private Map<String,Object> getTestZujianResult(String key,EntrustInfo entrustInfo){
        return generatorSampleData(key,entrustInfo);
    }

    private Map<String,Object> generatorSampleData(String key,EntrustInfo entrustInfo){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> liedata = new ArrayList<>();

        Map<String,String> modelTemp = new HashedMap();
        Map<String,List<String>> datas = new HashedMap();
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for (EntrustTestGroup testGroup : testGroupList){
            /**
             * 同一个试验组中的样品进行的试验项都相同,所以一次性先全取出来
             */
            List<TaskExecuteVO> taskExecuteList = testGroup.getTaskExecuteList();
            for (TaskExecuteVO taskExecuteVO : taskExecuteList){
                if (EntrustConstants.Ability_Type.ITEM.equals(taskExecuteVO.getType())){
                    String testItem = taskExecuteVO.getName();
                    String sampleIds = taskExecuteVO.getSampleId();
                    String[] sampleIdArr = sampleIds.split(",");
                    for (String sampleId : sampleIdArr){
                        EntrustSampleGroupItem sampleGroupItem = sampleGroupItemService.get(sampleId);
                        String sn = sampleGroupItem.getSn();
                        if(datas.containsKey(sn)){
                            datas.get(sn).add(testItem);
                        }else{
                            List<String> itemNames = new ArrayList<>();
                            itemNames.add(testItem);
                            datas.put(sn,itemNames);
                        }
                        modelTemp.put(sn,sampleGroupItem.getModel());
                    }
                }
            }
        }
        for (String sn : datas.keySet()){
            List<String> testItems = datas.get(sn);
            StringBuilder testItemSb = new StringBuilder();
            for (String testItem : testItems){
                testItemSb.append(testItem).append((char)11);
            }
            Map<String, Object> lieMap = new HashMap<>();
            lieMap.put(key + "_" + "sn", sn);
            lieMap.put(key + "_" + "model",modelTemp.get(sn));
            lieMap.put(key + "_" + "testItem", testItemSb.substring(0,testItemSb.length()-1));
            liedata.add(lieMap);
        }
        result.put("liedata",liedata);
        return result;
    }

    //执行试验的清单 特殊处理
    private Map<String,Object> getExecuteResult(String key,EntrustInfo entrustInfo){
        Map<String,Object> result = new HashMap<>();

        List<String> testItems = new ArrayList<>();
        Map<String,List<String>> datas = new HashedMap();
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for (EntrustTestGroup testGroup : testGroupList){
            /**
             * 同一个试验组中的样品进行的试验项都相同,所以一次性先全取出来
             */
            List<TaskExecuteVO> taskExecuteList = testGroup.getTaskExecuteList();
            for (TaskExecuteVO taskExecuteVO : taskExecuteList){
                if (EntrustConstants.Ability_Type.ITEM.equals(taskExecuteVO.getType()) && EntrustConstants.TaskStatus.DONE.equals(taskExecuteVO
                        .getStatus())){
                    String sampleIds = taskExecuteVO.getSampleId();
                    String[] sampleIdArr = sampleIds.split(",");
                    for (String sampleId : sampleIdArr){
                        EntrustSampleGroupItem sampleGroupItem = sampleGroupItemService.get(sampleId);
                        String sn = sampleGroupItem.getSn();
                        if(datas.containsKey(sn)){
                            datas.get(sn).add(taskExecuteVO.getName());
                        }else{
                            List<String> itemNames = new ArrayList<>();
                            itemNames.add(taskExecuteVO.getName());
                            datas.put(sn,itemNames);
                        }
                    }
                    if(!testItems.contains(taskExecuteVO.getName())) {
                        testItems.add(taskExecuteVO.getName());
                    }
                }
            }
        }

        List<String> sampleDataList = new ArrayList<>();
        for (String sn : datas.keySet()){
            sampleDataList.add(sn);
        }
        List<String[]> liedata = new ArrayList<>();
        sampleDataList.add(0,"Test Items/测试目录");
        String[] colData = new String[sampleDataList.size()];
        sampleDataList.toArray(colData);
        liedata.add(colData);

        for (String testItem : testItems){
            String[] rowData = new String[colData.length];
            rowData[0] = testItem;
            for (int i=1;i<colData.length;i++){
                String sn = colData[i];
                if(datas.get(sn).contains(testItem)){
                    rowData[i]="√";
                }else{
                    rowData[i]="";
                }
            }
            liedata.add(rowData);
        }

        result.put("liedata",liedata);
        return result;
    }

    //测试结果摘要
    private Map<String,Object> getTestSummaryResult(String key,EntrustInfo entrustInfo){
        Map<String,Object> result = new HashMap<>();

        Map<String,List<String>> datas = new HashedMap();
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for (EntrustTestGroup testGroup : testGroupList){
            /**
             * 同一个试验组中的样品进行的试验项都相同,所以一次性先全取出来
             */
            List<TaskExecuteVO> taskExecuteList = testGroup.getTaskExecuteList();
            for (TaskExecuteVO taskExecuteVO : taskExecuteList){
                if (EntrustConstants.Ability_Type.ITEM.equals(taskExecuteVO.getType())){
                    String testItem = taskExecuteVO.getName();
                    String sampleIds = taskExecuteVO.getSampleId();
                    String[] sampleIdArr = sampleIds.split(",");
                    for (String sampleId : sampleIdArr){
                        EntrustSampleGroupItem sampleGroupItem = sampleGroupItemService.get(sampleId);
                        String sn = sampleGroupItem.getSn();
                        if(datas.containsKey(sn)){
                            datas.get(sn).add(testItem);
                        }else{
                            List<String> itemNames = new ArrayList<>();
                            itemNames.add(testItem);
                            datas.put(sn,itemNames);
                        }
                    }
                }
            }
        }

        /**
         * 渲染数据
         */
        List<Map<String,Object>> liedata = new ArrayList<>();

        for (String sn : datas.keySet()){
            Map<String,Object> lieMap = new HashMap<>();
            StringBuilder testUnitSb = new StringBuilder();
            List<String> testItems = datas.get(sn);
            for (int i = 0; i < testItems.size(); i++) {
                testUnitSb.append((i+1)+".").append(testItems.get(i)).append((char)11);
            }
            lieMap.put(key+"_"+"sn",sn);
            lieMap.put(key+"_"+"testItem",testUnitSb.substring(0,testUnitSb.length()-1));
            liedata.add(lieMap);
        }

        result.put("liedata",liedata);
        return result;
    }

    //组件图
    private List<Map<String,Object>> getZujianPic(EntrustInfo entrustInfo){
        List<Map<String,Object>> dataList = new ArrayList<>();

        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for (EntrustTestGroup testGroup : testGroupList){
            /**
             * 同一个试验组中的样品进行的试验项都相同,所以一次性先全取出来
             */
            List<TaskExecuteVO> taskExecuteList = testGroup.getTaskExecuteList();
            for (TaskExecuteVO taskExecuteVO : taskExecuteList){
                if (EntrustConstants.Ability_Type.UNIT.equals(taskExecuteVO.getType())){

                    String imgIds = taskExecuteVO.getImgIds();
                    if(StringUtils.isEmpty(imgIds)){
//                        continue;
                    }else{
                        String[] attIds = imgIds.split(",");
                        for (String id : attIds){
                            if (StringUtils.isNotEmpty(id)) {
                                Map<String, Object> oneMap = new HashMap<>();
                                AttachmentInfo attachmentInfo = assetInfoService.get(id);
                                String fileName = attachmentInfo.getName();
                                if (StringUtils.isEmpty(fileName)){
                                    continue;
                                }
                                fileName = fileName.split(",")[0];
                                for (TaskExecuteVO ptask : taskExecuteList){
                                    if(taskExecuteVO.getPid().equals(ptask.getId())){
                                        fileName = ptask.getName()+"("+fileName+")";
                                        break;
                                    }
                                }
                                oneMap.put("filename", fileName);
                                oneMap.put("filePath", "/file/download/" + attachmentInfo.getId());
                                dataList.add(oneMap);
                            }
                        }
                    }


                    //获取该附件id
                    List<AttachmentInfo> attachmentInfos = assetInfoService.getAttachListByBusiness(taskExecuteVO.getTaskId());

                    if(CollectionUtils.isNotEmpty(attachmentInfos)){
                        for(AttachmentInfo a:attachmentInfos){
                            Map<String, Object> oneFile = new HashMap<>();
                            //只获取图片类型的
                            if(KnowledgeType.Image.getValue().equals(a.getDocType())){
                                String fileName = a.getName();
                                if (StringUtils.isEmpty(fileName)){
//                                    continue;
                                }else{
                                    fileName = fileName.split(",")[0];
                                    for (TaskExecuteVO ptask : taskExecuteList){
                                        if(taskExecuteVO.getPid().equals(ptask.getId())){
                                            fileName = ptask.getName()+"("+fileName+")";
                                            break;
                                        }
                                    }
                                    oneFile.put("filename", fileName);
                                    oneFile.put("filePath", "/file/download/" + a.getId());
                                    dataList.add(oneFile);
                                }

                            }
                        }
                    }
                }
            }
        }
       /* Map<String,Object> oneMap = new HashMap<>();
        oneMap.put("filename","条码号2020302032（使用前图）");
        oneMap.put("filepath","G:/Mine/20171123/IMG_20171029_131605.jpg");
        dataList.add(oneMap);
        oneMap = new HashMap<>();
        oneMap.put("filename","条码号2020302033（使用后图）");
        oneMap.put("filepath","G:/Mine/20171123/IMG_20171103_104915.jpg");
        dataList.add(oneMap);*/
        return dataList;
    }


    /**
     * 试验详情
     * @param entrustInfo
     * @return
     */
    private List<TaskExecuteVO> getJcxList(EntrustInfo entrustInfo){
        /**
         * 结构化任务数据
         */
        List<TaskExecuteVO> taskList = new ArrayList<>();
        List<EntrustTestGroup> testGroupList = entrustInfo.getTestGroupList();
        for(EntrustTestGroup testGroup : testGroupList){
            List<TaskExecuteVO> taskExecuteList = testGroup.getTaskExecuteList();
            for (TaskExecuteVO itemTask : taskExecuteList){
                if(EntrustConstants.Ability_Type.ITEM.equals(itemTask.getType()) && EntrustConstants.TaskStatus.DONE.equals(itemTask.getStatus())){
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
                    taskList.add(itemTask);
                }
            }


        }
         for (int i = 0; i <taskList.size() ; i++) {
            for (int j = 0; j <taskList.get(i).getSubTasks().size() ; j++) {
                TaskExecuteVO vo =taskList.get(i).getSubTasks().get(j);
                vo.setBaogaoYxbq(baogaombYxbqService.getJcxmbyxbqJson(vo.getTemplateId()));//报告已选标签
                Map returnMap =  yuanshijlmbRkService.selectJGData(vo.getTaskId(),vo.getTemplateId());
                if(returnMap!=null)
                {
                    Object fm = returnMap.get("formrkdata");
                    Object lim = returnMap.get("listrkdata");
                    if(fm!=null){
                        vo.setFormrkdata((Map) fm);
                    }
                    if(lim!=null){
                        vo.setListrkdata((List<Map>) lim);
                    }
                }
            }
        }

        /*List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> bqMap = new HashMap<>();
        // TODO: 18/1/8 111111为原始记录模板ID
        bqMap = baogaombYxbqService.getJcxmbyxbqJson("111111");
        result.put("baogaoYxbq",bqMap);

        Map<String,Object> formRkMap = new HashMap<>();
        formRkMap.put("FIELD1form111111","aa");
        result.put("formrkdata",formRkMap);//入库 去入库表查询 表单入库

        List<Map<String,Object>> liedataList = new ArrayList<>();
        Map<String,Object> listRKMap = new HashMap<>();
        listRKMap.put("FIELD1list111111","2222");
        liedataList.add(listRKMap);
        listRKMap = new HashMap<>();
        listRKMap.put("FIELD2list111111","2233");
        listRKMap.put("FIELD1list111111","212334");
        liedataList.add(listRKMap);
        result.put("lierkdata",liedataList);//列表入库
        dataList.add(result);*/
        return taskList;
    }

}
