package com.demxs.tdm.common.file.impl;

import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.file.exception.DownloadException;
import com.demxs.tdm.common.utils.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 本地文档存储
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class LocalFileStoreImpl implements FileStore {
    private final static Log log = LogFactory.getLog(LocalFileStoreImpl.class);
    private String storeDir;//文件存储目录   约定以"/" 结尾

    @Override
    public String copyFile(String srcPath) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String saveFile(File file) {
        String newName = file.getName();
//        //上传文件的扩展名
//        String oExt = FilenameUtils.getExtension(file.getName());
//        //校验新文件名是否带扩展名
//        String nExt = FilenameUtils.getExtension(newName);
//        if(StringUtils.isNotEmpty(oExt) && StringUtils.isEmpty(nExt)){
//            //原文件有扩展名  新文件名无扩展名  需要将新文件加上扩展名
//            newName = newName + "."+oExt;
//        }
        String newFilePath = getNewFilePath(newName);
        File newFile = new File(storeDir+newFilePath);
        try {
            FileUtils.copyFile(file,newFile);
            FileUtils.forceDelete(file);
        } catch (Exception e) {
            log.error("save File error,fileName="+file.getName(),e);
            return null;
        }
        return newFilePath;
    }

    @Override
    public String saveFile(String fileName, byte[] bytes) {
        String newFilePath = getNewFilePath(fileName);
        File newFile = new File(storeDir+newFilePath);
        try {
            FileUtils.writeByteArrayToFile(newFile,bytes);
        } catch (Exception e) {
            log.error("save File error,fileName="+fileName,e);
            return null;
        }
        return newFilePath;
    }
    @Override
    public String saveFileByPath(String filePath, byte[] bytes) {
      //  String newFilePath = getNewFilePath(fileName);
        File newFile = new File(storeDir+filePath);
        try {
            FileUtils.writeByteArrayToFile(newFile,bytes);
        } catch (Exception e) {
            log.error("save File error,fileName="+filePath,e);
            return null;
        }
        return filePath;
    }
    @Override
    public boolean deleteFile(String path) {
        try {
            FileUtils.forceDeleteOnExit(new File(storeDir+path));
        } catch (IOException e) {
            log.error("delete file error filePath="+path,e);
            return false;
        }
        return true;
    }
    // 文件目录存储规划:  文件类型/日期/文件
    // 文件目录存储规划:  文件类型/日期/文件
    private static String getNewFilePath(String newName) {
        /*String dir = "";
        switch (fileType){
            case FileType.IMAGE:break;
            case FileType.UPGRADE_PACK:dir="pack";break;
            case FileType.ATTACHED_FILE:dir="attach";break;
            case FileType.TEMP_FILE:dir="temp";break;
        }
        if(StringUtils.isNotEmpty(dir)){
            return "temp/" + DateFormatUtils.format(new Date(), "yyyyMMdd")+"/"+newName;
        }*/
        //DateUtils.getDateTime()
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+"/"+""+newName;
    }

    public void setStoreDir(String storeDir) {
        this.storeDir = storeDir;
    }

    @Override
    public String downloadFile(String path) throws DownloadException {
        path = path.indexOf(storeDir)>=0?path:(storeDir+path);
        /*File file = FileUtils.getFile(path);
        String newFilePath = getNewFilePath(file.getName());
        File newFile = new File(storeDir+newFilePath);
        try {
            FileUtils.copyFile(file,newFile);
        } catch (Exception e) {
            log.error("download File error,fileName="+file.getName(),e);
            throw  new DownloadException(e);
        }
        return newFilePath;*/
        return path;
    }

    @Override
    public byte[] downloadFileToBytes(String path) throws DownloadException {
        byte[] bytes = new byte[0];
        File file = FileUtils.getFile(storeDir+path);
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            log.error("download File error,fileName="+file.getName(),e);
            throw new DownloadException(e);
        }
        return bytes;
    }

    public LocalFileStoreImpl(String storeDir) {
        this.storeDir = storeDir;
    }

    @Override
    public String saveFile(String fileName, InputStream fis) {
        return null;
    }

    @Override
    public InputStream downloadFileToStream(String path) throws DownloadException {
        return null;
    }
}
