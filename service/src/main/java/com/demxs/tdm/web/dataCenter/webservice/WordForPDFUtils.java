package com.demxs.tdm.web.dataCenter.webservice;


import com.aspose.cells.*;
import com.aspose.words.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public  class WordForPDFUtils {

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = WordForPDFUtils.class.getClassLoader().getResourceAsStream("template/license.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void excel2pdf(String Address) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            File pdfFile = new File("C:\\Users\\Wayne\\Desktop\\test.pdf");// 输出路径
            Workbook wb = new Workbook(Address);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File excel2pdf(String excelPath,String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return null;
        }
        PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
        FileOutputStream fileOS = null;
        try {
            File pdfFile = new File(pdfPath);// 输出路径
            Workbook wb = new Workbook(excelPath);// 原始excel路径
            Worksheet ws = wb.getWorksheets().get(0);
            HorizontalPageBreakCollection p1=ws.getHorizontalPageBreaks();
            fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS, SaveFormat.PDF);
            return new File(pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileOS!=null) {
                try {
                    fileOS.close();
                } catch (IOException e) {
                    //nothing
                }
            }
        }
        return null;
    }


    public static File word2pdf(String docPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return null;
        }
        FileOutputStream fileOS = null;
        try {
            File file = new File(pdfPath); // 新建一个空白pdf文档
            fileOS = new FileOutputStream(file);
            Document doc = new Document(docPath); // Address是将要被转化的word文档
            doc.save(fileOS, com.aspose.words.SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            return new File(pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileOS!=null) {
                try {
                    fileOS.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
