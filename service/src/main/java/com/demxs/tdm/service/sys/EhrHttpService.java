package com.demxs.tdm.service.sys;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

///**
// * Created by ranhl on 2017-11-30.
// */
public class EhrHttpService {
    public static void HttpPostData() {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            String uri = "http://ehr.longi-silicon.com:8200/psc/ps/EMPLOYEE/HRMS/s/WEBLIB_LGI.ISCRIPT1.FieldFormula.IScript_Main?postDatabin=y";
            HttpPost httppost = new HttpPost(uri);
            //添加http头信息
          //  httppost.addHeader("Authorization", "your token"); //认证token
            httppost.addHeader("Content-Type", "application/json");
            httppost.addHeader("userid", "PSAPPS");
            httppost.addHeader("pwd", "PSAPPS");
            JSONObject obj = new JSONObject();
            obj.put("object", "dept");
            obj.put("lgi_int_dt", "2017-12-03");
            obj.put("lgi_int_flag", "M");
            httppost.setEntity(new StringEntity(obj.toString()));
            HttpResponse response;
            response = httpclient.execute(httppost);
            //检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code+"code");
            if (code == 200) {
                String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "","name": ""}
                obj= JSONObject.fromObject(rev);
                JSONArray jsonArray = JSONArray.fromObject(obj.getString("data"));
                Object[] objs = jsonArray.toArray();
                System.out.println("&&&&&&&&&&&&&&&&&&="+objs.length);
                for (Object object : objs) {
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    System.out.println(jsonObject.getString("DEPTID")+"@@@@@@@@@@@@@@@@@="+jsonObject.getString("LGI_INT_DT"));
                    if(jsonObject.has("LGI_INT_FLAG")){
                        System.out.println(jsonObject.getString("LGI_INT_FLAG"));
                    }
                   // System.out.println(jsonObject.getString("assignNo"));
                }

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        EhrHttpService.HttpPostData();
   }
//
}
