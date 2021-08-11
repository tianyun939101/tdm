package com.demxs.tdm.service.resource.knowledge;



import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.dao.business.PlanDao;
import com.demxs.tdm.dao.business.instrument.DzCirculationRecordDao;
import com.demxs.tdm.dao.business.instrument.DzInstrumentsApparatusesDao;
import com.demxs.tdm.domain.business.Plan;
import com.demxs.tdm.service.business.instrument.impl.ApparatusCategoryService;
import com.demxs.tdm.service.business.instrument.impl.ApparatusLocationService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;

@Service
public class PlanApparatusesServiceImpl<T> extends CrudService<PlanDao, Plan> {
    @Autowired
    DzInstrumentsApparatusesDao dzInstrumentsApparatusesDao;
    @Autowired
    DzCirculationRecordDao dzCirculationRecordDao;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApparatusCategoryService categoryService;
    @Autowired
    private ApparatusLocationService locationService;

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private UserDao userDao;
    public   void   planSendMail(){

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
//发送方
List<DzCirculationRecord>  userList=dzCirculationRecordDao.getUserName();
            if(!CollectionUtils.isEmpty(userList)){
                for(DzCirculationRecord  name:userList){
                    String  email=name.getEmail();
                    String userName=name.getUname();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    List<DzCirculationRecord>   list=dzCirculationRecordDao.getInOrOut(userName);
                    helper.setFrom(fromMail);
                            //接收方
                            helper.setTo(email);
                            //主题
                            helper.setSubject("计划任务");
                            //邮件内容
                            StringBuilder content = new StringBuilder("<html><head></head><body><h2>您有计划中的任务尚未完成，请尽快完成!</h2>");
                            content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
                            content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>任务名称</th><th>任务开始时间</th><th>任务应完时间</th></tr>");
                            for (DzCirculationRecord data : list) {
                            content.append("<tr>");
                            content.append("<td>" + data.taskItemName() + "</td>"); //第一列设备名称
                            content.append("<td>" + data.requireStartTime()+ "</td>"); //第二列设备类型
                            content.append("<td>" + data.requireCompleteTime() + "</td>"); //第三列负责人
                            content.append("</tr>");
                            }
                            content.append("</table>");
                            content.append("</body></html>");
                            helper.setText(content.toString(), true);
                            javaMailSenderImpl.send(message);*/


