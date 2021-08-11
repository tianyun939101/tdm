package com.demxs.tdm.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wuliepeng
 * Date: 2017-07-25
 * Time: 下午3:32
 */
public class WebUtils {

    public static boolean isAjax(HttpServletRequest request){
//        String requestType = request.getHeader("X-Requested-With");
        String requestType = request.getHeader("X-CLIENT-TYPE");
        if(StringUtils.isEmpty(requestType)){
            requestType = request.getHeader("Access-Control-Request-Headers");
            if (StringUtils.isNotEmpty(requestType) && "X-CLIENT-TYPE".equalsIgnoreCase(requestType)){
                return true;
            }
        }
//        if (StringUtils.isNotEmpty(requestType) && "XMLHttpRequest".equalsIgnoreCase(requestType)){
        if (StringUtils.isNotEmpty(requestType) && "front-end-browser".equalsIgnoreCase(requestType)){
            return true;
        }
        return false;
    }
}
