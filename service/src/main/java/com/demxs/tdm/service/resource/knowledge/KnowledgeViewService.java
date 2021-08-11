package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.dao.resource.knowledge.KnowledgeViewDao;
import com.demxs.tdm.domain.business.instrument.DzCirculationRecord;
import com.demxs.tdm.domain.resource.kowledge.KnowledgeView;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KnowledgeViewService extends CrudService<KnowledgeViewDao, KnowledgeView> {

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;
    @Autowired
    private KnowledgeViewDao knowledgeViewDao;
    @Autowired
    private UserDao userDao;

    public void finish(String id , String tabName){
        knowledgeViewDao.updateStatus(tabName,id);
    }

    //待编前三天发送邮件提醒
    public   void   sendMail() {
        String fromMail= Global.getConfig("mail.from");
        MimeMessage message = javaMailSenderImpl.createMimeMessage();
        try {
            List<KnowledgeView> knowledgeViews = knowledgeViewDao.sendEmail();
            if(!CollectionUtils.isEmpty(knowledgeViews)){
                for(KnowledgeView know : knowledgeViews){
                    User byName = userDao.findByName(know.getUserName());
                    if(byName !=null){
                    //User user = byName.get(0);
                    String email = byName.getEmail();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    //发送方
                    helper.setFrom(fromMail);
                    //接收方
                    helper.setTo(email);
                    //主题
                    helper.setSubject("仪器仪表");
                    //邮件内容
                    String content = "您在"+know.getTabName()+"中有待编计划，计划完成日期为："+know.getPlan()+"。请尽快完成编制计划。";
                    helper.setText(content, true);
                    javaMailSenderImpl.send(message);
                    }
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }




    }
    public KnowledgeView get(String id){
      return   knowledgeViewDao.get(id);
    }



    public List<Map<String, Object>> histogram(KnowledgeView knowledgeView){
        List<Map<String,Object>> result = new ArrayList<>();
        List<Map<String,Object>> resultMap = new ArrayList<>();
        //        //未编辑数据量
        List<KnowledgeView> count = knowledgeViewDao.findCount(knowledgeView.getTabName(),knowledgeView.getPlan());
        List<Map<String,Object>> countMap = new ArrayList<>();
        //已发布和待编数量
        List<KnowledgeView> zhishi = knowledgeViewDao.findZHISHI(knowledgeView.getTabName(),knowledgeView.getPlan());
        List<Map<String,Object>> zsMap = new ArrayList<>();
    if(CollectionUtils.isNotEmpty(zhishi)){
        for(KnowledgeView knowledgeViewCountZhiShi : zhishi){
            Map<String, Object> map = new HashMap<>();
            //已发布
            String yfb = knowledgeViewCountZhiShi.getYfb();
            //待编
            String daibian = knowledgeViewCountZhiShi.getDaibian();
            //科室
            String labName = knowledgeViewCountZhiShi.getLabName();
            map.put("yfb",yfb);
            map.put("daibian",daibian);
            map.put("labName",labName);
            zsMap.add(map);
        }
    }
        if(CollectionUtils.isNotEmpty(count)){
            for(KnowledgeView knowledgeViewCount : count){
                Map<String, Object> map = new HashMap<>();
                //未编辑
                String audited = knowledgeViewCount.getAudited();
                //科室
                String officeId = knowledgeViewCount.getOfficeId();
                map.put("labName",officeId);
                map.put("audited",audited);
                countMap.add(map);
            }
        }
        if(CollectionUtils.isNotEmpty(zsMap)){
            for(Map<String,Object> map1 : zsMap){
                Map<String, Object> map = new HashMap<>();
                Map<String, Object> map3 = new HashMap<>();
                String countLab = map1.get("labName")==null?"":map1.get("labName").toString();
                int i = 0;
                for(Map<String,Object> map2 : countMap){
                    String zsLab = map2.get("labName")==null?"":map2.get("labName").toString();
                    if(countLab.equals(zsLab)){
                        i++;
                        map3.put("audited",map2.get("audited"));
                    }
                }
                if(i>0){
                    map.put("yfb",map1.get("yfb"));
                    map.put("daibian",map1.get("daibian"));
                    map.put("labName",map1.get("labName"));
                    map.put("audited",map3.get("audited"));
                    result.add(map);
                }else{
                    map.put("yfb",map1.get("yfb"));
                    map.put("daibian",map1.get("daibian"));
                    map.put("labName",map1.get("labName"));
                    map.put("audited","0");
                    result.add(map);
                }
            }
        }
        String yfb = "";
        String daibian = "";
        String labName = "";
        String audited = "";
        if(CollectionUtils.isNotEmpty(result)) {
            for (Map<String, Object> map : result) {
                String yfb1 = map.get("yfb").toString();
                yfb += yfb1 + ",";
                String daibian1 = map.get("daibian")==null?"":map.get("daibian").toString();
                daibian += daibian1 + ",";
                String labName1 = map.get("labName")==null?"":map.get("labName").toString();
                labName += labName1 + ",";
                String audited1 = map.get("audited")==null?"":map.get("audited").toString();
                audited += audited1 + ",";
            }
        }
        Map<String, Object> mapResult = new HashMap<>();
        mapResult.put("yfb",yfb);
        mapResult.put("daibian",daibian);
        mapResult.put("labName",labName);
        mapResult.put("audited",audited);
        resultMap.add(mapResult);
        return resultMap;
    }
}
