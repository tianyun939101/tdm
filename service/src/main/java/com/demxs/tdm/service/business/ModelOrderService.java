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
            //???????????????????????????????????????????????????
            ModelOrder modelPlan1 = modelOrderDao.get(id);
            if(modelPlan1!=null){
                //?????????
                String workId = modelPlan1.getId();
                //?????????????????????????????????
                List<ModelPlan> status = modelPlanDao.findStatus(workId);
                for(ModelPlan modelPlan : status){
                   /* User user = new User();
                    userDao.getByEmpNo()*/
                    User liableUser =userDao.get(modelPlan.getLiableUser());    //?????????
                    if(liableUser!=null){
                        String email = liableUser.getEmail();
                        String userId = modelPlan.getLiableUser();
                        //??????????????????????????????
                        List<ModelOrder> stateSend = modelOrderDao.findStatusByUserId(userId);
                        for(ModelOrder modelOrder1:stateSend){
                            //?????????
                            String inExecution = modelOrder1.getInExecution();
                            //?????????
                            String response = modelOrder1.getResponse();
                            //
                            Date requireCompleteTime = modelPlan.getCompleteTime();
                            String format = s2.format(requireCompleteTime);
                             int a = Integer.valueOf(inExecution)+Integer.valueOf(response);
                            emailUtil.sendMail(email,"?????????????????????????????????","????????????????????????"+a+"?????????????????????????????????????????????"+inExecution+"???,?????????"+response+"???????????????????????????????????????"+format+",????????????????????????");
                        }
                    }
                }
            }
        }
        try {
            Thread.sleep(250);
        }catch (InterruptedException  e){
            logger.debug("??????????????????????????????????????????");
            e.printStackTrace();
            return new AsyncResult<String>("??????????????????????????????????????????");
        }
        return new AsyncResult<String>("?????????????????????");
    }

    @Transactional
    public List<ModelPlan> exportNew(ModelOrder modelOrder){


        List<Map<Object,Object>> objs = new ArrayList<Map<Object,Object>>();
        List<ModelPlan> modelPlanList = new ArrayList<ModelPlan>();
        List<ModelPlan> workOrderList = new ArrayList<ModelPlan>();
        List<ModelPlan> backRe = new ArrayList<ModelPlan>();    //??????
        try {
            //?????????
            modelOrder.setEstablishName(UserUtils.getUser().getName());
            //?????????
            modelOrder.setAccendant(UserUtils.getUser().getName());
            //?????????excel???ID
            String workID = modelOrder.getWorkId();
            //??????????????????????????????
            super.save(modelOrder);
            /*List<ModelPlan> workIds = modelPlanDao.findWorkId(workID);
            if(CollectionUtils.isNotEmpty(workIds)){
                    modelPlanDao.delete(workID);
            }*/

            //???????????????ID
            if (workID != null && !"".equals(workID)) {
                List<String> officeName = officeDao.getOfficeName();
                //FTP ?????????????????????
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
                        if(split[0].indexOf("??????????????????")<0){
                            continue  a;
                        }
                    }
                    //??????????????????
                    modelPlanDao.deleteRe(modelPlan.getSerialNumber(),modelPlan.getName());
                    modelPlanDao.deleteRe1(modelPlan.getSerialNumber(),modelPlan.getName());
                    modelPlan.setWorkId(modelOrder.getId());
                    modelPlan.setWorkOrderName(modelOrder.getWorkOrderName());
                    //????????????
                    if("0".equals(modelPlan.getLiableUnit() )){
                        modelPlan.setLiableUnit(null);
                    }
                    //????????????
                    if("0".equals(modelPlan.getLiableTeam() )){
                        modelPlan.setLiableTeam(null);
                    }
                    //??????????????????
                    if("0".equals(modelPlan.getBeforeLiableUser() )){
                        modelPlan.setBeforeLiableUser(null);
                    }
                    if(modelPlan.getStatus()!=null) { //??????
                        //????????????
                        switch (modelPlan.getStatus()) {
                            case "?????????":
                                modelPlan.setStatus("0");
                                break;
                            case "?????????":
                                modelPlan.setStatus("1");
                                break;
                            case "?????????":
                                modelPlan.setStatus("2");
                                break;
                            case "?????????":
                                modelPlan.setStatus("3");
                                break;
                        }
                    }
                    if(modelPlan.getSerialNumber()!=null){
                        //???????????????????????????
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
            //?????????
            modelOrder.setEstablishName(UserUtils.getUser().getName());
            //?????????
            modelOrder.setAccendant(UserUtils.getUser().getName());
            //?????????excel???ID
            String workID = modelOrder.getWorkId();
            //??????????????????????????????
            super.save(modelOrder);
            //???????????????ID
            if(workID!=null && !"".equals(workID)){
                //FTP ?????????????????????
                InputStream is = fileStore.downloadFileToStream(BASE_DIR + attachmentInfoService.get(workID).getPath());
                workOrderList = tool( workOrderList,modelOrder);
                //????????????excel
                int i = 0;
                List<ModelPlan> modelPlans = new ArrayList<ModelPlan>();
                a:for(ModelPlan modelPlan : workOrderList){
                    if(StringUtils.isNotBlank(modelPlan.getUserOutorg())){
                        String[] split = modelPlan.getUserOutorg().split("#");
                        if(split[0].indexOf("??????????????????")<0){
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
        //?????????
        modelOrder.setEstablishName(UserUtils.getUser().getName());
        //?????????
        modelOrder.setAccendant(UserUtils.getUser().getName());
        //?????????excel???ID
        String workID = modelOrder.getWorkId();
        String workOrderName = modelOrder.getWorkOrderName();
        //??????????????????????????????
       // super.save(modelOrder);
        //????????????
        List<ModelPlan> back = new ArrayList<ModelPlan>();
        List<ModelPlan> save = new ArrayList<ModelPlan>();
        List<ModelPlan> backRe = new ArrayList<ModelPlan>();    //??????
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
                //??????id
                modelPlan.setWorkId(modelOrder.getId());
                modelPlan.setWorkOrderName(workOrderName);
                String userName = modelPlan.getLiableUser();
                //????????????????????????????????????
                if(StringUtils.isNotBlank(userName)){
                    List<User> byName2 = userDao.getByName(userName);
                    //?????????
                    if(!Collections3.isEmpty(byName2)){
                        modelPlan.setLiableUser(byName2.get(0).getId());
                    }else {
                        User user = new User();
                        user.setName(userName);
                        UUID uuid = UUID.randomUUID();
                        user.setId(uuid.toString());
                        userDao.insert(user);
                        //??????????????????ID
                        List<User> byName1 = userDao.getByName(userName);
                        if (!Collections3.isEmpty(byName1)) {
                            modelPlan.setLiableUser(byName1.get(0).getId());
                        }
                    }
                }
                //????????????
                if("0".equals(modelPlan.getLiableUnit() )){
                    modelPlan.setLiableUnit(null);
                }
                //????????????
                if("0".equals(modelPlan.getLiableTeam() )){
                    modelPlan.setLiableTeam(null);
                }
                //??????????????????
                if("0".equals(modelPlan.getBeforeLiableUser() )){
                    modelPlan.setBeforeLiableUser(null);
                }
                if(modelPlan.getStatus()!=null) { //??????
                    //????????????
                    switch (modelPlan.getStatus()) {
                        case "?????????":
                            modelPlan.setStatus("0");
                            break;
                        case "?????????":
                            modelPlan.setStatus("1");
                            break;
                        case "?????????":
                            modelPlan.setStatus("2");
                            break;
                        case "?????????":
                            modelPlan.setStatus("3");
                            break;
                    }
                }if(modelPlan.getSerialNumber()!=null){
                    //???????????????????????????
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
                //??????id
                modelPlan.setWorkId(modelOrder.getId());
                modelPlan.setWorkOrderName(workOrderName);
                String userName = modelPlan.getLiableUser();
                //????????????????????????????????????
                if(StringUtils.isNotBlank(userName)){
                    List<User> byName2 = userDao.getByName(userName);
                    //?????????
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
                //????????????
                if("0".equals(modelPlan.getLiableUnit() )){
                    modelPlan.setLiableUnit(null);
                }
                //????????????
                if("0".equals(modelPlan.getLiableTeam() )){
                    modelPlan.setLiableTeam(null);
                }
                //??????????????????
                if("0".equals(modelPlan.getBeforeLiableUser() )){
                    modelPlan.setBeforeLiableUser(null);
                }
                if(modelPlan.getStatus()!=null) { //??????
                    //????????????
                    switch (modelPlan.getStatus()) {
                        case "?????????":
                            modelPlan.setStatus("0");
                            break;
                        case "?????????":
                            modelPlan.setStatus("1");
                            break;
                        case "?????????":
                            modelPlan.setStatus("2");
                            break;
                        case "?????????":
                            modelPlan.setStatus("3");
                            break;
                    }
                }if(modelPlan.getSerialNumber()!=null){
                    //???????????????????????????
                    String father = findFather(modelPlan.getSerialNumber(),modelPlan);
                    if(StringUtils.isNotBlank(father)){
                        modelPlan.setParentId(father);
                    }
                }
                modelPlanService.save(modelPlan);
            }
        } catch (Exception e) {
            logger.error("???????????????????????????", e);
        }
        return back;
    }
    //??????????????????
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
        //?????????????????????
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
     * ????????????????????????
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
            //???excel??????????????????,???sheet1????????????
            Sheet sheet = workbook.getSheetAt(0);
            int lastRaw = sheet.getLastRowNum();
            for (int i = firstIndex; i < lastRaw; i++) {
                //???i???
                Row row = sheet.getRow(i+1);
                T parseObject = aimClass.newInstance();
                Field[] fields = aimClass.getDeclaredFields();
                for (int j = 1; j < fields.length-7; j++) {
                    Field field = fields[j];
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    //???j???
                    Cell cell = row.getCell(j-1);
                    if (cell == null)
                        continue;
                    //????????????????????????,????????????,???12345????????????????????????????????????String???,??????????????????double,???????????????cell.getStringCellValue()??????
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
            String[] strs = new String[]{"????????????","??????","WBS??????","WBS??????","WBS???????????????","??????","??????","??????","?????????","????????????","????????????","?????????","???????????????","?????????????????????",
                    "?????????????????????","????????????","????????????","?????????","???????????????","??????????????????","????????????????????????","????????????","??????????????????","??????????????????","??????????????????","????????????","????????????","????????????",
                    "??????????????????","???????????????","????????????","??????????????????","TTGF","????????????","??????0???????????????","??????1???????????????","????????????????????????","????????????","????????????","????????????","????????????????????????",
                    "??????????????????","?????????","?????????????????????","?????????","??????1","??????2","??????3","??????4","??????5","????????????"};
            int lastRaw = sheet.getLastRowNum()+1;

            //???
            Row row = sheet.getRow(1);
            //??? ???????????????
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
                if(map.get("????????????") != null ){
                   Cell cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null? row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);//?????????????????????string
                    serialNumber = cell.getStringCellValue();
                }
                String type ="";
                if(map.get("??????") != null ){
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????").toString()))==null? row.createCell(Integer.parseInt(map.get("??????").toString())) :row.getCell(Integer.parseInt(map.get("??????").toString()));
                    cell.setCellType(CellType.STRING);
                    type = cell.getStringCellValue();
                }
                String wbs = "";
                if(map.get("WBS??????") != null ) {
                    Cell  cell  = row.getCell(Integer.parseInt(map.get("WBS??????").toString()))==null?row.createCell(Integer.parseInt(map.get("WBS??????").toString())):row.getCell(Integer.parseInt(map.get("WBS??????").toString()));
                    cell.setCellType(CellType.STRING);
                    wbs = cell.getStringCellValue();
                }
                String wbsName = "";
                if(map.get("WBS??????") != null ) {
                    Cell cell = row.createCell(Integer.parseInt(map.get("WBS??????").toString()));
                    cell.setCellType(CellType.STRING);
                    wbsName = row.getCell(Integer.parseInt(map.get("WBS??????").toString()))==null? String.valueOf(cell) :row.getCell(Integer.parseInt(map.get("WBS??????").toString())).getStringCellValue();

                }
                String wbsACC = "";
                if(map.get("WBS???????????????") != null ) {
                    Cell  cell  = row.getCell(Integer.parseInt(map.get("WBS???????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("WBS???????????????").toString())):row.getCell(Integer.parseInt(map.get("WBS???????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    wbsACC = cell.getStringCellValue();
                }
                String status = "";
                if(map.get("??????") != null ) {
                    Cell  cell  = row.getCell(Integer.parseInt(map.get("??????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????").toString())):row.getCell(Integer.parseInt(map.get("??????").toString()));
                    cell.setCellType(CellType.STRING);
                    status = cell.getStringCellValue();
                }
                String source = "";
                if(map.get("??????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????").toString())):row.getCell(Integer.parseInt(map.get("??????").toString()));
                    cell.setCellType(CellType.STRING);
                    source = cell.getStringCellValue();
                }
                String name = "";
                if(map.get("??????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????").toString())):row.getCell(Integer.parseInt(map.get("??????").toString()));
                    cell.setCellType(CellType.STRING);
                    name = cell.getStringCellValue();
                }
                String follower = "";
                if(map.get("?????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????").toString()))==null?row.createCell(Integer.parseInt(map.get("?????????").toString())):row.getCell(Integer.parseInt(map.get("?????????").toString()));
                    cell.setCellType(CellType.STRING);
                    follower = cell.getStringCellValue();
                }
                String liableTeam = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    liableTeam = cell.getStringCellValue();
                }
                String liableUnit = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    liableUnit = cell.getStringCellValue();
                }
                String liableUser = "";
                if(map.get("?????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????").toString()))==null?row.createCell(Integer.parseInt(map.get("?????????").toString())):row.getCell(Integer.parseInt(map.get("?????????").toString()));
                    cell.setCellType(CellType.STRING);
                    liableUser = cell.getStringCellValue();
                }
                String liableUserNo = "";
                if(map.get("WBS??????") != null ) {
                 Cell  cell = row.getCell(Integer.parseInt(map.get("???????????????").toString()))==null?
                          row.createCell(Integer.parseInt(map.get("???????????????").toString())):row.getCell(Integer.parseInt(map.get("???????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    liableUserNo = cell.getStringCellValue();
                }
                String userOutunit = "";
                if(map.get("?????????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("?????????????????????").toString())):row.getCell(Integer.parseInt(map.get("?????????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    userOutunit = cell.getStringCellValue();
                }
                String userOutorg = "";
                if(map.get("?????????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("?????????????????????").toString())):row.getCell(Integer.parseInt(map.get("?????????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    userOutorg = cell.getStringCellValue();
                }
                String startTime = null;
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    startTime = cell.getStringCellValue();
                }
                String completeTime = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    completeTime = cell.getStringCellValue();
                }
                String evaluateUser = "";
                if(map.get("?????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????").toString()))==null?row.createCell(Integer.parseInt(map.get("?????????").toString())):row.getCell(Integer.parseInt(map.get("?????????").toString()));
                    cell.setCellType(CellType.STRING);
                    evaluateUser = cell.getStringCellValue();
                }
                String evaluateUserNo = "";
                if(map.get("???????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("???????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("???????????????").toString())):row.getCell(Integer.parseInt(map.get("???????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    evaluateUserNo = cell.getStringCellValue();
                }
                String beforeLiableUser = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????????????????").toString())):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    beforeLiableUser = cell.getStringCellValue();
                }
                String beforeLiableUserNo = "";
                if(map.get("????????????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    beforeLiableUserNo = cell.getStringCellValue();
                }
                String subStage = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    subStage = cell.getStringCellValue();
                }
                String coeHour = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????????????????").toString())):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    coeHour = cell.getStringCellValue();
                }
                String actualCompleteTime = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????????????????").toString())):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    actualCompleteTime = cell.getStringCellValue();
                }
                String assessmentTime = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????????????????").toString())):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    assessmentTime = cell.getStringCellValue();
                }
                String planWorkingHour = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    planWorkingHour = cell.getStringCellValue();
                }
                String labourHour = "";
                if(map.get("WBS??????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    labourHour = cell.getStringCellValue();
                }
                String isPrepareCard = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????????????????").toString())):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    isPrepareCard = cell.getStringCellValue();
                }
                String prepareCardUser = "";
                if(map.get("???????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("???????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("???????????????").toString())):row.getCell(Integer.parseInt(map.get("???????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    prepareCardUser = cell.getStringCellValue();
                }
                String prepareCardState = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    prepareCardState = cell.getStringCellValue();
                }
                String prepareCardTime = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("??????????????????").toString())):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
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
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    superiorAttention = cell.getStringCellValue();
                }
                String projectControlZero = "";
                if(map.get("??????0???????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????0???????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????0???????????????").toString()))):row.getCell(Integer.parseInt(map.get("??????0???????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    projectControlZero = cell.getStringCellValue();
                }
                String projectControlOne = "";
                if(map.get("??????1???????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????1???????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????1???????????????").toString()))):row.getCell(Integer.parseInt(map.get("??????1???????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    projectControlOne = cell.getStringCellValue();
                }
                String unitControlPlan = "";
                if(map.get("????????????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("????????????????????????").toString()))):row.getCell(Integer.parseInt(map.get("????????????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    unitControlPlan = cell.getStringCellValue();
                }
                String criticalPath = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("????????????").toString()))):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    criticalPath = cell.getStringCellValue();
                }
                String masterNode = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("????????????").toString()))):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    masterNode = cell.getStringCellValue();
                }
                String importmentTask = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("????????????").toString()))):row.getCell(Integer.parseInt(map.get("????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    importmentTask = cell.getStringCellValue();
                }
                String annualQualityPro = "";
                if(map.get("????????????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("????????????????????????").toString()))):row.getCell(Integer.parseInt(map.get("????????????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    annualQualityPro = cell.getStringCellValue();
                }
                String TYearActionPlan = "";
                if(map.get("??????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????????????????").toString()))):row.getCell(Integer.parseInt(map.get("??????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    TYearActionPlan = cell.getStringCellValue();
                }
                String GGY = "";
                if(map.get("?????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("?????????").toString()))):row.getCell(Integer.parseInt(map.get("?????????").toString()));
                    cell.setCellType(CellType.STRING);
                    GGY = cell.getStringCellValue();
                }
                String reQualityProblem = "";
                if(map.get("?????????????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????????????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("?????????????????????").toString()))):row.getCell(Integer.parseInt(map.get("?????????????????????").toString()));
                    cell.setCellType(CellType.STRING);
                    reQualityProblem = cell.getStringCellValue();
                }
                String goodMaintenance = "";
                if(map.get("?????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("?????????").toString()))==null?(row.createCell(Integer.parseInt(map.get("?????????").toString()))):row.getCell(Integer.parseInt(map.get("?????????").toString()));
                    cell.setCellType(CellType.STRING);
                    goodMaintenance = cell.getStringCellValue();
                }
                String remarksOne = "";
                if(map.get("??????1") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????1").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????1").toString()))):row.getCell(Integer.parseInt(map.get("??????1").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksOne = cell.getStringCellValue();
                }
                String remarksTwo = "";
                if(map.get("??????2") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????2").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????2").toString()))):row.getCell(Integer.parseInt(map.get("??????2").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksTwo = cell.getStringCellValue();
                }
                String remarksThir = "";
                if(map.get("??????3") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????3").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????3").toString()))):row.getCell(Integer.parseInt(map.get("??????3").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksThir = cell.getStringCellValue();
                }
                String remarksFour = "";
                if(map.get("??????4") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????4").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????4").toString()))):row.getCell(Integer.parseInt(map.get("??????4").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksFour = cell.getStringCellValue();
                }
                String remarksFif = "";
                if(map.get("??????5") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("??????5").toString()))==null?(row.createCell(Integer.parseInt(map.get("??????5").toString()))):row.getCell(Integer.parseInt(map.get("??????5").toString()));
                    cell.setCellType(CellType.STRING);
                    remarksFif = cell.getStringCellValue();
                }
                String evolveState = "";
                if(map.get("????????????") != null ) {
                    Cell  cell = row.getCell(Integer.parseInt(map.get("????????????").toString()))==null?row.createCell(Integer.parseInt(map.get("????????????").toString())):row.getCell(Integer.parseInt(map.get("????????????").toString()));
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
