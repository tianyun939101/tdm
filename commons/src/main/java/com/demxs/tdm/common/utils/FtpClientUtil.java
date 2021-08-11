package com.demxs.tdm.common.utils;


import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.FtpConfigDao;
import com.demxs.tdm.common.sys.entity.FtpConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Jason
 * @Date: 2020/5/12 14:26
 * @Description: ftp连接工具
 */
public class FtpClientUtil {

    public static final int DEFAULT = 0;
    public static final int YL_1 = 1;
    public static final int YL_2 = 2;
    public static final int YL_3 = 3;
    public static final int DY = 4;
    public static final int SH = 5;
    public static final int FILE = 6;

    private static final int DEFAULT_COLLECTION_SIZE = 200000;
    private static final Integer DEPTH = Integer.parseInt(Global.getConfig("pr.flightData.directory.depth"));
    public static final String TYPE = "ftpFile";
    private final static FtpConfigDao FTP_CONFIG_DAO = SpringContextHolder.getBean(FtpConfigDao.class);
    private static final Map<Integer, FtpConfig> CONFIG_MAP = new ConcurrentHashMap<>(16);

    static {
        List<FtpConfig> list = FTP_CONFIG_DAO.findList(new FtpConfig());
        if(null != list){
            for (FtpConfig ftpConfig : list) {
                CONFIG_MAP.put(Integer.parseInt(ftpConfig.getType()),ftpConfig);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/7/17 15:06
    * @params [type]
    * 获取配置
    * @return com.demxs.tdm.common.sys.entity.FtpConfig
    */
    public static FtpConfig getConfig(Integer type){
        if(CONFIG_MAP.isEmpty()){
            List<FtpConfig> list = FTP_CONFIG_DAO.findList(new FtpConfig());
            FtpConfig config = null;
            if(null != list){
                for (FtpConfig ftpConfig : list) {
                    CONFIG_MAP.put(Integer.parseInt(ftpConfig.getType()),ftpConfig);
                    if(null != type && ftpConfig.getType().equals(type.toString())){
                        config = ftpConfig;
                    }
                }
            }
            return config;
        }else{
            return CONFIG_MAP.get(type);
        }
    }

    public static void clearCache(){
        CONFIG_MAP.clear();
    }

    /**
    * @author Jason
    * @date 2020/5/12 14:44
    * @params []
    * 建立ftp连接，返回一个ftp客户端对象
    * @return org.apache.commons.net.ftp.FTPClient
    */
    public static FTPClient connection(){
        return connection(DEFAULT);
    }

    /**
    * @author Jason
    * @date 2020/6/15 10:26
    * @params [type]
    * 重载方法
    * @return org.apache.commons.net.ftp.FTPClient
    */
    public static FTPClient connection(int type){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setConnectTimeout(10 * 1000);
        FtpConfig ftpConfig = getConfig(type);
        if(null == ftpConfig) {
            return null;
        }
        try {
            //连接FTP服务器
            ftpClient.connect(ftpConfig.getAddress(), ftpConfig.getPort());
            //登录FTP服务器
            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            //是否成功登录FTP服务器
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("*****************login failed*******************");
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return ftpClient;
    }

    /**
    * @author Jason
    * @date 2020/6/15 10:26
    * @params [filePath, fileName, os]
    * 重载方法
    * @return java.util.Map<java.lang.String,java.lang.String>
    */
    public static Map<String,String> downFile(String filePath, String fileName, OutputStream os){
        return downFile(DEFAULT,filePath,fileName,os);
    }

    public static Map<String,String> downFile(int type,String filePath, String fileName, OutputStream os){
        FTPClient client = connection(type);
        return downFile(client,filePath,fileName,os);
    }

    /**
    * @author Jason
    * @date 2020/5/12 14:47
    * @params [filePath, fileName, os]
    * 下载文件
    * @return void
    */
    public static Map<String,String> downFile(FTPClient ftpClient,String filePath, String fileName, OutputStream os){
        if(null != ftpClient){
            try {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                boolean changed = ftpClient.changeWorkingDirectory("/");
                if(changed){
                    if(os instanceof ServletOutputStream){
                        System.out.println("################################download..."+filePath + "/" + fileName+"################################");
                        InputStream is = ftpClient.retrieveFileStream(filePath + "/" + fileName);
                        if(null != is){
                            IOUtil.writeByteToFile(is,os);
                            return ResultMapUtil.success();
                        }else{
                            return ResultMapUtil.error("文件不存在！！");
                        }
                    }else {
                        boolean result = ftpClient.retrieveFile(filePath + "/" + fileName, os);
                        if(result){
                            return ResultMapUtil.success();
                        }else{
                            return ResultMapUtil.failed();
                        }
                    }
                }else {
                    return ResultMapUtil.error("文件不存在！！");
                }
            }catch (IOException e){
                return ResultMapUtil.error(e.getMessage());
            }finally {
                logout(ftpClient);
            }
        }else{
            return ResultMapUtil.error("连接失败");
        }
    }

    public static InputStream downloadFile(FTPClient ftpClient,String path){
        int index = path.lastIndexOf("/");
        String fileName = path.substring(index+1,path.length());
        String filePath = path.substring(0,index);
        return FtpClientUtil.downloadFile(ftpClient,filePath,fileName);
    }

    public static InputStream downloadFile(FTPClient ftpClient,String filePath,String fileName){
        InputStream is;
        if(null != ftpClient){
            try {
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                boolean changed = ftpClient.changeWorkingDirectory(filePath);
                if(changed){
                    is = ftpClient.retrieveFileStream(filePath + "/" + fileName);
                }else {
                    throw new ServiceException("文件不存在！！");
                }
            }catch (IOException e){
                throw new ServiceException("FTP 获取文件失败！");
            }finally {
                logout(ftpClient);
            }
        }else{
            throw new ServiceException("FTP客户端连接失败！");
        }
        return is;
    }
    /**
    * @author Jason
    * @date 2020/7/14 10:43
    * @params [type]
    * 获取根目录部分文件
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public static List<Map<String,String>> getDirList(int type){
        return getDirList(type,DEPTH);
    }

    /**
    * @author Jason
    * @date 2020/6/15 14:49
    * @params [type]
    * 获取根目录部分文件
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public static List<Map<String,String>> getDirList(int type,int depth){
        FTPClient ftpClient = null;
        try {
            ftpClient = connection(type);
            assert ftpClient != null;
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return getDirList(type,ftpClient,new ArrayList<>(DEFAULT_COLLECTION_SIZE),"/",depth,0);
    }

    /**
    * @author Jason
    * @date 2020/6/15 14:49
    * @params [ftpClient, list, dirPath]
    * 递归获取
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public static List<Map<String,String>> getDirList(int type,FTPClient ftpClient,List<Map<String,String>> list,String dirPath,int depth,int curDepth){
        try {
            if(curDepth < depth){
                boolean changed = ftpClient.changeWorkingDirectory(dirPath);
                if(changed){
                    FTPFile[] ftpFiles = ftpClient.listDirectories();
                    if(null != ftpFiles){
                        for (FTPFile ftpFile : ftpFiles) {
                            if(ftpFile.isDirectory()){
                                Map<String, String> map = new HashMap<>(3);
                                map.put("id",type + dirPath+ftpFile.getName()+"/");
                                map.put("name",ftpFile.getName());
                                map.put("pId",type + dirPath);
                                map.put("fileType",TYPE);
                                list.add(map);
                                int tempCurDepth = curDepth + 1;
                                list.addAll(getDirList(type,ftpClient,new ArrayList<>(),dirPath+ftpFile.getName()+"/",depth,tempCurDepth));
                            }
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
    * @author Jason
    * @date 2020/6/15 14:51
    * @params [type, path]
    * 获取指定目录下文件
    * @return org.apache.commons.net.ftp.FTPFile[]
    */
    public static List<FTPFile> getFiles(int type,String path){
        return getFiles(type,path,null);
    }

    public static List<FTPFile> getFiles(int type,String path,String keyWord){
        return getFiles(Objects.requireNonNull(connection(type)),path,keyWord);
    }

    /**
    * @author Jason
    * @date 2020/6/15 14:49
    * @params [ftpClient, path]
    * 获取指定目录下文件
    * @return org.apache.commons.net.ftp.FTPFile[]
    */
    public static List<FTPFile> getFiles(FTPClient ftpClient,String path,String keyWord){
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean changed = ftpClient.changeWorkingDirectory(path);
            if(changed){
                List<FTPFile> list = new ArrayList<>();
                for (FTPFile ftpFile : ftpClient.listFiles()) {
                    if(ftpFile.isFile()){
                        if(StringUtils.isNotBlank(keyWord)){
                            //模糊查询
                            if(ftpFile.getName().contains(keyWord) || keyWord.contains(ftpFile.getName())){
                                list.add(ftpFile);
                            }
                        }else{
                            list.add(ftpFile);
                        }
                    }
                }
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            logout(ftpClient);
        }
        return null;
    }

    /**
    * @author Jason
    * @date 2020/6/15 16:15
    * @params [type, path, name]
    * 根据目录名和文件名获取文件
    * @return org.apache.commons.net.ftp.FTPFile
    */
    public static FTPFile getFile(int type,String path,String name){
        return getFile(Objects.requireNonNull(connection(type)),path,name);
    }

    /**
    * @author Jason
    * @date 2020/6/15 16:16
    * @params [ftpClient, path, name]
    * 根据目录名和文件名获取文件,以type为命名空间根目录
    * @return org.apache.commons.net.ftp.FTPFile
    */
    public static FTPFile getFile(FTPClient ftpClient,String path,String name){
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            boolean changed = ftpClient.changeWorkingDirectory(path);
            if(changed){
                FTPFile[] ftpFiles = ftpClient.listFiles();
                if(null != ftpFiles){
                    for (FTPFile ftpFile : ftpFiles) {
                        if(name.equals(ftpFile.getName())){
                            return ftpFile;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            logout(ftpClient);
        }
        return null;
    }

    /**
    * @author Jason
    * @date 2020/6/16 10:36
    * @params [type, path, name]
    * 根据目录名和文件名获取多个文件
    * @return org.apache.commons.net.ftp.FTPFile
    */
    public static List<FTPFile> getFile(int type,String path,String ...name){
        return getFile(type,Objects.requireNonNull(connection(type)),path,name);
    }

    /**
     * @author Jason
     * @date 2020/6/16 10:36
     * @params [type, path, name]
     * 获取同一目录下的多个文件,以type为命名空间根目录
     * @return org.apache.commons.net.ftp.FTPFile
     */
    public static List<FTPFile> getFile(int type,FTPClient ftpClient,String path,String ...name){
        List<FTPFile> list = new ArrayList<>();
        try {
            if(null != name && name.length > 0){
                List<String> nameList  = new ArrayList<>(Arrays.asList(name));
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                boolean changed = ftpClient.changeWorkingDirectory(path);
                if(changed){
                    FTPFile[] ftpFiles = ftpClient.listFiles();
                    if(null != ftpFiles){
                        for (FTPFile ftpFile : ftpFiles) {
                            if(nameList.contains(ftpFile.getName())){
                                ftpFile.setName(type + ftpFile.getName());
                                list.add(ftpFile);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            logout(ftpClient);
        }
        return list;
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:28
    * @params [type, path, fileName]
    * 判断ftp服务器是否存在该文件
    * @return boolean
    */
    public static boolean fileExist(int type, String path, String fileName){
        return fileExist(connection(type),path,fileName);
    }

    /**
     * @author Jason
     * @date 2020/7/17 13:28
     * @params [type, path, fileName]
     * 判断ftp服务器是否存在该文件
     * @return boolean
     */
    public static boolean fileExist(FTPClient ftpClient,String path,String fileName){
        try {
            if (null != ftpClient) {
                boolean changed = ftpClient.changeWorkingDirectory(path);
                if (changed) {
                    FTPFile[] ftpFiles = ftpClient.listFiles();
                    if (null != ftpFiles) {
                        for (FTPFile ftpFile : ftpFiles) {
                            if (fileName.equals(ftpFile.getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(null != ftpClient){
                logout(ftpClient);
            }
        }
    }

    /**
    * @author Jason
    * @date 2020/6/16 14:53
    * @params [ftpClient]
     * ftp登出方法
    * @return void
    */
    public static void logout(FTPClient ftpClient){
        int replyCode = ftpClient.getReplyCode();
        if(FTPReply.isPositiveCompletion(replyCode)){
            try {
                ftpClient.logout();
            }catch (Exception e){
            }
        }
    }

    public static void uploadFile(FTPClient ftpClient,InputStream fis,String path,String fileName){
        try {
            //更改工作空间（就是设定路径）
            if(!ftpClient.changeWorkingDirectory(path)){
                //生成路径（一定要生成路径，不然如果路径不存在的话，怎么传都只会传到根目录下）
                ftpClient.makeDirectory(path);
                ftpClient.changeWorkingDirectory(path);
            }
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("Utf-8");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //参数1是文件名（一定要后缀）
            ftpClient.storeFile(fileName, fis);

        } catch (Exception e) {
            throw new ServiceException("FTP 文件传输异常",e.getCause());
        } finally{
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new ServiceException("FTP 文件传输异常",e.getCause());
            }
        }
    }

    public static boolean deleteFile(FTPClient ftpClient,String path){
        int index = path.lastIndexOf("/");
        String fileName = path.substring(index+1,path.length());
        String filePath = path.substring(0,index);
        return FtpClientUtil.deleteFile(ftpClient,filePath,fileName);
    }

    public static boolean  deleteFile(FTPClient ftpClient,String pathname, String filename) {
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

}
