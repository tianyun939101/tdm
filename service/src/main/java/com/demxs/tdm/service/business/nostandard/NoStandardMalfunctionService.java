package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.dto.ApproveDTO;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.ATAChapterDao;
import com.demxs.tdm.dao.business.nostandard.NoStandardMalfunctionDao;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.yuangong.YuangongDao;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.configuration.NoStandardMalfunctionServiceEnum;
import com.demxs.tdm.domain.business.nostandard.NoStandardMalfunction;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class NoStandardMalfunctionService extends CrudService<NoStandardMalfunctionDao, NoStandardMalfunction> {
    @Autowired
    private UserDao userDao;
    @Autowired
    private LabInfoDao labInfoDao;
    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private NoStandardMalfunctionDao noStandardMalfunctionDao;
    @Autowired
    private ATAChapterDao ataChapterDao;
    @Autowired
    private YuangongDao yuangongDao;

    // ??????CookieStore??????yuangong/form
    static CookieStore cookieStore = null;
    static HttpClientContext context = null;

    @Override
    public Page<NoStandardMalfunction> findPage(Page<NoStandardMalfunction> page, NoStandardMalfunction entity) {
        ArrayList<NoStandardMalfunction> noStandardMalfunctions = new ArrayList<>();
        Page<NoStandardMalfunction> page1 = super.findPage(page, entity);
        List<NoStandardMalfunction> list = page1.getList();
        if(CollectionUtils.isNotEmpty(list)){
            for(NoStandardMalfunction malfunction : list){
                //??????????????????????????????
                User u =  new User();
                u.setNo(malfunction.getJobNo());
                User user = userDao.getByEmpNo(u);
                malfunction.setJobNo(user.getNo());
                malfunction.setUser(user);
                //???????????????
                LabInfo labInfo = labInfoDao.get(malfunction.getOrganizationNum());
                malfunction.setOrganizationName(labInfo.getName());
                noStandardMalfunctions.add(malfunction);
            }
        }
        page1.setList(noStandardMalfunctions);
        return page1;
    }

    public NoStandardMalfunction edit(NoStandardMalfunction malfunction){
        NoStandardMalfunction  m1 = new NoStandardMalfunction();
        //????????????
        if(malfunction !=null && StringUtils.isNotBlank(malfunction.getId())){
            m1 =  noStandardMalfunctionDao.get(malfunction.getId());
        }else{
            m1 = noStandardMalfunctionDao.findByTaskId(malfunction.getTaskId());
        }

            if(m1 != null){
                malfunction = m1;
                if(StringUtils.isNotBlank( malfunction.getJobNo())){
                    User user1 = new User();
                    user1.setNo(malfunction.getJobNo());    //??????????????????
                    User user = userDao.getByEmpNo(user1);
                    if (user != null) {
                        //?????????????????????
                        malfunction.setJobNo(user.getNo());
                        malfunction.setJobId(user.getId());
                        malfunction.setUser(user);
                    }
                }
                //ATA??????
                String ataNumId = malfunction.getAta();
                if(StringUtils.isNotBlank(ataNumId)){
                    String[] split = ataNumId.split("???");
                    ArrayList<ATAChapter> ataChapters = new ArrayList<>();
                    for(String s : split){
                        ATAChapter ataChapter = ataChapterDao.get(s);
                        ataChapters.add(ataChapter);
                    }
                    malfunction.setATAList(ataChapters);
                }
                //?????????
                if(malfunction.getAuthorizedMinUser() != null){
                    String id = malfunction.getAuthorizedMinUser().getId();
                    User user = userDao.get(id);
                    malfunction.setAuthorizedMinUser(user);
                    malfunction.setOrganizationName(user.getLabInfoName());
                }

            }else{  //????????????
                User user = UserUtils.getUser();
                if (user != null) {
                    malfunction.setUser(user);
                    malfunction.setJobNo(user.getNo());
                    malfunction.setJobId(user.getId());
                    malfunction.setOrganizationNum(user.getLabInfoId());
                    malfunction.setOrganizationName(user.getLabInfoName());
                }
            }
        return  malfunction;
    }



    /**
     * ?????????????????????????????????
     * @param malfunction
     */
    public void submit(NoStandardMalfunction malfunction, String flag) {
        //?????????????????????
        malfunction.setStatus(NoStandardMalfunctionServiceEnum.APPLY.getCode());

        if("submit".equalsIgnoreCase(flag)){
            this.save(malfunction);
            User auditUser = UserUtils.get(malfunction.getAuthorized());
            malfunction.setAuthorizedMinUser(auditUser);
            String title = "?????????'"+UserUtils.getUser().getName()+"'????????????????????????";
            Map<String,Object> vars = new HashMap<>(1);
            vars.put("message", title);
            //????????????
            actTaskService.apiStartProcess("testMalfunction", auditUser.getLoginName(), malfunction.getId(), title, vars);
            //????????????,????????????????????????
            malfunction.setStatus(NoStandardMalfunctionServiceEnum.AUTHOR.getCode());
            //?????????????????????
            malfunction.setAuditUser(malfunction.getAuthorizedMinUser());
    }
        this.save(malfunction);
    }


    /**
     * ??????
     * @param approveDto
     */
    public void approve(ApproveDTO approveDto) {
        //????????????
        NoStandardMalfunction malfunction = this.get(approveDto.getId());
        String status=malfunction.getStatus();
        //?????????????????????
        User auditUser = UserUtils.get(malfunction.getAuthorizedMinUser().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //??????
        String title = "?????????'"+auditUser.getName()+"'????????????????????????";
        vars.put("message", title);
        actTaskService.apiComplete(malfunction.getId(),approveDto.getOpinion(), Global.YES, auditUser.getLoginName(),vars);
        //????????????
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,1,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //??????????????????????????????
        malfunction.setAuditUser(malfunction.getAuthorizedMinUser());
        switch (status){
            case "01": status = NoStandardMalfunctionServiceEnum.PROO.getCode();       //??????=???????????????
                break;
            case "02":status = NoStandardMalfunctionServiceEnum.EXIT.getCode(); //????????????=????????????
                break;
        }
        malfunction.setStatus(status);
        this.save(malfunction);
        String status1 = malfunction.getStatus();

        if("03".equals(status1)){
            try {
                post(malfunction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ??????
     * @param approveDto
     */
    public void reject(ApproveDTO approveDto) {
        //????????????
        NoStandardMalfunction malfunction = this.get(approveDto.getId());
        User user1 = new User();
        user1.setNo(malfunction.getJobNo());    //??????????????????
        malfunction.setUser(userDao.getByEmpNo(user1));
        malfunction.setId(approveDto.getId());
        //???????????????
        User auditUser = UserUtils.get(malfunction.getUser().getId());
        Map<String,Object> vars = new HashMap<>(1);
        //??????
        User user = userDao.get(malfunction.getAuthorizedMinUser().getId());
        String title = "?????????????????????????????????'"+user.getName()+"'?????????";
        vars.put("message", title);
        String assignee = auditUser== null ? "" : auditUser.getLoginName();
        actTaskService.apiComplete(malfunction.getId(),approveDto.getOpinion(), Global.NO, assignee,vars);
        //????????????
        AuditInfo auditInfo = new AuditInfo(approveDto.getId(),auditUser,0,approveDto.getOpinion());
        auditInfoService.save(auditInfo);
        //??????????????????????????????
        malfunction.setAuditUser(malfunction.getUser());
        malfunction.setStatus(NoStandardMalfunctionServiceEnum.REJECT.getCode());
        this.save(malfunction);
    }


    /**
     * ????????????,????????????
     */
    public HttpEntity post(NoStandardMalfunction noStandardMalfunction) throws IOException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //??????
        String testUri = "http://6.40.20.131:80/api/v1/fracasInfo/";
        StringBuffer uri = new StringBuffer();
        uri.append(testUri);
        String s1 = uri.toString();
        //??????http?????????
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse fracasTest = login(httpClient, "fracasTest", "Comac.001");
        HttpEntity post = fracasTest.getEntity();
        String postStr = EntityUtils.toString(post);
        JSONObject jsonObject =JSONObject.fromObject(postStr);
        String token = (String)jsonObject.get("token");
        System.out.println(token);
        //??????POST??????
        HttpPost httpPost = new HttpPost(s1);

        //??????ContentType
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
        Date happenDate = noStandardMalfunction.getHappenDate();
        Date reportDate = noStandardMalfunction.getReportDate();
        //????????????
        obj.put("token", token);    //token??????
        obj.put("id", noStandardMalfunction.getId());   //id
        obj.put("aircraft", noStandardMalfunction.getAirModel());   //????????????
        obj.put("ata", noStandardMalfunction.getAta());    //ata??????
        obj.put("correction", "");
        obj.put("analysis_basic", "");
        obj.put("emergency_measure", "");
        obj.put("emergency_measure_basic", "");
        obj.put("flight_phase", "");
        obj.put("immediate_cause", "");
        obj.put("occurrence_time", f.format(happenDate));   //????????????
        obj.put("occurrence_place", noStandardMalfunction.getHappenPlace());    //????????????
        obj.put("problem_consequence", "");
        obj.put("problem_description", noStandardMalfunction.getMatterDes());    //????????????
        obj.put("problem_number", noStandardMalfunction.getTaskNo());
        obj.put("problem_sources", "");
        obj.put("problem_title", noStandardMalfunction.getMatterTittle());  //????????????
        obj.put("remote_cause", "");
        obj.put("report_agency", "");
        obj.put("report_time",  f.format(reportDate));   //????????????
        obj.put("reporter", noStandardMalfunction.getJobNo());  //?????????
        obj.put("root_cause", "");
        obj.put("warning_info", "");
        obj.put("close_state", "");
        obj.put("execution_task", noStandardMalfunction.getExecuteTask());  //????????????
        obj.put("aircraft_type", Integer.valueOf(noStandardMalfunction.getAirModel()));
        obj.put("component", "");

        StringEntity s = new StringEntity(obj.toString());
        httpPost.setHeader("Accept", "application/json");
        //??????????????????
        CloseableHttpResponse loginResponse = null;
        try {
            List<Cookie> cookies = cookieStore.getCookies();
            String tmpcookies= "";
            for(Cookie c:cookies){
                tmpcookies += c.toString()+";";
            }
            httpPost.setHeader("cookie",tmpcookies);
            /*?????????????????????????????????*/
            //???????????????POST????????????
            httpPost.setEntity(s);
            loginResponse = httpClient.execute(httpPost);
            //??????????????????
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
    /**
     * ??????
     * @param httpClient
     * @param username
     * @param userPass
     */
    public HttpResponse login(CloseableHttpClient httpClient, String username, String userPass)  {
        try {
            HttpPost httpPost = new HttpPost("http://6.40.20.131:80/api/v1/user/login"); //http://10.11.53.247:6030/login(????????????)
            /*ArrayList<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();//????????????post???????????????????????????????????????????????????
            data.add(new BasicNameValuePair("username", username));//??????????????????
            data.add(new BasicNameValuePair("userPass", userPass));//??????????????????
            //??????????????????
            UrlEncodedFormEntity postEntity = postEntity = new UrlEncodedFormEntity(data, "UTF-8");*/
            JSONObject obj = new JSONObject();
            obj.put("username", username);
            obj.put("password", userPass);
            StringEntity s = new StringEntity(obj.toString());
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(s);

            // ??????post??????
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpPost);
           /* String location = httpResponse.getFirstHeader("Location").getValue();
            if (location != null && location.startsWith("")) {
                System.out.println("loginError");
            }*/
            // cookie store
          //  setCookieStore(httpResponse);
            return httpResponse;

        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        // ????????????Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
                JSESSIONID);
        cookie.setVersion(0);
        //??????
        cookie.setDomain("6.40.20.131");
        //??????????????????????????????
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
    }

}
