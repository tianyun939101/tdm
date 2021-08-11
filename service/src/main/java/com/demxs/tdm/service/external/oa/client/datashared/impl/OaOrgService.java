package com.demxs.tdm.service.external.oa.client.datashared.impl;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.WebServiceHttpUtil;
import com.demxs.tdm.domain.external.oa.orgmsg.*;
import com.demxs.tdm.service.external.oa.client.datashared.IOaOrgService;
import com.demxs.tdm.service.external.util.XmlDataUtil;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OaOrgService implements IOaOrgService {

    //private DataSharedWebService webService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String DEFAULT_ROLE_NAME = "OA系统用户";
    private static Role role = null;
   // private final static String WSDL_URL = "http://10.11.49.169:9000/isc_mp_auth/sync_endpoint/services/dataSharedWebService?wsdl";
    private final static String CONTENT_TYPE = "text/xml;charset=utf8";


    String WSDL_URL= Global.getConfig("OA.OrgWerbservice");
    @Override
    public int updateFromOaSysByOrgCode(String orgCode) {
        /*if(null == webService){
            webService = new DataSharedWebService_Service().getDataSharedWebService();
        }*/
        //String xmlStr = XmlDataUtil.objectToXmlStr(requestMsg, RequestMsg.class);
        //String orgXMLInfo = webService.getOrgInfo(xmlStr);
        if(null == role){
            role = systemService.getRoleByName(DEFAULT_ROLE_NAME);
        }
        String content = prepareGetOrgInfoRequestHead(orgCode);
        String orgXMLInfo = WebServiceHttpUtil.doPost(WSDL_URL, CONTENT_TYPE, content);
        if(StringUtils.isBlank(orgXMLInfo)){
            return 0;
        }
        orgXMLInfo = prepareResultStr("orgmsg",orgXMLInfo);
        OrgMsg orgMsg = (OrgMsg) XmlDataUtil.xmlStrToObject(OrgMsg.class, orgXMLInfo);
        if(null != orgMsg){
            List<OAOrg> list = orgMsg.getList();
            if(null != list && !list.isEmpty()){
                Map<String,String> idMap = new HashMap<>(list.size());
                for (OAOrg oaOrg : list) {
                    idMap.put(oaOrg.getOrgCode(),oaOrg.getParentOrgCode());
                }
                int count = 0;
                for (OAOrg oaOrg : list) {
                    Office office = new Office(oaOrg.getOrgCode());
                    Office o1 = officeService.get(office);
                    if(null == o1){
                        office.setIsNewRecord(true);
                    }else{
                        office.setId(o1.getId());
                    }
                    office.setCode(oaOrg.getOrgCode());
                    office.setUseable("1");
                    Office tempOffice = new Office();
                    tempOffice.setCode(oaOrg.getOrgCode());
                    Office off = officeService.getByOfficeCode(tempOffice);
                    if(null != off){
                        office.setId(off.getId());
                    }
                    office.setName(oaOrg.getOrgName());

                    Office tempOffice2 = new Office();
                    tempOffice2.setId(oaOrg.getParentOrgCode());
                    StringBuilder pIds = new StringBuilder();
                    pIds.append("0").append(",");
                    tempOffice2.setParentIds(recursionGetPId(idMap,pIds,oaOrg.getParentOrgCode()));
                    office.setParent(tempOffice2);

                    if(StringUtils.isNumeric(oaOrg.getOrderNum())){
                        office.setSort(Integer.parseInt(oaOrg.getOrderNum()));
                    }
                    StringBuilder ids = new StringBuilder();
                    ids.append("0").append(",");
                    office.setParentIds(recursionGetPId(idMap,ids,oaOrg.getOrgCode()));
                    officeService.save(office);
                    count++;
                    UserMsg userMsg = oaOrg.getUserMsg();
                    if(null != userMsg){
                        List<OAUser> userList = userMsg.getList();
                        if(null != userList && !userList.isEmpty()){
                            for (OAUser oaUser : userList) {
                                User u = new User();
                                u.setLoginName(oaUser.getUid());
                                try {
                                    User u1 = systemService.getUserByLoginName(u.getLoginName());
                                    if (null != u1) {
                                        u.setId(u1.getId());
                                    }
                                    u.setPassword(SystemService.entryptPassword(DEFAULT_PASSWORD));
                                    u.setOffice(new Office(oaUser.getOrg().getOrgCode()));
                                    u.setName(oaUser.getUserName());
                                    u.setMobile(oaUser.getMobile());
                                    u.setEmail(oaUser.getEmail());
                                    if("M".equals(oaUser.getSex())){
                                        u.setSex("0");
                                    }else if("F".equals(oaUser.getSex())){
                                        u.setSex("1");
                                    }
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
                                }
                            }
                        }
                    }
                }
                return count;
            }
        }
        return 0;
    }

    private static String recursionGetPId(Map<String,String> map,StringBuilder ids,String code){
        String pId = map.get(code);
        if(null != pId){
            ids.append(pId).append(",");
        }else {
            return ids.toString();
        }
        return recursionGetPId(map,ids,pId);
    }

    /**
    * @author Jason
    * @date 2020/7/22 16:18
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

    /**
    * @author Jason
    * @date 2020/7/22 16:20
    * @params [orgCode]
    * 编译请求头信息
    * @return java.lang.String
    */
    private static String prepareGetOrgInfoRequestHead(String orgCode){
        return prepareRequestHead("user:getOrgInfo",orgCode);
    }

    /**
    * @author Jason
    * @date 2020/7/22 16:20
    * @params [flag, orgCode]
    * 编译请求头信息
    * @return java.lang.String
    */
    private static String prepareRequestHead(String flag,String orgCode){
        StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:user=\"http://ideal.com/user\">\n");
        sb.append(" <soapenv:Header/>\n<soapenv:Body>\n      <").append(flag).append(">\n");
        sb.append(" <requestData><![CDATA[<requestMsg><ssoQuery>11</ssoQuery><sysCode>tdm</sysCode><orgCode>")
                .append(orgCode).append("</orgCode></requestMsg>]]></requestData>\n");
        sb.append("  </").append(flag).append(">\n   </soapenv:Body>\n</soapenv:Envelope>");
        return sb.toString();
    }
}
