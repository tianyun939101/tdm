package com.demxs.tdm.common.utils;

import com.demxs.tdm.common.config.Global;

public class FastdfsPathUtils {


    public static String getFileAllPath(String path) {
        try {
            if(path == null || path.equals("")){
                return "";
            }else{
                String endPath = Global.getConfig("fastUrl")+Global.getConfig("fastdfsIP")+path;
                return endPath;
            }
        } catch (Exception e) {
            return "";
        }
    }
}
