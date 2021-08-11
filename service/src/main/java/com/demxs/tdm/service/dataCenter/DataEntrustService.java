package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.DataEntrustDao;
import com.demxs.tdm.domain.dataCenter.DataEntrust;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataEntrustService extends  CrudService<DataEntrustDao, DataEntrust> {

    @Autowired
    DataEntrustDao dataEntrustDao;



    public Page<DataEntrust> list(Page<DataEntrust> page, DataEntrust entity) {
        Page<DataEntrust> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<DataEntrust> findPage(Page<DataEntrust> page, DataEntrust entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public DataEntrust get(String id) {
        DataEntrust equipment = super.dao.get(id);
        return equipment;
    }


    public void save(DataEntrust entity) {
        super.save(entity);

    }

    @Async(value = "asyncExecutor")
    public  boolean  sendMail(InputStream is){

        String fromMail= Global.getConfig("mail.from");
        String  host=Global.getConfig("mail.host");
        int  port=Integer.parseInt(Global.getConfig("mail.port"));
        String  pwd=Global.getConfig("mail.password");
       /* String fromMail="1370161936@qq.com";
        String  host="smtp.qq.com";
        int  port=587;
        String  pwd="aytmztiprrhjgfcb";*/
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        MimeMessage message = new MimeMessage(session);
        try{

            message.setFrom(new InternetAddress(fromMail, "委托单信息"));
            //发送给谁
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("2280771127@qq.com"));
            message.setSubject("委托单信息");
            message.addHeader("charset", "UTF-8");

            /*添加正文内容*/
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("您收到一份委托信息请查收!");

            contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
            multipart.addBodyPart(contentPart);

            /*添加附件*/
            MimeBodyPart fileBody = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(is, "application/msexcel");
            fileBody.setDataHandler(new DataHandler(source));
            String fileName  = "委托单.xls";
            // 中文乱码问题
            fileBody.setFileName(MimeUtility.encodeText(fileName));
            multipart.addBodyPart(fileBody);

            message.setContent(multipart);
            message.setSentDate(new Date());
            message.saveChanges();
            Transport transport = session.getTransport("smtp");

            transport.connect(host, port, fromMail, pwd);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (Exception  e){
            e.printStackTrace();
        }
        return true;

    }


    public  Workbook   getWebWork(DataEntrust dataEntrust, HttpServletRequest request){

        Workbook workBook= null;
        try {
            String url=DataEntrustService.class.getClassLoader().getResource("template/111.xlsx").getPath();
           InputStream in = new FileInputStream(url);
          // File file=new File(url);
          //  BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(url)));
           workBook =new XSSFWorkbook(OPCPackage.open(in));
            Sheet sheet=workBook.getSheetAt(0);
            int rowIndex =1;
                Row row=sheet.createRow(rowIndex);
                if(StringUtils.isNotEmpty(dataEntrust.getEntrustName())){
                    row.createCell(0).setCellValue(dataEntrust.getEntrustName());
                }else{
                    row.createCell(0).setCellValue("");
                }
                if(StringUtils.isNotEmpty(dataEntrust.getProductName())){
                    row.createCell(1).setCellValue(dataEntrust.getProductName());
                }else{
                    row.createCell(1).setCellValue("");
                }
                if(StringUtils.isNotEmpty(dataEntrust.getContactName())){
                    row.createCell(2).setCellValue(dataEntrust.getContactName());
                }else{
                    row.createCell(2).setCellValue("");
                }
                if(StringUtils.isNotEmpty(dataEntrust.getPhone())){
                    row.createCell(3).setCellValue(dataEntrust.getPhone());
                }else{
                    row.createCell(3).setCellValue("");
                }
                if(StringUtils.isNotEmpty(dataEntrust.getAddress())){
                    row.createCell(4).setCellValue(dataEntrust.getAddress());
                }else{
                    row.createCell(4).setCellValue("");
                }
                if(StringUtils.isNotEmpty(dataEntrust.getRemarks())){
                    row.createCell(5).setCellValue(dataEntrust.getRemarks());
                }else{
                    row.createCell(5).setCellValue("");
                }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return workBook;

    }

    public static Workbook create(InputStream in){
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        Workbook  work=null;
        try{
            if (POIFSFileSystem.hasPOIFSHeader(in)) {
                work= new HSSFWorkbook(in);
            }
            if (POIXMLDocument.hasOOXMLHeader(in)) {
                work= new XSSFWorkbook(OPCPackage.open(in));
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
     return work;
    }
}
