package Oberfläche;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountErstellen extends JFrame {
    private JTextField textField1;
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton erstellenButton;
    private JButton datenLöschenButton;
    private JTextField textField2;
    private File userDirectory = FileUtils.getUserDirectory();
    private File logFile = new File(userDirectory + "/PasswortSystem/Logs.txt");

    public AccountErstellen() {
        erstellenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File filePasswort = new File(userDirectory + "/PasswortSystem");

                if (!filePasswort.exists()) {

                    filePasswort.mkdirs();
                    String username = textField1.getText();
                    String passwort1 = String.valueOf(passwordField1.getPassword());
                    String passwortBestaetigen = String.valueOf(passwordField2.getPassword());
                    String email = String.valueOf(textField2.getText());

                    String usernameEncoded = DatatypeConverter.printBase64Binary(username.getBytes());
                    String passwortEncoded = DatatypeConverter.printBase64Binary(passwort1.getBytes());
                    String passwortBestEncoded = DatatypeConverter.printBase64Binary(passwortBestaetigen.getBytes());
                    String emailEncoded = DatatypeConverter.printBase64Binary(email.getBytes());

                    if (passwortBestEncoded.equals(passwortEncoded)) {

                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(userDirectory + "/PasswortSystem/Passwort.txt", true));
                            writer.append((usernameEncoded + ("\n")));
                            writer.append((passwortEncoded + ("\n")));
                            writer.append((emailEncoded + ("\n")));
                            writer.close();
                            BufferedWriter writeLogs = new BufferedWriter(new FileWriter(userDirectory + "/PasswortSystem/Logs.txt", true));

                            dispose();

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(null, "Der Account konnte nicht angelegt werden!");
                            loggen(ex);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Ein Account ist bereits vorhanden!");
                }
            }
        });
        datenLöschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = new File(userDirectory + "/PasswortSystem");

                int input = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich alle Daten löschen?");


                if (input == 0) {
                    try {

                        FileUtils.deleteDirectory(file);

                        JOptionPane.showMessageDialog(null, "Die Daten wurden gelöscht!");
                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Die Daten konnten nicht gelöscht werden!");
                        loggen(ex);
                    }

                }


            }
        });
    }

    public void AccountErstellen() {

        add(panel1);
        setSize(400, 450);
        setTitle("Account erstellen");
        setVisible(true);
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




