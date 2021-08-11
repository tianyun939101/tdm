package com.demxs.tdm.service.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.sys.Cmos;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.client.CookieStore;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.ReturnType;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * CMOS文件系统接口
 */
@Service
public class CMOSHttpService{
    // 创建CookieStore实例yuangong/form
    static CookieStore cookieStore = null;
    static HttpClientContext context = null;

    public HttpEntity post(String userNo, String documentCode, String documentVersion , String sourceSystem) throws UnsupportedEncodingException {
        //测试
        String testUri = "http://6.40.18.116:6030/cmosDocumentInfo/cmosDocumentDownLoadAndView";
        //正式
        String productUri = "http://6.40.18.116:6030/cmosDocumentInfo/cmosDocumentDownLoadAndView";
        StringBuffer uri = new StringBuffer();
        uri.append(testUri+"?");
        uri.append("userNo="+userNo);
        uri.append("&");
        uri.append("documentCode="+documentCode);
        uri.append("&");
        uri.append("documentVersion="+documentVersion);
        uri.append("&");
        uri.append("sourceSystem="+sourceSystem);
        String s1 = uri.toString();
        //获取http客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        login(httpClient,"admin","admin");
        //创建POST请求
        HttpPost httpPost = new HttpPost(s1);

        //设置ContentType
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        JSONObject obj = new JSONObject();
        obj.put("userNo", userNo);
        obj.put("documentCode", documentCode);
        obj.put("decomentVersion", documentVersion);
        obj.put("sourceSystem", sourceSystem);
        StringEntity s = new StringEntity(obj.toString());
        httpPost.setHeader("Accept", "application/json");
        //设置响应模型
        CloseableHttpResponse loginResponse = null;
        try {
            List<Cookie> cookies = cookieStore.getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }
            httpPost.setHeader("cookie",tmpcookies);
            /*执行请求，获取响应结果*/
            //客户端执行POST登录请求
            httpPost.setEntity(s);
            loginResponse = httpClient.execute(httpPost);
            //返回响应模型
            HttpEntity entity = loginResponse.getEntity();
            return entity;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void login(CloseableHttpClient httpClient, String username, String userPass) {
        try {
            HttpPost httpPost = new HttpPost("http://6.40.18.116:6030/login"); //http://10.11.53.247:6030/login(正式环境)
            /*ArrayList<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();//用来存放post请求的参数，前面一个键，后面一个值
            data.add(new BasicNameValuePair("username", username));//把昵称放进去
            data.add(new BasicNameValuePair("userPass", userPass));//把密码放进去
            //输入数据编码
            UrlEncodedFormEntity postEntity = postEntity = new UrlEncodedFormEntity(data, "UTF-8");*/
            JSONObject obj = new JSONObject();
            obj.put("userName", username);
            obj.put("userPass", userPass);
            StringEntity s = new StringEntity(obj.toString());
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(s);

            // 执行post请求
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpPost);
           /* String location = httpResponse.getFirstHeader("Location").getValue();
            if (location != null && location.startsWith("")) {
                System.out.println("loginError");
            }*/
            // cookie store
            setCookieStore(httpResponse);

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setContext() {
        context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider> create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY,
                        new BrowserCompatSpecFactory()).build();
        context.setCookieSpecRegistry(registry);
        context.setCookieStore(cookieStore);
    }

    public static void setCookieStore(HttpResponse httpResponse) {
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie")
                .getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
                setCookie.indexOf(";"));
        System.out.println("JSESSIONID:" + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
                JSESSIONID);
        cookie.setVersion(0);
        //跨域
        cookie.setDomain("6.40.18.116"); //正式环境：10.11.53.247    测试环境：6.40.18.116
        //同一应用服务器内共享
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
    }



}
