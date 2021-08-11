package com.demxs.tdm.common.file.impl;

import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.file.exception.DownloadException;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.FtpClientUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;

/**
 * @author wuhui
 * @date 2020/9/9 15:56
 **/
@Service("ftpStore")
public class FtpFileStoreImpl implements FileStore {
    private final static Log log = LogFactory.getLog(FtpFileStoreImpl.class);

    private String storeDir;//文件存储目录   约定以"/" 结尾

    @Override
    public boolean deleteFile(String path) {
        FtpClientUtil.deleteFile(this.getClient(),path);
        return false;
    }

    @Override
    public String saveFile(File file) {
        String filePath =   DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        InputStream fis = null;
        String fileName = file.getName();
        try {
            fis = new FileInputStream(file);
            FTPClient ftpClient = this.getClient();
            FtpClientUtil.uploadFile(ftpClient,fis,storeDir + filePath,fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        filePath += "/" + fileName;
        return filePath;
    }

    @Override
    public String saveFile(String fileName, byte[] bytes) {
        String filePath =  DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        InputStream fis = null;
        fis =  new ByteArrayInputStream(bytes);
        FTPClient ftpClient = this.getClient();
        FtpClientUtil.uploadFile(ftpClient,fis,storeDir + filePath,fileName);
        filePath += "/" + fileName;
        return filePath;
    }

    /**
     * @Describe:ftp文件存储
     * @Author:WuHui
     * @Date:18:14 2020/9/9
     * @param fileName
     * @param fis
     * @return:java.lang.String
    */
    @Override
    public String saveFile(String fileName, InputStream fis) {
        String filePath =  DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        FTPClient ftpClient = this.getClient();
        FtpClientUtil.uploadFile(ftpClient,fis,storeDir + filePath,fileName);
        filePath += "/" + fileName;
        return filePath;
    }

    @Override
    public String saveFileByPath(String filePath, byte[] bytes) {
        String path = filePath;
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        InputStream fis = null;
        fis =  new ByteArrayInputStream(bytes);
        FTPClient ftpClient = this.getClient();
        FtpClientUtil.uploadFile(ftpClient,fis,filePath,fileName);
        return filePath;
    }

    @Override
    public String copyFile(String srcPath) throws Exception {
        return null;
    }

    @Override
    public String downloadFile(String srcPath) throws DownloadException {
        return null;
    }

    @Override
    public byte[] downloadFileToBytes(String path) throws DownloadException {
        FTPClient client = this.getClient();
        InputStream is = FtpClientUtil.downloadFile(client,path);
        byte[] byts = FileUtils.inputStreamToByte(is);
        return byts ;
    }

    @Override
    public InputStream downloadFileToStream(String path) throws DownloadException {
        FTPClient client = this.getClient();
        InputStream is = FtpClientUtil.downloadFile(client,path);
        return is;
    }

    /**
     * @Describe:获取ftp客户端
     * @Author:WuHui
     * @Date:16:12 2020/9/9
     * @param
     * @return:FtpClient
    */
    private FTPClient getClient(){
        FTPClient client = FtpClientUtil.connection(FtpClientUtil.FILE);
        return client;
    }

    public String getStoreDir() {
        return storeDir;
    }

    public void setStoreDir(String storeDir) {
        this.storeDir = storeDir;
    }
}
