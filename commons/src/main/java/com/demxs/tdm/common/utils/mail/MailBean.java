package com.demxs.tdm.common.utils.mail;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-08-10 13:39.
 * @Modefied By：
 */
public class MailBean implements Serializable {

    private String from;
    private String fromName;
    private String[] toEmails;

    private String subject;

    private Map data ; 			//邮件数据
    private String template;	//邮件模板


    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String[] getToEmails() {
        return toEmails;
    }
    public void setToEmails(String[] toEmails) {
        this.toEmails = toEmails;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
    public Map getData() {
        return data;
    }
    public void setData(Map data) {
        this.data = data;
    }
    public String getFromName() {
        return fromName;
    }
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }


}
