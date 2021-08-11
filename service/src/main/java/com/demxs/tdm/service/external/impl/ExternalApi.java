package com.demxs.tdm.service.external.impl;

import com.demxs.tdm.domain.external.*;
import com.demxs.tdm.service.external.util.WebServiceClient;
import com.demxs.tdm.service.external.util.WebServiceConfig;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.domain.external.*;
import com.demxs.tdm.service.external.IExternalApi;
import com.landray.kmss.sys.notify.webservice.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.lang.Exception;
import java.util.*;

/**
 * 外接接口服务实现类
 * User: wuliepeng
 * Date: 2017-11-30
 * Time: 上午10:37
 */
@Service
public class ExternalApi implements IExternalApi {
    private static final Logger log = LoggerFactory.getLogger(ExternalApi.class);
    private static final int CONNECTION_TIMEOUT = 5000;

    @Override
    public List<MesResult> findSampleBomBySn(String apiAddress, String sn) throws ServiceException {
        List<MesResult> result = new ArrayList<MesResult>();
        //HttpPost httpPost = new HttpPost(apiAddress);

        //List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("LOTSN",sn));
        //UrlEncodedFormEntity encodedParams = null;
        /*try {
            //encodedParams = new UrlEncodedFormEntity(params,"utf-8");
            //httpPost.setEntity(encodedParams);
        } catch (UnsupportedEncodingException e) {
            log.error("请求参数编码错误",e);
            throw new ServiceException("请求参数编码错误",e);
        }*/

        try
        {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            /**
             * 构造请求
             */
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("LOTSN",sn));
            String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            HttpGet httpGet=new HttpGet(apiAddress+"?"+paramStr);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(CONNECTION_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);

            /**
             * 执行请求
             */
            for (int i = 0; i < 5; i++) {
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity responseEntity = httpResponse.getEntity();
                    StatusLine statusLine = httpResponse.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if(statusCode != HttpStatus.OK.value()){
                        throw new ServiceException("接口访问失败,返回状态码: " + statusCode);
                    }

                    /**
                     * 解析请求
                     */
                    String resultStr = EntityUtils.toString(responseEntity);
                    SAXReader saxReader = new SAXReader();
                    Document doc = saxReader.read(new ByteArrayInputStream(resultStr.getBytes("utf-8")));
                    Element root = doc.getRootElement();
                    // 查找指定节点名称QName的所有子节点elets
                    List<Element> list = root.elements("Prams");
                    // 获取emp
                    for (Element object : list) {
                        MesResult item=new MesResult();
                        for (Element element : (List<Element>) object.elements()) {
                            if(element.getName().equals("ProductFamilyName")){
                                item.setProductFamilyName(element.getText());
                            }
                            if(element.getName().equals("ProductVendor")){
                                item.setProductVendor(element.getText());
                            }
                            if(element.getName().equals("ProductName")){
                                item.setProductName(element.getText());
                            }
                            if(element.getName().equals("ProductDesc")){
                                item.setProductDesc(element.getText());
                            }
                            if(element.getName().equals("ProductLotSN")){
                                item.setProductLotSN(element.getText());
                            }
                        }
                        result.add(item);
                    }
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    //throw e;
                }
            }
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Boolean sendTodo(SendTodoParam todoParam) throws ServiceException {
        Boolean resultFlag = false;
        /*WebServiceConfig cfg = WebServiceConfig.getInstance();

        ISysNotifyTodoWebService service = null;
        try {
            service = (ISysNotifyTodoWebService)callService(cfg.getAddress(), cfg.getServiceClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 请在此处添加业务代码
        NotifyTodoSendContext context = new NotifyTodoSendContext();

        context.setAppName(todoParam.getAppName());
        context.setModelName(todoParam.getModelName());
        context.setModelId(todoParam.getModelId());
        context.setSubject(todoParam.getSubject());
        context.setLink(todoParam.getLink());
        context.setType(todoParam.getType());
        context.setTargets(todoParam.getTargets());
        context.setCreateTime(todoParam.getCreateTime());
        NotifyTodoAppResult result = null;
        try {
            result = service.sendTodo(context);
        } catch (Exception_Exception e) {
            log.error("发送代办任务异常",e);
        }
        if(result.getReturnState()==NotifyTodoAppResult.NO_OPERATE){
            //未操作
        }else if(result.getReturnState()==NotifyTodoAppResult.OP_FAIL){
            //操作失败
            log.error("发送代办操作失败;",result.getMessage());
        }else if(result.getReturnState()==NotifyTodoAppResult.OP_SUCCESS){
            //操作成功
            resultFlag = true;
        }
        return resultFlag;
        */
        return true;
    }

