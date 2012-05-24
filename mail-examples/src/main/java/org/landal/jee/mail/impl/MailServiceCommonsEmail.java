package org.landal.jee.mail.impl;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.landal.jee.mail.MailService;

/**
 * Send mail using commons-mail API
 *
 */
public class MailServiceCommonsEmail implements MailService {

	private static final String DEFAULT_CHARSET = "UTF8";
	
	/**
	 * File di properties da cui caricare le proprietà per inviare 
	 * la mail.
	 */
	private static final String MAIL_PROPERTIES= "mail.properties";

	private Properties properties;

	public MailServiceCommonsEmail() {
		properties = new Properties();

		try {
			ClassLoader myCL = MailServiceCommonsEmail.class.getClassLoader();
			properties.load(myCL
					.getResourceAsStream("META-INF/"+MAIL_PROPERTIES));
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

		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(getMtaAddress());
			email.setCharset(getCharset());
			// destinatari
			for (String destinatario : destinatari) {
				email.addTo(destinatario, destinatario);
			}
			email.setFrom(emailMittente, emailMittente);
			email.setSubject(oggetto);
			email.setHtmlMsg(corpo);
			email.setTextMsg("Your email client does not support HTML messages");

			// aggiungo attachment se è presente
			if (attachments != null && !attachments.isEmpty()) {
				EmailAttachment attachment = null;
				for (File fileToAttach : attachments) {
					attachment = new EmailAttachment();
					attachment.setPath(fileToAttach.getAbsolutePath());
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					attachment.setName(fileToAttach.getName());
					email.attach(attachment);
				}
			}

			email.send();

		} catch (EmailException e) {
			throw new Exception(
					"Errore nell'invio della mail: EmailException ", e);
		}

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
