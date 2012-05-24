package org.landal.jee.mail;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.landal.jee.mail.impl.MailServiceCommonsEmail;

public class EmailServiceTest {

	private MailService emailService;

	public EmailServiceTest() {
		this.emailService = new MailServiceCommonsEmail();
	}

	@Test
	public void testSendMail() {

		List<String> destinatari = new ArrayList<String>();
		destinatari.add("alex.landini@gmail.com");

		try {
			emailService.sendMail("oggetto", "<html><p>corpo</p></html>",
					"alex.landini@gmail.com", destinatari);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSendMailWithAttchment() {

		List<String> destinatari = new ArrayList<String>();
		destinatari.add("alex.landini@gmail.com");

		List<File> allegati = new ArrayList<File>();
		allegati.add(new File("target/test-classes/allegato-email.rtf"));

		try {
			emailService.sendMail("oggetto", "<html><p>corpo</p></html>",
					"alex.landini@gmail.com", destinatari, allegati);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
