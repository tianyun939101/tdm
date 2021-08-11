package com.demxs.tdm.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class WebServiceHttpUtil {

    private final static String DEFAULT_CHARSET = "UTF-8";

    /**
    * @author Jason
    * @date 2020/7/22 16:20
    * @params [wsdlUrl, contentType, content]
    * 发送post请求，并返回字符串
    * @return java.lang.String
    */
    public static String doPost(String wsdlUrl,String contentType,String content){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(wsdlUrl);
        StringEntity stringEntity = new StringEntity(content, DEFAULT_CHARSET);
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Content-Type",contentType);
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
