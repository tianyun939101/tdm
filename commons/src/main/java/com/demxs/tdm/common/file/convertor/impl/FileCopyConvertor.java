package com.demxs.tdm.common.file.convertor.impl;


import com.demxs.tdm.common.file.exception.ConvertException;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文档复制转换
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 下午1:53
 */
public class FileCopyConvertor implements DocConvertor {
    private static final Logger log = LoggerFactory.getLogger(FileCopyConvertor.class);

    @Override
    public void run(String inputFile, String targetFile) throws ConvertException {
        try{
            FileUtils.copyFile(inputFile,targetFile);
        } catch (Exception e){
            throw new ConvertException(e);
        }
    }
}
