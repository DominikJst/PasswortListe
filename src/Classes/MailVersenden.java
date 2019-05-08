package Classes;

import org.apache.commons.io.FileUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class MailVersenden{

    private File userDirectory = FileUtils.getUserDirectory();

    public void mailSenden() {

        String sender = "smtp.gmail.com";
        String host = "";

        try {

            String email = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Passwort.txt")).get(2);
            String passwort = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Passwort.txt")).get(1);
            String passwortEncoded = new String(DatatypeConverter.parseBase64Binary(passwort));
            String emailEncoded = new String(DatatypeConverter.parseBase64Binary(email));

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);

            Session session = Session.getDefaultInstance(properties);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailEncoded));
            message.setSubject("Hello World!");
            message.setText(passwortEncoded);

            Transport.send(message);
            System.out.println("send");


        }
        catch (Exception e) {
            System.out.println(e);
        }

    }


}
