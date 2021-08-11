package com.demxs.tdm.comac.common.jdbc.utils;

import jcifs.smb.*;

import java.io.*;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yangyouxing
 * date on 2017/7/31.
 */
public final class SambaUtils {

    public SambaUtils() {}

    /**
     * 从samba服务器上下载指定的文件到本地目录
     * @param remoteSmbFile   Samba服务器远程文件
     * @throws SmbException
     */

    public static void dirAll(SmbFile remoteSmbFile, List<Map<String,Object>> allFile) throws SmbException {

        if (!remoteSmbFile.exists()) {
            System.out.println("Samba服务器远程文件不存在");
            return;
        }

        SmbFile[] smbFiles = remoteSmbFile.listFiles();
        for(SmbFile smbFile:smbFiles){
            Map map = new HashMap();
            if(smbFile.isDirectory()){
                dirAll(smbFile,allFile);
                //downloadFileFromSamba(smbFile,localDir + File.separator + smbFile.getName());
            }else{

                // 如果遇到文件夹则放入数组
                if (remoteSmbFile.getPath().endsWith(File.separator)) {
                    //dirAllStrArr.add(dirFile.getPath() + file.getName());
                    //dirAllStrArr.add(file.getName());
                    map.put("fileDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(smbFile.lastModified())));
                    map.put("fileName",smbFile.getName());
                    map.put("filePath",remoteSmbFile.getPath() + smbFile.getName());
                    allFile.add(map);
                } else {
                       /* dirAllStrArr.add(dirFile.getPath() + File.separator
                                + file.getName());*/
                    //dirAllStrArr.add(file.getName());
                    map.put("fileDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(smbFile.lastModified())));
                    map.put("fileName",smbFile.getName());
                    map.put("filePath",remoteSmbFile.getPath() + File.separator
                            + smbFile.getName());
                    allFile.add(map);
                }

               /* InputStream in = null;
                OutputStream out = null;

                try{
                    //获取远程文件的文件名,这个的目的是为了在本地的目录下创建一个同名文件
                    String remoteSmbFileName = smbFile.getName();

                    //本地文件由本地目录，路径分隔符，文件名拼接而成
                    File localFile = new File(localDir + File.separator + remoteSmbFileName);

                    // 如果路径不存在,则创建
                    File parentFile = localFile.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }

                    //打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
                    in = new BufferedInputStream(new SmbFileInputStream(smbFile));
                    //打开文件输出流，指向新创建的本地文件，作为最终复制到的目的地
                    out = new BufferedOutputStream(new FileOutputStream(localFile));

                    //缓冲内存
                    byte[] buffer = new byte[1024];
                    while (in.read(buffer) != -1){
                        out.write(buffer);
                        buffer = new byte[1024];
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try  {
                        out.close();
                        in.close();
                    } catch  (IOException e) {
                        e.printStackTrace();
                    }
                }*/
            }
        }

    }

    public static List getAll(SmbFile file,List<Map<String,Object>> list) throws Exception {
        dirAll(file, list);
        return list;
    }

    /**
     * 上传本地文件到Samba服务器指定目录
     * @param url
     * @param auth
     * @param localFilePath
     * @throws MalformedURLException
     * @throws SmbException
     */
    public static void uploadFileToSamba(String url, NtlmPasswordAuthentication auth, String localFilePath) throws MalformedURLException, SmbException{
        if ((localFilePath == null) || ("".equals(localFilePath.trim()))) {
            System.out.println("本地文件路径不可以为空");
            return;
        }

        //检查远程父路径，不存在则创建
        SmbFile remoteSmbFile = new SmbFile(url, auth);
        String parent = remoteSmbFile.getParent();
        SmbFile parentSmbFile = new SmbFile(parent, auth);
        if (!parentSmbFile.exists()) {
            parentSmbFile.mkdirs();
        }

        InputStream in = null;
        OutputStream out = null;

        try{
            File localFile = new File(localFilePath);

            //打开一个文件输入流执行本地文件，要从它读取内容
            in = new BufferedInputStream(new FileInputStream(localFile));

            //打开一个远程Samba文件输出流，作为复制到的目的地
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteSmbFile));

            //缓冲内存
            byte [] buffer =  new   byte [1024];
            while  (in.read(buffer) != - 1 ) {
                out.write(buffer);
                buffer = new byte[1024];
            }

        } catch  (Exception e) {
            e.printStackTrace();

        } finally  {
            try  {
                out.close();
                in.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] readFileToByteArray(String filePath) throws Exception {
        SmbFile smbFile = new SmbFile(filePath);
        int length = smbFile.getContentLength();// 得到文件的大小
        byte buffer[] = new byte[length];
        SmbFileInputStream in = new SmbFileInputStream(smbFile);
        // 建立smb文件输入流
        while ((in.read(buffer)) != -1) {

        }
        in.close();


        return buffer;
    }

    public static void main(String[] args) throws Exception {
        String host = "smb://66.0.91.17/ccc/";
        //samba服务器上的文件
        String filePath = "smb://66.0.91.17/ccc/20180202/NightFlight/20170707008UV后测/\\LRP503039170601100030.jpg";
        //创建Smb文件,地址一定要使用smb://
        SmbFile remoteSmbFile = new SmbFile(host);
        List ll = SambaUtils.getAll(remoteSmbFile, new ArrayList<>());
        System.out.println(ll);
        //System.out.println(readFileToByteArray(filePath));
    }

}
