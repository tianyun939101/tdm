package com.demxs.tdm.comac.common.jdbc.utils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 *
 *
 * 　如果是无需密码的共享，则类似如下格式：smb://ip/sharefolder(例如：smb://192.168.0.77/test)
 * 　如果需要用户名、密码，则类似如下格式：Smb://username:password@ip/sharefolder(例如：smb://chb:123456@192.168.0.1/test)
 *
 * // smb:域名;用户名:密码@目的IP/文件夹/文件名.xxx   test.smbPut("smb://szpcg;jiang.t:xxx@192.168.193.13/Jake", "c://test.txt") ;
 */
public class FileList {


    // 设置一个全局动态数组，来存放文件路径
    // 主要遍历文件夹，包含所有子文件夹、文件的情况时，用到递归，所以要这样设置
    public static ArrayList<Map<String,Object>> dirAllStrArr = new ArrayList<Map<String,Object>>();


    // 这里是仅仅查询当前路径下的所有文件夹、文件并且存放其路径到文件数组
    // 由于遇到文件夹不查询其包含所有子文件夹、文件，因此没必要用到递归
    public static ArrayList<Map<String,Object>> Dir(File dirFile) throws Exception {
        ArrayList<Map<String,Object>> dirStrArr = new ArrayList<Map<String,Object>>();

        if (dirFile.exists()) {
            // 直接取出利用listFiles()把当前路径下的所有文件夹、文件存放到一个文件数组
            File files[] = dirFile.listFiles();
            for (File file : files) {
                Map map = new HashMap();
                // 如果传递过来的参数dirFile是以文件分隔符，也就是/或者\结尾，则如此构造
                if (dirFile.getPath().endsWith(File.separator)) {
                    map.put("fileName",file.getName());
                    map.put("filePath",dirFile.getPath() + file.getName());
                    //dirStrArr.add(dirFile.getPath() + file.getName());
                    dirStrArr.add(map);
                } else {
                    // 否则，如果没有文件分隔符，则补上一个文件分隔符，再加上文件名，才是路径
                    /*dirStrArr.add(dirFile.getPath() + File.separator
                            + file.getName());*/
                    map.put("fileDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(file.lastModified())));
                    map.put("fileName",file.getName());
                    map.put("filePath",dirFile.getPath() + File.separator
                            + file.getName());
                    dirStrArr.add(map);
                }
            }
        }
        return dirStrArr;
    }

    public static void DirAll(File dirFile,List<Map<String,Object>> allFile) throws Exception {

        if (dirFile.exists()) {
            File files[] = dirFile.listFiles();
            for (File file : files) {
                Map map = new HashMap();
                // 如果遇到文件夹则递归调用。
                if (file.isDirectory()) {
                    // 递归调用
                    DirAll(file,allFile);
                } else {
                    // 如果遇到文件夹则放入数组
                    if (dirFile.getPath().endsWith(File.separator)) {
                        //dirAllStrArr.add(dirFile.getPath() + file.getName());
                        //dirAllStrArr.add(file.getName());
                        map.put("fileDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(file.lastModified())));
                        map.put("fileName",file.getName());
                        map.put("filePath",dirFile.getPath() + file.getName());
                        allFile.add(map);
                    } else {
                       /* dirAllStrArr.add(dirFile.getPath() + File.separator
                                + file.getName());*/
                        //dirAllStrArr.add(file.getName());
                        map.put("fileDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date(file.lastModified())));
                        map.put("fileName",file.getName());
                        map.put("filePath",dirFile.getPath() + File.separator
                                + file.getName());
                        allFile.add(map);
                    }
                }
            }
        }
    }

    public static String getFileNameWithoutExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(".") == -1)) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

        public static List getAll(File file,List<Map<String,Object>> list) throws Exception {
        DirAll(file,list);
        return list;
    }

    public static void main(String[] args) throws Exception {
        File dirFile = new File("\\\\66.0.91.17\\ccc");
        List ll = getAll(dirFile,new ArrayList<>());
        System.out.println(ll);
        System.out.println(getAll(dirFile,new ArrayList<>()));


        // smb:域名;用户名:密码@目的IP/文件夹/文件名.xxx
        //smbGet("smb://szpcg;jiang.t:xxx@192.168.193.13/Jake/test.txt", "c://") ;
        //smbPut("smb://szpcg;jiang.t:xxx@192.168.193.13/Jake", "c://test.txt") ;
        //smb://66.0.91.19/data
        //smbGet("smb://66.0.91.19/data","D:\\data");


    }
}
