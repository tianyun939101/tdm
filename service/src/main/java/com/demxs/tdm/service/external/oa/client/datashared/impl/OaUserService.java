package com.demxs.tdm.service.external.oa.client.datashared.impl;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.WebServiceHttpUtil;
import com.demxs.tdm.domain.external.oa.usermsg.OAUser;
import com.demxs.tdm.domain.external.oa.RequestMsg;
import com.demxs.tdm.domain.external.oa.usermsg.Org;
import com.demxs.tdm.domain.external.oa.usermsg.UserMsg;
import com.demxs.tdm.service.external.oa.client.datashared.IOaUserService;
import com.demxs.tdm.service.external.oa.client.datashared.entity.DataSharedWebService;
import com.demxs.tdm.service.external.oa.client.datashared.entity.DataSharedWebService_Service;
import com.demxs.tdm.service.external.util.XmlDataUtil;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OaUserService implements IOaUserService {

    //private DataSharedWebService webService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private OfficeService officeService;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_ROLE_NAME = "OA系统用户";
   // private static final String WSDL_URL = "http://10.11.49.169:9000/isc_mp_auth/sync_endpoint/services/dataSharedWebService?wsdl";
    private final static String CONTENT_TYPE = "text/xml;charset=utf8";
    private static Role role = null;

    String WSDL_URL= Global.getConfig("OA.OrgWerbservice");
    @Override
    public int updateFromOaSysByOrgCode(String orgCode) {
        /*if(null == webService){
            webService = new DataSharedWebService_Service().getDataSharedWebService();
        }*/
        if(null == role){
            role = systemService.getRoleByName(DEFAULT_ROLE_NAME);
        }
        //String xmlStr = XmlDataUtil.objectToXmlStr(requestMsg, RequestMsg.class);
        //String userInfoByOrg = webService.getUserInfoByOrg(xmlStr);
        String content = prepareGetOrgInfoRequestHead(orgCode);
        String userInfoByOrg = WebServiceHttpUtil.doPost(WSDL_URL, CONTENT_TYPE, content);
        if(StringUtils.isBlank(userInfoByOrg)){
            return 0;
        }
        userInfoByOrg = prepareResultStr("usermsg",userInfoByOrg);
        UserMsg user = (UserMsg) XmlDataUtil.xmlStrToObject(UserMsg.class, userInfoByOrg);
        if(null != user){
            List<OAUser> list = user.getList();
            if(null != list && !list.isEmpty()){
                return this.updateFromOaSys(list);
            }else {
              return 0;
            }
        }else{
            return 0;
        }
    }

    @Override
    public int updateFromOaSys(Collection<OAUser> collection) {
        /*if(null == webService){
            webService = new DataSharedWebService_Service().getDataSharedWebService();
        }*/
        int count = 0;
        for (OAUser oaUser : collection) {
            User u = new User();
            u.setLoginName(oaUser.getUid());
            try {
                User u1 = systemService.getUserByLoginName(u.getLoginName());
                u.setPassword(SystemService.entryptPassword(DEFAULT_PASSWORD));
                if (null != u1) {
                    u.setId(u1.getId());
                }
                u.setOffice(new Office(oaUser.getOrg().getOrgCode()));
                u.setName(oaUser.getUserName());
                u.setMobile(oaUser.getMobile());
                u.setEmail(oaUser.getEmail());
                if("M".equals(oaUser.getSex())){
                    u.setSex("0");
                }else if("F".equals(oaUser.getSex())){
                    u.setSex("1");
                }
                Role role = systemService.getRoleByName(DEFAULT_ROLE_NAME);
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                if(null != u1){
                    roleList.addAll(u1.getRoleList());
                }
                u.setRoleList(roleList);
                systemService.saveUser(u);
                count++;
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return count;
    }

    /**
    * @author Jason
    * @date 2020/7/30 17:11
    * @params [orgCode]
    * 编译请求头信息
    * @return java.lang.String
    */
    private static String prepareGetOrgInfoRequestHead(String orgCode){
        return prepareRequestHead("user:getUserInfoByOrg",orgCode);
    }

    /**
    * @author Jason
    * @date 2020/7/30 17:11
    * @params [flag, orgCode]
    * 编译请求头信息
    * @return java.lang.String
    */
    private static String prepareRequestHead(String flag,String orgCode){
        StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:user=\"http://ideal.com/user\">\n");
        sb.append(" <soapenv:Header/><soapenv:Body>\n<").append(flag).append(">\n");
        sb.append(" <requestData><![CDATA[<requestMsg><ssoQuery>11</ssoQuery><sysCode>tdm</sysCode><orgCode>")
                .append(orgCode).append("</orgCode></requestMsg>]]></requestData>\n");
        sb.append("  </").append(flag).append(">\n</soapenv:Body>\n</soapenv:Envelope>");
        return sb.toString();
    }

    /**
     * @author Jason
     * @date 2020/7/30 17:12
     * @params [flag, body]
     * 截取、转义webService服务器返回的数据
     * @return java.lang.String
     */
    private static String prepareResultStr(String flag,String body){
        int start = body.indexOf("&lt;" + flag + "&gt;");
        int end = body.lastIndexOf("&lt;/" + flag + "&gt;") + ("&lt;/" + flag + "&gt;").length();
        if(end - start > 1){
            body = body.substring(start,end);
        }
        body = body.replaceAll("&quot;","\"");
        body = body.replaceAll("&lt;","<");
        body = body.replaceAll("&gt;",">");
        return body;
    }
}
