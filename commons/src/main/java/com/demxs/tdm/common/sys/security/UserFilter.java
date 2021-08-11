package com.demxs.tdm.common.sys.security;

import com.demxs.tdm.common.sys.security.ltpa.LtpaToken;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.sys.utils.UserUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: wuliepeng
 * Date: 2017-12-15
 * Time: 下午3:10
 */
public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            if(subject.getPrincipal()==null){
                Cookie[] cookies = ((HttpServletRequest)request).getCookies();
                if(cookies == null){
                    return false;
                }
                for (Cookie cookie : cookies){
                    if(cookie.getName().equals("LtpaToken")){
                        String cookieValue = cookie.getValue();
                        LtpaToken ltpaToken = new LtpaToken(cookieValue);
                        if(ltpaToken.isValid()){
                            String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
                            UsernamePasswordToken token = new UsernamePasswordToken(ltpaToken.getUser(),null,false,host,null,false);
                            token.setLoginType(UsernamePasswordToken.LTPA_TOKEN_TYPE);
                            UserUtils.getSubject().login(token);
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
            }
            // If principal is not null, then the user is known and should be allowed access.
            return subject.getPrincipal() != null;
        }
    }
}
