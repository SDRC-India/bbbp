package org.sdrc.bbbp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.WordUtils;
import org.sdrc.bbbp.models.MailModel;
import org.sdrc.bbbp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private MessageSource notificationSource;
	
	@Override
	public String sendMail(MailModel mail) throws MessagingException   {
	
		Properties props = new Properties();
		props.put(
				notificationSource.getMessage(Constants.Mail.SMTP_HOST_KEY, null, null),
				notificationSource.getMessage(Constants.Mail.SMTP_HOST, null, null));
		props.put(notificationSource.getMessage(Constants.Mail.SOCKETFACTORY_PORT_KEY,
				null, null), notificationSource.getMessage(
				Constants.Mail.SOCKETFACTORY_PORT, null, null));
		props.put(notificationSource.getMessage(Constants.Mail.SOCKETFACTORY_CLASS_KEY,
				null, null), notificationSource.getMessage(
				Constants.Mail.SOCKETFACTORY_CLASS, null, null));
		props.put(
				notificationSource.getMessage(Constants.Mail.SMTP_AUTH_KEY, null, null),
				notificationSource.getMessage(Constants.Mail.SMTP_AUTH, null, null));
		props.put(
				notificationSource.getMessage(Constants.Mail.SMTP_PORT_KEY, null, null),
				notificationSource.getMessage(Constants.Mail.SMTP_PORT, null, null));

		javax.mail.Session session = javax.mail.Session.getDefaultInstance(
				props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(notificationSource
								.getMessage(Constants.Mail.AUTHENTICATION_USERID,
										null, null), notificationSource.getMessage(
								Constants.Mail.AUTHENTICATION_PASSWORD, null, null));
					}
				});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(notificationSource.getMessage(
				Constants.Mail.AUTHENTICATION_USERID, null, null)));

		// adding "to"
		List<String> toList = mail.getToEmailIds();
		String toAddress = "";

		for (String to : toList) {
			toAddress += to;
			if (toList.size() > 1) {
				toAddress += ",";
			}
		}

		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toAddress));

		// adding "cc"
		List<String> ccList = mail.getCcEmailIds();
		if (null != ccList && ccList.size() > 0) {
			String ccAddress = "";

			for (String cc : ccList) {
				ccAddress += cc;
				if (ccList.size() > 1) {
					ccAddress += ",";
				}
			}

			message.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(ccAddress));
		}

		

		message.setSubject(mail.getSubject());

		//set mail message
		
		String mailMessageBody = null != mail.getMessage() ? mail.getMessage() : "";

		String msg = (String) ("<html>"
				+ "<body><b>Dear " + WordUtils.capitalize(mail.getToUserName()) + ",</b><br><br>"
		// + "NOTIFICATION DETAILS:" + "\n" + "Message : " + mail.getMsg()
				+ mailMessageBody + "<br><br><b>" + "Regards," + "<br>" + mail.getFromUserName()+"</b>"
						+ "	</body>"
						+ "</html>");
		
		// for attaching files and send it through email	
		if(mail.getAttachments()==null)
		{
			message.setContent(msg,"text/html");
		}
		
		else if(mail.getAttachments().size() > 0) {
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(msg,"text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			
			Iterator<Entry<String, String>> it = mail.getAttachments().entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
		        
		        String path = (String)pair.getValue();
				String name = (String)pair.getKey();
				
				messageBodyPart = new MimeBodyPart();
				String filename = path +  name;
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(name);
				multipart.addBodyPart(messageBodyPart);
		        
		    }
			
			message.setContent(multipart);
			
		} 
		
		Transport.send(message);
		return "Done";
	}

}
