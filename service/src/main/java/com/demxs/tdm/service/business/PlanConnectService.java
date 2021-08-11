package com.demxs.tdm.service.business;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.PlanConnectDao;
import com.demxs.tdm.dao.business.PlanDao;
import com.demxs.tdm.domain.business.PlanConnect;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanConnectService extends CrudService<PlanConnectDao, PlanConnect> {


    @Autowired
    PlanConnectDao   planConnectDao;
    @Autowired
    OfficeDao officeDao;
    /**
     * 责任人完成率统计
     * @return
     */
    public List<Map<String, Object>> findState(String labId, String userIds, Date start, Date end, String name,String parentId,String time){
        List<Map<String,Object>> result = new ArrayList<>();
        //存储型号计划
        List<PlanConnect> planConnect1 = new ArrayList<PlanConnect>();
        //存储非型号计划
        List<PlanConnect> stateModel = new ArrayList<PlanConnect>();
        List<PlanConnect> toolModel = new ArrayList<PlanConnect>();
        //查询是否包含子节点
        List<Office> childrenByParentIdOne = officeDao.findChildrenByParentIdOne(parentId);
        if(CollectionUtils.isNotEmpty(childrenByParentIdOne)){
            //包含子节点
            int i = 0;
            int j = 0;
             for(Office office : childrenByParentIdOne){
                 List<PlanConnect> tool = new ArrayList<PlanConnect>();
                parentId = office.getId();
                 String parentIds = office.getParentIds();
                 if(StringUtils.isNotBlank(parentIds) &&parentIds.split(",").length<4){
                     planConnect1 = planConnectDao.findState(labId, start , end ,userIds,name,parentId,"",time);
                        stateModel = planConnectDao.findStateModel(labId, start, end, userIds, name,parentId,"",time);
                        if(CollectionUtils.isNotEmpty(planConnect1)){
                            int finishedCont = 0;
                            int inExecutionCont = 0;
                            int responseCont = 0;
                            int planwork = 0;
                            int prawork = 0;
                            PlanConnect planConnect = new PlanConnect();
                            for(int m = 0;m<planConnect1.size();m++){
                                int finished = Integer.valueOf((stateModel.get(m).getFinished() == null||stateModel.get(m).getFinished() == ""?"0":stateModel.get(m).getFinished()));
                                int inExecution = Integer.valueOf((stateModel.get(m).getInExecution() == null||stateModel.get(m).getInExecution() == ""?"0":stateModel.get(m).getInExecution()));
                                int response = Integer.valueOf((stateModel.get(m).getResponse() == null||stateModel.get(m).getResponse() == ""?"0":stateModel.get(m).getResponse()));
                                double plan = Double.valueOf(stateModel.get(m).getPlanWorkingHour() == null||stateModel.get(m).getPlanWorkingHour() == ""?"0":stateModel.get(m).getPlanWorkingHour());
                                double pra =Double.valueOf(stateModel.get(m).getPraWorkingHour() == null||stateModel.get(m).getPraWorkingHour() == ""?"0":stateModel.get(m).getPraWorkingHour());
                                finishedCont+=finished;
                                inExecutionCont+=inExecution;
                                responseCont+=response;
                                planwork+=plan;
                                prawork+=pra;
                            }
                            planConnect.setFinished(String.valueOf(finishedCont));
                            planConnect.setInExecution(String.valueOf(inExecutionCont));
                            planConnect.setResponse(String.valueOf(responseCont));
                            planConnect.setPlanWorkingHour(String.valueOf(planwork));
                            planConnect.setPraWorkingHour(String.valueOf(prawork));
                            planConnect.setLiableBody(childrenByParentIdOne.get(i).getId());
                            tool.add(planConnect);
                            i++;
                        }
                        if(CollectionUtils.isNotEmpty(stateModel)){
                            int finishedCont = 0;
                            int inExecutionCont = 0;
                            int responseCont = 0;
                            int planwork = 0;
                            int prawork = 0;
                            PlanConnect planConnect = new PlanConnect();
                            for(int m = 0;m<stateModel.size();m++){
                                int finished = Integer.valueOf((stateModel.get(m).getFinished() == null||stateModel.get(m).getFinished() == ""?"0":stateModel.get(m).getFinished()));
                                int inExecution = Integer.valueOf((stateModel.get(m).getInExecution() == null||stateModel.get(m).getInExecution() == ""?"0":stateModel.get(m).getInExecution()));
                                int response = Integer.valueOf((stateModel.get(m).getResponse() == null||stateModel.get(m).getResponse() == ""?"0":stateModel.get(m).getResponse()));
                                double plan = Double.valueOf(stateModel.get(m).getPlanWorkingHour() == null||stateModel.get(m).getPlanWorkingHour() == ""?"0":stateModel.get(m).getPlanWorkingHour());
                                double pra =Double.valueOf(stateModel.get(m).getPraWorkingHour() == null||stateModel.get(m).getPraWorkingHour() == ""?"0":stateModel.get(m).getPraWorkingHour());

                                finishedCont+=finished;
                                inExecutionCont+=inExecution;
                                responseCont+=response;
                                planwork+=plan;
                                prawork+=pra;
                            }
                            planConnect.setFinished(String.valueOf(finishedCont));
                            planConnect.setInExecution(String.valueOf(inExecutionCont));
                            planConnect.setResponse(String.valueOf(responseCont));
                            planConnect.setPlanWorkingHour(String.valueOf(planwork));
                            planConnect.setPraWorkingHour(String.valueOf(prawork));
                            planConnect.setLiableBody(childrenByParentIdOne.get(j).getId());
                            tool.add(planConnect);
                            j++;
                        }
                         this.Tool(result,tool);
                    }else{
                     //不包含子节点
                     planConnect1 =planConnectDao.findState(labId, start , end ,userIds,name,"",parentId,time);
                     stateModel = planConnectDao.findStateModel(labId, start, end, userIds, name,"",parentId,time);
                     this.Tool(result,planConnect1);
                     this.Tool(result,stateModel);
                 }
             }
        }else{
            //不包含子节点
            planConnect1 =planConnectDao.findState(labId, start , end ,userIds,name,"",parentId,time);
            stateModel = planConnectDao.findStateModel(labId, start, end, userIds, name,"",parentId,time);
            this.Tool(result,planConnect1);
            this.Tool(result,stateModel);
        }
        return result;
    }





    //柱状图
    public List<Map<String, Object>> findColum(String labId, String userIds, Date start, Date end, String name,String time){
        List<Map<String,Object>> result = new ArrayList<>();
        List<PlanConnect> colum = planConnectDao.findColum(labId, start, end, name,userIds);
        if(colum!=null || colum.size()!=0) {
            for (PlanConnect planConnect : colum) {
                Map<String, Object> map = new HashMap<>();
                String a = planConnect.getLiableUser().getName();
                map.put("liableUser", a);////责任人体
                map.put("count", planConnect.getPlanGross());//计划总量
                result.add(map);
            }
        }
        List<PlanConnect> findColumModel = planConnectDao.findColumModel(labId, start, end, name,userIds);
        if(findColumModel!=null || findColumModel.size()!=0){
            for (PlanConnect planConnect: findColumModel ) {
                Map<String,Object> map = new HashMap<>();
                String a =  planConnect.getLiableUser().getName();
                if(StringUtils.isNotBlank(a)){
                    String[] split = a.split("#");
                    map.put("liableBody",split[split.length-1]);//责任主体
                }
                map.put("count",planConnect.getPlanGross());//计划总量
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 任务数
     * @param labId
     * @param userIds
     * @param start
     * @param end
     * @param name
     * @return
     */
    public List<Map<String, Object>> findPie(String labId, String userIds, Date start, Date end, String name,String parentId,String time) {
        List<Map<String, Object>> result = new ArrayList<>();
        //存储型号计划
        List<PlanConnect> planConnect1 = new ArrayList<PlanConnect>();
        //存储非型号计划
        List<PlanConnect> stateModel = new ArrayList<PlanConnect>();
        List<PlanConnect> tool = new ArrayList<PlanConnect>();
        List<PlanConnect> toolModel = new ArrayList<PlanConnect>();
        //查询是否包含子节点
        List<Office> childrenByParentIdOne = officeDao.findChildrenByParentIdOne(parentId);

        if (CollectionUtils.isNotEmpty(childrenByParentIdOne)) {
            //包含子节点
            int i = 0;
            int j = 0;
            for (Office office : childrenByParentIdOne) {
                parentId = office.getId();
                String parentIds = office.getParentIds();
                if(StringUtils.isNotBlank(parentIds) && parentIds.split(",").length<4){
                        planConnect1 = planConnectDao.findPie(labId, start, end, userIds, name, parentId, "", time);
                        stateModel = planConnectDao.findPieModel(labId, start, end, userIds, name, parentId, "", time);
                        if (CollectionUtils.isNotEmpty(planConnect1)) {
                            int count = 0;
                            PlanConnect planConnect = new PlanConnect();
                            for (int m = 0; m < planConnect1.size(); m++) {
                                int sum = Integer.valueOf(planConnect1.get(m).getPlanGross() == null || planConnect1.get(m).getPlanGross() == "" ? "0" : planConnect1.get(m).getPlanGross());
                                count += sum;
                            }
                            planConnect.setPlanGross(String.valueOf(count));
                            planConnect.setLiableBody(childrenByParentIdOne.get(i).getId());
                            tool.add(planConnect);
                            i++;
                        }
                        if (CollectionUtils.isNotEmpty(stateModel)) {
                            int count = 0;
                            PlanConnect planConnect = new PlanConnect();
                            for (int m = 0; m < stateModel.size(); m++) {
                                int sum = Integer.valueOf(stateModel.get(m).getPlanGross() == null || stateModel.get(m).getPlanGross() == "" ? "0" : stateModel.get(m).getPlanGross());
                                count += sum;
                            }
                            planConnect.setPlanGross(String.valueOf(count));
                            planConnect.setLiableBody(childrenByParentIdOne.get(j).getId());
                            tool.add(planConnect);
                            j++;
                        }
                }else{
                    //不包含子节点
                    planConnect1 = planConnectDao.findPie(labId, start, end, name, userIds, "", parentId, time);
                    stateModel = planConnectDao.findPieModel(labId, start, end, name, userIds, "", parentId, time);
                    if (planConnect1 != null || planConnect1.size() != 0) {
                        for (PlanConnect planConnect : planConnect1) {
                            Map<String, Object> map = new HashMap<>();
                            String a = planConnect.getLiableBody();
                            Office listByNameIfExistOne = officeDao.get(a);
                            if (listByNameIfExistOne != null) {
                                map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                                map.put("count", planConnect.getPlanGross());//工时
                            }
                            result.add(map);
                        }
                    }
                    if (stateModel != null || stateModel.size() != 0) {
                        for (PlanConnect planConnect : stateModel) {
                            Map<String, Object> map = new HashMap<>();
                            String a = planConnect.getLiableBody();
                            Office listByNameIfExistOne = officeDao.get(a);
                            if (listByNameIfExistOne != null) {
                                map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                                map.put("count", planConnect.getPlanGross());//计划总量

                            }
                            result.add(map);
                        }
                    }
                }
            }
            if (tool != null || tool.size() != 0) {
                for (PlanConnect planConnect : tool) {
                    Map<String, Object> map = new HashMap<>();
                    String a = planConnect.getLiableBody();
                    Office listByNameIfExistOne = officeDao.get(a);
                    if (listByNameIfExistOne != null) {
                        map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                        map.put("count", planConnect.getPlanGross());//工时
                    }
                    result.add(map);
                }
            }
        } else {
            //不包含子节点
            planConnect1 = planConnectDao.findPie(labId, start, end, name, userIds, "", parentId, time);
            stateModel = planConnectDao.findPieModel(labId, start, end, name, userIds, "", parentId, time);
            if (planConnect1 != null || planConnect1.size() != 0) {
                for (PlanConnect planConnect : planConnect1) {
                    Map<String, Object> map = new HashMap<>();
                    String a = planConnect.getLiableBody();
                    Office listByNameIfExistOne = officeDao.get(a);
                    if (listByNameIfExistOne != null) {
                        map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                        map.put("count", planConnect.getPlanGross());//工时
                    }
                    result.add(map);
                }
            }
            if (stateModel != null || stateModel.size() != 0) {
                for (PlanConnect planConnect : stateModel) {
                    Map<String, Object> map = new HashMap<>();
                    String a = planConnect.getLiableBody();
                    Office listByNameIfExistOne = officeDao.get(a);
                    if (listByNameIfExistOne != null) {
                        map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                        map.put("count", planConnect.getPlanGross());//计划总量

                    }
                    result.add(map);
                }
            }
        }
        return result;
    }
    /**
     * 工时
     * @param labId
     * @param userIds
     * @param start
     * @param end
     * @param name
     * @return
     */
    public List<Map<String, Object>> hourList(String labId, String userIds, Date start, Date end, String name,String parentId,String time){
        List<Map<String, Object>> result = new ArrayList<>();
        //存储型号计划
        List<PlanConnect> planConnect1 = new ArrayList<PlanConnect>();
        //存储非型号计划
        List<PlanConnect> stateModel = new ArrayList<PlanConnect>();
        List<PlanConnect> tool = new ArrayList<PlanConnect>();
        List<PlanConnect> toolModel = new ArrayList<PlanConnect>();
        //查询是否包含子节点
        List<Office> childrenByParentIdOne = officeDao.findChildrenByParentIdOne(parentId);
        if (CollectionUtils.isNotEmpty(childrenByParentIdOne)) {
            //包含子节点
            int i = 0;
            int j = 0;
            for (Office office : childrenByParentIdOne) {
                parentId = office.getId();
                String parentIds = office.getParentIds();
                if(StringUtils.isNotBlank(parentIds) && parentIds.split(",").length<4){
                        planConnect1 = planConnectDao.hour(labId, start, end,userIds, "",parentId,time);
                        stateModel = planConnectDao.hourModel(labId, start, end,userIds, "",parentId,time);
                        if (CollectionUtils.isNotEmpty(planConnect1)) {
                            int count = 0;
                            PlanConnect planConnect = new PlanConnect();
                            for (int m = 0; m < planConnect1.size(); m++) {
                                int sum = Integer.valueOf(planConnect1.get(m).getPlanGross() == null || planConnect1.get(m).getPlanGross() == "" ? "0" : planConnect1.get(m).getPlanGross());
                                count += sum;
                            }
                            planConnect.setPlanGross(String.valueOf(count));
                            planConnect.setLiableBody(childrenByParentIdOne.get(i).getId());
                            tool.add(planConnect);
                            i++;
                        }
                        if (CollectionUtils.isNotEmpty(stateModel)) {
                            int count = 0;
                            PlanConnect planConnect = new PlanConnect();
                            for (int m = 0; m < stateModel.size(); m++) {
                                int sum = Integer.valueOf(stateModel.get(m).getPlanGross() == null || stateModel.get(m).getPlanGross() == "" ? "0" : stateModel.get(m).getPlanGross());
                                count += sum;
                            }
                            planConnect.setPlanGross(String.valueOf(count));
                            planConnect.setLiableBody(childrenByParentIdOne.get(j).getId());
                            tool.add(planConnect);
                            j++;
                        }
                    }else{
                    planConnect1 = planConnectDao.hourById(labId, start, end,userIds, "",parentId,time);
                    stateModel = planConnectDao.hourModelById(labId, start, end,userIds, "",parentId,time);
                    if (planConnect1 != null || planConnect1.size() != 0) {
                        for (PlanConnect planConnect : planConnect1) {
                            Map<String, Object> map = new HashMap<>();
                            String a = planConnect.getLiableBody();
                            Office listByNameIfExistOne = officeDao.get(a);
                            if (listByNameIfExistOne != null) {
                                map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                                map.put("count", planConnect.getPlanGross());//工时
                                result.add(map);
                            }
                        }
                    }
                    if (stateModel != null || stateModel.size() != 0) {
                        for (PlanConnect planConnect : stateModel) {
                            Map<String, Object> map = new HashMap<>();
                            String a = planConnect.getLiableBody();
                            Office listByNameIfExistOne = officeDao.get(a);
                            if (listByNameIfExistOne != null) {
                                map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                                map.put("count", planConnect.getPlanGross());//计划总量

                            }
                            result.add(map);
                        }
                    }
                }
            }
            if (tool != null || tool.size() != 0) {
                for (PlanConnect planConnect : tool) {
                    Map<String, Object> map = new HashMap<>();
                    String a = planConnect.getLiableBody();
                    Office listByNameIfExistOne = officeDao.get(a);
                    if (listByNameIfExistOne != null) {
                        map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                        map.put("count", planConnect.getPlanGross());//工时
                        result.add(map);
                    }
                }
            }
        } else {
            //不包含子节点
            planConnect1 = planConnectDao.hourById(labId, start, end,userIds, "",parentId,time);
            stateModel = planConnectDao.hourModelById(labId, start, end,userIds, "",parentId,time);
            if (planConnect1 != null || planConnect1.size() != 0) {
                for (PlanConnect planConnect : planConnect1) {
                    Map<String, Object> map = new HashMap<>();
                    String a = planConnect.getLiableBody();
                    Office listByNameIfExistOne = officeDao.get(a);
                    if (listByNameIfExistOne != null) {
                        map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                        map.put("count", planConnect.getPlanGross());//工时
                        result.add(map);
                    }
                }
            }
            if (stateModel != null || stateModel.size() != 0) {
                for (PlanConnect planConnect : stateModel) {
                    Map<String, Object> map = new HashMap<>();
                    String a = planConnect.getLiableBody();
                    Office listByNameIfExistOne = officeDao.get(a);
                    if (listByNameIfExistOne != null) {
                        map.put("liableBody", listByNameIfExistOne.getName());//责任主体\责任人派出部门
                        map.put("count", planConnect.getPlanGross());//计划总量

                    }
                    result.add(map);
                }
            }
        }
        return result;
    }
   /* public List<Map<String, Object>> hourList(String labId, String userIds, Date start, Date end, String name,String parentId,String time){



        List<PlanConnect> colum = new ArrayList<PlanConnect>();
        List<PlanConnect> findPieModel =new ArrayList<PlanConnect>();
        List<Office> childrenByParentIdOne = officeDao.findChildrenByParentIdOne(parentId);
        if(CollectionUtils.isEmpty(childrenByParentIdOne)){
            colum = planConnectDao.hourById(labId, start, end,userIds, "",parentId,time);
            findPieModel = planConnectDao.hourModelById(labId, start, end,userIds, "",parentId,time);
        }else{
            colum = planConnectDao.hour(labId, start, end,userIds, "",parentId,time);
            findPieModel = planConnectDao.hourModel(labId, start, end,userIds, "",parentId,time);
        }
        List<Map<String,Object>> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(colum)){
            for(PlanConnect planConnect : colum){
                Map<String,Object> map = new HashMap<>();
                String a = planConnect.getLiableBody();
                map.put("liableBody",a);//责任主体\责任人派出部门
                map.put("count",planConnect.getPlanGross());//工时总量
                result.add(map);
            }
        }


        if(CollectionUtils.isNotEmpty(findPieModel)){
            for(PlanConnect planConnect : findPieModel){
                Map<String,Object> map = new HashMap<>();
                String a = planConnect.getLiableBody();
                map.put("liableBody",a);//责任主体\责任人派出部门门
                map.put("count",planConnect.getPlanGross());//工时总量
                result.add(map);
            }
        }
        return result;
    }*/

    /**
     *
     * @param result
     * @param list
     * @return
     */
    public List<Map<String, Object>> Tool(List<Map<String, Object>>  result,List<PlanConnect> list){
        if(CollectionUtils.isNotEmpty(list)){
            int count = 0;
            for(PlanConnect planConnect : list){
                Map<String,Object> map = new HashMap<>();
                count =  Integer.valueOf(
                        planConnect.getFinished()==null?"0":planConnect.getFinished())+
                        Integer.valueOf(planConnect.getInExecution()==null?"0":planConnect.getInExecution())+
                        Integer.valueOf(planConnect.getResponse()==null?"0":planConnect.getResponse());
                if(StringUtils.isBlank(planConnect.getPlanWorkingHour())){  //计划工时
                    planConnect.setPlanWorkingHour("0");
                }
                if(StringUtils.isBlank(planConnect.getPraWorkingHour())){   //实动工时
                    planConnect.setPraWorkingHour("0");
                }

                map.put("finished",planConnect.getFinished());//已完成
                map.put("inExecution",planConnect.getInExecution());//执行中
                map.put("response",planConnect.getResponse());//已响应
                String a = planConnect.getLiableBody();
                Office listByNameIfExistOne = officeDao.get(a);
                if(listByNameIfExistOne!=null){
                    map.put("liableBody",listByNameIfExistOne.getName());//责任主体\责任人派出部门
                }
                map.put("count",count);//计划总量
                map.put("rate",String.format("%.2f", (Double.valueOf(planConnect.getFinished()==null?"0":planConnect.getFinished())/count)*100));//完成率String.format("%.2f", value).toString();
                map.put("planWorkingHour",planConnect.getPlanWorkingHour());//计划工时
                map.put("praWorkingHour",planConnect.getPraWorkingHour());//实动工时
                double s =  (Double.valueOf(planConnect.getPraWorkingHour())/Double.valueOf(planConnect.getPlanWorkingHour()))*100;
                if(Double.isNaN(s)){
                    map.put("rateTime","0.00");//实动工时占比
                }else{
                    map.put("rateTime",String.format("%.2f", (Double.valueOf(planConnect.getPraWorkingHour())/Double.valueOf(planConnect.getPlanWorkingHour()))*100));//实动工时占比
                }
                //责任人
                if(planConnect.getLiableUser()!=null ){
                    map.put("name",planConnect.getLiableUser().getName());
                }else{
                    map.put("name",null);
                }
                result.add(map);
            }
        }
        return result;
    }
}
