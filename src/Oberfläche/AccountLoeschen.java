package Oberfläche;

import Objekte.Accounts;
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
import java.util.ArrayList;

public class AccountLoeschen extends JFrame {
    private JTextField textField1;
    private JPanel panel1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton löschenButton;
    private String readerStelle1;
    private String readerStelle2;
    private String readerStelle3;
    private File userDirectory = FileUtils.getUserDirectory();
    private ArrayList<Accounts> listOfAccountsDelete = new ArrayList<>();
    private ArrayList<Accounts> listOfAccountsDeleteNeu = new ArrayList<>();
    private File logFile = new File(userDirectory + "/PasswortSystem/Logs.txt");

    public AccountLoeschen() {
        löschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loeschen();
            }
        });
    }

    public void AccLoschen() {

        add(panel1);
        setSize(300, 400);
        setTitle("Profile löschen");
        setVisible(true);
    }

    public void loeschen() {

        try {
            String username = textField1.getText();
            String plattform = textField2.getText();
            String passwort = String.valueOf(passwordField1.getPassword());

            File directory = new File(userDirectory + "/PasswortSystem/Profile");
            int fileCount = directory.list().length;


            for (int io = 0; io < fileCount; io++) {

                readerStelle1 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (io + 1) + ".txt")).get(0);
                readerStelle2 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (io + 1) + ".txt")).get(1);
                readerStelle3 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (io + 1) + ".txt")).get(2);


                String passwordDecoded = new String(DatatypeConverter.parseBase64Binary(readerStelle1));
                String usernameDecoded = new String(DatatypeConverter.parseBase64Binary(readerStelle2));
                Accounts newEncodedAccount = new Accounts(passwordDecoded, usernameDecoded, readerStelle3);
                listOfAccountsDelete.add(newEncodedAccount);

            }

            int i = 0;

            do {
                i++;

            } while (!listOfAccountsDelete.get(i - 1).getPasswort().equals(passwort) && !listOfAccountsDelete.get(i - 1).getUsername().equals(username) && !listOfAccountsDelete.get(i - 1).getPlattform().equals(plattform));


            File user = new File(userDirectory + "/PasswortSystem/Profile/Profil" + (i) + ".txt");
            user.delete();
            neuNummerieren();
            dispose();


        } catch (Exception e) {
            loggen(e);
        }

    }

    public void neuNummerieren() {

        try {
            File directory = new File(userDirectory + "/PasswortSystem/Profile");
            int fileCount = directory.list().length;

            for (int i = 0; i < fileCount; i++) {

                File file = new File(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 1) + ".txt");

                if (file.exists()) {

                    String readerStelle1Neu = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 1) + ".txt")).get(0);
                    String readerStelle2Neu = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 1) + ".txt")).get(1);
                    String readerStelle3Neu = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 1) + ".txt")).get(2);


                    Accounts newEncodedAccountNeu = new Accounts(readerStelle2Neu, readerStelle1Neu, readerStelle3Neu);
                    listOfAccountsDeleteNeu.add(newEncodedAccountNeu);
                    File user = new File(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 1) + ".txt");
                    user.delete();

                } else {

                    String readerStelle1Neu = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 2) + ".txt")).get(0);
                    String readerStelle2Neu = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 2) + ".txt")).get(1);
                    String readerStelle3Neu = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 2) + ".txt")).get(2);


                    Accounts newEncodedAccountNeu = new Accounts(readerStelle2Neu, readerStelle1Neu, readerStelle3Neu);
                    listOfAccountsDeleteNeu.add(newEncodedAccountNeu);
                    File user = new File(userDirectory + "/PasswortSystem/Profile/Profil" + (i + 2) + ".txt");
                    user.delete();

                }
            }

            for (int ie = 0; ie < listOfAccountsDeleteNeu.size(); ie++) {

                String newFilePasswort = listOfAccountsDeleteNeu.get(ie).getPasswort();
                String newFileUsername = listOfAccountsDeleteNeu.get(ie).getUsername();
                String newFilePlattform = listOfAccountsDeleteNeu.get(ie).getPlattform();


                BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/PasswortSystem/Profile/Profil" + (ie + 1) + ".txt", true));
                writer.append((newFileUsername + ("\n")));
                writer.append((newFilePasswort + ("\n")));
                writer.append((newFilePlattform + ("\n")));
                writer.close();
            }

        } catch (Exception er) {
            loggen(er);
        }

    }


    public void loggen(Exception exception){
        try{
            JOptionPane.showMessageDialog(null, "Fehler!");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter df;
            df = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");

            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true));
            logWriter.append("Loeschen " + exception.toString() + " / " + now.format(df) + "\n");
            logWriter.close();

        }catch(Exception p){
            loggen(p);
        }

    }

}
