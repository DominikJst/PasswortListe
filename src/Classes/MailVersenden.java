package Classes;

import Oberfläche.AccountErstellen;
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



public class MailVersenden {

    AccountErstellen erstellen = new AccountErstellen();

    private File userDirectory = FileUtils.getUserDirectory();

    public void mailSenden() {

        try {

            String email = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Passwort.txt")).get(2);
            String passwort = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Passwort.txt")).get(1);
            String passwortEncoded = new String(DatatypeConverter.parseBase64Binary(passwort));
            String emailEncoded = new String(DatatypeConverter.parseBase64Binary(email));

            String from = "testbublik32@gmail.com";
            String passFrom = "PASSWORD";

            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.user", from);
            properties.put("mail.smtp.password", passFrom);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(properties);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailEncoded));

            message.setText("Das Passwort lautet: " + passwortEncoded + "\n" + "Zu dem Account mit der Mail: " + emailEncoded);
            message.setSubject("Das Passwort zu dem Account verwalter!");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, passFrom);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            erstellen.loggen("Sent message successfully");


        } catch (Exception e) {

            erstellen.loggen(e.toString());
        }


    }

}
