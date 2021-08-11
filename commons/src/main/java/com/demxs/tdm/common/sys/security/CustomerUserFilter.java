package com.demxs.tdm.common.sys.security;

import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guojinlong on 2017/7/25.
 */
public class CustomerUserFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        /*if(true){
            return true;
        }*/
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map map = new HashMap();
        String contentType = request.getContentType();
        if(StringUtils.isNotBlank(contentType) && request.getContentType().contains("multipart/form-data")){
            return true;
        }
        if (subject.getPrincipal() == null) {
            if (com.demxs.tdm.common.utils.WebUtils.isAjax(httpRequest)) {
//					WebUtils.toHttp(response).sendError(SC_UNAUTHORIZED);//401
                map.put("status","401");
                WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//401
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (com.demxs.tdm.common.utils.WebUtils.isAjax(httpRequest)) {
                map.put("status", "403");
                WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//403
//					WebUtils.toHttp(response).sendError(SC_FORBIDDEN);
            } else {
                String unauthorizedUrl = this.getLoginUrl();
                if (StringUtils.isNotEmpty(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    map.put("status", "401");
                    WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//401
                }
                map.put("status", "403");
//					WebUtils.toHttp(response).sendError(SC_FORBIDDEN);
                WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//403
            }

        }
        return false;
    }
}
