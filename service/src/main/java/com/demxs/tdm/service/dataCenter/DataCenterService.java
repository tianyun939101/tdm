package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.lab.SubCenterDao;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.business.nostandard.NoStandardEntrustInfo;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestLog;
import com.demxs.tdm.domain.dataCenter.*;
import com.demxs.tdm.domain.dataCenter.VO.DataCenterSearch;
import com.demxs.tdm.domain.dataCenter.constant.DataCenterSearchConstant;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.ATAChapterService;
import com.demxs.tdm.service.business.core.IEntrustService;
import com.demxs.tdm.service.business.nostandard.NoStandardEntrustInfoService;
import com.demxs.tdm.service.business.nostandard.NoStandardTestLogService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DataCenterService {

    @Autowired
    private DataBaseInfoService dataBaseInfoService;

    @Autowired
    private DataTestInfoService dataTestInfoService;

    @Autowired
    private ATAChapterService ataChapterService;

    @Autowired
    private NoStandardEntrustInfoService noStandardEntrustInfoService;

    @Autowired
    private IEntrustService entrustService;

    @Autowired
    private AttachmentInfoService attachmentInfoService;
    
    @Autowired
    private DataRelateSpdmService dataRelateSpdmService;

    @Autowired
    private FlightBindTestInfoDataService bindTestInfoDataService;

    @Autowired
    private NoStandardTestLogService noStandardTestLogService;

    @Autowired
    private LabInfoDao labInfoDao;

    @Autowired
    private SubCenterDao subCenterDao;

    private static final String NATURE_KEY = "entrust_nature";
    /**
     * 获取数据中心树形图
     * @param dataCenterSearch
     * @return
     */
    public List<Map<String, Object>> getTree(DataCenterSearch dataCenterSearch) {
        if(null == dataCenterSearch.getViewType() ||
                DataCenterSearchConstant.ViewType.ATA_TREE.equals(dataCenterSearch.getViewType())){
            return this.getAtaTree();
        }else if(DataCenterSearchConstant.ViewType.LAB_TREE.equals(dataCenterSearch.getViewType())){
            return this.getLabTree();
        }
        return null;
    }

    /**
    * @author Jason
    * @date 2020/7/28 9:16
    * @params []
    * ata章节树视图
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getAtaTree(){
        List<Map<String, Object>> mapList = Lists.newArrayList();
        DataBaseInfo dataBaseInfo = new DataBaseInfo();
        dataBaseInfo.setSearchBy(UserUtils.getUser());
        mapList = dataBaseInfoService.findATADatatree(dataBaseInfo);
        /*//获取ATA章节数据
        List<ATAChapter> ataList = ataChapterService.findList(new ATAChapter());
        //获取ATA关联申请单数据
        for (int i=0; i<ataList.size(); i++){
            ATAChapter e = ataList.get(i);
            mapList.add(this.setMap(e.getId(), e.getParentId(), e.getName(),null,null));
        }
        //根据权限获取数据中心基础数据
        List<DataBaseInfo> dataList = dataBaseInfoService.findAllList(dataBaseInfo);
        for(DataBaseInfo data:dataList){
            if(StringUtils.isNotBlank(data.getAtaChapter())){
                String[] ataArr = data.getAtaChapter().split(",");
                for(String ataId:ataArr){
                    String testId = IdGen.uuid();
                    if((DataCenterConstants.EntrustType.NO_STANDARD).equals(data.getEntrustType())){//非标委托单
                        mapList.add(this.setMap(testId, ataId, data.getTestName(),data.getEntrustType(),data.getEntrustId()));
                    }else if ((DataCenterConstants.EntrustType.STANDARD).equals(data.getEntrustType())){//标准委托单
                        mapList.add(this.setMap(testId, ataId, data.getTestName(),data.getEntrustType(),data.getEntrustId()));
                    }else if((DataCenterConstants.EntrustType.UPLOAD).equals(data.getEntrustType())){//数据提报数据
                        mapList.add(this.setMap(testId, ataId, data.getTestName(),data.getEntrustType(),data.getId()));
                    }else{//其他
                        mapList.add(this.setMap(testId, ataId, data.getTestName(),data.getEntrustType(),null));
                    }
                    for(DataTestInfo test:data.getTestInfoList()){
                        String dataId = IdGen.uuid();
                        mapList.add(this.setMap(dataId, testId, test.getTestItem().getName(),"4",test.getId()));//试验子项目
                        //增加关联spdm数据
                        List<DataRelateSpdm> relateList = dataRelateSpdmService.getByTestId(test.getId());
                        for(DataRelateSpdm relate:relateList){
                            mapList.add(this.setMap(relate.getId(), dataId, "仿真数据","5",relate.getId()));//仿真数据
                        }
                        mapList.add(this.setMap(IdGen.uuid(), dataId, "试飞数据","6",test.getId()));
                    }
                }
            }
        }*/
        return mapList;
    }

    /**
    * @author Jason
    * @date 2020/7/28 9:17
    * @params []
    * 试验室列表视图
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getLabTree() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        DataBaseInfo dataBaseInfo = new DataBaseInfo();
        dataBaseInfo.setSearchBy(UserUtils.getUser());
        //查询速度过慢采用视图整合数据
        mapList = dataBaseInfoService.findLabBaseInfoTree(dataBaseInfo);
        //查询试验性质数据字典
        /*List<Dict> natureList = DictUtils.getDictList(NATURE_KEY);
        Map<String, Dict> natureMap = new HashMap<>(natureList.size());
        for (Dict dict : natureList) {
            natureMap.put(dict.getValue(),dict);
        }
        List<SubCenter> subCenterList = subCenterDao.findList(new SubCenter());
        if(null != subCenterList){
            for (SubCenter subCenter : subCenterList) {
                mapList.add(this.setMap(subCenter.getId(), "0", subCenter.getName(),null,null));
                //查询分中心下的试验室
                List<LabInfo> list = labInfoDao.findList(new LabInfo().setCenter(subCenter.getId()));
                if(null != list){
                    for (LabInfo labInfo : list) {
                        mapList.add(this.setMap(labInfo.getId(), subCenter.getId(), labInfo.getName(),null,null));
                        List<DataBaseInfo> infoList = dataBaseInfoService.findByLabId(new DataBaseInfo().setLabId(labInfo.getId()));
                        Map<String,Integer> dictCountMap = new HashMap<>(infoList.size());
                        for (DataBaseInfo baseInfo : infoList) {
                            if(StringUtils.isNotBlank(baseInfo.getTestNature()) && natureMap.get(baseInfo.getTestNature()) != null){
                                Dict nature = natureMap.get(baseInfo.getTestNature());
                                String natureId = labInfo.getId() + ":" + nature.getId();
                                if((DataCenterConstants.EntrustType.NO_STANDARD).equals(baseInfo.getEntrustType())){
                                    //非标委托单
                                    mapList.add(this.setMap(baseInfo.getId(), natureId, baseInfo.getTestName(),baseInfo.getEntrustType(),baseInfo.getEntrustId()));
                                    dictCountMap.put(nature.getLabel(),dictCountMap.get(nature.getLabel()) == null ? 1 : dictCountMap.get(nature.getLabel()) + 1);
                                }else if ((DataCenterConstants.EntrustType.STANDARD).equals(baseInfo.getEntrustType())){
                                    //标准委托单
                                    mapList.add(this.setMap(baseInfo.getId(), natureId, baseInfo.getTestName(),baseInfo.getEntrustType(),baseInfo.getEntrustId()));
                                    dictCountMap.put(nature.getLabel(),dictCountMap.get(nature.getLabel()) == null ? 1 : dictCountMap.get(nature.getLabel()) + 1);
                                }else if((DataCenterConstants.EntrustType.UPLOAD).equals(baseInfo.getEntrustType())){
                                    //数据提报数据
                                    mapList.add(this.setMap(baseInfo.getId(), natureId, baseInfo.getTestName(),baseInfo.getEntrustType(),baseInfo.getId()));
                                    dictCountMap.put(nature.getLabel(),dictCountMap.get(nature.getLabel()) == null ? 1 : dictCountMap.get(nature.getLabel()) + 1);
                                }else{//其他
                                    mapList.add(this.setMap(baseInfo.getId(), natureId, baseInfo.getTestName(),baseInfo.getEntrustType(),null));
                                }

                                for(DataTestInfo test:baseInfo.getTestInfoList()){
                                    mapList.add(this.setMap(test.getId(), baseInfo.getId(), test.getTestItem().getName(),"4",test.getId()));//试验子项目
                                    //增加关联spdm数据
                                    List<DataRelateSpdm> relateList = dataRelateSpdmService.getByTestId(test.getId());
                                    for(DataRelateSpdm relate:relateList){
                                        mapList.add(this.setMap(relate.getId(), test.getId(), "仿真数据","5",relate.getId()));//仿真数据
                                    }
                                    mapList.add(this.setMap(IdGen.uuid(), test.getId(), "试飞数据","6",test.getId()));
                                }
                            }
                        }
                        for (Dict dict : natureList) {
                            String name = dict.getLabel();
                            if(dictCountMap.get(dict.getLabel()) != null){
                                name += "[" + dictCountMap.get(dict.getLabel()) + "]";
                            }
                            //生成试验室id为前缀的数据字典id
                            mapList.add(this.setMap(labInfo.getId() + ":" + dict.getId(),
                                    labInfo.getId(),name,null,null));
                        }
                    }
                }
            }
        }*/
        return mapList;
    }

    private Map setMap(String id, String pId,String name,String bsType,String bsId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("pId", pId);
        map.put("name", name);
        map.put("bsType", StringUtils.isBlank(bsType)?0:bsType);
        map.put("bsId", StringUtils.isBlank(bsId)?0:bsId);
        return map;
    }

    /**
     * 获取申请单详情
     * @param entrustId
     * @return
     */
    public NoStandardEntrustInfo getNoStandardEntrustInfo(String entrustId) {
        return noStandardEntrustInfoService.get(entrustId);
    }

    /**
     * 获取试验项目详情
     * @param testId
     * @return
     */
    public DataBaseInfo getItemInfo(String testId) {
        DataBaseInfo dataBaseInfo = new DataBaseInfo();
        List<AttachmentInfo> imgList = new ArrayList<>();
        List<AttachmentInfo> videoList = new ArrayList<>();
        List<AttachmentInfo> audioList = new ArrayList<>();
        DataTestInfo dataTestInfo = dataTestInfoService.get(testId);
//        for(DataInfo dataInfo:dataTestInfo.getDatalist()){
//            switch (dataInfo.getDataType()==null?"7":dataInfo.getDataType()){
//                case DataCenterConstants.DataType.ORIGINAL_RECORD://原始记录单
//                    dataTestInfo.setOriginalRecord(dataInfo.getTestData());
//                    break;
//                case DataCenterConstants.DataType.TEST_DATA://试验数据
//                    dataTestInfo.setTestData(dataInfo.getTestData());
//                    break;
//                case DataCenterConstants.DataType.TEST_LOG://试验日志
//                    dataTestInfo.setTestLog(dataInfo.getTestData());
//                    break;
//                case DataCenterConstants.DataType.VIDEO://视频数据
//                    if (StringUtils.isNotEmpty(dataInfo.getTestData())) {
//                        for (String id : dataInfo.getTestData().split(",")) {
//                            AttachmentInfo attachmentInfo = attachmentInfoService.get(id);
//                            if (attachmentInfo != null) {
//                                videoList.add(attachmentInfo);
//                            }
//                        }
//                    }
//                    dataTestInfo.setVideoData(dataInfo.getTestData());
//                    break;
//                case DataCenterConstants.DataType.AUDIO://音频数据
//                    if (StringUtils.isNotEmpty(dataInfo.getTestData())) {
//                        for (String id : dataInfo.getTestData().split(",")) {
//                            AttachmentInfo attachmentInfo = attachmentInfoService.get(id);
//                            if (attachmentInfo != null) {
//                                audioList.add(attachmentInfo);
//                            }
//                        }
//                    }
//                    dataTestInfo.setAudioData(dataInfo.getTestData());
//                    break;
//                case DataCenterConstants.DataType.IMAGE://图片数据
//                    if (StringUtils.isNotEmpty(dataInfo.getTestData())) {
//                        for (String id : dataInfo.getTestData().split(",")) {
//                            AttachmentInfo attachmentInfo = attachmentInfoService.get(id);
//                            if (attachmentInfo != null) {
//                                imgList.add(attachmentInfo);
//                            }
//                        }
//                    }
//                    dataTestInfo.setImgData(dataInfo.getTestData());
//                    break;
//                default:
//                    break;
//            }
//        }
        List<String> orList = new ArrayList<>();
        List<String> tdList = new ArrayList<>();
        List<String> tlList = new ArrayList<>();
        for(DataInfo dataInfo:dataTestInfo.getDatalist()){
            switch (dataInfo.getDataType()==null?"0":dataInfo.getDataType()){
                case DataCenterConstants.DataType.ORIGINAL_RECORD://原始记录单
                    orList.add(dataInfo.getTestData());
                    break;
                case DataCenterConstants.DataType.TEST_DATA://试验数据
                    tdList.add(dataInfo.getTestData());
                    break;
                case DataCenterConstants.DataType.TEST_LOG://试验日志
                    tlList.add(dataInfo.getTestData());
                    break;
                case DataCenterConstants.DataType.VIDEO://视频数据
                   if (StringUtils.isNotEmpty(dataInfo.getTestData())) {
                        for (String id : dataInfo.getTestData().split(",")) {
                            AttachmentInfo attachmentInfo = attachmentInfoService.get(id);
                            if (attachmentInfo != null) {
                                videoList.add(attachmentInfo);
                            }
                        }
                    }
                    break;
                case DataCenterConstants.DataType.AUDIO://音频数据
                    if (StringUtils.isNotEmpty(dataInfo.getTestData())) {
                        for (String id : dataInfo.getTestData().split(",")) {
                            AttachmentInfo attachmentInfo = attachmentInfoService.get(id);
                            if (attachmentInfo != null) {
                                audioList.add(attachmentInfo);
                            }
                        }
                    }
                    break;
                case DataCenterConstants.DataType.IMAGE://图片数据
                    if (StringUtils.isNotEmpty(dataInfo.getTestData())) {
                        for (String id : dataInfo.getTestData().split(",")) {
                            AttachmentInfo attachmentInfo = attachmentInfoService.get(id);
                            if (attachmentInfo != null) {
                                imgList.add(attachmentInfo);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        dataTestInfo.setOriginalRecord(StringUtils.join(orList,","));
        dataTestInfo.setTestData(StringUtils.join(tdList,","));
        dataTestInfo.setTestLog(StringUtils.join(tlList,","));
        dataTestInfo.setImgList(imgList);
        dataTestInfo.setVideoList(videoList);
        dataTestInfo.setAudioList(audioList);
        dataBaseInfo = dataBaseInfoService.get(dataTestInfo.getBaseId());
        dataBaseInfo.setDataTestInfo(dataTestInfo);
        return dataBaseInfo;
    }

    public EntrustInfo getStandardEntrustInfo(String entrustId) {
        return entrustService.get(entrustId);
    }

    public DataBaseInfo getreportDataInfo(String reportId) {
        return dataBaseInfoService.get(reportId);
    }

    public DataRelateSpdm getDataRelateSpdm(String relateId) {
        return dataRelateSpdmService.get(relateId);
    }

    public List<FlightBindTestInfoData> flightDataList(FlightBindTestInfoData bindTestInfoData) {
        return bindTestInfoDataService.findByTestInfoId(bindTestInfoData);
    }

    public List<NoStandardTestLog> getTestLog(String ids) {
        List<NoStandardTestLog> list = new ArrayList<>();
        String[] arr = ids.split(",");
        for(String s:arr){
            list.add(noStandardTestLogService.get(s));
        }
        return list;
    }
}
