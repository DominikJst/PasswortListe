package Oberfläche;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Login extends JFrame {
    private JPasswordField passwordField1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton einloggenButton;
    private JButton accountErstellenButton;
    private JButton passwortVergessenButton;
    private File userDirectory = FileUtils.getUserDirectory();
    private File logFile = new File(userDirectory + "/PasswortSystem/Logs.txt");


    public void Login() {

        add(panel1);
        setSize(700, 500);
        setTitle("Login Menü");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    public Login() {
        einloggenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(userDirectory + "/PasswortSystem");
                    File filePasswort = new File(userDirectory + "/PasswortSystem/Passwort.txt");

                    if (filePasswort.exists()) {

                        String usernameEncoeded = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Passwort.txt")).get(0);
                        String passwordEncoded = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Passwort.txt")).get(1);

                        String userDecode = new String(DatatypeConverter.parseBase64Binary(usernameEncoeded));
                        String passDecode = new String(DatatypeConverter.parseBase64Binary(passwordEncoded));
                        String username = textField1.getText();
                        String passwort = String.valueOf(passwordField1.getPassword());

                        if (passDecode.equals(passwort) && userDecode.equals(username) || passDecode.equals(passwort) && username.equals(userDecode + " ")) {

                            PasswortVerwaltung verwaltung = new PasswortVerwaltung();
                            verwaltung.Passwortverwaltung();
                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "Username oder Passwort ist falsch!");
                        }


                    } else if (!file.exists()) {

                        JOptionPane.showMessageDialog(null, "Erstellen Sie bitte zu erst einen Account!");
                        dispose();
                    }


                } catch (Exception f) {
                    loggen(f);
                }

            }
        });
        accountErstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AccountErstellen erstellen = new AccountErstellen();

                erstellen.AccountErstellen();

            }
        });
        passwortVergessenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PasswortVergessen passwortMail = new PasswortVergessen();

                passwortMail.huh();
            }
        });
    }

    public void loggen(Exception exception) {

        try {
            JOptionPane.showMessageDialog(null, "Fehler!");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter df;
            df = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");

            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true));
            logWriter.append("Login " + exception.toString() + " / " + now.format(df) + "\n");
            logWriter.close();


        } catch (Exception e) {
            loggen(e);
        }
    }

}
