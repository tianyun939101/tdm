package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.WordUtils;
import com.demxs.tdm.common.utils.zrutils.WordUtil;
import com.demxs.tdm.dao.business.nostandard.NoStandardTestLogDao;
import com.demxs.tdm.domain.ability.TestCategoryAssessRequest;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.NoStandardTestLogEnum;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecution;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestLog;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.ability.TestCategoryAssessRequestService;
import com.demxs.tdm.service.business.AuditInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: Jason
 * @Date: 2020/3/6 09:20
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class NoStandardTestLogService extends CrudService<NoStandardTestLogDao, NoStandardTestLog> {

    @Autowired
    private AuditInfoService infoService;
    @Autowired
    NoStandardTestCheckService standardTestCheckService;
    @Autowired
    private NoStandardTestLogDao noStandardTestLogDao;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private UserDao userDao;
    @Transactional(readOnly = false)
    public int changeStatus(NoStandardTestLog testLog){
        return super.dao.changeStatus(testLog);
    }

    @Override
    public  NoStandardTestLog get(String id){
        return this.dao.get(id);
    }

    @Transactional(readOnly = false)
    public int save(NoStandardExecution noStandardExecution, Collection<NoStandardTestLog> testLogs){
        int count = 0;
        if(null != testLogs && !testLogs.isEmpty()){
            for (NoStandardTestLog testLog : testLogs) {
                testLog.setExecutionId(noStandardExecution.getId());
                Yuangong testManager = noStandardExecution.getTestManager();
                if(null != testManager && null != testManager.getUser()){
                    if(StringUtils.isNotBlank(testManager.getUser().getName())){
                        testLog.setTestManager(testManager.getUser().getName());
                    }
                }
                testLog.setStatus(NoStandardTestLogEnum.NOT_SUBMITTED.getCode());
                super.save(testLog);
                count++;
            }
        }
        return count;
    }

    @Transactional
    public void audit(NoStandardTestLog testLog){
        AuditInfo info = testLog.getAuditInfo();
        info.setAuditUser(UserUtils.getUser());

        infoService.save(info);
    }

   public List<NoStandardTestLog> getByExecutionId(NoStandardTestLog  noStandardTestLog){
        return noStandardTestLogDao.getByExecutionId(noStandardTestLog);
   }

    public void download(NoStandardTestLog noStandardTestLog, HttpServletResponse response){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        noStandardTestLog = this.get(noStandardTestLog.getId());
        NoStandardTestCheck s= standardTestCheckService.getTestCheckByReportId(noStandardTestLog.getExecutionId());
        List<AuditInfo> byKey = auditInfoService.getByKey(s.getId());
        String auditEx = "";
        String auditStat = "";
        if(CollectionUtils.isNotEmpty(byKey)){
            String strU = "";
            for (AuditInfo auditInfo : byKey) {
                User auditUser = auditInfo.getAuditUser();
                if(auditUser != null){
                    strU += auditUser.getName()+",";
                }
            }
            if(StringUtils.isNotBlank(strU) &&"05".equals(s.getStatus()) ){
                String[] split = strU.split(",");
                auditEx  = split[split.length-1];
                auditStat  = split[split.length-2];
            }
        }
        XWPFDocument document = null;
        try{
            URL resource = NoStandardTestLogService.class.getClassLoader().getResource("template/试验日志下载.docx");
            String url= NoStandardTestLogService.class.getClassLoader().getResource("template/试验日志下载.docx").getPath();
            url = url.replaceFirst("/","");
            document = new XWPFDocument(POIXMLDocument.openPackage(url));
            //获取表格对象集合
            List<XWPFTable> tables = document.getTables();

            StringBuffer tableText = new StringBuffer();
             Map<String,String> params = new HashMap<>();
            params.put("code",noStandardTestLog.getCode());
            if(noStandardTestLog.getDate() != null){
                params.put("date",format.format(noStandardTestLog.getDate()));
            }
            params.put("position",noStandardTestLog.getPosition());
            params.put("outlineCode",noStandardTestLog.getOutlineCode());
            params.put("testName",noStandardTestLog.getTestName());
            params.put("testManager",noStandardTestLog.getTestManager());
            params.put("otherUser",noStandardTestLog.getOtherUser());
            params.put("record",noStandardTestLog.getRecord());
            params.put("exProblem",noStandardTestLog.getExProblem());
            params.put("proStat",noStandardTestLog.getProStat());
            params.put("notekeeper",userDao.get(noStandardTestLog.getCreateBy().getId()).getName());
            params.put("auditEx",auditEx);
            params.put("disposal",auditStat);
            params.put("auditStat",auditStat);
            WordUtils.changeText(document, params);
            this.eachTable(tables, tableText,params);
            //解析替换文本段落对象
           /* WordUtils.changeText(document, params);
            XWPFTable stateTable = tables.get(0);
            List<XWPFTableRow> rows = stateTable.getRows();
            //遍历表格,并替换模板
            this.eachTable(rows, params);*/
            document.write(response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*/public  void eachTable(List<XWPFTableRow> rows ,Map<String, String> textMap){
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if(this.checkText(cell.getText())){
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        String  runStr= "";
                        for (XWPFRun run : runs) {
                            if(StringUtils.isNotBlank(run.toString())){
                                runStr+=run.toString();
                            }
                        }
                        XWPFRun xwpfRun = runs.get(0);
                        if(runStr.indexOf("$") > 1){
                            runStr = runStr.substring(runStr.indexOf("$"),runStr.length());
                        }
                        xwpfRun.setText(changeValue(xwpfRun,runStr, textMap),0);
                    }
                }
            }
        }
    }*/
    public  void eachTable(List<XWPFTable> tables , StringBuffer tableText,Map<String, String> textMap){
        for (XWPFTable xwpfTable : tables) {
            //获取表格行数据
            List<XWPFTableRow> rows = xwpfTable.getRows();
            for (XWPFTableRow xwpfTableRow : rows) {
                //获取表格单元格数据
                List<XWPFTableCell> cells = xwpfTableRow.getTableCells();
                for (XWPFTableCell xwpfTableCell : cells) {
                    if (this.checkText(xwpfTableCell.getText())) {
                        List<XWPFParagraph> paragraphs = xwpfTableCell.getParagraphs();
                        for (XWPFParagraph xwpfParagraph : paragraphs) {
                            List<XWPFRun> runs = xwpfParagraph.getRuns();
                            String runStr = "";
                            for (int i = 0; i < runs.size(); i++) {
                                XWPFRun run = runs.get(i);
                                if (StringUtils.isNotBlank(run.toString())) {
                                    runStr += run.toString();
                                }
                            }
                            int count = 0;
                            int sum = 0;
                            int a = 0;
                            int b = 0;
                            int c = 0;
                            int d = 0;
                            String [] strs = new String[runStr.length()];
                            for(int i = 0;i<strs.length;i++){
                                strs[i] = runStr.substring(i,i+1);
                            }
                            //挨个字符进行查找，查找到之后count++
                            for(int i = 0;i<strs.length;i++){
                                if(strs[i].equals("$")){

                                    if(count == 1){
                                         a = i;
                                    }else if(count == 0){
                                         b = i;
                                    }
                                    count++;
                                }
                                if(strs[i].equals("}")){

                                    if(sum == 1){
                                        c = i;
                                    }else if(sum == 0){
                                        d = i;
                                    }
                                    sum++;
                                }
                            }
                            if(count>=2){
                                String substring = runStr.substring(a, c+1);
                                String substring1 = runStr.substring(b, d+1);
                                for (int i = 0; i < runs.size(); i++) {
                                        XWPFRun run = runs.get(i);
                                        if(run.toString().indexOf("$")!=-1 ){
                                            if(i<4){
                                                run.setText(changeValue(run,substring1, textMap),0);
                                            }else{
                                                run.setText(changeValue(run,substring, textMap),0);
                                            }
                                        }else{
                                            run.setText(changeValue(run,substring, new HashMap<String, String>()),0);
                                        }

                                }
                            }else{
                                if(runStr.indexOf("$") > 1){
                                    runStr = runStr.substring(runStr.indexOf("$"),runStr.length());
                                }
                                if(runStr.indexOf("$") != -1){
                                    for (int i = 0; i < runs.size(); i++) {
                                        if(i==0){
                                            XWPFRun run = runs.get(i);
                                            run.setText(changeValue(run,runStr, textMap),0);
                                        }else{
                                            XWPFRun run = runs.get(i);
                                            run.setText(changeValue(run,runStr, new HashMap<String, String>()),0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static String changeValue(XWPFRun run,String value, Map<String, String> textMap){
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        for (Map.Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${"+textSet.getKey()+"}";
            if(value.indexOf(key)!= -1){
                value = textSet.getValue() == null ? "" : textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if(WordUtils.checkText(value)){
            value = "";
        }
        //判断是否需要换行
        if(value.contains("\r\n")){
            String[] lines = value.split("\r\n");
            for(int i= 1;i<lines.length;i++){
                run.addBreak();
                run.setText(lines[i],i);
            }
            value = lines[0];
        }
        return value;
    }

    public static boolean checkText(String text){
        boolean check  =  false;

        if(text.indexOf("$")!= -1 ){
            check = true;
        }
        return check;

    }
}
