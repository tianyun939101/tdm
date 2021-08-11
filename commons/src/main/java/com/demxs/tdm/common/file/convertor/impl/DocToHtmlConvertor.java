package com.demxs.tdm.common.file.convertor.impl;


import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.exception.ConvertException;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import com.demxs.tdm.common.file.util.OpenOfficeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文档转换为html
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class DocToHtmlConvertor implements DocConvertor {
    private static final Logger log = LoggerFactory.getLogger(DocToHtmlConvertor.class);

    @Override
    public void run(String inputFile, String targetFile) throws ConvertException {
        try {
            OpenOfficeUtils.convert(inputFile,targetFile, Global.getConfig("openOffice.host"),Integer.valueOf(Global.getConfig("openOffice.port")));
            log.info("成功转换: " + inputFile + "转换到" + targetFile);
            log.info("成功转换: " + inputFile + "转换到" + targetFile);

        }catch (Exception e){
            log.error("转换错误"+ e + "/" + inputFile + "转换到" + targetFile);
            throw new ConvertException(e);
        }finally {

        }
    }
}
