package com.demxs.tdm.common.utils;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.utils.DictUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jason
 * @Date: 2020/7/16 11:09
 * @Description:
 */
public class FlightDataFileUtil {

    private static final Integer DEPTH = Integer.parseInt(Global.getConfig("pr.flightData.directory.depth"));
    private final static String BASE_DIE = Global.getConfig("sourceDir");
    private final static String FLIGHT_DATA_DIR = Global.getConfig("pr.flightData.synchronizeData.dir");
    private final static String SYMBOL = "/";
    public final static String PREFIX = BASE_DIE  + FLIGHT_DATA_DIR + SYMBOL;
    public final static String TYPE = "localFile";

    /**
     * 试飞文件命名空间
     */
    private final static Map<String,String> NAME_SPACE_MAP = new HashMap<>(16);

    /**
    * @author Jason
    * @date 2020/7/17 13:28
    * @params [key]
    * 获取命名空间
    * @return java.lang.String
    */
    public static String getNameSpace(int key){
        return getNameSpace(String.valueOf(key));
    }

    /**
     * @author Jason
     * @date 2020/7/17 13:28
     * @params [key]
     * 获取命名空间
     * @return java.lang.String
     */
    public static String getNameSpace(String key){
        return NAME_SPACE_MAP.get(key);
    }

    static {
        //初始化命名空间
        List<Dict> dictList = DictUtils.getDictList("flight_data_server_type");
        for (Dict dict : dictList) {
            NAME_SPACE_MAP.put(dict.getValue(),dict.getLabel());
        }
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:29
    * @params [type]
    * 获取本地指定文件夹下的指定层级的子级文件夹
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    */
    public static List<Map<String,String>> getDirList(int type){
        String nameSpace = NAME_SPACE_MAP.get(String.valueOf(type));
        if(null != nameSpace){
            return getDirList(type,PREFIX + nameSpace,nameSpace,new ArrayList<>(),DEPTH,0);
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * @author Jason
     * @date 2020/7/17 13:29
     * @params [type]
     * 获取本地指定文件夹下的指定层级的子级文件夹
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public static List<Map<String,String>> getDirList(int type,String path,String nameSpace,List<Map<String,String>> list,int depth,int curDepth){
        if(curDepth < depth) {
            File file = new File(path);
            if(file.exists()){
                File[] fileList = file.listFiles();
                if (null != fileList) {
                    for (File f : fileList) {
                        if (f.isDirectory()) {
                            String relativePath = path.substring(path.indexOf(nameSpace) + nameSpace.length());
                            String pId;
                            if(relativePath.length() > 0){
                                relativePath = relativePath.substring(1);
                                String temp = relativePath;
                                relativePath = type + SYMBOL + relativePath + SYMBOL + f.getName() + SYMBOL;
                                pId = type + SYMBOL + temp + SYMBOL;
                            }else{
                                relativePath = type + SYMBOL + f.getName() + SYMBOL;
                                pId = type + SYMBOL;
                            }
                            Map<String, String> map = new HashMap<>(3);
                            map.put("id", relativePath);
                            map.put("name", f.getName());
                            map.put("pId", pId);
                            map.put("fileType",TYPE);
                            list.add(map);
                            int tempCurDepth = curDepth + 1;
                            list.addAll(getDirList(type, path + SYMBOL + f.getName(),nameSpace, new ArrayList<>(), depth, tempCurDepth));
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * @author Jason
     * @date 2020/7/17 13:29
     * @params [type]
     * 获取本地指定文件夹下的文件，模糊搜索
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public static List<File> getFiles(String path){
        return getFiles(path,null);
    }

    /**
     * @author Jason
     * @date 2020/7/17 13:29
     * @params [type]
     * 获取本地指定文件夹下的文件，模糊搜索
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    public static List<File> getFiles(String path, String keyWord){
        List<File> list = new ArrayList<>();
        File file = new File(path);
        if(file.exists()){
            File[] files = file.listFiles();
            if(null != files){
                for (File f : files) {
                    if(f.isFile()){
                        if(StringUtils.isNotBlank(keyWord)){
                            if(f.getName().contains(keyWord) || keyWord.contains(f.getName())){
                                list.add(f);
                            }
                        }else{
                            list.add(f);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:30
    * @params [path, os]
    * 从本地读取文件写出到os
    * @return java.util.Map<java.lang.String,java.lang.String>
    */
    public static Map<String,String> downFileFromLocal(String path, OutputStream os){
        File file = new File(path);
        if(file.exists()){
            try (FileInputStream fis = new FileInputStream(file)){
                IOUtil.writeByteToFile(fis,os);
                return ResultMapUtil.success();
            }catch (Exception e){
                e.printStackTrace();
                return ResultMapUtil.success(e.getMessage());
            }
        }else{
            return ResultMapUtil.failed("文件不存在");
        }
    }

    /**
    * @author Jason
    * @date 2020/7/17 13:30
    * @params [path]
    * 判断文件是否存在
    * @return boolean
    */
    public static boolean fileExist(String path){
        return new File(path).exists();
    }
}
