package insynctive.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.BodyTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

public class MailManager {

	public static StringBuffer getEmailByBody(String username, String password, String containsMsg) throws Exception {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com", username , password);

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);

		Message[] messages = null;
		boolean isMailFound = false;
		Message confirmationMAil = null;

		// Search for mail MAX 10 runs
		for (int i = 0; i < 10; i++) {
			messages = folder.search(new BodyTerm(containsMsg), folder.getMessages());
			for(Message message : messages){
				if (!message.isSet(Flags.Flag.SEEN)) {
					confirmationMAil = message;
					isMailFound = true;
					break;
				}
			}
			// wait for 3 seconds if message is not found
			if (!isMailFound) {
				Thread.sleep(3000);
			}
		}

		// Search latests for unread mail
		for(Message message : messages){
			if (!message.isSet(Flags.Flag.SEEN)) {
				confirmationMAil = message;
				isMailFound = true;
				break;
			}
		}
		
		// Test fails if no unread mail was found from God
		if (!isMailFound) {
			throw new Exception("Could not find email");

			// Read the content of mail and launch registration URL
		} else {
			String line;
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					confirmationMAil.getInputStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			return buffer;
		}
	}
	
	public static boolean checkIfChangeEmailIsSending(String username, String password, String beforeEmail)
			throws Exception {
		try {
			StringBuffer buffer = getEmailByBody(username, password,
					"Your login was changed from " + beforeEmail + " to " + username);
			return buffer != null;
		} catch (Exception ex) {
			return false;
		}

	}

	public static String getAuthLink(String username, String password, String runID) throws Exception {

		StringBuffer buffer = getEmailByBody(username, password, runID);
		String[] splitHref = buffer.toString().split(">Create Password Now")[0].split("href=\"");
		String registationUrl = splitHref[splitHref.length - 1].split("\"")[0];

		System.out.println(registationUrl);

		return registationUrl;
	}

	public static String getVerificationCode(String username, String password) throws Exception {

		StringBuffer buffer = getEmailByBody(username, password, "Your HR Self Service verification code is ");
		String number = buffer.toString().split("Your HR Self Service verification code is ")[1].split("<")[0];
		System.out.println(number);
		
		return number;
	}
}
