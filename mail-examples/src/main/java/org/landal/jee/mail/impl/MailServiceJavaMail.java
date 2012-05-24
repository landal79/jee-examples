package org.landal.jee.mail.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.landal.jee.mail.MailService;

/**
 * Send mail using javax.mail API
 * 
 *
 */
public class MailServiceJavaMail implements MailService {

	private static final Log logger = LogFactory
			.getLog(MailServiceJavaMail.class);

	private static final String DEFAULT_CHARSET = "UTF8";

	private Properties properties;

	public MailServiceJavaMail() {
		properties = new Properties();

		try {
			ClassLoader myCL = MailServiceJavaMail.class.getClassLoader();
			properties.load(myCL
					.getResourceAsStream("META-INF/mail.properties"));
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	@Override
	public boolean sendMail(String oggetto, String corpo, String emailMittente,
			List<String> emailDestinatari) throws Exception {
		sendMail(emailDestinatari, oggetto, corpo, emailMittente, null);
		return false;
	}

	public boolean sendMail(String oggetto, String corpo, String emailMittente,
			List<String> emailDestinatari, List<File> attachments)
			throws Exception {
		sendMail(emailDestinatari, oggetto, corpo, emailMittente, attachments);
		return true;
	}

	private void sendMail(List<String> destinatari, String oggetto,
			String corpo, String emailMittente, List<File> attachments)
			throws Exception {

		assert emailMittente != null : "Email mittente null";
		// Verifico i destinatari.
		assert destinatari != null && !destinatari.isEmpty() : "Nessun destinario";

		logger.info("Send Mail: [oggetto] " + oggetto + " | [mittente] "
				+ emailMittente + " | [corpo] " + corpo);

		try {

			Session session = getMailSession();

			Message message = new MimeMessage(session);

			// InternetAddress
			// imposto i destinatari
			List<InternetAddress> destAddress = new ArrayList<InternetAddress>();
			for (String to : destinatari) {
				destAddress.add(new InternetAddress(to));
			}

			message.setFrom(new InternetAddress(emailMittente));
			message.setRecipients(Message.RecipientType.TO, destAddress
					.toArray(new InternetAddress[destAddress.size()]));
			message.setSubject(oggetto);

			// imposto gli allegati
			if (attachments != null && !attachments.isEmpty()) {
				logger.info("Put mail attachemts");
				// multi-part
				Multipart multipart = new MimeMultipart();

				// inserisco il testo
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(corpo, "text/html;charset="
						+ getCharset());
				multipart.addBodyPart(messageBodyPart);

				for (File allegato : attachments) {
					messageBodyPart = new MimeBodyPart();
					messageBodyPart.setDataHandler(new DataHandler(allegato
							.toURI().toURL()));
					messageBodyPart.setFileName(allegato.getName());
					multipart.addBodyPart(messageBodyPart);
				}

				message.setContent(multipart);

			} else {
				message.setContent(corpo, "text/html;charset=" + getCharset());
			}

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'invio della mail ", e);
		}

	}

	private Session getMailSession() throws Exception {
		Context context;
		try {
			logger.info("Lookup Mail Session: " + getJNDISession());
			context = new InitialContext();
			return (Session) context.lookup(getJNDISession());
		} catch (Exception e) {
			logger.info("Mail Session not found: " + getJNDISession());
			e.printStackTrace();
			Properties props = new Properties();
			props.put("mail.smtp.host", getMtaAddress());
			return Session.getDefaultInstance(props, null);
		}
	}

	private String getJNDISession() throws Exception {
		String mailSession = properties.getProperty("jee.mail");

		if (mailSession == null)
			throw new Exception("jee.mail is not found on mail.properties!");

		return mailSession;

	}

	private String getCharset() {

		String charset = properties.getProperty("mail.charset");

		return charset != null ? charset : DEFAULT_CHARSET;
	}

	private String getMtaAddress() throws Exception {

		String mta = properties.getProperty("mail.smtp");

		if (mta == null)
			throw new Exception(
					"MTA is not found on mail.properties! [mail.smtp]");

		return mta;
	}



}
