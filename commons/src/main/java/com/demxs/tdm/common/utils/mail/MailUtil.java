package com.demxs.tdm.common.utils.mail;

import com.demxs.tdm.common.utils.FreeMarkers;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-08-10 13:41.
 * @Modefied By：
 */
public class MailUtil {
    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private JavaMailSenderImpl javaMailSender;

    /**
     * @param mailBean
     * @return
     * @throws MessagingException
     */
    public boolean send(MailBean mailBean) throws MessagingException {
        mailBean.setFrom(javaMailSender.getUsername());
        MimeMessage msg = createMimeMessage(mailBean);
        if (msg == null) {
            return false;
        }
        this.sendMail(msg, mailBean);
        return true;
    }

    private void sendMail(MimeMessage msg, MailBean mailBean) {
        javaMailSender.send(msg);
    }

    /*
     * 记日记用的
     */
    private String arrayToStr(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (String str : array) {
            sb.append(str + " , ");
        }
        return sb.toString();
    }

    /*
     * 根据 mailBean 创建 MimeMessage
     */
    private MimeMessage createMimeMessage(MailBean mailBean) throws MessagingException {
        if (!checkMailBean(mailBean)) {
            return null;
        }
        String text = getMailText(mailBean);
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        messageHelper.setFrom(mailBean.getFrom());
        try {
            messageHelper.setFrom(mailBean.getFrom(), mailBean.getFromName());
        } catch (UnsupportedEncodingException e) {

        }
        messageHelper.setSubject(mailBean.getSubject());
        messageHelper.setTo(mailBean.getToEmails());
        messageHelper.setText(text, true); // html: true
        return msg;
    }

    //通过模板构造邮件内容，参数username将替换模板文件中的${username}标签。
    private String getMailText(MailBean mailBean) {
        String htmlText = "";
        try {
            String temp = StringEscapeUtils.unescapeHtml4(mailBean.getTemplate());
            temp = temp.replace("&lt;","<").replace("&gt;",">");
            htmlText = FreeMarkers.renderString(temp,mailBean.getData());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htmlText;
    }

    /*
	 * check 邮件
	 */
    private boolean checkMailBean(MailBean mailBean){
        if (mailBean == null) {
            return false;
        }
        if (mailBean.getToEmails() == null) {
            return false;
        }
        if (mailBean.getTemplate() == null) {
            return false;
        }
        return true;
    }

    public static void main(String[] args){
        MailBean mailBean = new MailBean();
        mailBean.setTemplate("dddddddddddddddddddd${test}");
        String[] toEmails = {"zhangren_sw@163.com"};
        Map maps = new HashMap();
        maps.put("test","111");
        mailBean.setData(maps);
        MailUtil mailUtil = new MailUtil();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        JavaMailSender javaMailSender = (JavaMailSender)ctx.getBean("mailSender");
        try {
            javaMailSender.send(mailUtil.createMimeMessage(mailBean));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //System.out.print(test);
    }
}
