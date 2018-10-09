package com.cimr.job.executor.util;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * 邮件发送.Util
 *
 * @author tuping 2018-9-04 15:06:20
 */
public class MailUtil {
	private static Logger logger = LoggerFactory.getLogger(MailUtil.class);
	private static String hostName = "smtp.163.com";
	private static String user = "cimriot@163.com";
	private static String pwd = "tuping1982";
	private static String nickName = "长智物联";
	
	/**
	 *
	 * @param toAddress		收件人邮箱
	 * @param mailSubject	邮件主题
	 * @param mailBody		邮件正文
	 * @return
	 */
	public static boolean sendMail(String toAddress, String mailSubject, String mailBody){

		try {
			// Create the email message
			HtmlEmail email = new HtmlEmail();

			//email.setDebug(true);		// 将会打印一些log
			//email.setTLS(true);		// 是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
			//email.setSSL(true);

			email.setHostName(hostName);
			email.setSmtpPort(25);
			//email.setSslSmtpPort(port);
			email.setAuthenticator(new DefaultAuthenticator(user, pwd));
			email.setCharset(Charset.defaultCharset().name());

			email.setFrom(user, nickName);
			email.addTo(toAddress);
			email.setSubject(mailSubject);
			email.setMsg(mailBody);

			//email.attach(attachment);	// add the attachment

			email.send();				// send the email
			return true;
		} catch (EmailException e) {
			logger.error(e.getMessage(), e);

		}
		return false;
	}

}
