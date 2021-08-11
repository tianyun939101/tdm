package com.demxs.tdm.common.file.convertor.impl;


import com.demxs.tdm.common.file.exception.ConvertException;
import com.demxs.tdm.common.utils.CompressFile;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 压缩文件转换
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 下午2:43
 */
public class ZipRarToHtmlConvertor implements DocConvertor {
    private static final Logger log = LoggerFactory.getLogger(ZipRarToHtmlConvertor.class);
    @Override
    public void run(String inputFile, String targetFile) throws ConvertException {
        try {
            log.debug("解压文件" + inputFile);
            log.debug("解压目录" + targetFile);
            deCompress(inputFile, FileUtils.getFileExtension(inputFile),targetFile);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ConvertException(e.getMessage());
        }

    }

    public static void deCompress(String sourceFile,String fileType,String destDir) throws Exception{
        // 保证文件夹路径最后是"/"或者"\"
        char lastChar = destDir.charAt(destDir.length() - 1);
        if(lastChar != '/' && lastChar != '\\'){
            destDir += File.separator;
        }
        //根据类型,进行相应的解压缩
        String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);
        if (fileType != null){
            type = fileType;
        }
        if ("zip".equalsIgnoreCase(type)){
            CompressFile.unZipFiles(new File(sourceFile), destDir);
        } else if("rar".equalsIgnoreCase(type)){
            CompressFile.unRarFile(sourceFile,destDir);
        } else {
            throw new Exception("只支持zip和rar格式的压缩包!");
        }
    }
}
