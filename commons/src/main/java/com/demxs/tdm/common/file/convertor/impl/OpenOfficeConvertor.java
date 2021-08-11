package com.demxs.tdm.common.file.convertor.impl;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.exception.ConvertException;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import com.demxs.tdm.common.file.util.OpenOfficeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用OpenOffice转换office文档
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 下午1:58
 */
public class OpenOfficeConvertor implements DocConvertor {
    private static final Logger log = LoggerFactory.getLogger(OpenOfficeConvertor.class);


    private static boolean isStart = false;
    @Override
    public void run(String inputFile, String targetFile) throws ConvertException {

        try {
            OpenOfficeUtils.convert(inputFile,targetFile, Global.getConfig("openoffice.host"),Integer.valueOf(Global.getConfig("openoffice.port")));
            log.info("成功转换: " + inputFile + "转换到" + targetFile);
        } catch (Exception e) {
            log.error("转换错误" + e + "/" + inputFile + "转换到" + targetFile);
            throw new ConvertException(e);
        } finally {
        }
    }

    public static void main(String[] args) {
        try {
            OpenOfficeUtils.convert("/Users/wuliepeng/Downloads/123.docx","/Users/wuliepeng/Downloads/123.pdf","127.0.0.1",8100);
            log.info("成功转换");
        } catch (Exception e) {
            log.error("转换错误: " + e);
            throw new ConvertException(e);
        } finally {
        }
    }
}
