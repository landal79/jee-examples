package org.landal.jee.mail;

import java.io.File;
import java.util.List;

public interface MailService {

	/**
	 * Invio di una mail con allegati.
	 * 
	 * @param oggetto
	 *            Oggetto della mail.
	 * 
	 * @param corpo
	 *            Corpo della mail, in HTML.
	 * 
	 * @param emailMittente
	 *            Indirizzo email del mittente.
	 * 
	 * @param emailDestinatari
	 *            Lista degli indirizzi email dei destinatari.
	 * 
	 * @param attachments
	 *            File Allegati.
	 * 
	 * @return true se e solo se l'invio della mail è andato a buon fine.
	 * 
	 * @throws Exception
	 *             Generata nel caso si siano verificati degli errori durante
	 *             l'invio della mail.
	 * 
	 */
	public boolean sendMail(String oggetto, String corpo, String emailMittente,
			List<String> emailDestinatari, List<File> attachments)
			throws Exception;
	
	
	public boolean sendMail(String oggetto, String corpo, String emailMittente,
			List<String> emailDestinatari)
			throws Exception;

}
