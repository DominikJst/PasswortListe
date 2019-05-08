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

public class PasswortVerwaltung extends JFrame{
    private JPanel panel1;
    private JButton accountAnlegenButton;
    private JTextPane textPane1;
    private JButton accountLoeschenButton;
    private JButton verlassenButton;
    private JButton automatischGenerierenButton;
    private JButton aktualisierenButton;
    private String readerStelle1;
    private String readerStelle2;
    private String readerStelle3;
    private File userDirectory = FileUtils.getUserDirectory();
    StringBuilder buffer = new StringBuilder();
    private File logFile = new File(userDirectory + "/PasswortSystem/Logs.txt");

    public void Passwortverwaltung() {

        add(panel1);
        setSize(800, 600);
        setTitle("Hauptmenue");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public PasswortVerwaltung() {
        accountAnlegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AccountsAnlegen anlegen = new AccountsAnlegen();
                anlegen.AccountsAnlegen();
            }
        });

        verlassenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        automatischGenerierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PasswortAutomGenerieren genPass = new PasswortAutomGenerieren();
                genPass.openPassGenerieren();

            }
        });
            aktualisierenButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        buffer.setLength(0);

                        File directory = new File(userDirectory + "/PasswortSystem/Profile");
                        int ie = directory.list().length;


                        for (int f = 1; f <= ie; f++) {

                            File file = new File(userDirectory + "/PasswortSystem/Profile/Profil" + (f) + ".txt");

                            if (file.exists()) {
                                readerStelle1 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (f) + ".txt")).get(0);
                                readerStelle2 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (f) + ".txt")).get(1);
                                readerStelle3 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (f) + ".txt")).get(2);

                                String passwordDecoded = new String(DatatypeConverter.parseBase64Binary(readerStelle2));
                                String usernameDecoded = new String(DatatypeConverter.parseBase64Binary(readerStelle1));

                                String huh = passwordDecoded + " " + usernameDecoded + " " + readerStelle3;
                                buffer.append(huh).append(("\n"));
                            } else {
                                readerStelle1 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (f + 1) + ".txt")).get(0);
                                readerStelle2 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (f + 1) + ".txt")).get(1);
                                readerStelle3 = Files.readAllLines(Paths.get(userDirectory + "/PasswortSystem/Profile/Profil" + (f + 1) + ".txt")).get(2);

                                String passwordDecoded = new String(DatatypeConverter.parseBase64Binary(readerStelle2));
                                String usernameDecoded = new String(DatatypeConverter.parseBase64Binary(readerStelle1));

                                String huh = passwordDecoded + " " + usernameDecoded + " " + readerStelle3;
                                buffer.append(huh).append(("\n"));
                            }

                        }


                        textPane1.setText(buffer.toString());


                    } catch (Exception ef) {
                        loggen(ef);
                    }

                }
            });

        accountLoeschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountLoeschen loeschen = new AccountLoeschen();
                loeschen.AccLoschen();

            }
        });
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
