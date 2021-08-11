package com.demxs.tdm.service.business;

import com.demxs.tdm.comac.common.act.utils.EmailUtil;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.ImportExcel;
import com.demxs.tdm.common.web.OpResult;
import com.demxs.tdm.dao.business.ModelOrderDao;
import com.demxs.tdm.dao.business.ModelPlanDao;
import com.demxs.tdm.domain.business.ModelOrder;
import com.demxs.tdm.domain.business.ModelPlan;
import com.demxs.tdm.domain.business.Plan;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.resource.yuangong.YuangongService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class ModelOrderService extends CrudService<ModelOrderDao, ModelOrder> {

    @Autowired
    ModelPlanDao modelPlanDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ModelOrderDao modelOrderDao;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private FileStore fileStore;
    @Autowired
    private AttachmentInfoService attachmentInfoService;
    @Autowired
    private YuangongService yuangongServicel;
    @Autowired
    private ModelPlanService modelPlanService;
    @Autowired
    private OfficeDao officeDao;

    private static final  SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private static final String BASE_DIR = Global.getConfig("sourceDir");
     @Async
    public Future<String> remind(ModelOrder modelOrder){
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        String id = modelOrder.getId();
        if(StringUtils.isNotEmpty(id)){
            //本人任务单的数据，且处于未完成状态
            ModelOrder modelPlan1 = modelOrderDao.get(id);
            if(modelPlan1!=null){
                //任务单
                String workId = modelPlan1.getId();
                //查询本任务单未完成任务
                List<ModelPlan> status = modelPlanDao.findStatus(workId);
                for(ModelPlan modelPlan : status){
                   /* User user = new User();
                    userDao.getByEmpNo()*/
                    User liableUser =userDao.get(modelPlan.getLiableUser());    //负责人
                    if(liableUser!=null){
                        String email = liableUser.getEmail();
                        String userId = modelPlan.getLiableUser();
                        //查询该人任务完成情况
                        List<ModelOrder> stateSend = modelOrderDao.findStatusByUserId(userId);
                        for(ModelOrder modelOrder1:stateSend){
                            //执行中
                            String inExecution = modelOrder1.getInExecution();
                            //已响应
                            String response = modelOrder1.getResponse();
                            //
                            Date requireCompleteTime = modelPlan.getCompleteTime();
                            String format = s2.format(requireCompleteTime);
                             int a = Integer.valueOf(inExecution)+Integer.valueOf(response);
                            emailUtil.sendMail(email,"型号任务计划完成情况：","截止今日，您还有"+a+"项任务尚未完成。其中包含执行中"+inExecution+"项,已响应"+response+"项。所有任务截止完成日期为"+format+",请尽快完成任务！");
                        }
                    }
                }
            }
        }
        try {
            Thread.sleep(250);
        }catch (InterruptedException  e){
            logger.debug("邮件发送失败！请联系管理员。");
            e.printStackTrace();
            return new AsyncResult<String>("邮件发送失败！请联系管理员。");
        }
        return new AsyncResult<String>("邮件正在发送。");
    }

    @Transactional
    public List<ModelPlan> exportNew(ModelOrder modelOrder){


        List<Map<Object,Object>> objs = new ArrayList<Map<Object,Object>>();
        List<ModelPlan> modelPlanList = new ArrayList<ModelPlan>();
        List<ModelPlan> workOrderList = new ArrayList<ModelPlan>();
        List<ModelPlan> backRe = new ArrayList<ModelPlan>();    //重名
        try {
            //创建人
            modelOrder.setEstablishName(UserUtils.getUser().getName());
            //维护人
            modelOrder.setAccendant(UserUtils.getUser().getName());
            //导入的excel的ID
            String workID = modelOrder.getWorkId();
            //先存储任务单基本信息
            super.save(modelOrder);
            /*List<ModelPlan> workIds = modelPlanDao.findWorkId(workID);
            if(CollectionUtils.isNotEmpty(workIds)){
                    modelPlanDao.delete(workID);
            }*/

            //导入的文件ID
            if (workID != null && !"".equals(workID)) {
                List<String> officeName = officeDao.getOfficeName();
                //FTP 服务器读取文件
                InputStream is = fileStore.downloadFileToStream(BASE_DIR + attachmentInfoService.get(workID).getPath());
                Workbook workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);
                workOrderList = tool(workOrderList, sheet);
                Cell cell1 = sheet.getRow(0).getCell(0);
                if(cell1 != null){
                String stringCellValue = cell1.getStringCellValue();
                int i = stringCellValue.indexOf(":");
                String substring = stringCellValue.substring(i+1, stringCellValue.length());
                System.out.println(substring);
                Date parse = s1.parse(substring);
                    modelOrder.setUpdateTime(parse);
                }else{
                        modelOrder.setUpdateTime(new Date());
                }
                super.save(modelOrder);
                List<ModelPlan> modelPlans = new ArrayList<ModelPlan>();
                a:for(ModelPlan modelPlan : workOrderList){
                    if(StringUtils.isNotBlank(modelPlan.getUserOutorg())){
                        String[] split = modelPlan.getUserOutorg().split("#");
                        if(split[0].indexOf("试验验证中心")<0){
                            continue  a;
                        }
                    }
                    //删除重复数据
                    modelPlanDao.deleteRe(modelPlan.getSerialNumber(),modelPlan.getName());
                    modelPlanDao.deleteRe1(modelPlan.getSerialNumber(),modelPlan.getName());
                    modelPlan.setWorkId(modelOrder.getId());
                    modelPlan.setWorkOrderName(modelOrder.getWorkOrderName());
                    //责任单位
                    if("0".equals(modelPlan.getLiableUnit() )){
                        modelPlan.setLiableUnit(null);
                    }
                    //责任团队
                    if("0".equals(modelPlan.getLiableTeam() )){
                        modelPlan.setLiableTeam(null);
                    }
                    //转包前责任人
                    if("0".equals(modelPlan.getBeforeLiableUser() )){
                        modelPlan.setBeforeLiableUser(null);
                    }
                    if(modelPlan.getStatus()!=null) { //状态
                        //格式转换
                        switch (modelPlan.getStatus()) {
                            case "已完成":
                                modelPlan.setStatus("0");
                                break;
                            case "执行中":
                                modelPlan.setStatus("1");
                                break;
                            case "已响应":
                                modelPlan.setStatus("2");
                                break;
                            case "已发布":
                                modelPlan.setStatus("3");
                                break;
                        }
                    }
                    if(modelPlan.getSerialNumber()!=null){
                        //根据序列号保留层级
                        String father = findFather(modelPlan.getSerialNumber(),modelPlan);
                        if(StringUtils.isNotBlank(father)){
                            modelPlan.setParentId(father);
                        }
                    }
                    if(StringUtils.isNotBlank(modelPlan.getLiableUserNo())){
                        User user = new User();
                        user.setNo(modelPlan.getLiableUserNo());
                        User byEmpNo = userDao.getByEmpNo(user);
                        if(byEmpNo != null){
                            modelPlan.setLiableUser(byEmpNo.getId());
                        }
                        modelPlanService.save(modelPlan);
                    }else{
                        if(StringUtils.isNotBlank(modelPlan.getLiableUser())){
                            List<User> byName2 = userDao.getByName(modelPlan.getLiableUser());
                            List<User> users = new ArrayList<User>();
                            List<String> strings = new ArrayList<>();
                            int m = 0;
                            for(User user : byName2){
                                if(user.getOffice() != null && StringUtils.isNotBlank(user.getOffice().getName())){
                                    strings.add(user.getOffice().getName());
                                    if(officeName.contains(user.getOffice().getName())){
                                        users.add(user);
                                        m++;
                                    }
                                }
                            }
                            if(m==1){
                                modelPlan.setLiableUser(byName2.get(0).getId());
                                modelPlanService.save(modelPlan);
                            }else{
                                backRe.add(modelPlan);
                            }
                        }

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return backRe;

    }




    @Transactional
/*    public List<ModelPlan> export(ModelOrder modelOrder){
        List<Map<Object,Object>> objs = new ArrayList<Map<Object,Object>>();
        List<ModelPlan> modelPlanList = new ArrayList<ModelPlan>();
        List<ModelPlan> workOrderList = new ArrayList<ModelPlan>();
        try {
            //创建人
            modelOrder.setEstablishName(UserUtils.getUser().getName());
            //维护人
            modelOrder.setAccendant(UserUtils.getUser().getName());
            //导入的excel的ID
            String workID = modelOrder.getWorkId();
            //先存储任务单基本信息
            super.save(modelOrder);
            //导入的文件ID
            if(workID!=null && !"".equals(workID)){
                //FTP 服务器读取文件
                InputStream is = fileStore.downloadFileToStream(BASE_DIR + attachmentInfoService.get(workID).getPath());
                workOrderList = tool( workOrderList,modelOrder);
                //开始遍历excel
                int i = 0;
                List<ModelPlan> modelPlans = new ArrayList<ModelPlan>();
                a:for(ModelPlan modelPlan : workOrderList){
                    if(StringUtils.isNotBlank(modelPlan.getUserOutorg())){
                        String[] split = modelPlan.getUserOutorg().split("#");
                        if(split[0].indexOf("试验验证中心")<0){
                            continue  a;
                        }
                    }
                    Map<Object,Object> map = new HashMap<Object,Object>();
                    map.put("name",modelPlan.getLiableUser());
                    map.put("row",++i);
                    modelPlans.add(modelPlan);
                    objs.add(map);
                }
                String str = "";
                for(Map<Object, Object> map: objs){
                    Object row = map.get("row");
                    Object name = map.get("name");
                    if(objs.size() - 1 == objs.indexOf(map)){
                        str +=  "select distinct "+row+" sheetrow,name , (select count(1) from sys_user where name = '"+name+"') cnt from SYS_USER where name = '"+name+"'";
                    }else{
                        str +=  "select distinct "+row+" sheetrow,name , (select count(1) from sys_user where name = '"+name+"') cnt from SYS_USER where name = '"+name+"'  union all ";
                    }
                }
                List<Map<Object, Object>> result = dao.exec(str);
                modelPlanList = disposeData(result,modelOrder,modelPlans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelPlanList;
    }*/

    public List<ModelPlan> disposeData(List<Map<Object, Object>> result, ModelOrder modelOrder, List<ModelPlan> modelPlans){
        //创建人
        modelOrder.setEstablishName(UserUtils.getUser().getName());
        //维护人
        modelOrder.setAccendant(UserUtils.getUser().getName());
        //导入的excel的ID
        String workID = modelOrder.getWorkId();
        String workOrderName = modelOrder.getWorkOrderName();
        //先存储任务单基本信息
       // super.save(modelOrder);
        //存储介质
        List<ModelPlan> back = new ArrayList<ModelPlan>();
        List<ModelPlan> save = new ArrayList<ModelPlan>();
        List<ModelPlan> backRe = new ArrayList<ModelPlan>();    //重名
        List<String> officeName = officeDao.getOfficeName();
        try {
            int i =0;
            for(Map<Object, Object> map : result){
                String cnt = map.get("CNT").toString();
                if("2".equals(cnt)){
                    modelPlans.get(i).setWorkOrderName(workOrderName);
                    back.add(modelPlans.get(i));
                }else{
                    save.add(modelPlans.get(i));
                }
                i++;
            }
            for(ModelPlan modelPlan : save){
                //关联id
                modelPlan.setWorkId(modelOrder.getId());
                modelPlan.setWorkOrderName(workOrderName);
                String userName = modelPlan.getLiableUser();
                //查找责任人对应的责任主体
                if(StringUtils.isNotBlank(userName)){
                    List<User> byName2 = userDao.getByName(userName);
                    //责任人
                    if(!Collections3.isEmpty(byName2)){
                        modelPlan.setLiableUser(byName2.get(0).getId());
                    }else {
                        User user = new User();
                        user.setName(userName);
                        UUID uuid = UUID.randomUUID();
                        user.setId(uuid.toString());
                        userDao.insert(user);
                        //责任人名称转ID
                        List<User> byName1 = userDao.getByName(userName);
                        if (!Collections3.isEmpty(byName1)) {
                            modelPlan.setLiableUser(byName1.get(0).getId());
                        }
                    }
                }
                //责任单位
                if("0".equals(modelPlan.getLiableUnit() )){
                    modelPlan.setLiableUnit(null);
                }
                //责任团队
                if("0".equals(modelPlan.getLiableTeam() )){
                    modelPlan.setLiableTeam(null);
                }
                //转包前责任人
                if("0".equals(modelPlan.getBeforeLiableUser() )){
                    modelPlan.setBeforeLiableUser(null);
                }
                if(modelPlan.getStatus()!=null) { //状态
                    //格式转换
                    switch (modelPlan.getStatus()) {
                        case "已完成":
                            modelPlan.setStatus("0");
                            break;
                        case "执行中":
                            modelPlan.setStatus("1");
                            break;
                        case "已响应":
                            modelPlan.setStatus("2");
                            break;
                        case "已发布":
                            modelPlan.setStatus("3");
                            break;
                    }
                }if(modelPlan.getSerialNumber()!=null){
                    //根据序列号保留层级
                    String father = findFather(modelPlan.getSerialNumber(),modelPlan);
                    if(StringUtils.isNotBlank(father)){
                        modelPlan.setParentId(father);
                    }
                }
                String userOutorg = modelPlan.getUserOutorg();
                String[] split = userOutorg.split("#");
                String s = split[split.length - 1];
                List<Office> listByNameIfExistOne = officeDao.findListByNameIfExistOne(s);
                if(CollectionUtils.isNotEmpty(listByNameIfExistOne)){
                    String id = listByNameIfExistOne.get(0).getId();
                    modelPlan.setUserOutorg(id);
                }else{
                   /* if(split.length >= 2){
                        String s1 = split[split.length - 2];
                        List<Office> listByNameIfExistOne1 = officeDao.findListByNameIfExistOne(s1);
                    }*/
                    modelPlan.setUserOutorg("");
                }
                modelPlanService.save(modelPlan);
            }

            a:for(ModelPlan modelPlan : back){
                List<User> users = new ArrayList<User>();
                //关联id
                modelPlan.setWorkId(modelOrder.getId());
                modelPlan.setWorkOrderName(workOrderName);
                String userName = modelPlan.getLiableUser();
                //查找责任人对应的责任主体
                if(StringUtils.isNotBlank(userName)){
                    List<User> byName2 = userDao.getByName(userName);
                    //责任人
                    List<String> strings = new ArrayList<>();
                    if(!Collections3.isEmpty(byName2)){
                        int m = 0;
                        for(User user : byName2){
                            if(user.getOffice() != null && StringUtils.isNotBlank(user.getOffice().getName())){
                                strings.add(user.getOffice().getName());
                                if(officeName.contains(user.getOffice().getName())){
                                    users.add(user);
                                    m++;
                                }
                            }
                        }
                        if(m==1){
                            modelPlan.setLiableUser(byName2.get(0).getId());
                        }else{
                            backRe.add(modelPlan);
                            continue a;
                        }
                    }
                }
                //责任单位
                if("0".equals(modelPlan.getLiableUnit() )){
                    modelPlan.setLiableUnit(null);
                }
                //责任团队
                if("0".equals(modelPlan.getLiableTeam() )){
                    modelPlan.setLiableTeam(null);
                }
                //转包前责任人
                if("0".equals(modelPlan.getBeforeLiableUser() )){
                    modelPlan.setBeforeLiableUser(null);
                }
                if(modelPlan.getStatus()!=null) { //状态
                    //格式转换
                    switch (modelPlan.getStatus()) {
                        case "已完成":
                            modelPlan.setStatus("0");
                            break;
                        case "执行中":
                            modelPlan.setStatus("1");
                            break;
                        case "已响应":
                            modelPlan.setStatus("2");
                            break;
                        case "已发布":
                            modelPlan.setStatus("3");
                            break;
                    }
                }if(modelPlan.getSerialNumber()!=null){
                    //根据序列号保留层级
                    String father = findFather(modelPlan.getSerialNumber(),modelPlan);
                    if(StringUtils.isNotBlank(father)){
                        modelPlan.setParentId(father);
                    }
                }
                modelPlanService.save(modelPlan);
            }
        } catch (Exception e) {
            logger.error("保存任务单信息失败", e);
        }
        return back;
    }
    //寻找父级节点
    public String findFather(String serialNumber, ModelPlan plan){

        String[] split = serialNumber.split("\\.");
        String one = new String();
        int length = split.length;
        switch (length){
            case 2:
                one = split[0];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("2");
                break;
            case 3:
                one = split[0]+"."+split[1];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("3");
                break;
            case 4:
                one = split[0]+"."+split[1]+"."+split[2];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("4");break;
            case 5:
                one = split[0]+"."+split[1]+"."+split[2]+"."+split[3];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("5");break;
            case 6:
                one = split[0]+"."+split[1]+"."+split[2]+"."+split[3]+"."+split[4];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("6");break;
            case 7:
                one = split[0]+"."+split[1]+"."+split[2]+"."+split[3]+"."+split[4]+"."+split[5];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("7");break;
            case 8:
                one = split[0]+"."+split[1]+"."+split[2]+"."+split[3]+"."+split[4]+"."+split[5]+"."+split[6];
                one = findFather1(one,plan.getWorkId());
                plan.setDeep("8");break;
            default:
                one = null;
                plan.setDeep("1");
                break;
        }
        return one;
    }
    public String findFather1(String num,String workId){
        //在同一个任务单
        List<ModelPlan> father = modelPlanDao.findFather(num,workId);
        String a = new String();
        for (ModelPlan plan:father){
            a = plan.getId();
        }
        return a;
    }
    public static <T> List<T> parseFromExcel(String path, Class<T> aimClass, InputStream fis) {
        return parseFromExcel(0, aimClass, fis);
    }

    /**
     * 文件导入工具方法
     * @param firstIndex
     * @param aimClass
     * @param fis
     * @param <T>
     * @return
     */
    @SuppressWarnings("deprecation")
    public static <T> List<T> parseFromExcel(int firstIndex, Class<T> aimClass,InputStream fis) {
        List<T> result = new ArrayList<T>();
        try {
            Workbook workbook = WorkbookFactory.create(fis);
            //对excel文档的第一页,即sheet1进行操作
            Sheet sheet = workbook.getSheetAt(0);
            int lastRaw = sheet.getLastRowNum();
            for (int i = firstIndex; i < lastRaw; i++) {
                //第i行
                Row row = sheet.getRow(i+1);
                T parseObject = aimClass.newInstance();
                Field[] fields = aimClass.getDeclaredFields();
                for (int j = 1; j < fields.length-7; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    //第j列
                    Cell cell = row.getCell(j-1);
                    if (cell == null)
                        continue;
                    //很重要的一行代码,如果不加,像12345这样的数字是不会给你转成String的,只会给你转成double,而且会导致cell.getStringCellValue()报错
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String cellContent = cell.getStringCellValue();
                    cellContent = "".equals(cellContent) ? null : cellContent;
                    if (type.equals(String.class)) {
                        field.set(parseObject, cellContent);
                    } else if (type.equals(char.class) || type.equals(Character.class)) {
                        field.set(parseObject, cellContent.charAt(0));
                    } else if (type.equals(int.class) || type.equals(Integer.class)) {
                        field.set(parseObject, Integer.parseInt(cellContent));
                    } else if (type.equals(long.class) || type.equals(Long.class)) {
                        field.set(parseObject, Long.parseLong(cellContent));
                    } else if (type.equals(float.class) || type.equals(Float.class)) {
                        field.set(parseObject, Float.parseFloat(cellContent));
                    } else if (type.equals(double.class) || type.equals(Double.class)) {
                        field.set(parseObject, Double.parseDouble(cellContent));
                    } else if (type.equals(short.class) || type.equals(Short.class)) {
                        field.set(parseObject, Short.parseShort(cellContent));
                    } else if (type.equals(byte.class) || type.equals(Byte.class)) {
                        field.set(parseObject, Byte.parseByte(cellContent));
                    } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        field.set(parseObject, Boolean.parseBoolean(cellContent));
                    }else if (type.equals(Date.class) || type==Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            field.set(parseObject, sdf.parse(cellContent));
                        }catch (Exception e){
                            field.set(parseObject, null);
                        }

                    }
                }
                result.add(parseObject);
            }
            fis.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //System.err.println("An error occured when parsing object from Excel. at " + this.getClass());
        }
        return result;
    }



    public List<ModelPlan> tool(List<ModelPlan> list,Sheet sheet){
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String[] strs = new String[]{"层级序号","类型","WBS编号","WBS名称","WBS账户控制人","状态","来源","名称","关注人","责任团队","责任单位","责任人","责任人工号","责任人派出单位",
                    "责任人派出部门","开始时间","应完时间","评价人","评价人工号","转包前责任人","转包前责任人工号","所属阶段","工时价值系数","实际完成时间","确认完成时间","计划工时","实动工时","完成形式",
                    "是否编制卡单","卡单编制人","卡单状态","卡单发布时间","TTGF","上级关注","项目0级管控计划","项目1级管控计划","单位部门管控计划","关键路径","主要节点","重点任务","年度目标精品工程",
                    "三年行动计划","高高原","重复性质量问题","好维修","备注1","备注2","备注3","备注4","备注5","进展说明"};
            int lastRaw = sheet.getLastRowNum()+1;

            //行
            Row row = sheet.getRow(1);
            //列 包含多少列
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            Map<String,Object> map = new HashMap();
            for(int i = 0;i<physicalNumberOfCells-1;i++){
                String cellContent = row.getCell(i).getStringCellValue();
                boolean contains = Arrays.asList(strs).contains(cellContent);
                if(contains){
                    map.put(cellContent,i);
                }
            }
            for(int i = 2;i<lastRaw-1;i++){
                row = sheet.getRow(i);
                String serialNumber ="";
                if(map.get("层级序号") != null ){
                   Cell cell = row.getCell(Integer.parseInt(map.get("层级序号").toString()))==null? row.createCell(Integer.parseInt(map.get("层级序号").toString())):row.getCell(Integer.parseInt(map.get("层级序号").toString()));
                    cell.setCellType(CellType.STRING);//定义字段类型为string
                    serialNumber = cell.getStringCellValue();
                }
                String type ="";
                if(map.get("类型") != null ){
                    Cell  cell = row.getCell(Integer.parseInt(map.get("类型").toString()))==null? row.createCell(Integer.parseInt(map.get("类型").toString())) :row.getCell(Integer.parseInt(map.get("类型").toString()));
                    cell.setCellType(CellType.STRING);
                    type = cell.getStringCellValue();
                }
                String wbs = "";
                if(map.get("WBS编号") != null ) {
                    Cell  cell  = row.getCell(Integer.parseInt(map.get("WBS编号").toString()))==null?row.createCell(Integer.parseInt(map.get("WBS编号").toString())):row.getCell(Integer.parseInt(map.get("WBS编号").toString()));
                    cell.setCellType(CellType.STRING);
                    wbs = cell.getStringCellValue();
                }
                String wbsName = "";
                if(map.get("WBS名称") != null ) {
                    Cell cell = row.createCell(Integer.parseInt(map.get("WBS名称").toString()));
                    cell.setCellType(CellType.STRING);
                    wbsName = row.getCell(Integer.parseInt(map.get("WBS名称").toString()))==null? String.valueOf(cell) :row.getCell(Integer.parseInt(map.get("WBS名称").toString())).getStringCellValue();

                }
                String wbsACC = "";
                if(map.get("WBS账户控制人") != null ) {
                    Cell  cell  = row.getCell(Integer.parseInt(map.get("WBS账户控制人").toString()))==null?row.createCell(Integer.parseInt(map.get("WBS账户控制人").toString())):row.getCell(Integer.parseInt(map.get("WBS账户控制人").toString()));
                    cell.setCellType(CellType.STRING);
                    wbsACC = cell.getStringCellValue();
                }
                String status = "";
                if(map.get("状态") != null ) {
                    Cell  cell  = row.getCell(Integer.parseInt(map.get("状态").toString()))==null?row.createCell(Integer.parseInt(map.get("状态").toString())):row.getCell(Integer.parseInt(map.get("状态").toString()));
                    cell.setCellType(CellType.STRING);
                    status = cell.getStringCellValue();
                }
                String source = "";
                if(map.get("来源") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("来源").toString()))==null?row.createCell(Integer.parseInt(map.get("来源").toString())):row.getCell(Integer.parseInt(map.get("来源").toString()));
                    cell.setCellType(CellType.STRING);
                    source = cell.getStringCellValue();
                }
                String name = "";
                if(map.get("名称") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("名称").toString()))==null?row.createCell(Integer.parseInt(map.get("名称").toString())):row.getCell(Integer.parseInt(map.get("名称").toString()));
                    cell.setCellType(CellType.STRING);
                    name = cell.getStringCellValue();
                }
                String follower = "";
                if(map.get("关注人") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("关注人").toString()))==null?row.createCell(Integer.parseInt(map.get("关注人").toString())):row.getCell(Integer.parseInt(map.get("关注人").toString()));
                    cell.setCellType(CellType.STRING);
                    follower = cell.getStringCellValue();
                }
                String liableTeam = "";
                if(map.get("责任团队") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("责任团队").toString()))==null?row.createCell(Integer.parseInt(map.get("责任团队").toString())):row.getCell(Integer.parseInt(map.get("责任团队").toString()));
                    cell.setCellType(CellType.STRING);
                    liableTeam = cell.getStringCellValue();
                }
                String liableUnit = "";
                if(map.get("责任单位") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("责任单位").toString()))==null?row.createCell(Integer.parseInt(map.get("责任单位").toString())):row.getCell(Integer.parseInt(map.get("责任单位").toString()));
                    cell.setCellType(CellType.STRING);
                    liableUnit = cell.getStringCellValue();
                }
                String liableUser = "";
                if(map.get("责任人") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("责任人").toString()))==null?row.createCell(Integer.parseInt(map.get("责任人").toString())):row.getCell(Integer.parseInt(map.get("责任人").toString()));
                    cell.setCellType(CellType.STRING);
                    liableUser = cell.getStringCellValue();
                }
                String liableUserNo = "";
                if(map.get("WBS编号") != null ) {
                 Cell  cell = row.getCell(Integer.parseInt(map.get("责任人工号").toString()))==null?
                          row.createCell(Integer.parseInt(map.get("责任人工号").toString())):row.getCell(Integer.parseInt(map.get("责任人工号").toString()));
                    cell.setCellType(CellType.STRING);
                    liableUserNo = cell.getStringCellValue();
                }
                String userOutunit = "";
                if(map.get("责任人派出单位") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("责任人派出单位").toString()))==null?row.createCell(Integer.parseInt(map.get("责任人派出单位").toString())):row.getCell(Integer.parseInt(map.get("责任人派出单位").toString()));
                    cell.setCellType(CellType.STRING);
                    userOutunit = cell.getStringCellValue();
                }
                String userOutorg = "";
                if(map.get("责任人派出部门") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("责任人派出部门").toString()))==null?row.createCell(Integer.parseInt(map.get("责任人派出部门").toString())):row.getCell(Integer.parseInt(map.get("责任人派出部门").toString()));
                    cell.setCellType(CellType.STRING);
                    userOutorg = cell.getStringCellValue();
                }
                String startTime = null;
                if(map.get("开始时间") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("开始时间").toString()))==null?row.createCell(Integer.parseInt(map.get("开始时间").toString())):row.getCell(Integer.parseInt(map.get("开始时间").toString()));
                    cell.setCellType(CellType.STRING);
                    startTime = cell.getStringCellValue();
                }
                String completeTime = "";
                if(map.get("应完时间") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("应完时间").toString()))==null?row.createCell(Integer.parseInt(map.get("应完时间").toString())):row.getCell(Integer.parseInt(map.get("应完时间").toString()));
                    cell.setCellType(CellType.STRING);
                    completeTime = cell.getStringCellValue();
                }
                String evaluateUser = "";
                if(map.get("评价人") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("评价人").toString()))==null?row.createCell(Integer.parseInt(map.get("评价人").toString())):row.getCell(Integer.parseInt(map.get("评价人").toString()));
                    cell.setCellType(CellType.STRING);
                    evaluateUser = cell.getStringCellValue();
                }
                String evaluateUserNo = "";
                if(map.get("评价人工号") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("评价人工号").toString()))==null?row.createCell(Integer.parseInt(map.get("评价人工号").toString())):row.getCell(Integer.parseInt(map.get("评价人工号").toString()));
                    cell.setCellType(CellType.STRING);
                    evaluateUserNo = cell.getStringCellValue();
                }
                String beforeLiableUser = "";
                if(map.get("转包前责任人") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("转包前责任人").toString()))==null?row.createCell(Integer.parseInt(map.get("转包前责任人").toString())):row.getCell(Integer.parseInt(map.get("转包前责任人").toString()));
                    cell.setCellType(CellType.STRING);
                    beforeLiableUser = cell.getStringCellValue();
                }
                String beforeLiableUserNo = "";
                if(map.get("转包前责任人工号") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("转包前责任人工号").toString()))==null?row.createCell(Integer.parseInt(map.get("转包前责任人工号").toString())):row.getCell(Integer.parseInt(map.get("转包前责任人工号").toString()));
                    cell.setCellType(CellType.STRING);
                    beforeLiableUserNo = cell.getStringCellValue();
                }
                String subStage = "";
                if(map.get("所属阶段") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("所属阶段").toString()))==null?row.createCell(Integer.parseInt(map.get("所属阶段").toString())):row.getCell(Integer.parseInt(map.get("所属阶段").toString()));
                    cell.setCellType(CellType.STRING);
                    subStage = cell.getStringCellValue();
                }
                String coeHour = "";
                if(map.get("工时价值系数") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("工时价值系数").toString()))==null?row.createCell(Integer.parseInt(map.get("工时价值系数").toString())):row.getCell(Integer.parseInt(map.get("工时价值系数").toString()));
                    cell.setCellType(CellType.STRING);
                    coeHour = cell.getStringCellValue();
                }
                String actualCompleteTime = "";
                if(map.get("实际完成时间") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("实际完成时间").toString()))==null?row.createCell(Integer.parseInt(map.get("实际完成时间").toString())):row.getCell(Integer.parseInt(map.get("实际完成时间").toString()));
                    cell.setCellType(CellType.STRING);
                    actualCompleteTime = cell.getStringCellValue();
                }
                String assessmentTime = "";
                if(map.get("确认完成时间") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("确认完成时间").toString()))==null?row.createCell(Integer.parseInt(map.get("确认完成时间").toString())):row.getCell(Integer.parseInt(map.get("确认完成时间").toString()));
                    cell.setCellType(CellType.STRING);
                    assessmentTime = cell.getStringCellValue();
                }
                String planWorkingHour = "";
                if(map.get("计划工时") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("计划工时").toString()))==null?row.createCell(Integer.parseInt(map.get("计划工时").toString())):row.getCell(Integer.parseInt(map.get("计划工时").toString()));
                    cell.setCellType(CellType.STRING);
                    planWorkingHour = cell.getStringCellValue();
                }
                String labourHour = "";
                if(map.get("WBS编号") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("实动工时").toString()))==null?row.createCell(Integer.parseInt(map.get("实动工时").toString())):row.getCell(Integer.parseInt(map.get("实动工时").toString()));
                    cell.setCellType(CellType.STRING);
                    labourHour = cell.getStringCellValue();
                }
                String isPrepareCard = "";
                if(map.get("是否编制卡单") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("是否编制卡单").toString()))==null?row.createCell(Integer.parseInt(map.get("是否编制卡单").toString())):row.getCell(Integer.parseInt(map.get("是否编制卡单").toString()));
                    cell.setCellType(CellType.STRING);
                    isPrepareCard = cell.getStringCellValue();
                }
                String prepareCardUser = "";
                if(map.get("卡单编制人") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("卡单编制人").toString()))==null?row.createCell(Integer.parseInt(map.get("卡单编制人").toString())):row.getCell(Integer.parseInt(map.get("卡单编制人").toString()));
                    cell.setCellType(CellType.STRING);
                    prepareCardUser = cell.getStringCellValue();
                }
                String prepareCardState = "";
                if(map.get("卡单状态") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("卡单状态").toString()))==null?row.createCell(Integer.parseInt(map.get("卡单状态").toString())):row.getCell(Integer.parseInt(map.get("卡单状态").toString()));
                    cell.setCellType(CellType.STRING);
                    prepareCardState = cell.getStringCellValue();
                }
                String prepareCardTime = "";
                if(map.get("卡单发布时间") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("卡单发布时间").toString()))==null?row.createCell(Integer.parseInt(map.get("卡单发布时间").toString())):row.getCell(Integer.parseInt(map.get("卡单发布时间").toString()));
                    cell.setCellType(CellType.STRING);
                    prepareCardTime = cell.getStringCellValue();
                }
                String ttgf = "";
                if(map.get("TTGF") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("TTGF").toString()))==null?row.createCell(Integer.parseInt(map.get("TTGF").toString())):row.getCell(Integer.parseInt(map.get("TTGF").toString()));
                    cell.setCellType(CellType.STRING);
                    ttgf = cell.getStringCellValue();
                }
                String superiorAttention = "";
                if(map.get("上级关注") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("上级关注").toString()))==null?row.createCell(Integer.parseInt(map.get("上级关注").toString())):row.getCell(Integer.parseInt(map.get("上级关注").toString()));
                    cell.setCellType(CellType.STRING);
                    superiorAttention = cell.getStringCellValue();
                }
                String projectControlZero = "";
                if(map.get("项目0级管控计划") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("项目0级管控计划").toString()))==null?(row.createCell(Integer.parseInt(map.get("项目0级管控计划").toString()))):row.getCell(Integer.parseInt(map.get("项目0级管控计划").toString()));
                    cell.setCellType(CellType.STRING);
                    projectControlZero = cell.getStringCellValue();
                }
                String projectControlOne = "";
                if(map.get("项目1级管控计划") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("项目1级管控计划").toString()))==null?(row.createCell(Integer.parseInt(map.get("项目1级管控计划").toString()))):row.getCell(Integer.parseInt(map.get("项目1级管控计划").toString()));
                    cell.setCellType(CellType.STRING);
                    projectControlOne = cell.getStringCellValue();
                }
                String unitControlPlan = "";
                if(map.get("单位部门管控计划") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("单位部门管控计划").toString()))==null?(row.createCell(Integer.parseInt(map.get("单位部门管控计划").toString()))):row.getCell(Integer.parseInt(map.get("单位部门管控计划").toString()));
                    cell.setCellType(CellType.STRING);
                    unitControlPlan = cell.getStringCellValue();
                }
                String criticalPath = "";
                if(map.get("关键路径") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("关键路径").toString()))==null?(row.createCell(Integer.parseInt(map.get("关键路径").toString()))):row.getCell(Integer.parseInt(map.get("关键路径").toString()));
                    cell.setCellType(CellType.STRING);
                    criticalPath = cell.getStringCellValue();
                }
                String masterNode = "";
                if(map.get("主要节点") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("主要节点").toString()))==null?(row.createCell(Integer.parseInt(map.get("主要节点").toString()))):row.getCell(Integer.parseInt(map.get("主要节点").toString()));
                    cell.setCellType(CellType.STRING);
                    masterNode = cell.getStringCellValue();
                }
                String importmentTask = "";
                if(map.get("重点任务") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("重点任务").toString()))==null?(row.createCell(Integer.parseInt(map.get("重点任务").toString()))):row.getCell(Integer.parseInt(map.get("重点任务").toString()));
                    cell.setCellType(CellType.STRING);
                    importmentTask = cell.getStringCellValue();
                }
                String annualQualityPro = "";
                if(map.get("年度目标精品工程") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("年度目标精品工程").toString()))==null?(row.createCell(Integer.parseInt(map.get("年度目标精品工程").toString()))):row.getCell(Integer.parseInt(map.get("年度目标精品工程").toString()));
                    cell.setCellType(CellType.STRING);
                    annualQualityPro = cell.getStringCellValue();
                }
                String TYearActionPlan = "";
                if(map.get("三年行动计划") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("三年行动计划").toString()))==null?(row.createCell(Integer.parseInt(map.get("三年行动计划").toString()))):row.getCell(Integer.parseInt(map.get("三年行动计划").toString()));
                    cell.setCellType(CellType.STRING);
                    TYearActionPlan = cell.getStringCellValue();
                }
                String GGY = "";
                if(map.get("高高原") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("高高原").toString()))==null?(row.createCell(Integer.parseInt(map.get("高高原").toString()))):row.getCell(Integer.parseInt(map.get("高高原").toString()));
                    cell.setCellType(CellType.STRING);
                    GGY = cell.getStringCellValue();
                }
                String reQualityProblem = "";
                if(map.get("重复性质量问题") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("重复性质量问题").toString()))==null?(row.createCell(Integer.parseInt(map.get("重复性质量问题").toString()))):row.getCell(Integer.parseInt(map.get("重复性质量问题").toString()));
                    cell.setCellType(CellType.STRING);
                    reQualityProblem = cell.getStringCellValue();
                }
                String goodMaintenance = "";
                if(map.get("好维修") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("好维修").toString()))==null?(row.createCell(Integer.parseInt(map.get("好维修").toString()))):row.getCell(Integer.parseInt(map.get("好维修").toString()));
                    cell.setCellType(CellType.STRING);
                    goodMaintenance = cell.getStringCellValue();
                }
                String remarksOne = "";
                if(map.get("备注1") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("备注1").toString()))==null?(row.createCell(Integer.parseInt(map.get("备注1").toString()))):row.getCell(Integer.parseInt(map.get("备注1").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksOne = cell.getStringCellValue();
                }
                String remarksTwo = "";
                if(map.get("备注2") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("备注2").toString()))==null?(row.createCell(Integer.parseInt(map.get("备注2").toString()))):row.getCell(Integer.parseInt(map.get("备注2").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksTwo = cell.getStringCellValue();
                }
                String remarksThir = "";
                if(map.get("备注3") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("备注3").toString()))==null?(row.createCell(Integer.parseInt(map.get("备注3").toString()))):row.getCell(Integer.parseInt(map.get("备注3").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksThir = cell.getStringCellValue();
                }
                String remarksFour = "";
                if(map.get("备注4") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("备注4").toString()))==null?(row.createCell(Integer.parseInt(map.get("备注4").toString()))):row.getCell(Integer.parseInt(map.get("备注4").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksFour = cell.getStringCellValue();
                }
                String remarksFif = "";
                if(map.get("备注5") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("备注5").toString()))==null?(row.createCell(Integer.parseInt(map.get("备注5").toString()))):row.getCell(Integer.parseInt(map.get("备注5").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksFif = cell.getStringCellValue();
                }
                String evolveState = "";
                if(map.get("进展说明") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("进展说明").toString()))==null?row.createCell(Integer.parseInt(map.get("进展说明").toString())):row.getCell(Integer.parseInt(map.get("进展说明").toString()));
                    cell.setCellType(CellType.STRING);
                    evolveState = cell.getStringCellValue();
                }

                ModelPlan modelPlan = new ModelPlan(serialNumber,type,wbs,wbsName,wbsACC,status,source,name,follower,liableTeam,liableUnit,liableUser,liableUserNo,userOutunit,userOutorg,startTime,
                        completeTime,evaluateUser,evaluateUserNo,beforeLiableUser,beforeLiableUserNo,subStage,coeHour,actualCompleteTime,assessmentTime,planWorkingHour,labourHour,isPrepareCard,
                        prepareCardUser,prepareCardState,prepareCardTime,ttgf,superiorAttention,projectControlZero,projectControlOne,unitControlPlan,criticalPath,masterNode,
                        importmentTask,annualQualityPro,TYearActionPlan,GGY,reQualityProblem,goodMaintenance,remarksOne,remarksTwo,remarksThir,remarksFour,remarksFif,evolveState);
                list.add(modelPlan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
