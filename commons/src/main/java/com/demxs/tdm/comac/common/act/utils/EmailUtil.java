package com.demxs.tdm.comac.common.act.utils;

import com.demxs.tdm.common.config.Global;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;


@Component(value = "PlanEmailUtils")
public class EmailUtil {


    Logger   log= LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    /**
     * fromMail  -- xxxx@.com
     *
     * toMail  ----xxx@.com
     *
     * subject  -----主题
     *
     * content ----邮件内容
     *
     * 普通邮件
     *
     * */
    public  void  sendMail(String toMail,String subject,String content){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String fromMail= Global.getConfig("mail.from");
        try{

            mailMessage.setFrom(fromMail);
            //接收方
            mailMessage.setTo(toMail);
            //主题
            mailMessage.setSubject(subject);
            //内容
            mailMessage.setText(content);
            javaMailSenderImpl.send(mailMessage);

        }catch (Exception  e){
            e.printStackTrace();
            log.error("--------邮件发送失败---------");
        }
    }
    /**
     * fromMail  -- xxxx@.com
     *
     * toMail  ----xxx@.com
     *
     * subject  -----主题
     *
     * content ----邮件内容
     *
     * 复杂邮件
     *
     * */
    public  void  sendFullMail(String toMail,String subject,List<T> list){
        MimeMessage message = javaMailSenderImpl.createMimeMessage();
        String fromMail= Global.getConfig("mail.from");
        try{

            MimeMessageHelper helper = new MimeMessageHelper(message, true,"utf-8");
            //发送方
            helper.setFrom(fromMail);
            //接收方
            helper.setTo(toMail);
            //主题
            helper.setSubject(subject);
            //邮件内容
            StringBuilder content = new StringBuilder("<html><head></head><body><h2>您有下表中未归还的仪器仪表设备，请尽快归还</h2>");
            content.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
            content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>设备名称</th><th>设备型号</th><th>责任人</th></tr>");

            //这一块需要重新写----遍历实体类中的属性
            for (T data : list) {
                content.append("<tr>");
                content.append("<td>" + data+ "</td>"); //第一列设备名称
                content.append("<td>" + data+ "</td>"); //第二列设备类型
                content.append("<td>" +data+ "</td>"); //第三列负责人
                content.append("</tr>");
            }
            content.append("</table>");
            content.append("</body></html>");
            helper.setText(content.toString(), true);
            javaMailSenderImpl.send(message);

        }catch (Exception  e){
            e.printStackTrace();
            log.error("--------邮件发送失败---------");
        }
    }
}
