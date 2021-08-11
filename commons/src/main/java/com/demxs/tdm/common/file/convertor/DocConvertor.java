package com.demxs.tdm.common.file.convertor;


import com.demxs.tdm.common.file.exception.ConvertException;

/**
 * 文件转换接口
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:38
 */
public interface DocConvertor {

    public static Float defaultZoom = 0.8f;

    /**
     * 文档转换
     * @param inputFile 需要转换的文档
     * @param targetFile    目标文档
     */
    public abstract void run(String inputFile, String targetFile) throws ConvertException;
}
