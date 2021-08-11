package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.MailmasterDao;
import com.demxs.tdm.dao.configure.MailmobanDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.mail.MailBean;
import com.demxs.tdm.common.utils.mail.MailUtil;
import com.demxs.tdm.domain.configure.Mailmaster;
import com.demxs.tdm.domain.configure.Mailmoban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

/**
 * 邮件配置Service
 * @author 张仁
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class MailmasterService extends CrudService<MailmasterDao, Mailmaster> {
	@Autowired
	private MailmasterDao mailmasterDao;

	@Autowired
	private MailmobanDao mailmobanDao;

	@Override
	public Mailmaster get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Mailmaster> findList(Mailmaster mailmaster) {
		return super.findList(mailmaster);
	}
	
	@Override
	public Page<Mailmaster> findPage(Page<Mailmaster> page, Mailmaster mailmaster) {
		return super.findPage(page, mailmaster);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Mailmaster mailmaster) {
		super.save(mailmaster);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Mailmaster mailmaster) {
		super.delete(mailmaster);
	}

	public void tingyongqt(String id){
		mailmasterDao.tingyongqt(id);
	}

	public JavaMailSenderImpl getMailSenderInfo(HttpServletRequest request){
		Mailmaster mailmaster = (Mailmaster)request.getSession().getAttribute("mailmaster");
		if (mailmaster!=null){
			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
			javaMailSender.setHost(mailmaster.getMailhost());
			javaMailSender.setUsername(mailmaster.getUsername());
			javaMailSender.setPassword(mailmaster.getPassword());
			javaMailSender.setDefaultEncoding("UTF-8");
			//加认证机制
			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.auth", true);
			javaMailProperties.put("mail.smtp.timeout", 25000);
			javaMailProperties.put("mail.smtp.port", 465);
			javaMailProperties.put("mail.smtp.socketFactory.port", 465);
			javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
			javaMailProperties.put("javax.net.ssl.trustStore", "D:/workspaceV6R3/Java/jdk1.7.0_25/jre/lib/security/jssecacerts");
			javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			javaMailSender.setJavaMailProperties(javaMailProperties);
			return  javaMailSender;
		}
		return null;
	}

	public JavaMailSenderImpl getMailSenderInfo(String mailmasterid){
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		Mailmaster curmailmaster = this.get(mailmasterid);
		javaMailSender.setHost(curmailmaster.getMailhost());
		javaMailSender.setUsername(curmailmaster.getUsername());
		javaMailSender.setPassword(curmailmaster.getPassword());
		javaMailSender.setDefaultEncoding("UTF-8");
		//加认证机制
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.timeout", 25000);
		javaMailProperties.put("mail.smtp.port", 465);
		javaMailProperties.put("mail.smtp.socketFactory.port", 465);
		javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
		javaMailProperties.put("javax.net.ssl.trustStore", "D:/workspaceV6R3/Java/jdk1.7.0_25/jre/lib/security/jssecacerts");
		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailSender.setJavaMailProperties(javaMailProperties);
		return javaMailSender;
	}

	public Mailmaster getMailMasterqy(){
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		Mailmaster mailmasterserchbean = new Mailmaster();
		mailmasterserchbean.setIsqiyong("1");
		List<Mailmaster> mailmasterList = findList(mailmasterserchbean);
		if (mailmasterList.size()>0){
			Mailmaster curmailmaster = mailmasterList.get(0);
			return curmailmaster;
		}
		return null;
	}

	public Boolean sendMail(String id,MailBean mailBean,HttpServletRequest request){
		Mailmoban mailmoban = mailmobanDao.get(id);
		mailBean.setTemplate(mailmoban.getMobannr());
		MailUtil mailUtil = new MailUtil();
		mailUtil.setJavaMailSender(getMailSenderInfo(request));
		try {
			mailUtil.send(mailBean);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}