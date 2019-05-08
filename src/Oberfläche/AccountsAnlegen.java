package Oberfläche;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountsAnlegen extends JFrame{
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JButton button1;
    private JPasswordField passwordField1;
    private File userDirectory = FileUtils.getUserDirectory();
    private File logFile = new File(userDirectory + "/PasswortSystem/Logs.txt");


    public AccountsAnlegen() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String plattform = textField1.getText();
                String username = textField2.getText();
                String passwort = String.valueOf(passwordField1.getPassword());

                String usernameEncoded = DatatypeConverter.printBase64Binary(username.getBytes());
                String passwortEncoded = DatatypeConverter.printBase64Binary(passwort.getBytes());

                anlegen(passwortEncoded, usernameEncoded, plattform);

                dispose();
            }
        });
    }

    public void AccountsAnlegen() {

        add(panel1);
        setSize(300, 400);
        setTitle("Profile anlegen");
        setVisible(true);
    }



    public void anlegen(String passwortVersch, String usernameVersch, String plattformVersch) {

        try {
            File directory = new File (userDirectory +"/PasswortSystem/Profile");

            if(!directory.exists()){

                directory.mkdir();
            }

            int fileCount = directory.list().length;

            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/PasswortSystem/Profile/Profil" + (fileCount + 1) +".txt", true));
            writer.append((passwortVersch + ("\n")));
            writer.append((usernameVersch + ("\n")));
            writer.append((plattformVersch + ("\n")));
            writer.close();

        }catch(Exception e) {

            JOptionPane.showMessageDialog(null, "Der Account konnte nicht angelegt werden!");
            loggen(e);
        }

    }

    public void loggen(Exception exception) {

        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter df;
            df = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");

            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true));
            logWriter.append("Konto erstellen " + exception.toString() + " / " + now.format(df) + "\n");
            logWriter.close();


        } catch (Exception eg) {
            loggen(eg);
        }
    }


}
