package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.dao.dataCenter.ZyFileCheckDao;
import com.demxs.tdm.dao.dataCenter.ZyFileReportDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.FileCheckEnum;
import com.demxs.tdm.domain.dataCenter.VO.ZyFileReport;
import com.demxs.tdm.domain.dataCenter.ZyFileAttribute;
import com.demxs.tdm.domain.dataCenter.ZyFileCheck;
import com.demxs.tdm.domain.dataCenter.ZyFileCheckRelation;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.dataCenter.jason.ComplexExcelSUtil;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.service.sys.DictService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyFileCheckService extends  CrudService<ZyFileCheckDao, ZyFileCheck> {

    @Autowired
    ZyFileCheckDao zyFileCheckDao;

    @Autowired
    private ActTaskService actTaskService;

    @Autowired
    ZyFileCheckRelationService zyFileCheckRelationService;

    @Autowired
    AuditInfoService auditInfoService;

    @Autowired
    SystemService  systemService;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private AssetInfoService assetInfoService;

    @Autowired
    private ZyFileReportService zyFileReportService;

    public Page<ZyFileCheck> list(Page<ZyFileCheck> page, ZyFileCheck entity) {
        Page<ZyFileCheck> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyFileCheck> findPage(Page<ZyFileCheck> page, ZyFileCheck entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyFileCheck get(String id) {
        ZyFileCheck equipment = super.dao.get(id);
        return equipment;
    }


    public void saveInfo(ZyFileCheck entity) {
        super.save(entity);
        /*if(CollectionUtils.isNotEmpty(entity.getZyFileCheckRelationList())){
            ZyFileCheck  zy=zyFileCheckDao.get(entity.getId());
            //????????????id????????????????????????
            List<String> list=zyFileCheckDao.getIsExist(entity.getId());
            if(StringUtils.isEmpty(zy.getStatus()) || "01".equals(zy.getStatus()) ){
                if(CollectionUtils.isNotEmpty(list)) {
                    for(String s:list){
                        ZyFileCheckRelation z=new ZyFileCheckRelation();
                        z.setId(s);
                        z.setDelFlag("1");
                        zyFileCheckRelationService.delete(z);
                    }
                }
                for (ZyFileCheckRelation z : entity.getZyFileCheckRelationList()) {
                    z.setFileId(entity.getId());
                    z.setIsFlag("1");
                    zyFileCheckRelationService.save(z);
                }
            }
        }*/

        //flag??????==1?????????
        if("1".equals(entity.getFlag())){
            User auditUser = UserUtils.get(entity.getCheckId());
            String title = "?????????'"+UserUtils.getUser().getName()+"'?????????????????????";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            //????????????
            actTaskService.apiStartProcess("file_check", auditUser.getLoginName(), entity.getId(), title, vars);
            entity.setStatus(FileCheckEnum.MANAGER_AUDIT.getCode());
            //????????????
            AuditInfo auditInfo = new AuditInfo(entity.getId(),UserUtils.getUser(),1,"????????????");
            auditInfoService.save(auditInfo);
            super.save(entity);
        }
    }

    /**
     * @Describe:??????
     * @Author:zwm
     * @Date:10:01 2021/01/29
     * @param approveDto
     * @return:void
     */
    public void approve(ApproveDTO approveDto) {
        //????????????
        ZyFileCheck zyFileCheck = this.get(approveDto.getId());
        String status=zyFileCheck.getStatus();
        zyFileCheck.setId(approveDto.getId());
        User auditUser=null;
        Map<String,Object> vars = new HashMap<>(1);
        List<String> userLoginNames = new ArrayList<>();
        //???????????????????????????
        if("02".equals(status)){
            auditUser=systemService.getUser(zyFileCheck.getCreateBy().getId());
        }else{
            auditUser=systemService.getUser(zyFileCheck.getCreateBy().getId());
        }
        //??????
        String title = "?????????'"+zyFileCheck.getCreateBy().getName()+"'??????????????????????????????";
        vars.put("message", title);
        vars.put("checkId",zyFileCheck.getId());
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(zyFileCheck.getId(),approveDto.getOpinion(), Global.YES, assignee,vars);
        //????????????
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //??????????????????????????????
        if(auditUser!=null)
            zyFileCheck.setCheckId(auditUser.getId());
        switch(FileCheckEnum.get(zyFileCheck.getStatus())){
            case CHECK:
                status = FileCheckEnum.MANAGER_AUDIT.getCode();
                break;
            case MANAGER_AUDIT:
                status = FileCheckEnum.AUDIT.getCode();
                break;
            case AUDIT:
                status = FileCheckEnum.SUBMIT.getCode();
                break;
            default:
                break;
        }
        zyFileCheck.setStatus(status);
        this.save(zyFileCheck);
    }
    /**
     * @Describe:??????
     * @Author:zwm
     * @Date:10:38 2021/01/29
     * @param approveDto
     * @return:void
     */
    public void reject(ApproveDTO approveDto) {
        //????????????
        ZyFileCheck zyFileCheck = this.get(approveDto.getId());
        zyFileCheck.setId(approveDto.getId());
        //???????????????
        User auditUser = UserUtils.get(zyFileCheck.getCreateBy().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //??????
        String title = "???????????????????????????????????????'"+UserUtils.getUser().getName()+"'?????????";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(zyFileCheck.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //????????????
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //??????????????????????????????
        zyFileCheck.setCheckId(UserUtils.getUser().getId());
        zyFileCheck.setStatus("01");
        this.save(zyFileCheck);

        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String>  getIsExist(String fileId){
        return this.dao.getIsExist(fileId);
    }


    public void submitFile(ZyFileCheck entity){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=new Date();
        if(CollectionUtils.isNotEmpty(entity.getZyFileCheckRelationList())){
            ZyFileCheck  zy=zyFileCheckDao.get(entity.getId());
            ZyFileCheckRelation  zs=new ZyFileCheckRelation();
            zs.setFileId(entity.getId());
            zs.setDelFlag("0");
            List<ZyFileCheckRelation> list=zyFileCheckRelationService.findList(zs);
            if(CollectionUtils.isNotEmpty(list)) {
                for(ZyFileCheckRelation s:list){
                    zyFileCheckRelationService.delete(s);
                }
            }
            for (ZyFileCheckRelation z : entity.getZyFileCheckRelationList()) {
                z.setFileId(entity.getId());
                zyFileCheckRelationService.save(z);
            }
            zy.setCheckDate(d);
            this.save(zy);
        }
    }

    //????????????????????????????????????????????????
    public List<Map<String,String>>  getSumByOfficeAndLevel(){
        return zyFileCheckDao.getSumByOfficeAndLevel();
    }

    //????????????????????????????????????
    public List<Map<String,String>>  getTotalByOfficeAndLevel(){
        return zyFileCheckDao.getTotalByOfficeAndLevel();
    }
    //????????????????????????????????????????????????
    public List<Map<String,String>>  getSumByOfficeAndType(){
        return zyFileCheckDao.getSumByOfficeAndType();
    }

    //????????????????????????????????????
    public List<Map<String,String>>  getTotalByOfficeAndType(){
        return zyFileCheckDao.getTotalByOfficeAndType();
    }


    //????????????????????????????????????????????????
    public List<Map<String,String>>  getSumByOfficeAndContent(){
        return zyFileCheckDao.getSumByOfficeAndContent();
    }

    //????????????????????????????????????
    public List<Map<String,String>>  getTotalByOfficeAndContent(){
        return zyFileCheckDao.getTotalByOfficeAndContent();
    }

    public void  getInfoByOffcieAndLevel(){
        InputStream is1=null;
        List<Map<String,String>>  list=zyFileCheckDao.getSumByOfficeAndLevel();
        List<Map<String,String>>  listTotal=zyFileCheckDao.getTotalByOfficeAndLevel();
        try {
            if (CollectionUtils.isEmpty(list)) {
                // response.setContentType("APPLICATION/OCTET-STREAM");
                //  response.setHeader("Content-Disposition", "attachment; filename=" + new String(("???????????????.txt").getBytes("gb2312"), "ISO8859-1"));
            } else {
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                String[] headRow = new String[]{"??????","???????????????","?????????","?????????","??????"};
                ComplexExcelSUtil.ExportConfig config = new ComplexExcelSUtil.ExportConfig(workbook, headRow, "?????????????????????");
                int curRowNum = 0;
                //?????????????????????
                final int baseColumnNum = 4;
                curRowNum = ComplexExcelSUtil.createHead(sheet, config, curRowNum);
                CellStyle cellStyle = config.getWorkbook().createCellStyle();
                Integer s=0;
                for (Map<String,String>  map: list) {
                    try {
                        int startRow = curRowNum;
                        s++;
                        List<String> str = new ArrayList<>(baseColumnNum);
                        str.add(map.get("lab").toString());
                        str.add(String.valueOf(map.get("one")));
                        str.add(String.valueOf(map.get("problerm")));
                        str.add(String.valueOf(map.get("approval")));
                        str.add(String.valueOf(map.get("total")));
                        curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str, curRowNum, cellStyle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("?????????????????????");
                    }
                }
                List<String> str1 = new ArrayList<>(baseColumnNum);
                str1.add("??????");
                str1.add(String.valueOf(listTotal.get(0).get("one")));
                str1.add(String.valueOf(listTotal.get(0).get("problerm")));
                str1.add(String.valueOf(listTotal.get(0).get("approval")));
                str1.add(String.valueOf(listTotal.get(0).get("total")));
                curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str1, curRowNum, cellStyle);
                /*response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        new String(("?????????????????????" + new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(new Date()) + ".xlsx").getBytes("gb2312"), "ISO8859-1"));
                workbook.write(response.getOutputStream());*/
                String filePath1 = System.getProperty("java.io.tmpdir")+ File.separator;
                String   fileName1=IdGen.uuid()+".xlsx";
                File tempFile = new File(filePath1);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(tempFile.getPath() + File.separator + fileName1);
                workbook.write(fos);
                filePath1=tempFile.getPath() + File.separator +fileName1;
                is1 = new FileInputStream(new File(filePath1));
                String filePath = fileStore.saveFile(fileName1,is1);
                AttachmentInfo attachment = assetInfoService.save(fileName1,"file_check", filePath, 1L);
                ZyFileReport zy=new ZyFileReport();
                zy.setAttachId(attachment.getId());
                zy.setFileType("????????????");
                zyFileReportService.save(zy);
                logger.info("?????????????????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("????????????????????????????????????" + e.getMessage());
        }
    }

    public void  getInfoByOffcieAndType(){
        InputStream is1=null;
        List<Map<String,String>>  list=zyFileCheckDao.getSumByOfficeAndType();
        List<Map<String,String>>  listTotal=zyFileCheckDao.getTotalByOfficeAndType();
        try {
            if (CollectionUtils.isEmpty(list)) {
                //  response.setContentType("APPLICATION/OCTET-STREAM");
                //  response.setHeader("Content-Disposition", "attachment; filename=" + new String(("???????????????.txt").getBytes("gb2312"), "ISO8859-1"));
            } else {
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                String[] headRow = new String[]{"??????","????????????","??????????????????","????????????","????????????","??????"};
                ComplexExcelSUtil.ExportConfig config = new ComplexExcelSUtil.ExportConfig(workbook, headRow, "?????????????????????");
                int curRowNum = 0;
                //?????????????????????
                final int baseColumnNum = 4;
                curRowNum = ComplexExcelSUtil.createHead(sheet, config, curRowNum);
                CellStyle cellStyle = config.getWorkbook().createCellStyle();
                Integer s=0;
                for (Map<String,String>  map: list) {
                    try {
                        int startRow = curRowNum;
                        s++;
                        List<String> str = new ArrayList<>(baseColumnNum);
                        str.add(map.get("lab").toString());
                        str.add(String.valueOf(map.get("standard")));
                        str.add(String.valueOf(map.get("teachFile")));
                        str.add(String.valueOf(map.get("testConfig")));
                        str.add(String.valueOf(map.get("testReport")));
                        str.add(String.valueOf(map.get("total")));
                        curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str, curRowNum, cellStyle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("?????????????????????");
                    }
                }
                List<String> str1 = new ArrayList<>(baseColumnNum);
                str1.add("??????");
                str1.add(String.valueOf(listTotal.get(0).get("one")));
                str1.add(String.valueOf(listTotal.get(0).get("problerm")));
                str1.add(String.valueOf(listTotal.get(0).get("approval")));
                str1.add(String.valueOf(listTotal.get(0).get("total")));
                curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str1, curRowNum, cellStyle);
                /*response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        new String(("?????????????????????" + new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(new Date()) + ".xlsx").getBytes("gb2312"), "ISO8859-1"));
                workbook.write(response.getOutputStream());*/
                String filePath1 = System.getProperty("java.io.tmpdir")+ File.separator;
                String   fileName1=IdGen.uuid()+".xlsx";
                File tempFile = new File(filePath1);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(tempFile.getPath() + File.separator + fileName1);
                workbook.write(fos);
                filePath1=tempFile.getPath() + File.separator +fileName1;
                is1 = new FileInputStream(new File(filePath1));
                String filePath = fileStore.saveFile(fileName1,is1);
                AttachmentInfo attachment = assetInfoService.save(fileName1,"file_check" ,filePath, 1L);
                ZyFileReport zy=new ZyFileReport();
                zy.setAttachId(attachment.getId());
                zy.setFileType("????????????");
                zyFileReportService.save(zy);
                logger.info("?????????????????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("????????????????????????????????????" + e.getMessage());
        }
    }

    public   List<ZyFileCheck> findDataList(ZyFileCheck zyFileCheck){
        return this.dao.findDataList(zyFileCheck);
    }


    public void  getInfoByOffcieAndContent(){
        InputStream is1=null;
        List<Map<String,String>>  list=zyFileCheckDao.getSumByOfficeAndContent();
        List<Map<String,String>>  listTotal=zyFileCheckDao.getTotalByOfficeAndContent();
        try {
            if (CollectionUtils.isEmpty(list)) {
                //  response.setContentType("APPLICATION/OCTET-STREAM");
                //  response.setHeader("Content-Disposition", "attachment; filename=" + new String(("???????????????.txt").getBytes("gb2312"), "ISO8859-1"));
            } else {
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                String[] headRow = new String[]{"??????","????????????","??????","??????","??????","???","??????","???","??????","??????","???????????????","??????","?????????","???????????????","??????","??????","???????????????????????????","???????????????","?????????","??????"};
                ComplexExcelSUtil.ExportConfig config = new ComplexExcelSUtil.ExportConfig(workbook, headRow, "???????????????");
                int curRowNum = 0;
                //?????????????????????
                final int baseColumnNum = 4;
                curRowNum = ComplexExcelSUtil.createHead(sheet, config, curRowNum);
                CellStyle cellStyle = config.getWorkbook().createCellStyle();
                Integer s=0;
                for (Map<String,String>  map: list) {
                    try {
                        int startRow = curRowNum;
                        s++;
                        List<String> str = new ArrayList<>(baseColumnNum);
                        str.add(String.valueOf(map.get("lab")));
                        str.add(String.valueOf(map.get("one")));
                        str.add(String.valueOf(map.get("tow")));
                        str.add(String.valueOf(map.get("three")));
                        str.add(String.valueOf(map.get("four")));
                        str.add(String.valueOf(map.get("five")));
                        str.add(String.valueOf(map.get("six")));
                        str.add(String.valueOf(map.get("seven")));
                        str.add(String.valueOf(map.get("eight")));
                        str.add(String.valueOf(map.get("night")));
                        str.add(String.valueOf(map.get("ten")));
                        str.add(String.valueOf(map.get("elven")));
                        str.add(String.valueOf(map.get("twitny")));
                        str.add(String.valueOf(map.get("thirty")));
                        str.add(String.valueOf(map.get("fourty")));
                        str.add(String.valueOf(map.get("fifity")));
                        str.add(String.valueOf(map.get("sixty")));
                        str.add(String.valueOf(map.get("seventy")));
                        str.add(String.valueOf(map.get("eighty")));
                        str.add(String.valueOf(map.get("total")));
                        curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str, curRowNum, cellStyle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("???????????????");
                    }
                }
                List<String> str1 = new ArrayList<>(baseColumnNum);
                str1.add("??????");
                str1.add(String.valueOf(listTotal.get(0).get("one")));
                str1.add(String.valueOf(listTotal.get(0).get("tow")));
                str1.add(String.valueOf(listTotal.get(0).get("three")));
                str1.add(String.valueOf(listTotal.get(0).get("four")));
                str1.add(String.valueOf(listTotal.get(0).get("five")));
                str1.add(String.valueOf(listTotal.get(0).get("six")));
                str1.add(String.valueOf(listTotal.get(0).get("seven")));
                str1.add(String.valueOf(listTotal.get(0).get("eight")));
                str1.add(String.valueOf(listTotal.get(0).get("night")));
                str1.add(String.valueOf(listTotal.get(0).get("ten")));
                str1.add(String.valueOf(listTotal.get(0).get("elven")));
                str1.add(String.valueOf(listTotal.get(0).get("twitny")));
                str1.add(String.valueOf(listTotal.get(0).get("thirty")));
                str1.add(String.valueOf(listTotal.get(0).get("fourty")));
                str1.add(String.valueOf(listTotal.get(0).get("fifity")));
                str1.add(String.valueOf(listTotal.get(0).get("sixty")));
                str1.add(String.valueOf(listTotal.get(0).get("seventy")));
                str1.add(String.valueOf(listTotal.get(0).get("eighty")));
                str1.add(String.valueOf(listTotal.get(0).get("total")));
                curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str1, curRowNum, cellStyle);
                /*response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        new String(("?????????????????????" + new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(new Date()) + ".xlsx").getBytes("gb2312"), "ISO8859-1"));
                workbook.write(response.getOutputStream());*/
                String filePath1 = System.getProperty("java.io.tmpdir")+ File.separator;
                String   fileName1=IdGen.uuid()+".xlsx";
                File tempFile = new File(filePath1);
                if (!tempFile.exists()) {
                    tempFile.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(tempFile.getPath() + File.separator + fileName1);
                workbook.write(fos);
                filePath1=tempFile.getPath() + File.separator +fileName1;
                is1 = new FileInputStream(new File(filePath1));
                String filePath = fileStore.saveFile(fileName1,is1);
                AttachmentInfo attachment = assetInfoService.save(fileName1,"file_check" ,filePath, 1L);
                ZyFileReport zy=new ZyFileReport();
                zy.setAttachId(attachment.getId());
                zy.setFileType("??????");
                zyFileReportService.save(zy);
                logger.info("???????????????????????????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("??????????????????????????????" + e.getMessage());
        }
    }
}
