package com.nitsoft.ecommerce.notification.email.transport;

import com.nitsoft.ecommerce.notification.email.Email;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;

public class PostalService {

    
	private static EmailTransportConfiguration emailTransportConfig = new EmailTransportConfiguration();
	private static Session session;

	public void send(Email email) throws AddressException, MessagingException {
		Message message = createMessage(email);
		send(message); 
                
	}

	protected Session getSession() {
		if (session == null) {
			Properties properties = System.getProperties();
			properties.put("mail.smtp.host", emailTransportConfig
					.getSmtpServer());
                        properties.put("mail.smtp.port", emailTransportConfig
					.getSmtpPort());
			properties.put("mail.smtp.auth", emailTransportConfig
					.isAuthenticationRequired());

			session = Session.getInstance(properties);
		}

		return session;
	}

	protected Message createMessage(Email email) throws MessagingException {
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart mimeText = new MimeBodyPart();
                mimeText.setHeader("Content-Type", "text/plain;charset=UTF-8");
                mimeText.setContent(email.getBody(), "text/html;charset=UTF-8");
		multipart.addBodyPart(mimeText);
                 
		MimeMessage message = new MimeMessage(getSession());
		message.setFrom(new InternetAddress(email.getFromAddress()));

		for (String to : email.getToAddresses()) {
			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
		}

		for (String cc : email.getCcAddresses()) {
			message.setRecipients(Message.RecipientType.CC, InternetAddress
					.parse(cc));
		}

		for (String bcc : email.getBccAddresses()) {
			message.setRecipients(Message.RecipientType.BCC, InternetAddress
					.parse(bcc));
		}

		for (String attachment : email.getAttachments()) {
			MimeBodyPart mimeAttachment = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(attachment);
			mimeAttachment.setDataHandler(new DataHandler(fds));
			mimeAttachment.setFileName(fds.getName());
			multipart.addBodyPart(mimeAttachment);
		}

		message.setContent(multipart);
		message.setSubject(email.getSubject(), "UTF-8");
		message.setHeader("X-Mailer", "Fluent Mail API"); 
		message.setSentDate(Calendar.getInstance().getTime());
                
                return message;
	}

	protected void send(Message message) throws NoSuchProviderException,
			MessagingException {
		SMTPTransport smtpTransport = (SMTPTransport) getSession()
				.getTransport(getProtocol());
		if (emailTransportConfig.isAuthenticationRequired()) {
			smtpTransport.connect(emailTransportConfig.getSmtpServer(),emailTransportConfig.getSmtpPort(),
					emailTransportConfig.getUsername(), emailTransportConfig
							.getPassword());
		} else {
			smtpTransport.connect();
		}
		smtpTransport.sendMessage(message, message.getAllRecipients());
		smtpTransport.close();
	}

	protected String getProtocol() {
		String protocol = "smtp";
		if (emailTransportConfig.useSecureSmtp()) {
			protocol = "smtps";
		}
		return protocol;
	}
}
