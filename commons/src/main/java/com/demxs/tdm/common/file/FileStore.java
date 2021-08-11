package com.demxs.tdm.common.file;


import com.demxs.tdm.common.file.exception.DownloadException;

import java.io.File;
import java.io.InputStream;

/**
 * 文件存储
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public interface FileStore {
    /***
     * 删除文件
     * @param path 文件保存路径
     * @return
     */
    boolean deleteFile(String path);

    /**
     * 保存文件
     *
     * @param file 文件
     * @return 返回oss存储路径
     */
    String saveFile(File file);

    /**
     * 保存文件
     *
     * @param bytes 文件字节流
     * @return
     */
    String saveFile(String fileName, byte[] bytes);


    /**
     * @Describe:文件字节流存储
     * @Author:WuHui
     * @Date:11:09 2020/9/22
     * @param fileName
     * @param fis
     * @return:java.lang.String
     */
    String saveFile(String fileName, InputStream fis);
    /**
     * 原目录下保存文件
     *
     * @param bytes 文件字节流
     * @return
     */
    String saveFileByPath(String filePath, byte[] bytes);

    /***
     * 拷贝文件
     * @param srcPath  源文件路径
     * @return
     */
    String copyFile(String srcPath) throws Exception;


    /**
     * 下载文件
     *
     * @param path 文档地址
     */
    String downloadFile(String path) throws DownloadException;

    /**
     * 下载文件
     *
     * @param path 文档地址
     */
    byte[] downloadFileToBytes(String path) throws DownloadException;


    InputStream downloadFileToStream(String path) throws DownloadException;

}


