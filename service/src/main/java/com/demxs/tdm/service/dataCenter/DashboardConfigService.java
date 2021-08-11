package com.demxs.tdm.service.dataCenter;

import cc.eguid.commandManager.CommandManager;
import cc.eguid.commandManager.CommandManagerImpl;
import cc.eguid.commandManager.data.CommandTasker;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.ability.TestCategoryDao;
import com.demxs.tdm.dao.ability.TestItemUnitDao;
import com.demxs.tdm.dao.business.EntrustInfoDao;
import com.demxs.tdm.dao.business.TestTaskDao;
import com.demxs.tdm.dao.business.dz.DzAbilityAtlasDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardEntrustInfoDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardExecutionDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardExecutionItemDao;
import com.demxs.tdm.dao.dataCenter.DashboardConfigDao;
import com.demxs.tdm.dao.dataCenter.DataBaseInfoDao;
import com.demxs.tdm.dao.lab.LabEquipmentBindDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.domain.ability.TestCategory;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.ability.TestItemUnit;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.business.constant.NoStandardEntrustInfoEnum;
import com.demxs.tdm.domain.business.dz.DzAbilityAtlas;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecutionItem;
import com.demxs.tdm.domain.dataCenter.DashboardConfig;
import com.demxs.tdm.domain.dataCenter.ReportEquipmentInfo;
import com.demxs.tdm.domain.dataCenter.ReportResourceInfo;
import com.demxs.tdm.domain.dataCenter.ReportTestTaskInfo;
import com.demxs.tdm.domain.lab.LabEquipmentBind;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author: Jason
 * @Date: 2020/6/2 10:31
 * @Description: 关于数据大屏的配置服务
 */
@Service
@Transactional(readOnly = true)
public class DashboardConfigService extends CrudService<DashboardConfigDao,DashboardConfig> {

    @Autowired
    private TestCategoryDao testCategoryDao;
    @Autowired
    private DzAbilityAtlasDao abilityAtlasDao;
    @Autowired
    private ShebeiDao shebeiDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private NoStandardEntrustInfoDao noStandardEntrustInfoDao;
    @Autowired
    private EntrustInfoDao standardEntrustInfoDao;
    @Autowired
    private TestTaskDao testTaskDao;
    @Autowired
    private NoStandardExecutionItemDao executionItemDao;
    @Autowired
    private NoStandardExecutionDao executionDao;
    @Autowired
    private TestItemUnitDao testItemUnitDao;
    @Autowired
    private LabEquipmentBindDao equipmentBindDao;

    @Autowired
    private DataBaseInfoService dataBaseInfoService;
    @Autowired
    private ExperimentAbilityService experimentAbilityService;
    @Autowired
    private DataBaseInfoDao dataBaseInfoDao;

    @Autowired
    private SystemService systemService;



    private static Logger logger = LoggerFactory.getLogger(DashboardConfigService.class);

    private final static String SERVER_HOST = Global.getConfig("nginx.server.host");
    private final static String REMOTE_DESKTOP_ADDRESS = Global.getConfig("remote.desktop.address");
    private final static String REMOTE_DESKTOP_PORT = Global.getConfig("remote.desktop.port");
    /**
     * 执行cmd命令行对象
     */
    private static CommandManager commandManager = new CommandManagerImpl();
    /**
     * 任务映射：任务名 -> 存活/已关闭
     */
    private final static Map<String,Integer> LIVE_TASK = new HashMap<>(16);
    private final static Integer LIVED = 1;
    private final static Integer CLOSED = 0;
    private final static ScheduledExecutorService SINGLE_POOL = Executors.newSingleThreadScheduledExecutor();
    /**
     * 每60秒监听一次心跳，判断用户是否已离开本页面
     */
    private final static int CYCLE = 60;
    static {
        startMonitoring();
    }


    @Transactional(readOnly = false)
    public DashboardConfig getByType(DashboardConfig config1){
        DashboardConfig config= this.dao.getByType(config1);
        if("1".equals(config.getStatus())){
            config=getConfig(config1.getType(),config1.getCenter());
        }
        if("center".equals(config1.getType())){
            config = experimentAbilityService.center(config1.getCenter());
        }
        return  config;
    }

