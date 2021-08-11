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
            //判断当前id是关联表是否存在
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

        //flag标志==1是提交
        if("1".equals(entity.getFlag())){
            User auditUser = UserUtils.get(entity.getCheckId());
            String title = "请处理'"+UserUtils.getUser().getName()+"'文件检查申请！";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            //开启流程
            actTaskService.apiStartProcess("file_check", auditUser.getLoginName(), entity.getId(), title, vars);
            entity.setStatus(FileCheckEnum.MANAGER_AUDIT.getCode());
            //审批履历
            AuditInfo auditInfo = new AuditInfo(entity.getId(),UserUtils.getUser(),1,"发起审批");
            auditInfoService.save(auditInfo);
            super.save(entity);
        }
    }

    /**
     * @Describe:审批
     * @Author:zwm
     * @Date:10:01 2021/01/29
     * @param approveDto
     * @return:void
     */
    public void approve(ApproveDTO approveDto) {
        //获取数据
        ZyFileCheck zyFileCheck = this.get(approveDto.getId());
        String status=zyFileCheck.getStatus();
        zyFileCheck.setId(approveDto.getId());
        User auditUser=null;
        Map<String,Object> vars = new HashMap<>(1);
        List<String> userLoginNames = new ArrayList<>();
        //获取下一节点审批人
        if("02".equals(status)){
            auditUser=systemService.getUser(zyFileCheck.getCreateBy().getId());
        }else{
            auditUser=systemService.getUser(zyFileCheck.getCreateBy().getId());
        }
        //审批
        String title = "请处理'"+zyFileCheck.getCreateBy().getName()+"'提交的文件检查申请！";
        vars.put("message", title);
        vars.put("checkId",zyFileCheck.getId());
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(zyFileCheck.getId(),approveDto.getOpinion(), Global.YES, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
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
     * @Describe:驳回
     * @Author:zwm
     * @Date:10:38 2021/01/29
     * @param approveDto
     * @return:void
     */
    public void reject(ApproveDTO approveDto) {
        //获取数据
        ZyFileCheck zyFileCheck = this.get(approveDto.getId());
        zyFileCheck.setId(approveDto.getId());
        //驳回提交者
        User auditUser = UserUtils.get(zyFileCheck.getCreateBy().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //审批
        String title = "您提交的文件检查申请，已被'"+UserUtils.getUser().getName()+"'驳回！";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(zyFileCheck.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //审批履历
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //更新状态及当前处理人
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

    //获取每月通过科室分类后的检查级别
    public List<Map<String,String>>  getSumByOfficeAndLevel(){
        return zyFileCheckDao.getSumByOfficeAndLevel();
    }

    //获取每月通过检查级别总和
    public List<Map<String,String>>  getTotalByOfficeAndLevel(){
        return zyFileCheckDao.getTotalByOfficeAndLevel();
    }
    //获取每月通过科室分类后的文件类别
    public List<Map<String,String>>  getSumByOfficeAndType(){
        return zyFileCheckDao.getSumByOfficeAndType();
    }

    //获取每月通过文件类别总和
    public List<Map<String,String>>  getTotalByOfficeAndType(){
        return zyFileCheckDao.getTotalByOfficeAndType();
    }


    //获取每月通过科室分类后的文件类别
    public List<Map<String,String>>  getSumByOfficeAndContent(){
        return zyFileCheckDao.getSumByOfficeAndContent();
    }

    //获取每月通过文件类别总和
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
                //  response.setHeader("Content-Disposition", "attachment; filename=" + new String(("数据集为空.txt").getBytes("gb2312"), "ISO8859-1"));
            } else {
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                String[] headRow = new String[]{"科室","一票否决项","问题项","建议项","总计"};
                ComplexExcelSUtil.ExportConfig config = new ComplexExcelSUtil.ExportConfig(workbook, headRow, "文件问题统计表");
                int curRowNum = 0;
                //基础信息结束列
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
                        logger.error("文件问题统计表");
                    }
                }
                List<String> str1 = new ArrayList<>(baseColumnNum);
                str1.add("合计");
                str1.add(String.valueOf(listTotal.get(0).get("one")));
                str1.add(String.valueOf(listTotal.get(0).get("problerm")));
                str1.add(String.valueOf(listTotal.get(0).get("approval")));
                str1.add(String.valueOf(listTotal.get(0).get("total")));
                curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str1, curRowNum, cellStyle);
                /*response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        new String(("文件问题统计表" + new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(new Date()) + ".xlsx").getBytes("gb2312"), "ISO8859-1"));
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
                zy.setFileType("检查级别");
                zyFileReportService.save(zy);
                logger.info("导出文件问题统计表成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出文件问题统计表失败：" + e.getMessage());
        }
    }

    public void  getInfoByOffcieAndType(){
        InputStream is1=null;
        List<Map<String,String>>  list=zyFileCheckDao.getSumByOfficeAndType();
        List<Map<String,String>>  listTotal=zyFileCheckDao.getTotalByOfficeAndType();
        try {
            if (CollectionUtils.isEmpty(list)) {
                //  response.setContentType("APPLICATION/OCTET-STREAM");
                //  response.setHeader("Content-Disposition", "attachment; filename=" + new String(("数据集为空.txt").getBytes("gb2312"), "ISO8859-1"));
            } else {
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                String[] headRow = new String[]{"科室","标准规范","一般技术文件","试验大纲","试验报告","总计"};
                ComplexExcelSUtil.ExportConfig config = new ComplexExcelSUtil.ExportConfig(workbook, headRow, "文件类型统计表");
                int curRowNum = 0;
                //基础信息结束列
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
                        logger.error("文件类型统计表");
                    }
                }
                List<String> str1 = new ArrayList<>(baseColumnNum);
                str1.add("合计");
                str1.add(String.valueOf(listTotal.get(0).get("one")));
                str1.add(String.valueOf(listTotal.get(0).get("problerm")));
                str1.add(String.valueOf(listTotal.get(0).get("approval")));
                str1.add(String.valueOf(listTotal.get(0).get("total")));
                curRowNum = ComplexExcelSUtil.outputLine(sheet, config, str1, curRowNum, cellStyle);
                /*response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", "attachment; filename=" +
                        new String(("文件类型统计表" + new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(new Date()) + ".xlsx").getBytes("gb2312"), "ISO8859-1"));
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
                zy.setFileType("文件类型");
                zyFileReportService.save(zy);
                logger.info("导出文件类型统计表成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出文件类型统计表失败：" + e.getMessage());
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
                //  response.setHeader("Content-Disposition", "attachment; filename=" + new String(("数据集为空.txt").getBytes("gb2312"), "ISO8859-1"));
            } else {
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");
                String[] headRow = new String[]{"科室","引用文件","正文","前言","数值","表","排版","图","目次","字体","依据性文件","引言","编制人","尺寸和公差","附录","公式","符号、代号和缩略语","术语和定义","终结线","总计"};
                ComplexExcelSUtil.ExportConfig config = new ComplexExcelSUtil.ExportConfig(workbook, headRow, "按项统计表");
                int curRowNum = 0;
                //基础信息结束列
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
                        logger.error("按项统计表");
                    }
                }
                List<String> str1 = new ArrayList<>(baseColumnNum);
                str1.add("合计");
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
                        new String(("文件类型统计表" + new SimpleDateFormat("yyyy-MM-dd hh:ss:mm").format(new Date()) + ".xlsx").getBytes("gb2312"), "ISO8859-1"));
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
                zy.setFileType("按项");
                zyFileReportService.save(zy);
                logger.info("导出按项统计表成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出按项统计表失败：" + e.getMessage());
        }
    }
}