    @Override
    public Boolean deleteTodo(DeleteTodoParam todoParam) throws ServiceException {
        return this.deleteTodo(todoParam, 1);
    }
    public Boolean deleteTodo(DeleteTodoParam todoParam,int type) throws ServiceException {
        Boolean resultFlag = false;
        WebServiceConfig cfg = WebServiceConfig.getInstance();

        ISysNotifyTodoWebService service = null;
        try {
            service = (ISysNotifyTodoWebService)callService(cfg.getAddress(), cfg.getServiceClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 请在此处添加业务代码
        NotifyTodoRemoveContext context = new NotifyTodoRemoveContext();
        context.setAppName(todoParam.getAppName());
        context.setModelName(todoParam.getModelName());
        context.setModelId(todoParam.getModelId());
        context.setType(todoParam.getType());
        context.setTargets(todoParam.getTargets());
        NotifyTodoAppResult result = null;
        try {
            if(type == 1){
                context.setOptType(1);
                result = service.deleteTodo(context);
            } else if (type == 2) {
                context.setOptType(1);
                result = service.setTodoDone(context);
            }
        } catch (Exception_Exception e) {
            if(type == 1){
                log.error("删除代办任务异常",e);
            }else{
                log.error("任务设为已办异常",e);
            }
        }
        if(result.getReturnState()==NotifyTodoAppResult.NO_OPERATE){
            //未操作
        }else if(result.getReturnState()==NotifyTodoAppResult.OP_FAIL){
            //操作失败
            if(type == 1){
                log.error("删除代办操作失败;",result.getMessage());
            }else{
                log.error("设为已办操作失败;",result.getMessage());
            }
        }else if(result.getReturnState()==NotifyTodoAppResult.OP_SUCCESS){
            //操作成功
            resultFlag = true;
        }
        return resultFlag;
    }

    @Override
    public Boolean completeTodo(DeleteTodoParam todoParam) throws ServiceException {
        return this.deleteTodo(todoParam, 2);
    }

    @Override
    public List<EhrDeptResult> getEhrDeptList(String ehrAddress, String userName, String password, String synDate, String synFlag) {
        List<EhrDeptResult> l = new ArrayList<EhrDeptResult>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ehrAddress);

        //使用接口时需要传的用户名，密码：
        //userName="PSAPPS";
        // password="PSAPPS";
        //添加http头信息
        //  httppost.addHeader("Authorization", "your token"); //认证token
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("userid", userName);
        httppost.addHeader("pwd", password);
       /* JSONObject obj = new JSONObject();*/
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("object", "dept");
        obj.put("lgi_int_dt", synDate);
        obj.put("lgi_int_flag", synFlag);
        try {
            httppost.setEntity(new StringEntity(JsonMapper.toJsonString(obj)));
            HttpResponse response;
            response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code + "code");
            if (code == 200) {
                String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "","name": ""}
                Map result = (Map)JsonMapper.fromJsonString(rev,Map.class);
                List<Map<String,String>> deptArr = (List<Map<String,String>>)result.get("data");
                if(CollectionUtils.isNotEmpty(deptArr)){
                    for(Map<String,String> m:deptArr){

                            EhrDeptResult erd = new EhrDeptResult();
                        if(null!=m.get("DEPTID") && !"".equals(m.get("DEPTID"))) {
                            erd.setDeptId(m.get("DEPTID"));
                            erd.setEffStatus(m.get("EFF_STATUS"));
                            erd.setDescr(m.get("DESCR"));
                            erd.setLgiDescrEng(m.get("LGI_DESCR_ENG"));
                            erd.setManagerId(m.get("MANAGER_ID"));
                            erd.setManagerName(m.get("MANAGER_NAME"));
                            erd.setCompany(m.get("COMPANY"));
                            erd.setCompanyDescr(m.get("COMPANY_DESCR"));
                            erd.setLgiCostCengerId(m.get("LGI_COST_CENTER_ID"));
                            erd.setTreeNodeNum(new Integer(m.get("TREE_NODE_NUM")));
                            erd.setParDeptIdChn(m.get("PART_DEPTID_CHN"));
                            Date date = DateUtils.parseDate(m.get("LGI_INT_DT"));
                            erd.setLgiIntDt(date);
                            erd.setLgiIntFlag(m.get("LGI_INT_FLAG"));
                            l.add(erd);
                        }
                        }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public List<EhrEmployeeResult> getEhrEmployeeList(String ehrAddress, String userName, String password, String synDate, String synFlag) {
        List<EhrEmployeeResult> l = new ArrayList<EhrEmployeeResult>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ehrAddress);

        //使用接口时需要传的用户名，密码：
        //userName="PSAPPS";
        // password="PSAPPS";
        //添加http头信息
        //  httppost.addHeader("Authorization", "your token"); //认证token
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("userid", userName);
        httppost.addHeader("pwd", password);
        Map obj = new HashMap();
        obj.put("object", "pers");
        obj.put("lgi_int_dt", synDate);
        obj.put("lgi_int_flag", synFlag);
        try {
            httppost.setEntity(new StringEntity(JsonMapper.toJsonString(obj)));
            HttpResponse response;
            response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code + "code");
            if (code == 200) {
                String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "","name": ""}
                Map map = (Map)JsonMapper.fromJsonString(rev,Map.class);
                if(map!=null && map.size()>0){
                    if(map.containsKey("data")){
                        List<Map<String,String>> empResults = (List<Map<String,String>>)map.get("data");
                        if(CollectionUtils.isNotEmpty(empResults)){
                            for(Map<String,String> onemap:empResults){
                                EhrEmployeeResult erd=new EhrEmployeeResult();

                                if(new Integer(onemap.get("EMPL_RCD")).intValue()==0) {   //判断该条数据是否是员工的主岗数据， 0表示是主岗，1-99表示为兼职。
                                    erd.setEmplId(onemap.get("EMPLID"));
                                    erd.setName(onemap.get("NAME"));
                                    erd.setNameAc(onemap.get("NAME_AC"));
                                    erd.setSex(onemap.get("SEX"));
                                    erd.setMobilePhone(onemap.get("MOBILE_PHONE"));
                                    erd.setEmailAddr(onemap.get("EMAIL_ADDR"));
                                    erd.setHrStatus(onemap.get("HR_STATUS"));
                                    erd.setCompany(onemap.get("COMPANY"));
                                    erd.setDeptId(onemap.get("DEPTID"));
                                    erd.setOprId(onemap.get("OPRID"));
                                    erd.setLastUpdDtTm(DateUtils.parseDate(onemap.get("LASTUPDDTTM")));
                                    erd.setLgiIntFlag(onemap.get("LGI_INT_FLAG"));
                                    l.add(erd);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 调用服务，生成客户端的服务代理
     *
     * @param address WebService的URL
     * @param serviceClass 服务接口全名
     * @return 服务代理对象
     * @throws Exception
     */
    public static Object callService(String address, Class serviceClass)
            throws Exception {
        return WebServiceClient.callService(address,serviceClass);
    }

}
