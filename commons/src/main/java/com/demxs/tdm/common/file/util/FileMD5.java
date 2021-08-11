package com.demxs.tdm.common.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 文档MD5校验
 * User: wuliepeng
 * Date: 2017-07-31
 * Time: 上午11:43
 */
public class FileMD5 {
    /**
     * Get MD5 of one file:hex string,test OK!
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");

            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16).toUpperCase();
    }

    /***
     * Get MD5 of one file！test ok!
     *
     * @param filepath
     * @return
     */
    public static String getFileMD5(String filepath) {
        File file = new File(filepath);
        return getFileMD5(file);
    }


    /***
     * compare two file by Md5
     *
     * @param file1
     * @param file2
     * @return
     */
    public static boolean isSameMd5(File file1, File file2){
        String md5_1=getFileMD5(file1);
        String md5_2=getFileMD5(file2);
        return md5_1.equals(md5_2);
    }
    /***
     * compare two file by Md5
     *
     * @param filepath1
     * @param filepath2
     * @return
     */
    public static boolean isSameMd5(String filepath1, String filepath2){
        File file1=new File(filepath1);
        File file2=new File(filepath2);
        return isSameMd5(file1, file2);
    }
    public static void main(String[] args){
        String file1 = "D:\\java_dev\\install_package\\aliyun_java_sdk_20141113\\aliyun-sdk-oss-2.0.0.jar";
        String file2 = "D:\\java_dev\\install_package\\aliyun_java_sdk_20141113\\md5_test.zip";
        System.out.println(getFileMD5(new File(file1)));
        System.out.println(getFileMD5(file2));
        System.out.println(isSameMd5(file1,file2));

    }
}
