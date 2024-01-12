package my.edu.utem.ftmk.fis9.global.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Satrya Fajri Pratama
 * @version 1.0
 */
public class EmailSender extends Authenticator
{
	private static final String server = "smtp01.utem.edu.my";
	private static final String email = "smsm@utem.edu.my";
	private static final String password = "!p@SSW0rd#";

	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(email, password);
	}

	public synchronized void send(boolean save, String subject, String body, String... recipients) throws IOException, MessagingException 
	{
		Properties props = new Properties();

		props.put("mail.smtp.host", server);
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.socketFactory.port", "25");
		/*props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");*/
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "true");

		InternetAddress address = new InternetAddress(email, "FIS9 Administrator");
		Session session = Session.getInstance(props);
		//Session session = Session.getInstance(props, this);
		MimeMessage message = new MimeMessage(session);

		message.setFrom(address);
		message.setSender(address);
		message.setSubject(subject);
		message.setContent(body, "text/html");

		if (recipients.length > 0)
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients[0]));

		if (recipients.length > 1)
			message.setRecipient(Message.RecipientType.CC, new InternetAddress(recipients[1]));

		if (recipients.length > 2)
			message.setRecipient(Message.RecipientType.BCC, new InternetAddress(recipients[2]));

		Transport.send(message);

		if (save)
		{
			Store store = session.getStore("imap");

			store.connect(server, email, password);

			Folder folder = store.getFolder("INBOX.Sent");

			folder.open(Folder.READ_WRITE);  
			message.setFlag(Flag.SEEN, false);  
			folder.appendMessages(new Message[] {message});  
			store.close();
		}
	}
}