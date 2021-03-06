package com.demxs.tdm.service.business;

import com.demxs.tdm.comac.common.act.utils.EmailUtil;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.ModelOrderDao;
import com.demxs.tdm.dao.business.PlanConnectDao;
import com.demxs.tdm.dao.business.PlanDao;
import com.demxs.tdm.dao.business.WorkOrderDao;
import com.demxs.tdm.domain.business.Plan;
import com.demxs.tdm.domain.business.PlanConnect;
import com.demxs.tdm.domain.business.WorkOrder;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.resource.yuangong.YuangongService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

@Service
/*@EnableAsync*/
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class WorkOrderService extends  CrudService<WorkOrderDao, WorkOrder> {

    @Autowired
    WorkOrderDao workOrderDao;
    @Autowired
    private PlanDao planDao;
    @Autowired
    private PlanConnectDao planConnectDao;
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
    private ModelOrderDao modelOrderDao;
    @Autowired
    UserDao userDao;
    @Autowired
    private PlanService planService;
    @Autowired
    private OfficeDao officeDao;


    private static final String BASE_DIR = Global.getConfig("sourceDir");

    public Page<WorkOrder> list(Page<WorkOrder> page, WorkOrder workOrder) {
        Page<WorkOrder> Page = super.findPage(page, workOrder);
        return Page;
    }

    public Page<WorkOrder> findPage(Page<WorkOrder> page, WorkOrder entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public WorkOrder get(String id) {
        WorkOrder workOrder = super.dao.get(id);
        return workOrder;
    }


    public void save(WorkOrder workOrder) {
        if(workOrder.getEstablishTime()== null){
            workOrder.setEstablishTime(new Date());
        }
        super.save(workOrder);
      //
        //  System.out.println("--------------"+workOrder.getId());
    }

    @Async
    public Future<String> remind(WorkOrder workOrder){
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        String id = workOrder.getId();
        if(StringUtils.isNotEmpty(id)){
            //???????????????????????????????????????????????????
            List<Plan> state = planDao.findState(id);
            for(Plan plan : state){
                String liableUserId1 = plan.getLiableUserId();
                User liableUser = new User();
                if(StringUtils.isNotBlank(liableUserId1)){
                    liableUser = userDao.get(liableUserId1);
                }
                if(liableUser!=null){
                    String email = liableUser.getEmail();
                    String liableUserId = plan.getLiableUserId();
                    List<PlanConnect> stateSend = planConnectDao.findStateSend(liableUserId);
                    for(PlanConnect planConnect1:stateSend){
                        //?????????
                         String inExecution = planConnect1.getInExecution();
                        //?????????
                        String response = planConnect1.getResponse();
                        //
                        Date requireCompleteTime = plan.getRequireCompleteTime();
                        String format = s2.format(requireCompleteTime);
                         int a = Integer.valueOf(inExecution)+Integer.valueOf(response);
                        emailUtil.sendMail(email,"?????????????????????????????????","????????????????????????"+a+"?????????????????????????????????????????????"+inExecution+"???,?????????"+response+"???????????????????????????????????????"+format+",????????????????????????");
                    }
                }
            }
        }
        try {
            Thread.sleep(250);
        }catch (InterruptedException  e){
            logger.debug("??????????????????????????????????????????");
            e.printStackTrace();
            return new AsyncResult<String>("??????????????????!?????????????????????");
        }
        return new AsyncResult<String>("?????????????????????");
    }

    public List<Plan> export(WorkOrder workOrder){
        List<Map<Object,Object>> objs = new ArrayList<Map<Object,Object>>();
        List<Plan> plans = new ArrayList<Plan>();
        try {
            //?????????
            workOrder.setEstablishName(UserUtils.getUser().getName());
            //?????????
            workOrder.setAccendant(UserUtils.getUser().getName());
            //?????????excel???ID
            String workID = workOrder.getWorkId();
            //??????????????????????????????
            super.save(workOrder);
            //???????????????ID
            if(workID!=null && !"".equals(workID)){
                //??????????????????
                List<String> officeName = officeDao.getOfficeName();
                //FTP ?????????????????????
                InputStream is = fileStore.downloadFileToStream(BASE_DIR + attachmentInfoService.get(workID).getPath());
                List<Plan> workOrderList = parseFromExcel( 1, Plan.class,is);
                //????????????excel
                int i = 0;
                List<Plan> modelPlans = new ArrayList<Plan>();
                a:for(Plan plan : workOrderList){
                    if(StringUtils.isNotBlank(plan.getLiableBody())){
                        //????????????/?????????????????????
                        if(!officeName.contains(plan.getLiableBody())){
                            continue a;
                        }
                    }
                    Map<Object,Object> map = new HashMap<Object,Object>();
                    map.put("name",plan.getLiableUserId());
                    map.put("row",++i);
                    modelPlans.add(plan);
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
                System.out.println(str);
                List<Map<Object, Object>> result = modelOrderDao.exec(str);
                plans = disposeData(result,workOrder,modelPlans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plans;
    }

    public List<Plan> disposeData(List<Map<Object, Object>> result, WorkOrder workOrder, List<Plan> plans){
        //?????????
        workOrder.setEstablishName(UserUtils.getUser().getName());
        //?????????
        workOrder.setAccendant(UserUtils.getUser().getName());
        //?????????excel???ID
        String workID = workOrder.getWorkId();
        String workOrderName = workOrder.getWorkOrderName();
        List<String> officeName = officeDao.getOfficeName();
        //????????????
        List<Plan> back = new ArrayList<Plan>();    //??????
        List<Plan> backRe = new ArrayList<Plan>();    //??????
        List<Plan> save = new ArrayList<Plan>();
        try {
            int i =0;
            for(Map<Object, Object> map : result){
                String cnt = map.get("CNT").toString();
                int c = Integer.parseInt(cnt);
                if(c>1){
                    plans.get(i).setWorkOrderName(workOrderName);
                    back.add(plans.get(i));
                }else{
                    save.add(plans.get(i));
                }
                i++;
            }
            for(Plan plan : save){
                //??????id
                plan.setWorkId(workOrder.getId());
                plan.setWorkOrderName(workOrderName);
                String userName = plan.getLiableUserId();
                //????????????????????????????????????
                if(StringUtils.isNotBlank(userName)){
                    List<User> byName2 = userDao.getByName(userName);
                    //?????????
                    if(!Collections3.isEmpty(byName2)){
                        plan.setLiableUserId(byName2.get(0).getId());
                    }else {
                        User user = new User();
                        user.setName(userName);
                        UUID uuid = UUID.randomUUID();
                        user.setId(uuid.toString());
                        userDao.insert(user);
                        //??????????????????ID
                        List<User> byName1 = userDao.getByName(userName);
                        if (!Collections3.isEmpty(byName1)) {
                            plan.setLiableUserId(byName1.get(0).getId());
                        }
                    }
                    //???????????????????????????
                    User user = byName2.get(0);
                    Office office = user.getOffice();
                    plan.setOffice(office);
                }
                if(plan.getState()!=null){ //??????
                    //????????????
                    switch (plan.getState()){
                        case "?????????" : plan.setState("0");break;
                        case "?????????" : plan.setState("1");break;
                        case "?????????" : plan.setState("2");break;
                    }
                }if(plan.getSerialNumber()!=null){
                    //???????????????????????????
                    String father = findFather(plan.getSerialNumber(),plan);
                    plan.setParentid(father);
                }
                if("0".equals(plan.getAssistCompany())){
                    plan.setAssistCompany("");
                }
                if("0".equals(plan.getMaturity())){
                    plan.setMaturity("");
                }
                if("0".equals(plan.getInPut())){
                    plan.setInPut("");
                }
                if("0".equals(plan.getOutPut())){
                    plan.setOutPut("");
                }
                if("0".equals(plan.getAllowCondtion())){
                    plan.setAllowCondtion("");
                }
                if("0".equals(plan.getMajor())){
                    plan.setMajor("");
                }
                if("0".equals(plan.getAircraftType())){
                    plan.setAircraftType("");
                }

                if("0".equals(plan.getSortie())){
                    plan.setSortie("");
                }
                if("0".equals(plan.getFiguerNum())){
                    plan.setFiguerNum("");
                }
                if("0".equals(plan.getSource())){
                    plan.getSource("");
                }
                if("0".equals(plan.getDescribe())){
                    plan.setDescribe("");
                }
                if("0".equals(plan.getBusinessNum())){
                    plan.setBusinessNum("");
                }
                if("0".equals(plan.getEvoleExplain())){
                    plan.setEvoleExplain("");
                }
                planService.save(plan);
            }

            a:for(Plan plan : back){
                List<User> users = new ArrayList<User>();
                //??????id
                plan.setWorkId(workOrder.getId());
                plan.setWorkOrderName(workOrderName);
                String userName = plan.getLiableUserId();
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
                            plan.setLiableUserId(users.get(0).getId());
                        }else{
                            backRe.add(plan);
                            continue a;
                        }

                    }
                    //???????????????????????????
                    User user = byName2.get(0);
                    Office office = user.getOffice();
                    plan.setOffice(office);
                }
                if(plan.getState()!=null){ //??????
                    //????????????
                    switch (plan.getState()){
                        case "?????????" : plan.setState("0");break;
                        case "?????????" : plan.setState("1");break;
                        case "?????????" : plan.setState("2");break;
                    }
                }if(plan.getSerialNumber()!=null){
                    //???????????????????????????
                    String father = findFather(plan.getSerialNumber(),plan);
                    plan.setParentid(father);
                }
                if("0".equals(plan.getAssistCompany())){
                    plan.setAssistCompany("");
                }
                if("0".equals(plan.getMaturity())){
                    plan.setMaturity("");
                }
                if("0".equals(plan.getInPut())){
                    plan.setInPut("");
                }
                if("0".equals(plan.getOutPut())){
                    plan.setOutPut("");
                }
                if("0".equals(plan.getAllowCondtion())){
                    plan.setAllowCondtion("");
                }
                if("0".equals(plan.getMajor())){
                    plan.setMajor("");
                }
                if("0".equals(plan.getAircraftType())){
                    plan.setAircraftType("");
                }

                if("0".equals(plan.getSortie())){
                    plan.setSortie("");
                }
                if("0".equals(plan.getFiguerNum())){
                    plan.setFiguerNum("");
                }
                if("0".equals(plan.getSource())){
                    plan.getSource("");
                }
                if("0".equals(plan.getDescribe())){
                    plan.setDescribe("");
                }
                if("0".equals(plan.getBusinessNum())){
                    plan.setBusinessNum("");
                }
                if("0".equals(plan.getEvoleExplain())){
                    plan.setEvoleExplain("");
                }
                planService.save(plan);
            }
        } catch (Exception e) {
            logger.error("???????????????????????????", e);
        }
        return backRe;
    }
    //??????????????????
    public String findFather(String serialNumber, Plan plan){

        String[] split = serialNumber.split("\\.");
        String one = new String();
        int length = split.length;
        switch (length){
            case 2:
                one = split[0];
                one = findFather1(one);
                plan.setLevel("2");
                break;
            case 3:
                one = split[0]+"."+split[1];
                one = findFather1(one);
                plan.setLevel("3");
                break;
            case 4:
                one = split[0]+"."+split[1]+"."+split[2];
                one = findFather1(one);
                plan.setLevel("4");break;
            default:
                one = null;
                plan.setLevel("1");
                break;
        }
        return one;
    }
    public String findFather1(String num){
        List<Plan> father = planDao.findFather(num);
        for (Plan plan:father){
            num = plan.getId();
        }
        return num;
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
                    cellContent = "".equals(cellContent) ? "0" : cellContent;
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
}