    @Transactional(readOnly = false)
    public DashboardConfig getByTypeNew(DashboardConfig config){
        config= this.dao.getByType(config);
        return  config;

    }

    @Transactional(readOnly = false)
    public int deleteByType(DashboardConfig config){
        return this.dao.deleteByType(config);
    }

    @Transactional(readOnly = false)
    public void save(List<DashboardConfig> configList){
        if(null != configList && !configList.isEmpty()){
            for (DashboardConfig dashboardConfig : configList) {
                if(StringUtils.isNotEmpty(dashboardConfig.getStatus())){
                    if("0".equals(dashboardConfig.getStatus())){
                        this.deleteByType(dashboardConfig);
                        dashboardConfig.setStatus("0");
                        super.save(dashboardConfig);
                    }else{
                        this.dao.updateStatus(dashboardConfig.getType(),"1");
                    }
                }else{
                    this.deleteByType(dashboardConfig);
                    dashboardConfig.setStatus("0");
                    super.save(dashboardConfig);
                }

            }
        }
    }

    /**
    * @author Jason
    * @date 2020/6/5 16:57
    * @params []
    * 阻燃大屏试验资源面板数据支持
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getFlameTestResource(){
        List<Map<String,Object>> result = new ArrayList<>();
        List<TestCategory> rootNodeList = testCategoryDao.findRootNode(new TestCategory());
        List<DzAbilityAtlas> allList = abilityAtlasDao.findAllList(new DzAbilityAtlas());
        if(null != rootNodeList){
            for (TestCategory root : rootNodeList) {
                Set<String> labIdSet = new HashSet<>(500);
                for (DzAbilityAtlas dzAbilityAtlas : allList) {
                    if(null != dzAbilityAtlas && null != dzAbilityAtlas.getTestCategory()){
                        //代表是根
                        if(null == dzAbilityAtlas.getTestCategory().getParent()){
                            //判断是否是当前循环的根节点
                            if(root.getId().equals(dzAbilityAtlas.getTestCategory().getId())){
                                labIdSet.add(dzAbilityAtlas.getLabId());
                            }
                        }else{
                            if(StringUtils.isNotBlank(dzAbilityAtlas.getTestCategory().getParentIds())){
                                String[] ids = dzAbilityAtlas.getTestCategory().getParentIds().split(",");
                                for (String id : ids) {
                                    TestCategory category = testCategoryDao.get(id);
                                    //代表是根
                                    if(null != category && null == category.getParent()){
                                        //判断是否是当前循环的根节点
                                        if(root.getId().equals(id)){
                                            labIdSet.add(dzAbilityAtlas.getLabId());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                /*List<String> children = testCategoryDao.findChildren(root);
                List<String> testCategoryIds = new ArrayList<>(children.size()+1);
                testCategoryIds.add(root.getId());
                testCategoryIds.addAll(children);
                //oracle单次where in() 语句不允许超过1000条目
                int size = 1000;
                int count = testCategoryIds.size() / size;
                //不足size长度则不截取
                if(count == 0){
                    labIdSet.addAll(abilityAtlasDao.findByCategoryId(testCategoryIds));
                }else{
                    for(int i = 0 ; i < count ; i ++){
                        int range = Math.min((i + 1) * size, testCategoryIds.size());
                        labIdSet.addAll(abilityAtlasDao.findByCategoryId(testCategoryIds.subList(i * size ,range)));
                    }
                }*/
                int equipmentCount = 0;
                if(!labIdSet.isEmpty()){
                    equipmentCount = shebeiDao.findCountByLabId(new ArrayList<>(labIdSet));
                }
                int userCount = 0;
                for (String id : labIdSet) {
                    List<User> userlist = userDao.findByLabIdAndRoleId(id, null);
                    if(null != userlist){
                        userCount += userlist.size();
                    }
                }
                Map<String, Object> map = new HashMap<>(3);
                map.put("name",root.getName().replaceAll("试验",""));
                map.put("nameList",new String[]{"试验人员分布","试验设备数量","标准规范数量","知识流程数量"});
                map.put("value",new int[]{userCount,equipmentCount});
                result.add(map);
            }
        }
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/8 9:40
    * @params []
    * 阻燃大屏试验能力面板数据支持
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getFlameTestAbility() {
        List<Map<String,Object>> result = new ArrayList<>();
        List<TestCategory> rootNodeList = testCategoryDao.findRootNode(new TestCategory());
        List<DzAbilityAtlas> allList = abilityAtlasDao.findAllList(new DzAbilityAtlas());
        if(null != rootNodeList && null != allList) {
            for (TestCategory root : rootNodeList) {
                int count = 0;
                for (DzAbilityAtlas dzAbilityAtlas : allList) {
                    if(null != dzAbilityAtlas && null != dzAbilityAtlas.getTestCategory()){
                        if(dzAbilityAtlas.getTestCategory().getId().equals(root.getId()) ||
                            dzAbilityAtlas.getTestCategory().getParentIds().contains(root.getId()) ||
                                dzAbilityAtlas.getTestCategory().getParentId().equals(root.getId())){
                            count++;
                        }
                    }
                }
                Map<String, Object> map = new HashMap<>(2);
                map.put("name",root.getName());
                map.put("value",count);
                result.add(map);
            }
        }
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/8 14:16
    * @params []
    * 阻燃大屏试验任务统计面板数据支持
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getFlameTestTask() {
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String, Object> totalCount = new HashMap<>(2);
        totalCount.put("name","总任务数");
        totalCount.put("value",noStandardEntrustInfoDao.selectTotalCount(null) +
                standardEntrustInfoDao.selectTotalCount(null));
        totalCount.put("class","icon-plane");
        Map<String, Object> completeCount = new HashMap<>(2);
        completeCount.put("name","已完成");
        completeCount.put("value",noStandardEntrustInfoDao.selectTotalCount(NoStandardEntrustInfoEnum.FILE.getCode()) +
                standardEntrustInfoDao.selectTotalCount(EntrustConstants.FinishStage.DONE.toString()));
        completeCount.put("class","icon-plane");
        Map<String, Object> handingCount = new HashMap<>(2);
        handingCount.put("name","进行中");
        handingCount.put("value",noStandardEntrustInfoDao.selectHandingCount(NoStandardEntrustInfoEnum.FILE.getCode()) +
                standardEntrustInfoDao.selectHandingCount(EntrustConstants.FinishStage.DONE.toString(),
                        EntrustConstants.FinishStage.UNDO.toString(),
                        EntrustConstants.FinishStage.STOP.toString()));
        handingCount.put("class","icon-train");
        Map<String, Object> delayCount = new HashMap<>(2);
        delayCount.put("name","已延期");
        delayCount.put("value",Integer.parseInt(totalCount.get("value").toString())
                - (Integer.parseInt(completeCount.get("value").toString()) + Integer.parseInt(handingCount.get("value").toString())));
        delayCount.put("class","icon-bag");
        result.add(totalCount);
        result.add(completeCount);
        result.add(delayCount);
        result.add(handingCount);
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/8 16:11
    * @params [labId]
    * 阻燃大屏试验任务列表面板数据支持
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getFlameTestList(String labId) {
        List<Map<String,Object>> result = new ArrayList<>();
        List<TestTask> standardTestTaskList = standardEntrustInfoDao.findTestTaskByLabId(labId);
        if(null != standardTestTaskList){
            for (TestTask testTask : standardTestTaskList) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("name",testTask.getTaskName());
                map.put("executionName",testTask.getOwnerName());
                map.put("type",EntrustConstants.EntrustType.STANDARD);
                map.put("id",testTask.getId());
                map.put("labId",labId);
                result.add(map);
            }
        }
        List<String> noStandardExecutionIdList = executionDao.findIdByLabId(labId);
        if(null != noStandardExecutionIdList){
            for (String id : noStandardExecutionIdList) {
                List<NoStandardExecutionItem> executionItemList = executionItemDao.findByExecutionId(id);
                if(null != executionItemList){
                    for (NoStandardExecutionItem executionItem : executionItemList) {
                        Map<String, Object> map = new HashMap<>(4);
                        map.put("name",executionItem.getTestItem().getName());
                        map.put("executionName",executionItem.getOpUser());
                        map.put("type",EntrustConstants.EntrustType.NO_STANDARD);
                        map.put("id",executionItem.getId());
                        map.put("labId",labId);
                        result.add(map);
                    }
                }
            }
        }
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/9 9:35
    * @params [type, testTaskId, opUser]
    * 阻燃大屏试验信息面板数据支持
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public Map<String, Object> getFlameTestInfo(String type, String testTaskId,String opUser,String labId) {
        Map<String,Object> result = new HashMap<>(6);
        //试验介绍
        if(EntrustConstants.EntrustType.STANDARD.equals(type)){
            TestItem testItem = testTaskDao.getTestItemById(testTaskId);
            result.put("testItem",testItem);
            Shebei s = new Shebei();
            s.setLabInfo(new LabInfo(labId));
            //工控机通道
            List<Shebei> shebeiList = shebeiDao.findList(s);
            List<TestItemUnit> testItemUnitList = testItemUnitDao.findByItem(testItem.getId());
            if(null != shebeiList && null != testItemUnitList){
                Set<LabEquipmentBind> set = new HashSet<>();
                for (Shebei shebei : shebeiList) {
                    for (TestItemUnit testItemUnit : testItemUnitList) {
                        if(null != shebei && StringUtils.isNotBlank(shebei.getShebeizz()) &&
                            null != testItemUnit && null != testItemUnit.getUnit() &&
                                StringUtils.isNotBlank(testItemUnit.getUnit().getDeviceCredentials())){
                            String shebeizz = shebei.getShebeizz();
                            String unitZz = testItemUnit.getUnit().getDeviceCredentials();
                            if(shebeizz.contains(unitZz) || unitZz.contains(shebeizz)){
                                //查询绑定信息
                                LabEquipmentBind bind = equipmentBindDao.getByLabIdAndEquipId(new LabEquipmentBind().setLabId(labId).setEquipId(shebei.getId()));
                                if(null != bind){
                                    set.add(bind.setShebei(shebei));
                                }
                            }
                        }
                    }
                }
                result.put("equipmentList",set.toArray());
            }
        }else if(EntrustConstants.EntrustType.NO_STANDARD.equals(type)){
            NoStandardExecutionItem executionItem = executionItemDao.get(testTaskId);
            if(null != executionItem){
                TestItem testItem = executionItem.getTestItem();
                result.put("testItem",testItem);
                Shebei s = new Shebei();
                s.setLabInfo(new LabInfo(labId));
                //工控机通道
                List<Shebei> shebeiList = shebeiDao.findList(s);
                List<TestItemUnit> testItemUnitList = testItem.getTestUnitsList();
                if(null != shebeiList && null != testItemUnitList){
                    Set<LabEquipmentBind> set = new HashSet<>();
                    for (Shebei shebei : shebeiList) {
                        for (TestItemUnit testItemUnit : testItemUnitList) {
                            if(null != shebei && StringUtils.isNotBlank(shebei.getShebeizz()) &&
                                    null != testItemUnit && null != testItemUnit.getUnit() &&
                                    StringUtils.isNotBlank(testItemUnit.getUnit().getDeviceCredentials())){
                                String shebeizz = shebei.getShebeizz();
                                String unitZz = testItemUnit.getUnit().getDeviceCredentials();
                                if(shebeizz.contains(unitZz) || unitZz.contains(shebeizz)){
                                    //查询绑定信息
                                    LabEquipmentBind bind = equipmentBindDao.getByLabIdAndEquipId(new LabEquipmentBind().setLabId(labId).setEquipId(shebei.getId()));
                                    if(null != bind){
                                        set.add(bind.setShebei(shebei));
                                    }
                                }
                            }
                        }
                    }
                    result.put("equipmentList",set.toArray());
                }
            }
        }
        //试验环境待定
        result.put("environment",null);
        if(StringUtils.isNotBlank(opUser)){
            //试验人员
            if(opUser.contains("，")){
                result.put("opUser",opUser.split("，"));
            }else if(opUser.contains(",")){
                result.put("opUser",opUser.split(","));
            }else {
                result.put("opUser",new String[]{opUser});
            }
        }
        //参试人员待定
        result.put("testTakers",null);
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/12 12:58
    * @params [channel, address, userName, password, preChannel]
    * 阻燃大屏视频通道面板
    * @return boolean
    */
    public boolean transport(String channel,String address,String userName,String password,String preChannel){
        //执行命令行之前，先停止上一个进程
        this.stop(preChannel);
        String command = "ffmpeg -re  -rtsp_transport tcp -i rtsp://"+userName+":"+password+"@"+address+
                "554 -f flv -vcodec libx264 -vprofile baseline -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 640x360 -q 10 " +
                SERVER_HOST+channel;

        CommandTasker task = commandManager.query(channel);
        if(null == task){
            String status = commandManager.start(channel, command);
            LIVE_TASK.put(channel,LIVED);
            if(status == null){
                return false;
            }
        }
        return true;
    }

    /**
    * @author Jason
    * @date 2020/6/22 12:26
    * @params [channel]
    * 停止一个任务
    * @return boolean
    */
    public boolean stop(String channel){
        CommandTasker tasker = commandManager.query(channel);
        LIVE_TASK.put(channel,CLOSED);
        if(tasker == null){
            return true;
        }
        return commandManager.stop(channel);
    }

    /**
    * @author Jason
    * @date 2020/6/22 12:26
    * @params [name]
    * 保持任务存活状态
    * @return void
    */
    public void keep(String name){
        LIVE_TASK.put(name,LIVED);
    }

    /**
    * @author Jason
    * @date 2020/6/22 12:26
    * @params [name]
    * 定时任务
    * @return void
    */
    private static void startMonitoring(){
        SINGLE_POOL.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(!LIVE_TASK.isEmpty()){
                    for (String taskName : LIVE_TASK.keySet()) {
                        if(StringUtils.isNotBlank(taskName)){
                            if(null == LIVE_TASK.get(taskName) || CLOSED.equals(LIVE_TASK.get(taskName))){
                                CommandTasker tasker = commandManager.query(taskName);
                                if(null != tasker){
                                    commandManager.stop(taskName);
                                }
                                LIVE_TASK.put(taskName,CLOSED);
                            }else{
                                LIVE_TASK.put(taskName,null);
                            }
                        }
                    }
                }
            }
        },0,CYCLE, TimeUnit.SECONDS);
    }

    /**
    * @author Jason
    * @date 2020/6/23 14:59
    * @params []
    * 获取视频设备nginx服务器地址
    * @return java.lang.String
    */
    public String getBaseRtmpUrl() {
        return SERVER_HOST;
    }

    /**
    * @author Jason
    * @date 2020/6/23 15:00
    * @params []
    * 获取远程桌面代理服务器地址、端口
    * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String,Object> getRemoteDesktopAddress(){
        Map<String, Object> map = new HashMap<>(2);
        map.put("address",REMOTE_DESKTOP_ADDRESS);
        map.put("port",REMOTE_DESKTOP_PORT);
        return map;
    }

    /**
    * @author Jason
    * @date 2020/8/28 13:26
    * @params []
    * 拉取阿里云服务器上的试验数据文件
    * @return java.util.List<java.lang.String>
    */
    public List<String> reloadRealTimeData() {
        return SimpleGetObjectSample.getRealTimeData();
    }

    private final static class SimpleGetObjectSample{
        private static String endpoint = "oss-cn-shanghai.aliyuncs.com";
        private static String accessKeyId = "LTAI4G9RHQhRJN95V9hPXZ92";
        private static String accessKeySecret = "eOLKOudvQSXIZ2h80upSkWlg3hSJ8d";

        private static String bucketName = "comac-gdjl";
        /**
         * 文件key
         */
        private static String key = "gdjl";
        private static final int DEFAULT_LINE_COUNT = 6;
        /**
         * 分隔符
         */
        private static final String SYMBOL = ",";
        /**
         * 正负浮点、整数匹配
         */
        static String reg = "-?[0-9]+.*[0-9]*";

        private static List<String> getRealTimeData(){
            List<String> list = null;
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            try {
                System.out.println("Downloading an object");
                OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent(),"GBK"));
                list = new ArrayList<>(DEFAULT_LINE_COUNT);
                while (true) {
                    String line = reader.readLine();
                    if (line == null){
                        break;
                    }
                    if(line.contains(SYMBOL)){
                        String[] split = line.split(SYMBOL);
                        if(split.length > 0){
                            for (int i = 0; i < split.length; i++) {
                                if(split[i].matches(reg)){
                                    list.add(split[i]);
                                }
                            }
                        }
                    }
                }
                reader.close();
            } catch (OSSException oe) {
                logger.error("Caught an OSSException, which means your request made it to OSS, "
                        + "but was rejected with an error response for some reason.");
                logger.error("Error Message: " + oe.getErrorMessage());
                logger.error("Error Code:       " + oe.getErrorCode());
                logger.error("Request ID:      " + oe.getRequestId());
                logger.error("Host ID:           " + oe.getHostId());
            } catch (ClientException ce) {
                logger.error("Caught an ClientException, which means the client encountered "
                        + "a serious internal problem while trying to communicate with OSS, "
                        + "such as not being able to access the network.");
                logger.error("Error Message: " + ce.getMessage());
            }catch (IOException ioe){
                logger.error("IOException Error Message: " + ioe.getMessage());
            }
            finally {
                client.shutdown();
            }
            return list;
        }
    }
    public DashboardConfig getConfig(String type,String center){
        DashboardConfig config=null;
        if("task".equals(type)){
            config=dataBaseInfoService.getDataSum(center);
        }
        if("taskStatistics".equals(type)){
          //  config=dataBaseInfoService.getTaskDataList();
            //优化项
            config=dataBaseInfoService.getTaskMessList();
        }
        if("equipment".equals(type)){
            config=getEquipmentSum(center);
        }
        if("resourceStatistics".equals(type)){
            config=getLabDetailSum(center);
        }
        if("labAbility".equals(type)){  //试验能力统计
            config = experimentAbilityService.find(center);
        }
        if("labAbilityCount".equals(type)){
            config = experimentAbilityService.findCount(center);
        }
        if("verificationCapability".equals(type)){  //试验验证能力
            config = experimentAbilityService.getVerificationCapability(center);
        }
        if("abilityBuild".equals(type)){  //试验室能力建设状态
            config = experimentAbilityService.abilityBuild(center);
        }
        if("comprehensive".equals(type)){//综合看板
            config = experimentAbilityService.comprehensive(center);
        }
        return config;
    }
    //设备统计
    public DashboardConfig  getEquipmentSum(String center){
        DashboardConfig  config=new DashboardConfig();
        List<String> list=new ArrayList<>();
        //获取所有设备
        String dataSum=shebeiDao.getEquipmentSum("","",center);
        list.add(dataSum);
        //获取本年度所有设备
        String nowSum=shebeiDao.getEquipmentSum("","1",center);
        list.add(nowSum);
        //获取正在适用所有设备
        String useSum=shebeiDao.getEquipmentSum("0","",center);
        list.add(useSum);
        //获取闲置设备
        String freeSum=shebeiDao.getEquipmentSum("2","",center);
        list.add(freeSum);
        String value=dataSum+","+nowSum+","+useSum+","+freeSum;
        config.setValue(value);
        config.setType("equipment");
        config.setValueList(list);
        config.setStatus("1");
        return config;
    }


    //设备统计
    public List<ReportEquipmentInfo> getEquipmentInfo(String center){
        ReportEquipmentInfo  r=new ReportEquipmentInfo();
        List<ReportEquipmentInfo>  l=new ArrayList<>();
        //获取所有设备
        String dataSum=shebeiDao.getEquipmentSum("","",center);
        r.setEquipmentSum(dataSum);
        //获取本年度所有设备
        String nowSum=shebeiDao.getEquipmentSum("","1",center);
        r.setNewEquipment(nowSum);
        //获取正在适用所有设备
        String useSum=shebeiDao.getEquipmentSum("0","",center);
        r.setInUseEquipment(useSum);
        //获取闲置设备
        String freeSum=shebeiDao.getEquipmentSum("2","",center);
        r.setFreeEquipment(freeSum);
        l.add(r);
        return l;
    }

    //试验资源统计
    public List<ReportResourceInfo> getTestResourceSum(String center){
        //获取试验资源统计
        List<Map<String,String>>  list=this.dao.getLabDetailList(center);
        List<ReportResourceInfo>  l=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ReportResourceInfo  c=new ReportResourceInfo();
            //获取实验室名称
            String labName=String.valueOf(list.get(i).get("centerName"));
            //获取实验室人员数量
            String  userSum=String.valueOf(list.get(i).get("userSum"));
            //获取试验设备数量
            String  equipmentSum=String.valueOf(list.get(i).get("equipmentSum"));
            //获取标准体系数量
            String  knowlegerSum=String.valueOf(list.get(i).get("knowledgeSum"));
            c.setKnowlegeSum(knowlegerSum);
            c.setLabName(labName);
            c.setTestEquipmentSum(equipmentSum);
            c.setTestUserSum(userSum);
            l.add(c);
        }
        return l;
    }

    //试验资源统计
    public DashboardConfig getLabDetailSum(String center){
        DashboardConfig  config=new DashboardConfig();
        //获取试验资源统计
        List<Map<String,String>>  list=this.dao.getLabDetailList(center);
        List<String>  l=new ArrayList<>();
        String value="";
        String  valueName="";
        String  equipmentStr="";
        String  userStr="";
        String  knowlegeStr="";
        for(int i=0;i<list.size();i++){
            //获取实验室名称
            String labName=String.valueOf(list.get(i).get("centerName"));
            //获取实验室人员数量
            String  userSum=String.valueOf(list.get(i).get("userSum"));
            //获取试验设备数量
            String  equipmentSum=String.valueOf(list.get(i).get("equipmentSum"));
            //获取标准体系数量
            String  knowlegerSum=String.valueOf(list.get(i).get("knowledgeSum"));
            if(i==list.size()-1){
                valueName+=labName;
                userStr+=userSum;
                equipmentStr+=equipmentSum;
                knowlegeStr+=knowlegerSum;
            }else{
                valueName=valueName+labName+",";
                userStr=userStr+userSum+",";
                equipmentStr=equipmentStr+equipmentSum+",";
                knowlegeStr=knowlegeStr+knowlegerSum+",";
            }

        }
        l.add(userStr);
        l.add(equipmentStr);
        l.add(knowlegeStr);
        value=userStr+"/-/"+equipmentStr+"/-/"+knowlegeStr;
        config.setType("resourceStatistics");
        config.setValue(value);
        config.setValueName(valueName);
        config.setValueList(l);
        config.setStatus("1");
        return config;
    }

    //获取试验任务信息
    public List<ReportTestTaskInfo>  getTaskMessList(){

        List<String> dataList=new ArrayList<>();
        List<String> centerList=new ArrayList<>();
        List<ReportTestTaskInfo> l=new ArrayList<>();
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String u= UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getLoginName();
        if(StringUtils.isNotEmpty(u)){
            String subCenter=systemService.getUserBelongTestCenter(u);
            centerList.add(subCenter);
        }else{
            centerList.add("上飞院");
            centerList.add("上飞公司");
            centerList.add("北研中心");
            centerList.add("客服公司");
            centerList.add("试飞中心");
        }

        String value="";
        String dataTime="";
        //获取单位下试验数量
        List<Map<String,String>> centerDataList=dataBaseInfoDao.getCenterDataMess();
        //获取外部服务商下试验数量
        List<Map<String,String>> serverDataList=dataBaseInfoDao.getServerDataMess();
        //如果为空，全部只为0
        if(!CollectionUtils.isEmpty(centerDataList)){
            for(int i=0;i<centerList.size();i++) {
                ReportTestTaskInfo r = new ReportTestTaskInfo();
                r.setCenterName(centerList.get(i));
                r.setMonth1("0");
                r.setMonth2("0");
                r.setMonth3("0");
                r.setMonth4("0");
                r.setMonth5("0");
                r.setMonth6("0");
                r.setMonth7("0");
                r.setMonth8("0");
                r.setMonth9("0");
                r.setMonth10("0");
                r.setMonth11("0");
                r.setMonth12("0");
                String center = centerList.get(i);
                for (int j = 0; j < centerDataList.size(); j++) {
                    String centerName = String.valueOf(centerDataList.get(j).get("centerName"));
                    if (center.equals(centerName)) {
                        String dateInfo = String.valueOf(centerDataList.get(j).get("dateInfo"));
                        String dataSum = String.valueOf(centerDataList.get(j).get("dataSum"));
                        if(dateInfo.equals("null")){
                            continue;
                        }
                        String[] ss = dateInfo.split("-");
                        switch (ss[1]) {
                            case "01":
                                r.setMonth1(dataSum);
                                break;
                            case "02":
                                r.setMonth2(dataSum);
                                break;
                            case "03":
                                r.setMonth3(dataSum);
                                break;
                            case "04":
                                r.setMonth4(dataSum);
                                break;
                            case "05":
                                r.setMonth5(dataSum);
                                break;
                            case "06":
                                r.setMonth6(dataSum);
                                break;
                            case "07":
                                r.setMonth7(dataSum);
                                break;
                            case "08":
                                r.setMonth8(dataSum);
                                break;
                            case "09":
                                r.setMonth9(dataSum);
                                break;
                            case "10":
                                r.setMonth10(dataSum);
                                break;
                            case "11":
                                r.setMonth11(dataSum);
                                break;
                            case "12":
                                r.setMonth12(dataSum);
                                break;
                        }
                    } else {
                        continue;
                    }

                }
                l.add(r);
            }
        }
        //如上面代码对试验服务商进行判断

        if(!CollectionUtils.isEmpty(serverDataList)) {
            ReportTestTaskInfo r = new ReportTestTaskInfo();
            r.setCenterName("试验服务商");
            r.setMonth1("0");
            r.setMonth2("0");
            r.setMonth3("0");
            r.setMonth4("0");
            r.setMonth5("0");
            r.setMonth6("0");
            r.setMonth7("0");
            r.setMonth8("0");
            r.setMonth9("0");
            r.setMonth10("0");
            r.setMonth11("0");
            r.setMonth12("0");
            for (int j = 0; j < serverDataList.size(); j++) {
                String dateInfo = String.valueOf(serverDataList.get(j).get("dateInfo"));
                String dataSum = String.valueOf(serverDataList.get(j).get("dataSum"));
                String[] ss = dateInfo.split("-");
                switch (ss[1]) {
                    case "01":
                        r.setMonth1(dataSum);
                        break;
                    case "02":
                        r.setMonth2(dataSum);
                        break;
                    case "03":
                        r.setMonth3(dataSum);
                        break;
                    case "04":
                        r.setMonth4(dataSum);
                        break;
                    case "05":
                        r.setMonth5(dataSum);
                        break;
                    case "06":
                        r.setMonth6(dataSum);
                        break;
                    case "07":
                        r.setMonth7(dataSum);
                        break;
                    case "08":
                        r.setMonth8(dataSum);
                        break;
                    case "09":
                        r.setMonth9(dataSum);
                        break;
                    case "10":
                        r.setMonth10(dataSum);
                        break;
                    case "11":
                        r.setMonth11(dataSum);
                        break;
                    case "12":
                        r.setMonth12(dataSum);
                        break;
                }

            }
            l.add(r);
        }
        return l;
    }

    public void updateStatus(String type,String status){
        this.dao.updateStatus(type,status);
    }
}
