package com.demxs.tdm.common.file.impl;

import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.file.exception.DownloadException;
import com.demxs.tdm.common.file.fast.pool.Pool;
import com.demxs.tdm.common.file.fast.pool.StorageClient;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * fastdfs文件存储
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class FastDfsFileStoreImpl implements FileStore {
    private final static Logger log = LoggerFactory.getLogger(FastDfsFileStoreImpl.class);

    private Pool fastdfsPool;
    private String groupName;

    @Override
    public String saveFile(File file) {
        if (file == null) {
            return null;
        }
        String path = "";
        try {
            byte[] fileBuff = getFileBuffer(file);
            String suffix = getFileExtName(file.getName());
            path = send(fileBuff, file.getName(),suffix);
            if (StringUtils.isNotEmpty(path) && file.exists()) {
                FileUtils.forceDelete(file);
            }
        } catch (Exception e) {
            log.error("upload file to fastdfs failed", e);
        }
        return path;
    }

    @Override
    public String saveFile(String fileName, byte[] bytes) {
        String path = "";
        try {
            String suffix = getFileExtName(fileName);
            path = send(bytes, fileName, suffix);
            if (StringUtils.isNotEmpty(path)) {
                log.info("upload file to fastdfs success");
            }
        } catch (Exception e) {
            log.error("upload file to fastdfs failed", e);
        }
        return path;
    }
    @Override
    public String saveFileByPath(String filePath, byte[] bytes) {
        String path = "";
        /*try {
            String suffix = getFileExtName(fileName);
            path = send(bytes, fileName, suffix);
            if (StringUtils.isNotEmpty(path)) {
                log.info("upload file to fastdfs success");
            }
        } catch (Exception e) {
            log.error("upload file to fastdfs failed", e);
        }*/
        return path;
    }
    @Override
    public boolean deleteFile(String path) {
        boolean b = false;
        try {
            b = deleteFileByPath(path);
        } catch (Exception e) {
            log.error("delete file from fast dfs failed,path={},{}", path, e);
        }
        //return b;
        return true;
    }

    private boolean deleteFileByPath(String path) throws IOException, Exception {
        boolean result = false;
        StorageClient client = null;
        try {
            client = fastdfsPool.getResource();
            result = client.delete_file(groupName, path) == 0 ? true : false;
            fastdfsPool.returnResource(client);
        } catch (Exception e) {
            // 发生io异常等其它异常，默认删除这次连接重新申请
            log.error("FastDfs:deleteFileByPath throw:", e);
            if (client != null) {
                fastdfsPool.returnBrokenResource(client);
            }
            throw e;
        }
        return result;
    }

    private boolean deleteFileByFileId(String fileId) throws IOException, Exception {
        boolean result = false;
        StorageClient client = null;
        try {
            client = fastdfsPool.getResource();
            result = client.delete_file1(fileId) == 0 ? true : false;
            fastdfsPool.returnResource(client);
        } catch (Exception e) {
            // 发生io异常等其它异常，默认删除这次连接重新申请
            log.error("ImageServerImpl execution deleteFile throw:", e);
            if (client != null) {
                fastdfsPool.returnBrokenResource(client);
            }
            throw e;
        }
        return result;
    }

    @Override
    public String copyFile(String path) throws Exception {
        String upPath = null;
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        StorageClient client = null;
        try {
            client = fastdfsPool.getResource();
            byte[] downFile = client.download_file(groupName,path);
            if(downFile!=null && downFile.length > 0){
                upPath = client.upload_file1(groupName,downFile,getFileExtName(path),null);
                if(StringUtils.isNotEmpty(upPath)){
                    upPath = StringUtils.replace(upPath,groupName+"/","");
                }
            }
            fastdfsPool.returnResource(client);
        } catch (Exception e) {
            // 发生io异常等其它异常，默认删除这次连接重新申请
            log.error("FastDfsFileStoreImpl execution send throw :", e);
            if (client != null) {
                fastdfsPool.returnBrokenResource(client);
            }
            throw e;
        }
        return upPath;
    }

    @Override
    public byte[] downloadFileToBytes(String path) throws DownloadException {
        byte[] downFile = null;
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        StorageClient client = null;
        try {
            client = fastdfsPool.getResource();
            downFile = client.download_file(groupName,path);
            fastdfsPool.returnResource(client);
        } catch (Exception e) {
            // 发生io异常等其它异常，默认删除这次连接重新申请
            log.error("FastDfsFileStoreImpl execution send throw :", e);
            if (client != null) {
                try {
                    fastdfsPool.returnBrokenResource(client);
                } catch (Exception e1) {
                    throw new DownloadException(e1);
                }
            }
            throw new DownloadException(e);
        }
        return downFile;
    }

    @Override
    public InputStream downloadFileToStream(String path) throws DownloadException {
        return null;
    }

    @Override
    public String downloadFile(String path) throws DownloadException {
        String downPath = null;
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        try {
            byte[] downFile = this.downloadFileToBytes(path);
            if(downFile!=null && downFile.length > 0) {
                File file = FileUtils.createTempFile(FileUtils.getFileExtension(path));
                OutputStream out = new FileOutputStream(file);
                IOUtils.write(downFile, out);
                downPath = file.getAbsolutePath();
                out.close();
            }
        } catch (Exception e) {
            // 发生io异常等其它异常，默认删除这次连接重新申请
            log.error("FastDfsFileStoreImpl execution send throw :", e);
            throw new DownloadException(e);
        }
        return downPath;
    }

    private String send(byte[] fileBuff , String fileName, String fileExtName) throws IOException, Exception {
        String upPath = null;
        if (fileBuff == null) {
            return null;
        }
        StorageClient client = null;
        try {
            client = fastdfsPool.getResource();
            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("fileName", fileName);
            metaList[1] = new NameValuePair("fileExtName", fileExtName);
            metaList[2] = new NameValuePair("fileLength", String.valueOf(fileBuff.length));
            upPath = client.upload_file1(groupName, fileBuff, fileExtName, metaList);
            if(StringUtils.isNotEmpty(upPath)){
                upPath = StringUtils.replace(upPath,groupName+"/","");
            }
            fastdfsPool.returnResource(client);
        } catch (Exception e) {
            // 发生io异常等其它异常，默认删除这次连接重新申请
            log.error("FastDfsFileStoreImpl execution send throw :", e);
            if (client != null) {
                fastdfsPool.returnBrokenResource(client);
            }
            throw e;
        }
        return upPath;
    }

    private String getFileExtName(String name) {
        String extName = null;
        if (name != null && name.contains(".")) {
            extName = name.substring(name.lastIndexOf(".") + 1);
        }
        return extName;
    }

    private byte[] getFileBuffer(File file) {
        byte[] fileByte = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fileByte = new byte[fis.available()];
            fis.read(fileByte);
        } catch (FileNotFoundException e) {
            log.error("FastDfsFileStoreImpl  read file   throw :", e);
        } catch (IOException e) {
            log.error("FastDfsFileStoreImpl  read file   throw :", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                fis = null;
            }
        }
        return fileByte;
    }

    public void setFastdfsPool(Pool fastdfsPool) {
        this.fastdfsPool = fastdfsPool;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String saveFile(String fileName, InputStream fis) {
        return null;
    }
}
