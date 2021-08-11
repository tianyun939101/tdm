package com.demxs.tdm.common.utils;

import com.demxs.tdm.comac.common.constant.StatusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jason
 * @Date: 2020/5/12 14:59
 * @Description:
 */
public class ResultMapUtil {

    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    public static Map<String,String> success(){
        return success(StatusEnum.SUCCESS.getMessage());
    }

    public static Map<String,String> success(String msg){
        Map<String, String> map = new HashMap<>(2);
        map.put(STATUS,StatusEnum.SUCCESS.getStatus());
        map.put(MESSAGE,msg);
        return map;
    }

    public static Map<String,String> failed(){
        return failed(StatusEnum.FAILED.getMessage());
    }

    public static Map<String,String> failed(String msg){
        Map<String, String> map = new HashMap<>(2);
        map.put(STATUS,StatusEnum.FAILED.getStatus());
        map.put(MESSAGE,msg);
        return map;
    }


    public static Map<String,String> missing(){
        return missing(StatusEnum.MISSING.getMessage());
    }

    public static Map<String,String> missing(String msg){
        Map<String, String> map = new HashMap<>(2);
        map.put(STATUS,StatusEnum.MISSING.getStatus());
        map.put(MESSAGE,msg);
        return map;
    }

    public static Map<String,String> error(){
        return error(StatusEnum.ERROR.getMessage());
    }

    public static Map<String,String> error(String msg){
        Map<String, String> map = new HashMap<>(2);
        map.put(STATUS,StatusEnum.ERROR.getStatus());
        map.put(MESSAGE,msg);
        return map;
    }

    public static Map<String,String> general(String status,String msg){
        Map<String, String> map = new HashMap<>(2);
        map.put(STATUS,status);
        map.put(MESSAGE,msg);
        return map;
    }

    public static Map<String,String> general(StatusEnum statusEnum){
        Map<String, String> map = new HashMap<>(2);
        map.put(statusEnum.getStatus(),statusEnum.getMessage());
        return map;
    }

    public static Map<String,String> general(StatusEnum statusEnum,String msg){
        Map<String, String> map = new HashMap<>(2);
        map.put(statusEnum.getStatus(),msg);
        return map;
    }

    public static boolean isMatch(String status,Map<String,String> map) {
        return null != map && null != status && status.equals(map.get(STATUS));
    }

    public static boolean isMatch(StatusEnum statusEnum,Map<String,String> map){
        return null != statusEnum && null != map && statusEnum.getStatus().equals(map.get(STATUS));
    }

    public static boolean isMatchAll(StatusEnum statusEnum,Map<String,String> map){
        return null != statusEnum && null != map &&
                statusEnum.getStatus().equals(map.get(STATUS)) && statusEnum.getMessage().equals(map.get(MESSAGE));
    }

    public static boolean isSuccessFully(Map<String,String> map){
        return null != map && StatusEnum.SUCCESS.getStatus().equals(map.get(STATUS));
    }

    public static boolean isMissing(Map<String,String> map){
        return null != map && StatusEnum.MISSING.getStatus().equals(map.get(STATUS));
    }

    public static boolean isFailed(Map<String,String> map){
        return null != map && StatusEnum.FAILED.getStatus().equals(map.get(STATUS));
    }

    public static boolean isError(Map<String,String> map){
        return null != map && StatusEnum.ERROR.getStatus().equals(map.get(STATUS));
    }

    public static String getMsg(Map<String,String> map){
        if(null == map){
            return null;
        }else{
            return map.get(MESSAGE);
        }
    }
}
