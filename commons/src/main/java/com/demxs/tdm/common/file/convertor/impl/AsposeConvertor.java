package com.demxs.tdm.common.file.convertor.impl;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.demxs.tdm.common.file.exception.ConvertException;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: wuliepeng
 * Date: 2018-03-28
 * Time: 上午2:46
 */
public class AsposeConvertor implements DocConvertor {
    private static final Logger log = LoggerFactory.getLogger(OpenOfficeConvertor.class);

    @Override
    public void run(String inputFile, String targetFile) throws ConvertException {
        try {
            doc2pdf(inputFile,targetFile);
            log.info("成功转换: " + inputFile + "转换到" + targetFile);
        } catch (Exception e) {
            log.error("转换错误" + e + "/" + inputFile + "转换到" + targetFile);
            throw new ConvertException(e);
        } finally {
        }
    }

    private boolean getLicense() throws Exception {
        boolean result = false;
        try {
            InputStream is = AsposeConvertor.class.getClassLoader().getResourceAsStream("License.xml"); //  license
            //InputStream is = new FileInputStream("/Users/wuliepeng/work/longi/web/src/main/resources/License.xml");
            // .xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public void doc2pdf(String wordPath, String pdfPath) throws Exception {
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File(pdfPath);  //新建一个pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath);                    //Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            os.close();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            throw e;
        }
    }

    /*public static void main(String[] args) {
        AsposeConvertor convertor = new AsposeConvertor();
        convertor.run("/Users/wuliepeng/Downloads/20180308027.docx","/Users/wuliepeng/Downloads/20180308028.pdf");
    }*/
}
