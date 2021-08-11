package com.demxs.tdm.service.dataCenter.subcenterconfig;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.IPCDao;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.SubCenterTestTaskDao;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.TestInfoDao;
import com.demxs.tdm.domain.dataCenter.DashboardConfig;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.IPC;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.SubCenterConfig;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.SubCenterTestTask;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.TestInfo;
import com.demxs.tdm.service.dataCenter.DashboardConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jason
 * @Date: 2020/6/18 14:34
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class SubCenterConfigService {

    @Autowired
    private DashboardConfigService dashboardConfigService;
    @Autowired
    private IPCDao ipcDao;
    @Autowired
    private TestInfoDao testInfoDao;
    @Autowired
    private SubCenterTestTaskDao testTaskDao;

    private final static String TEST_TASK_KEY = "subcenter_testTask";
    private final static String TEST_ABILITY_KEY = "subcenter_ability";
    private final static String TEST_RESOURCE_KEY = "subcenter_resource";
    private final static String TEST_OFFICE_KEY = "subcenter_office";
    private final static String[] classes = new String[]{"icon-plane","icon-plane","icon-bag","icon-train"};


    public void save(SubCenterConfig subCenterConfig){
        Map<String, DashboardConfig> map = subCenterConfig.getDashboardConfig();
        if(null != map && !map.isEmpty()){
            for (String key : map.keySet()) {
                DashboardConfig config = map.get(key);
                dashboardConfigService.deleteByType(config);
                dashboardConfigService.save(config);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/6/19 9:44
    * @params [testTaskId, opUser]
    * 获取试验信息
    * @return java.util.Map<java.lang.String,java.lang.Object>
    */
    public Map<String, Object> getTestInfo(String testTaskId, String opUser) {
        Map<String, Object> result = new HashMap<>(5);
        //工控机
        List<IPC> ipcList = ipcDao.findByTestTaskId(testTaskId);
        result.put("equipmentList",ipcList);
        //试验项
        TestInfo testItem = testInfoDao.getByTestTaskId(testTaskId);
        result.put("testItem",testItem);
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
        SubCenterTestTask testTask = testTaskDao.get(testTaskId);
        result.put("process",testTask == null ? new String[]{} : testTask.getProcessList());
        //试验环境待定
        result.put("environment",null);
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/19 10:42
    * @params []
    * 获取试验任务统计
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getTestTaskCount(){
        List<Map<String,Object>> result = new ArrayList<>();
        DashboardConfig config = dashboardConfigService.getByType(new DashboardConfig().setType(TEST_TASK_KEY));
        if(null != config){
            List<String> valueNameList = config.getValueNameList();
            List<String> valueList = config.getValueList();
            for (int i = 0; i < valueNameList.size(); i++) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("name",valueNameList.get(i));
                if(i < valueList.size()){
                    map.put("value",valueList.get(i));
                }
                if(i < classes.length){
                    map.put("class",classes[i]);
                }
                result.add(map);
            }
        }
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/19 10:43
    * @params []
    * 获取试验能力统计
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getTestAbility() {
        List<Map<String, Object>> result = new ArrayList<>();
        DashboardConfig config = dashboardConfigService.getByType(new DashboardConfig().setType(TEST_ABILITY_KEY));
        if (null != config) {
            List<String> valueNameList = config.getValueNameList();
            List<String> valueList = config.getValueList();
            for (int i = 0; i < valueNameList.size(); i++) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("name",valueNameList.get(i));
                if(i < valueList.size()){
                    map.put("value",valueList.get(i));
                }
                result.add(map);
            }
        }
        return result;
    }

    /**
    * @author Jason
    * @date 2020/6/19 10:50
    * @params []
    * 获取试验资源
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String, Object>> getTestResource() {
        List<Map<String, Object>> result = new ArrayList<>();
        DashboardConfig officeName = dashboardConfigService.getByType(new DashboardConfig().setType(TEST_OFFICE_KEY));
        List<String> nameList = null;
        if(null != officeName){
            nameList = officeName.getValueList();
        }
        DashboardConfig config = dashboardConfigService.getByType(new DashboardConfig().setType(TEST_RESOURCE_KEY));
        if (null != config) {
            List<String> valueNameList = config.getValueNameList();
            String[] valueList = config.getValueList() == null ? null : config.getValueList().get(0).split(DashboardConfig.getSplitSymbol());
            Map<String, Object> map = new HashMap<>(3);
            map.put("nameList",nameList);
            map.put("valueNameList",valueNameList);
            if(null != valueList){
                List<String[]> list = new ArrayList<>();
                for (String s : valueList) {
                    list.add(s.split(","));
                }
                map.put("valueList",list);
            }
            result.add(map);
        }
        return result;
    }
}
