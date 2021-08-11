package com.demxs.tdm.service.sys;


import com.demxs.tdm.domain.external.MesResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ranhl on 2017-11-23.
 */
@Service
@SuppressWarnings("deprecation")
public class MesHttpClient {

    //get\post接口掉方法
    public static List<MesResult> callInterface(String lotSN) {

        HttpClient httpClient = new DefaultHttpClient();
        List<MesResult> l=new ArrayList<MesResult>();
        // HttpGet httpGet = new HttpGet("http://66.0.94.253:8018/LEJN01A.asmx/GetLotInfo?LOTSN=EGP503033171101700001");
        HttpPost httpPost=new HttpPost("http://66.0.94.253:8018/LEJN01A.asmx/GetLotInfo");
        List<NameValuePair>params=new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("LOTSN",lotSN));
        UrlEncodedFormEntity entity= null;
        try {
            entity = new UrlEncodedFormEntity(params,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(entity);
        String entityStr = null;
        try
        {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity1 = httpResponse.getEntity();
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            System.out.println("statusCode:" + statusCode);
            entityStr = EntityUtils.toString(entity1);
            SAXReader saxReader = new SAXReader();
            Document doc = null;
            List rowList = null;

            try {
                doc = saxReader.read(new ByteArrayInputStream(entityStr.getBytes()));
                Element root = doc.getRootElement();
                // 查找指定节点名称QName的所有子节点elets
                List<Element> list = root.elements("Prams");
                // 获取emp

                for (Element object : list) {
                    //  System.out.println(object.getName());
                    MesResult mb=new MesResult();
                    for (Element element : (List<Element>) object.elements()) {
                        // System.out.print(((Element) element).getName() + ":");
                        //   System.out.print(element.getText() + " ");
                        if(((Element) element).getName().equals("ProductFamilyName")){
                            mb.setProductFamilyName(element.getText());
                        }
                        if(((Element) element).getName().equals("ProductVendor")){
                            mb.setProductVendor(element.getText());
                        }
                        if(((Element) element).getName().equals("ProductName")){
                            mb.setProductName(element.getText());
                        }
                        if(((Element) element).getName().equals("ProductDesc")){
                            mb.setProductDesc(element.getText());
                        }
                        if(((Element) element).getName().equals("ProductLotSN")){
                            mb.setProductLotSN(element.getText());
                        }

                    }
                    l.add(mb);
                    //   System.out.println();
                }
//                   for(int i=0;i<l.size();i++){
//                       MesDataBean mb1=new MesDataBean();
//                       mb1=l.get(i);
//                       System.out.println("%%%%%%%%%%%%%%%="+mb1.getProductFamilyName());
//                   }

            } catch (DocumentException e) {
                e.printStackTrace();
            }

        }

        catch(Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return l;
    }





    public static void main(String[] args){
       /* List<MesResult> results = MesHttpClient.callInterface("EGP503033171101700001");
        System.out.println(JsonMapper.toJsonString(results));*/
        List<String> list = new ArrayList<String>();
        list.get(1);
    }
}




