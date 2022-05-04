/*
 * Recruitment System 2022
 */
package com.recruitment.backend.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.recruitment.backend.entity.User;
import com.recruitment.backend.util.RequestModel;

/*
 *tharinda.jayamal@gmail.com
 */
@Service
public class EmailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${mail.protocol}")
	private String mailProtocol;

	@Value("${mail.host}")
	private String mailHost;

	@Value("${stmp.auth}")
	private String stmpAuth;

	@Value("${stmp.port}")
	private String stmpPort;

	@Value("${mail.debug}")
	private String mailDebug;

	@Value("${socket.factory.port}")
	private String socketFactoryPort;

	@Value("${socket.factory.class}")
	private String socketFactoryClass;

	@Value("${socket.factory.fallback}")
	private String socketFactoryFallback;

	@Value("${sender.email}")
	private String senderEmail;

	@Value("${sender.password}")
	private String senderPassword;

	public void sendEmail(RequestModel requestModel, User recruiter) {
		log.info("Starting to sending email to "
				+ requestModel.getReceiverEmail());
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", mailProtocol);
		props.setProperty("mail.host", mailHost);
		props.put("mail.smtp.auth", stmpAuth);
		props.put("mail.smtp.port", stmpPort);
		props.put("mail.debug", mailDebug);
		props.put("mail.smtp.socketFactory.port", socketFactoryPort);
		props.put("mail.smtp.socketFactory.class", socketFactoryClass);
		props.put("mail.smtp.socketFactory.fallback", socketFactoryFallback);
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(senderEmail,
								senderPassword);
					}
				});
		Transport transport;

		try {
			transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(senderEmail);

			MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setSubject(requestModel.getSubject());
			message.setContent(requestModel.getMessage()
					+ "\n\nPlease reply to : " + recruiter.getUserName(),
					"text/plain");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					requestModel.getReceiverEmail()));

			transport.connect();
			Transport.send(message);
			transport.close();
		} catch (NoSuchProviderException e) {
			log.error("Error occured while sending email to "
					+ requestModel.getReceiverEmail());
			log.error(e.getMessage());
		} catch (MessagingException e) {
			log.error("Error occured while sending email to "
					+ requestModel.getReceiverEmail());
			log.error(e.getMessage());
		}
	}
}
