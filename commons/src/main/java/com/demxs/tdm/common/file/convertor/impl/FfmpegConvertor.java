package com.demxs.tdm.common.file.convertor.impl;


import com.demxs.tdm.common.file.exception.ConvertException;
import com.demxs.tdm.common.file.convertor.DocConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 视频文件转换,将所有上传的视频文件都转换为flv的文件格式
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 下午3:06
 */
public class FfmpegConvertor implements DocConvertor {
    private static final Logger log = LoggerFactory.getLogger(FfmpegConvertor.class);

    @Override
    public void run(String inputF, String targetFile) throws ConvertException {
        // // TODO: 17/7/31 ffmpg文件转换 
    }
}
