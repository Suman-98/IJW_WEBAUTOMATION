package ijw.utils.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import ijw.config.framework.Base;

public class EmailReport extends Base {

	public static void emailReport(String string) {

		try {
			// Receiver's email ID needs to be mentioned
			String to = "suman.mondal@brinker.com, neelabhjyoti.mitra@brinker.com"; //basabdutta.roy@brinker.com,arnava.dutta@brinker.com,kranthi.kiran@brinker.com, neelabhjyoti.mitra@brinker.com"; //basabdutta.roy@brinker.com, , 
													// gurpreet.pal@brinker.com,soumita.paul@brinker.com,
													// neelabhjyoti.mitra@brinker.com";

			// Sender's email ID needs to be mentioned
			String from = "suman.mondal@brinker.com";
			// Assuming you are sending email from localhost
			// String host = "EXCH1.home.eat.brinker.org";
			String host = "smtp.office365.com";

			String pass = "Brinker@33435";

			// Get system properties
			Properties properties = System.getProperties();

			// Setup mail server
			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.port", "587");
			properties.put("mail.smtp.starttls.enable", true);
			properties.put("mail.smtp.auth", true);

			// Get the default Session object.
			Session session = Session.getDefaultInstance(properties);

			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDateTime now = LocalDateTime.now();
			String date = dtf.format(now);
			message.setSubject("IJW Automation Run Result_" + date);
			// Now set the actual message
			message.setText(string);
			BodyPart objMessageBodyPart = new MimeBodyPart();
			objMessageBodyPart.setContent(string, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(objMessageBodyPart);
			objMessageBodyPart = new MimeBodyPart();

			try {
				FileInputStream fis = new FileInputStream(
						PROJECT_PATH + "/src/test/java/ijw/properties/framework/data.properties");
				prop = new Properties();
				prop.load(fis);
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			String filePath = prop.getProperty("Reportpath");
			System.out.println("this is from email class-" + filePath);

			// Create data source to attach the file in mail
			DataSource source = new FileDataSource(filePath);
			objMessageBodyPart.setDataHandler(new DataHandler(source));
			objMessageBodyPart.setFileName(filePath);
			multipart.addBodyPart(objMessageBodyPart);
			message.setContent(multipart);
			// Send Email
			Transport.send(message, from, pass);
			System.out.println("Email Send Successfully");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

		/***********************************************
		 * System.out.println("12345"); Email email = new SimpleEmail();
		 * email.setHostName("smtp.office365.com"); email.setSmtpPort(25);
		 * email.setAuthenticator(new DefaultAuthenticator("suman.mondal@brinker.com",
		 * "Brinker@33435")); email.setSSLOnConnect(true);
		 * email.setFrom("suman.mondal@brinker.com"); email.setSubject("IJW AUTOMATION
		 * TEST REPORT"); email.setMsg("This is a test mail ...Hi I'm here");
		 * email.addTo("suman.mondal@brinker.com"); email.send();
		 * System.out.println("Email send ");
		 * 
		 **************************************************/
		/*
		 * prop = returnProperty(PROJECT_PATH +
		 * "/src/test/java/ijw/properties/framework/data.properties");
		 * 
		 * String to = "Suman.Mondal@brinker.com";
		 * 
		 * // Sender's email ID needs to be mentioned String from =
		 * "Suman.Mondal@brinker.com";
		 * 
		 * String password = "Brinker@33435";
		 * 
		 * // Assuming you are sending email from localhost String host =
		 * "smtp.office365.com";
		 * 
		 * // Get system properties //Properties properties = System.getProperties();
		 * 
		 * // Setup mail server prop.setProperty("mail.smtp.host", host);
		 * 
		 * prop.setProperty("mail.smtp.port", "587");
		 * 
		 * prop.put("mail.smtp.starttls.enable", true);
		 * 
		 * prop.put("mail.smtp.auth", true);
		 * 
		 * 
		 * 
		 * // Get the default Session object. Session session =
		 * Session.getDefaultInstance(prop); // Create a default MimeMessage object.
		 * MimeMessage message = new MimeMessage(session); try {
		 * 
		 * // Set From: header field of the header. message.setFrom(new
		 * InternetAddress(from));
		 * 
		 * // Set To: header field of the header.
		 * 
		 * message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		 * 
		 * // Set Subject: header field DateTimeFormatter dtf =
		 * DateTimeFormatter.ofPattern("dd/MM/yyyy"); LocalDateTime now =
		 * LocalDateTime.now();
		 * message.setSubject("Automation Run Result - "+dtf.format(now));
		 * 
		 * // Now set the actual message
		 * 
		 * message.setText("Hi team, Have a god Day....*_*");
		 * 
		 * BodyPart objMessageBodyPart = new MimeBodyPart();
		 * 
		 * objMessageBodyPart.setContent("Hi team, Have a god Day...*_*", "text/html");
		 * 
		 * Multipart multipart = new MimeMultipart();
		 * 
		 * multipart.addBodyPart(objMessageBodyPart);
		 * 
		 * multipart.addBodyPart(objMessageBodyPart);
		 * 
		 * message.setContent(multipart);
		 * 
		 * // Send message Transport.send(message,from,password);
		 * System.out.println("Email send successfully");
		 * 
		 * } catch (MessagingException mex) {
		 * System.out.println("send failed, exception: " + mex); }
		 */
		//////

	}

}