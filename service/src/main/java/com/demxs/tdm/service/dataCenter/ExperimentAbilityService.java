package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.ExperimentAbilityDao;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.dao.resource.yuangong.YuangongDao;
import com.demxs.tdm.domain.dataCenter.DashboardConfig;
import com.demxs.tdm.service.ability.TestCategoryService;
import com.demxs.tdm.service.lab.LabInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class ExperimentAbilityService  {
    @Autowired
    private ExperimentAbilityDao experimentAbilityDao;
    @Autowired
    private TestCategoryService testCategoryService;
    @Autowired
    private LabInfoDao labInfoDao;
    @Autowired
    private ShebeiDao shebeiDao;
    @Autowired
    private YuangongDao yuangongDao;
    @Autowired
    private LabInfoService labInfoService;

    /**
     * 试验能力统计
     * @return
     */
    public DashboardConfig find(String centerName){
        DashboardConfig dashboardConfig = new DashboardConfig();
        List<String> valueNameList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        //String others = experimentAbilityDao.findOthers();
        List<Map<String, Object>> maps = experimentAbilityDao.find(centerName);
        for(Map<String, Object> map : maps){
            valueList.add(map.get("STATUS").toString());
            valueNameList.add(map.get("LEVELL").toString());
        }
        dashboardConfig.setValueNameList(valueNameList);
        dashboardConfig.setValueList(valueList);
        return dashboardConfig;
    }

    public DashboardConfig findCount(String centerName){
        DashboardConfig dashboardConfig = new DashboardConfig();
        List<String> valueNameList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        List<Map<String, Object>> maps = experimentAbilityDao.findCount(centerName);
        for(Map<String, Object> map : maps){
            if(map.get("STATUSNAME")!=null && map.get("STATUS") != null){
                valueList.add(map.get("STATUS").toString());
                valueNameList.add(map.get("STATUSNAME").toString());
            }
        }
        List<String> valueNameList1 = new ArrayList<>();
        for(String str: valueNameList){
            switch(str){
                case "A":
                    valueNameList1.add("未形成");
                    break;
                case "B":
                    valueNameList1.add("试运行");
                    break;
                case "C":
                    valueNameList1.add("初步具备");
                    break;
                case "D":
                    valueNameList1.add("已具备");
                    break;
            }
        }
        int a = 0;
        for(String s : valueList){
            a += Integer.parseInt(s);
        }
        valueNameList1.add(0,"能力总数");
        valueList.add(0,String.valueOf(a));
        dashboardConfig.setValueNameList(valueNameList1);
        dashboardConfig.setValueList(valueList);
        return dashboardConfig;
    }
    /**
     * 试验室 试验验证能力
     */
    public DashboardConfig getVerificationCapability(String centerName){
        DashboardConfig dashboardConfig = new DashboardConfig();
        List<String> valueList = new ArrayList<>();
        if(StringUtils.isNotBlank(centerName)){
            List<Map<String, Object>> center = experimentAbilityDao.getCenterAbilityLevel(centerName);
            List<Map<String, Object>> centerList = complement(center);
            String stA = centerList.get(0).get("total").toString();
            String stB = centerList.get(1).get("total").toString();
            String stC = centerList.get(2).get("total").toString();
            String stD = centerList.get(3).get("total").toString();
            valueList.add(stA);
            valueList.add(stB);
            valueList.add(stC);
            valueList.add(stD);
            dashboardConfig.setValueList(valueList);
            dashboardConfig.setCenter(centerName);
        }else{
            //北研中心
            List<Map<String, Object>> centerlBY = experimentAbilityDao.getCenterAbilityLevel("北研中心");
            List<Map<String, Object>> centerlBYList = complement(centerlBY);
            //客服公司
            List<Map<String, Object>> serviceCom = experimentAbilityDao.getCenterAbilityLevel("客服公司");
            List<Map<String, Object>> serviceComList = complement(serviceCom);
            //上飞公司
            List<Map<String, Object>> sfCom = experimentAbilityDao.getCenterAbilityLevel("上飞公司");
            List<Map<String, Object>> sfComList = complement(sfCom);
            //上飞院
            List<Map<String, Object>> sfCour = experimentAbilityDao.getCenterAbilityLevel("上飞院");
            List<Map<String, Object>> sfCourList = complement(sfCour);
            //试飞中心
            List<Map<String, Object>> centerFlight = experimentAbilityDao.getCenterAbilityLevel("试飞中心");
            List<Map<String, Object>> centerFlightList = complement(centerFlight);
            //数据装填
            String stA = centerlBYList.get(0).get("total").toString()+","+serviceComList.get(0).get("total").toString()+","+sfComList.get(0).get("total").toString()+","+sfCourList.get(0).get("total").toString()+","+centerFlightList.get(0).get("total").toString();
            String stB = centerlBYList.get(1).get("total").toString()+","+serviceComList.get(1).get("total").toString()+","+sfComList.get(1).get("total").toString()+","+sfCourList.get(1).get("total").toString()+","+centerFlightList.get(1).get("total").toString();
            String stC = centerlBYList.get(2).get("total").toString()+","+serviceComList.get(2).get("total").toString()+","+sfComList.get(2).get("total").toString()+","+sfCourList.get(2).get("total").toString()+","+centerFlightList.get(2).get("total").toString();
            String stD = centerlBYList.get(3).get("total").toString()+","+serviceComList.get(3).get("total").toString()+","+sfComList.get(3).get("total").toString()+","+sfCourList.get(3).get("total").toString()+","+centerFlightList.get(3).get("total").toString();

            valueList.add(stA);
            valueList.add(stB);
            valueList.add(stC);
            valueList.add(stD);
            dashboardConfig.setValueList(valueList);
            dashboardConfig.setCenter("北研中心,客服公司,上飞公司,上飞院,试飞中心");
        }
        return dashboardConfig;
    }

    /**
     * 补全数据
     * @param list
     * @return
     */
    public List<Map<String, Object>> complement(List<Map<String, Object>> list){
        String str = new String();
        String strName = new String();
        Map<String, Object> maps = new HashMap<String, Object>();
        if(list.size()<4){
            for(Map<String, Object> map : list){
                str += map.get("status");
                strName = map.get("center").toString();
            }
            if(str.indexOf("A")== -1){
                maps.put("center",strName);
                maps.put("status","A");
                maps.put("total","0");
                list.add(0,maps);
            }
            if(str.indexOf("B")== -1){
                maps.put("center",strName);
                maps.put("status","B");
                maps.put("total","0");
                list.add(1,maps);
            }
            if(str.indexOf("C")== -1){
                maps.put("center",strName);
                maps.put("status","C");
                maps.put("total","0");
                list.add(2,maps);
            }
            if(str.indexOf("D")== -1){
                maps.put("center",strName);
                maps.put("status","D");
                maps.put("total","0");
                list.add(3,maps);
            }
        }
        return list;

    }
    /**
     * 试验室能力建设状态
     */
    public DashboardConfig abilityBuild(String centerName){

        DashboardConfig dashboardConfig = new DashboardConfig();
        List<String> valueNameList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        String stA = "";
        String stB = "";
        String stC = "";
        String stD = "";
        String st = "";
        //中心
        String centerId = null;
        if(StringUtils.isNotBlank(centerName)){
            List<String> centerByCenterName = labInfoDao.findCenterByCenterName(centerName);
            centerId = centerByCenterName.get(0);
        }
        //获取试验室
        List<String> xAxis = labInfoService.getEnalbeLabByCenter(null);
        List<String> allEnalbeLab = labInfoDao.findAllEnalbeLab(centerId);
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        List<String> d = new ArrayList<>();
        //获取试验室数据
        List<Map<String, Object>> maps = experimentAbilityDao.abilityBuild(centerName);
        for(int i = 0;i<=allEnalbeLab.size()-1;i++){
            valueNameList.add(allEnalbeLab.get(i));
            for(Map<String,Object> map : maps){
                if(allEnalbeLab.get(i).equals(map.get("name"))){
                    st+=map.get("status").toString();
                }
            }
            if(st.length()<4 || st.indexOf("0") !=1){
                if (st.indexOf("A") == -1) {
                    a.add(i,"0");
                }
                if (st.indexOf("B") == -1) {
                    b.add(i,"0");
                }
                if (st.indexOf("C") == -1) {
                    c.add(i,"0");
                }
                if (st.indexOf("D") == -1) {
                    d.add(i,"0");
                }
            }
            for(Map<String,Object> map : maps){
                if(allEnalbeLab.get(i).equals(map.get("name"))){
                    switch (map.get("status").toString()) {
                        case "A":
                            a.add(i, map.get("total").toString());
                            break;
                        case "B":
                            b.add(i, map.get("total").toString());
                            break;
                        case "C":
                            c.add(i, map.get("total").toString());
                            break;
                        case "D":
                            d.add(i, map.get("total").toString());
                            break;
                    }
                }
            }
            st = "";
        }
        for(String strA : a){
            stA+=strA+"," ;
        }
        for(String strB : b){
            stB+=strB+"," ;
        }
        for(String strC : c){
            stC+=strC+"," ;
        }
        for(String strD : d){
            stD+=strD+"," ;
        }
        valueList.add(stA);
        valueList.add(stB);
        valueList.add(stC);
        valueList.add(stD);
        dashboardConfig.setValueNameList(valueNameList);
        dashboardConfig.setValueList(valueList);
        return dashboardConfig;
    }

    /**
     * 综合看板
     * @return
     */
    public DashboardConfig comprehensive(String centerName){
        DashboardConfig dashboardConfig = new DashboardConfig();
        List<String> valueNameList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        String value = "";
        //获取所有设备
        String dataSum=shebeiDao.getEquipmentSum("","",centerName);    //3945
        //公司试验室
        String s = null;
        if(StringUtils.isNotBlank(centerName)){
            List<String> centerByCenterName = labInfoDao.findCenterByCenterName(centerName);
            s = centerByCenterName.get(0);
        }
        List<String> allEnalbeLab = labInfoDao.findAllEnalbeLab(s);
        String size = String.valueOf(allEnalbeLab.size()) ;
        //试验能力
        List<Map<String, Object>> status = experimentAbilityDao.findStatus(centerName);
        String planAbi = new String();
        String haveAbi = new String();
        int bAbi = 0;
        int cAbi = 0;
        int construction = 0;
        for(Map<String, Object> map : status){
            switch (map.get("status").toString()){
                case "A":
                    planAbi = map.get("total").toString();
                    break;
                case "B":
                    bAbi= Integer.parseInt(map.get("total").toString());
                    break;
                case "C":
                    cAbi= Integer.parseInt(map.get("total").toString());
                    break;
                case "D":
                    haveAbi= map.get("total").toString();
                    break;
            }
        }
        construction = bAbi + cAbi; //建设中
        //试验人员
        String testUser = "0";
        String count = yuangongDao.count(centerName);
        if(StringUtils.isNotBlank(count)){
            testUser = count;
        }
        valueList.add("0");valueNameList.add("历史承接的任务");
        valueList.add(planAbi);valueNameList.add("规划的试验能力");
        valueList.add(String.valueOf(construction));valueNameList.add("建设中试验能力");
        valueList.add(size);valueNameList.add("公司试验室");
        valueList.add(dataSum);valueNameList.add("试验设备");
        valueList.add(haveAbi);  valueNameList.add("已形成试验能力");
        valueList.add("0");valueNameList.add("正在开展的试验任务");
        valueList.add("0");valueNameList.add("试验服务商数量");
        valueList.add("0");valueNameList.add("试验营业额");
        valueList.add(testUser);valueNameList.add("试验人员");

        dashboardConfig.setValueNameList(valueNameList);
        dashboardConfig.setValueList(valueList);
        return dashboardConfig;
    }
    public DashboardConfig center(String centerName) {
        DashboardConfig dashboardConfig = new DashboardConfig();
        List<String> stringList = experimentAbilityDao.centerName();
        dashboardConfig.setValueList(stringList);
        return dashboardConfig;
    }

}