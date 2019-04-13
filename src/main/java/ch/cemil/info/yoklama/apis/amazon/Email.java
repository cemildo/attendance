package ch.cemil.info.yoklama.apis.amazon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

@Service
public class Email {

    @Autowired
    Environment env;
    // Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "cemil82@gmail.com";
    static final String FROMNAME = "Cemil Dogan";

    // Replace recipient@example.com with a "To" address. If your account 
    // is still in the sandbox, this address must be verified.
    static final String TO = "cemil81@yahoo.com";


    // Replace smtp_username with your Amazon SES SMTP user name.
    private String SMTP_USERNAME = "AKIAIUJ5SARLSGMJ4MKQ";

    // Replace smtp_password with your Amazon SES SMTP password.
    private String SMTP_PASSWORD = "AuzqXpYamn/LPbyTgeMhmxdcIhEGwngOMkCr+2WJQJbY";



    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    // static final String CONFIGSET = "ConfigSet";

    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
    static final String HOST = "email-smtp.eu-west-1.amazonaws.com";

    // The port you will connect to on the Amazon SES SMTP endpoint. 
    static final int PORT = 587;

    static final String SUBJECT = "KZO Haftalik bilgilendirme!";


    public void send(String BODY) throws Exception {

        System.out.println(SMTP_USERNAME+" --- " + SMTP_PASSWORD);
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT, "UTF-8");
        msg.setContent(BODY,"text/html; charset=UTF-8");

        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        // msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}